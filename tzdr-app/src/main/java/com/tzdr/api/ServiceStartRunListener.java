package com.tzdr.api;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.tzdr.common.utils.WeChatUtil;
@Service
public class ServiceStartRunListener  implements ApplicationListener<ContextRefreshedEvent>{
	private Logger logger = LoggerFactory.getLogger(ServiceStartRunListener.class);
	private static Properties props = new Properties(); 
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			boolean flag = loadConfig();
			if(flag){
				WechatConfig();
			}
		}
	}
	public boolean loadConfig(){
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("wechat4j.properties"));
			return true;
		} catch (IOException e) {
			logger.info("配置加载失败");
			return false;
		}
	}
	public void WechatConfig(){
		WeChatUtil.setAPPID(props.getProperty("wechat.appid"));
		WeChatUtil.setSECRET(props.getProperty("wechat.appsecret"));
	}
}
