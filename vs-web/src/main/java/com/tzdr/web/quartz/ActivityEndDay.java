package com.tzdr.web.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tzdr.business.service.wuser.WUserService;

/**
* @Description: (8800活动通知)
* @ClassName: ActivityEndDay
* @author Linfeng
* @date 2015年1月15日 上午11:09:21
 */
@Component
public class ActivityEndDay {
	
	public static final Logger logger = LoggerFactory.getLogger(ActivityEndDay.class);


	
	@Autowired
	private WUserService wuserService;
	

	
	public void executeEndDay(){
		try {
			wuserService.executeEndDay();
		} catch (InterruptedException e) {
			logger.error("8800活动定时发送短信报错！");
		}
	}
}
