package com.tzdr.cms.controller.internationFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.business.service.OutDisk.OutDiskPriceService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.OutDiskPrice;
/**
 * 国际综合价格设置
 * 类说明
 * @author  zhaozhao
 * @date    2016年2月23日上午9:44:20
 * @version 1.0
 */
@Controller
@RequestMapping("admin/OutDiskPrice")
public class OutDiskPriceController extends BaseCmsController<OutDiskPrice>{

	@Autowired
	private OutDiskPriceService outDiskPriceService;
	
	@Override
	public BaseService<OutDiskPrice> getBaseService() {
		// TODO Auto-generated method stub
		return outDiskPriceService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:operationalConfig:OutDiskParameters");
	}

}
