package com.tzdr.domain.dao.fTogetherTrade;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FTogetherConjuncture;

/**
 * Created by huangkai on 2016/5/20.
 */
public interface FTogetherConjunctureDao extends BaseJpaDao<FTogetherConjuncture, String> {
	
	public List<FTogetherConjuncture> findByTradeIdOrderByMinutesAsc(String tradeId);
	
	
	public List<FTogetherConjuncture> findByTradeIdAndPointIsNotNullOrderByMinutesDesc(String tradeId);
}
