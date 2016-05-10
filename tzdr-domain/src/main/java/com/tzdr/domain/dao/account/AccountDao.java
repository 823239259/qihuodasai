package com.tzdr.domain.dao.account;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.ParentAccount;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
public interface AccountDao extends BaseJpaDao<Account, String> {
	
	public List<Account> findByParentAccountAndStatusAndDeletedFalse(ParentAccount parentAccount,short status);
	
	public List<Account> findByParentAccountAndDeletedFalse(ParentAccount parentAccount);
	
	public List<Account> findByAssetIdAndDeletedFalseAndStatusIn(String assetId,List<Short> statusList);
	
	public List<Account> findByAccountAndDeletedFalseAndStatusIn(String account,List<Short> statusList);
	
	public List<Account> findByAccountAndStatusAndDeletedFalse(String account,Short status);
	
}
