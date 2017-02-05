package com.tzdr.business.service.togetherTrade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.togetherTrade.TogetherTradeDao;
import com.tzdr.domain.vo.cms.TogetherUserTradeVo;
import com.tzdr.domain.web.entity.TogetherTrade;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TogetherTradeService extends BaseServiceImpl<TogetherTrade, TogetherTradeDao> {

	private static Logger logger = LoggerFactory.getLogger(TogetherTradeService.class);

	@Autowired
	private TradeDayService tradeDayService;

	/**
	 * 根据groupid查找合买方案
	 * 
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getByGroupId(String groupId) {
		if (StringUtil.isBlank(groupId)) {
			logger.info("groupId is null");
			return null;
		}
		String sql = "select id, tid, gid, profit_ratio, profit_money, type from w_together_trade wtt where wtt.gid = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		Map<String, Object> result = ((List<Map<String, Object>>) nativeQuery(sql, params)).get(0);

		return result;
	}

	/**
	 * 根据groupid股票合买盈利分成
	 * 
	 * @param groupId
	 * @param profitMoney
	 */
	public void updateByGroupId(String groupId, double profitMoney) {
		if (StringUtil.isBlank(groupId) || profitMoney < 0) {
			logger.info("groupId is null");
			return;
		}
		String sql = "update w_together_trade wtt set wtt.profit_money = ? where wtt.gid = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(profitMoney);
		params.add(groupId);

		int rows = nativeUpdate(sql, params);
		if (rows > 0) {
			logger.info("update profit money success!");
		} else {
			logger.info("update profit money failed!");
		}
	}

	/**
	 * 股票合买列表
	 * 
	 * @param countOfCurrentPage
	 * @param currentPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryTogetherTradeList(int countOfCurrentPage, int currentPage, Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(countOfCurrentPage, currentPage);
		// params 查询参数依次存入
		List<Object> params = Lists.newArrayList();

		StringBuffer sql = new StringBuffer("select ta.tid, ta.gid, ifnull(ta.totalLeverMoney, 0) totalLeverMoney, ifnull(ta.money, 0) money, ta.naturalDays, ta.annualizeRate, ta.profitRatio, ");
		sql.append(" ta.tStatus, ta.addTime, ta.startTime, ta.estimateEndtime, ta.tradeDays, ta.delayDays, ifnull(ta.returnRate, 0) returnRate ");
		sql.append(" from (select wtt.tid, wtt.gid, wut.total_lever_money totalLeverMoney, wut.money, concat('T+', wut.natural_days) naturalDays, ");
		sql.append(" concat(round((wut.fee_month*365)/wut.money*100, 2), '%') annualizeRate, ");
		//sql.append(" (select concat(profit_ratio*100, '%') from w_together_config) profitRatio, ");
		sql.append(" concat(cast(wtt.profit_ratio*100 as decimal(9,2)), '%') profitRatio, ");
		sql.append(" case when wut.`status` = 0 then 1 when wut.`status` = 1 then 2 when wut.`status` = 2 and wht.audit_end_status = 1 then 3 else 4 end tStatus, ");
		sql.append(" wut.addtime addTime, wut.starttime startTime, wut.estimate_endtime estimateEndtime, 0 tradeDays, 0 delayDays, ");
		sql.append(" round((wut.finished_money-wut.total_lever_money)/(wut.lever_money+wut.append_lever_money)*100, 2) returnRate ");
		sql.append(" from w_together_trade wtt left join w_user_trade wut on wtt.tid = wut.id left join w_hand_trade wht on wut.id = wht.trade_id where 1 = 1) ta ");
		sql.append(" where 1 = 1 order by ta.tStatus, ta.addTime desc ");

		MultiListParam multilistParam = new MultiListParam(pageInfo, searchParams, sql.toString(), params);
		pageInfo = multiListPageQuery(multilistParam, TogetherUserTradeVo.class);
		List<Object> result = pageInfo.getPageResults();
		if (null != result && !result.isEmpty()) {
			String today = "", startDay = "",estimateEndtime="";
			
			int tradeDays = 0, delayDays = 0;
			for (Iterator<Object> iterator = result.iterator(); iterator.hasNext();) {
				TogetherUserTradeVo vo = (TogetherUserTradeVo) iterator.next();
				today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
				startDay = Dates.parseBigInteger2Date(vo.getStartTime(), Dates.CHINESE_DATE_FORMAT_LONG);
				tradeDays = tradeDayService.getTradeDays(startDay, today);
				vo.setTradeDays(tradeDays);
				estimateEndtime = Dates.parseBigInteger2Date(vo.getEstimateEndtime(), Dates.CHINESE_DATE_FORMAT_LONG);
				delayDays = tradeDayService.getTradeDays(estimateEndtime,today);
				vo.setDelayDays(delayDays);
				vo.setReturnRate(vo.getReturnRate());
			}
		}

		return pageInfo;
	}

}
