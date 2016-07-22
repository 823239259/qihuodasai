package com.tzdr.business.service.monthUserTradeParams.impl;

import java.io.Serializable;





import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.monthUserTradeParams.MonthUserTradeParamsService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.monthUserTradeParams.MonthUserTradeParamsDao;
import com.tzdr.domain.web.entity.MonthUserTradeParams;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MonthUserTradeParamsServiceImpl extends BaseServiceImpl<MonthUserTradeParams,MonthUserTradeParamsDao > implements
		MonthUserTradeParamsService {

	@Override
	public MonthUserTradeParams getParams() {
		MonthUserTradeParams monthUserTradeParams = this.getEntityDao().getAll().get(0);
		if(null != monthUserTradeParams){
			return monthUserTradeParams;
		}
		return null;
	}

	

}
