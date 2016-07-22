package com.tzdr.business.service.MonthUserTradeService;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.MonthUserTrade;

public interface MonthUserTradeService extends BaseService<MonthUserTrade> {

	public MonthUserTrade findByTradeId(String tradeId);
}
