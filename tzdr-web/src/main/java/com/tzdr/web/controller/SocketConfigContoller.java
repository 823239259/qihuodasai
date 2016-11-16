package com.tzdr.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.socketconfig.SocketConfigService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.SocketConfig;
@Controller
@RequestMapping(value = "/socket/config")
public class SocketConfigContoller {
	@Autowired
	private SocketConfigService socketConfigService;
	@ResponseBody
	@RequestMapping(value = "/getVersion",method = RequestMethod.GET)
	public JsonResult getVersion(HttpServletRequest request,@RequestParam("appVersion") String appVersion){
		JsonResult jsonResult = new JsonResult();
		List<SocketConfig> config = socketConfigService.findSocketConfigByAppVersion(appVersion);
		if(config != null && config.size() > 0){
			jsonResult.setSuccess(true);
			jsonResult.appendData("data",config.get(0));
		}else{
			jsonResult.setSuccess(false);
		}
		return jsonResult;
	}
}
