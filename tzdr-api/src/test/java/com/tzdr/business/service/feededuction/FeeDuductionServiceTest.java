package com.tzdr.business.service.feededuction;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.common.utils.Dates;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月4日 下午3:55:46
 * 类说明
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FeeDuductionServiceTest {

	@Autowired
	private FeeDuductionService  feeDuductionService;
	
	@Autowired
	private UserFundService  userFundService;
	//@Test
	public void test() throws T2SDKException {
		// 扣除欠费
		//feeDuductionService.deductionArrearage();
		
		//扣除当日管理费
		System.out.println(userFundService.isDeductionTodayFee("40288a0f4b1a8198014b1a8ea4b50004"));
		
		//发送当日欠费短信
	//	feeDuductionService.sendCurrentArrearsSms();
		
		//发送下一日不够扣短信
		//feeDuductionService.isCanFeeDeductionNextDay();
	}
}
