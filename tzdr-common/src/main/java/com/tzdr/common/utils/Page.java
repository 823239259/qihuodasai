package com.tzdr.common.utils;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class Page implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer pageIndex;
	private Integer size;
	private Integer total;
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Page(HttpServletRequest request){
		String startIndex = request.getParameter("pageIndex");
		String size = request.getParameter("size");
		if(startIndex == null){
			startIndex = "0";
		}
		if(size == null){
			size = "20";
		}
		this.pageIndex = Integer.parseInt(startIndex);
		this.size = Integer.parseInt(size);
	}
	public Page(){}
}
