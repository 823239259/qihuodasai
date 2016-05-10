package com.tzdr.business.service.userTrade;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.business.service.exception.UserTradeConcurrentException;
import com.tzdr.common.api.hundsun.data.Combinfo;
import com.tzdr.common.api.hundsun.data.Combostock;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年1月26日下午2:56:43
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class HundsunJresTrade {
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private ParentAccountService parentAccountService;
	
	
	//@Test
	public void transfer() throws Exception {
		HundsunJres hundsunJres  = HundsunJres.getInstance();
		String userToken = hundsunJres.Login();
		List<Combinfo> combinfoList = hundsunJres.funcAmCombinfoQry(userToken, null, null);
		Map<Long,String> combinfoes = new HashMap<Long,String>();
		
		
		for (Combinfo en:combinfoList) {
			System.out.println("unitNumber:" + en.getAssetId() + "    combineId:" + en.getCombineId());
			combinfoes.put(en.getAssetId(),en.getCombineId());
		}
		//ParentAccount parentAccountObj = parentAccountService.findByParentAccountId(parentAccount);
		//账户编号	Y	
		//组合编号	Y	
		//目标组合编号	Y	转入的组合编号。
		//发生金额	Y	调整金额。
		//业务操作类别	Y	107--现金划转、117--保证金划转
		//母账号
		
		//账户编号
		String fundAccount = "13760001";
		//11625-单元编号:15663
		//11592-单元编号:15630
		//11626-单元编号:15664
		//组合编号
		String combineId = "11624";
		//目标组合编号
		String targetCombineId = "11591";
		Boolean flag = hundsunJres.funcAmAssetMove(userToken, fundAccount,
				combineId, targetCombineId,
				BigDecimalUtils.round(400, 2), HundsunJres.CASH,"");
		
		/*if (transferMoney(parentAccount, combineId,
				CacheManager.getHundSunCombineId(parentAccountObj.getUnitNumber()), assetTotalValue)) */
	}
	
	
	//@Test
	public void adf() throws Exception {
		HundsunJres hundsunJres = HundsunJres.getInstance();
		String userToken = hundsunJres.Login();
		String groupId = "TB42Y5H";
		double assetTotalValue = 0D;
		List<UserTrade> userTrades = userTradeService.findByGroupIdOrderByAddtimeAsc(groupId);
		if (userTrades == null || userTrades.size() <= 0) {
			throw new UserTradeConcurrentException("com.tzdr.business.server.connection.timeout",null);
		}
		UserTrade userTrade = userTrades.get(0);
		if (userTrade.getStatus() == 2 || userTrade.getStatus() == 3) {
			throw new UserTradeConcurrentException("com.tzdr.business.not.end.repeat",null);
		}
		
		//获取母账户编号和组合编号
		String parentAccount = userTrade.getParentAccountNo();
		String combineId = userTrade.getCombineId();
			//持仓查询
		List<Combostock> combostocks = 
				hundsunJres.funcAmCombostockQry(userToken,parentAccount,combineId,null, null);
		if (!CollectionUtils.isEmpty(combostocks)){
			//持有股票不能终结方案
			throw new UserTradeConcurrentException("com.tzdr.business.end.holding",null);
		}
		
		
	}
	
	public static void main(String[] args) {
		URL url = HundsunJresTrade.class.getResource("");
		String path = url.getPath();
		System.out.println(path);
	}

	

}
