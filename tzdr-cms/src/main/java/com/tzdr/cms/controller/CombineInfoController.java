package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.CombineInfo;

/**
 * 
 * @zhouchen
 * 2015年2月11日
 */
@Controller
@RequestMapping("/admin/combine")
public class CombineInfoController extends BaseCmsController<CombineInfo> {
	 
	private static Logger log = LoggerFactory.getLogger(CombineInfoController.class);
	@Autowired
	private CombineInfoService combineInfoService;

	@Override
	public BaseService<CombineInfo> getBaseService() {
		return combineInfoService;
	}
	
	
	/**
	 * 刷新 组合信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/refresh")
	@ResponseBody
	public JsonResult refresh(HttpServletRequest request){
		combineInfoService.refreshCombineInfos();
		return new JsonResult("刷新成功");
	}
}
