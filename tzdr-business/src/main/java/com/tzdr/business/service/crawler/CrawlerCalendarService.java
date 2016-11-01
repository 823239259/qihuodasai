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
}
