package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 
 * @version 2.0
 * 2015年4月3日下午2:32:17
 */
public class AdjustmentTransferJob  extends QuartzJobBean{
	
	public static final Logger logger = LoggerFactory
			.getLogger(AdjustmentTransferJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		logger.info("------------------验资中自动划账任务--------begin-------------------");
		/*UserTradeService userTradeService = SpringUtils.getBean(UserTradeService.class);
		userTradeService.adjustmentTransfer();*/
		logger.info("------------------验资中自动划账任务--------end-------------------");
		
	}
}
