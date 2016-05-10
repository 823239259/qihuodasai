package com.tzdr.business.service.userTrade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.api.service.ApiTradeService;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月4日 下午3:55:46
 * 类说明
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTradeServiceTest {

	@Autowired
	private UserTradeService  userTradeService;
	
	@Autowired
	private ApiTradeService  apiTradeService;
	@Test
	public void test() throws T2SDKException {
		//userTradeService.isUpMarginLine("13769002");
		apiTradeService.findUserTrades("10055");
	}

}
