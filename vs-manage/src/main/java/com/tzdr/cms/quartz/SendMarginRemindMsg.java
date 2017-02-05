package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 低于补仓线定时短信
 * @zhouchen 2015年1月7日
 */
@Component
public class SendMarginRemindMsg extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(SendMarginRemindMsg.class);
	
	//private static  UserTradeService userTradeService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		/*logger.info("-----------------补仓提醒任务--------begin-------------------");		

		userTradeService = SpringUtils.getBean(UserTradeService.class);
		
		userTradeService.sendMarginRemindMsg();
		logger.info("-----------------补仓提醒任务--------end-------------------");	*/	

	}
}
