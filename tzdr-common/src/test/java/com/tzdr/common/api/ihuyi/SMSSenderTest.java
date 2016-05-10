package com.tzdr.common.api.ihuyi;

import java.util.HashMap;
import java.util.Map;

public class SMSSenderTest {

	//@Test
	//@Ignore
	public void test() {
		int smsChannel = 1;
		Map<String,String> map= new HashMap<String,String>();
		map.put("module", "test");
		map.put("code", "test2");
		SMSSender.getInstance().sendByTemplate(smsChannel, "18996152105", "8800.template", null);
	}

}
