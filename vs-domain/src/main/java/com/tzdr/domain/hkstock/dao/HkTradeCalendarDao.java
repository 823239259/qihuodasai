package com.tzdr.domain.hkstock.dao;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.hkstock.entity.HkTradeCalendar;

/**
 * 
 * @zhouchen
 * 2015年1月4日
 */
public interface HkTradeCalendarDao extends BaseJpaDao<HkTradeCalendar, String>  {
	
	
	public HkTradeCalendar  findByIsTradeTrueAndDate(Long date);
	
	
	public HkTradeCalendar  findByDate(Long date);
}
