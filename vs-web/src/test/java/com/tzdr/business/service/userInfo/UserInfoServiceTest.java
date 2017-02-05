package com.tzdr.business.service.userInfo;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.domain.web.entity.UserInfo;
import com.tzdr.domain.web.entity.WUser;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserInfoServiceTest {
	
	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private UserInfoService userInfoService;
	
//	@Ignore
//	@Test
	public void getUserInfoByUId(){
		UserInfo userInfo = userInfoService.getUserInfoByUId("402892414a93e7e1014a93e90dff0001");
		Assert.notNull(userInfo);
	}
	
//	@Ignore
	@Test
	public void saveUserInfo(){
		WUser wuser = wUserService.getUser("402892414a93e7e1014a93e90dff0001");
		UserInfo userInfo = new UserInfo();
		userInfo.setWuser(wuser);
//		userInfo.setUid("402892414a93e7e1014a93e90dff0001");
		userInfo.setVersion((long)1);
		userInfoService.saveUserInfo(userInfo);
	}

}
