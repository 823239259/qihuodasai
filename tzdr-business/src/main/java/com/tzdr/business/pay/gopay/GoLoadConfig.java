package com.tzdr.business.pay.gopay;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

public class GoLoadConfig extends HttpServlet implements ServletContextListener{
	private static final long serialVersionUID = 1L;

	/**
	 * 加载国付宝国付宝支付信息
	 * @throws IOException 
	 */
	public void init(){
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("gopay.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		GoConfig.setCustomerId(properties.getProperty("customerId"));
		GoConfig.setVerficationCode(properties.getProperty("verficationCode"));
		GoConfig.setPayAcctId(properties.getProperty("payAcctId"));
		
		
		//配置take
		GoTakeConfig.setVersion(properties.getProperty("take_version"));
		GoTakeConfig.setTradCode(properties.getProperty("take_tradCode"));
		GoTakeConfig.setMerchantEncod(properties.getProperty("take_merchantEncod"));
		GoTakeConfig.setCorpPersonFlag(properties.getProperty("take_corpPersonFlag"));
		GoTakeConfig.setApprove(properties.getProperty("take_approve"));
		
		
		//配置pay
		GoPayConfig.setVersion(properties.getProperty("pay_version"));
		GoPayConfig.setTradCode(properties.getProperty("pay_tradCode"));
		GoPayConfig.setMerchantEncod(properties.getProperty("pay_charset"));
		GoPayConfig.setLanguage(properties.getProperty("pay_language"));
		GoPayConfig.setSignType(properties.getProperty("pay_signType"));
		GoPayConfig.setIsRepeatSubmit(properties.getProperty("pay_isRepeatSubmit"));
		GoPayConfig.setFrontMerUrl(properties.getProperty("pay_frontMerUrl"));
		GoPayConfig.setBackgroundMerUrl(properties.getProperty("pay_backgroundMerUrl"));
		GoPayConfig.setCurrencyType(properties.getProperty("pay_currencyType"));
		GoPayConfig.setServerTimeUrl(properties.getProperty("pay_server_time_url"));
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
