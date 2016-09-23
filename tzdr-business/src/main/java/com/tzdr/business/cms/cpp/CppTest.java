package com.tzdr.business.cms.cpp;

import java.util.Map;


import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.Cpp;

public interface CppTest extends BaseService<Cpp>{
	public PageInfo<Object> find(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams);
}
