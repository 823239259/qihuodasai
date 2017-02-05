package com.tzdr.web.utils;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.StringUtils;
import com.tzdr.business.service.operational.OperationalConfigService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.securityInfo.impl.SecurityInfoServiceImpl;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.web.controller.statusPage.StatusPageController;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 静态
 * @version 2.0
 * 2015年2月3日下午2:31:48
 */
public class PageStatusListener implements ServletContextListener {
	//默认时间
	private Integer defaultMinute = 60 * 2;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			//分钟
			long minute = 60;
			//毫秒
			long millisecond = 1000;
			StatusPageController statusPage = new StatusPageController();
			OperationalConfigService operationalConfigService = SpringUtils.getBean(OperationalConfigService.class);
			WUserService wuserService = SpringUtils.getBean(WUserService.class);
			//private SecurityInfoServiceImpl securityInfoService;
			//@Autowired
			//private UserTradeService userTradeService;
			SecurityInfoServiceImpl securityInfoService = SpringUtils.getBean(SecurityInfoServiceImpl.class);
			UserTradeService userTradeService = SpringUtils.getBean(UserTradeService.class);
			statusPage.setOperationalConfigService(operationalConfigService);
			statusPage.setWuserService(wuserService);
			statusPage.setUserTradeService(userTradeService);
			statusPage.setSecurityInfoService(securityInfoService);
			
			String statusPageRefreshTimeStr = 
					servletContextEvent.getServletContext().getInitParameter("statusPageRefreshTime");
			if (statusPageRefreshTimeStr != null 
					&& !"".equals(statusPageRefreshTimeStr)) {
				Timer timer = new Timer();
				timer.schedule(new PageStatusTask(servletContextEvent.getServletContext(),statusPage.getData()),1000,
						millisecond * minute * new Integer(statusPageRefreshTimeStr));
			}
			else {
				Timer timer = new Timer();
				timer.schedule(new PageStatusTask(servletContextEvent.getServletContext()),1000,
						millisecond * minute * defaultMinute);
			}
			
		} 
		catch (Exception e) {}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		
		
	}
	
	

}
