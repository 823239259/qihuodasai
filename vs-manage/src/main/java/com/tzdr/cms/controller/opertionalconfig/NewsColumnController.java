package com.tzdr.cms.controller.opertionalconfig;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
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
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.OperationalConfig;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 运营维护配置controller
 */
@Controller
@RequestMapping("/admin/config/newsColumn")
public class NewsColumnController extends BaseCmsController<OperationalConfig>{

	@Autowired
	private OperationalConfigService  operationalConfigService;
	
	@Override
	public BaseService<OperationalConfig> getBaseService() {
		return operationalConfigService;
	}
	
	public NewsColumnController() {
		setResourceIdentity("sys:operationalConfig:newsColumn");
	}
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		//查看
		 if (permissionList != null) {
	           this.permissionList.assertHasViewPermission();
	     }
		request.setAttribute("type",2);
		return ViewConstants.OpertinalConfigViewJsp.LIST_VIEW;
	}
	
	
	@RequestMapping(value="getComboboxData")
	@ResponseBody
	public Object getComboboxData(HttpServletRequest  request){
		return operationalConfigService.findData(2);
	}
	
	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.OpertinalConfigViewJsp.NEWS_COLUMN_EDIT_VIEW;
		}
	
		if (StringUtil.equals(fromType,Constants.EDIT)){
			request.setAttribute("config",operationalConfigService.get(id));
			return ViewConstants.OpertinalConfigViewJsp.NEWS_COLUMN_EDIT_VIEW;
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
	
	@RequestMapping(value = "/setEnable", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult setEnable(@RequestParam("ids") String ids,Boolean isEnable) throws Exception  {
		
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray) || idArray.length==0 ){
			return new JsonResult(Boolean.FALSE,MessageUtils.message("business.update.not.found.data"));
		}
		operationalConfigService.setIsEnable(idArray, isEnable);
		return new JsonResult(MessageUtils.message("update.success"));
	}
}
