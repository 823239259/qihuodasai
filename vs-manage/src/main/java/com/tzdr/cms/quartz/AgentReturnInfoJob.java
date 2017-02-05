package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tzdr.business.service.generalize.AgentReturnInfoService;
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
public class AgentReturnInfoJob  extends QuartzJobBean{
	
	public static final Logger logger = LoggerFactory
			.getLogger(AgentReturnInfoJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		logger.info("------------------下级返点信息统计任务--------begin-------------------");
		AgentReturnInfoService agentReturnInfoService = SpringUtils.getBean(AgentReturnInfoService.class);
		agentReturnInfoService.agentDayReturnIncomeExecute();
		logger.info("------------------下级返点信息统计任务--------end-------------------");

		
	}
}
