package com.tzdr.business.pay.dxtx;

import java.io.IOException;
import java.util.Properties;

public class DxtxLoadConfig {
		public static void init(){
			Properties properties = new Properties();
			try {
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dxtx.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			DxtxConfig.setGoodsId(Integer.parseInt(properties.getProperty("good_id")));
			DxtxConfig.setAppKey(properties.getProperty("appkey"));
			DxtxConfig.setNotifyingUrl(properties.getProperty("notifyingUrl"));
			DxtxConfig.setSyncnotifyingUrl(properties.getProperty("syncnotifyingUrl"));
			DxtxConfig.setAppSign(properties.getProperty("app_sign"));
			DxtxConfig.setSignUrl(properties.getProperty("sign_url"));
		}
}
