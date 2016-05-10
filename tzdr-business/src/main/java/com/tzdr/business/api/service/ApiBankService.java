package com.tzdr.business.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.drawmoney.UserBankDao;
import com.tzdr.domain.web.entity.UserBank;


/**
 * TOKEN 服务service
 * @author zhouchen
 * 2015年5月21日 下午12:02:06
 */
@Service
@Transactional
public class ApiBankService extends  BaseServiceImpl<UserBank,UserBankDao> {
	
	public static final Logger logger = LoggerFactory.getLogger(ApiBankService.class);	

	/**
	 * 根据银行卡id 和用户id查询银行卡信息
	 * @param bankId
	 * @param userId
	 * @return
	 */
	public UserBank findByIdAndUserId(String bankId,String userId){
		return this.getEntityDao().findByIdAndUid(bankId, userId);
	}
}
