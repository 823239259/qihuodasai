package com.tzdr.domain.dao.cpp;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.cpp.MockTradeMoneyAccount;

public interface MockTradeMoneyAccountDao extends BaseJpaDao<MockTradeMoneyAccount, String>,JpaSpecificationExecutor<MockTradeMoneyAccount>{
	
}
