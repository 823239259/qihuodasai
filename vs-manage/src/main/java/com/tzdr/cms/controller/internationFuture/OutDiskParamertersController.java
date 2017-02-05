package com.tzdr.cms.controller.internationFuture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.OutDisk.OutDiskParametersService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.OutDiskParameters;
/**
 * 国际综合参数设置
 * 类说明
 * @author  zhaozhao
 * @date    2016年2月23日上午9:43:54
 * @version 1.0
 */
@Controller
@RequestMapping("admin/OutDiskParameters")
public class OutDiskParamertersController extends BaseCmsController<OutDiskParameters>{
	
	@Autowired
	private OutDiskParametersService outDiskParametersService;
	
	@Override
	public BaseService<OutDiskParameters> getBaseService() {
		// TODO Auto-generated method stub
		return outDiskParametersService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:settingParams:OutDiskParameters");
	}
	
	@RequestMapping("list")
	public String List(){
		return ViewConstants.OutDisk.LIST_PA_VIEW;
	}
	@RequestMapping(value = "/outUpdate",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult update(HttpServletRequest request,OutDiskParameters diskParameters){
		outDiskParametersService.update(diskParameters);
		return new JsonResult(MessageUtils.message("update.success"));
	}

}
