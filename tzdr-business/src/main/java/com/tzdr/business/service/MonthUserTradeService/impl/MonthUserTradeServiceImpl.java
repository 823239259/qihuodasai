package com.tzdr.business.service.MonthUserTradeService.impl;





import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.MonthUserTradeService.MonthUserTradeService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.monthUserTrade.MonthUserTradeDao;
import com.tzdr.domain.web.entity.MonthUserTrade;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MonthUserTradeServiceImpl extends BaseServiceImpl<MonthUserTrade, MonthUserTradeDao> implements MonthUserTradeService{

	@Override
	public MonthUserTrade findByTradeId(String tradeId) {
		return this.getEntityDao().findByTradeId(tradeId);
	}
	
	
}
