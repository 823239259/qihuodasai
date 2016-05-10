package com.tzdr.api.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.AutoAliPayRequest;
import com.tzdr.api.request.BankTransferRequest;
import com.tzdr.api.request.BindAlipayRequest;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.api.service.ApiFundService;
import com.tzdr.business.api.service.ApiRechargeService;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <B>说明: </B>系统支付相关接口
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/pay")
public class PayController {
	
	public static final Logger logger = LoggerFactory.getLogger(PayController.class);	

	
	@Autowired
	private ApiFundService  apiFundService;
	
	
	@Autowired
	private PayService  payService;
	
	
	@Autowired
	private ApiRechargeService  apiRechargeService;
	
	
	@Autowired
	private ApiUserService  apiUserService;
	
	@Autowired
	private UserVerifiedService  userVerifiedService;
	
	@Autowired
	private SecurityInfoService securityInfoService;
	
	/**
	 * 支付宝充值
	 * @param account
	 * @param uid
	 * @param money
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/alipay",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult alipay(@RequestBody RequestObj requestObj,HttpServletRequest request,HttpServletResponse response) {
		
		String account=requestObj.getAccount();
		String uid=requestObj.getUid();
		String money=String.valueOf(requestObj.getMoney());
		
		if (StringUtil.isBlank(uid)){
			return new ApiResult(false, ResultStatusConstant.FAIL,"pay.fail;uid.is.null.");
		}
		if (StringUtil.isBlank(account)){
			return new ApiResult(false, ResultStatusConstant.Alipay.ACCOUNT_IS_NULL,"account.is.null.");
		}
		WUser user = this.payService.getUser(uid);
		if (ObjectUtil.equals(null, user)){
			return new ApiResult(false, ResultStatusConstant.Alipay.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		if (NumberUtils.toDouble(money)<DataConstant.ALIPAY_MIN_MONEY){
			return new ApiResult(false, ResultStatusConstant.Alipay.UNDER_MIN_PAY_MONEY,"under.min.pay.money.");
		}
		String ip=IpUtils.getIpAddr(request);
		payService.insertEntity(Constant.Source.PGB,user,DataConstant.ALIPAY,DataConstant.PAY_NO_PROCESSING,account,money,ip,DataConstant.ALIPAY_TYPE,null);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"pay.success.");
	}
	
	
	/**
	 * 支付宝 自动充值接口 
	 * @param autoAliPayRequest
	 * @return
	 */
	@RequestMapping(value = "/auto_alipay",method=RequestMethod.POST)
	@ResponseBody
	public synchronized ApiResult autoAlipay(@RequestBody AutoAliPayRequest autoAliPayRequest){
		if (autoAliPayRequest.isInvalid()){
			return new ApiResult(false, ResultStatusConstant.FAIL,"invalid.params.");
		}
		
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
		rechargeList.setTradeAccount(DataConstant.ALIPAY);
		rechargeList.setType(DataConstant.ALIPAY_TYPE);
		rechargeList.setRemark(autoAliPayRequest.getTradeTime().concat(MessageUtils.message("auto.alipay.remark",autoAliPayRequest.getMoney())));
		// 如果真实姓名不为空  同时校验支付宝帐号 和真实姓名
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
		
		apiRechargeService.autoAliPay(rechargeList);
		logger.info(rechargeList.getRemark()
				.concat(";alipayAccount:"+autoAliPayRequest.getAccount())
				.concat("serialNo:"+autoAliPayRequest.getSerialNo())
				.concat("realName:"+autoAliPayRequest.getRealName())
				.concat("status:"+rechargeList.getStatusvalue()));
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"auto.alipay.success.");

	}
	
	/**
	 * 绑定支付宝帐号 
	 * @param autoAliPayRequest
	 * @return
	 */
	@RequestMapping(value = "/bind_alipay",method=RequestMethod.POST)
	@ResponseBody
	public  ApiResult bindAlipay(@RequestBody BindAlipayRequest bindAlipayRequest){
		if (bindAlipayRequest.isInvalid()){
			return new ApiResult(false, ResultStatusConstant.FAIL,"invalid.params.");
		}
		String uid = bindAlipayRequest.getUid();
		ApiUserVo appUserVo = apiUserService.findByUid(uid);
		if (ObjectUtil.equals(null, appUserVo)){
			return new ApiResult(false,ResultStatusConstant.BindAlipay.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		String alipayAccount = bindAlipayRequest.getAlipayAcount();
		UserVerified tempUserVerified = userVerifiedService.queryUserVerifiedByAliAccount(alipayAccount);
		if(!ObjectUtil.equals(null, tempUserVerified)) {
			return new ApiResult(false, ResultStatusConstant.BindAlipay.ALIPAY_ACCOUNT_EXIST,"alipay.account.exist.");
		}
		//绑定支付宝账号
		UserVerified userverified=securityInfoService.findByUserId(uid);
		userverified.setAlipayAccount(alipayAccount);
		userVerifiedService.update(userverified);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"bind.alipay.success.");
	}	
	
	
	
	/**
	 * 银行转账接口
	 * @param bankTransferRequest
	 * @return
	 */
	@RequestMapping(value = "/bank_transfer",method=RequestMethod.POST)
	@ResponseBody
	public  synchronized ApiResult bankTransfer(@RequestBody BankTransferRequest bankTransferRequest,HttpServletRequest request){
	
		if (bankTransferRequest.isInvalid()){
			return new ApiResult(false, ResultStatusConstant.FAIL,"invalid.params.");
		}
		
		Double money = bankTransferRequest.getMoney();
		if (new BigDecimal(money).compareTo(new BigDecimal(DataConstant.ONE))<DataConstant.ZERO){
			return new ApiResult(false,ResultStatusConstant.BankTransfer.MONEY_CANNOT_LESS_THAN_ONE,"money.cannot.less.than.one.");
		}
		
		String uid = bankTransferRequest.getUid();
		WUser wUser = apiUserService.get(uid);
		if (ObjectUtil.equals(null, wUser)){
			return new ApiResult(false,ResultStatusConstant.BankTransfer.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		String ip=IpUtils.getIpAddr(request);
		payService.insertEntity(Constant.Source.PGB,wUser,bankTransferRequest.getAbbreviation(),DataConstant.PAY_NO_PROCESSING,
				"",String.valueOf(money),ip,DataConstant.TRANSBANK_TYPE,bankTransferRequest.getSerialnum());
				
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"submit.success.");
	}	
	
}
