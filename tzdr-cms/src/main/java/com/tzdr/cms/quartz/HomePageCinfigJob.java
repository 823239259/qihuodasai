package com.tzdr.cms.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.google.common.collect.Lists;
import com.tzdr.business.service.homepagecinfig.HomePageCinfigService;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.HttpClientUtils;
import com.tzdr.common.utils.SpringUtils;

/**
* @Description: 首页参数更新定时器
* @ClassName: HomePageCinfigJob
* @author wangpinqun
* @date 2015年11月23日 上午11:09:21
 */
public class HomePageCinfigJob  extends QuartzJobBean{
	
	public static final Logger logger = LoggerFactory.getLogger(HomePageCinfigJob.class);

	private static HomePageCinfigService homePageCinfigService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------首页参数更新任务--------begin-------------------");
		try 
		{
			//获取HomePageCinfigService实例
			homePageCinfigService = SpringUtils.getBean(HomePageCinfigService.class);
			this.updateBatchHomePageCinfig();    //更新首页参数
			this.refreshHomePageCinfigParam();   //刷新应用首页参数
		} catch (BeansException e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			HandleException(e, "executeInternal");
		}
		logger.info("------------------首页参数更新任务--------end-------------------");

	}
	
	/**
	 * 更新首页参数
	 */
	public void updateBatchHomePageCinfig(){
		try 
		{
			Map<String, String> params = new HashMap<String, String>();
			params.put("randomNUser", "TotalTUsers");
			params.put("randomNTrade", "TotalTNum");
			homePageCinfigService.updateBatchDataMap(params);
		} catch (BeansException e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			HandleException(e, "executeInternal");
		}
	}
	
	/**
	 * 刷新应用首页参数
	 */
	public void refreshHomePageCinfigParam(){
		try 
		{
			String applyUrls = ConfUtil.getContext("apply.urls");
			
			String[] urls = null;
			
			if(StringUtil.isNotBlank(applyUrls)){
				urls = applyUrls.split(";");
			}
			if(urls != null && urls.length > 0){
				for (String httpUrl : urls) {
					this.refreshApplyHomePageParam(httpUrl);
				}
			}
		} catch (BeansException e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			HandleException(e, "executeInternal");
		}
	}
	
	/**
	 * 刷新应用首页参数
	 */
	public void refreshApplyHomePageParam(String httpUrl){
		Exception ex = null;
		for (int i = 0; i < 3; i++) {
			ex = null;
			try 
			{
				String reuslt = HttpClientUtils.httpRequest2Json(httpUrl);
				logger.info("httpUrl："+httpUrl+",reuslt:"+reuslt);
				break;
			} catch (Exception e) {
				logger.error(Exceptions.getStackTraceAsString(e));
				ex = e;
			}
		}
		
		if(ex != null){
			HandleException(ex, "executeInternal");
		}
	}
	
	/**
	 * 异常处理
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
