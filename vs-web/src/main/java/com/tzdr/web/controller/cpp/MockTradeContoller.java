package com.tzdr.web.controller.cpp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.cpp.MockTradeAccountService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.UserSessionBean;

@RequestMapping(value = "/mock/trade")
@Controller
public class MockTradeContoller {
	@Autowired
	private MockTradeAccountService mockTradeAccountService;
	@Autowired
	private WUserService wUserService;
	@RequestMapping(value = "/openMock")
	@ResponseBody
	public JsonResult openMockTradeAccount(HttpServletRequest request,@RequestParam("username")String username,@RequestParam("password")String password){
		JsonResult resultJson = new JsonResult();
		boolean flag = mockTradeAccountService.openMockAccount(username, password);
		if(flag){
			resultJson.setMessage("模拟账号开户成功");
			resultJson.setSuccess(true);
		}else{
			resultJson.setMessage("模拟账号开户失败");
			resultJson.setSuccess(false);
		}
		return resultJson;
	}
	/**
	 * 修改模拟盘账户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMock",method = RequestMethod.POST)
	public JsonResult updateMockTradeAccount(HttpServletRequest request,@RequestParam("password")String password,@RequestParam(value = "username",required = false)String username){
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult resultJson = new JsonResult();
		boolean flag = mockTradeAccountService.updateMockAccount(userSessionBean == null ? username : userSessionBean.getMobile(), password);
		if(flag){
			resultJson.setMessage("模拟账号修改成功");
			resultJson.setSuccess(true);
		}else{
			resultJson.setMessage("模拟账号修改失败");
			resultJson.setSuccess(false);
		}
		return resultJson;
	}
	@ResponseBody
	@RequestMapping(value = "/getAllUser")
	public JsonResult getAllUser(HttpServletRequest request){
		List<WUser> wUsers = wUserService.getAll();
		JsonResult resultJson = new JsonResult(true);
		resultJson.appendData("data", wUsers);
		return resultJson;
	}
	@ResponseBody
	@RequestMapping(value = "/userOpenMock")
	public JsonResult userOpenMock(HttpServletRequest request,@RequestParam("strs") String str){
		String[] strs = str.split(",");
		mockTradeAccountService.userOpenMock(strs);
		return new JsonResult(true);
	}
}
