package com.tzdr.cms.quartz;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.google.common.collect.Lists;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.SpringUtils;

/**
* @Description: 15:30 解冻下一个交易日开始的账户
* @ClassName: 
* @author LinFeng
* @date 
 */
public class ThawNextAccount  extends QuartzJobBean{
	
	public static final Logger logger = LoggerFactory.getLogger(ThawNextAccount.class);

	private static UserTradeService userTradeService;


	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("-----------------解冻下一个交易生效配资帐号--------begin-------------------");		

		try 
		{
			userTradeService = SpringUtils.getBean(UserTradeService.class);
			//解冻下一个交易日开始的账户
			userTradeService.thawNextUserTrade();
		} catch (BeansException e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			HandleException(e, "executeInternal");
		}
		
		logger.info("-----------------解冻下一个交易生效配资帐号--------end-------------------");		

	}
	
	/**
	 * 异常处理
	 * 
	 * @param e
	 * @param method
	 */
	private void HandleException(Exception e, String method) {
		// 发送邮件
		String devEmail = ConfUtil.getContext("mail.to.dev");
		List<String> pramas = Lists.newArrayList();
		String methodName = this.getClass().getName() + "." + method;
		String exception = Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,"exceptionemail", pramas);
		} catch (Exception ex) {
			logger.error("email:", ex.getMessage());
		}
		logger.error(methodName + ",error::{}", exception);
	}
}
