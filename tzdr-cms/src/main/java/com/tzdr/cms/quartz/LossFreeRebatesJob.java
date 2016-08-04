package com.tzdr.cms.quartz;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tzdr.business.service.extension.ActivityRewardService;
import com.tzdr.common.config.ActivityConfig;
import com.tzdr.common.utils.SpringUtils;


public class LossFreeRebatesJob extends QuartzJobBean{
	private Logger logger = LoggerFactory.getLogger(LossFreeRebatesJob.class);
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		if(ActivityConfig.now_time < ActivityConfig.activity_onLineEndTime){
			ActivityRewardService activityRewardService = SpringUtils.getBean(ActivityRewardService.class);
			activityRewardService.doSaveActivityReward(getStartTime(), getEndTime());
			logger.info(getStartTime()+"-"+getEndTime()+"奖励执行完毕");
		}
	}
	private static Long getStartTime(){
		long current = getCalendar();
		return (current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset())/1000;//今天零点零分零秒的毫秒数
	}
	private static Long getEndTime(){
		return (getStartTime()*1000+24*60*60*1000-1)/1000;//今天23点59分59秒的毫秒数;
	}
	private static Long getCalendar(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime().getTime();
	}
	public static void main(String[] args) throws ParseException {
	}
}
