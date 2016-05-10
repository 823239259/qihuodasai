package com.tzdr.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.api.util.TokenUtils;
import com.tzdr.business.api.service.ApiTokenService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.Md5Utils;
import com.tzdr.domain.api.entity.ApiToken;


/**
 * 
 * <B>说明: </B>token 管理控制器
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/token")
public class TokenController {

	@Autowired
	private ApiTokenService  apiTokenService;
	
	
	/**
	 * 创建token接口
	 * @param requestObj
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult create(@RequestBody RequestObj requestObj,HttpServletRequest request) {
		String userName=requestObj.getLoginName();
		String password=requestObj.getPassword();
		if (StringUtil.isBlank(userName) || StringUtil.isBlank(password)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"create.fail;params.is.null.");
		}
		String source = RequestUtils.getSource(request);
		if (StringUtil.isBlank(source)){
			return new ApiResult(false,ResultStatusConstant.NO_SOURCE,"request.not.found.source.");
		}
		
		String ipAddr = RequestUtils.getIp(request);
		System.out.println("-------------------------------------------"+ipAddr+"-------------------------------------------");
		ApiToken appToken =apiTokenService.getTokenByUserName(userName, ipAddr,source);
		if (ObjectUtil.equals(null,appToken) 
				|| !StringUtil.equals(Md5Utils.hash(appToken.getPassword()),password)){
			return new ApiResult(false,ResultStatusConstant.TokenCreate.AUTH_FAIL,"auth.fail.");
		}
		
		String token = TokenUtils.createToken(appToken);
		if (StringUtil.isBlank(token)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"create.fail.");
		}
		appToken.setToken(token);
		appToken =  apiTokenService.create(appToken);
		
		Map<Object,Object> data = Maps.newHashMap();
		data.put("token", appToken.getToken());
		data.put("invalidTime", appToken.getInvalidTime());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"request.success",data);
		
	}
	
	
	/**
	 * 获取token
	 * @param request
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/query",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult query(@RequestBody RequestObj requestObj,HttpServletRequest request) {
		String userName=requestObj.getLoginName();
		String password=requestObj.getPassword();
		if (StringUtil.isBlank(userName) || StringUtil.isBlank(password)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"create.fail;params.is.null.");
		}
		String source = RequestUtils.getSource(request);
		if (StringUtil.isBlank(source)){
			return new ApiResult(false,ResultStatusConstant.NO_SOURCE,"request.not.found.source.");
		}
		
		String ipAddr = RequestUtils.getIp(request);
		ApiToken appToken =apiTokenService.getTokenByUserName(userName, ipAddr,source);
		if (ObjectUtil.equals(null,appToken) 
				|| !StringUtil.equals(Md5Utils.hash(appToken.getPassword()),password)){
			return new ApiResult(false,ResultStatusConstant.TokenCreate.AUTH_FAIL,"auth.fail.");
		}
			
		long currentTime = Dates.getCurrentLongDate();
		if(currentTime > appToken.getInvalidTime()){
			return new ApiResult(false,ResultStatusConstant.TOKEN_INVALID,"token.invalid.");
		}
		
		Map<Object,Object> data = Maps.newHashMap();
		data.put("token", appToken.getToken());
		data.put("invalidTime", appToken.getInvalidTime());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"request.success",data);
	}

}
