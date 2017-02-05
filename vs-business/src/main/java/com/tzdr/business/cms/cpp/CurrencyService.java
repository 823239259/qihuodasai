package com.tzdr.business.cms.cpp;

import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.Currency;

public interface CurrencyService extends BaseService<Currency>{

	
	public PageInfo<Object> find(EasyUiPageInfo easyUiPageInfo,Map<String, Object> searchParams);
		
	
}

