package com.tzdr.cms.quartz;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.tzdr.business.service.crawler.CrawlerBackUpHistoryService;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.SpringUtils;

public class WallStreetcnBackHistoryJob extends QuartzJobBean{
	private Logger logger = LoggerFactory.getLogger(WallStreetcnBackHistoryJob.class);
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("备份日历数据开始");
		Date date = new Date();
		//获取上周星期天的时间
		Date lastWeekDate = DateUtils.addDates(date, -1);
		//获取上周的上周星期天的时间
		Date lastAndLastWeebDate = DateUtils.addDates(date, -8);
		CrawlerBackUpHistoryService calendarService = SpringUtils.getBean(CrawlerBackUpHistoryService.class);
		calendarService.backUpHistory(lastAndLastWeebDate, lastWeekDate);
		logger.info("备份日历数据结束");
	}

}
