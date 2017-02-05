package com.tzdr.business.service.generalize;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tzdr.common.domain.PageInfo;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneralizeServiceTest {

	@Autowired
	private GeneralizeService generalizeService;
	
//	@Test
	@Ignore
	public void getGeneralizeVisitCount(){
		Long result = generalizeService.getGeneralizeVisitCount("402892414a93e7e1014a93e90dff0001");
		Assert.notNull(result);
	}
	
//	@Test
	@Ignore
	public void getGeneralizeVisitClieantIpCount(){
		Long result = generalizeService.getGeneralizeVisitClieantIpCount("402892414a93e7e1014a93e90dff0001");
		Assert.notNull(result);
	}
	
//	@Test
	@Ignore
	public void queryPageGeneralizeVisitVo(){
		
		PageInfo<Object> pageInfo = new PageInfo<Object>();
		pageInfo.setCurrentPage(1);
		pageInfo  = generalizeService.queryPageGeneralizeVisitVo("402892414a93e7e1014a93e90dff0001", pageInfo);
		Assert.notNull(pageInfo);
	}
	
//	@Test
	@Ignore
	public void getWuserTotalChild(){
		Integer childs = generalizeService.getWuserTotalChild("-1");
		Assert.notNull(childs);
	}
	
//	@Test
	@Ignore
	public void queryPageByParentId(){
		PageInfo<Object> pageInfo = new PageInfo<Object>();
		pageInfo.setCurrentPage(1);
		pageInfo = generalizeService.queryPageGeneralizeVisitUserVo("-1", pageInfo);
		Assert.notNull(pageInfo);
	}
}
