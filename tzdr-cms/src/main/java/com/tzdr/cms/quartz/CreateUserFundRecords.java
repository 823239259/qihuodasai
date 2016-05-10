package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tzdr.business.service.reports.UserFundsRecordService;
import com.tzdr.common.utils.SpringUtils;

/** 
 * 生成用户资金记录表
 * @zhouchen
 * 2015年4月7日
 */
public class CreateUserFundRecords extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(CreateUserFundRecords.class);
	
	private static  UserFundsRecordService userFundsRecordService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------用户资金记录任务--------begin-------------------");
		userFundsRecordService = SpringUtils.getBean(UserFundsRecordService.class);
		userFundsRecordService.createUserFundRecords();
		logger.info("------------------用户资金记录任务--------end-------------------");

	}
	
}
