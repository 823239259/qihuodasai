package com.tzdr.business.service.drawMoney.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.drawMoney.UserBankService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.drawmoney.UserBankDao;
import com.tzdr.domain.web.entity.UserBank;


/**
 * 用户银行卡绑定
 * <P>title:@UserBankServiceImpl.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年1月7日
 * @version 1.0
 */
@Service("userBankService")
@Transactional
public class UserBankServiceImpl extends BaseServiceImpl<UserBank,UserBankDao> implements UserBankService{

	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.DrawMoney.UserBankService#findUserBankByuserId(java.lang.String)
	 */
	@Override
	public List<UserBank> findUserBankByuserId(String userId){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Boolean> sortMap=new HashMap<String,Boolean>();
		map.put("EQ_uid", userId);
		//sortMap.put("addtime", true);
		sortMap.put("isdefault", false);
		return this.getEntityDao().query(map, sortMap);
	}

	@Override
	public List<UserBank> findUserDefaultBankByuserId(String userId){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Boolean> sortMap=new HashMap<String,Boolean>();
		//map.put("EQ_isdefault", 1);
		map.put("EQ_uid", userId);
		//sortMap.put("addtime", true);
		
		return this.getEntityDao().query(map, sortMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.DrawMoney.UserBankService#findUsercardbycard(java.lang.String)
	 */
	@Override
	public UserBank findUsercardbycard(String bankcard,String userId) {
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Boolean> sortMap=new HashMap<String,Boolean>();
		map.put("EQ_uid", userId);
		map.put("EQ_card", bankcard);
		List<UserBank> banks=this.getEntityDao().query(map, sortMap);
		if(banks!=null && banks.size()>0){
			return banks.get(0);
		}
		return null;
	}

	/*
	 * 保存银行卡(non-Javadoc)
	 * @see com.tzdr.business.service.DrawMoney.UserBankService#saveCard(com.tzdr.domain.web.entity.UserBank)
	 */
	@Override
	public UserBank saveCard(UserBank bank) {
		return this.getEntityDao().save(bank);
	}

	/*
	 * 删除银行卡(non-Javadoc)
	 * @see com.tzdr.business.service.DrawMoney.UserBankService#delCard(java.lang.String)
	 */
	@Override
	public void delCard(String cardId) {
		UserBank bank=this.getEntityDao().get(cardId);
		this.getEntityDao().delete(bank);
		
	}

	/*
	 * 设置默认银行卡(non-Javadoc)
	 * @see com.tzdr.business.service.DrawMoney.UserBankService#setDefaulcard(java.lang.String)
	 */
	@Override
	public void setDefaulcard(String cardId,String userId) {
		List<UserBank> userbanks=findUserBankByuserId(userId);
		for(UserBank bank:userbanks){
			bank.setIsdefault((short)0);
			this.getEntityDao().update(bank);
		}
		UserBank ubank=this.getEntityDao().get(cardId);
		ubank.setIsdefault((short)1);
		this.getEntityDao().update(ubank);
		
	}

	@Override
	public String findCard(String cardId) {
		UserBank bankcard=this.getEntityDao().get(cardId);
		String card=bankcard.getCard();
		return card;
	}
}
