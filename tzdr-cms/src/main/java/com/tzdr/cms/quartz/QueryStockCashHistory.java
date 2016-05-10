package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tzdr.business.service.stock.StockCurrentHistoryService;
import com.tzdr.common.utils.SpringUtils;

/**
 * 定时获取恒生资产值
 * @zhouchen
 * 2015年1月17日
 */
public class QueryStockCashHistory extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(QueryStockCashHistory.class);
	
	private static StockCurrentHistoryService  service;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------获取上一天恒生账户资金任务--------begin-------------------");		

		service = SpringUtils.getBean(StockCurrentHistoryService.class);
		service.saveDatas();
		logger.info("------------------获取上一天恒生账户资金任务--------end-------------------");		

	}
}
