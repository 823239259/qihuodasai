package com.tzdr.domain.api.vo;

import java.io.Serializable;

/**
 * wuser vo
 * @author zhouchen
 * 2015年5月26日 上午9:57:05
 */
public class ApiUserVo implements Serializable  {
	
	
	private static final long serialVersionUID = -3390139639987375733L;
	private String tname;//用户姓名
	private String email;//邮箱
	private String mobile;//手机号
	private String uid; // 用户id
	private String password;//用户密码
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
