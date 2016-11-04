package com.tzdr.business.service.crawler;


import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.CrawlerCalendar;

public interface CrawlerCalendarService extends BaseService<CrawlerCalendar>{
	/**
	 * 根据第三方id获取数据
	 * @param calendarId
	 * @return
	 */
	public CrawlerCalendar doGetCrawlerCalendarByCalendarId(String calendarId);
	/**
	 * 采集数据保存
	 * @param array
	 */
	public void  doSaveCrawlerCalendarList(List<CrawlerCalendar> calendars);
	/**
	 * 根据数据创建时间查询数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<CrawlerCalendar> findByCreateTimeBetween(Long startTime,Long endTime);
	/**
	 * 批量删除数据
	 * @param entities
	 */
	public void deleteBatch(List<CrawlerCalendar> entities);
}
