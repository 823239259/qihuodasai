package com.tzdr.domain.vo;

import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年4月16日下午1:01:09
 */
public class AgentChildVo {
	
	@SqlColumn
	private String id;
	
	@SqlColumn(name="parent_id")
	private String parentId;
	
	@SqlColumn
	private Integer childSize;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getChildSize() {
		return childSize;
	}
	public void setChildSize(Integer childSize) {
		this.childSize = childSize;
	}

}
