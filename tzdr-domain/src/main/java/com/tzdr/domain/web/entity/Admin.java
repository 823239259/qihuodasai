package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_admin")
public class Admin  extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String uname;
	private String tname;
	private String password;
	private String loginip;
	private String groupid;
	private String mobile;
	private Integer logintime;
	private short status;


	@Column(name="uname", nullable=false, length=100)
	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Column(name="tname", nullable=false, length=100)
	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Column(name="password", nullable=false, length=100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="loginip", length=100)
	public String getLoginip() {
		return this.loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	@Column(name="groupid", length=32)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@Column(name="mobile", nullable=false, length=11)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name="logintime")
	public Integer getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Integer logintime) {
		this.logintime = logintime;
	}

	@Column(name="status", nullable=false)
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
}