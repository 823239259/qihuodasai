package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时获取历史成交记录
 * @zhouchen
 * 2015年1月17日
 */
public class QueryRealDeal extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(QueryRealDeal.class);
	
	//private static  RealDealService realDealService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------股票成交查询任务--------begin-------------------");		

		/*realDealService = SpringUtils.getBean(RealDealService.class);
		realDealService.saveRealDeals();	*/
		logger.info("------------------股票成交查询任务--------end-------------------");		

	}
}
