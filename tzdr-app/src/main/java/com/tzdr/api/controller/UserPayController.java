package com.tzdr.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.BankTransferRequest;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.pay.pingpp.example.ChargeExample;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.pay.PaymentSupportBankService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.common.api.bbpay.util.BbConfigUtil;
import com.tzdr.common.api.payease.PayEase;
import com.tzdr.common.api.payease.util.PayEaseUtil;
import com.tzdr.common.api.payease.vo.PayEaseParams;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.domain.constants.Constant;
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
	private ApiUserService  apiUserService;
	
	@Autowired
	private UserVerifiedService  userVerifiedService;
	
	@Autowired
	private SecurityInfoService securityInfoService;
	
	
	@Autowired
	private PayService  payService;
	
	@Autowired
	private PaymentSupportBankService paymentSupportBankService;
	
	
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
				
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"submit.success.");
	}	
	/**
	 * ping++支付
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pingplusplus",method = RequestMethod.POST)
	public String pingplusplus(HttpServletRequest request){
		String uid = AuthUtils.getCacheUser(request).getUid();  //获取用户信息
		WUser user = this.payService.getUser(uid);
		String paymoney=request.getParameter("money");
		String payWay = request.getParameter("payWay");
		if(paymoney != null && Integer.parseInt(paymoney) > 0){
			int status=0;
			String paytype = "3" ;
			int source = 1;
			String ip = IpUtils.getIpAddr(request);
			String orderNo = ChargeExample.randomNo();
			String charage = payService.doSavePingPPRecharge(payWay,source,user,status,"",paymoney,ip,paytype,orderNo);
			System.out.println(charage);
			return charage;
		}
		return null;
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
	   System.out.println(JSONObject.toJSONString(payEaseParams));
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"Successful submit",payEaseParams);
	}
}
