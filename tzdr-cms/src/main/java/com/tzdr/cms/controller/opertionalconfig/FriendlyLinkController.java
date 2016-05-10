package com.tzdr.cms.controller.opertionalconfig;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tzdr.business.service.operational.OperationalConfigService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.OperationalConfig;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 运营维护配置controller
 */
@Controller
@RequestMapping("/admin/config/friendlyLink")
public class FriendlyLinkController extends BaseCmsController<OperationalConfig>{

	@Autowired
	private OperationalConfigService  operationalConfigService;
	
	@Override
	public BaseService<OperationalConfig> getBaseService() {
		return operationalConfigService;
	}
	
	public FriendlyLinkController() {
		setResourceIdentity("sys:operationalConfig:friendlyLink");
	}
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		setResourceIdentity("sys:operationalConfig:friendlyLink");
		request.setAttribute("type",1);
		return ViewConstants.OpertinalConfigViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.OpertinalConfigViewJsp.FRIENDLY_LINK_EDIT_VIEW;
		}
	
		if (StringUtil.equals(fromType,Constants.EDIT)){
			request.setAttribute("config",operationalConfigService.get(id));
			return ViewConstants.OpertinalConfigViewJsp.FRIENDLY_LINK_EDIT_VIEW;
		}
		
		return ViewConstants.ERROR_VIEW;
	}
}
