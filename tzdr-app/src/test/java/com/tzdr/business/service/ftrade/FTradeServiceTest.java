package com.tzdr.business.service.ftrade;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tzdr.business.app.service.FTradeService;
import com.tzdr.domain.app.vo.FTradeVo;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FTradeServiceTest {

	@Autowired
	private FTradeService fTradeService;
	
	@Test
//	@Ignore
	public void findGoods(){
		
		List<FTradeVo> dataList = fTradeService.findGoods();
		Assert.notNull(dataList);
	}
}
