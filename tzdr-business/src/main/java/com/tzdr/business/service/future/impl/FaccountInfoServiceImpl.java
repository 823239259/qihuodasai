package com.tzdr.business.service.future.impl;

import java.math.BigDecimal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.future.FaccountInfoService;
import com.tzdr.business.service.future.FtradeParentAccountService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.dao.future.FaccountInfoDao;
import com.tzdr.domain.web.entity.future.FaccountInfo;


/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年7月23日下午4:46:23
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class FaccountInfoServiceImpl extends BaseServiceImpl<FaccountInfo, FaccountInfoDao> implements FaccountInfoService {
	
	@Autowired
	private FtradeParentAccountService ftradeParentAccountService;


	@Override
	public FaccountInfo findByUid(String uid) {
		// TODO Auto-generated method stub
		return getEntityDao().findByUid(uid);
	}


	@Override
	public FaccountInfo findOrSave(String userId) {
		// TODO Auto-generated method stub
		FaccountInfo accountInfo = findByUid(userId);
		if(accountInfo==null){
			accountInfo = new FaccountInfo();
			accountInfo.setUid(userId);
			accountInfo.setFtradeParentAccount(
					ftradeParentAccountService.queryAvailableAccount(Constant.PARENT_ACCOUNT_LIMIT_NUMBER));
			accountInfo.setAvlBalance(new BigDecimal(0));
			accountInfo.setBalance(new BigDecimal(0));
			accountInfo.setOperationRight(-1);
			accountInfo.setRestrictState(1);
			save(accountInfo);
		}
		return accountInfo;
	}
	
	
}
