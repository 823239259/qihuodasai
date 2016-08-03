package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tzdr.business.service.endplan.EndPlanService;
import com.tzdr.common.utils.SpringUtils;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 
 * @version 2.0
 * 2015年4月3日下午2:32:17
 */
public class ProgrammeExtensionSendMessageJob  extends QuartzJobBean{
	
	public static final Logger logger = LoggerFactory
			.getLogger(ProgrammeExtensionSendMessageJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------母账户止日5至7天短信提醒--------begin-------------------");		

		EndPlanService endPlanService = SpringUtils.getBean(EndPlanService.class);
		endPlanService.programmeExtensionSendMessage();
		logger.info("------------------母账户止日5至7天短信提醒--------end-------------------");		

	}
}
