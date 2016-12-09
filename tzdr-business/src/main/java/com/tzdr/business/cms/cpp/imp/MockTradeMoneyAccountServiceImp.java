package com.tzdr.business.cms.cpp.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tzdr.business.cms.cpp.MockTradeMoneyAccountService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.config.CppConfig;
import com.tzdr.cpp.DataSource;
import com.tzdr.domain.dao.cpp.MockTradeMoneyAccountDao;
import com.tzdr.domain.web.entity.cpp.MockTradeMoneyAccount;

@Service("mockTradeMoneyAccountService")
@Transactional
@DataSource(value = "dataSource2")
public class MockTradeMoneyAccountServiceImp extends BaseServiceImpl<MockTradeMoneyAccount, MockTradeMoneyAccountDao> implements MockTradeMoneyAccountService{
	@Override
	public void openMockMoneyAccount(Integer accountId, String username) {
		List<Map<String, Object>> maps =  getEntityDao().queryMapBySql("select CurrencyNo as currencyNo from a_currency_list", null);
		List<Object[]> mockTradeMoneyAccounts = new ArrayList<>();
		String sql = "insert into mock_trade_money_account(AccountId,TradeAccountUsername,InMoney,TodayCanUse,TodayAmount,CloseProfit,CounterFee,CurrencyNo"
				+ ",DayCloseProfit,DayFloatingProfit,Deposit,FloatingProfit,FrozenMoney,KeepDeposit,OldBalance,OldAmount,OldCanCashout,OldCanUse,OutMoney"
				+ ",Premium,RiskRate,TodayBalance,TodayCanCashout,TotalProfit,UnaccountProfit,UnexpiredProfit) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		for (int i = 0; i < maps.size(); i++) {
			Double money = CppConfig.INIT_MOCK_MONEY;
			String currencyNo = String.valueOf(maps.get(i).get("currencyNo"));
			if(!currencyNo.equals("USD")){
				money=0.00;
			}
			Object[] objects = new Object[]{
					accountId,username,money,money,money,0.00,0.00,currencyNo
					,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00
			};
			mockTradeMoneyAccounts.add(objects);
		}
		try {
			batchSave(sql, maps.size(), mockTradeMoneyAccounts);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
