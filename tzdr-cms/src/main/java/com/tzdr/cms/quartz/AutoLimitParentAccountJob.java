package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tzdr.business.service.endplan.EndPlanService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.common.utils.SpringUtils;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 
 * @version 2.0
 * 2015年4月3日下午2:32:17
 */
public class AutoLimitParentAccountJob  extends QuartzJobBean{
	
	public static final Logger logger = LoggerFactory
			.getLogger(AutoLimitParentAccountJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------自动限制买入定时任备执行开始--------begin-------------------");
		EndPlanService endPlanService = SpringUtils.getBean(EndPlanService.class);
		endPlanService.autoLimitParentAccount();
		logger.info("------------------自动限制买入定时任备执行开始--------end-------------------");
		
	}
}
