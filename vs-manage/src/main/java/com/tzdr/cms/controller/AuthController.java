package com.tzdr.cms.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.auth.Auth;

@Controller
@RequestMapping("/admin/auth")
public class AuthController extends BaseCmsController<Auth> {
	private static Logger log = LoggerFactory
			.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;
	

	@Override
	public BaseService<Auth> getBaseService() {
		return authService;
	}
	
	public AuthController() {
		setResourceIdentity("sys:rolemanager:grantuser");
	}
	@RequestMapping(value="saveAuth")
	@ResponseBody
	public JsonResult  saveAuth(HttpServletRequest request,@RequestParam("roleIds") String roleIds,
			@RequestParam(value="userIds") String userIds){
		
		  //新增权限
		  if (permissionList != null) {
	            this.permissionList.assertHasCreatePermission();
	       }
		  
		if (StringUtil.isBlank(userIds) || StringUtil.isBlank(roleIds)){
			log.debug(MessageUtils.message("auth.save.param.null"));
			return new JsonResult(Boolean.FALSE, MessageUtils.message("auth.save.param.null"));
		}
	
		Set<Long> roleIdSet = WebUtil.convertStringToSet(roleIds);
		Set<Long> UserIdSet = WebUtil.convertStringToSet(userIds);
		
		authService.addUserAuth(UserIdSet,roleIdSet);
		return new JsonResult(MessageUtils.message("auth.save.success"));
		
		
	}
}
