package com.tzdr.api.controller;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.api.util.PasswordUtils;
import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.WUser;

/**
 * 找回密码 
 * @author zhouchen
 * 2016年3月22日 下午5:02:47
 */
@Controller
@RequestMapping(value = "/")
public class RetrievePwdController {
	
	@Autowired
	private SecurityCodeService securityCodeService;
	
	@Autowired
	private WUserService  wUserService;
	
	@Autowired
	private PasswordService  passwordService;
	
	@Autowired
	private SecurityInfoService securityInfoService;

	
	

	/**
	 * 忘记密码 更新操作
	 * @param mobile
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reset_password",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult forgetResetPassword(RequestObj requestObj,HttpServletRequest request){
		
		String mobile=requestObj.getMobile();
		String password=requestObj.getPassword();		
		
		if (StringUtil.isBlank(password) || StringUtil.isBlank(mobile)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.error.");
		}
		if (!PasswordUtils.validatePwd(password)){
			return new ApiResult(false,ResultStatusConstant.ForgetPwd.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		WUser wUser = wUserService.getWUserByMobile(mobile);
		if (ObjectUtil.equals(null,wUser)){
			return new ApiResult(false,ResultStatusConstant.ForgetPwd.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}	
		
		wUser.setPassword(passwordService.encryptPassword(password, wUser.getLoginSalt()));
		wUserService.updateUser(wUser);
		AuthUtils.clearCacheUser(wUser.getId());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"forget.password.update.success.");
	}
	/**
	 * 手机验证码验证
	 * @param request
	 * @param mobile
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reset_validation/code")
	public ApiResult resetValidationCode(HttpServletRequest request,@RequestParam("mobile")String mobile,@RequestParam("code") String code){
		ApiResult result = new ApiResult();
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
		result.setSuccess(true);
		return result;
	}
}
