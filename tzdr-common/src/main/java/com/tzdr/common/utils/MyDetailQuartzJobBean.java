package com.tzdr.common.utils;

import java.lang.reflect.Method;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyDetailQuartzJobBean extends QuartzJobBean {
	public static final Logger log = LoggerFactory
			.getLogger(MyDetailQuartzJobBean.class);
	private String targetObject;
	private String targetMethod;
	private ApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			log.info("execute targetObject [" + targetObject + "] at once>>>>>>");
			Object otargetObject = ctx.getBean(targetObject);
			Method m = null;

			try {
				log.info("execute  targetMethod [" + targetMethod + "] at once>>>>>>");
				m = otargetObject.getClass().getMethod(targetMethod,
						new Class[] { JobExecutionContext.class });
				m.invoke(otargetObject, new Object[] { context });
			} catch (SecurityException e) {
				log.error(e.getMessage());
			} catch (NoSuchMethodException e) {
				log.error(e.getMessage());
			}
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

}
