package com.tzdr.business.api.hundsun;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.common.api.hundsun.data.Combinfo;
import com.tzdr.common.api.hundsun.data.Combofund;
import com.tzdr.common.api.hundsun.data.Combostock;
import com.tzdr.common.api.hundsun.data.Entrust;
import com.tzdr.common.api.hundsun.data.EntrustResult;
import com.tzdr.common.api.hundsun.data.Realdeal;
import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.utils.BigDecimalUtils;

/**
 * @Description:
 * @ClassName: HundsunJresTest.java
 * @author Lin Feng
 * @date 2015年2月28日
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class HundsunJresTest {


	// @Test
	// @Ignore
	public void test() throws T2SDKException {
		HundsunJres hundsunJres = HundsunJres.getInstance();
		JSONObject userToken = hundsunJres.funcAmUserLogin("16289001", "1628");
		System.out.print(userToken);
		// String query=hundsunJres.query(userToken, "16280001");
		// String query=hundsunJres.query(userToken,"16280001","10930");
		List<Combostock> queryList = hundsunJres.funcAmCombostockQry(
				userToken.getString("userToken"), null, null, null, null);
		System.out.println();
		for (Combostock combostock : queryList) {
			System.out.println(combostock.getCombineId() + "|"
					+ combostock.getInitDate() + "|"
					+ combostock.getStockAccount() + "|"
					+ combostock.getStockCode());
		}

		hundsunJres.stop();
	}

	//@Test
	//@Ignore
	public void test1() throws T2SDKException {
		HundsunJres hundsunJres = HundsunJres.getInstance();
		String userToken = hundsunJres.Login();
		List<StockCurrent> stockCurrentList = hundsunJres
				.funcAmStockCurrentQry(userToken,"62360008","55336");
		double assetTotalValue = 0D;
		if (!CollectionUtils.isEmpty(stockCurrentList)) {
			StockCurrent stockCurrent = stockCurrentList.get(0);
			// 结算总资产
			assetTotalValue = BigDecimalUtils.addRound(
					stockCurrent.getCurrentCash(),
					stockCurrent.getMarketValue());
		}
		System.out.print("结算总资产:"+assetTotalValue);
		hundsunJres.stop();
	}

	// @Test
	// @Ignore
	public void test2() throws T2SDKException {
		HundsunJres hundsunJres = HundsunJres.getInstance();

		JSONObject userToken = hundsunJres.funcAmUserLogin("16289001", "1628");
		System.out.print(userToken);
		// String query=hundsunJres.query(userToken, "16280001");
		// String query=hundsunJres.query(userToken,"16280001","10930");
		List<Combinfo> combinfoList = hundsunJres.funcAmCombinfoQry(
				userToken.getString("userToken"), null, null);
		System.out.print(combinfoList);
		hundsunJres.stop();
	}

	// @Test
	// @Ignore
	public void test3() throws T2SDKException {
		HundsunJres hundsunJres = HundsunJres.getInstance();
		JSONObject userToken = hundsunJres.funcAmUserLogin("16289001", "1628");
		System.out.print(userToken);
		// String query=hundsunJres.query(userToken, "16280001");
		// String query=hundsunJres.query(userToken,"16280001","10930");
		boolean query = hundsunJres.funcAmUserLogout(
				userToken.getString("userToken"), "16289001");
		System.out.print(query);
		hundsunJres.stop();
	}

	// @Test
	// @Ignore
	public void test4() throws T2SDKException {
		HundsunJres hundsunJres = HundsunJres.getInstance();
		JSONObject userToken = hundsunJres.funcAmUserLogin("16289001", "1628");
		System.out.print(userToken);
		// String query=hundsunJres.query(userToken, "16280001");
		// String query=hundsunJres.query(userToken,"16280001","10930");
		List<Combofund> queryList = hundsunJres.funcAmCombofundQry(
				userToken.getString("userToken"), "16289001", "10930");
		System.out.println("combofund");
		for (Combofund combofund : queryList) {
			System.out.println(combofund.getEnableBalance());
		}
		hundsunJres.stop();
	}

	 //@Test
	// @Ignore
	public void test5() throws T2SDKException {
		HundsunJres hundsunJres = HundsunJres.getInstance();
		JSONObject userToken = hundsunJres.funcAmUserLogin("13769001", "1376");
		System.out.print(userToken);
		// String query=hundsunJres.query(userToken, "16280001");
		// String query=hundsunJres.query(userToken,"16280001","10930");
		List<Combinfo> queryList = hundsunJres.funcAmCombinfoQry(
				userToken.getString("userToken"),null, null);
		for (Combinfo combinfo : queryList) {
			System.out.println("账户编号："+combinfo.getFundAccount()+"组合编号:" + combinfo.getCombineId() + "-单元编号:"
					+ combinfo.getAssetId());
		}
		hundsunJres.Logout(userToken.getString("userToken"));
		hundsunJres.stop();
	}

	//@Test
	// @Ignore
	public void test6() throws T2SDKException {
		HundsunJres hundsunJres = HundsunJres.getInstance();
		String userToken = hundsunJres.Login("13769001", "1376");
		System.out.print(userToken);
		// String query=hundsunJres.query(userToken, "16280001");
		// String query=hundsunJres.query(userToken,"16280001","10930");
		List<Entrust> entrusts = 	 hundsunJres.funcAmEntrustQry(userToken, "13760005", "11823",null, null, null, 0, 0, 0, 0);
		for (Entrust entrust : entrusts) {
			System.out.println("证券代码:" + entrust.getStockCode()+"，交易市场："+entrust.getExchangeType()
					+"，委托方向："+entrust.getEntrustDirection()
					+ "-委托数量:" + entrust.getEntrustAmount());
		}
		hundsunJres.stop();
	}
	
	
	// @Test
	// @Ignore
	public void test8() throws T2SDKException {
		HundsunJres hundsunJres = HundsunJres.getInstance();
		String userToken = hundsunJres.Login("62369007", "\\shxh9007");
		System.out.print(userToken);
		// String query=hundsunJres.query(userToken, "16280001");
		// String query=hundsunJres.query(userToken,"16280001","10930");
		//List<Combasset> combassets = hundsunJres.funcAmCombassetQry(
		//		userToken,"13760005", "2334");
		
//		hundsunJres.funcAmUserLogout(userToken);
		hundsunJres.stop();
	}

	//@Test
		// @Ignore
		public void test9() throws T2SDKException {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login("13769001", "1376");
			System.out.print(userToken);
			
			// 限制买入
			//hundsunJres.funcAmChangeOperatorInfo(userToken,13769002L,"3", null, 180L, null, "2", null);

			//冻结
			hundsunJres.funcAmChangeOperatorInfo(userToken,13769001L,"7", null, 180L, null, null, null);
			
			//解冻
			//hundsunJres.funcAmChangeOperatorInfo(userToken,13769002L,"7", null, 180L, null, null, null);
			
			// String query=hundsunJres.query(userToken, "16280001");
			// String query=hundsunJres.query(userToken,"16280001","10930");
			//List<Combasset> combassets = hundsunJres.funcAmCombassetQry(
			//		userToken,"13760005", "2334");
			
//			hundsunJres.funcAmUserLogout(userToken);
			hundsunJres.stop();
		}
		
		//@Test
		// @Ignore
		public void test11() throws T2SDKException {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login("13769001", "1376");
			System.out.print(userToken);
			// String query=hundsunJres.query(userToken, "16280001");
			// String query=hundsunJres.query(userToken,"16280001","10930");
			List<Combostock> combostocks = 	 hundsunJres.funcAmCombostockQry(userToken, "13760005", "11823", null, null);
			for (Combostock combostock : combostocks) {
				System.out.println("证券代码:" + combostock.getStockCode()+"，交易市场："+combostock.getExchangeType()
						+"，当前数量"+combostock.getCurrentAmount()
						+ "，可用数量:" + combostock.getEnableAmount()
						+",当前成本："+combostock.getCostBalance()
						+",当前总成本："+combostock.getCostBalance()*combostock.getCurrentAmount()
						+",当前市值："+combostock.getMarketValue());
			}
			hundsunJres.stop();
		}
		
		
		//@Test 	
		// @Ignore 普通委托
		public void test12() throws T2SDKException {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login("13769001", "1376");
			System.out.print(userToken);
			// String query=hundsunJres.query(userToken, "16280001");
			// String query=hundsunJres.query(userToken,"16280001","10930");
			//普通委托 平仓
			EntrustResult result = 	 hundsunJres.funcAmEntrust(userToken, "13760005", "11823",
					null, null, "2", "000656", "2", "A",100.0, 1, null, null);
			System.out.println(result.getBatchNo()+","+result.isSuccess());
			hundsunJres.stop();
		}

		//@Test
		// @Ignore 历史成交查询
		public void funcAmRealdealHistoryQry() throws T2SDKException {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login("13769001", "1376");
			System.out.print(userToken);
			// String query=hundsunJres.query(userToken, "16280001");
			// String query=hundsunJres.query(userToken,"16280001","10930");
			List<Realdeal> realdeals = 	 hundsunJres.funcAmRealdealHistoryQry(userToken,20150116,0,0);
			for (Realdeal realdeal : realdeals) {
				System.out.println("成交序号:" + realdeal.getBusinessNo()+"，账户编号："+realdeal.getFundAccount()
						+"，证券代码"+realdeal.getStockCode()
						+ "，成交时间:" + realdeal.getBusinessTime()
						+",成交数量："+realdeal.getBusinessAmount()
						+",成交价格："+realdeal.getBusinessPrice()
						+",成交金额："+realdeal.getBusinessBalance());
			}
			hundsunJres.stop();
		}

		
		
			//@Test
				// @Ignore 历史成交查询
				public void funcAmmove() throws T2SDKException {
					HundsunJres hundsunJres = HundsunJres.getInstance();
					String userToken = hundsunJres.Login("13769001", "1376");
					System.out.print(userToken);
					// String query=hundsunJres.query(userToken, "16280001");
					// String query=hundsunJres.query(userToken,"16280001","10930");
					boolean ssk = hundsunJres.funcAmAssetMove(userToken,"13760001", null,
							"16744", 100.0, HundsunJres.CASH, "ss");
					System.out.println(ssk);
					hundsunJres.stop();
				}
}
