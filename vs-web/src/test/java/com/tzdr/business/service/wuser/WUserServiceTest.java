package com.tzdr.business.service.wuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.web.entity.WUser;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WUserServiceTest {

	@Autowired
	private WUserService wUserService;
	
//	@Test
	@Ignore
	public void saveWUser(){
		WUser parentWuser = new WUser();
		parentWuser.setMobile("15200000000");
		parentWuser.setPassword("shxh1234");
		parentWuser.setUname("上海信闳-平台");
		parentWuser.setCtime((new Date().getTime()/1000));
		parentWuser.setUserType("-1");
		List<WUser> parentWuserChildList = new ArrayList<WUser>();
		for (int i = 0; i < 2; i++) {                        //二级代理商
			WUser parentWuserChild = new WUser();
			parentWuserChild.setMobile("1520000000"+(1+i));
			parentWuserChild.setPassword("shxh1234");
			parentWuserChild.setUname(i== 0 ? "上海信闳-web渠道" : "上海信闳-cms渠道");
			parentWuserChild.setUserType(i== 0 ?  "-2" : "-3");
			parentWuserChild.setCtime((new Date().getTime()/1000));
			parentWuserChild.setParentNode(parentWuser);
			parentWuserChildList.add(parentWuserChild);
		}
		parentWuser.setChilds(parentWuserChildList);
		wUserService.saveWUser(parentWuser);
	}
	
	@Test
//	@Ignore
	public void saveChildWUser(){
			WUser parentWuserChild = new WUser();     			//二级代理商
			parentWuserChild.setMobile("15400000002");
			parentWuserChild.setPassword("shxh1234");
			parentWuserChild.setUname("上海信闳-三级代理商2");
			parentWuserChild.setCtime((new Date().getTime()/1000));
			parentWuserChild.setRegIp("127.0.0.1");
			parentWuserChild.setRebate(10.00);
			parentWuserChild.setUserType("0");
			parentWuserChild.setTotalCommission(0.00);
			wUserService.saveWUser(parentWuserChild);
	}
	
//	@Test
	@Ignore
	public void getWUser(){
		try {
			WUser wuser = wUserService.getUser("40288a114ae33322014ae3341d010001");
			WUser parent = wuser.getParentNode();
			System.out.println(parent);
			Assert.notNull(wuser);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
