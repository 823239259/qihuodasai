package com.tzdr.business.service.commission;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tzdr.business.service.generalize.CommissionService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.UserCommissionVo;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommissionServiceTest {

	@Autowired
	private CommissionService commissionService;
	
	
	@Autowired
	private WUserService wuserService;
	
	
//	@Test
	public void queryPageUserCommissionVo(){
		ConnditionVo connVo = new ConnditionVo();
		connVo.addParam("ctimeStr_start", "2015-03-10 11:33:04");
		connVo.addParam("ctimeStr_end", "2015-03-19 11:33:04");
		connVo.addParam("rebateStr_start", "0");
		connVo.addParam("rebateStr_end", "100");
		/*connVo.addParam("mobile", "15215050049");
		connVo.addParam("tname", "wangpinqun");*/
		List<UserCommissionVo> re = commissionService.queryUserCommissionVo(connVo);
		
		List<String> ids = new ArrayList<String>();
		for (UserCommissionVo userCommissionVo : re) {
			ids.add(userCommissionVo.getUserId());
		}
		
		
		wuserService.findByIdIn(ids);
	}
}
