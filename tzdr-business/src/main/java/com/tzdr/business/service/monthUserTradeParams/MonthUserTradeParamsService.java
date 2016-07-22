package com.tzdr.business.service.monthUserTradeParams;

import antlr.collections.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.MonthUserTradeParams;

public interface MonthUserTradeParamsService extends BaseService<MonthUserTradeParams>{
	
	public MonthUserTradeParams getParams();
}
