package com.tzdr.business.service.activity;

import java.util.List;

import javax.transaction.Transactional;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.dao.activity.ActivityProfitRecordDao;
import com.tzdr.domain.vo.ActivityProfitRecordVo;
import com.tzdr.domain.web.entity.ActivityProfitRecord;


/**
 * 8800 活动收益记录
 * @zhouchen
 * 2015年2月9日
 */
@Service
@Transactional
public class ActivityProfitRecordService extends BaseServiceImpl<ActivityProfitRecord,ActivityProfitRecordDao>{
	
	private static final Double  beginMoney = 8800.0;
	
	public static final Logger logger = LoggerFactory.getLogger(ActivityProfitRecordService.class);
	
	@Autowired
	private UserTradeService  userTradeService;
	
	public List<ActivityProfitRecordVo> queryActivityUserData(){
		String sql = "SELECT us.id uid, us.mobile, trade.group_id tradeGroupId, trade.parent_account_no parentAccountNo, trade.combine_id combineId, trade.`status`,trade.accrual FROM w_user_trade trade, w_user us WHERE trade.uid = us.id AND trade.activity_type = 1 AND trade.`status` IN (1, 2) GROUP BY trade.group_id ";
		List<ActivityProfitRecordVo> list = nativeQuery(sql, null,ActivityProfitRecordVo.class);
		return list;
	}
	
	/**
	 * 定时保存 记录
	 */
	public void saveRecord(){
		List<ActivityProfitRecordVo> list = this.queryActivityUserData();
		if (CollectionUtils.isEmpty(list)){
			return ;
		}
		
		// 获取组合资产信息
		List<StockCurrent>  stockCurrents = userTradeService.getAllStockCurrents();
		if (CollectionUtils.isEmpty(stockCurrents)) {
			return;
		}
		List<ActivityProfitRecord>  activityProfitRecords = Lists.newArrayList();
		for (ActivityProfitRecordVo recordVo : list){
			if (2 == recordVo.getStatus()){
				
				ActivityProfitRecord activityProfitRecord  = new ActivityProfitRecord();
				activityProfitRecord.setUid(recordVo.getUid());
				activityProfitRecord.setMobile(recordVo.getMobile());
				activityProfitRecord.setTradeGroupId(recordVo.getTradeGroupId());
				activityProfitRecord.setProfit(recordVo.getAccrual());
				activityProfitRecord.setProfitRate(BigDecimalUtils.mulRound(BigDecimalUtils.div(recordVo.getAccrual(),beginMoney,3),100)+"%");
				activityProfitRecord.setProfitDate(Dates.getCurrentLongDay());
				activityProfitRecords.add(activityProfitRecord);
				continue;
			}
			
			String fundAccount = recordVo.getParentAccountNo();
			String combineId = recordVo.getCombineId();
			if (StringUtil.isBlank(combineId) || StringUtil.isBlank(fundAccount)){
				continue;
			}
			
			for (StockCurrent stockCurrent:stockCurrents){
				String sfundAccount = stockCurrent.getFundAccount();
				String scombineId = stockCurrent.getCombineId();
				if (StringUtil.equals(scombineId, combineId) 
						&& StringUtil.equals(sfundAccount, fundAccount)){
					double assetTotalValue = BigDecimalUtils.addRound(stockCurrent.getCurrentCash(), stockCurrent.getMarketValue());					
					ActivityProfitRecord activityProfitRecord  = new ActivityProfitRecord();
					activityProfitRecord.setUid(recordVo.getUid());
					activityProfitRecord.setMobile(recordVo.getMobile());
					activityProfitRecord.setTradeGroupId(recordVo.getTradeGroupId());
					Double  profit = BigDecimalUtils.subRound(assetTotalValue,beginMoney);
					activityProfitRecord.setProfit(profit);
					activityProfitRecord.setProfitRate(BigDecimalUtils.mulRound(BigDecimalUtils.div(profit,beginMoney,3),100)+"%");
					activityProfitRecord.setProfitDate(Dates.getCurrentLongDay());
					activityProfitRecords.add(activityProfitRecord);
					
				}
			}
		}
		
		if (CollectionUtils.isEmpty(activityProfitRecords)){
			return ;
		}

		List<ActivityProfitRecord> templist = Lists.newArrayList();
		for (ActivityProfitRecord activityProfitRecord:activityProfitRecords) {
			templist.add(activityProfitRecord);
			if (templist.size()==500){
				saves(templist);
				list.clear();
			}
		}
		saves(templist);
	}
}