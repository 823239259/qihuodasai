package com.tzdr.cms.controller.opertionalconfig;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.operational.OperationalConfigService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.HttpClientUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.OperationalConfig;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 运营维护配置controller
 */
@Controller
@RequestMapping("/admin/config/banner")
public class BannerController extends BaseCmsController<OperationalConfig>{

	@Autowired
	private OperationalConfigService  operationalConfigService;
	
	@Override
	public BaseService<OperationalConfig> getBaseService() {
		return operationalConfigService;
	}
	
	public BannerController() {
		setResourceIdentity("sys:operationalConfig:banner");
	}
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		request.setAttribute("type",4);
		return ViewConstants.OpertinalConfigViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id,@RequestParam(value="type",required=false) String type) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		
		if (StringUtil.equals(fromType,Constants.ADD)){
			if(StringUtil.isNotBlank(type)){
				request.setAttribute("type",type);
			}
			return ViewConstants.OpertinalConfigViewJsp.BANNER_EDIT_VIEW;
		}
	
		if (StringUtil.equals(fromType,Constants.EDIT)){
			OperationalConfig operationalConfig = operationalConfigService.get(id);
			request.setAttribute("type",operationalConfig.getType());
			request.setAttribute("config",operationalConfig);
			return ViewConstants.OpertinalConfigViewJsp.BANNER_EDIT_VIEW;
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
	/**
	 * 刷新网站banner缓存数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/refreshWebHomePageBanner", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult auditPass(HttpServletRequest request) throws Exception{
			
		String applyUrls = ConfUtil.getContext("apply.urls");
		
		String[] urls = null;
		
		if(StringUtil.isNotBlank(applyUrls)){
			urls = applyUrls.split(";");
		}
		
		if(urls != null && urls.length > 0){
			for (String httpUrl : urls) {
				HttpClientUtils.httpRequest2Json(httpUrl);
			}
		}
		
		return new JsonResult("刷新成功！");
	}
	
	
	/**
	 * 刷新PGBbanner缓存数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/refreshPgbHomePageBanner", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult auditPassPGB(HttpServletRequest request) throws Exception{
			
		String applyUrls = ConfUtil.getContext("apply.urls.pgb");
		
		String[] urls = null;
		
		if(StringUtil.isNotBlank(applyUrls)){
			urls = applyUrls.split(";");
		}
		
		if(urls != null && urls.length > 0){
			for (String httpUrl : urls) {
				HttpClientUtils.httpRequest2Json(httpUrl);
			}
		}
		
		return new JsonResult("刷新成功！");
	}
}
