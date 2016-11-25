package com.tzdr.domain.dao.crawler;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CrawlerCalendar;

public interface CrawlerCalendarDao extends BaseJpaDao<CrawlerCalendar, String>{
	@Query("from CrawlerCalendar where calendarId = ?1")
	public List<CrawlerCalendar> findByCalendar(String calendarId);
	
	@Query("from CrawlerCalendar where createTime BETWEEN ?1 and ?2")
	public List<CrawlerCalendar> findByCalendarCreateBetween(Long startTime,Long endTime);
	
	@Query(value = "select * from  crawler_calendar  LIMIT ?1 , ?2" ,nativeQuery = true )
	public List<CrawlerCalendar> findByCalerdarPage(Integer startIndex,Integer size);
}
