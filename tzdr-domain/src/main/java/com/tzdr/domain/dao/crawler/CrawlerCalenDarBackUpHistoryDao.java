package com.tzdr.domain.dao.crawler;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CrawlerCalendarHistory;

public interface CrawlerCalenDarBackUpHistoryDao extends BaseJpaDao<CrawlerCalendarHistory, String>{
	/**
	 * 根据第三方数据id查询数据
	 * @param calendarId
	 * @return
	 */
	@Query("from CrawlerCalendarHistory where calendarId = ?1")
	public List<CrawlerCalendarHistory> findByCrawlerCalendarId(String calendarId);
}
