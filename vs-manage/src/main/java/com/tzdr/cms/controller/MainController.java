package com.tzdr.cms.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.resource.ResourceService;
import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.resource.Menu;
import com.tzdr.domain.cms.entity.user.User;


@Controller
@RequestMapping("/admin")
public class MainController 
{
	private static Logger log = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService passwordService;
	
	@RequestMapping(value = "/main")
	public String toMain(HttpServletRequest request,Model model) throws Exception 
	{
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection currentUserName = subject.getPrincipals();
		String userName = StringUtil.toString(currentUserName);
		User user  = userService.findByUsername(userName);
		List<Menu>  menus  = resourceService.findMenus(user);
		model.addAttribute("menus", menus);
		model.addAttribute("user", user);
		return ViewConstants.MAIN_VIEW;
	}
	
	@RequestMapping(value = "/updatePasswod")
	@ResponseBody
	public JsonResult updatePasswod(HttpServletRequest request,User user,
			@RequestParam("newPasswod") String newPasswod) throws Exception 
	{
		if (StringUtil.isBlank(newPasswod)){
			return  new JsonResult(Boolean.FALSE,MessageUtils.message("user.update.password.newpassword.null"));
		}
		
		User dbUser = userService.get(user.getId());
		if (ObjectUtil.equals(dbUser, null)){
			return  new JsonResult(Boolean.FALSE,MessageUtils.message("user.update.password.userinfo.not.exsit"));
		}
		
		String oldPasswod = passwordService.encryptPassword(dbUser.getRealname(), user.getPassword(),dbUser.getSalt());
		if (!StringUtil.equals(oldPasswod,dbUser.getPassword())){
			return  new JsonResult(Boolean.FALSE,MessageUtils.message("user.update.password.oldpasswor.error"));
		}
		userService.changePassword(dbUser, newPasswod);
		return  new JsonResult(MessageUtils.message("user.update.password.success"));
		
	}
	
	
}
