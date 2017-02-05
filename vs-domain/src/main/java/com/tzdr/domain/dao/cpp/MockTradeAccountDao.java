package com.tzdr.domain.dao.cpp;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.cpp.MockTradeAccount;

public interface MockTradeAccountDao extends BaseJpaDao<MockTradeAccount, String>,JpaSpecificationExecutor<MockTradeAccount>{
	/**
	 * 
	 * @param username
	 * @return
	 */
	@Query("from MockTradeAccount where username = ?1")
	public List<MockTradeAccount> doGetMockTradeAccountByUsername(String username);
	@Query("from MockTradeAccount where username = ?1")
	public MockTradeAccount doGetMockTradeAccountByUsernameOne(String username);
}
