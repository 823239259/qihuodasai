package com.tzdr.cms.controller.cpp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.cpp.CurrencyService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;

@Controller
@RequestMapping(value ="/admin/currency")
public class CurrencyController {

	@Autowired
	private CurrencyService currencyService;
	
	@RequestMapping(value = "/find")
	@ResponseBody
	public JsonResult find(EasyUiPageInfo easyUiPage,HttpServletResponse response,HttpServletRequest request){
		easyUiPage.setRows(50);
		easyUiPage.setPage(1);
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
	
		PageInfo<Object> pageInfo =  currencyService.find(easyUiPage , searchParams);
		JsonResult jsonResult =new JsonResult(true);
		jsonResult.setObj(pageInfo.getPageResults());
		return jsonResult ;
	}
	
	
}
