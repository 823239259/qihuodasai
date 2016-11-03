package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tzdr.business.service.crawler.CrawlerCalendarService;
import com.tzdr.common.utils.SpringUtils;

public class WallStreetcnBackHistory extends QuartzJobBean{
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		CrawlerCalendarService calendarService = SpringUtils.getBean(CrawlerCalendarService.class);
	}

}
