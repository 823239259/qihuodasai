package com.tzdr.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页对象
 * 
 * @author LinFeng
 * 
 */
public class PageInfo<T> {
	/** 查询分页结果 mybatis中要自己再把结果集set进来 */
	private List<T> pageResults;

	/** 当页记录数 默认为10条记录 */
	private int countOfCurrentPage = 10;

	/** 当前页号 */
	private int currentPage;

	/** 总共记录数 */
	private long totalCount;

	/** 总共页数 */
	private long totalPage;

	/** 构造函数 */
	public PageInfo() {
		this.currentPage = 1;
		this.countOfCurrentPage = 10;
		this.totalCount = 0;
		this.totalPage = 0;
	}

	public PageInfo(int countOfCurrentPage, int currentPage) {
		super();
		this.countOfCurrentPage = countOfCurrentPage;
		this.currentPage = currentPage;
	}
	
	/**
	 * 
	 * @param currentPageStr String 当前页
	 * @param pageSizeStr String 当前页大小
	 */
	public PageInfo(HttpServletRequest request) {
		String pageStr = request.getParameter("page");
		String rowsStr = request.getParameter("rows");
		if (pageStr == null || "".equals(pageStr)) {
			pageStr = "1";
		}
		if (rowsStr == null || "".equals(rowsStr)) {
			rowsStr = "10";
		}
		this.setCurrentPage(new Integer(pageStr));
		this.setCountOfCurrentPage(new Integer(rowsStr));
	}

	/**
	 * 
	 * @param currentPageStr String 当前页
	 * @param pageSizeStr String 当前页大小
	 */
	public PageInfo(String currentPageStr,String pageSizeStr) {
		this.setCurrentPage(new Integer(currentPageStr));
		this.setCountOfCurrentPage(new Integer(pageSizeStr));
	}

	/** 是否有下页 */
	public boolean hasNext() {
		return currentPage != totalPage;
	}

	/** 是否有上页 */
	public boolean hasPrevious() {
		return currentPage != 1;
	}

	public int getCountOfCurrentPage() {
		return countOfCurrentPage;
	}

	public void setCountOfCurrentPage(int countOfCurrentPage) {
		this.countOfCurrentPage = countOfCurrentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getPageResults() {
		if (this.pageResults == null) {
			this.pageResults = new ArrayList(0);
		}
		return pageResults;
	}

	public void setPageResults(List<T> pageResults) {
		this.pageResults = pageResults;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPage() {
		return ((totalCount + countOfCurrentPage) - 1) / countOfCurrentPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	/** 装配pageResults */
	public void calPageResults(List<T> results) {
		int iOffset;
		if (results == null) {
			throw new IllegalArgumentException("null argument!");
		}
		int iDatasSize = results.size();
		if (iDatasSize >= countOfCurrentPage * currentPage) {
			iOffset = countOfCurrentPage;
		} else {
			iOffset = iDatasSize - countOfCurrentPage * (currentPage - 1);
		}
		int iStart = countOfCurrentPage * (currentPage - 1);

		pageResults = results.subList(iStart, iStart + iOffset);
	}
}
