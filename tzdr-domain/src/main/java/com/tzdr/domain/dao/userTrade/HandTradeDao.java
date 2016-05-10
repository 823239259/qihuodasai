package com.tzdr.domain.dao.userTrade;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.HandTrade;


/**
 * 
 * @author zhouchen
 * 2015年4月27日 上午11:02:52
 */
public interface HandTradeDao extends BaseJpaDao<HandTrade, String>  {
	
	List<HandTrade> findByTradeId(String tradeId);
	
}
