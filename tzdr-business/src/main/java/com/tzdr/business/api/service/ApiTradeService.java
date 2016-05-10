package com.tzdr.business.api.service;

import java.math.BigInteger;
import java.util.List;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.api.constants.Constant;
import com.tzdr.domain.api.vo.ApiTradeVo;
import com.tzdr.domain.api.vo.TradeDetail;
import com.tzdr.domain.dao.userTrade.UserTradeDao;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;


/**
 * TOKEN 服务service
 * @author zhouchen
 * 2015年5月21日 下午12:02:06
 */
@Service
@Transactional
public class ApiTradeService extends BaseServiceImpl<UserTrade,UserTradeDao> {
	public static final Logger logger = LoggerFactory.getLogger(ApiTradeService.class);	
	
	@Autowired
	private UserTradeService  userTradeService;
	
	
	@Autowired
	private WUserService  wUserService;
	/**
	 * 获取用户操盘中所有的配资方案id
	 * @param uid
	 * @return
	 */
	public UserTradeCmsVo findUserTradeIds(String uid) {
		String sql = "  SELECT uid,GROUP_CONCAT(id) userTradeId from w_user_trade where  `status`=1 AND activity_type = 0 and uid=?  GROUP BY uid ";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		@SuppressWarnings("unchecked")
		List<UserTradeCmsVo> list = nativeQuery(sql,params, UserTradeCmsVo.class);
		if (CollectionUtils.isEmpty(list)){
			return  null;
		}
		return list.get(0);
	}

	
	/**
	 * 用户的待补仓方案
	 * @param uid
	 * @return
	 */
	public List<UserTradeCmsVo> findUserMarginTrades(String uid) 
	{
		String sql = "SELECT trade.combine_id combineId, trade.account, trade.group_id groupId, trade.`password`, "
				+ " sum(trade.money) totalLending, sum(trade.lever_money) totalLeverMoney, sum(trade.append_lever_money) totalAppendLeverMoney, "
				+ " sum(trade.warning) warning, sum(trade.`open`) open, trade.starttime, trade.endtime, trade.`status`,"
				+ " trade.hs_belong_broker hsBelongBroker, trade.unit_number unitNumber, trade.parent_account_no parentAccountNo,"
				+ " trade.asset_id assetId FROM w_user_trade trade WHERE trade.`status` = 1 AND trade.fee_type IN (0, 1) AND uid =? "
				+ " GROUP BY trade.group_id";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		List<UserTradeCmsVo> list = nativeQuery(sql, params,UserTradeCmsVo.class);
		List<UserTradeCmsVo> tempTradeVos = Lists.newArrayList();
		if (CollectionUtils.isEmpty(list)) {
			return tempTradeVos;
		}

		// 获取组合资产信息
		List<StockCurrent> stockCurrents = userTradeService.getAllStockCurrents();
		if (CollectionUtils.isEmpty(stockCurrents)) {
			return tempTradeVos;
		}

		for (StockCurrent stockCurrent : stockCurrents) {
			String scombineId = stockCurrent.getCombineId();
			String sfundAccount = stockCurrent.getFundAccount();
			for (UserTradeCmsVo tradeVo : list) {
				String combineId = tradeVo.getCombineId();
				String parentAccountNo = tradeVo.getParentAccountNo();
				if (StringUtil.isBlank(combineId)
						|| StringUtil.isBlank(parentAccountNo)) {
					continue;
				}

				if (StringUtil.equals(scombineId, combineId)
						&& StringUtil.equals(sfundAccount, parentAccountNo)) {
					double assetTotalValue = BigDecimalUtils.addRound(
							stockCurrent.getCurrentCash(),
							stockCurrent.getMarketValue());
					double warning = tradeVo.getWarning();
					if (assetTotalValue > warning) {
						continue;
					}
					tradeVo.setAssetTotalValue(assetTotalValue);
					tradeVo.setMinimumDeposit(BigDecimalUtils.sub(warning,
							assetTotalValue));
					tempTradeVos.add(tradeVo);
				}
			}
		}
		return tempTradeVos;
	}
	
	
	/**
	 * 获取用户方案
	 * @param uid
	 * @return
	 */
	public List<ApiTradeVo> findUserTrades(String uid) 
	{
		String sql = " SELECT hand.audit_status auditStatus, hand.audit_end_status auditEndStatus, SUM(fund.money) arrearageMoney,"
				+ " trade.fee_type feeType, trade.account_id accountId, MIN(trade.addtime) addtime, trade.combine_id combineId, "
				+ " trade.account, trade.group_id groupId, trade.`password`, sum(trade.money) totalLending, sum(trade.lever_money) totalLeverMoney, "
				+ " sum(trade.append_lever_money) totalAppendLeverMoney, sum(trade.warning) warning, sum(trade.`open`) `open`, "
				+ " MAX(trade.accrual) totalAccrual, MAX(trade.natural_days) naturalDays, MIN(trade.starttime) starttime, trade.endtime,"
				+ " trade.`status`, trade.buy_status buyStatus, trade.sell_status sellStatus, trade.hs_belong_broker hsBelongBroker, "
				+ " trade.unit_number unitNumber, trade.parent_account_no parentAccountNo, trade.asset_id assetId FROM w_user_trade trade"
				+ " LEFT JOIN w_hand_trade hand ON hand.trade_id = trade.id LEFT JOIN w_user_fund fund ON fund.lid = trade.group_id AND"
				+ " fund.pay_status = 0 AND fund.type IN (11, 12) WHERE trade.uid =? and trade.activity_type in(0,1,2)  GROUP BY trade.group_id ORDER BY trade.addtime DESC  ";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		List<UserTradeCmsVo> list = nativeQuery(sql, params,UserTradeCmsVo.class);
		if (CollectionUtils.isEmpty(list)){
			return null;
		}
		List<ApiTradeVo> apiTradeVos = Lists.newArrayList();
		for (UserTradeCmsVo tradeCmsVo : list){
			ApiTradeVo apiTradeVo = new ApiTradeVo();
			apiTradeVo.setAccrual(tradeCmsVo.getTotalAccrual());
			apiTradeVo.setGroupId(tradeCmsVo.getGroupId());
			apiTradeVo.setTradeFund(tradeCmsVo.getTotalLending());
			apiTradeVo.setOperateFund(tradeCmsVo.getTotalOperateMoney());
			apiTradeVo.setLoanDays(tradeCmsVo.getNaturalDays());
			apiTradeVo.setFeeType(tradeCmsVo.getFeeType());
			apiTradeVo.setStatus(tradeCmsVo.getStatus());
			// 是否存在欠费
			Double arrearageMoney = tradeCmsVo.getArrearageMoney();
			if (ObjectUtil.equals(null, arrearageMoney) || arrearageMoney>=0){
				apiTradeVo.setArrearage(false);
			}else
			{
				apiTradeVo.setArrearage(true);
			}
			
			// 设置方案状态
			if (Constant.FEE_TYPE_WELLGOLD == tradeCmsVo.getFeeType()){
				//开户 中 
				if(Constant.AUDIT_STATUS_WAIT == tradeCmsVo.getAuditStatus()){ 
					apiTradeVo.setStatus(Constant.OPENING_ACCOUNT);
				}
				// 开户失败
				if(Constant.AUDIT_STATUS_NOT_PASS == tradeCmsVo.getAuditStatus()){ 
					apiTradeVo.setStatus(Constant.OPEN_FAIL);
				}
			}
			
			apiTradeVos.add(apiTradeVo);
		}
		return apiTradeVos;
	}
	
