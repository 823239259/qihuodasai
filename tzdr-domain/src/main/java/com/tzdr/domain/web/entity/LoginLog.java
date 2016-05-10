package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;

/**
 * 
 * @author LiuYang
 *
 * 2015年6月18日 上午10:40:48
 */
@Entity
@Table(name="w_login_log")
public class LoginLog extends BaseEntity{

	
	private static final long serialVersionUID = 3122117906180236825L;
	@Column
	private String uid;
	
	@Column
	private Long loginTime=Dates.getCurrentLongDate();
	
	@Column
	private String loginIp;
	
	@Column
	private String city;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
	public LoginLog() {
		// TODO Auto-generated constructor stub
	}

	public LoginLog(String uid, String loginIp, String city) {
		super();
		this.uid = uid;
		this.loginIp = loginIp;
		this.city = city;
	}
	
	
	
}
