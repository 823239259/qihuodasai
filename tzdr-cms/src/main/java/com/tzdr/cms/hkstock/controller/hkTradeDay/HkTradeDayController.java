package com.tzdr.cms.hkstock.controller.hkTradeDay;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.hkstock.service.HkTradeCalendarService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.hkstock.entity.HkTradeCalendar;

import jodd.util.StringUtil;

/**
 * 港股交易日管理
 * @Description: 
 * @author liuhaichuan
 * @date 2015年10月20日
 *
 */
@Controller
@RequestMapping("/admin/hkstock/hkTradeDay")
public class HkTradeDayController extends BaseCmsController<HkTradeCalendar>{
	
	private static Logger logger = LoggerFactory.getLogger(HkTradeDayController.class);
	
	@Autowired
	private HkTradeCalendarService hkTradeCalendarService;

	@Override
	public BaseService<HkTradeCalendar> getBaseService() {
		// TODO Auto-generated method stub
		return hkTradeCalendarService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:system:tradeday");
	}
	
	/**
	 * 编辑交易日
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, String id) throws Exception 
	{	
		if (StringUtil.isBlank(id)){
			return ViewConstants.ERROR_VIEW;
		}
		HkTradeCalendar tradeDay=hkTradeCalendarService.get(id);
		request.setAttribute("tradeDay", tradeDay);
		return  HkViewConstants.HkTradeDayJsp.EDIT_VIEW;
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
		if (hkTradeCalendarService.isCreate(year)){
			return new JsonResult(Boolean.FALSE,year+" 年的日历已经创建过了。");
		}
		hkTradeCalendarService.createCalendar(year);
		return new JsonResult("生成成功");
	}

}
