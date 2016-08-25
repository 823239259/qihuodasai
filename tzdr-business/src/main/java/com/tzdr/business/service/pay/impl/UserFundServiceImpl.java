package com.tzdr.business.service.pay.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.dao.pay.UserFundDao;
import com.tzdr.domain.vo.BillingFeeVo;
import com.tzdr.domain.vo.HandUserFundTotalVo;
import com.tzdr.domain.vo.HandUserFundVo;
import com.tzdr.domain.vo.HandUserFundVoNew;
import com.tzdr.domain.vo.TotalMarginVo;
import com.tzdr.domain.vo.UserFundVo;
import com.tzdr.domain.vo.UserFundWebVo;
import com.tzdr.domain.vo.UserTradeArrearageVo;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
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
@Transactional(propagation=Propagation.REQUIRED)
@Service("userFundService")
public class UserFundServiceImpl extends BaseServiceImpl<UserFund,UserFundDao> implements UserFundService{
	public static final Logger logger = LoggerFactory.getLogger(UserFundServiceImpl.class);

	@Autowired
	private WUserService  wUserService;
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Override
	public List<UserFund> findByUidAndPayStatus(String uid, short payStatus,String tradeId) {
		return getEntityDao().findByUidAndPayStatusAndNoOrderByMoneyAsc(uid, payStatus,tradeId);
	}


	@Override
	public void deductionMoney(UserFund userFund,String userId) {
		
		WUser  dbWuser = wUserService.getUser(userId);
		if (userFund.getPayStatus()==0){
			userFund.setAmount(dbWuser.getAvlBal());
			getEntityDao().save(userFund);
			logger.debug("扣除 {}的{}成功，但未支付。",dbWuser.getUname(),CacheManager.getDataMapByKey(DataDicKeyConstants.USER_FUND_TYPE, String.valueOf(userFund.getType())));
			return ;
		}
		
		logger.info("系统扣除当日用户费用：用户手机号【"+dbWuser.getMobile()+"】,扣费前余额【"+dbWuser.getAvlBal()+"】");

		
		dbWuser.setAvlBal(BigDecimalUtils.addRound(dbWuser.getAvlBal(),userFund.getMoney()));
		//dbWuser.setFund(BigDecimalUtils.addRound(dbWuser.getAvlBal(),dbWuser.getFrzBal()));
		wUserService.updateUser(dbWuser);
		userFund.setAmount(dbWuser.getAvlBal());
		userFund.setUptime(Dates.getCurrentLongDate());
		getEntityDao().save(userFund);
		logger.debug("扣除 {}的{}成功，已支付。",dbWuser.getUname(),CacheManager.getDataMapByKey(DataDicKeyConstants.USER_FUND_TYPE, String.valueOf(userFund.getType())));

		logger.info("系统扣除当日用户费用：用户手机号【"+dbWuser.getMobile()+"】,扣除金额【"+userFund.getMoney()+"】，扣费之后余额【"+dbWuser.getAvlBal()+"】");

	}


	@Override
	public List<UserFund> queryGroupArrears(String uid,String groupid, short payStatus,
			Long currentDate) {
		return getEntityDao().findByUidAndPayStatusAndLidAndMoneyLessThanAndAddtimeLessThanOrderByAddtimeAsc(uid,payStatus, groupid ,NumberUtils.toDouble("0"), currentDate);
	}


	@Override
	public List<UserFund> findByPayStatus(short payStatus) {
		return getEntityDao().findByPayStatusAndMoneyLessThanOrderByMoneyAsc(payStatus,NumberUtils.toDouble("0"));
	}


	@Override
	public List<UserFundVo> findTodayArrears() {
		String sql = "SELECT uid,tradeIds FROM ( SELECT uid, MIN(addtime) addtime, GROUP_CONCAT(`no`) tradeIds FROM w_user_fund WHERE money < 0 AND pay_status = 0 GROUP BY uid ) temp WHERE addtime BETWEEN ? AND ?";
		List<Object> params = Lists.newArrayList();
		params.add(Dates.getCurrentLongDay());
		params.add(Dates.getNextLongDay());
		List<UserFundVo> fundVos = nativeQuery(sql, params, UserFundVo.class);
		return fundVos;
	}


	@Override
	public UserFund findUserfundByNo(String no, String userId) {
	
		return getEntityDao().findByNoAndUid(no,userId);
	}
	
	@Override
	public List<UserFundVo> getYestodayFee() {
		String sql = "SELECT uid,MIN(uptime) upTime,SUM(money) money FROM w_user_fund WHERE pay_status = 1 AND type = 12 AND uptime BETWEEN ? AND ? GROUP BY uid";
		List<Object> params = Lists.newArrayList();
		params.add(Dates.getYestodayZeroLong());
		params.add(Dates.getCurrentLongDay());
		List<UserFundVo> fundVos = nativeQuery(sql, params, UserFundVo.class);
		return fundVos;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.pay.UserFundService#findData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PageInfo<UserFund> findData(String pageIndex, String perPage,
			String userId,String type,Long mintime,Long maxtime) {
		PageInfo<UserFund> pageInfo=new PageInfo<UserFund>(Integer.valueOf(perPage),Integer.valueOf(pageIndex)+1);
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Boolean> sortmap=new HashMap<String,Boolean>();
		sortmap.put("addtime", false);
		map.put("EQ_uid", userId);
		//map.put("EQ_payStatus", 1);
		if(mintime>0){
			map.put("GTE_addtime", mintime);
		}
		if(maxtime>0){
			map.put("LTE_addtime", maxtime);
		}
		
		if(StringUtil.isNotBlank(type)){
			List<Integer> arrayList=new ArrayList<Integer>();
			String[] types=type.split(",");
			for(String str:types){
				arrayList.add(Integer.valueOf(str));
			}
			map.put("IN_type", arrayList);
		}
		pageInfo=this.query(pageInfo, map,sortmap);
		return pageInfo;
	}
	
	@Override
	public List<UserFund> findByUidAndLidAndTypeIn(String userId,String groupId,List<Integer> type){
		return getEntityDao().findByUidAndLidAndTypeIn(userId, groupId, type);
	}
	
