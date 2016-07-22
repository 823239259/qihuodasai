package com.tzdr.business.service.stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.realdeal.RealDealService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.stock.StockCurrentHistoryDao;
import com.tzdr.domain.vo.HundSunFundVo;
import com.tzdr.domain.vo.UserFundsNewVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.StockCurrentHistory;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
@Service
@Transactional
public class StockCurrentHistoryService extends BaseServiceImpl<StockCurrentHistory,StockCurrentHistoryDao> {
	public static final Logger logger = LoggerFactory.getLogger(StockCurrentHistoryService.class);
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private RealDealService realDealService;
	
	@Autowired
	private ParentAccountService parentAccountService;

	public void saveDatas(){
		String sql = " SELECT trade.account_id accountId, trade.`status`, wuser.id uid, acc.account_name accountName, verified.tname uname, trade.combine_id combineId, trade.group_id groupId, trade.parent_account_no parentAccountNo, trade.asset_id assetId FROM w_user_trade trade, w_user wuser, w_user_verified verified, w_account acc WHERE wuser.id = trade.uid AND wuser.id = verified.uid AND trade.account_id = acc.id AND trade.fee_type < 2 GROUP BY trade.group_id ORDER BY trade.addtime ASC ";
		List<UserTradeCmsVo> list = nativeQuery(sql,null, UserTradeCmsVo.class);
		if (CollectionUtils.isEmpty(list)){
			return;
		}
		
		List<StockCurrent> stockCurrents = userTradeService.getAllStockCurrents();
		if (CollectionUtils.isEmpty(stockCurrents)){
			logger.info("定时任务获取资产信息失败。");
			return;
		}
		
		List<StockCurrentHistory> tempHistories = Lists.newArrayList();
		for (UserTradeCmsVo tradeCmsVo:list){
			
			String combineId=tradeCmsVo.getCombineId();
			String assetId = tradeCmsVo.getAssetId();
			String fundAccount = tradeCmsVo.getParentAccountNo();
			
			if (StringUtil.isBlank(combineId) 
					|| StringUtil.isBlank(assetId) 
					|| StringUtil.isBlank(fundAccount)){
				continue;
			}
			
			for (StockCurrent stockCurrent:stockCurrents){
				String scombineId=stockCurrent.getCombineId();
				long sassetId = stockCurrent.getAssetId();
				String sfundAccount = stockCurrent.getFundAccount();
				
				if (StringUtil.equals(combineId, scombineId)
						&& StringUtil.equals(sfundAccount, fundAccount)
						&& sassetId==NumberUtils.toLong(assetId)){
					tempHistories.add(new StockCurrentHistory(stockCurrent,tradeCmsVo.getAccountName(), tradeCmsVo.getGroupId()));
				}
			}
			
			if (tempHistories.size()==500){
				saves(tempHistories);
				tempHistories.clear();
			}
		}
		
		if (!CollectionUtils.isEmpty(tempHistories)){
			saves(tempHistories);
			tempHistories.clear();
		}
				
		List<ParentAccount> parentAccounts =  parentAccountService.findAvailableDatas();
		if (CollectionUtils.isEmpty(parentAccounts)){
			return;
		}
		
		for (ParentAccount parentAccount:parentAccounts){
			String fundAccount = parentAccount.getAccountNo();
			long  assertId = NumberUtils.toLong(parentAccount.getUnitNumber());
			for (StockCurrent stockCurrent:stockCurrents){
				long sassetId = stockCurrent.getAssetId();
				String sfundAccount = stockCurrent.getFundAccount();
				
				if (StringUtil.equals(sfundAccount, fundAccount) && sassetId == assertId){
					tempHistories.add(new StockCurrentHistory(stockCurrent,parentAccount.getAccountName(),null));
				}
		}
	}
	
		if (!CollectionUtils.isEmpty(tempHistories)){
			saves(tempHistories);
		}
	}
	
