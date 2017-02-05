package com.tzdr.business.service.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.SpringUtils;

/**
 * 通过手机号、短信内容 线程发送
 * @zhouchen
 * 2015年5月7日
 */
public class SMSSendForContentThread extends Thread {
	
	private static DataMapService dataMapService;
	
	public static final Logger logger = LoggerFactory.getLogger(SMSSendForContentThread.class);
	
	private String mobile;
	private String content;
	private long millis=0;
	

	public SMSSendForContentThread(String mobile, String content,long millis) {
		this.mobile = mobile;
		this.content = content;
		this.millis=millis;
		dataMapService = SpringUtils.getBean(DataMapService.class);
	}

	public SMSSendForContentThread(String mobile, String content) {
		this.mobile = mobile;
		this.content = content;
		dataMapService = SpringUtils.getBean(DataMapService.class);
	}
	
	public void run() {		
		try {
			if (this.millis>0){
				Thread.sleep(millis);
			}
			SMSSender.getInstance().send(dataMapService.getSmsContentOthers(),mobile,content);
			
		} catch (Exception e) {
			try {
				Thread.sleep(1500);
			} catch (Exception execp) {
				logger.error(Exceptions.getStackTraceAsString(execp));
			}
			SMSSender.getInstance().send(dataMapService.getSmsContentOthers(),mobile,content);
		}		
	}
}
