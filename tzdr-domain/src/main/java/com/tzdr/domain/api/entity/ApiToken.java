package com.tzdr.domain.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.api.constants.Constant;



/**
 * app 应用token 记录
 * @author zhouchen
 * 2015年5月21日 上午11:50:30
 */
@Entity
@Table(name="w_api_token")
public class ApiToken extends BaseEntity {
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	
	/**
	 * 用户帐号
	 */
	private String userName;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 服务器固定ip
	 */
	private String ipAddr;
	
	/**
	 * token值
	 */
	private String token;
	
	/**
	 * 生成时间
	 */
	private Long  createTime=Dates.getCurrentLongDate();

	
	
	/**
	 * 失效时间
	 */
	private Long  invalidTime=Dates.getCurrentLongDate()+Constant.TOKEN_INVALID_TIME;

	
	/**
	 * 客户端来源 wap android  ios peigubao
	 */
	private String source;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	public ApiToken() {
		// TODO Auto-generated constructor stub
	}

	public ApiToken(String userName, String password, String ipAddr) {
		super();
		this.userName = userName;
		this.password = password;
		this.ipAddr = ipAddr;
	}

	public Long getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Long invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	
	
	
	
}