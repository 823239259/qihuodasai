package com.tzdr.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.cms.cpp.MockTradeAccountService;

import jodd.util.StringUtil;

@Controller
@RequestMapping(value = "/mock/trade")
public class MockTradeContoller {
	@Autowired
	public MockTradeAccountService mockTradeAccountService;
	/**
	 * 开通模拟账户
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/openMock")
	public ApiResult openMock(HttpServletRequest request,@RequestParam("username")String username,@RequestParam("password")String password){
		if(StringUtil.isBlank(username) || StringUtil.isBlank(password)){
			return new ApiResult(false,ResultStatusConstant.AUTH_PARAMS_ERROR,"param is null");
		}
		boolean flag = mockTradeAccountService.openMockAccount(username, password);
		if(flag){
			return new ApiResult(true,ResultStatusConstant.SUCCESS,"open mock success");
		}else{
			return new ApiResult(false,ResultStatusConstant.FAIL,"open mock fail");
		}
	}
	/**
	 * 修改模拟盘账户密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMock",method = RequestMethod.POST)
	public ApiResult updateMock(HttpServletRequest request,@RequestParam("username")String username,@RequestParam("password")String password){
		if(StringUtil.isBlank(username) || StringUtil.isBlank(password)){
			return new ApiResult(false,ResultStatusConstant.AUTH_PARAMS_ERROR,"param is null");
		}
		boolean flag = mockTradeAccountService.updateMockAccount(username, password);
		ApiResult apiResult = new ApiResult();
		if(flag){
			apiResult.setSuccess(false);return new ApiResult(true,ResultStatusConstant.SUCCESS,"update mock success");
		}else{
			return new ApiResult(false,ResultStatusConstant.FAIL,"update mock fail");
		}
	}
}
