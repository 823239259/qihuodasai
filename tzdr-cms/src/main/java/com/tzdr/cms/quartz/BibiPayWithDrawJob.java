package com.tzdr.cms.quartz;

import java.util.List;
import java.util.TreeMap;

import jodd.util.StringUtil;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.service.drawMoney.DrawMoneyService;
import com.tzdr.business.service.withdrawal.WithdrawalService;
import com.tzdr.common.api.bbpay.Bibipay;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.vo.BibiTreatDrawListVo;

/**
 * 币币支付 提现处理，定时处理提现数据更新状态
 * @zhouchen
 * 2015年12月3日
 */
public class BibiPayWithDrawJob extends QuartzJobBean {

	public static final Logger logger = LoggerFactory.getLogger(BibiPayWithDrawJob.class);
	
	private static  WithdrawalService withdrawalService;
	
	private static  DrawMoneyService drawMoneyService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		logger.info("-----------------币币支付 提现处理，定时处理提现数据更新状态--------begin-------------------");		
		withdrawalService = SpringUtils.getBean(WithdrawalService.class);
		drawMoneyService = SpringUtils.getBean(DrawMoneyService.class);
		
		List<BibiTreatDrawListVo> bibiTreatDrawListVos = withdrawalService.queryBbTreatOrders();
		if (CollectionUtils.isEmpty(bibiTreatDrawListVos)){
			return;
		}
		
		for (BibiTreatDrawListVo treatDrawListVo : bibiTreatDrawListVos){
			String id = treatDrawListVo.getId();
			String bbOrderId = treatDrawListVo.getBbOrderId();
			if (StringUtil.isBlank(id) || StringUtil.isBlank(bbOrderId)){
				continue;
			}
			
			TreeMap<String, String> resultMap = null;
			try {
				resultMap = Bibipay.getInstance().queryOutOrder(bbOrderId);
			} catch (Exception e) {
				logger.error("币币支付实时查下提现支付结果异常",e);
				EmailExceptionHandler.getInstance().HandleHintWithData("币币支付调用出款查询接口失败","queryOutOrder","币币支付订单ID:"+bbOrderId+",提现记录ID:"+id);
				continue;
			}
			
			if (CollectionUtils.isEmpty(resultMap)){
				continue;
			}
			String status = resultMap.get("status");
			String orderId = resultMap.get("orderid");
			if (StringUtil.isBlank(orderId) || StringUtil.isBlank(status)){
				continue;
			}
			
			if (StringUtil.equals(status,Bibipay.WITHDRAW_PAY_SUCCESS)){
				drawMoneyService.updatDraw(orderId,"", bbOrderId ,"4",Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
			}
			
			if (StringUtil.equals(status,Bibipay.WITHDRAW_PAY_FAIL)){
				drawMoneyService.updatDraw(orderId,"", bbOrderId ,"3",Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
			}
		}
		logger.info("------------------币币支付 提现处理，定时处理提现数据更新状态--------end-------------------");		

	}
}
