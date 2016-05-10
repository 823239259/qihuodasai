package com.tzdr.common.web.support;

import java.util.List;
import java.util.Map;

import com.tzdr.common.domain.PageInfo;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月31日 下午1:31:28
 * 多表查询时  参数配置
 */
public class MultiListParam {

	/**
	 *  分页条件
	 */
	private PageInfo<Object> pageInfo;
	
	/**
	 * 搜索条件
	 */
	private Map<String, Object> searchMap;
	
	/** 
	 * sql 语句
	 */
	private String sql;
	
	/**
	 * 查询参数
	 */
	private List<Object> params;

	/**
	 * 排序列字段名。
	 */
	private String sort;
	
	/**
	 * 排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'。
	 */
	private String order;
	
	
	public PageInfo<Object> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<Object> pageInfo) {
		this.pageInfo = pageInfo;
	}

	public Map<String, Object> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<String, Object> searchMap) {
		this.searchMap = searchMap;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
	
	public MultiListParam() {
		// TODO Auto-generated constructor stub
	}

	public MultiListParam(PageInfo<Object> pageInfo,
			Map<String, Object> searchMap, String sql, List<Object> params) {
		super();
		this.pageInfo = pageInfo;
		this.searchMap = searchMap;
		this.sql = sql;
		this.params = params;
	}

	/**
	 * WEB 端分页查询  只需用 这个构造方法
	 * @param pageInfo 分页条件
	 * @param sql  sql语句
	 * @param params sql参数
	 */
	public MultiListParam(PageInfo<Object> pageInfo, String sql, List<Object> params) {
		super();
		this.pageInfo = pageInfo;
		this.sql = sql;
		this.params = params;
	}
	
	public MultiListParam(PageInfo<Object> pageInfo,
			Map<String, Object> searchMap, String sql, List<Object> params,
			String sort, String order) {
		super();
		this.pageInfo = pageInfo;
		this.searchMap = searchMap;
		this.sql = sql;
		this.params = params;
		this.sort = sort;
		this.order = order;
	}

	public MultiListParam(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams,List<Object> params,String sql){
		this.pageInfo  = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		this.params=params;
		this.sort = easyUiPage.getSort();
		this.order = easyUiPage.getOrder();
		this.searchMap=searchParams;
		this.sql = sql;
	}
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public MultiListParam(Map<String, Object> searchMap, String sql, List<Object> params) {
		super();
		this.searchMap = searchMap;
		this.sql = sql;
		this.params = params;
	}
	
}
