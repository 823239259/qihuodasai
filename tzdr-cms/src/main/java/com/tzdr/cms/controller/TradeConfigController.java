package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.TradeConfig;

/**
 * 配资相关配资参数 维护
 * @zhouchen
 * 2015年1月26日
 */
@Controller
@RequestMapping("/admin/tradeConfig")
public class TradeConfigController extends BaseCmsController<TradeConfig> {
	 
	private static Logger log = LoggerFactory.getLogger(TradeConfigController.class);
	@Autowired
	private TradeConfigService tradeConfigService;

	@Override
	public BaseService<TradeConfig> getBaseService() {
		return tradeConfigService;
	}

	public TradeConfigController() {
		setResourceIdentity("sys:settingParams:tradeConfig");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		return ViewConstants.TradeConfigViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.TradeConfigViewJsp.EDIT_VIEW;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			TradeConfig  tradeConfig  = tradeConfigService.get(id);
			request.setAttribute("config",tradeConfig);
			return ViewConstants.TradeConfigViewJsp.EDIT_VIEW;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
	
	/**
	 * 设置计算管理费的天数  或 利息天数
	 * @param request
	 * @param days
	 * @param type
	 * @return
	 */
	@RequestMapping(value="setDays")
	@ResponseBody
	public JsonResult setDays(HttpServletRequest request,@RequestParam("days") Double days,
			@RequestParam("utype") int  type){
		if (0.0==days){
			return new JsonResult(Boolean.FALSE,"设置天数必须大于0");
		}
		
		tradeConfigService.updateDays(days, type);
		return new JsonResult("设置成功");
	}
}
