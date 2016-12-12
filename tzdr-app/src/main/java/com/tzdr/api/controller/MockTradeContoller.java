package com.tzdr.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.support.ApiResult;
import com.tzdr.business.cms.cpp.MockTradeAccountService;

@Controller
@RequestMapping(value = "/mock/trade")
public class MockTradeContoller {
	@Autowired
	public MockTradeAccountService mockTradeAccountService;
	/**
	 * 開通模擬賬號
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/openMock")
	public ApiResult openMock(HttpServletRequest request,@RequestParam("username")String username,@RequestParam("password")String password){
		ApiResult apiResult = new ApiResult();
		boolean flag = mockTradeAccountService.openMockAccount(username, password);
		if(flag){
			apiResult.setSuccess(true);
			apiResult.setMessage("模拟账号开户成功");
		}else{
			apiResult.setSuccess(false);
			apiResult.setMessage("模拟账号开户失败");
		}
		return apiResult;
	}
	/**
	 * 修改模拟盘账户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMock",method = RequestMethod.POST)
	public ApiResult updateMock(HttpServletRequest request,@RequestParam("username")String username,@RequestParam("password")String password){
		boolean flag = mockTradeAccountService.updateMockAccount(username, password);
		ApiResult apiResult = new ApiResult();
		if(flag){
			apiResult.setMessage("修改账号成功!");
			apiResult.setSuccess(false);
		}else{
			apiResult.setMessage("修改账号失败!");
			apiResult.setSuccess(false);
		}
		return apiResult;
	}
}
