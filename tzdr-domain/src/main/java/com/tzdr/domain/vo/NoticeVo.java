package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;

/**
 * 公告实体对象
 * @Description: TODO(显示公告信息) 
 * @author liuhaichuan
 * @date 2015年4月28日
 *
 */
public class NoticeVo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3890041062609526323L;

	@SqlColumn
	private String id;
	
	@SqlColumn
	private String content;
	
	@SqlColumn
	private int status;
	
	@SqlColumn
	private int diff;

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
