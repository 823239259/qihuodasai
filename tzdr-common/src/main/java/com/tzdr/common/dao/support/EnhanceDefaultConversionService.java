package com.tzdr.common.dao.support;

import org.springframework.core.convert.support.DefaultConversionService;

public class EnhanceDefaultConversionService extends DefaultConversionService {

	public EnhanceDefaultConversionService() {
		super();
		addConverter(new StringToDateConverter());
	}

}