	public List<HundSunFundVo> getHundSunFunds(ConnditionVo connVo){
		String queryDate=(String) connVo.getValue("search_date");
		String hsBelongBroker=(String) connVo.getValue("hsBelongBroker");
		String uname=(String) connVo.getValue("uname");
		String accountName=(String) connVo.getValue("accountName");
		
		long yestodayBeginTime = Dates.getDatebyDaynum(-2);
		long beginTime = Dates.getDatebyDaynum(-1);
		long endTime = Dates.getLastLongDayNum(-1);
		
		if (StringUtil.isNotBlank(queryDate)){
				String beginDate  = queryDate+" 00:00:00";
		        String endDate = queryDate+" 23:59:59";
		        beginTime = Dates.parse(beginDate,Dates.CHINESE_DATETIME_FORMAT_LINE).getTime()/1000;
		        endTime = Dates.parse(endDate,Dates.CHINESE_DATETIME_FORMAT_LINE).getTime()/1000;
		        yestodayBeginTime = beginTime-86400;
		}
		String inteDate = Dates.format(new Date(beginTime*1000),Dates.CHINESE_DATE_FORMAT_LONG);

		String sql = "select * from (SELECT getAccountReaclMoney(wpa.unit_number,wpa.account_no,?) stockAssets,getAccountReaclNumber(wpa.unit_number,wpa.account_no,?) stockNumber,0 type,wpa.account_no parentAccountNo,wpa.unit_number assetId, wpa.unit_number accountName, wpa.unit_number uname, wpa.account_name hsBelongBroker,wpa.account_name realName, temp4.changeFund, get_yestoday_balance ( wpa.account_name,?) yestodayBalance, get_yestoday_balance ( wpa.account_name,?) currentBalance FROM (SELECT * from w_parent_account where deleted=0) wpa LEFT JOIN ( SELECT temp3.parent_account_no, ROUND(SUM(temp3.changeFund),2) changeFund FROM ( SELECT temp1.parent_account_no, IFNULL(temp2.money, 0) changeFund FROM ( SELECT ut.parent_account_no, ut.group_id, ut.uid FROM w_user_trade ut, w_account acc WHERE acc.id = ut.account_id GROUP BY ut.account_id ) temp1 LEFT JOIN ( SELECT lid, uid, SUM(money) money FROM w_user_fund WHERE pay_status = 1 AND type IN (10, 15, 16, 17, 18) AND addtime BETWEEN ? AND ? GROUP BY lid ) temp2 ON temp1.group_id = temp2.lid AND temp2.uid = temp1.uid ) temp3 GROUP BY temp3.parent_account_no ) temp4 on wpa.account_no = temp4.parent_account_no) temp5 ";
		List<Object> params = Lists.newArrayList();
		params.add(inteDate);
		params.add(inteDate);
		params.add(yestodayBeginTime);
		params.add(beginTime);
		params.add(beginTime);
		params.add(endTime);
		if (StringUtil.isNotBlank(hsBelongBroker)){
			sql = sql + " where temp5.hsBelongBroker  like ? ";
			params.add("%"+hsBelongBroker+"%");
		}
		if (StringUtil.isNotBlank(uname)){
			sql = sql + (params.size()==6?" where temp5.uname like ? ":"and temp5.uname like ? ");
			params.add("%"+uname+"%");
		}
		if (StringUtil.isNotBlank(accountName)){
			sql = sql + (params.size()==6?" where temp5.accountName  like ? ":" and temp5.accountName like ? ");
			params.add("%"+accountName+"%");
		}
		List<HundSunFundVo> hundSunFundVos = nativeQuery(sql, params, HundSunFundVo.class);
		
		String accountSql = "select * from (SELECT getAccountReaclMoney(temp1.combine_id,temp1.parent_account_no,?) stockAssets,getAccountReaclNumber(temp1.combine_id,temp1.parent_account_no,?) stockNumber,1 type,temp1.asset_id assetId, temp1.parent_account_no parentAccountNo, temp1.account account, pa.account_name parentAccountName, temp1.account_name accountName, pa.account_name hsBelongBroker, temp1.mobile uname, temp1.combine_id combineId, temp1.tname realName, temp1.group_id groupId, temp1.lever_money + temp1.append_lever_money LeverMoney, CAST(temp1.lever AS CHAR) lever, CAST(temp1.natural_days as CHAR) naturalDays, ROUND(IFNULL(temp2.money, 0),2) changeFund, temp1.`status`, get_yestoday_balance ( temp1.account_name,?) yestodayBalance, get_yestoday_balance ( temp1.account_name, ? ) currentBalance FROM ( SELECT ut.parent_account_no, ut.hs_belong_broker, us.mobile, uv.tname, acc.account_name, ut.group_id, ut.uid, SUM(ut.lever_money) lever_money, SUM(ut.append_lever_money) append_lever_money, ut.lever, MAX(ut.natural_days) natural_days, ut.`status`,ut.asset_id, ut.combine_id, ut.account FROM w_user_trade ut, w_user us, w_user_verified uv, w_account acc WHERE ut.uid = us.id AND uv.uid = us.id AND acc.id = ut.account_id GROUP BY ut.account_id ORDER BY ut.parent_account_no DESC) temp1 LEFT JOIN ( SELECT lid, uid, SUM(money) money FROM w_user_fund WHERE pay_status = 1 AND type IN (10, 15, 16, 17, 18) AND addtime BETWEEN ? AND ? GROUP BY lid ) temp2 ON temp1.group_id = temp2.lid AND temp2.uid = temp1.uid LEFT JOIN w_parent_account pa ON pa.account_no = temp1.parent_account_no ) temp5 ";
		List<Object> accountParams = Lists.newArrayList();
		accountParams.add(inteDate);
		accountParams.add(inteDate);
		accountParams.add(yestodayBeginTime);
		accountParams.add(beginTime);
		accountParams.add(beginTime);
		accountParams.add(endTime);
		if (StringUtil.isNotBlank(hsBelongBroker)){
			accountSql = accountSql + "  where temp5.hsBelongBroker like ?  ";
			accountParams.add("%"+hsBelongBroker+"%");
		}
		if (StringUtil.isNotBlank(uname)){
			accountSql = accountSql + (accountParams.size()==6?"  where temp5.uname like ? ":"and temp5.uname like ? ");
			accountParams.add("%"+uname+"%");
		}
		if (StringUtil.isNotBlank(accountName)){
			accountSql = accountSql  + (accountParams.size()==6?" where temp5.accountName like ? ":" and temp5.accountName like ? ");
			accountParams.add("%"+accountName+"%");
		}
		
		List<HundSunFundVo> hundSunAccountFundVos = nativeQuery(accountSql, accountParams, HundSunFundVo.class);
		
		 hundSunFundVos.addAll(hundSunAccountFundVos);
		 
		 // 计算相关参数
		 if (CollectionUtils.isEmpty(hundSunFundVos)){
			 return  null;
		 }
		 
		 double totalLeverMoney = 0; //保证金合计
		 double totalLever = 0; // 倍数合计
		 double totalDays= 0; //天数合计
		 double totalStockNumber = 0; //股票成交数合计
		 double totalStockAssets = 0; // 股票成交金额
		 double totalYestodayBalance = 0; //前一日账户余额合计
		 double totalChangeFund = 0; //资金划拨合计
		 double totalCommission = 0; //佣金差合计
		 double totalTransferFee = 0; //过户费差合计
		 double totalAccval = 0; //累计盈亏合计
		 double totalBalance = 0; //账户余额合计
		 //总的配资数
		 Double accountVoSize = NumberUtils.toDouble(String.valueOf(hundSunAccountFundVos.size()));
		 if (accountVoSize==0){
			 accountVoSize=1.0;
		 }
		 
		 List<StockCurrent> stockCurrents = Lists.newArrayList();
		 if (beginTime==Dates.getCurrentLongDay()){
			 stockCurrents = userTradeService.getAllStockCurrents();			 
		 }
		 for (HundSunFundVo hundSunFundVo:hundSunFundVos){
			 String assertId = hundSunFundVo.getAssetId();
			 String combineId = hundSunFundVo.getCombineId();
			 String fundAccount = hundSunFundVo.getParentAccountNo();
			 //判断日期并获取 账户余额
			 if (beginTime==Dates.getCurrentLongDay()){
				 for (StockCurrent stockCurrent:stockCurrents){
					 String sassertId = String.valueOf(stockCurrent.getAssetId());
					 String sfundAccount = stockCurrent.getFundAccount();
					 if (StringUtil.equals(fundAccount, sfundAccount) && StringUtil.equals(assertId,sassertId)){
						 hundSunFundVo.setCurrentBalance(BigDecimalUtils.addRound(stockCurrent.getCurrentCash(), stockCurrent.getMarketValue()));
					 }
				 }
			 }
			 //累计盈亏
			 hundSunFundVo.setAccrual(BigDecimalUtils.subRound(BigDecimalUtils.subRound(hundSunFundVo.getCurrentBalance(),hundSunFundVo.getYestodayBalance()),hundSunFundVo.getChangeFund()));
		 
			 //子账户持股 数
			// hundSunFundVo.setStockNumber(realDealService.getAccountReaclNumber(inteDate, fundAccount, combineId));
			 //hundSunFundVo.setStockAssets(realDealService.getAccountReaclMoney(inteDate, fundAccount, combineId));
			 
			 //计算合计
			 totalLeverMoney = BigDecimalUtils.addRound(totalLeverMoney,hundSunFundVo.getLeverMoney());
			 totalLever = BigDecimalUtils.addRound(totalLever,NumberUtils.toDouble(hundSunFundVo.getLever()));
			 totalDays = BigDecimalUtils.addRound(NumberUtils.toDouble(hundSunFundVo.getNaturalDays()),totalDays);
			 totalStockNumber = BigDecimalUtils.addRound(NumberUtils.toDouble(hundSunFundVo.getStockNumber()),totalStockNumber);
			 totalYestodayBalance = BigDecimalUtils.addRound(totalYestodayBalance,hundSunFundVo.getYestodayBalance());
			 totalStockAssets = BigDecimalUtils.addRound(totalStockAssets,hundSunFundVo.getStockAssets());
			 totalChangeFund = BigDecimalUtils.addRound(totalChangeFund,hundSunFundVo.getChangeFund());
			 totalCommission = BigDecimalUtils.addRound(totalCommission,hundSunFundVo.getCommission());
			 totalTransferFee = BigDecimalUtils.addRound(totalTransferFee,hundSunFundVo.getTransferFee());
			 totalAccval = BigDecimalUtils.addRound(totalAccval,hundSunFundVo.getAccrual());
			 totalBalance = BigDecimalUtils.addRound(totalBalance,hundSunFundVo.getCurrentBalance());
		 }
		 
		 HundSunFundVo hundSunFundVo = new HundSunFundVo(String.valueOf(BigDecimalUtils.divRound(totalDays,accountVoSize)),
				 String.valueOf(BigDecimalUtils.divRound(totalLever,accountVoSize)), 
				 totalTransferFee, totalLeverMoney, totalCommission, totalChangeFund,
				 totalAccval, totalYestodayBalance, totalStockAssets, String.valueOf(totalStockNumber),"资金合计", totalBalance);
		 hundSunFundVos.add(hundSunFundVo);
		 return hundSunFundVos;
	}
	
	
	public List<UserFundsNewVo> getUserFunds(ConnditionVo connVo){
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		paramNames.add("beginTime");
		paramNames.add("endTime");
		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		if (starttimeStr_start != null && starttimeStr_end != null) {
			params.add(TypeConvert.strToZeroDate(starttimeStr_start,0).getTime()/1000);
			params.add(TypeConvert.strToZeroDate(starttimeStr_end,1,-1).getTime()/1000);
		}
		else {
			params.add(TypeConvert.longCriticalTimeDay(0));
			params.add(TypeConvert.strToZeroDate(TypeConvert.dbDateYmd(),1,-1).getTime()/1000);
		}
		
		StringBuffer sqlBuf = new StringBuffer("SELECT u.mobile,real_name realName,card_capital_margin cardCapitalMargin,balance_capital_margin balanceCapitalMargin,amount_capital amountCapital,lastday_balance lastdayBalance,income_recharge incomeRecharge,income_rebate incomeRebate,income_other incomeOther,profit,management_fee managementFee,profit_money profitMoney,-`revoke_manager_money` revokeManagerMoney,interest_fee interestFee,-revoke_interest revokeInterest,deduction_fee deductionFee,actual_fee actualFee,draw_fee drawFee,drawing_fee drawingFee,cover_money AS coverMoney,all_money AS allMoney,plat_balance platBalance,fund_date fundDate	FROM w_userfund_record,w_user u WHERE uid=u.id AND fund_date>=:beginTime and fund_date<=:endTime ");
		
		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			paramNames.add("mobile");
			sqlBuf.append(" AND u.mobile LIKE :mobile");
			params.add(mobile + "%");
		}
		
