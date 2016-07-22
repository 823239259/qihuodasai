package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.common.utils.SpringUtils;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see T+1 佣金划账
 * @version 2.0
 * 2015年2月28日上午11:10:08
 */
@Component
public class EndOfCommissionMoney extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(EndOfCommissionMoney.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		logger.info("------------------佣金划账至母账户任务--------begin-------------------");
		/*UserTradeService userTradeService = SpringUtils.getBean(UserTradeService.class);
		userTradeService.endOfCommission();*/
		logger.info("------------------佣金划账至母账户任务--------end-------------------");

		
	}
}
