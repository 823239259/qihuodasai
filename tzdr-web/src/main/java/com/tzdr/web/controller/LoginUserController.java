package com.tzdr.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.WUser;

import jodd.util.Base64;

@Controller
@RequestMapping(value = "/login/user")
public class LoginUserController {
	@Autowired
	private WUserService wUserService;
	/**
	 * 获取用户登录信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAccount",method = RequestMethod.GET)
	public JsonResult getAccount(HttpServletRequest request,@RequestParam("mobile") String mobile){
		JsonResult result = new JsonResult(true);
		WUser user = wUserService.getWUserByMobile(Base64.decodeToString(mobile));
		result.appendData("data", user);
		return result;
	}
}
