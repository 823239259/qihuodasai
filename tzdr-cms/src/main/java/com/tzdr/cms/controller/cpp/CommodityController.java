package com.tzdr.cms.controller.cpp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.cpp.CommodityService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.CommodityVo;
import com.tzdr.domain.web.entity.Commodity;

@Controller
@RequestMapping(value ="/admin/commodity")
public class CommodityController {

	@Autowired
	private CommodityService commodityService;
	
	
	@RequestMapping(value = "/find")
	@ResponseBody
	public Object find(EasyUiPageInfo easyUiPage,HttpServletResponse response,HttpServletRequest request){
		easyUiPage.setRows(easyUiPage.getRows());
		easyUiPage.setPage(easyUiPage.getPage());
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
	
		PageInfo<Object> pageInfo =  commodityService.find(easyUiPage , searchParams);
		
		return new EasyUiPageData<Object>(pageInfo);
	}
	
	

	@RequestMapping(value = "/add")
	@ResponseBody
	public JsonResult add(Commodity commodity,HttpServletResponse response,HttpServletRequest request){
	if(commodity!=null){
		commodity.setDepositRatio(10d);
		commodity.setTradeFee(10d);
		commodityService.add(commodity);
	}else{
		return new JsonResult(false);
	}
		return new JsonResult(true);
	}
	
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public JsonResult update(Commodity commodity,HttpServletResponse response,HttpServletRequest request){
	if(commodity!=null){
		
		commodityService.updateCommodity(commodity);
	}else{
		return new JsonResult(false);
	}
		return new JsonResult(true);
	}
	
	
	
}
