package com.tzdr.business.pay.pingpp.config;

import com.pingplusplus.Pingpp;
import com.tzdr.business.pay.pingpp.config.enums.Live;

public class Config {
	/**
	 * 模式有两种：test,live
	 */
	public static Live PATTERN = Live.LIVE;
	public static String APP_ID = "app_S808W99O4SK8qrfX";
	public static String TESTPING_KEY = "sk_test_jHeHO4XfH0u9W9SuvTyvLyjH";
	public static String LIVEPING_KEY = "sk_live_rvbzj9Ge5K84m50GeHKKifH8";
	public static String PING_KEY;
	public static String PUBLIC_KEY = "----------BEGIN PUBLIC KEY-----\n"
										+"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzs8SiPoFQT9K0lWa6WSx\n"
										+"0d6UnA391KM2aFwijY0AK7r+MiAe07ivenopzFL3dqIRhQjuP7d30V85kWydN5UZ\n"
										+"cm/tZgm4K+8wttb988hOrzSjtPOMghHK+bnDwE8FIB+ZbHAZCEVhNfE6i9kLGbHH\n"
										+"Q617+mxUTJ3yEZG9CIgke475o2Blxy4UMsRYjo2gl5aanzmOmoZcbiC/R5hXSQUH\n"
										+"XV9/VzA7U//DIm8Xn7rerd1n8+KWCg4hrIIu/A0FKm8zyS4QwAwQO2wdzGB0h15t\n"
										+"uFLhjVz1W5ZPXjmCRLzTUoAvH12C6YFStvS5kjPcA66P1nSKk5o3koSxOumOs0iC\n"
										+"EQIDAQAB\n"
										+"-----END PUBLIC KEY-----";									
	/**
	 * 支付宝网页充值成功跳转页面
	 */
	public static String ALIPAY_PC_SUCCESS_URL = "http://test.www.dktai.com/user/account";
	/**
	 * 支付宝手机网站支付成功跳转页面
	 */
	public static String ALIPAY_WAP_SUCCESS_URL="http://test.www.dktai.com/paySucApp";
	public static String BODY = "维胜充值";
	public static String SUBJECT = "维胜充值购买";
	static {
		if (PATTERN == Live.TEST) {
			PING_KEY = TESTPING_KEY;
		} else if (PATTERN == Live.LIVE) {
			PING_KEY = LIVEPING_KEY;
		}
		Config.setApikey(PING_KEY);
	}
	
	public static void setApikey(String apiKey) {
		Pingpp.apiKey = apiKey;
	}
}
