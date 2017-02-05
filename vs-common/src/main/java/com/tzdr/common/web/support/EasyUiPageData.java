package com.tzdr.common.web.support;

import java.util.List;

import com.tzdr.common.domain.PageInfo;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月4日 下午1:33:11
 * 类说明
 */
public class EasyUiPageData<T> {
	
	/**
	 * 总行数
	 */
	private long total;
	
	/**
	 * 当前页数据
	 */
	private List<T> rows;
	
	private T footer;
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public EasyUiPageData() {
		// TODO Auto-generated constructor stub
	}

	public EasyUiPageData(PageInfo<T> pageInfo) {
		
		this.rows = pageInfo.getPageResults();
		this.total = pageInfo.getTotalCount();
	}

	public EasyUiPageData(long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public T getFooter() {
		return footer;
	}

	public void setFooter(T footer) {
		this.footer = footer;
	}

	public EasyUiPageData(PageInfo<T> pageInfo,T footer) {
		
		this.rows = pageInfo.getPageResults();
		this.total = pageInfo.getTotalCount();
		this.footer = footer;
	}
	
}
