package com.tzdr.business.service.thread;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.SpringUtils;

/**
 * @Description:
 * @ClassName: SMSSenderThread.java
 * @author Lin Feng
 * @date 2015年1月26日
 */
public class SMSSenderThread extends Thread {
	
	private static DataMapService dataMapService;
	
	public static final Logger logger = LoggerFactory.getLogger(SMSSenderThread.class);
	
	private String mobile;
	private String templateKey;
	private Map<String, String> map;
	private long millis=0;
	
	public SMSSenderThread(String mobile, String templateKey,
			Map<String, String> map) {
		this.mobile = mobile;
		this.templateKey = templateKey;
		this.map = map;
		dataMapService = SpringUtils.getBean(DataMapService.class);
	}

	public SMSSenderThread(String mobile, String templateKey,
			Map<String, String> map,long millis) {
		this.mobile = mobile;
		this.templateKey = templateKey;
		this.map = map;
		this.millis=millis;
		dataMapService = SpringUtils.getBean(DataMapService.class);
	}

	
	public void run() {		
		try {
			if (this.millis>0){
				Thread.sleep(millis);
			}
			SMSSender.getInstance().sendByTemplate(dataMapService.getSmsContentOthers(),mobile,
					templateKey, map);		
		} catch (Exception e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			try {
				Thread.sleep(1500);
			} catch (Exception execp) {
				logger.error(Exceptions.getStackTraceAsString(execp));
			}
			SMSSender.getInstance().sendByTemplate(dataMapService.getSmsContentOthers(),mobile,
					templateKey, map);
		}		
	}
}
