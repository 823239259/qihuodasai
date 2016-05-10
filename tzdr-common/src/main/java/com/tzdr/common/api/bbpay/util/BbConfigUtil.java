package com.tzdr.common.api.bbpay.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author zhouchen
 * @version 创建时间：2015年10月14日 上午10:22:15
 * 类说明
 */
public class BbConfigUtil {

	public BbConfigUtil() {
	}
	private static Properties props = new Properties(); 
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("bbpayapi.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getContext(String key){
		return props.getProperty(key);
	}

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    } 
}
