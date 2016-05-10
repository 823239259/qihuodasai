package com.tzdr.api.request;

/**
 * 
 * <B>说明: </B>密码重置接口请求
 * @zhouchen
 * 2016年1月20日
 */
public class PasswordRequest extends BaseRequet {

	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 新密码
	 */
	private String newPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	
	
}
