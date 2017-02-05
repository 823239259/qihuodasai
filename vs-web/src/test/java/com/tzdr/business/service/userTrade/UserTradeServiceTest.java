package com.tzdr.business.service.userTrade;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.UserTradeVo;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTradeServiceTest {

	@Autowired
	private UserTradeService userTradeService;
	
	@Ignore
//	@Test
	public void getSumAccrualByWuserAndStatus(){
		Double result = userTradeService.getSumAccrualByWuserAndStatus("402892414a93e7e1014a93e90dff0001", (short)1);
		Assert.notNull(result);
	}
	
	@Test
	public void queryUserTradeVoByWuserAndStatus(){
		List<UserTradeVo> userTradeVos = userTradeService.queryUserTradeVoByWuserAndStatus("40288a114ae33322014ae3341d010001", (short)1, (short)0);
		Assert.notNull(userTradeVos);
	}
	
	@Ignore
	//@Test
	public void test1(){
		PageInfo<Object> userTradeVos = userTradeService.queryUserTrade("402881ee4ad26212014ad26d31df0001",10,1,null);
		Assert.notNull(userTradeVos);
	}
}