	@Override
	public Double sumMoneyByUidAndLidAndTypeIn(String userId,String groupId,List<Integer> type){
		List<Object> params=Lists.newArrayList();
		params.add(userId);
		params.add(groupId);
		params.addAll(type);
		Double money=	(Double)nativeQueryOne("select sum(money) from w_user_fund where uid=? and lid=? and type in "+getQueryInSql(type.size()),params);
	return money==null?0:money;
	}

	
	@Override
	public Double sumMoneyByLidAndType(String groupId,Integer type){
		List<Object> params=Lists.newArrayList();
		params.add(groupId);
		params.add(type);
		Double money=	(Double)nativeQueryOne("select sum(money) from w_user_fund where lid=? and type=?",params);
		return money==null?0:money;
	}
	
	@Override
	public List<BillingFeeVo> totalMoneyByGroupId(String groupId) {
		String sql = " SELECT w.lid groupId,w.type,sum(w.money) money "
				   + " FROM w_user_fund w WHERE w.lid = ? GROUP BY w.type";
		return this.getEntityDao().queryBySql(sql, BillingFeeVo.class, groupId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.pay.UserFundService#getFundbytype(java.lang.String)
	 */
	@Override
	public List<UserFundVo> getFundbytype(String type,String userId) {
		String sql = "SELECT count(*) as count, SUM(abs(money)) summoney FROM w_user_fund WHERE  uid='"+userId+"' AND type in("+type+")  ";
		List<Object> params = Lists.newArrayList();
		List<UserFundVo> fundVos = nativeQuery(sql, params, UserFundVo.class);
		return fundVos;
	}
	
	/**
	 * 存操作
	 * @param userFund UserFund
	 */
	public void depositOperation(UserFund userFund) {
		String uid = userFund.getUid();
		WUser wuser = this.wUserService.get(uid);
		//取绝对值
		Double money = Math.abs(userFund.getMoney());
		//存
		wuser.setAvlBal(BigDecimalUtils.addRound(wuser.getAvlBal(), money));
		
		//资产总值 = 可用余额 + 冻结金额
		wuser.setFund(BigDecimalUtils.addRound(wuser.getAvlBal(), wuser.getFrzBal()));
		
		//佣金收入的情况
		if (TypeConvert.USER_FUND_COMMISSION_IN == userFund.getType()) {
			wuser.setTotalCommission(BigDecimalUtils.addRound(
					wuser.getTotalCommission(),userFund.getMoney()) )  ;
		}
		
		this.wUserService.update(wuser);
		//添加时间
		userFund.setAddtime(TypeConvert.dbDefaultDate());
		//更新时间
		userFund.setUptime(TypeConvert.dbDefaultDate());
		//冻结金额
		userFund.setFreeze(wuser.getFrzBal() == null?0D:wuser.getFrzBal());
		userFund.setFreeze(BigDecimalUtils.round1(userFund.getFreeze(), 2));
		//当前余额
		userFund.setAmount(wuser.getAvlBal() == null?0D:wuser.getAvlBal());
		userFund.setAmount(BigDecimalUtils.round1(userFund.getAmount(), 2));
		//用户ID
		userFund.setUid(wuser.getId());
		if (userFund.getId() == null) {
			this.save(userFund);
		}
		else {
			this.update(userFund);
		}
	   arrearsProcess(userFund);
	}
	
	/**
	 * 
	 * 存取类型 1） 存、 2）取、3）冻结
	 */
	/*
	public void rechargeOperation(WUser wuser, UserFund userFund,int takeDepositType) {
		//取绝对值
		Double money = Math.abs(userFund.getMoney());
		//存
		if (takeDepositType == 1) {
			wuser.setAvlBal(BigDecimalUtils.addRound(wuser.getAvlBal(), money));
		}
		else if (takeDepositType == 2) {
		//取	
			wuser.setAvlBal(BigDecimalUtils.subRound(wuser.getAvlBal(), money));
		}
		else if (takeDepositType == 3) {
		//冻结
			wuser.setAvlBal(BigDecimalUtils.subRound(wuser.getAvlBal(), money));
			wuser.setFrzBal(BigDecimalUtils.addRound(wuser.getFrzBal(), money));
		}
		//资产总值 = 可用余额 + 冻结金额
		wuser.setFund(BigDecimalUtils.addRound(wuser.getAvlBal(), wuser.getFrzBal()));
		this.wUserService.update(wuser);
		//添加时间
		userFund.setAddtime(TypeConvert.dbDefaultDate());
		//更新时间
		userFund.setUptime(TypeConvert.dbDefaultDate());
		//冻结金额
		userFund.setFreeze(wuser.getFrzBal() == null?0D:wuser.getFrzBal());
		//当前余额
		userFund.setAmount(wuser.getAvlBal() == null?0D:wuser.getAvlBal());
		//组ID
		userFund.setLid(wuser.getGroupid());
		//用户ID
		userFund.setUid(wuser.getId());
		if (userFund.getId() == null) {
			this.save(userFund);
		}
		else {
			this.update(userFund);
		}
		
	}*/
	
	
	/**
	 * 用户充值
	 * 当用户存在"爆仓" 的情况时，需要扣除欠费
	 * @param arrearsUserFund UserFund
	 * @return void
	 */
	public void arrearsProcess(UserFund arrearsUserFund) {
		if (arrearsUserFund == null) {
			return;
		}
		String userId = arrearsUserFund.getUid();
		if (userId == null || "".equals(userId)) {
			return;
		}
		if (arrearsUserFund == null || arrearsUserFund.getMoney() == 0) {
			return;
		}
		String groupId = arrearsUserFund.getLid();
		//注意（欠费计算前取绝对值）
		WUser wuser = this.wUserService.get(userId);
		//可用余额
		Double avlBal = wuser.getAvlBal();
		//1)查询出UserFund 配置配资欠费记录
		//2)用欠费金额从大到小排序【因欠费存的数据都为负数-34、-50、-80】未支付
		arrearsUserFund.setUid(wuser.getId());
		this.rechargeOperation(arrearsUserFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
		List<UserFund> unPaids = 
				this.getEntityDao().findByUidAndPayStatusAndTypeOrderByMoneyDesc(userId,TypeConvert.UN_PAID, 18);
//		if (unPaids == null || unPaids.size() == 0) {
//			return;
//		}
		
		//充值金额
		Double rechangeMoney = arrearsUserFund.getMoney();
		BigDecimal rechangeMoneyBig = new BigDecimal(rechangeMoney.toString());
		//1)判断是否有余额【没有时】
		//avlBal < 0
		if (new BigDecimal(avlBal.toString()).compareTo(new BigDecimal("0")) < 0) {
			if(unPaids != null && !unPaids.isEmpty()){    //扣除欠费
				//无余额的情况下
				for (UserFund userFund:unPaids) {
					//欠费金额
					double arrearsMoney = Math.abs(userFund.getMoney());
					BigDecimal arrearsMoneyBig = new BigDecimal(String.valueOf(arrearsMoney));
					if (arrearsMoneyBig.equals(rechangeMoneyBig)) {
						userFund.setPayStatus(TypeConvert.PAID);
						userFund.setUptime(TypeConvert.dbDefaultDate()+3);
						userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
						this.update(userFund);
						break;
					}
					else if (arrearsMoneyBig.compareTo(rechangeMoneyBig) < 0) {
						//arrearsMoney < rechangeMoney
						//rechangeMoney -= arrearsMoney;
						rechangeMoneyBig = rechangeMoneyBig.subtract(arrearsMoneyBig, MathContext.DECIMAL32);
						userFund.setPayStatus(TypeConvert.PAID);
						userFund.setUptime(TypeConvert.dbDefaultDate()+3);
						userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
						this.update(userFund);
		        	}
		        	else {
		        		//rechangeMoney > 0
		        		if (rechangeMoneyBig.compareTo(new BigDecimal("0")) > 0) {
		        			//arrearsMoney -= rechangeMoney;
		        			arrearsMoneyBig = arrearsMoneyBig.subtract(rechangeMoneyBig, MathContext.DECIMAL32);
		        			//userFund.setMoney(rechangeMoney);
		        			userFund.setUptime(TypeConvert.dbDefaultDate()+3);
		        			userFund.setPayStatus(TypeConvert.PAID);
		        			userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
		        			this.update(userFund);
		        			
		        			rechangeMoneyBig = new BigDecimal(-TypeConvert.scale(arrearsMoneyBig, 2).doubleValue());
		        			//充值欠费
							UserFund addUserFund = new UserFund();
							addUserFund.setType(userFund.getType());
			        		addUserFund.setMoney(-TypeConvert.scale(arrearsMoneyBig, 2).doubleValue());
			        		addUserFund.setLid(userFund.getLid());
			        		addUserFund.setRid(userFund.getRid());
			        		addUserFund.setAddtime(TypeConvert.dbDefaultDate()+4);
			        		addUserFund.setUptime(TypeConvert.dbDefaultDate()+4);
			        		addUserFund.setAmount(addUserFund.getMoney());
			        		addUserFund.setRemark(TypeConvert.payRemark("配资欠费", addUserFund.getMoney()));
			        		addUserFund.setPayStatus(TypeConvert.UN_PAID);
			        		addUserFund.setTypeStatus(1);
			        		addUserFund.setUid(wuser.getId());
			        		addUserFund.setSysUserId(arrearsUserFund.getSysUserId());
			        		this.save(addUserFund);
			        		//取出余额支付欠费
		        		}
		        		break;
		        	}
				}
			}
			/*else {
				//当有余额存在时进行扣费
				//haveAvlBalance(unPaids, avlBal, wuser);
				unPaids = 
						this.getEntityDao().findByUidAndPayStatusAndTypeOrderByMoneyDesc(userId,TypeConvert.UN_PAID, 18);
				for (UserFund userFund:unPaids) {
					userFund.setPayStatus(TypeConvert.PAID);
					this.update(userFund);
				}
				//无余额情况下
				//notHaveAvlBalance(unPaids,rechangeMoney);
			}*/
			
			//未支付补仓记录
			List<UserFund> unPaidCovers = 
					this.getEntityDao().findByUidAndPayStatusAndTypeOrderByMoneyDesc(userId,TypeConvert.UN_PAID, 27);
			
			if(rechangeMoneyBig.doubleValue() > 0 && unPaidCovers != null && !unPaidCovers.isEmpty()){  //未支付补仓记录清算
				//无余额的情况下
				for (UserFund userFund:unPaidCovers) {
					//欠费金额
					double arrearsMoney = Math.abs(userFund.getMoney());
					BigDecimal arrearsMoneyBig = new BigDecimal(String.valueOf(arrearsMoney));
					if (arrearsMoneyBig.equals(rechangeMoneyBig)) {
						userFund.setPayStatus(TypeConvert.PAID);
						userFund.setUptime(TypeConvert.dbDefaultDate()+3);
						this.update(userFund);
						break;
					}
					else if (arrearsMoneyBig.compareTo(rechangeMoneyBig) < 0) {
						//arrearsMoney < rechangeMoney
						//rechangeMoney -= arrearsMoney;
						rechangeMoneyBig = rechangeMoneyBig.subtract(arrearsMoneyBig, MathContext.DECIMAL32);
						userFund.setPayStatus(TypeConvert.PAID);
						userFund.setUptime(TypeConvert.dbDefaultDate()+3);
						this.update(userFund);
		        	}
		        	else {
		        		//rechangeMoney > 0
		        		if (rechangeMoneyBig.compareTo(new BigDecimal("0")) > 0) {
		        			//arrearsMoney -= rechangeMoney;
		        			arrearsMoneyBig = arrearsMoneyBig.subtract(rechangeMoneyBig, MathContext.DECIMAL32);
		        			//userFund.setMoney(rechangeMoney);
		        			userFund.setUptime(TypeConvert.dbDefaultDate()+3);
		        			userFund.setPayStatus(TypeConvert.PAID);
		        			this.update(userFund);
		        			
		        			rechangeMoneyBig = new BigDecimal(-TypeConvert.scale(arrearsMoneyBig, 2).doubleValue());
		        			//充值欠费
							UserFund addUserFund = new UserFund();
							addUserFund.setType(userFund.getType());
			        		addUserFund.setMoney(-TypeConvert.scale(arrearsMoneyBig, 2).doubleValue());
			        		addUserFund.setLid(userFund.getLid());
			        		addUserFund.setRid(userFund.getRid());
			        		addUserFund.setNo(userFund.getNo());
			        		addUserFund.setAddtime(TypeConvert.dbDefaultDate()+4);
			        		addUserFund.setUptime(TypeConvert.dbDefaultDate()+4);
			        		addUserFund.setAmount(addUserFund.getMoney());
			        		addUserFund.setPayStatus(TypeConvert.UN_PAID);
			        		addUserFund.setUid(wuser.getId());
			        		addUserFund.setTypeStatus(1);
			        		addUserFund.setRemark(TypeConvert.payRemark("补仓欠费", addUserFund.getMoney()));
			        		addUserFund.setSysUserId(arrearsUserFund.getSysUserId());
			        		this.save(addUserFund);
			        		//取出余额支付欠费
		        		}
		        		break;
		        	}
				}
			}
		}
	}
	
	/**
	 * 余额不大于0时情况下
	 * @param unPaids List<UserFund>
	 * @param rechangeMoney Double
	 */
	private void notHaveAvlBalance(List<UserFund> unPaids,Double rechangeMoney ) {
		
		//无余额的情况下
		for (UserFund userFund:unPaids) {
			//欠费金额
			double arrearsMoney = Math.abs(userFund.getMoney());
			BigDecimal arrearsMoneyBig = new BigDecimal(arrearsMoney);
			BigDecimal rechangeMoneyBig = new BigDecimal(rechangeMoney);
			if (arrearsMoneyBig.compareTo(rechangeMoneyBig) == 0) {
				userFund.setPayStatus(TypeConvert.PAID);
				userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
				break;
			}
			else if (arrearsMoneyBig.compareTo(rechangeMoneyBig) < 0) {
				//arrearsMoney < rechangeMoney
				//rechangeMoney -= arrearsMoney;
				rechangeMoneyBig = rechangeMoneyBig.subtract(arrearsMoneyBig);
				userFund.setPayStatus(TypeConvert.PAID);
				userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
        	}
        	else {
        		if (rechangeMoneyBig.compareTo(new BigDecimal(0)) > 0) {
        			//rechangeMoney > 0
        			//arrearsMoney -= rechangeMoney;
        			arrearsMoneyBig = TypeConvert.scale(arrearsMoneyBig.subtract(rechangeMoneyBig),2);
        			userFund.setMoney(rechangeMoney);
        			userFund.setPayStatus(TypeConvert.PAID);
        			userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
        			//充值欠费
					UserFund addUserFund = new UserFund();
					addUserFund.setType(userFund.getType());
	        		addUserFund.setMoney(-arrearsMoneyBig.doubleValue());
	        		addUserFund.setRemark(TypeConvert.payRemark("配资欠费", addUserFund.getMoney()));
	        		addUserFund.setPayStatus(TypeConvert.UN_PAID);
	        		//取出余额支付欠费
        		}
        		break;
        	}
		}
		
		
	}
	
	/**
	 * 当余额存在时进行扣费
	 * @param unPaids List<UserFund>
	 * @param avlBal Double
	 * @param wuser WUser
	 */
	private void haveAvlBalance(List<UserFund> unPaids,Double avlBal,WUser wuser) {
		BigDecimal avlBalBig = new BigDecimal(avlBal);
		//有余额的情况下
		for (UserFund userFund:unPaids) {
			//欠费金额
			double arrearsMoney = Math.abs(userFund.getMoney());
			BigDecimal arrearsMoneyBig = new BigDecimal(arrearsMoney);
			if (avlBalBig.compareTo(arrearsMoneyBig) == 0) {
				userFund.setPayStatus(TypeConvert.PAID);
				userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
				//取出余额支付
				userFund.setUid(wuser.getId());
				this.rechargeOperation(userFund,TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
				break;
			}
			else if (avlBalBig.compareTo(arrearsMoneyBig) > 0) {
				//avlBal > arrearsMoney
				//avlBal -= arrearsMoney;
				avlBalBig = avlBalBig.subtract(arrearsMoneyBig);
				userFund.setPayStatus(TypeConvert.PAID);
				userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
            	//取出余额 ==支付==>爆仓欠费
				userFund.setUid(wuser.getId());
            	this.rechargeOperation(userFund,TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
        	}
        	else {
        		//余额不足的情况
        		if (avlBalBig.compareTo(new BigDecimal(0)) > 0) {
        			//avlBal > 0
        			//arrearsMoney -= avlBal;
        			arrearsMoneyBig = arrearsMoneyBig.subtract(avlBalBig);
        			userFund.setMoney(TypeConvert.scale(avlBalBig,2).doubleValue());
        			userFund.setPayStatus(TypeConvert.PAID);
        			userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
        			userFund.setUid(wuser.getId());
        			//取出余额支付欠费
					this.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
					UserFund addUserFund = new UserFund();
					addUserFund.setType(userFund.getType());
	        		addUserFund.setMoney(-TypeConvert.scale(arrearsMoneyBig,2).doubleValue());
	        		addUserFund.setRemark(TypeConvert.payRemark("配资欠费", addUserFund.getMoney()));
	        		addUserFund.setPayStatus(TypeConvert.UN_PAID);
	        		userFund.setUid(wuser.getId());
	        		//取出余额支付欠费
	        		this.rechargeOperation(addUserFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
        		}
        		break;
        	}
		}
		
		
	}
	
	/**
	 * 
	 * 存取类型 1） 存、 2）取、3）冻结
	 */
	@Override
	public void rechargeOperation(UserFund userFund,int takeDepositType) {
		WUser wuser = this.wUserService.get(userFund.getUid());
		//取绝对值
		Double money = Math.abs(userFund.getMoney());
		//存
		if (takeDepositType == 1) {
			userFund.setPayStatus(TypeConvert.PAID);
			if (userFund.getType() != null &&  userFund.getType()== TypeConvert.TAKE_COMMISSION_INCOME) {
				//totalCommission
				wuser.setTotalCommission(BigDecimalUtils.add2(
						wuser.getTotalCommission(),userFund.getMoney()));
			}
			
			wuser.setAvlBal(BigDecimalUtils.addRound(wuser.getAvlBal(), money));
		}
		else if (takeDepositType == 2) {
		//取	
			userFund.setPayStatus(userFund.isUnpayFlag()?userFund.getPayStatus():TypeConvert.PAID);
			userFund.setMoney(-Math.abs(userFund.getMoney()));
			wuser.setAvlBal(BigDecimalUtils.subRound(wuser.getAvlBal(), money));
		}
		else if (takeDepositType == 3) {
		//冻结
			wuser.setAvlBal(BigDecimalUtils.subRound(wuser.getAvlBal(), money));
			wuser.setFrzBal(BigDecimalUtils.addRound(wuser.getFrzBal(), money));
		}
		//资产总值 = 可用余额 + 冻结金额
		//wuser.setFund(BigDecimalUtils.addRound(wuser.getAvlBal(), wuser.getFrzBal()));
		this.wUserService.update(wuser);
		//添加时间
		userFund.setAddtime(userFund.getAddtime()!=null?userFund.getAddtime()+2:TypeConvert.dbDefaultDate()+2);
		//更新时间
		userFund.setUptime(userFund.getUptime()!=null?userFund.getUptime()+2:TypeConvert.dbDefaultDate()+2);
		//冻结金额
		userFund.setFreeze(wuser.getFrzBal() == null?0D:wuser.getFrzBal());
		//当前余额
		userFund.setAmount(wuser.getAvlBal() == null?0D:wuser.getAvlBal());
		//组ID
		//userFund.setLid(wuser.getGroupid());
		//用户ID
		userFund.setUid(wuser.getId());
		if (userFund.getId() == null) {
			this.save(userFund);
		}
		else {
			this.update(userFund);
		}
	}


	@Override
	public boolean isDeductionTodayFee(String tradeId) {
		List<UserFund> list  = 
				getEntityDao().findByNoAndTypeAndAddtimeGreaterThan(tradeId,12,Dates.getCurrentLongDay());
		if (CollectionUtils.isEmpty(list)){
			return false;
		}
		return true;
	}

	

	@Override
	public PageInfo<UserFundWebVo> queryUserFundWebVoByConn(PageInfo<UserFundWebVo> page,
			ConnditionVo conn) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlBuf = new StringBuffer(
				  " SELECT m_temp.* FROM (SELECT u.addtime,"
				+ " (SELECT w.mobile FROM w_user w WHERE w.id=u.uid)mobile,"
				+ " (SELECT v.tname FROM w_user_verified v WHERE v.uid=u.uid)tname,"
				+ " u.money,u.type,u.remark,u.uid "
				+ " FROM w_user_fund u WHERE 1=1 AND u.type=13) m_temp WHERE 1=1 ");
		
		StringBuffer sumBuf = new StringBuffer(
				  " SELECT SUM(m_temp.money) totalAmount FROM (SELECT u.addtime,"
				+ " (SELECT w.mobile FROM w_user w WHERE w.id=u.uid)mobile,"
				+ " (SELECT v.tname FROM w_user_verified v WHERE v.uid=u.uid)tname,"
				+ " u.money,u.type,u.remark,u.uid "
				+ " FROM w_user_fund u WHERE 1=1 AND u.type=13) m_temp WHERE 1=1 AND m_temp.money > 0");
		
		if (conn != null) {
			String mobile = TypeConvert.objToStrIsNotNull(conn.getValue("mobile"));
			if (mobile != null) {
				sqlBuf.append(" AND m_temp.mobile like ?");
				sumBuf.append(" AND m_temp.mobile like ?");
				params.add("%"+ mobile);
			}
			if (page.getCurrentPage() <= 0) {
				page.setCurrentPage(1);
			}
			String uptimeStr_start = TypeConvert.objToStrIsNotNull(conn.getValue("addtimeStr_start"));
			String uptimeStr_end = TypeConvert.objToStrIsNotNull(conn.getValue("addtimeStr_end"));
			if (TypeConvert.objToStrIsNotNull(uptimeStr_start) != null 
					&& TypeConvert.objToStrIsNotNull(uptimeStr_end) != null) {
				sqlBuf.append(" AND m_temp.addtime BETWEEN ? AND ?");
				sumBuf.append(" AND m_temp.addtime BETWEEN ? AND ?");
				params.add(TypeConvert.strToZeroDate1000(uptimeStr_start,0));
				params.add(TypeConvert.strToZeroDate1000(uptimeStr_end,1,-1));
			}
		}
		sqlBuf.append(" AND m_temp.uid IN (SELECT n.id FROM w_user n WHERE n.id = ? OR n.parent_id = ?)");
		sumBuf.append(" AND m_temp.uid IN (SELECT n.id FROM w_user n WHERE n.id = ? OR n.parent_id = ?)");
		
		params.add(conn.getValueStr("tzdrUser"));
		params.add(conn.getValueStr("tzdrUser"));
		sqlBuf.append(" ORDER BY m_temp.addtime DESC ");
		
		List<TotalMarginVo> margins = 
				this.getEntityDao().queryBySql(sumBuf.toString(), TotalMarginVo.class, conn, params.toArray());
		TotalMarginVo totalVo = margins.get(0);
		Double totalAmount = totalVo.getTotalAmount();
		
		PageInfo<UserFundWebVo> pageInfo = this.getEntityDao().queryPageBySql(page, sqlBuf.toString(), 
				UserFundWebVo.class, conn,params.toArray());
		if (pageInfo != null) {
			List<UserFundWebVo> data =  pageInfo.getPageResults();
			if (data != null) {
				for(UserFundWebVo webVo:data) {
					webVo.setTotalAmount(totalAmount.toString());
				}
			}
		}
		
		return pageInfo;
	}


	@Override
	public PageInfo<HandUserFundVo> queryHandlerRecharge(PageInfo<HandUserFundVo> page,ConnditionVo conn) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlBuf = new StringBuffer(
				  "  SELECT w.id,v.tname,u.mobile,w.uid,w.`no`,w.type,w.money,w.amount,"
				+" w.freeze,w.lid,w.trx_id trxId,w.rid,w.ruid,w.addtime,w.uptime,w.remark,s.realname"
				+" FROM w_user_fund w  LEFT JOIN sys_user s on w.sys_user_id=s.id   LEFT JOIN w_user u ON w.uid = u.id LEFT JOIN w_user_verified v ON w.uid=v.uid" 
				+" WHERE  1=1 AND(w.type = 3 OR w.type = 4)");
		if (conn != null) {
			String mobile = TypeConvert.objToStrIsNotNull(conn.getValue("mobile"));
			if (mobile != null) {
				sqlBuf.append(" AND u.mobile like ?");
				params.add(mobile + "%");
			}
			String typeValueStr = TypeConvert.objToStrIsNotNull(conn.getValue("type"));
			if (typeValueStr != null && TypeConvert.isDigital(typeValueStr)) {
				int type = Integer.parseInt(typeValueStr);
				sqlBuf.append(" AND w.type = ?");
				params.add(type);
			}
			String uptimeStr_start = TypeConvert.objToStrIsNotNull(conn.getValue("uptimeStr_start"));
			String uptimeStr_end = TypeConvert.objToStrIsNotNull(conn.getValue("uptimeStr_end"));
			if (TypeConvert.objToStrIsNotNull(uptimeStr_start) != null 
					&& TypeConvert.objToStrIsNotNull(uptimeStr_end) != null) {
				sqlBuf.append(" AND uptime BETWEEN ? AND ?");
				params.add(TypeConvert.strToDatetime1000Long(uptimeStr_start));
				params.add(TypeConvert.strToDatetime1000Long(uptimeStr_end));
			}
			
		}
		
		sqlBuf.append(" ORDER BY w.uptime DESC ");
		return this.getEntityDao().queryDataPageBySql(page, sqlBuf.toString(), 
				HandUserFundVo.class, params.toArray());
	}
	
	@Override
	public HandUserFundTotalVo queryHandlerRechargeTotal(ConnditionVo conn) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlBuf = new StringBuffer(
				  " SELECT SUM(w.money) money "
				+ " FROM w_user_fund w LEFT JOIN w_user u ON w.uid = u.id "
				+ " WHERE 1=1 AND(w.type = 3 OR w.type = 4) ");
		if (conn != null) {
			String mobile = TypeConvert.objToStrIsNotNull(conn.getValue("mobile"));
			if (mobile != null) {
				sqlBuf.append(" AND u.mobile like ?");
				params.add(mobile + "%");
			}
			String typeValueStr = TypeConvert.objToStrIsNotNull(conn.getValue("type"));
			if (typeValueStr != null && TypeConvert.isDigital(typeValueStr)) {
				int type = Integer.parseInt(typeValueStr);
				sqlBuf.append(" AND w.type = ?");
				params.add(type);
			}
			String uptimeStr_start = TypeConvert.objToStrIsNotNull(conn.getValue("uptimeStr_start"));
			String uptimeStr_end = TypeConvert.objToStrIsNotNull(conn.getValue("uptimeStr_end"));
			if (TypeConvert.objToStrIsNotNull(uptimeStr_start) != null 
					&& TypeConvert.objToStrIsNotNull(uptimeStr_end) != null) {
				sqlBuf.append(" AND uptime BETWEEN ? AND ?");
				params.add(TypeConvert.strToDatetime1000Long(uptimeStr_start));
				params.add(TypeConvert.strToDatetime1000Long(uptimeStr_end));
			}
			
		}
		
		sqlBuf.append(" ORDER BY w.uptime DESC ");
		List<HandUserFundTotalVo> totalVoes = 
				this.getEntityDao().queryBySql(sqlBuf.toString(),HandUserFundTotalVo.class,conn, params.toArray());
		if (totalVoes != null && totalVoes.size() > 0) {
			return totalVoes.get(0);
		}
		return null;
	}
	
	@Override
	public PageInfo<HandUserFundVoNew> queryUserFundVoList(PageInfo<HandUserFundVoNew> page,ConnditionVo conn) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlBuf = new StringBuffer(
				  " SELECT w.id,u.mobile,w.uid,w.`no`,w.type,w.money,w.amount,"
				+ " w.freeze,w.lid,w.trx_id trxId,w.rid,w.ruid,w.addtime,w.uptime,w.remark,w.pay_status payStatus "
				+ " FROM w_user_fund w LEFT JOIN w_user u ON w.uid = u.id "
				+ " WHERE 1=1 ");
		if (conn != null) {
			String mobile = TypeConvert.objToStrIsNotNull(conn.getValue("mobile"));
			if (mobile != null) {
				sqlBuf.append(" AND u.mobile like ?");
				params.add(mobile + "%");
			}
			String typeValueStr = TypeConvert.objToStrIsNotNull(conn.getValue("type"));
			if (typeValueStr != null && TypeConvert.isDigital(typeValueStr)) {
				int type = Integer.parseInt(typeValueStr);
				sqlBuf.append(" AND w.type = ?");
				params.add(type);
			}
			String uptimeStr_start = TypeConvert.objToStrIsNotNull(conn.getValue("uptimeStr_start"));
			String uptimeStr_end = TypeConvert.objToStrIsNotNull(conn.getValue("uptimeStr_end"));
			if (TypeConvert.objToStrIsNotNull(uptimeStr_start) != null 
					&& TypeConvert.objToStrIsNotNull(uptimeStr_end) != null) {
				sqlBuf.append(" AND uptime BETWEEN ? AND ?");
				params.add(TypeConvert.strToDatetime1000Long(uptimeStr_start));
				params.add(TypeConvert.strToDatetime1000Long(uptimeStr_end));
			}
			String uid = TypeConvert.objToStrIsNotNull(conn.getValue("uid"));
			if (uid != null) {
				sqlBuf.append(" AND u.id = ?");
				params.add(uid);
			}
			
		}
		
		//增加默认排序
		if(StringUtil.isBlank(conn.getSortFieldName())) {
			conn.addParam("sort", "addtimeStr");
			conn.addParam("order", "DESC");
		}
		//sqlBuf.append(" ORDER BY w.uptime DESC ");
		return this.getEntityDao().queryPageBySql(page, sqlBuf.toString(), 
				HandUserFundVoNew.class, conn,params.toArray());
	}
	
	@Override
	public List<UserFundVo> getTitleData(Long starttime, Long endtime,
			String type, String userId) {
		String sql = "select * from ( (SELECT count(*) as count, "
				+ " (SELECT min(v.tname) FROM w_user_verified v WHERE v.uid = uid) tname, "
				+ " (SELECT min(w.mobile) FROM w_user w WHERE w.id = uid) mobile,"
				+ " SUM(abs(money)) summoney FROM w_user_fund WHERE 1=1 "
				+ " and uid=? ";
		if(StringUtil.isNotBlank(type)){
			sql+=" AND type in("+type+")  ";
		}
		if(starttime>0){
			sql +=" and addtime>=?";
		}
		if(endtime>0){
			sql +=" and addtime<=?";
		}
		sql +=" and money>0) t, ";
		
		sql += "(SELECT count(*) as outcount, SUM(abs(money)) outsummoney FROM w_user_fund WHERE 1=1 "
				+ " and uid=? ";
		if(StringUtil.isNotBlank(type)){
			sql+=" AND type in("+type+")  ";
		}
		if(starttime>0){
			sql +=" and addtime>=?";
		}
		if(endtime>0){
			sql +=" and addtime<=?";
		}
		sql +=" and money<0 ) s )";
		List<Object> params = Lists.newArrayList();
		params.add(userId);
		if(starttime>0){
			params.add(starttime);
		}
		if(endtime>0){
			params.add(endtime);
		}
		params.add(userId);
		if(starttime>0){
			params.add(starttime);
		}
		if(endtime>0){
			params.add(endtime);
		}
		
		List<UserFundVo> fundVos = nativeQuery(sql, params, UserFundVo.class);
		return fundVos;
	}


	@Override
	public void deleteArrearsByLid(String lid) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("lid", lid);
		equals.put("type", 11);
		equals.put("payStatus", TypeConvert.UN_PAID);
		List<UserFund> list = this.getEntityDao().queryBySimple(equals, null, null);
		if (list != null) {
			for (UserFund userFund:list) {
				this.remove(userFund);
			}
		}
		Map<String,Object> equals2 = new HashMap<String,Object>();
		equals2.put("lid", lid);
		equals2.put("type", 12);
		equals2.put("payStatus", TypeConvert.UN_PAID);
		List<UserFund> list2 = this.getEntityDao().queryBySimple(equals2, null, null);
		if (list2 != null) {
			for (UserFund userFund:list2) {
				this.remove(userFund);
			}
		}
		
		
	}


	@Override
	public List<UserFund> getByLidAndType(String lid, Integer type) {
		return getEntityDao().findByLidAndType(lid, type);
	}

	@Override
	public List<UserFund> findByLidAndTypeOrderByAddtimeDesc(String lid,
			Integer type) {
		return getEntityDao().findByLidAndTypeOrderByAddtimeDesc(lid, type);
	}

	@Override
	public List<UserFund> getVolumeDetail(String groupId, String uid, int tickettype) {
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Boolean> sortmap=new HashMap<String,Boolean>();
		map.put("EQ_uid", uid);
		map.put("EQ_lid", groupId);
		map.put("EQ_type", tickettype);
		//map.put("EQ_payStatus", 1);
		return this.query(map, sortmap);
		
	}


	/**
	 * 
	 * 存取类型 1） 存、 2）取、3）冻结
	 */
	//@Override
	/*public void rechargeOperation(WUser wuser, UserFund userFund,int takeDepositType) {
		//取绝对值
		Double money = Math.abs(userFund.getMoney());
		//存
		if (takeDepositType == 1) {
			wuser.setAvlBal(BigDecimalUtils.addRound(wuser.getAvlBal(), money));
		}
		else if (takeDepositType == 2) {
		//取	
			wuser.setAvlBal(BigDecimalUtils.subRound(wuser.getAvlBal(), money));
		}
		else if (takeDepositType == 3) {
		//冻结
			wuser.setAvlBal(BigDecimalUtils.subRound(wuser.getAvlBal(), money));
			wuser.setFrzBal(BigDecimalUtils.addRound(wuser.getFrzBal(), money));
		}
		//资产总值 = 可用余额 + 冻结金额
		wuser.setFund(BigDecimalUtils.addRound(wuser.getAvlBal(), wuser.getFrzBal()));
		this.wUserService.update(wuser);
		//添加时间
		userFund.setAddtime(TypeConvert.dbDefaultDate());
		//更新时间
		userFund.setUptime(TypeConvert.dbDefaultDate());
		//冻结金额
		userFund.setFreeze(wuser.getFrzBal() == null?0D:wuser.getFrzBal());
		//当前余额
		userFund.setAmount(wuser.getAvlBal() == null?0D:wuser.getAvlBal());
		//组ID
		userFund.setLid(wuser.getGroupid());
		//用户ID
		userFund.setUid(wuser.getId());
		if (userFund.getId() == null) {
			this.save(userFund);
		}
		else {
			this.update(userFund);
		}
	}*/

	@Override
	@SuppressWarnings("unchecked")
	public List<UserTradeArrearageVo> queryUserTradeArrearageVo(String uid,
			String tradeId,Integer ...types) {
		
		if(StringUtil.isBlank(uid)){
			return null;
		}
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		StringBuffer sql = new StringBuffer("SELECT f.rid AS tradeId,MAX(f.addtime) AS maxTime,sum(ABS(f.money)) AS money,MIN(f.addtime) AS minTime,t.`status` AS `status` ");
		sql.append(" FROM w_user_fund f,w_user_trade t ");
		sql.append(" WHERE f.uid=? AND f.rid=t.program_no AND f.pay_status=0  ");
		if(types != null && types.length > 0){
			sql.append(" AND f.type IN"+getQueryInSql(types.length)+" ");
			for (int type : types) {
				params.add(type);
			}
		}
		if(!StringUtil.isBlank(tradeId)){
			sql.append(" AND f.rid=? ");
			params.add(tradeId);
		}
		sql.append(" GROUP BY f.rid ORDER BY sum(ABS(f.money)) DESC ");
		
		List<UserTradeArrearageVo> resutl = nativeQuery(sql.toString(), params, UserTradeArrearageVo.class);
		
		if(resutl != null && !resutl.isEmpty()){
			for (UserTradeArrearageVo userTradeArrearageVo : resutl) {
				Date endTime = new Date();
				Date biginTime = new Date(userTradeArrearageVo.getMaxTime().longValue() * 1000l);
				long days = Dates.daysBetween2(biginTime,endTime);
				userTradeArrearageVo.setDays(BigInteger.valueOf(days));
			}
		}
		return resutl;
	}

	@Override
	public List<UserTradeArrearageVo> queryHkUserTradeArrearageVo(String uid,
			String tradeId, Integer... types) {
		if(StringUtil.isBlank(uid)){
			return null;
		}
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		StringBuffer sql = new StringBuffer("SELECT f.rid AS tradeId,MAX(f.addtime) AS maxTime,sum(ABS(f.money)) AS money,MIN(f.addtime) AS minTime,t.`status` AS `status` ");
		sql.append(" FROM w_user_fund f,hk_user_trade t ");
		sql.append(" WHERE f.uid=? AND f.rid=t.program_no AND f.pay_status=0  ");
		if(types != null && types.length > 0){
			sql.append(" AND f.type IN"+getQueryInSql(types.length)+" ");
			for (int type : types) {
				params.add(type);
			}
		}
		if(!StringUtil.isBlank(tradeId)){
			sql.append(" AND f.rid=? ");
			params.add(tradeId);
		}
		sql.append(" GROUP BY f.rid ORDER BY sum(ABS(f.money)) DESC ");
		
		List<UserTradeArrearageVo> resutl = nativeQuery(sql.toString(), params, UserTradeArrearageVo.class);
		
		if(resutl != null && !resutl.isEmpty()){
			for (UserTradeArrearageVo userTradeArrearageVo : resutl) {
				Date endTime = new Date();
				Date biginTime = new Date(userTradeArrearageVo.getMaxTime().longValue() * 1000l);
				long days = Dates.daysBetween2(biginTime,endTime);
				userTradeArrearageVo.setDays(BigInteger.valueOf(days));
			}
		}
		return resutl;
	}
	
	@Override
	public List<UserFund> queryUserFunds(String uid, String tradeId,Integer...types) {
		if(StringUtil.isBlank(uid)){
			return null;
		}
		List<Integer> typeList = null;
		if(types != null && types.length > 0){
			typeList = new ArrayList<Integer>();
			for (Integer type : types) {
				typeList.add(type);
			}
		}
		return this.getEntityDao().findByUidAndPayStatusAndRidAndTypeIn(uid, (short)0, tradeId, typeList);
	}
	
	@Override
	public void updateUserFund(WUser wuser, String tradeId) {
	
		Double avlBal = wuser.getAvlBal();   //用户余额
		
		Double arrearageMoney = 0.00;
		
		List<UserFund> userFundList = this.queryUserFunds(wuser.getId(), tradeId, new Integer[]{11,12});
		
		if(userFundList != null && !userFundList.isEmpty()){
			for (UserFund userFund : userFundList) {   //补费处理
				double money = userFund.getMoney();
				money = Math.abs(money);
				arrearageMoney += money;
			}
		}
		arrearageMoney = BigDecimalUtils.round2(arrearageMoney, 2);
		if(avlBal >= arrearageMoney){  //余额大于欠费总额，进行补费操作
			int index = 1;
			for (UserFund userFund : userFundList) {
				index++;
				double money = userFund.getMoney();
				money = Math.abs(money);
				avlBal = BigDecimalUtils.round2(avlBal-money, 2);
				userFund.setAmount(avlBal);
				userFund.setPayStatus((short)1);
				userFund.setUptime(Dates.getCurrentLongDate()+index);
				if(11 == userFund.getType().intValue()){  //利息
					userFund.setRemark("支付利息"+money+"元");
				}else{       //管理费
					userFund.setRemark("支付管理费"+money+"元");
				}
				logger.info("【用户补费】->[补费用户编号"+wuser.getId()+",补费用户手机:"+wuser.getMobile()+",补费编号："+userFund.getId()+",方案编号(rid)："+userFund.getRid()+",补费类型："+userFund.getType()+",补费金额："+money+",账户当前余额："+BigDecimalUtils.round2(avlBal+money, 2)+",补费后账户余额："+avlBal+"]");
				this.getEntityDao().update(userFund);   //补费
			}
			wuser.setAvlBal(avlBal);  //用户余额减少
			logger.info("【用户补费】->【更新用户余额："+avlBal+"】");
			wUserService.update(wuser);   //更新用户余额
			
			//获取方案信息
			UserTrade userTrade = userTradeService.findByProgramNoAndWuser(tradeId, wuser);
			
			if(userTrade != null){   //判断是否是属于A股方案
				if(1 != userTrade.getStatus() || 2 == userTrade.getFeeType()){   //判断是不是操盘中(过滤掉涌金版方案)的，不是操盘中的直接返回 
					return;
				}
				
				//判断账户是否在补仓线以上,没有在补仓线上  直接返回
				if (!userTradeService.isUpMarginLine(userTrade.getGroupId())){
					return;
				}
				//取消限制买入
				userTradeService.changeOperationStatus(new String[]{userTrade.getGroupId()}, 0, "system");  
			}else{   //港股方案
				return;
			}
		}
	}


	@Override
	public List<UserFundVo> getManagementFee(Long startTimeLong, Long endTimeLong) {
		String sql = "SELECT uid,MIN(uptime) upTime,SUM(money) money FROM w_user_fund WHERE pay_status = 1 AND type = 12 AND uptime BETWEEN ? AND ? GROUP BY uid";
		List<Object> params = Lists.newArrayList();
		params.add(startTimeLong);
		params.add(endTimeLong);
		List<UserFundVo> fundVos = nativeQuery(sql, params, UserFundVo.class);
		return fundVos;
	}

	@Override
	public List<UserFund> findByPayStatusAndUid(short payStatus,String uid) {
		return getEntityDao().findByPayStatusAndUidAndMoneyLessThanOrderByMoneyAsc(payStatus,uid,NumberUtils.toDouble("0"));
	}
}
