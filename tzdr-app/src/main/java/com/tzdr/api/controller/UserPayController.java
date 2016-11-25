package com.tzdr.api.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.BankTransferRequest;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.api.service.ApiRechargeService;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.pay.gopay.DateUtil;
import com.tzdr.business.pay.gopay.handle.GoPayTradeData;
import com.tzdr.business.pay.gopay.model.GoPayRequestModel;
import com.tzdr.business.pay.pingpp.config.Config;
import com.tzdr.business.pay.pingpp.config.enums.Channel;
import com.tzdr.business.pay.pingpp.example.ChargeExample;
import com.tzdr.business.pay.pingpp.model.PingPPModel;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.pay.PaymentSupportBankService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.common.api.bbpay.util.BbConfigUtil;
import com.tzdr.common.api.payease.PayEase;
import com.tzdr.common.api.payease.util.PayEaseUtil;
import com.tzdr.common.api.payease.vo.PayEaseParams;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.vo.AutoWechatRequestVo;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**  
 * @Title: UserPayController.java     
 * @Description: 支付宝业务信息管理类    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 下午2:22:05    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/user/pay")
public class UserPayController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserPayController.class);
	
	/**
	 * app支付成功，商户前端返回地址
	 */
	public static final String APP_SRETURL = BbConfigUtil.getContext(DataConstant.APP_SRETURL);
	@Autowired
	private MessagePromptService messagePromptService;
	
	@Autowired
	private ApiUserService  apiUserService;
	
	@Autowired
	private UserVerifiedService  userVerifiedService;
	
	@Autowired
	private SecurityInfoService securityInfoService;
	
	
	@Autowired
	private PayService  payService;
	
	@Autowired
	private PaymentSupportBankService paymentSupportBankService;
	@Autowired
	private ApiRechargeService  apiRechargeService;
	
	/**
	* @Title: getAlipayAcount    
	* @Description: 获取支付宝信息
	* @param modelMap
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/getalipayacount",method=RequestMethod.POST)
	@ResponseBody
	public  ApiResult getAlipayAcount(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		
		//获取用户安全信息
		UserVerified userverified = securityInfoService.findByUserId(uid);
		
		String alipayAcount = userverified.getAlipayAccount();  //获取支付宝帐号
		
		Map<String,Object> dataMap = new HashMap<String,Object>(); 
		dataMap.put("alipayAcount", alipayAcount);
		
		return new ApiResult(true, ResultStatusConstant.SUCCESS,null,dataMap);
	}
	
	/**
	* @Title: bindAlipay    
	* @Description: 绑定支付宝信息
	* @param bindAlipayRequest
	* @return
	 */
	@RequestMapping(value = "/bindalipay",method=RequestMethod.POST)
	@ResponseBody
	public  ApiResult bindAlipay(String alipayAcount,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		
		if (StringUtil.isBlank(alipayAcount)){  //判断账户是否为空
			return new ApiResult(false, ResultStatusConstant.BindAlipayConstant.ALIPAYACOUNT_NOT_NULL,"The alipayAcount cannot be empty");
		}
		
		//根据账户获取用户安全信息
		UserVerified tempUserVerified = userVerifiedService.queryUserVerifiedByAliAccount(alipayAcount); 
		if(!ObjectUtil.equals(null, tempUserVerified)) {
			return new ApiResult(false, ResultStatusConstant.BindAlipayConstant.ALIPAYACOUNT_EXIST,"The alipayAcount is exist");
		}
		
		UserVerified userverified = securityInfoService.findByUserId(uid);
		
		//绑定支付宝账号
		userverified.setAlipayAccount(alipayAcount);
		
		userVerifiedService.update(userverified);
		
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"Successful binding");
	}
	/**
	 * 验证用户是否绑定微信号
	 * @return
	 */
	@RequestMapping(value = "/check/wx/account",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult checkedWxAccount(HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();
		UserVerified userVerified = userVerifiedService.queryUserVerifiedByUi(uid);
		ApiResult resultJson = new ApiResult(false);
		if(userVerified != null){
			String wxAccount = userVerified.getWxAccount();
			if(wxAccount != null && wxAccount.length() > 0){
				resultJson.setSuccess(true);
				resultJson.setMessage(wxAccount);
			}
		}
		return resultJson;
	}
	/**
	 * 绑定微信账号
	 * @param request
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/wx/bind/account",method = RequestMethod.POST)
	public ApiResult wxBindAccount(HttpServletRequest request,@RequestParam("account")String account){
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user = this.payService.getUser(uid);
		ApiResult resultJson = new ApiResult(false);
		if (StringUtil.isNotBlank(account)) {
			UserVerified userVerified = userVerifiedService.queryUserVerifiedByWechatAccount(account);
			if (ObjectUtil.equals(null, userVerified)) {
				UserVerified uv = userVerifiedService.queryUserVerifiedByUi(user.getId());
				// 绑定微信账号
				uv.setWxAccount(account);
				userVerifiedService.update(uv);
				resultJson.setSuccess(true);
			} else {
				resultJson.setMessage("微信账号已存在");
			}
		} else {
			resultJson.setMessage("微信账号不能为空");
		}
		return resultJson;
	}
	/**
	 * 银行转账接口
	 * @param bankTransferRequest
	 * @return 
	 */
	@RequestMapping(value = "/bank_transfer",method=RequestMethod.POST)
	@ResponseBody
	public  synchronized ApiResult bankTransfer(BankTransferRequest bankTransferRequest,HttpServletRequest request){
	
		if (bankTransferRequest.isInvalid()){
			return new ApiResult(false, ResultStatusConstant.FAIL,"invalid.params.");
		}
		
		Double money = bankTransferRequest.getMoney();
		if (new BigDecimal(money).compareTo(new BigDecimal(DataConstant.ONE))<DataConstant.ZERO){
			return new ApiResult(false,ResultStatusConstant.BankTransfer.MONEY_CANNOT_LESS_THAN_ONE,"money.cannot.less.than.one.");
		}
		
		String uid =  AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser wUser = apiUserService.get(uid);
		if (ObjectUtil.equals(null, wUser)){
			return new ApiResult(false,ResultStatusConstant.BankTransfer.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		String ip=IpUtils.getIpAddr(request);
		payService.insertEntity(Constant.Source.TZDR,wUser,bankTransferRequest.getAbbreviation(),DataConstant.PAY_NO_PROCESSING,
				"",String.valueOf(money),ip,DataConstant.TRANSBANK_TYPE,bankTransferRequest.getSerialnum());
		messagePromptService.sendMessage(PromptTypes.isBankReCharge, wUser.getMobile());
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"submit.success.");
	}	
	/**
	 * ping++支付
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pingplusplus",method = RequestMethod.POST)
	public ApiResult pingplusplus(HttpServletRequest request){
		if(true)return null;
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user = this.payService.getUser(uid);
		String paymoney=request.getParameter("money");
		String payWay = request.getParameter("payWay");
		ApiResult resultJson = new ApiResult(false);
		if(paymoney != null && Double.parseDouble(paymoney) > 0){
			Channel payWayChannl = null;
			if(payWay == null){
				payWayChannl = Channel.ALIPAY_PC_DIRECT;
			}else{
				payWayChannl = Channel.newInstanceChannel(Integer.parseInt(payWay));
				if(payWayChannl == null){
					payWayChannl = Channel.ALIPAY_PC_DIRECT;
				}
			}
			int status = 0;
			String paytype = "3" ;
			int source = 1;
			String ip = IpUtils.getIpAddr(request);
			String orderNo = ChargeExample.randomNo();
			String charage = payService.doSavePingPPRecharge(payWayChannl,source,user,status,"",paymoney,ip,paytype,orderNo);
			if(charage.equals("1")){
				PingPPModel pingPPModel = new PingPPModel();
				pingPPModel.setAmount(Double.valueOf(paymoney));
				pingPPModel.setBody(Config.BODY);
				pingPPModel.setChannel(payWayChannl.getChannelCode());
				pingPPModel.setClient_ip(ip);
				pingPPModel.setCurrency("cny");
				pingPPModel.setOrder_no(orderNo);
				pingPPModel.setSubject(Config.SUBJECT);
				resultJson.setSuccess(true);
				resultJson.setData(ChargeExample.createCharge(pingPPModel).toString());
			}
		}
		return resultJson;
	}
	/**
	 * 国付宝充值
	 * @param request
	 * @param money
	 * @return
	 */
	@RequestMapping(value = "/gopay",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult goPay(HttpServletRequest request,@RequestParam("goPaymoney") Double goPaymoney, @RequestParam("gopayWay") String gopayWay){
		String uid = AuthUtils.getCacheUser(request).getUid();
		if(goPaymoney == null){  //判断充值金额不能为空
			return new ApiResult(false,ResultStatusConstant.PayConstant.MONEY_NOT_NULL,"The money cannot be empty");
		}
		
		if(goPaymoney < 1){  //判断金额范围
			return new ApiResult(false,ResultStatusConstant.PayConstant.MONEY_ERROR,"The money must be greater than or equal to 1 less than or equal to 5000000");
		}
		
		if(goPaymoney > Math.floor(goPaymoney*100)/100){  //判断小数是否保留2位
			return new ApiResult(false,ResultStatusConstant.PayConstant.MONEY_ERROR,"Top-up amount can only keep 2 decimal places");
		}
		String money = String.valueOf(goPaymoney);
		WUser user = payService.getUser(uid);
		String ip=IpUtils.getIpAddr(request);	 //获取客户端IP
		String orderNo = ChargeExample.randomNo();
		Channel payWayChannl = null;
		if (gopayWay == null) {
			payWayChannl = Channel.ALIPAY_PC_DIRECT;
		} else {
			payWayChannl = Channel.newInstanceChannel(Integer.parseInt(gopayWay));
			if (payWayChannl == null) {
				payWayChannl = Channel.ALIPAY_PC_DIRECT;
			}
		}
		int status = 0;
		String paytype = "2";
		int source = 1;
		String message = null;
		boolean flag = false;
		String result = payService.doSavePingPPRecharge(payWayChannl, source, user, status, "", money, ip, paytype,orderNo);
		if (result.equals("1")) {
			GoPayRequestModel goPayRequestModel = new GoPayRequestModel();
			goPayRequestModel.setBuyerContact("");
			goPayRequestModel.setBuyerName("");
			goPayRequestModel.setMerOrderNum(orderNo);
			goPayRequestModel.setTranAmt(money);
			goPayRequestModel.setFeeAmt("0");
			goPayRequestModel.setTranDateTime(DateUtil.getDate("yyyyMMddHHmmss"));
			goPayRequestModel.setTranIP(ip);
			goPayRequestModel.setGoodsName("维胜充值");
			goPayRequestModel.setGoodsDetail("维胜充值");
			goPayRequestModel.setBuyerName("MWEB");//移动支付标识，固定值
			try {
				String tradeData = GoPayTradeData.createTradeData(goPayRequestModel);
				message = tradeData;
				flag = true;
				logger.info(tradeData);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			message = "操作失败，请重试!";
		}
		ApiResult resultJson = new ApiResult();
		resultJson.setSuccess(flag);
		resultJson.setData(message);
		return resultJson;
	}
	/**
	 * ping++京东手机网页支付
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jdpay_wap",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult pingplusplusJdPayWap(HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user = this.payService.getUser(uid);
		String paymoney=request.getParameter("money");
		String payWay = request.getParameter("payWay");
		ApiResult resultJson = new ApiResult(false);
		if(paymoney != null && Double.parseDouble(paymoney) > 0){
			Channel payWayChannl = null;
			if(payWay == null){
				payWayChannl = Channel.ALIPAY_PC_DIRECT;
			}else{
				payWayChannl = Channel.newInstanceChannel(Integer.parseInt(payWay));
				if(payWayChannl == null){
					payWayChannl = Channel.JD_WAP;
				}
			}
			int status = 0;
			String paytype = "10" ;
			int source = 1;
			String ip = IpUtils.getIpAddr(request);
			String orderNo = ChargeExample.randomNo();
			String charage = payService.doSavePingPPRecharge(payWayChannl,source,user,status,"",paymoney,ip,paytype,orderNo);
			if(charage.equals("1")){
				PingPPModel pingPPModel = new PingPPModel();
				pingPPModel.setAmount(Double.valueOf(paymoney));
				pingPPModel.setBody(Config.BODY);
				pingPPModel.setChannel(payWayChannl.getChannelCode());
				pingPPModel.setClient_ip(ip);
				pingPPModel.setCurrency("cny");
				pingPPModel.setOrder_no(orderNo);
				pingPPModel.setSubject(Config.SUBJECT);
				String chargeData = ChargeExample.createCharge(pingPPModel).toString();
				resultJson.setSuccess(true);
				resultJson.setData(chargeData);
				logger.info(chargeData);
			}else{
				resultJson.setMessage("支付错误,请确认支付信息");
			}
		}
		return resultJson;
	}
	private static Object lock = new Object();
	/**
	 * 微信转账确认充值
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wechat_transfer",method =RequestMethod.POST)
	@ResponseBody
	public ApiResult wechatTransfer(HttpServletRequest request,@RequestParam("money")Double money,@RequestParam("transactionNo") String transactionNo){
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user = this.payService.getUser(uid);
		ApiResult resultJson = new ApiResult(true);
		if(user == null){
			resultJson.setSuccess(false);
			resultJson.setMessage("用户信息不存在");
			return resultJson;
		}
		UserVerified userVerified = userVerifiedService.queryUserVerifiedByUi(uid);
		if(userVerified != null){
				synchronized (lock) {
					List<RechargeList>	rechargeLists = apiRechargeService.queryByTradeNo(transactionNo);
					if(rechargeLists != null && rechargeLists.size() > 0){
						resultJson.setMessage("提交失败,重复的订单号");
						resultJson.setSuccess(false);
						return resultJson;
					}
					RechargeList  rechargeList = new RechargeList();
					rechargeList.setAccount("");
					rechargeList.setAddtime(new Date().getTime());
					rechargeList.setUid(uid);
					rechargeList.setSource(Constant.RegistSource.APP_TZDR_REGIST);
					/*rechargeList.setActualMoney(money);*/
					rechargeList.setMoney(money);
					rechargeList.setTradeAccount(DataConstant.WECHAT);
					rechargeList.setType(DataConstant.WECHAT_TYPE);
					rechargeList.setStatus(DataConstant.PAY_NO_PROCESSING);
					rechargeList.setTradeNo(transactionNo);
					apiRechargeService.autoWechat(rechargeList);
				}
				// TODO 充值银行审核，给工作人员发送Email
				try {
					messagePromptService.sendMessage(PromptTypes.isWechatTransfer, user.getMobile());
				} catch (Exception e) {
					logger.info("发送邮件失败", e);
				}
		}
		return resultJson;
	}
	/**
	* @Title: ebankrecharge    
	* @Description: 网页充值接口 
	* @param money   充值金额
	* @param response
	* @param request
	* @return
	 */
	@RequestMapping(value = "/ebankrecharge")
	@ResponseBody
	public ApiResult ebankrecharge(String money,HttpServletResponse response,HttpServletRequest request){
		
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		Double  dbmoney = Double.valueOf(money);
		if(money == null){  //判断充值金额不能为空
			return new ApiResult(false,ResultStatusConstant.PayConstant.MONEY_NOT_NULL,"The money cannot be empty");
		}
		
		if(dbmoney < 1){  //判断金额范围
			return new ApiResult(false,ResultStatusConstant.PayConstant.MONEY_ERROR,"The money must be greater than or equal to 1 less than or equal to 5000000");
		}
		
		if(dbmoney > Math.floor(dbmoney*100)/100){  //判断小数是否保留2位
			return new ApiResult(false,ResultStatusConstant.PayConstant.MONEY_ERROR,"Top-up amount can only keep 2 decimal places");
		}
		
		//校验用户当日通过手机app的充值金额，不能超过10w
		/*Double  userCharges =  payService.getUserDayCharges(uid,PayEaseConfigUtil.getContext("tzdr_app_account"));
		if (!ObjectUtil.equals(null,userCharges) && BigDecimalUtils.add(userCharges, dbmoney) >= 100000){
			return new ApiResult(false,ResultStatusConstant.PayConstant.OVER_MAX_DAY_CHARGE,"over.max.day.charges,max.money less than 100000");
		}
		*/
		WUser user = this.payService.getUser(uid);   //获取用户信息
		
		String ip=IpUtils.getIpAddr(request);	 //获取客户端IP
		
	   PayEaseParams  payEaseParams = new PayEaseParams(PayEase.WAP_PAY_PMODE, money, ip,DataConstant.PayStatus.NO_PROCESSING,"");
	   payEaseParams = this.payService.PayEasePay(user, PayEaseUtil.tzdrAppPay(payEaseParams),Constant.Source.TZDR,Constant.SystemFlag.TZDR_APP);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"Successful submit",payEaseParams);
	}
	/**
	 * 微信 自动充值接口 
	 * @param autoAliPayRequest
	 * @return
	 */
	@RequestMapping(value = "/auto_wechat",method=RequestMethod.POST)
	@ResponseBody
	public synchronized ApiResult autoAlipay(@RequestBody AutoWechatRequestVo autoAliPayRequest){
		if (autoAliPayRequest.isInvalid()){
			return new ApiResult(false, ResultStatusConstant.FAIL,"invalid.params.");
		}
		logger.info("微信自动充值推送信息" + autoAliPayRequest.toString());
		List<RechargeList> rechargeLists  = apiRechargeService.queryByTradeNo(autoAliPayRequest.getSerialNo());
		if (!CollectionUtils.isEmpty(rechargeLists)){
			return new ApiResult(false, ResultStatusConstant.AutoAlipay.SERIAL_NO_EXIST,"the.serialNo.has.been.processed.");
		}
		RechargeList  rechargeList = new RechargeList();
		rechargeList.setSource(autoAliPayRequest.getSource());
		rechargeList.setAccount(autoAliPayRequest.getAccount());
		rechargeList.setMoney(autoAliPayRequest.getMoney());
		rechargeList.setActualMoney(autoAliPayRequest.getMoney());
		rechargeList.setTradeNo(autoAliPayRequest.getSerialNo());
		rechargeList.setTradeAccount(DataConstant.WECHAT);
		rechargeList.setType(DataConstant.WECHAT_TYPE);
		rechargeList.setRemark(autoAliPayRequest.getTradeTime().concat(MessageUtils.message("auto.alipay.remark",autoAliPayRequest.getMoney())));
		// 如果真实姓名不为空  同时校验微信 和真实姓名
		List<ApiUserVo> apiUsers =  apiUserService.findByAlipay(autoAliPayRequest.getAccount());
		if (apiUsers.size()==DataConstant.ONE){
			ApiUserVo apiUserVo = apiUsers.get(DataConstant.ZERO);
			// 用户已经实名认证，但是账户姓名与认证实名不一致  则失败
			if (StringUtil.isNotBlank(apiUserVo.getTname())
						&& !StringUtil.equals(apiUserVo.getTname(),autoAliPayRequest.getRealName())){
				rechargeList.setUid(autoAliPayRequest.getRealName());
				rechargeList.setStatus(DataConstant.PAY_NO_PROCESSING);
			}else
			{
				rechargeList.setUid(apiUserVo.getUid());
				rechargeList.setStatus(DataConstant.RECHARGE_LIST_PAYS_STATUS_SUCCESS);
			}
		}
		else
		{
			// 如果用户信息不存在直接将用户真实姓名保存在uid 字段当中
			rechargeList.setUid(autoAliPayRequest.getRealName());
			rechargeList.setStatus(DataConstant.PAY_NO_PROCESSING);
		}
		apiRechargeService.autoWechat(rechargeList);
		logger.info(rechargeList.getRemark()
				.concat(";weichatAccount:"+autoAliPayRequest.getAccount())
				.concat("serialNo:"+autoAliPayRequest.getSerialNo())
				.concat("realName:"+autoAliPayRequest.getRealName())
				.concat("status:"+rechargeList.getStatusvalue()));
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"auto.alipay.success.");
	}
}
