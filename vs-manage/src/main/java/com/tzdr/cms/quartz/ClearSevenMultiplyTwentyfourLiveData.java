package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ClearSevenMultiplyTwentyfourLiveData extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(OldAndNewActivityJob.class);
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		
	}

}
