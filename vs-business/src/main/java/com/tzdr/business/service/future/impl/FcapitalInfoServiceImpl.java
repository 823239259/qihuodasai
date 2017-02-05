package com.tzdr.business.service.future.impl;


import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.future.FcapitalInfoService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.future.FcapitalInfoDao;
import com.tzdr.domain.web.entity.future.FcapitalInfo;


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
public class FcapitalInfoServiceImpl extends BaseServiceImpl<FcapitalInfo, FcapitalInfoDao> implements FcapitalInfoService {

	@Override
	public BigDecimal queryCumulativeTrans(String faid) {
		// TODO Auto-generated method stub
		return getEntityDao().queryCumulativeTrans(faid);
	}

}
