package com.tzdr.api.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.BankRequest;
import com.tzdr.api.request.WithDrawRequest;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.api.service.ApiBankService;
import com.tzdr.business.api.service.ApiFundService;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.drawBlackList.DrawBlackListService;
import com.tzdr.business.service.drawMoney.DrawMoneyService;
import com.tzdr.business.service.drawMoney.UserBankService;
import com.tzdr.business.service.drawMoneyData.DrawMoneyDataService;
import com.tzdr.business.service.pay.PaymentSupportBankService;
import com.tzdr.business.service.thread.SMSPgbSenderThread;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.common.api.bbpay.Bibipay;
import com.tzdr.common.api.bbpay.util.BbUtil;
import com.tzdr.common.api.payease.PayEase;
import com.tzdr.common.api.payease.util.PayEaseUtil;
import com.tzdr.common.api.umpay.WithdrawPay;
import com.tzdr.common.api.umpay.data.WithdrawPayExtendInfo;
import com.tzdr.common.api.umpay.data.WithdrawPayInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.PaymentSupportBankVo;
import com.tzdr.domain.web.entity.DrawBlackList;
import com.tzdr.domain.web.entity.DrawList;
import com.tzdr.domain.web.entity.DrawMoneyData;
import com.tzdr.domain.web.entity.PaymentSupportBank;
import com.tzdr.domain.web.entity.UserBank;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * <B>说明: </B>提现操作相关接口
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/user/withdraw")
public class UserWithDrawController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserWithDrawController.class);	
	
	@Autowired
	private ApiFundService  apiFundService;
	
	
	@Autowired
	private ApiUserService  apiUserService;
	
	@Autowired
	private DrawMoneyService drawMoneyService;
	
	
	@Autowired
	private UserBankService  userBankService;
	
	@Autowired
	private DrawBlackListService drawBlackListService;
	
	@Autowired
	private DataMapService  dataMapService;
	
	@Autowired
	private PasswordService  passwordService;
	
	@Autowired
	private DrawMoneyDataService drawMoneyDataService;
	
	@Autowired
	private ApiBankService apiBankService;
	
	@Autowired
	private PaymentSupportBankService  paymentSupportBankService;
	/**
	 * 获取用户绑定的银行卡
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bank_list",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult bankList(HttpServletRequest request,HttpServletResponse response) {
		
		String uid =AuthUtils.getCacheUser(request).getUid();
		ApiUserVo appUserVo = apiUserService.findByUid(uid);
		if (ObjectUtil.equals(null, appUserVo)){
			return new ApiResult(false,ResultStatusConstant.BankList.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		List<UserBank> banks=this.drawMoneyService.findUserBankbyUserId(uid);
		JSONArray jsonArray = new JSONArray();
		if (!CollectionUtils.isEmpty(banks)){
			for (UserBank bank : banks){
				JSONObject  jsonObject = new JSONObject();
				jsonObject.put("bankId",bank.getId());
				jsonObject.put("uid",bank.getUid());
				jsonObject.put("card",bank.getCard());
				PaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(bank.getBank());
				jsonObject.put("bankName",ObjectUtil.equals(null,paymentSupportBank)?"":paymentSupportBank.getBankName());
				jsonObject.put("abbreviation",bank.getBank());
				jsonObject.put("default",bank.getIsdefault()==1?true:false);
				jsonArray.add(jsonObject);
			}
		}
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"query.success.",jsonArray);
	}
	
	
	/**
	 * 获取当前系统支持提现的银行列表
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/support_banks",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult supportBanks(HttpServletRequest request,HttpServletResponse response) {
		
		String uid =AuthUtils.getCacheUser(request).getUid();
		ApiUserVo appUserVo = apiUserService.findByUid(uid);
		if (ObjectUtil.equals(null, appUserVo)){
			return new ApiResult(false,ResultStatusConstant.BankList.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		//获取提现渠道设置参数
		int withdrawSetting = dataMapService.getWithDrawSetting();
		if (Constant.PaymentChannel.BB_PAY!=withdrawSetting
				&& Constant.PaymentChannel.UM_PAY !=withdrawSetting
				&& Constant.PaymentChannel.EASE_PAY !=withdrawSetting){
			return new ApiResult(false,ResultStatusConstant.FAIL,"system.withdraw.setting.error.");
		}
		
		List<PaymentSupportBankVo>  paymentSupportBankVos = paymentSupportBankService.querySupportDrawBanks(withdrawSetting);
		JSONArray jsonArray = new JSONArray();
		if (!CollectionUtils.isEmpty(paymentSupportBankVos)){
			for (PaymentSupportBankVo bank : paymentSupportBankVos){
				JSONObject  jsonObject = new JSONObject();
				jsonObject.put("bankName",bank.getBankName());
				jsonObject.put("abbreviation",bank.getAbbreviation());
				jsonArray.add(jsonObject);
			}
		}
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"query.success.",jsonArray);
	}
	
	

	/**
	 * 绑定新的银行卡
	 * @param bankRequest
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add_bank",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult addBank(BankRequest bankRequest,HttpServletResponse response,HttpServletRequest request){	
		String uid =AuthUtils.getCacheUser(request).getUid();
		String card = bankRequest.getCard();
		String bank = bankRequest.getBank();
		String prov = request.getParameter("prov");// 省
		String city = request.getParameter("city");// 市
		String address = request.getParameter("address");// 具体地址
		String provinceCity=StringUtil.join(prov,city);
		if (StringUtil.isBlank(card) || StringUtil.isBlank(uid)|| StringUtil.isBlank(bank)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		
		WUser user = this.drawMoneyService.getUser(uid);
		if (ObjectUtil.equals(null, user)){
			return new ApiResult(false,ResultStatusConstant.AddBank.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		// 实名认证
		UserVerified userverified = user.getUserVerified();
		if (StringUtil.isBlank(userverified.getIdcard()) 
				||  StringUtil.isBlank(userverified.getTname())){
			return new ApiResult(false,ResultStatusConstant.AddBank.IDENTITY_AUTHENTICATION,"pleace.identity.authentication.");
		}
		
		UserBank ubank=drawMoneyService.findUsercardbycard(card,uid);
		if (!ObjectUtil.equals(null, ubank)){
			return new ApiResult(false,ResultStatusConstant.AddBank.THE_BANK_IS_EXIST,"the.bank.is.exist.");
		}
		
		String path=RequestUtils.getBankpathbybankname(bank);
		drawMoneyService.saveCard(user,bank,card,path,address,provinceCity);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"add.success.");

	}
	
	/**
	 * 删除已绑定的银行卡
	 * @param bankRequest
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/del_bank",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult delBank(BankRequest bankRequest,HttpServletResponse response,HttpServletRequest request){
		String uid =AuthUtils.getCacheUser(request).getUid();
		String bankid = bankRequest.getBankId();
		if (StringUtil.isBlank(bankid) || StringUtil.isBlank(uid)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		
		ApiUserVo appUserVo = apiUserService.findByUid(uid);
		if (ObjectUtil.equals(null, appUserVo)){
			return new ApiResult(false,ResultStatusConstant.DelBank.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		UserBank userBank =apiBankService.findByIdAndUserId(bankid,uid);
		if (ObjectUtil.equals(null, userBank)){
			return new ApiResult(false,ResultStatusConstant.DelBank.THE_BANK_NOT_EXIST,"the.bank.not.exist.");
		}
		
		boolean flag=drawMoneyService.checkCard(bankid,uid);
		if (flag){
			return new ApiResult(false,ResultStatusConstant.DelBank.WITHDRAWING_CANTOT_DELETE,"withdrawing.cantot.delete.");
		}
		
		this.drawMoneyService.delCard(bankid);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"delete.success.");
	}
	
	
	/**
	 * 删除已绑定的银行卡
	 * @param bankRequest
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/set_default_bank",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult delBank(String bankId,HttpServletResponse response,HttpServletRequest request){
		String uid =AuthUtils.getCacheUser(request).getUid();
		if (StringUtil.isBlank(bankId) || StringUtil.isBlank(uid)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		
		
		UserBank userBank =apiBankService.findByIdAndUserId(bankId,uid);
		if (ObjectUtil.equals(null, userBank)){
			return new ApiResult(false,ResultStatusConstant.SetDefaultBank.THE_BANK_NOT_EXIST,"the.bank.not.exist.");
		}
		this.drawMoneyService.setDefaulcard(bankId,uid);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"set.success.");
	}
	
	
	/**
	 * 根据用户和取款实体生成取款参数对象
	 * @param user 用户对象
	 * @param money 金额
	 * @param orderId 订单号
	 * @param cardno 银行卡号
	 * @param uvf 安全信息号
	 * @return
	 * @date 2015年3月26日
	 * @author zhangjun
	 */
	private WithdrawPayInfo setWithdrawInfo(WUser user,Double money,String orderId,String cardno,UserVerified uvf){
		WithdrawPayInfo withdrawPayInfo=new WithdrawPayInfo();
		java.text.DecimalFormat df = new DecimalFormat("#"); 
		UserBank ubank=this.userBankService.findUsercardbycard(cardno, user.getId());
		withdrawPayInfo.setAmount(df.format(BigDecimalUtils.mul(money,100.0)));
		withdrawPayInfo.setMerDate(DateUtils.dateTimeToString(new Date(),"yyyyMMdd"));
		withdrawPayInfo.setOrderId(orderId);
		withdrawPayInfo.setRecvAccount(cardno);
		withdrawPayInfo.setPurpose("取现"+money);
		withdrawPayInfo.setRecvUserName(uvf.getTname());
		withdrawPayInfo.setBankBrhname("上海信闳投资");
		withdrawPayInfo.setRecvGateId(ubank.getBank().toUpperCase());
		return withdrawPayInfo;
	}
	
	/**
	 * 申请提现接口
	 * @param withDrawRequest
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/handle",method=RequestMethod.POST)
	@ResponseBody
	public synchronized ApiResult handle(WithDrawRequest withDrawRequest,HttpServletResponse response,HttpServletRequest request){
		
		String  uid =AuthUtils.getCacheUser(request).getUid();
		String  card = withDrawRequest.getCard();
		String  abbreviation = withDrawRequest.getBank();
		String withdrawPwd = withDrawRequest.getWithdrawPwd();
		Double money = withDrawRequest.getMoney();
		
		if (ObjectUtil.equals(null,money) 
				|| StringUtil.isBlank(abbreviation) 
				|| StringUtil.isBlank(card) 
				|| StringUtil.isBlank(withdrawPwd)
				|| StringUtil.isBlank(uid)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		
		//校验是否支持该银行提现
		PaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(abbreviation);
		if (ObjectUtil.equals(null, paymentSupportBank)){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.NOT_SUPPORT_THE_BANK,"not.support.the.bank.");
		}
		//获取提现渠道设置参数
		int withdrawSetting = dataMapService.getWithDrawSetting();
		if (Constant.PaymentChannel.BB_PAY !=withdrawSetting 
				&& Constant.PaymentChannel.UM_PAY !=withdrawSetting
				&& Constant.PaymentChannel.EASE_PAY !=withdrawSetting){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		if (Constant.PaymentChannel.BB_PAY==withdrawSetting 
				&& DataConstant.PAYMENT_SUPPORT != paymentSupportBank.getSupportBbdraw()){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.NOT_SUPPORT_THE_BANK,"not.support.the.bank.");

		}
		if (Constant.PaymentChannel.UM_PAY==withdrawSetting 
				&& DataConstant.PAYMENT_SUPPORT != paymentSupportBank.getSupportUmdraw()){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.NOT_SUPPORT_THE_BANK,"not.support.the.bank.");

		}
		
		if (Constant.PaymentChannel.EASE_PAY==withdrawSetting 
				&& DataConstant.PAYMENT_SUPPORT != paymentSupportBank.getSupportEasedraw()){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.NOT_SUPPORT_THE_BANK,"not.support.the.bank.");
		}
		// 币币支付最多不超过50000
		if (Constant.PaymentChannel.BB_PAY==withdrawSetting 
				&& Double.valueOf(money)>=50000){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.BB_LIMIT_MAX_MONEY,"bb.limit.max.money.");
		}
		
		// 易支付支付最多不超过500000
		if (Constant.PaymentChannel.EASE_PAY ==withdrawSetting
				&& Double.valueOf(money)>=500000){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.EASEPAY_LIMIT_MAX_MONEY,"easepay.limit.max.money.");
		}
		
		WUser user=drawMoneyService.getUser(uid);
		if (ObjectUtil.equals(null, user)){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		// 是否实名认证
		if (!RequestUtils.isCertification(user)){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.NOT_CERTIFICATION,"user.not.certification.");
		}
		
		
		//余额大于提现金额并且配置用户大于10元
		if(Double.valueOf(money)<DataConstant.MIN_WITHDRAW_MONEY){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.MIN_WITHDRAW_MONEY,"min.withdraw.money.");
		} 
		 
		//提现时间控制
		List<DataMap> drawDate=dataMapService.findByTypeKey(DataDicKeyConstants.DRAW_DATE);
		if(!CollectionUtils.isEmpty(drawDate)){
			 String keydate=drawDate.get(DataConstant.ZERO).getValueKey();
			 Date startdate=DateUtils.stringToDate(keydate,Dates.CHINE_DATE_FORMAT_TO_MINUTE);
			 if(new Date().getTime()<=startdate.getTime()){
					return new ApiResult(false,ResultStatusConstant.WithDrawHandle.SYSTEM_UPGRADE_TIME,"sytem.upgrade.time.");
			 }
		 }
		// 银行卡号不存在
		UserBank drawBank=this.drawMoneyService.findUsercardbycard(card,uid);
		if (ObjectUtil.equals(null, drawBank)){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.THE_BANK_NOT_EXIST,"the.bank.not.exist.");
		}
		
		//判断是否有欠费记录
		DrawBlackList drawBlackList = drawBlackListService.getDrawBlackListByUid(uid);
		if(!ObjectUtil.equals(null, drawBlackList)){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.EXIST_ARREARAGE,drawBlackList.getReason());
		}
		// 当日提现不能超过5次
		List<DrawList> list=drawMoneyService.findDrawCount(user);
		if(list.size()>=DataConstant.WITHDRAW_TIME){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.OVER_LIMT_WITHDRAW_NUMBER,"over.limit.withdraw.number.");
		}  
		
		
		 
		// 余额不足不能提现
		Double balance=ObjectUtil.equals(null, user.getAvlBal())?DataConstant.ZERO_MONEY:user.getAvlBal();
		if (balance < money){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.NOT_SUFFICIENT_FUNDS,"not.sufficient.funds.");
		}
		
		// 提现密码不对
		String moneypwd=passwordService.encryptPassword(withdrawPwd, user.getLoginSalt());
		if (!StringUtil.equals(moneypwd,user.getUserVerified().getDrawMoneyPwd())){
			return new ApiResult(false,ResultStatusConstant.WithDrawHandle.WITHDRAW_PASSWORD_ERROR,"withdraw.password.error.");
		}
		
		// 开始提现插入数据
	    String ip=IpUtils.getIpAddr(request);
		String orderId=drawMoneyService.getRandomStr(DataConstant.WITHDRAW_ORDERID_LENGTH);
		boolean flag=false;
		DrawList drawList = null;
		try 
		{
			drawList = drawMoneyService.insertDraw(Constant.Source.TZDR,user,String.valueOf(money),card,paymentSupportBank,ip,orderId,withdrawSetting);
			flag=true;
		} catch (Exception exception) {
			logger.error("API接口-插入取款数据失败"+exception.getMessage());
			String dataDetail="API接口:userInfo:id:"+user.getId()+"|mobile:"+user.getMobile()+"orderId:"+orderId+"|ip:"+ip+"|异常："+exception.getMessage();
			EmailExceptionHandler.getInstance().HandleExceptionWithData(exception, "API接口--插入取款数据失败", this.getClass().getName()+":moreSuccess", dataDetail);
		}
		// 插入数据失败
	    if(!flag){
			return new ApiResult(false,ResultStatusConstant.FAIL,"withdraw.fail;inert.record.fail.");
	    }
		
	    //记录插入成功后不用审核开始调用取款接口
		DrawMoneyData moneydata=drawMoneyDataService.getAduitMoneyByType(DataConstant.OPEN_AUTO_WITHDRAW);
	    Double moneyval=moneydata.getMaxmoney();		
		if(moneyval > money){
			// 币币支付
			if (Constant.PaymentChannel.BB_PAY==withdrawSetting){
				return bbDrawMoney(drawList,money, paymentSupportBank, user, card);
			}
			//易支付
			if (Constant.PaymentChannel.EASE_PAY==withdrawSetting){
				return payeaseDrawMoney(drawList,money, paymentSupportBank, user, card);
			}
			// 联动优势
			if (Constant.PaymentChannel.UM_PAY==withdrawSetting){ 
				//开始调用接口
				WithdrawPayExtendInfo extendInfo=new WithdrawPayExtendInfo();
				//提现手续费
				String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE, "5000");
				Double handleFee = 0.00;
				//提现金额
				Double dmoney = Double.valueOf(money);
				WithdrawPayInfo withdrawPayInfo = null;
				
				if(!StringUtil.isBlank(handleFeeStr)) {
					handleFee = Double.valueOf(handleFeeStr);
				}
				
				if(BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
					//扣除手续费
					dmoney = BigDecimalUtils.subRound(dmoney, handleFee);
					withdrawPayInfo = this.setWithdrawInfo(user, dmoney,
								orderId, card, user.getUserVerified());
				} else {
					withdrawPayInfo = this.setWithdrawInfo(user, dmoney,
								orderId, card, user.getUserVerified());
				}
				UserVerified uvf=user.getUserVerified();
				extendInfo.setCheckFlag(DataConstant.WITHDRAW_EXTENDINFO_CHECK_FLAG);
				extendInfo.setIdentityCode(uvf.getIdcard());
				WithdrawPay draw=WithdrawPay.getInstance();
				JSONObject jsonResult=null;
				try {
					logger.info("APP接口-开始调用取款接口,金额:"+dmoney+", orderId:"+orderId+", 账号:"+user.getMobile());
					jsonResult = draw.getWithdrawResponse(withdrawPayInfo,extendInfo);
					logger.info("结束调用取款接口.");
				} catch (Exception e) {
					logger.error("APP接口-调用取款接口失败"+e.getMessage());
					String dataDetail="APP接口:userInfo:id:"+user.getId()+"|mobile:"+user.getMobile()+"orderId:"+orderId+"|ip:"+ip+"|异常："+e.getMessage();
					EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "API接口--调用取款接口失败", this.getClass().getName()+":moreSuccess", dataDetail);
				}
				
				if (ObjectUtil.equals(null,jsonResult)){
					return new ApiResult(false,ResultStatusConstant.FAIL,"withdraw.fail;transfer.withdraw.interface.fail.");			
				}
				
				String code=jsonResult.getString("retCode");
			    if(!StringUtil.equals(DataConstant.WITHDRAW_SUCCESS,code)){
					return new ApiResult(false,ResultStatusConstant.FAIL,"withdraw.fail;transfer.withdraw.interface.fail.");			
			    }
			    
			    //发送短信
				Map<String,String> map= new HashMap<String,String>();
				map.put("account", user.getMobile());
				map.put("money",String.valueOf(money));
				new SMSPgbSenderThread(user.getMobile(),"draw.money.template", map).start();
			 }
		   }
		 return new ApiResult(true,ResultStatusConstant.SUCCESS,"withdraw.success.");
	}

	
	/**
	 * 调用币币支付接口进行提现
	 * @param drawList
	 * @param dmoney
	 * @param paymentSupportBank
	 * @param user
	 * @param bankCard
	 * @return
	 */
	private ApiResult bbDrawMoney(DrawList drawList,Double dmoney,PaymentSupportBank  paymentSupportBank,WUser user,String bankCard){
		logger.info("API提现调用币币支付接口参数：金额["+dmoney+"],卡号["+bankCard+"]");
		if (ObjectUtil.equals(drawList, null)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"withdraw.fail;transfer.withdraw.interface.fail.");			
		}
		//提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,DataDicKeyConstants.BB_FEE);
		
		Double handleFee = 0.00;
		
		if(!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}
		
		if(BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			//扣除手续费
			dmoney = BigDecimalUtils.subRound(dmoney, handleFee);
		} 
		// 调用提现接口
		// 格式：联行号~|~银行卡号~|~开户人~|~结算金额~|~1：私人，2：公司
		JSONObject bbResult = BbUtil.withDrawMony(dmoney, paymentSupportBank.getBbpayContactNumber(), drawList.getCard(), user.getUserVerified().getTname(), drawList.getNo());

		if (Bibipay.HANDLE_SUCCESS_STATUS != bbResult.getIntValue("status")){
			logger.info("币币支付委托结算失败,"+bbResult);
			EmailExceptionHandler.getInstance().HandleHintWithData("币币支付委托结算失败","bbDrawMoney",bbResult.toJSONString());
			return new ApiResult(false,ResultStatusConstant.FAIL,"withdraw.fail;transfer.withdraw.interface.fail.");			
		}
		// 更新币币订单号到提现记录中
		drawMoneyService.updatDrawBybbOrderId(drawList.getId(),bbResult.getString("order_number"));
		//发送短信
		Map<String,String> map= new HashMap<String,String>();
		map.put("account", user.getMobile());
		map.put("money", dmoney.toString());
		new SMSPgbSenderThread(user.getMobile(),"draw.money.template", map).start();
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"withdraw.success.");
	}
	

	/**
	 * 易支付提现处理
	 * @param drawList
	 * @param dmoney
	 * @param paymentSupportBank
	 * @param user
	 * @param bankCard
	 * @return
	 */
	private ApiResult payeaseDrawMoney(DrawList drawList,Double dmoney,PaymentSupportBank paymentSupportBank,WUser user,String bankCard){
		logger.info("投资达人app端提现调用易支付支付接口参数：金额["+dmoney+"],卡号["+bankCard+"]");
		//提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,DataDicKeyConstants.PAYEASE_FEE);
		Double handleFee = 0.00;
		if(!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}
		
		if(BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			//扣除手续费
			dmoney = BigDecimalUtils.subRound(dmoney, handleFee);
		} 
		// 调用提现接口
		// 格式：分帐信息总行数|分帐总金额|$收方帐号|收方帐户名|收方开户行|收方省份|收方城市|付款金额| 客户标识|联行号
		JSONObject result =PayEaseUtil.tzdrDrawMony(dmoney, paymentSupportBank.getBbpayContactNumber(), drawList.getCard(), 
				user.getUserVerified().getTname(), drawList.getNo(),paymentSupportBank.getBankName());
		if (PayEase.WITHDRAW_INTERFACE_SUCCESS != result.getIntValue("status")){
			logger.info("易支付调用取款接口失败，"+result.toJSONString());
			EmailExceptionHandler.getInstance().HandleHintWithData("易支付调用取款接口失败","PayEaseDrawMoney", result.toJSONString());
			return new ApiResult(false,ResultStatusConstant.FAIL,"withdraw.fail;transfer.withdraw.interface.fail.");			
		}
		
		// 更新商户号和商户秘钥 到提现记录中，以便获取提现状态时方便
		drawMoneyService.updatDrawPayeaseInfo(drawList.getId(),result.getString("vmid"),result.getString("secret"));
		//发送短信
		Map<String,String> map= new HashMap<String,String>();
		map.put("account", user.getMobile());
		map.put("money", dmoney.toString());
		new SMSSenderThread(user.getMobile(),"draw.money.template", map).start();
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"withdraw.success.");
	}
}
