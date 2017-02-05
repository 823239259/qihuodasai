package com.tzdr.web.controller.homepage;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * 在线用户监听器
 * <P>title:@OnlineListener.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年1月29日
 * @version 1.0
 */
public class OnlineListener implements HttpSessionListener {
	
	/**
	 * 创建session后添加用户
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		OnlineCounter.getInstance().addUser();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		OnlineCounter.getInstance().delUser();
		
	}

}
