package com.tzdr.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurableConstants {
	protected static Logger logger = LoggerFactory.getLogger(ConfigurableConstants.class);
	protected static Properties p = new Properties();

	protected static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
			if (in != null)
				p.load(in);
		} catch (IOException e) {
			logger.error("load " + propertyFileName + " into Constants error!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close " + propertyFileName + " error!");
				}
			}
		}
	}

	protected static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}
}
