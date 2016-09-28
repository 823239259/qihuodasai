package com.tzdr.business.cms.cpp;

import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.Commodity;

public interface CommodityService extends BaseService<Commodity>{

	
	public PageInfo<Object> find(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams);
	
	public JsonResult add(Commodity commodity);
	
	public JsonResult updateCommodity(Commodity commodity);
}
