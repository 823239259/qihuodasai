package com.tzdr.common.web.support;
/**
 * @author zhouchen
 * @version 创建时间：2014年12月4日 下午3:09:57
 * 类说明
 */
public class EasyUiPageInfo {
	/**
	 * 页码，起始值 1。
	 */
	private int  page;
	
	/**
	 * 每页显示行数
	 */
	private int  rows;
	
	/**
	 * 排序列字段名。
	 */
	private String sort;
	
	/**
	 * 排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'。
	 */
	private String order;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
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
	
	
}
