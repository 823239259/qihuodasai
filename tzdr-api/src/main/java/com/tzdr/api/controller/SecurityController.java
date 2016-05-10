package com.tzdr.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.PasswordRequest;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.PasswordUtils;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.PgbSMSSender;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <B>说明: </B>用户安全信息类接口
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/security")
public class SecurityController {

	@Autowired
	private ApiUserService  apiUserService;
	
	@Autowired
	private SecurityCodeService securityCodeService;
	
	@Autowired
	private WUserService  wUserService;
	
	@Autowired
	private PasswordService  passwordService;
	
	@Autowired
	private SecurityInfoService securityInfoService;
	
	@Autowired
	private DataMapService dataMapService;
	
	/**
	 * 系统发送通知短信统一接口
	 * @param moblie 手机号码
	 * @param type   发送类型：
	 *    1：注册验证码短信
	 *    2：忘记密码短信
	 *    3: 修改绑定手机短信
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/send_sms",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult sendSms(@RequestBody RequestObj requestObj,HttpServletRequest request) {
		
		String mobile=requestObj.getMobile();
		int type=requestObj.getType();
		if (!RequestUtils.isMobileNum(mobile)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"mobile.parrten.error.");
		}
		
		//查看当前手机号码 上次发送成功时间 是否在一分钟
		SecurityCode oldSecurityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if (!ObjectUtil.equals(null,oldSecurityCode)){
			if (Dates.getCurrentLongDate()-oldSecurityCode.getCreatedate()<DataConstant.SMS_SEND_INTERVAL_TIME){
				return new ApiResult(false,ResultStatusConstant.SendSms.FREQUENT_OPERATION,"one.minute.unable.repeat.send.");
			}
		}
				
		// 短信发送次数
		int sendTimes = ObjectUtil.equals(null,DataConstant.SMS_LIMIT_MAPS.get(mobile))?DataConstant.ZERO:DataConstant.SMS_LIMIT_MAPS.get(mobile);
		ApiUserVo appUserVo = apiUserService.findByMobile(mobile);
		Map<String,String> smsParams= new HashMap<String,String>();  //创建短信动态参数集合 
		String template = "ihuyi.verification.code.template";
		if (DataConstant.SEND_SMS_TYPE_REGIST==type){
			if (!ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_EXIST,"mobile.exist.");
			}
			if (sendTimes>=DataConstant.SMS_LIMIT_NUMBER){
				return new ApiResult(false,ResultStatusConstant.FAIL,"over.max.limit.number.");
			}
			template = "ihuyi.verification.signin.code.template";
			
			smsParams.put("module",DataConstant.SEND_SMS_TYPE_REGIST_MODULE);
			DataConstant.SMS_LIMIT_MAPS.put(mobile,sendTimes+DataConstant.ONE);
		}
		

		if (DataConstant.SEND_SMS_TYPE_FORGET_PWD==type){
			if (ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_NOT_EXIST,"mobile.not.exist.");
			}		
			smsParams.put("module", DataConstant.SEND_SMS_TYPE_FORGET_PWD_MODULE);
		}
		
		
		if (DataConstant.SEND_SMS_TYPE_UPDATE_PHONE==type){
			if (ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_NOT_EXIST,"mobile.not.exist.");
			}		
			smsParams.put("module", DataConstant.SEND_SMS_TYPE_UPDATE_PHONE_MODULE);
		}
		
		if (DataConstant.SEND_SMS_TYPE_FORGET_WITHDRAW_PWD==type){
			if (ObjectUtil.equals(null, appUserVo)){
				return new ApiResult(false,ResultStatusConstant.SendSms.MOBILE_NOT_EXIST,"mobile.not.exist.");
			}		
			smsParams.put("module", DataConstant.SEND_SMS_TYPE_FORGET_WITHDRAW_PWD_MODULE);
		}
		
		if (CollectionUtils.isEmpty(smsParams)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"send.fail.");
		}
		
		//保存验证码信息
		String randomCode = RandomCodeUtil.randStr(6);   //生成6为验证码
		SecurityCode securityCode=new SecurityCode(mobile,Dates.getCurrentLongDate(), randomCode);
		securityCodeService.saveSecurityCode(securityCode,mobile);  //保存验证码信息
		
		smsParams.put("code", randomCode);
		// 获取短信通道
		int smsChannel = ((DataConstant.SEND_SMS_TYPE_REGIST == type) ? dataMapService.getPgbSmsContentRegister() : dataMapService.getPgbSmsContentOthers());
		
		if (!PgbSMSSender.getInstance().sendByTemplate(smsChannel, mobile, template, smsParams)) { // 判断短信发送是否成功
			return new ApiResult(false, ResultStatusConstant.FAIL, "send.fail.");
		}
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"send.success.");
	}
	
	/**
	 * 系统校验短信验证码统一接口
	 * @param mobile
	 * @param type
	 * 校验类型
	 * 1：忘记密码时校验验证码
	 * 2：修改绑定手机校验原手机验证码
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validate_code",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult validateCode(@RequestBody RequestObj requestObj,HttpServletRequest request) {

		String mobile=requestObj.getMobile();
		int type=requestObj.getType();
		String code=requestObj.getCode();
		
		if (DataConstant.VALIDATE_CODE_TYPE_FORGET_PWD != type
				&& DataConstant.VALIDATE_CODE_TYPE_UPDATE_PHONE != type
				&& DataConstant.VALIDATE_CODE_TYPE_FORGET_WITHDRAW_PWD !=type)
		{
			return new ApiResult(false,ResultStatusConstant.FAIL,"validate.fail.");
		}
		
		if (!RequestUtils.isMobileNum(mobile)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"mobile.parrten.error.");
		}
		ApiUserVo appUserVo = apiUserService.findByMobile(mobile);
		if (ObjectUtil.equals(null, appUserVo)){
			return new ApiResult(false,ResultStatusConstant.ValidateCode.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}	
		
		SecurityCode securityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if (ObjectUtil.equals(null,securityCode) 
				|| !StringUtil.equals(code, securityCode.getSecurityCode())){
			return new ApiResult(false,ResultStatusConstant.ValidateCode.ERROR_CODE,"error.code.");
		}
		
		if((Dates.getCurrentLongDate()-securityCode.getCreatedate()) > DataConstant.VALIDATE_CODE_INVALID_TIME){ 
			//判断验证码是否失效
			return new ApiResult(false,ResultStatusConstant.ValidateCode.INVALID_CODE,"invalid.code.");
		}
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"validate.success.");
	}
	
	

	/**
	 * 忘记密码 更新操作
	 * @param mobile
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forget_reset_password",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult forgetResetPassword(@RequestBody RequestObj requestObj,HttpServletRequest request){
		
		String mobile=requestObj.getMobile();
		String password=requestObj.getPassword();		
		String code=requestObj.getCode();
		
		if (StringUtil.isBlank(password) || StringUtil.isBlank(mobile) || StringUtil.isBlank(code)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");

		}
		
		if (!PasswordUtils.validatePwd(password)){
			return new ApiResult(false,ResultStatusConstant.ForgetPwd.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		
		WUser wUser = wUserService.getWUserByMobile(mobile);
		if (ObjectUtil.equals(null,wUser)){
			return new ApiResult(false,ResultStatusConstant.ForgetPwd.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}	
		
		
		SecurityCode securityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if (ObjectUtil.equals(null,securityCode) 
				|| !StringUtil.equals(code, securityCode.getSecurityCode())){
			return new ApiResult(false,ResultStatusConstant.ForgetPwd.ERROR_CODE,"error.code.");
		}
		
		if((Dates.getCurrentLongDate()-securityCode.getCreatedate()) > DataConstant.VALIDATE_CODE_INVALID_TIME){ 
			//判断验证码是否失效
			return new ApiResult(false,ResultStatusConstant.ForgetPwd.INVALID_CODE,"invalid.code.");
		}		
		
		wUser.setPassword(passwordService.encryptPassword(password, wUser.getLoginSalt()));
		wUserService.updateUser(wUser);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"forget.password.update.success.");
	}
	
	
	/**
	 * 修改密码
	 * @param uid
	 * @param password
	 * @param newPassword
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_password",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult update_password(@RequestBody RequestObj requestObj,HttpServletResponse response,HttpServletRequest request){
		
		String uid=requestObj.getUid();
		String password=requestObj.getPassword();
		String newPassword=requestObj.getNewPassword();
		
		if (StringUtil.isBlank(uid) || StringUtil.isBlank(password)
				|| StringUtil.isBlank(newPassword)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"input.info.not.complete.");
		}
		
		if (!PasswordUtils.validatePwd(password) || !PasswordUtils.validatePwd(newPassword)){
			return new ApiResult(false,ResultStatusConstant.UpdatePwd.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		
		WUser wuser = wUserService.get(uid);
		if (ObjectUtil.equals(null, wuser)){
			return new ApiResult(false,ResultStatusConstant.UpdatePwd.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		String userpwd=wuser.getPassword();
		String pwd=passwordService.encryptPassword(password, wuser.getLoginSalt());
		if (!StringUtil.equals(userpwd, pwd)){
			return new ApiResult(false,ResultStatusConstant.UpdatePwd.ERROR_OLD_PASSWORD,"error.old.password.");
		}
		
		String newpwd=passwordService.encryptPassword(newPassword, wuser.getLoginSalt());
		UserVerified userverified=securityInfoService.findByUserId(wuser.getId());
		String drawMoneyPassword = userverified.getDrawMoneyPwd();
		if (StringUtil.isNotBlank(drawMoneyPassword) 
				&& StringUtil.equals(newpwd,drawMoneyPassword)){
			return new ApiResult(false,ResultStatusConstant.UpdatePwd.SAME_WITH_DRAWMONEY_PASSWORD,"same.with.drawmoney.password");
		}
		securityInfoService.updatUserPwd(wuser,newPassword);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"update.password.success.");
	}
	
	
	
	/**
	 * 身份认证
	 * @param uid
	 * @param name
	 * @param card
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/validate_card",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult validateCard(@RequestBody RequestObj requestObj,
			HttpServletRequest request,HttpServletResponse response){
		
		String uid=requestObj.getUid();
		String name=requestObj.getName();
		String card=requestObj.getCard();
		
		if (StringUtil.isBlank(uid) || StringUtil.isBlank(name) || StringUtil.isBlank(card)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"input.info.not.complete.");
		}
		
		if(!ValidatorUtil.verifycard(card)){
			return new ApiResult(false,ResultStatusConstant.ValidateCard.CARD_PATTERN_ERROR,"card.pattern.error.");
		}
		
		UserVerified usercard=securityInfoService.findByIdCard(card);
		if (!ObjectUtil.equals(null, usercard)){
			return new ApiResult(false,ResultStatusConstant.ValidateCard.CARD_EXIST,"card.exist.");
		}
		
		UserVerified userverified=securityInfoService.findByUserId(uid);
		if (ObjectUtil.equals(null, userverified)){
			return new ApiResult(false,ResultStatusConstant.ValidateCard.USER_INFO_NOT_EXIST,"user.info.not.exist");
		}
		
		if (StringUtil.isNotBlank(userverified.getIdcard()) 
				&& StringUtil.isNotBlank(userverified.getTname())){
			return new ApiResult(false,ResultStatusConstant.ValidateCard.ALREADY_AUTHENTICATION,"already.authentication.");
		}
		
		Integer validatecount=userverified.getValidatenum()==null?0:userverified.getValidatenum();
		if (validatecount >= DataConstant.VALIDATE_CARD_MAX_TIME){
			userverified.setStatus(DataConstant.Idcard.NOPASS);//验证失败
			securityInfoService.update(userverified);
			return new ApiResult(false,ResultStatusConstant.FAIL,"validate.time.over.limited.");
		}

		//开始验证
		boolean flag=securityInfoService.vilidateCard(card, name);
		if (flag){
			userverified.setIdcard(card);
			userverified.setTname(name);
			userverified.setStatus(DataConstant.Idcard.NOCOMPLETE);//验证未上传照片
			securityInfoService.update(userverified);
			return new ApiResult(true,ResultStatusConstant.SUCCESS,"validate.success.");
		}
		// 认证失败
		validatecount+=1;
		userverified.setValidatenum(validatecount);
		if(validatecount>=3){
			userverified.setStatus(DataConstant.Idcard.NOPASS);//验证失败
		}
		securityInfoService.update(userverified);
		return new ApiResult(false,ResultStatusConstant.FAIL,"validate.fail.");
	}
	
	/**
	 * 修改绑定手机
	 * @param uid
	 * @param moblie
	 * @param code
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_phone",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult updatePhone(@RequestBody RequestObj requestObj,HttpServletResponse response,HttpServletRequest request){
		
		String uid=requestObj.getUid();
		String mobile=requestObj.getMobile();
		String code=requestObj.getCode();
		
		if (StringUtil.isBlank(uid) || StringUtil.isBlank(mobile) || StringUtil.isBlank(code)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");

		}
		
		if (!RequestUtils.isMobileNum(mobile)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"mobile.parrten.error.");
		}
		
		WUser user = securityInfoService.getUsesrbyId(uid);
		if (ObjectUtil.equals(null, user)){
			return new ApiResult(false,ResultStatusConstant.UpdatePhone.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}	
		
		WUser wuser=securityInfoService.getUsesrbyMobile(mobile);
		if (!ObjectUtil.equals(null, wuser)){
			return new ApiResult(false,ResultStatusConstant.UpdatePhone.MOBILE_EXIST,"user.is.exist.");
		}
		
		
		SecurityCode securityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if (ObjectUtil.equals(null,securityCode) 
				|| !StringUtil.equals(code, securityCode.getSecurityCode())){
			return new ApiResult(false,ResultStatusConstant.UpdatePhone.ERROR_CODE,"error.code.");
		}
		
		if((Dates.getCurrentLongDate()-securityCode.getCreatedate()) > DataConstant.VALIDATE_CODE_INVALID_TIME){ 
			//判断验证码是否失效
			return new ApiResult(false,ResultStatusConstant.UpdatePhone.INVALID_CODE,"invalid.code.");
		}
		
		 //更新手机号
		user.setMobile(mobile);
		this.securityInfoService.updatUserMobile(user,mobile);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"update.success.");
	}

	
	/**
	 * 设置用户的提现密码
	 * @param drawPwdRequest
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/set_withdraw_pwd",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult setWithdrawPwd(@RequestBody PasswordRequest drawPwdRequest,HttpServletResponse response,HttpServletRequest request){
		String uid = drawPwdRequest.getUid();
		String password = drawPwdRequest.getPassword();
		if (StringUtil.isBlank(password) || StringUtil.isBlank(uid)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		if (!PasswordUtils.validatePwd(password)){
			return new ApiResult(false,ResultStatusConstant.SetWithDrawPwd.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		
		UserVerified userverified=securityInfoService.findByUserId(uid);
		WUser user = securityInfoService.getUsesrbyId(uid);
		if (ObjectUtil.equals(null, user) || ObjectUtil.equals(null, userverified)){
			return new ApiResult(false,ResultStatusConstant.SetWithDrawPwd.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		//查询是否和登陆密码相同
		String drawpwd=passwordService.encryptPassword(password, user.getLoginSalt());
		if (StringUtil.equals(drawpwd,user.getPassword())){
			return new ApiResult(false,ResultStatusConstant.SetWithDrawPwd.SAME_WITH_LOGIN_PASSWORD,"same.with.login.password.");
		}
		
		this.securityInfoService.updatUserMoneyPwd(password,user,userverified);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"setting.success.");

	}
	
	/**
	 * 更新用户的提现密码
	 * @param drawPwdRequest
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_withdraw_pwd",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult updateWithdrawPwd(@RequestBody PasswordRequest drawPwdRequest,HttpServletResponse response,HttpServletRequest request){
		String uid = drawPwdRequest.getUid();
		String password = drawPwdRequest.getPassword();
		String newPassword = drawPwdRequest.getNewPassword();
		if (StringUtil.isBlank(password) || StringUtil.isBlank(uid) || StringUtil.isBlank(newPassword)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		if (!PasswordUtils.validatePwd(password) || !PasswordUtils.validatePwd(newPassword)){
			return new ApiResult(false,ResultStatusConstant.UpdateWithDrawPwd.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		
		UserVerified userverified=securityInfoService.findByUserId(uid);
		WUser user = securityInfoService.getUsesrbyId(uid);
		if (ObjectUtil.equals(null, user) || ObjectUtil.equals(null, userverified)){
			return new ApiResult(false,ResultStatusConstant.UpdateWithDrawPwd.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		String oldPwd =this.securityInfoService.getBuildPwd(password, user);
		if (!StringUtil.equals(oldPwd,userverified.getDrawMoneyPwd())){
			return new ApiResult(false,ResultStatusConstant.UpdateWithDrawPwd.OLD_PASSWORD_ERROR,"old.password.error.");
		}
		
		
		//查询是否和登陆密码相同
		String drawpwd=passwordService.encryptPassword(newPassword, user.getLoginSalt());
		if (StringUtil.equals(drawpwd,user.getPassword())){
			return new ApiResult(false,ResultStatusConstant.UpdateWithDrawPwd.SAME_WITH_LOGIN_PASSWORD,"same.with.login.password.");
		}
		
		if (StringUtil.equals(oldPwd, drawpwd)){
			return new ApiResult(false,ResultStatusConstant.UpdateWithDrawPwd.SAME_WITH_OLD_PASSWORD,"same.with.old.password.");
		}
		
		this.securityInfoService.updatUserMoneyPwd(newPassword,user,userverified);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"update.success.");

	}
}
