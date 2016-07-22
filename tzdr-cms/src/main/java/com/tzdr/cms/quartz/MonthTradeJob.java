package com.tzdr.cms.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.common.api.ihuyi.PgbSMSSender;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 月月配 自动延期任务
 * @author zhouchen
 * 2016年6月27日 上午10:23:02
 */
@Component
public class MonthTradeJob extends QuartzJobBean {

	public static final Logger logger = LoggerFactory
			.getLogger(MonthTradeJob.class);
	
	private static  UserTradeService userTradeService;
		
	private static  DataMapService dataMapService;
	
	private static NoticeRecordService noticeRecordService;
	
	//操盘中状态
	private static final Short  TRADE_OPER_STATUS = 1;
	
	/**
	 * 方案到期日期倒数第10、5、3、2天
	 */
	private static final Short  MAX_SOON_EXPIRE_DAYS = 9;
	
	private static final Short  MIN_SOON_EXPIRE_DAYS = 1;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("------------------ 月月配 自动延期任务--------begin-------------------");	
		
		userTradeService = SpringUtils.getBean(UserTradeService.class);
		dataMapService = SpringUtils.getBean(DataMapService.class);
		noticeRecordService = SpringUtils.getBean(NoticeRecordService.class);
		// 获取当天到期的方案 
		Long currentLongDay = Dates.getCurrentLongDay();
		List<UserTrade> userTrades = userTradeService.findByStatusAndActivityTypeAndEstimateEndtime(TRADE_OPER_STATUS,UserTrade.ActivityType.MONTH_TRADE,currentLongDay);
		if (!CollectionUtils.isEmpty(userTrades)){
			for (UserTrade userTrade : userTrades) {
				WUser  wuser = userTrade.getWuser();
				//默认为失败模版
				String content = MessageUtils.message("month.trade.auto.delay.fail",userTrade.getProgramNo());
				try {
					JsonResult  jsonResult  = userTradeService.delayMonthTrade(userTrade,1,true);
					if (jsonResult.isSuccess()){
						//发送成功短信 【您的月月配方案【变量】已自动延期1个月，新的操盘到期日为【变量】。客服热线400-633-9257】
						String estimateEndtime = (String) jsonResult.getObj();
						content = MessageUtils.message("month.trade.auto.delay.success",userTrade.getGroupId(),estimateEndtime);
						PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(),wuser.getMobile(), content);
					}
					else{
						//发送失败短信【您的月月配方案【变量】因账户余额不足自动延期失败，请在今日14:30之前手动追加操盘时间，否则系统将在14:30开始平仓并终结方案。客服热线400-633-9257】
						PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(),wuser.getMobile(), content);
					}
				} catch (Exception e) {
					PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(),wuser.getMobile(), content);
					logger.info("---------------方案ID【"+userTrade.getId()+"】自动延期失败了，请注意查看！---------------",e);
				}
				//保存通知记录
				noticeRecordService.save(new NoticeRecord(wuser.getId(), content,12));
			}
		}
		logger.info("------------------ 月月配 自动延期任务--------end-------------------");		
		
		
		logger.info("------------------ 月月配即将到期方案短信检测任务--------begin-------------------");	
		
		// 获取满足发送短信的方案列表
		Date currentDate = Dates.parseLong2Date(currentLongDay);
		Long start = Dates.addDay(currentDate,MIN_SOON_EXPIRE_DAYS).getTime()/1000;
		Long end = Dates.addDay(currentDate,MAX_SOON_EXPIRE_DAYS).getTime()/1000;
		List<UserTrade> soonExpireMonthTrades = userTradeService.findSoonExpireMonthTrades(TRADE_OPER_STATUS,UserTrade.ActivityType.MONTH_TRADE,start,end);
		if (!CollectionUtils.isEmpty(soonExpireMonthTrades)){
			for (UserTrade soonUserTrade : soonExpireMonthTrades) {
				WUser  wuser = soonUserTrade.getWuser();
				try {
					Date estimateEndtime = Dates.parseLong2Date(soonUserTrade.getEstimateEndtime());
					int days  =  Dates.daysBetween(currentDate, estimateEndtime)+1;
					// 方案到期日期倒数第10、5、3、2天
					if (days == 10 || days == 5 || days == 3 || days == 2){
						String  content = MessageUtils.message("month.trade.soon.expire",soonUserTrade.getGroupId(),days);
						PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(),wuser.getMobile(), content);
						//保存通知记录
						noticeRecordService.save(new NoticeRecord(wuser.getId(), content,11));
					}
				} catch (Exception e) {
					logger.info("用户【"+wuser.getMobile()+"】月月配即将到期短信短信发送失败！",e);	
				}
			}
		}
		logger.info("------------------ 月月配即将到期方案短信检测任务--------end-------------------");	

	}
}