	/**
	 * 获取用户方案
	 * @param uid
	 * @return
	 */
	public TradeDetail findUserTradeDetail(String groupId,String uid) 
	{
		String sql = " SELECT hand.audit_status auditStatus, hand.audit_end_status auditEndStatus, trade.fee_type feeType, "
				+ " trade.account_id accountId, MIN(trade.addtime) addtime, trade.combine_id combineId, trade.account, trade.group_id groupId,"
				+ " trade.`password`, sum(trade.money) totalLending, sum(trade.lever_money) totalLeverMoney, sum(trade.append_lever_money) totalAppendLeverMoney, "
				+ " sum(trade.warning) warning, sum(trade.`open`) `open`, MAX(trade.accrual) totalAccrual, MAX(trade.natural_days) naturalDays,"
				+ " MIN(trade.starttime) starttime, trade.endtime, trade.`status`, trade.buy_status buyStatus, trade.sell_status sellStatus, "
				+ " trade.hs_belong_broker hsBelongBroker, trade.unit_number unitNumber, trade.parent_account_no parentAccountNo, trade.asset_id assetId, "
				+ " trade.fee_day feeDay, trade.fee_month feeMonth FROM w_user_trade trade LEFT JOIN w_hand_trade hand ON hand.trade_id = trade.id"
				+ " WHERE trade.group_id =? and trade.uid =? GROUP BY trade.group_id  ";
		List<Object> params = Lists.newArrayList();	
		params.add(groupId);
		params.add(uid);
		List<UserTradeCmsVo> list = nativeQuery(sql, params,UserTradeCmsVo.class);
		if (CollectionUtils.isEmpty(list)){
			return null;
		}
		UserTradeCmsVo tradeCmsVo = list.get(0);
		TradeDetail tradeDetail = new TradeDetail();
		tradeDetail.setAccrual(tradeCmsVo.getTotalAccrual());
		tradeDetail.setGroupId(tradeCmsVo.getGroupId());
		tradeDetail.setTradeFund(tradeCmsVo.getTotalLending());
		tradeDetail.setTotalFund(tradeCmsVo.getTotalOperateMoney());
		tradeDetail.setLoanDays(tradeCmsVo.getNaturalDays());
		tradeDetail.setFeeType(tradeCmsVo.getFeeType());
		tradeDetail.setStatus(tradeCmsVo.getStatus());
		tradeDetail.setCashFund(tradeCmsVo.getTotalLeverMoney());
		tradeDetail.setInterest(tradeCmsVo.getFeeMonth());
		tradeDetail.setMangerFee(tradeCmsVo.getFeeDay());
		tradeDetail.setPassword(tradeCmsVo.getPassword());
		tradeDetail.setAccount(tradeCmsVo.getAccount());
		tradeDetail.setStartTime(Dates.parseBigInteger2Date(tradeCmsVo.getStarttime(),Dates.CHINESE_DATE_FORMAT_LINE));
		if (!ObjectUtil.equals(null,tradeCmsVo.getEndtime())){
			tradeDetail.setEndTime(Dates.parseBigInteger2Date(tradeCmsVo.getEndtime(),Dates.CHINESE_DATETIME_FORMAT_LINE));			
		}
		// 设置方案状态
		if (Constant.FEE_TYPE_WELLGOLD == tradeCmsVo.getFeeType()){
			//开户 中 
			if(Constant.AUDIT_STATUS_WAIT == tradeCmsVo.getAuditStatus()){ 
				tradeDetail.setStatus(Constant.OPENING_ACCOUNT);
			}
			// 开户失败
			if(Constant.AUDIT_STATUS_NOT_PASS == tradeCmsVo.getAuditStatus()){ 
				tradeDetail.setStatus(Constant.OPEN_FAIL);
			}
		}
		
		return tradeDetail;
	}
	
	
	/**
	 * 获取用户操盘,验资中方案数
	 * @return
	 */
	public int findUserOperateTrades(String uid) {
		String sql = "  SELECT count(0) from w_user_trade where  `status`in (0,1) AND activity_type in(0,1,2) and  type=0 and uid=? ";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		BigInteger count = (BigInteger) nativeQueryOne(sql, params);
		return  count.intValue();
	}
	
	public List<UserTrade> findByWuserAndGroupId(String uid,String groupId) {
		WUser wuser = wUserService.getUser(uid);
		return getEntityDao().findByWuserAndGroupIdOrderByAddtimeAsc(wuser,groupId);
	}
	
}
