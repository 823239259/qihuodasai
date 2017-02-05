package com.tzdr.business.service.crawler;

import java.util.Date;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.CrawlerCalendarHistory;

public interface CrawlerBackUpHistoryService extends BaseService<CrawlerCalendarHistory>{
	public void backUpHistory(Date start,Date end);
}
