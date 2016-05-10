package com.tzdr.common.api.umpay;

import java.io.IOException;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.api.umpay.data.WithdrawPayExtendInfo;
import com.tzdr.common.api.umpay.data.WithdrawPayInfo;
import com.umpay.api.exception.RetDataException;

/**
 * @Description:
 * @ClassName: WithdrawPayTest.java
 * @author Lin Feng
 * @date 2014年12月30日
 */
public class WithdrawPayTest {

	 //@Test
	// @Ignore
	public void test() throws RetDataException, IOException {
		 
		WithdrawPayInfo withdrawPayInfo = new WithdrawPayInfo();
		WithdrawPayExtendInfo withdrawPayExtendInfo = new WithdrawPayExtendInfo();
		withdrawPayInfo.setOrderId("20141230721609");
		withdrawPayInfo.setMerDate("20141230");
		withdrawPayInfo.setAmount("1");
		withdrawPayInfo.setPurpose("付款测试");
		withdrawPayInfo.setBankBrhname("XX支行");
		withdrawPayInfo.setRecvAccount("6222023100050149819");
		withdrawPayInfo.setRecvUserName("林锋");
		withdrawPayInfo.setRecvGateId("ICBC");
		JSONObject json=WithdrawPay.getInstance().getWithdrawResponse(withdrawPayInfo, withdrawPayExtendInfo);
		
		System.out.print(json.toString());
		
		
		
	}

}
