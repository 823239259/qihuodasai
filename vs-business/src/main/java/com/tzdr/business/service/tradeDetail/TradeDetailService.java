package com.tzdr.business.service.tradeDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.FPoundageParities;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.TradeDetail;

public interface TradeDetailService extends BaseService<TradeDetail>{
	/**
	 * 增加导入的交易信息
	 * @param detail
	 */
	public void doSaveTradeExclDetail(String detail,String fastId);
	/**
	 * 根据方案id获取交易信息
	 * @param fsteId
	 */
	public List<TradeDetail> doGetByFtseId(String fsteId);
	/**
	 * 根据方案id清理数据
	 * @param fastId
	 * @return
	 */
	public void deleteByFastId(String fastId);
	
	/**
	 * 根据成交号方案清理数据
	 * @param fastId
	 * @return
	 */
	public void deleteByTradeNo(String tradeNo);
	
	/**
	 * 根据交易账号获取成交记录
	 * @param tranAccount
	 * @return
	 */
	public List<TradeDetail> getByTranAccounts(String tranAccount);
	
	/**
	 * @param tradeDetails 成交记录	
	 * @param parities     结算汇率
	 * @param todayMoeny   账户今权益（今可用）
	 * @param traderBondSum  总的保证金
	 * @return
	 */
	public double countTranProfitLoss(List<TradeDetail> tradeDetails,List<FPoundageParities> parities,BigDecimal todayMoeny,BigDecimal traderTotal);
}
