package com.tzdr.cms.controller.cpp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.cpp.CppTest;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;

@Controller
@RequestMapping(value ="/cpp")
public class CppController{
	@Autowired
	private CppTest cppService;
	@RequestMapping(value = "/find")
	@ResponseBody
	public JsonResult find(HttpServletRequest request){
		EasyUiPageInfo easyUiPage = new EasyUiPageInfo();
		easyUiPage.setRows(100);
		easyUiPage.setPage(1);
		Map<String , Object> searchParams = new HashMap<>();
		JsonResult result = new JsonResult(true);
		PageInfo<Object> cppList =  cppService.find(easyUiPage , searchParams);
		
		result.setObj(cppList.getPageResults());
		return result;
	}
}
