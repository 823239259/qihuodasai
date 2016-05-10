package com.tzdr.business.service.pay.impl;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.tzdr.business.exception.BibiPayException;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.bbpay.Bibipay;
import com.tzdr.common.api.bbpay.vo.PayParamsObject;
import com.tzdr.common.api.umpay.FastPay;
import com.tzdr.common.api.umpay.NetDirectPay;
import com.tzdr.common.api.umpay.data.FastPayInfo;
import com.tzdr.common.api.umpay.data.NetDirectInfo;
import com.tzdr.common.api.umpay.data.PayGoodsInfo;
import com.tzdr.common.api.umpay.data.PayOrderInfo;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.domain.dao.pay.PayDao;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;


/**
 * 
 * <P>title:@PayServiceImpl.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月30日
 * @version 1.0
 */
@Service("payService")
@Transactional(propagation=Propagation.REQUIRED)
public class PayServiceImpl extends BaseServiceImpl<RechargeList,PayDao> implements PayService{
	public static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

	@Autowired
	private SecurityInfoService securityInfoService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private UserFundService userFundService;
	
	@Override
	public UserVerified findByUserId(String userId) {
		return securityInfoService.findByUserId(userId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.pay.PayService#insertEntity(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String insertEntity(int source,WUser user,String bankname,Integer status,
			String bankCard, String paymoney,String ip,String paytype,String serialnum) {
		RechargeList charge=new RechargeList();
		UserVerified verified=this.findByUserId(user.getId());
		charge.setUid(user.getId());
		charge.setAccount(bankCard);
		charge.setMoney(Double.valueOf(paymoney));
		charge.setSource(source);
		if(StringUtil.isNotBlank(serialnum)){
			charge.setTradeNo(serialnum);
		}
		double realMoney=0;
		if("1".equals(paytype)){//快捷支付
			 double free=BigDecimalUtils.mul(Double.valueOf(paymoney),0.007);
			 charge.setFee(free);
			 realMoney=BigDecimalUtils.add(Double.valueOf(paymoney), free);
		}else{
			 realMoney=Double.valueOf(paymoney);
		}
		// 网银支付时设置支付渠道
		if("2".equals(paytype)){
			charge.setPaymentChannel(1);
		}
		//生成订单号
		String 	orderId=this.getRandomStr(20);
		charge.setTradeAccount(bankname);
		charge.setAddtime(new Date().getTime()/1000);
		charge.setAddip(ip);
		charge.setType(paytype);
		charge.setNo(orderId);
		charge.setStatus(status);
		charge=getEntityDao().save(charge);
		//开始调用第三方支付接口
		PayOrderInfo payOrderInfo= new PayOrderInfo();
		PayGoodsInfo payGoodsInfo= new PayGoodsInfo();
		NetDirectInfo netDirectInfo=new NetDirectInfo();
		FastPayInfo fastPayInfo=new FastPayInfo ();
		payOrderInfo.setOrderId(orderId);
		DecimalFormat df = new DecimalFormat("#"); 
		payOrderInfo.setAmount(df.format(BigDecimalUtils.mul(realMoney,100.0)));
		payOrderInfo.setMerDate(DateUtils.dateTimeToString(new Date(),"yyyyMMdd"));
		payOrderInfo.setGateId(bankname.toUpperCase());
		String url="";
		if("1".equals(paytype)){//快捷支付
			fastPayInfo.setIdentityCode(verified.getIdcard());
			fastPayInfo.setIdentityType("IDENTITY_CARD");
			fastPayInfo.setCardId(bankCard);
			fastPayInfo.setIdentityCode(verified.getIdcard());
			fastPayInfo.setCardHolder(verified.getTname());
			FastPay fastPay=FastPay.getInstance();
		    url=fastPay.getUrl(payOrderInfo, payGoodsInfo, fastPayInfo);
		    
		}else if("2".equals(paytype)){//网银支付
			NetDirectPay netDirectPay=NetDirectPay.getInstance();
			url=netDirectPay.getUrl(payOrderInfo, payGoodsInfo, netDirectInfo);
			
		}
		return url;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.pay.PayService#updateEntity(com.tzdr.domain.web.entity.WUser, java.lang.String)
	 */
	@Override
	public void updateEntity(String orderId, String  tradeNo,String  tradeState,String  payDate,int paystatus) {
		logger.info("------------->"+orderId);
		RechargeList charge=getEntityDao().findByNo(orderId);
		if(charge!=null){
			String userId=charge.getUid();
			WUser wuser=wUserService.getUser(userId);
			int status=charge.getStatus()==null?0:charge.getStatus();
			if(status!=paystatus){
				charge.setStatus(paystatus);
				Calendar cal=Calendar.getInstance();
				String hh=String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
				String mm=cal.get(Calendar.MINUTE)<10?"0"+String.valueOf(cal.get(Calendar.MINUTE)):String.valueOf(cal.get(Calendar.MINUTE));
				String ss=cal.get(Calendar.SECOND)<10?"0"+String.valueOf(cal.get(Calendar.SECOND)):String.valueOf(cal.get(Calendar.SECOND));
				logger.info("------------->"+payDate+hh+mm+ss);
				Date date=new Date();//DateUtils.stringToDate(payDate+hh+mm+ss,"yyyyMMddHHmmss");
				long time=date.getTime()/1000;
				charge.setOktime(time);
				logger.info("------------->"+tradeState);
				if("TRADE_SUCCESS".equals(tradeState)){
					charge.setTradeNo(tradeNo);
					charge.setAddtime(new Date().getTime()/1000);
					charge.setActualMoney(charge.getMoney());
					this.getEntityDao().update(charge);
					//Double avlbal=wuser.getAvlBal()==null?0:wuser.getAvlBal();
					//Double acctbal=wuser.getAcctBal()==null?0:wuser.getAcctBal();
					//Double wfund=wuser.getFund()==null?0:wuser.getFund();
					logger.info("money------------->"+charge.getMoney());
					//更新余额
					//avlbal=BigDecimalUtils.add(avlbal, charge.getMoney());
					//acctbal=BigDecimalUtils.add(acctbal, charge.getMoney());
					//wfund=BigDecimalUtils.add(wfund, charge.getMoney());
					//wuser.setAcctBal(acctbal);
					//wuser.setFund(wfund);
					//wuser.setAvlBal(avlbal);
					//wUserService.updateUser(wuser);
					//logger.info("-----------avlbal"+avlbal);
					//插入充值记录表
					UserFund fund=new UserFund();
					fund.setMoney(charge.getMoney());
					fund.setRemark("网银充值"+charge.getMoney()+"元");
					fund.setType(1);
					fund.setNo(orderId);
					fund.setUid(userId);
					fund.setPayStatus((short)1);//已支付
					fund.setTrxId(tradeNo);
					//fund.setAmount(avlbal);
					fund.setAddtime(new Date().getTime()/1000);
					fund.setUptime(new Date().getTime()/1000);
					fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"充值"+charge.getMoney()+"元");
					userFundService.arrearsProcess(fund);
					//userFundService.save(fund);
					logger.info("-----------数据成功插入");
				}
				
			}
		}
		
	}

	@Override
	public WUser getUser(String userId) {
		return this.wUserService.getUser(userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.pay.PayService#findData(java.lang.String, java.lang.String)
	 */
	@Override
	public PageInfo<RechargeList> findData(String pageIndex, String perPage,String userId) {
		PageInfo<RechargeList> pageInfo=new PageInfo<RechargeList>(Integer.valueOf(perPage),Integer.valueOf(pageIndex)+1);
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Boolean> sortMap=new HashMap<String,Boolean>();
		map.put("EQ_uid", userId);
		sortMap.put("addtime", false);
		pageInfo=this.query(pageInfo, map, sortMap);

		return pageInfo;
	}

	/**
	 * 生成随机字符串
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	private String getRandomStr(int length){
		String orderId=StringCodeUtils.getRandomStr(length);
		RechargeList chargelist=getEntityDao().findByTradeNo(orderId);
		if(chargelist!=null){
			return getRandomStr(length);
		}
		return orderId;
	}
	
	/**
	 * 补录充值表单
	 */
	@Override
	public RechargeList saveRechargeRecord(String uid,String bankname,Integer status,
			String bankCard, Double paymoney,String ip,String paytype,String serialnum,Integer source){
		RechargeList charge=new RechargeList();
		charge.setUid(uid);
		charge.setAccount(bankCard);
		charge.setMoney(Double.valueOf(paymoney));
		charge.setActualMoney(Double.valueOf(paymoney));
		charge.setTradeNo(serialnum);
		//生成订单号
		String 	orderId=this.getRandomStr(20);
		charge.setTradeAccount(bankname);
		charge.setAddtime(new Date().getTime()/1000);
		charge.setAddip(ip);
		charge.setType(paytype);
		charge.setNo(orderId);
		charge.setStatus(status);
		charge.setSource(source);

		charge=getEntityDao().save(charge);
		return charge;
	}

	@Override
	public Map<String, Object> bbPay(WUser user, PayParamsObject sendObject,int source) {
		/**
		 * 先保存充值数据
		 */
		RechargeList charge=new RechargeList();
		charge.setUid(user.getId());
		// 实际支付金额
		double realMoney = Double.valueOf(sendObject.getPayMoney());
		charge.setMoney(realMoney);
		/**
		 *  TODO:手续费待定
		 *  double free=BigDecimalUtils.mul(realMoney,0.007);
		 *	charge.setFee(free);
		 *	realMoney=BigDecimalUtils.add(realMoney, free);
		 */
		//生成订单号
		String 	orderId=this.getRandomStr(20);
		charge.setTradeAccount(sendObject.getAbbreviation());
		charge.setAddtime(new Date().getTime()/1000);
		charge.setAddip(sendObject.getUserip());
		charge.setType(sendObject.getPayType());
		charge.setNo(orderId);
		charge.setStatus(sendObject.getStatus());
		charge.setPaymentChannel(2);
		charge.setSource(source);
		charge=getEntityDao().save(charge);

		//调用币币支付接口
		sendObject.setOrder(orderId);
		sendObject.setAmount((int)BigDecimalUtils.mulRound(realMoney,100));
		sendObject.setProductprice(sendObject.getAmount());
		sendObject.setTranstime(new Date().getTime());
		Map<String,Object> bbParams = Maps.newHashMap();
		try {
			bbParams = Bibipay.getInstance().pcpay(sendObject);
			if (CollectionUtils.isEmpty(bbParams)){
				throw new BibiPayException("bbpay.sign.filed.",null);
			}
			
			if (ObjectUtil.equals(null,bbParams.get("mobilePayUrl"))
				|| ObjectUtil.equals(null,bbParams.get("merchantaccount"))
				|| ObjectUtil.equals(null,bbParams.get("encryptkey"))
				|| ObjectUtil.equals(null,bbParams.get("data"))){
				throw new BibiPayException("bbpay.sign.filed.",null);
			}
			return bbParams;
		} catch (Exception e) {
			String details = "用户:"+user.getMobile()+"-充值金额："+sendObject.getPayMoney()+"元失败！";		
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "币币网银支付验签出错", this.getClass().getName(),details);
			logger.error("币币支付参数验签失败！"+details,e);
			throw new BibiPayException("bbpay.sign.filed.",null);
		}
	}

	@Override
	public RechargeList findByNo(String orderId) {
		return this.getEntityDao().findByNo(orderId);
	}

	@Override
	public Map<String, Object> bbPay(WUser user, PayParamsObject sendObject,
			int source, String sreturl) {
		/**
		 * 先保存充值数据
		 */
		RechargeList charge=new RechargeList();
		charge.setUid(user.getId());
		// 实际支付金额
		double realMoney = Double.valueOf(sendObject.getPayMoney());
		charge.setMoney(realMoney);
		/**
		 *  TODO:手续费待定
		 *  double free=BigDecimalUtils.mul(realMoney,0.007);
		 *	charge.setFee(free);
		 *	realMoney=BigDecimalUtils.add(realMoney, free);
		 */
		//生成订单号
		String 	orderId=this.getRandomStr(20);
		charge.setTradeAccount(sendObject.getAbbreviation());
		charge.setAddtime(new Date().getTime()/1000);
		charge.setAddip(sendObject.getUserip());
		charge.setType(sendObject.getPayType());
		charge.setNo(orderId);
		charge.setStatus(sendObject.getStatus());
		charge.setPaymentChannel(2);
		charge.setSource(source);
		charge=getEntityDao().save(charge);

		//调用币币支付接口
		sendObject.setOrder(orderId);
		sendObject.setAmount((int)BigDecimalUtils.mulRound(realMoney,100));
		sendObject.setProductprice(sendObject.getAmount());
		sendObject.setTranstime(new Date().getTime());
		Map<String,Object> bbParams = Maps.newHashMap();
		try {
			bbParams = Bibipay.getInstance().pcpay(sendObject,sreturl);
			if (CollectionUtils.isEmpty(bbParams)){
				throw new BibiPayException("bbpay.sign.filed.",null);
			}
			
			if (ObjectUtil.equals(null,bbParams.get("mobilePayUrl"))
				|| ObjectUtil.equals(null,bbParams.get("merchantaccount"))
				|| ObjectUtil.equals(null,bbParams.get("encryptkey"))
				|| ObjectUtil.equals(null,bbParams.get("data"))){
				throw new BibiPayException("bbpay.sign.filed.",null);
			}
			return bbParams;
		} catch (Exception e) {
			String details = "用户:"+user.getMobile()+"-充值金额："+sendObject.getPayMoney()+"元失败！";		
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "币币网银支付验签出错", this.getClass().getName(),details);
			logger.error("币币支付参数验签失败！"+details,e);
			throw new BibiPayException("bbpay.sign.filed.",null);
		}
	}
}
