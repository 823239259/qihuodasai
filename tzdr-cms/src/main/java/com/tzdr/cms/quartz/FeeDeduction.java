package com.tzdr.cms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.tzdr.business.hkstock.service.HkFeeDuductionService;
import com.tzdr.business.service.feededuction.FeeDuductionService;
import com.tzdr.common.utils.SpringUtils;

/**
 * 定时任务扣取 管理费和利息
 * 
 * @zhouchen 2015年1月7日
 */
@Component
public class FeeDeduction extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(FeeDeduction.class);
	
	private static  FeeDuductionService feeDuductionService;
	
	private static  HkFeeDuductionService hkFeeDuductionService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		logger.info("------------------系统扣费任务--------begin-------------------");		
		feeDuductionService = SpringUtils.getBean(FeeDuductionService.class);
		hkFeeDuductionService = SpringUtils.getBean(HkFeeDuductionService.class);
		
		// 扣除欠费
		feeDuductionService.deductionArrearage();

		// 扣除当日A股管理费
		feeDuductionService.deductionTodayFee();
		
		// 扣除当日港股管理费
		hkFeeDuductionService.deductionTodayFee();

		// 发送当日欠费短信
		feeDuductionService.sendCurrentArrearsSms();

		// 发送下一日不够扣短信
		feeDuductionService.isCanFeeDeductionNextDay();
		
		// 港股发送下一日不够扣短信
		hkFeeDuductionService.isCanFeeDeductionNextDay();
		
		logger.info("------------------系统扣费任务--------end-------------------");		

	}
}
