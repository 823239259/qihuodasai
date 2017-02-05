package com.tzdr.business.service.tradeDetail;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
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
}
