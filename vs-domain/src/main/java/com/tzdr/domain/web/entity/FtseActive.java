package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
/**
 * A50活动申请名额表
 * @author LiuYang
 *
 * 2015年11月3日 下午2:35:16
 */
@Entity
@Table(name = "f_ftse_active")
public class FtseActive extends BaseCrudEntity{
	private static final long serialVersionUID = -4052447915257806844L;
	
	/**
	 * 用户编号
	 */
	private String uid;
	
	/**
	 * 资格状态  1：拥有资格  2：名额已使用   0：名额已过期
	 */
	private Integer type;
	
	
	@Column(name = "uid", nullable = false, length = 32)
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
}
