package com.tzdr.business.cms.cpp.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzdr.business.cms.cpp.MockTradeAccountService;
import com.tzdr.business.cms.cpp.MockTradeMoneyAccountService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.cpp.DataSource;
import com.tzdr.domain.dao.cpp.MockTradeAccountDao;
import com.tzdr.domain.web.entity.cpp.MockTradeAccount;

@Service("mockTradeAccountService")
@Transactional
@DataSource(value = "dataSource2")
public class MockTradeAccountServiceImp extends BaseServiceImpl<MockTradeAccount, MockTradeAccountDao> implements MockTradeAccountService {
	@Autowired
	private MockTradeMoneyAccountService mockTradeMoneyAccountService;
	public boolean openMockAccount(String username,String password){
		boolean flag = false;
		List<MockTradeAccount> accounts = getEntityDao().doGetMockTradeAccountByUsername(username);
		if(accounts == null || accounts.size() == 0){
			List<Object[]> objects = new ArrayList<>();
			Object[] objects2 = new Object[]{username,password};
			objects.add(objects2);
			try {
				batchSave("insert into mock_trade_account(Username,Password) value(?,?)", 1, objects);
				mockTradeMoneyAccountService.openMockMoneyAccount(getEntityDao().doGetMockTradeAccountByUsernameOne(username).getId(), username);
				flag = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
}
