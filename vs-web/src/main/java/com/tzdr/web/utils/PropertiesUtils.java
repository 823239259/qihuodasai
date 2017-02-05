/**
 * 
 */
package com.tzdr.web.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * java配置文件操作工具类
 * 
 * @author uvanix
 *
 */
public class PropertiesUtils {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	private static Properties props = new Properties();

	/**
	 * 根据key获取value
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public static String getValueByKey(String filePath, String key) {
		String value = "";
		InputStream is = null;

		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
			props.load(is);
			value = props.getProperty(key);
		} catch (IOException e) {
			logger.error("IOException.", e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return value;
	}

	/**
	 * 获取所有key-value
	 * 
	 * @return
	 */
	public static Map<String, String> getAllValue(String filePath) {
		Map<String, String> result = new HashMap<String, String>();
		InputStream is = null;

		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
			props.load(is);
			Enumeration<?> en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				result.put(key, props.getProperty(key));
			}
		} catch (IOException e) {
			logger.error("IOException.", e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 写入Properties信息
	 * 
	 * @param filePath
	 * @param key
	 * @param value
	 */
	public static void setProperties(String filePath, String key, String value) {
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
			fos = new FileOutputStream(filePath);
			props.load(is);
			props.setProperty(key, value);
			props.store(fos, "set properties: " + key + "=" + value);
		} catch (IOException e) {
			logger.error("IOException.", e.getMessage());
		} finally {
			try {
				is.close();
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
