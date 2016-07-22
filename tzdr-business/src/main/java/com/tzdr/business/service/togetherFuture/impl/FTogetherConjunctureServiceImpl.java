package com.tzdr.business.service.togetherFuture.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.togetherFuture.FTogetherConjunctureService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.fTogetherTrade.FTogetherConjunctureDao;
import com.tzdr.domain.web.entity.FTogetherConjuncture;

/**
 * Created by huangkai on 2016/5/20.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FTogetherConjunctureServiceImpl extends BaseServiceImpl<FTogetherConjuncture,FTogetherConjunctureDao> implements FTogetherConjunctureService {

	@Override
	public List<FTogetherConjuncture> findByTradeID(String tradeID) {
		return this.getEntityDao().findByTradeIdOrderByMinutesAsc(tradeID);
	}
}
