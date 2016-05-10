package com.tzdr.domain.dao.userTrade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.TradeConfig;

/**
 * 
 * @zhouchen
 * 2015年1月7日
 */
public interface TradeConfigDao extends BaseJpaDao<TradeConfig, String>  {
	
	@Query("FROM TradeConfig WHERE dayRangeStart <= :day AND dayRangeEnd >= :day AND depositRangeStart < :deposit AND depositRangeEnd >= :deposit AND multipleRangeEnd >= :multiple AND multipleRangeStart <= :multiple ")
	public TradeConfig  findTradeConfig(@Param("day")Integer day,@Param("deposit")Double deposit,@Param("multiple")Integer multiple);
}
