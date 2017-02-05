package com.tzdr.common.api.alidayu.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author WangPinQun
 * @version 创建时间：2016年07月08日 上午10:22:15
 * 类说明
 */
public class AlidayuUtil {

	public AlidayuUtil() {
	}
	private static Properties props = new Properties(); 
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("alidayu.properties"));
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
