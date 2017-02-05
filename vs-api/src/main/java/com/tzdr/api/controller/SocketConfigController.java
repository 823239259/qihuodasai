package com.tzdr.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tzdr.api.constants.ResultStatusConstant;
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
		String version = request.getParameter("appVersions");
		List<SocketConfig> configs = socketConfigService.findSocketConfigByAppVersion(version);
		if(configs != null && configs.size() > 0){
			return new ApiResult(true,ResultStatusConstant.SUCCESS,"success",configs.get(0));
		}else{
			return new ApiResult(false,ResultStatusConstant.FAIL,"Didn't get to the data");
		}
	}
}