package com.tzdr.common.api.ihuyi;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SMSSenderTest {

	@Test
	//@Ignore
	public void test() {
//		int smsChannel = 1;
//		Map<String,String> map= new HashMap<String,String>();
//		map.put("module", "test");
//		map.put("code", "test2");
//		SMSSender.getInstance().sendByTemplate(smsChannel, "13896679979", "8800.template", null);
		SMSSender.getInstance().sendSMS("13896679979", "您的验证码是：【变量】。请不要把验证码泄露给其他人。");
	}

}
