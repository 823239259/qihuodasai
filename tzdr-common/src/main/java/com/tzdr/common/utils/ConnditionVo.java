package com.tzdr.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <p>根据条件VO对象</p>
 * @author QingLiu
 * @see 
 * @version 2.0
 * 2015年3月4日上午10:34:27
 */
public class ConnditionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4054776286797309226L;
	
	/**
	 * 条件数据
	 */
	private Map<String,Object> connditionData = new HashMap<String,Object>();
	
	/**
	 * 添加参数数据
	 */
	private List<String> params = new ArrayList<String>();
	
	private HttpServletRequest request;
	
	public ConnditionVo() {
		
	}
	
	/**
	 * 
	 * @param request HttpServletRequest
	 */
	public ConnditionVo(HttpServletRequest request) {
		this.request = request;
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			this.addParam(enu.nextElement());
		}
		
	}
	private static final String EXCEL_TYPE = "excelType9000000_0000_0001";
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isExcel() {
		String excelType = TypeConvert.objToStrIsNotNull(this.getValue(EXCEL_TYPE));
		if (excelType != null && "true".equals(excelType)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param data Collection<?>导出数据
	 * @param response HttpServletResponse
	 * @param filename String导出显示文件名
	 * @return boolean if true is excel export 
	 */
	public boolean isExcel(Collection<?> data,HttpServletResponse response,String filename) {
		if (this.isExcel()) {
			new ExportExcel(data,this.request,response,filename);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param data Collection<?>导出数据
	 * @param response HttpServletResponse
	 * @param filename String导出显示文件名
	 * @return boolean if false will auto export excel file 
	 */
	public boolean isNotExcel(Collection<?> data,HttpServletResponse response) {
		if (this.isExcel()) {
			String filename = "export.xls";
			new ExportExcel(data,this.request,response,filename);
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * 
	 * @param data Collection<?>导出数据
	 * @param response HttpServletResponse
	 * @param filename String导出显示文件名
	 * @return boolean if false will auto export excel file 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean isNotExcel(Collection<?> data,HttpServletResponse response,String filename) {
		if (this.isExcel()) {
			int posPoint = filename.indexOf(".");
			filename = posPoint == -1?filename + ".xls":filename;
			new ExportExcel(data,this.request,response,filename);
			return false;
		}
		else {
			return true;
		}
	}
	
	
	public void execute() {
		for (String key:params) {
			String paramValueStr = request.getParameter(key);
			connditionData.put(key, paramValueStr);
		}
		this.isExecute = true;
	}
	
	public void addParam(String key) {
		params.add(key);
	}
	
	
	
	/**
	 * 添加参数
	 * @param key T
	 * @param value V
	 */
	public void addParam(String key,Object value) {
		connditionData.put(key, value);
	}
	
	/**
	 * 获取对应的值
	 * @param key T
	 * @return V
	 */
	public Object getValue(String key) {
		if (!this.isExecute) {
			this.execute();
		}
		return connditionData.get(key);
	}
	
	public String getValueStr(String key) {
		if (!this.isExecute) {
			this.execute();
		}
		return TypeConvert.objToStrIsNotNull(connditionData.get(key));
	}
	
	/**
	 * 释放所以
	 */
	public void release() {
		connditionData.clear();
	}

	/**
	 * 
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	/**
	 * 获取排序列字段名称
	 * @return String
	 */
	public String getSortFieldName() {
		return this.getValueStr(SORT_NAME);
	}
	
	/**
	 * 获取排序列字段名称
	 * @return String
	 */
	public String getSortType() {
		return this.getValueStr(SORT_TYPE);
	}
	
	/**
	 * 设置数据对象值
	 * @param request HttpServletRequest
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			this.addParam(enu.nextElement());
		}
	}
	
	/**
	 * 自动Order By
	 * @param sql String
	 * @param clazz Class
	 * @return String
	 */
	public String autoOrderBy(String sql,Class clazz) {
		return TypeConvert.autoOrderBy(sql, clazz, this);
	}
	
	//是否已经执行过初始化
	private boolean isExecute = false;
	
	/**
	 * 排序名称
	 */
	private static String SORT_NAME = "sort";
	
	/**
	 * 获取排序类型 ASC、DESC
	 */
	private static String SORT_TYPE = "order";
	

}
