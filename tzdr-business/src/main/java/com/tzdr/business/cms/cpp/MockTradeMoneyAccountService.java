package com.tzdr.business.cms.cpp;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.cpp.MockTradeMoneyAccount;

public interface MockTradeMoneyAccountService extends BaseService<MockTradeMoneyAccount>{
	/**
	 * 生成模拟账号资金明细
	 * @param accountId
	 * @param username
	 * @return
	 */
	public void openMockMoneyAccount(Integer accountId,String username);
}
