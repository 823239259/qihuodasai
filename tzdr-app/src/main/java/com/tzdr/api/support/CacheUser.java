package com.tzdr.api.support;

import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;


/**
 * <B>说明: </B> 系统缓存的用户信息
 * @zhouchen
 * 2016年1月20日
 */
public class CacheUser {
	
	/**
	 * 用户id
	 */
	private String uid;
	
	/**
	 * 用户手机号
	 */
	private String mobile;
	
	/**
	 * 加密密钥  由密码种子+手机号生成的md5加密值
	 */
	private String secret;
	
	/**
	 * 用户姓名
	 */
	private String name;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public CacheUser() {
	}
	
	public CacheUser(WUser wUser,String secret) {
		this.mobile=wUser.getMobile();
		UserVerified  userVerified  = wUser.getUserVerified();
		if (null !=  userVerified ){
			this.name=userVerified.getTname();			
		}
		this.secret = secret;
		this.uid = wUser.getId();
	}
	
	
}
