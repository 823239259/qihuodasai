package com.tzdr.business.cms.cpp;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.cpp.MockTradeAccount;

public interface MockTradeAccountService extends BaseService<MockTradeAccount>{
	/**
	 * 开通模拟账号
	 * @param username 模拟账号
	 * @param password 模拟账号密码
	 * @return
	 */
	public boolean openMockAccount(String username,String password);
	/**
	 * 修改模拟账号
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean updateMockAccount(String username,String password);
}
