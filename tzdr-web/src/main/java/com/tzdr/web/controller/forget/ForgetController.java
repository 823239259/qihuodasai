package com.tzdr.web.controller.forget;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.cpp.MockTradeAccountService;
import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.utils.Md5Utils;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.SecurityCode;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.ViewConstants;

/**
* @Description: TODO(找回密码信息业务管理控制层)
* @ClassName: ForgetController
* @author wangpinqun
* @date 2014年12月27日 下午4:36:54
 */
@Controller
@RequestMapping("/")
public class ForgetController{
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ForgetController.class);

	@Autowired
	private WUserService wUserService;

	@Autowired
	private SecurityCodeService securityCodeService;

	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private DataMapService dataMapService;
	
	@Autowired
	private MockTradeAccountService mockTradeAccountService;
	
	/**
	* @Description: TODO(访问找回密码相关页面)
	* @Title: forgetPW
	* @param step      当前访问第几个页面
	* @param token     设置密钥
	* @param mobile    手机号码
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value = "/forgetpw")
	public String  forgetPW(Integer step,String token,String mobile,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		//忘记密码
//		if(step == null || 1 == step){ //验证码
//			return ViewConstants.ForgetViewJsp.FORGETONE_VIEW;
//		}else if(2 == step){ //设置密码
//			modelMap.put("token", token);
//			modelMap.put("mobile", mobile);
//			return ViewConstants.ForgetViewJsp.FORGETTWO_VIEW;
//		}else{  //密码设置成功
//			return ViewConstants.ForgetViewJsp.FORGETTHREE_VIEW;
//		}
		return ViewConstants.ForgetViewJsp.FORGET_TZDR_VIEW;
	}
	
	/**
	* @Description: TODO(下方找回密码短信验证码信息)
	* @Title: sendPasswordMobileCode
	* @param mobile    手机号码
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @return JsonResult    返回类型
	 */
	@RequestMapping(value = "/send_password_mobile_code")
	@ResponseBody
	public JsonResult sendPasswordMobileCode(String mobile,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		if(wUserService.getWUserByMobile(mobile) == null){ //判断手机号码是否已存在
			jsonResult.setMessage("mobileNotExist");
			return jsonResult;
		}
		String randomCode = RandomCodeUtil.randStr(6);   //生成6为验证码
		Map<String,String> smsParams= new HashMap<String,String>();  //创建短信动态参数集合 
		smsParams.put("module", "忘记密码");
		smsParams.put("code", randomCode);
		if(!SMSSender.getInstance().sendByTemplate(dataMapService.getSmsContentOthers(), mobile, "ihuyi.verification.code.template", smsParams)){ //判断短信发送是否成功
			jsonResult.setMessage("sendMobileCodeFail");
			return jsonResult;
		}
		SecurityCode securityCode = new SecurityCode();  //创建验证对象
		securityCode.setMobile(mobile);            
		Long createdate = new Date().getTime()/1000;     //生成验证码时间
		securityCode.setCreatedate(createdate);
		securityCode.setSecurityCode(randomCode); 
		securityCodeService.saveSecurityCode(securityCode,mobile);  //保存验证码信息
		Map<Object, Object> tokenParams = new HashMap<Object, Object>();
		tokenParams.put("token", Md5Utils.hash(randomCode+mobile));
		jsonResult.setData(tokenParams);
		return jsonResult;
	}
	
	/**
	* @Description: TODO(验证找回密码短信验证码)
	* @Title: verifyMobileCode
	* @param token     找回密码短信验证码的密钥
	* @param mobile    手机号码
	* @param code      验证码
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping( value = "/verify_mobile_code")
	@ResponseBody
	public JsonResult verifyMobileCode(String token,String mobile,String code,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		if(wUserService.getWUserByMobile(mobile) == null){ //判断手机号码是否已存在
			jsonResult.setMessage("mobileNotExist");
			return jsonResult;
		}
		SecurityCode securityCode = securityCodeService.getSecurityCodeByMobile(mobile);  //获取验证码信息
		if(securityCode == null || StringUtil.isBlank(token) ||StringUtil.isBlank(code) || !code.equals(securityCode.getSecurityCode()) || !token.equals(Md5Utils.hash(securityCode.getSecurityCode()+mobile))){   //判断验证码是否正确
			jsonResult.setMessage("codeError");
			return jsonResult;
		}else if((new Date().getTime()/1000)-(securityCode.getCreatedate()) > 5*60){  //判断验证码是否失效
			jsonResult.setMessage("codeTimeOut");
			return jsonResult;
		}
		WUser wUser = wUserService.getWUserByMobile(mobile);
		Map<Object, Object> tokenParams = new HashMap<Object, Object>();
		tokenParams.put("token", Md5Utils.hash(wUser.getId()+mobile));
		jsonResult.setData(tokenParams);
		return jsonResult;
	}
	
	/**
	* @Description: TODO(设置密码)
	* @Title: updatePassword  
	* @param mobile   手机号码
	* @param token    设置密码密钥
	* @param password 密码
	* @param modelMap
	* @param request
	* @param response
	* @return JsonResult    返回类型
	 */
	@RequestMapping( value = "/update_password")
	@ResponseBody
	public JsonResult updatePassword(final String mobile,String token,final String password,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		JsonResult  jsonResult = new JsonResult(true);
		WUser wUser = wUserService.getWUserByMobile(mobile);
		if(wUser == null || StringUtil.isBlank(token) || !token.equals(Md5Utils.hash(wUser.getId()+mobile))){  //判断token是否是当前修改的帐号
			jsonResult.setMessage("setPasswordFail");
			return jsonResult;
		}
		wUser.setPassword(passwordService.encryptPassword(password, wUser.getLoginSalt()));
		wUserService.updateUser(wUser);
		new Thread(new Runnable() {
			@Override
			public void run() {
				mockTradeAccountService.openMockAccount(mobile, password);
			}
		}).start();
		return jsonResult;
	}
}
