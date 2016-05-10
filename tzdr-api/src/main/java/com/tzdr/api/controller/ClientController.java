package com.tzdr.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.PasswordUtils;
import com.tzdr.business.api.service.ApiInternationFutureService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.domain.api.vo.InternationFutureVo;
import com.tzdr.domain.web.entity.WUser;

/**
 * <B>说明: </B>此方法供C客户端端调用开发的部分接口
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController {

	@Autowired
	private WUserService   wUserService;

	
	@Autowired
	private ApiInternationFutureService   apiInternationFutureService;

	/**
	 * 客户端调用的登录接口
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	@ResponseBody 
	public ApiResult login(@RequestBody RequestObj requestObj,
			HttpServletRequest request,HttpServletResponse response){
		
		String loginName=requestObj.getLoginName();
		String password=requestObj.getPassword();
		
		if (StringUtil.isBlank(loginName)
				|| StringUtil.isBlank(password)){
			return new ApiResult(false,ResultStatusConstant.Login.LOGIN_INFO_ERROR,"user.info.not.complete.");
		}
		if (!PasswordUtils.validatePwd(password)){
			return new ApiResult(false,ResultStatusConstant.Login.PASSWORD_PATTERN_ERROR,"password.pattern.error.");
		}
		WUser wUser = wUserService.login(loginName, password); //登录
		if(ObjectUtil.equals(null, wUser)){   //判断是否登录成功
			return new ApiResult(false,ResultStatusConstant.FAIL,"login.fail.");
		}
		wUserService.updateWUserByUserId(wUser.getId(), IpUtils.getIpAddr(request));   //记录登录信息
		JSONObject  jsonObject = new JSONObject();
		jsonObject.put("uid",wUser.getId());
		String  name  = wUser.getUserVerified().getTname();
		jsonObject.put("name",StringUtil.isBlank(name)?loginName:name);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"login.success.",jsonObject);
	} 
	
	
	
	/**
	 * 获取用户账户信息
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accounts",method=RequestMethod.POST)
	@ResponseBody 
	public ApiResult accounts(@RequestBody RequestObj requestObj,HttpServletRequest request,HttpServletResponse response){
		String uid=requestObj.getUid();
		if (StringUtil.isBlank(uid)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"uid.is.null.");
		}
		List<InternationFutureVo> internationFutureVos = apiInternationFutureService.findByUid(uid);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"handle.success.",internationFutureVos);
	} 
}
