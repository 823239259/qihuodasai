package com.tzdr.business.service.future.impl;

import java.math.BigDecimal;




import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.future.FuserTradeService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.future.FuserTradeDao;
import com.tzdr.domain.web.entity.future.FuserTrade;


/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年7月23日下午4:46:30
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class FuserTradeServiceImpl extends BaseServiceImpl<FuserTrade, FuserTradeDao> implements FuserTradeService {


	@Override
	public BigDecimal queryCautionMoney(String faid, Integer state,
			Integer actualType) {
		// TODO Auto-generated method stub
		return getEntityDao().queryCautionMoney(faid, state, actualType);
	}

	@Override
	public int getRowCount(String faid, Integer state, Integer actualType) {
		// TODO Auto-generated method stub
		return getEntityDao().getRowCount(faid, state, actualType);
	}

	@Override
	public BigDecimal queryBusinessMoney(String faid, Integer state,
			Integer actualType) {
		// TODO Auto-generated method stub
		return getEntityDao().queryBusinessMoney(faid, state, actualType);
	}

	@Override
	public BigDecimal queryGainMoney(String faid, Integer state,
			Integer actualType) {
		// TODO Auto-generated method stub
		return getEntityDao().queryGainMoney(faid, state, actualType);
	}
	
	
}
