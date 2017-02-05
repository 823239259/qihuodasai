package com.tzdr.business.pay.pingpp.example;

import com.alibaba.fastjson.JSONObject;

public class BackExample {
	/**
	 * 支付宝支付成功返回通知状态
	 * @return
	 */
	public static String paySuccessBack(){
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", "200");
		return resultJson.toJSONString();
	}
}
