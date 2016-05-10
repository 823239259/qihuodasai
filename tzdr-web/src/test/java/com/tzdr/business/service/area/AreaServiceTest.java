package com.tzdr.business.service.area;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tzdr.domain.web.entity.Area;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AreaServiceTest {

	@Autowired
	private AreaService areaService;
	
	@Ignore
//	@Test
	public void findByPidOrderBySortAsc(){
		List<Area> areaList = areaService.findByPidOrderBySortAsc("0");
		Assert.notEmpty(areaList);
	}
	
	@Test
	public void findAreaByIds(){
		List<String> ids  = new ArrayList<String>();
		ids.add("460000");
		ids.add("460100");
		List<Area> areaList = areaService.findAreaByIds(ids);
		Assert.notEmpty(areaList);
	}
}
