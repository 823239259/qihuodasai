package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时获取当日持仓
 * @zhouchen
 * 2015年1月17日
 */
public class QueryUserCombostock extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(QueryUserCombostock.class);
	
	//private static  UserCombostockService combostockService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		logger.info("-----------------当日持仓查询--------begin-------------------");		

		/*combostockService = SpringUtils.getBean(UserCombostockService.class);
		combostockService.saveCombostocks();*/
		
		logger.info("------------------当日持仓查询--------end-------------------");		

	}
}
