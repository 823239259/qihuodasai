package com.tzdr.cms.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.socketconfig.SocketConfigService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.SocketConfig;

@Controller
@RequestMapping(value = "/admin/socket/config")
public class SocketConfigContoller extends BaseCmsController<SocketConfig>{
	@Autowired
	private SocketConfigService socketConfigService;
	@RequestMapping(value = "/list")
	public String list(){
		return ViewConstants.SocketConfig.LIST_LIVE;
	}
	@RequestMapping(value = "/doGetById",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doGetById(@RequestParam("id") String id){
		JsonResult resultJson  = new JsonResult();
		resultJson.setSuccess(true);
		resultJson.appendData("data", socketConfigService.get(id));
		return resultJson;
	}
	@RequestMapping(value = "/listData",method = RequestMethod.POST)
	@ResponseBody
	private Object listData(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,HttpServletRequest request){
		//判断是否具有查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		PageInfo<Object> pageInfo = socketConfigService.doGetSocketConfig(easyUiPage,searchParams);
		return new EasyUiPageData<Object>(pageInfo);
	}
	@ResponseBody
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public JsonResult save(SocketConfig config,HttpServletRequest request){
		Long long1 = new Date().getTime();
		config.setCreateTime(long1);
		socketConfigService.save(config);
		return new JsonResult(true);
	}
	@ResponseBody
	@RequestMapping(value = "/socketUpdate" , method = RequestMethod.POST)
	public JsonResult updates(SocketConfig socketConfig , HttpServletRequest request){
		Long long1 = new Date().getTime();
		SocketConfig config = socketConfigService.get(socketConfig.getId());
		config.setUpdateTime(long1);
		config.setAppVersion(socketConfig.getAppVersion());
		config.setIsModel(socketConfig.getIsModel());
		config.setSocketUrl(socketConfig.getSocketUrl());
		config.setSocketVersion(socketConfig.getSocketVersion());
		config.setSocketModelUrl(socketConfig.getSocketModelUrl());
		socketConfigService.update(config);
		return new JsonResult(true);
	}
	@ResponseBody
	@RequestMapping(value = "/socketDelete" , method = RequestMethod.POST)
	public JsonResult delete(@RequestParam("id") String id , HttpServletRequest request){
		socketConfigService.removeById(id);
		return new JsonResult(true);
	}
	@Override
	public BaseService<SocketConfig> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}
}
