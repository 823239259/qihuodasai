package com.tzdr.web.utils;

import java.io.Serializable;

/**
* @Description: TODO(登录用户session信息)
* @ClassName: UserSessionBean
* @author wangpinqun
* @date 2015年2月13日 下午5:26:28
 */
public class UserSessionBean implements Serializable{
	
	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String uname;
	
	private String email;
	
	private String mobile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
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
}
