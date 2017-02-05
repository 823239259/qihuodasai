package com.tzdr.web.utils;

import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月3日下午2:43:42
 */
public class PageStatusTask extends TimerTask {

	/**
	 * 服务器ServletContext
	 */
	private ServletContext servletContext;
	//数据模型
	private Map<String,Object> dataModel;
	
	public PageStatusTask(){
		
	}
	
    public PageStatusTask(ServletContext servletContext){
		this.servletContext = servletContext;
	}
    
    public PageStatusTask(ServletContext servletContext,Map<String,Object> dataModel){
  		this.servletContext = servletContext;
  		this.dataModel = dataModel;
  	}
    
	@Override
	public void run() {
		try {
			//测试静态
			PageStatusUtil.writeHtml(servletContext,"homepage.ftl",dataModel);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public Map<String, Object> getDataModel() {
		return dataModel;
	}

	public void setDataModel(Map<String, Object> dataModel) {
		this.dataModel = dataModel;
	}
	
}

