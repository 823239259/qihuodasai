package com.tzdr.domain.dao.monthUserTrade;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.MonthUserTrade;


public interface MonthUserTradeDao  extends BaseJpaDao<MonthUserTrade, String>{

	/**
	 * 根据用户的方案id查询对应的关联记录
	 * @param tradeId
	 * @return
	 */
	public MonthUserTrade findByTradeId(String tradeId);
}
