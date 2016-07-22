package com.tzdr.cms.quartz;

import java.util.List;

import jodd.util.StringUtil;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.drawMoney.DrawMoneyService;
import com.tzdr.business.service.withdrawal.WithdrawalService;
import com.tzdr.common.api.payease.PayEase;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.vo.PayeaseTreatDrawListVo;

/**
 * 币币支付 提现处理，定时处理提现数据更新状态
 * @zhouchen
 * 2015年12月3日
 */
public class PayEaseWithDrawJob extends QuartzJobBean {

	public static final Logger logger = LoggerFactory.getLogger(PayEaseWithDrawJob.class);
	
	private static  WithdrawalService withdrawalService;
	
	private static  DrawMoneyService drawMoneyService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		logger.info("-----------------易支付 提现处理，定时处理提现数据更新状态--------begin-------------------");		
		withdrawalService = SpringUtils.getBean(WithdrawalService.class);
		drawMoneyService = SpringUtils.getBean(DrawMoneyService.class);
		
		List<PayeaseTreatDrawListVo> payeaseTreatDrawListVos = withdrawalService.queryPayEaseTreatOrders();
		if (CollectionUtils.isEmpty(payeaseTreatDrawListVos)){
			return;
		}
		
		for (PayeaseTreatDrawListVo treatDrawListVo : payeaseTreatDrawListVos){
			String orderId = treatDrawListVo.getOrderId();
			String vmid = treatDrawListVo.getVmid();
			String secret = treatDrawListVo.getSecret();
			if (StringUtil.isBlank(orderId) || StringUtil.isBlank(secret)  
					|| StringUtil.isBlank(vmid)){
				continue;
			}
			
			String vdata = "C"+orderId;
			JSONObject jsonObject = PayEase.getInstance().queryDrawStatus(vmid, secret,vdata);
			String status = jsonObject.getString("status");
			if (StringUtil.equals(status,PayEase.WITHDRAW_PAY_SUCCESS)){
				drawMoneyService.updatDraw(orderId,"", vdata ,"4",Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
			}
			
			if (StringUtil.equals(status,PayEase.WITHDRAW_PAY_FAIL)){
				drawMoneyService.updatDraw(orderId,"", vdata ,"3",Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
			}
		}
		logger.info("------------------易支付 提现处理，定时处理提现数据更新状态--------end-------------------");		

	}
}
