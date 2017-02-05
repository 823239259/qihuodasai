package com.tzdr.business.service.comprehensiveCommodity.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityFeeService;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.comprehensiveCommodity.ComprehensiveCommodityFeeDao;
import com.tzdr.domain.web.entity.ComprehensiveCommodityFee;

@Service("companyCommissionBalanceService")
@Transactional
public class ComprehensiveCommodityFeeServiceImpl extends BaseServiceImpl<ComprehensiveCommodityFee,ComprehensiveCommodityFeeDao> implements ComprehensiveCommodityFeeService{
	
	private static final Logger logger = LoggerFactory.getLogger(ComprehensiveCommodityFeeService.class);
	@Override
	public void create(ComprehensiveCommodityFee companyCommissionBalance) {
		// TODO Auto-generated method stub
		this.save(companyCommissionBalance);
	}
	
}
