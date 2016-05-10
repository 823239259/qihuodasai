package com.tzdr.web.support.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <P>title:@XssFilter.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年3月23日
 * @version 1.0
 */
public class XssFilters implements Filter { 
	public static final Logger logger = LoggerFactory.getLogger(XssFilters.class);

	@Override   
	public void init(FilterConfig filterConfig) throws ServletException { 
		logger.info("Xss filter inited!");  
	}    
	@Override  
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {    
		XssHttpWrapper xssRequest = new XssHttpWrapper((HttpServletRequest) request);   
		chain.doFilter(xssRequest, response);   
	}  
	@Override  
	public void destroy() {   
		logger.info("Xss filter destroyed!");   
	  }
	} 

