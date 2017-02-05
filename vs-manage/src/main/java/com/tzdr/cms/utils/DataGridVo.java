package com.tzdr.cms.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>总数</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2014年12月30日上午10:35:45
 * @param <T>
 */
public class DataGridVo<T> implements Serializable {
	
	private static final long serialVersionUID = -2910339549119857970L;
	
	//总计
	private Long total;
	//总数
	private List<T> rows = new ArrayList<T>();
	//表格地步信息（例如统计平均值，总价等）
	private List<Map<String, Object>> footer = new ArrayList<Map<String,Object>>(0);
	
	public DataGridVo() {
		super();
	}
	
	public void add(Collection<T> ts) {
		if (ts != null && ts.size() > 0) {
			for (T t:ts) {
				this.add(t);
			}
		}
	}

	public void add(T t) {
		rows.add(t);
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<Map<String, Object>> getFooter() {
		return footer;
	}

	public void setFooter(List<Map<String, Object>> footer) {
		this.footer = footer;
	}
}
