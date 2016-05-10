package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

/**
 * 系统公告
 * @Description: TODO(前台所有使用公共导航的页面都展示系统公告，cms后台管理) 
 * @author liuhaichuan
 * @date 2015年4月28日
 *
 */
@Entity
@Table(name="w_notice")
public class Notice extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6080555642679575257L;

	/**
	 * 字段名称：公告内容
	 * 字段说明：
	 */
	@Column(name="content")
	private String content;
	
	/**
	 * 字段名称：状态
	 * 字段说明：1：停用；2：启用
	 */
	@Column(name="status")
	private int status;
	
	/**
	 * 字段说明：1.投资达人  2.配股宝
	 */
	@Column(name="diff")
	private int diff;
	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
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
