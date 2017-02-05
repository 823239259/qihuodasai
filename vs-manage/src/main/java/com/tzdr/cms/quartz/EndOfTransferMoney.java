package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see T+1终结划账
 * @version 2.0
 * 2015年2月28日上午11:09:44
 */
@Component
public class EndOfTransferMoney extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(EndOfTransferMoney.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------终结方案划账任务--------begin-------------------");

		/*UserTradeService userTradeService = SpringUtils.getBean(UserTradeService.class);
		userTradeService.endOfTransferMoney();*/
		logger.info("------------------终结方案划账任务--------end-------------------");

		
	}
}