		String realName = connVo.getValueStr("realName");
		if (realName != null) {
			paramNames.add("realName");
			sqlBuf.append(" AND real_name LIKE :realName");
			params.add(realName + "%");
		}
		return this.getEntityDao().queryDataByParamNameSql(sqlBuf.toString(),
				UserFundsNewVo.class,paramNames.toArray(new String[paramNames.size()]), params.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public PageInfo<UserFundsNewVo> queryPageDataListVo(PageInfo<UserFundsNewVo> dataPage,ConnditionVo connVo){
		
		Map<String, Object> paramValues = new HashMap<String, Object>();
		
		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		if (starttimeStr_start != null && starttimeStr_end != null) {
			paramValues.put("beginTime", TypeConvert.strToZeroDate(starttimeStr_start,0).getTime()/1000);
			paramValues.put("endTime", TypeConvert.strToZeroDate(starttimeStr_end,1,-1).getTime()/1000);
		}
		else {
			paramValues.put("beginTime", TypeConvert.longCriticalTimeDay(0));
			paramValues.put("endTime", TypeConvert.strToZeroDate(TypeConvert.dbDateYmd(),1,-1).getTime()/1000);
		}
		
		StringBuffer sqlBuf = new StringBuffer("SELECT u.mobile,real_name realName,card_capital_margin cardCapitalMargin,balance_capital_margin balanceCapitalMargin,amount_capital amountCapital,lastday_balance lastdayBalance,income_recharge incomeRecharge,income_rebate incomeRebate,income_other incomeOther,profit,management_fee managementFee,profit_money profitMoney,-`revoke_manager_money` revokeManagerMoney,interest_fee interestFee,-revoke_interest revokeInterest,deduction_fee deductionFee,actual_fee actualFee,draw_fee drawFee,drawing_fee drawingFee,cover_money AS coverMoney,all_money AS allMoney,plat_balance platBalance,fund_date fundDate FROM w_userfund_record,w_user u WHERE uid=u.id AND fund_date>=:beginTime and fund_date<=:endTime ");
		
		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			sqlBuf.append(" AND u.mobile LIKE :mobile");
			paramValues.put("mobile", mobile + "%");
		}
		
		String realName = connVo.getValueStr("realName");
		if (realName != null) {
			sqlBuf.append(" AND real_name LIKE :realName");
			paramValues.put("realName", realName + "%");
		}
		PageInfo<UserFundsNewVo> pageInfo= getEntityDao().queryDataByParamsSql(dataPage, sqlBuf.toString(),UserFundsNewVo.class,paramValues,null);
			
		return pageInfo;
	}
	
