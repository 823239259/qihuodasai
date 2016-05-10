package com.tzdr.cms.controller.internationFuture;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.business.service.OutDisk.OutDiskParametersService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
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
	

}
