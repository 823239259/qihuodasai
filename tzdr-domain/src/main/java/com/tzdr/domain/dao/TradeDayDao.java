package com.tzdr.domain.dao;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.entity.TradeDay;

/**
 * 
 * @zhouchen
 * 2015年1月4日
 */
public interface TradeDayDao extends BaseJpaDao<TradeDay, String>  {
	
	
	public TradeDay  findByIsTradeTrueAndDate(Long date);
	
	
	public TradeDay  findByDate(Long date);
}