	@SuppressWarnings("unchecked")
	public UserFundsNewVo getDataTotalVo(ConnditionVo connVo) {
		
		Map<String, Object> paramValues = new HashMap<String, Object>();
		
		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		if (starttimeStr_start != null && starttimeStr_end != null) {
			paramValues.put("beginTime", TypeConvert.strToZeroDate(starttimeStr_start,0).getTime()/1000);
			paramValues.put("endTime", TypeConvert.strToZeroDate(starttimeStr_end,1,-1).getTime()/1000);
		}
		else {
			paramValues.put("beginTime", TypeConvert.longCriticalTimeDay(0));
			paramValues.put("endTime", TypeConvert.strToZeroDate(TypeConvert.dbDateYmd(),1,-1).getTime()/1000);
		}
		
		StringBuffer sqlBuf = new StringBuffer("SELECT IFNULL(SUM(card_capital_margin),0) cardCapitalMargin,IFNULL(SUM(balance_capital_margin),0) balanceCapitalMargin,IFNULL(SUM(amount_capital),0) amountCapital,IFNULL(SUM(lastday_balance),0) lastdayBalance,IFNULL(SUM(income_recharge),0) incomeRecharge,IFNULL(SUM(income_rebate),0) incomeRebate,IFNULL(SUM(income_other),0) incomeOther,ROUND(IFNULL(SUM(profit),0),2) profit,IFNULL(SUM(management_fee),0) managementFee,IFNULL(SUM(profit_money), 0) profitMoney,IFNULL(SUM(interest_fee),0) interestFee,IFNULL(SUM(deduction_fee),0) deductionFee,IFNULL(SUM(actual_fee),0) actualFee,IFNULL(SUM(draw_fee),0) drawFee,IFNULL(SUM(drawing_fee),0) drawingFee,IFNULL(SUM(cover_money),0) AS coverMoney,IFNULL(SUM(all_money),0) AS allMoney,IFNULL(SUM(plat_balance),0) platBalance FROM w_userfund_record,w_user u WHERE uid=u.id AND fund_date>=:beginTime and fund_date<=:endTime ");
		
		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			sqlBuf.append(" AND u.mobile LIKE :mobile");
			paramValues.put("mobile", mobile + "%");
		}
		
		String realName = connVo.getValueStr("realName");
		if (realName != null) {
			sqlBuf.append(" AND real_name LIKE :realName");
			paramValues.put("realName", realName + "%");
		}
		
		List<UserFundsNewVo> userFundsNewVoList =  this.getEntityDao().queryDataByParamsSql(sqlBuf.toString(),
				UserFundsNewVo.class,paramValues,null);
		
		UserFundsNewVo userFundsNewVo = null;
		
		if(userFundsNewVoList != null && userFundsNewVoList.size() > 0){
			userFundsNewVo = userFundsNewVoList.get(0);
		}
		
		return userFundsNewVo;
	}
}
 