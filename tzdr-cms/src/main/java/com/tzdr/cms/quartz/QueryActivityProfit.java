package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tzdr.business.service.activity.ActivityProfitRecordService;
import com.tzdr.common.utils.SpringUtils;

/**
 * 定时获取历史成交记录
 * @zhouchen
 * 2015年1月17日
 */
public class QueryActivityProfit extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(QueryActivityProfit.class);
	
	private static  ActivityProfitRecordService service;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------活动收益任务--------begin-------------------");		

		service = SpringUtils.getBean(ActivityProfitRecordService.class);
		service.saveRecord();
		logger.info("------------------活动收益任务--------end-------------------");		

	}
}
