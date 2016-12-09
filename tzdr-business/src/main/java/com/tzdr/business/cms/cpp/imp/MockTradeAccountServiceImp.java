package com.tzdr.business.cms.cpp.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tzdr.business.cms.cpp.MockTradeAccountService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.cpp.DataSource;
import com.tzdr.domain.dao.cpp.MockTradeAccountDao;
import com.tzdr.domain.web.entity.cpp.MockTradeAccount;

@Service("mockTradeAccountService")
@Transactional
@DataSource(value="dataSource2")
public class MockTradeAccountServiceImp extends BaseServiceImpl<MockTradeAccount, MockTradeAccountDao> implements MockTradeAccountService {
	public boolean openMockAccount(String username,String password){
		boolean flag = false;
		//List<MockTradeAccount> accounts = getEntityDao().doGetMockTradeAccountByUsername(username);
		List<Object[]> objects = new ArrayList<>();
		/*if(accounts == null || accounts.size() == 0){*/
			Object[] objects2 = new Object[]{username,password};
			objects.add(objects2);
			/*try {*/
				List<Object> objectparam = new ArrayList<>();
				objectparam.add(username);
				List<MockTradeAccount> mockTradeAccounts =  nativeQuery("select * from mock_trade_account where Username = ?", objectparam, MockTradeAccount.class);
				for (int i = 0; i < mockTradeAccounts.size(); i++) {
					System.out.println(mockTradeAccounts.get(i).getUsername());
				}
				/*batchSave("insert into mock_trade_account(Username,Password) value(?,?)", 1, objects);
				Integer accountId = getEntityDao().doGetMockTradeAccountByUsernameOne(username).getId();*/
				/*List<Map<String, Object>> maps =  getEntityDao().queryMapBySql("select CurrencyNo from a_currency_list", null);
				List<Object[]> mockTradeMoneyAccounts = new ArrayList<>();
				String sql = "insert into mock_trade_money_account(AccountId,TradeAccountUsername,InMoney,TodayCanUse,TodayAmount,CloseProfit,CounterFee,CurrencyNo"
						+ ",DayCloseProfit,DayFloatingProfit,Deposit,FloatingProfit,FrozenMoney,KeepDeposit,OldBalance,OldAmount,OldCanCashout,OldCanUse,OutMoney"
						+ ",Premium,RiskRate,TodayBalance,TodayCanCashout,TotalProfit,UnaccountProfit,UnexpiredProfit) "
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				for (int i = 0; i < maps.size(); i++) {
					Double money = CppConfig.INIT_MOCK_MONEY;
					String currencyNo = String.valueOf(maps.get(i).get("CurrencyNo"));
					if(!currencyNo.equals("USD")){
						money=0.00;
					}
					Object[] objectsMockTradeAccount = new Object[]{
							accountId,username,money,money,money,0.00,0.00,currencyNo
							,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00
					};
						mockTradeMoneyAccounts.add(objectsMockTradeAccount);
				}
				batchSave(sql, maps.size(), mockTradeMoneyAccounts);*/
				flag = true;
			/*} catch (SQLException e) {
				e.printStackTrace();
			}*/
		/*}else{
			Object[] objects2 = new Object[]{password,username};
			nativeUpdate("update mock_trade_account set Password = ? where Username = ?", objects2);
		}*/
		return flag;
	}
}
