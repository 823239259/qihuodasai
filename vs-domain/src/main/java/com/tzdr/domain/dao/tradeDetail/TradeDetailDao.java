package com.tzdr.domain.dao.tradeDetail;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.TradeDetail;

public interface TradeDetailDao extends BaseJpaDao<TradeDetail, String>{
	@SQLDelete(sql = "delete from  w_trade_detail where fast_id = ?1")
	public void deleteByFastId(String fastId);
	/**
	 * 根据方案id获取交易信息
	 * @param fastId
	 */
	@Query("from TradeDetail where fastId = ?1")
	public List<TradeDetail> doGetByFtseId(String fastId);
}
