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

import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.entity.TradeDay;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 交易日维护controller
 */
@Controller
@RequestMapping("/admin/tradeDay")
public class TradeDayController extends BaseCmsController<TradeDay> {
	 
	private static Logger log = LoggerFactory.getLogger(TradeDayController.class);
	@Autowired
	private TradeDayService tradeDayService;

	@Override
	public BaseService<TradeDay> getBaseService() {
		return tradeDayService;
	}

	public TradeDayController() {
		setResourceIdentity("sys:system:tradeday");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		return ViewConstants.TradeDayViewJsp.LIST_VIEW;
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, String id) throws Exception 
	{	
		if (StringUtil.isBlank(id)){
			return ViewConstants.ERROR_VIEW;
		}
		
		TradeDay tradeDay = tradeDayService.get(id);
		request.setAttribute("tradeDay", tradeDay);
		return  ViewConstants.TradeDayViewJsp.EDIT_VIEW;
	}
	
	
	/**
	 * 生成日历
	 * @param request
	 * @param year
	 * @return
	 */
	@RequestMapping(value="createCalener")
	@ResponseBody
	public JsonResult setDays(HttpServletRequest request,@RequestParam("year") int year){
		if (year < 2015){
			return new JsonResult(Boolean.FALSE,"输入的年份有误。请输入2015年以后的年份");
		}
		if (tradeDayService.isCreate(year)){
			return new JsonResult(Boolean.FALSE,year+" 年的日历已经创建过了。");
		}
		tradeDayService.createCalendar(year);
		return new JsonResult("生成成功");
	}
	
}
