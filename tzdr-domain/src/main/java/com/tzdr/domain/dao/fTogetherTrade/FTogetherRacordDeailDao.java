package com.tzdr.domain.dao.fTogetherTrade;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FTogetherRecordDetail;

/**
 * Created by huangkai on 2016/5/20.
 */
public interface FTogetherRacordDeailDao extends BaseJpaDao<FTogetherRecordDetail, String> {
	
	/**
	 * 根据方案ID查询对应按合买时间升序排列的合买记录
	 * @param tradeId
	 * @return
	 */
	public List<FTogetherRecordDetail> findByTradeIdAndDirectionOrderByBuyTimeAsc(String tradeId,Integer direction);
}
