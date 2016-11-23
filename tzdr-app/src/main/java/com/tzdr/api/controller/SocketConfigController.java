package com.tzdr.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.support.ApiResult;
import com.tzdr.business.service.socketconfig.SocketConfigService;
import com.tzdr.domain.web.entity.SocketConfig;

@Controller
@RequestMapping("/socket/config")
public class SocketConfigController {
	@Autowired
	private SocketConfigService socketConfigService;
	@RequestMapping(value = "/getVersions")
	@ResponseBody
	public ApiResult getVersion(HttpServletRequest request,HttpServletResponse response){
		String appVersion = request.getParameter("appVersions");
		ApiResult result = new ApiResult(false);
		List<SocketConfig> configs = socketConfigService.findSocketConfigByAppVersion(appVersion);
		if(configs != null && configs.size() > 0){
			result.setSuccess(true);
			result.setData(configs.get(0));
		}else{
			result.setSuccess(false);
		}
		return result;
	}
}