package com.tzdr.api.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tzdr.api.constants.DataConstant;

/**
 * <B>说明: </B>每天凌晨清除注册短信验证码超过5次的信息
 * @zhouchen
 * 2015年11月17日
 */
@Component
public class ClearRegistMsgMaxTimesTask {

	public static final Logger logger = LoggerFactory
			.getLogger(ClearRegistMsgMaxTimesTask.class);

	public void executeClear() {
		logger.info("------------------清除注册短信发送超过5次的手机号码任务--------begin-------------------");	
		
		DataConstant.SMS_LIMIT_MAPS.clear();
		
		logger.info("------------------清除注册短信发送超过5次的手机号码任务--------end-------------------");		

	}
}
