package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.JsonResult;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 */
@Controller
@RequestMapping(value = "/admin")
public class LoginController {

	@RequestMapping(value = "login")
	public String login(HttpServletRequest request,Model model) {

		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {
			subject.logout();
		}

		Object loginException =  request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (ObjectUtil.equals(null, loginException)){
			SecurityUtils.getSubject().getSession().setTimeout(3600000);  
			return ViewConstants.ADMIN_LOGIN_VIEW;
		}
		
		JsonResult  errorResult = new JsonResult(false);
		String exceptionName = StringUtil.toString(loginException);
		if (StringUtil.equals(UnknownAccountException.class.getName(),exceptionName)){
			errorResult.setMessage(MessageUtils.message("user.not.exists"));
		}else if (StringUtil.equals(AuthenticationException.class.getName(),exceptionName)){
			errorResult.setMessage(MessageUtils.message("user.password.not.match"));
		}else if (StringUtil.equals(ExcessiveAttemptsException.class.getName(),exceptionName)){
			errorResult.setMessage(MessageUtils.message("user.password.retry.limit.exceed"));
		}else{
			errorResult.setMessage(MessageUtils.message("user.unknown.error"));
		}
		model.addAttribute("error", errorResult);
		return ViewConstants.ADMIN_LOGIN_VIEW;
	}

}
