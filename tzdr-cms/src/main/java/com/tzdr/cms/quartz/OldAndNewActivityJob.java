package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.tzdr.business.service.activity.ActivityOldAndNewService;
import com.tzdr.common.utils.SpringUtils;

/**
 * 老带新数据统计 定时任务
 * @author gc
 * 
 */
public class OldAndNewActivityJob extends QuartzJobBean{
	public static final Logger logger = LoggerFactory
			.getLogger(OldAndNewActivityJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------老带新数据统计定时任务--------begin-------------------");		

		ActivityOldAndNewService activityOldAndNewService = SpringUtils.getBean(ActivityOldAndNewService.class);
		activityOldAndNewService.getActivityStatistics();
		logger.info("------------------老带新数据统计定时任务--------end-------------------");	
	}
}
