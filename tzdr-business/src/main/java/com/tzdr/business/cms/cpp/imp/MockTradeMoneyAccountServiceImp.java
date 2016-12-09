package com.tzdr.business.cms.cpp.imp;


import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tzdr.business.cms.cpp.MockTradeMoneyAccountService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.cpp.DataSource;
import com.tzdr.domain.dao.cpp.MockTradeMoneyAccountDao;
import com.tzdr.domain.web.entity.cpp.MockTradeMoneyAccount;

@Service("mockTradeMoneyAccountService")
@DataSource(value = "dataSource2")
@Transactional
public class MockTradeMoneyAccountServiceImp extends BaseServiceImpl<MockTradeMoneyAccount, MockTradeMoneyAccountDao> implements MockTradeMoneyAccountService{
	@Override
	public void openMockMoneyAccount(Integer accountId, String username) {
		
	}
}
