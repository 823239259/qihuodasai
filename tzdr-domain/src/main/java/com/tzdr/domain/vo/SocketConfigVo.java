package com.tzdr.domain.vo;

import java.io.Serializable;

public class SocketConfigVo implements Serializable{
	private static final long serialVersionUID = -2671895821264515156L;
	private String id;
	/**
	 * app版本号
	 */
	private String appVersion;
	/**
	 * socket地址
	 */
	private String socketUrl;
	/**
	 * socket版本号
	 */
	private String socketVersion;
	/**
	 * 开发环境(0-开发，1-生产)
	 */
	private String isModel;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 修改时间
	 */
	private Long updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getSocketUrl() {
		return socketUrl;
	}
	public void setSocketUrl(String socketUrl) {
		this.socketUrl = socketUrl;
	}
	public String getSocketVersion() {
		return socketVersion;
	}
	public void setSocketVersion(String socketVersion) {
		this.socketVersion = socketVersion;
	}
	public String getIsModel() {
		return isModel;
	}
	public void setIsModel(String isModel) {
		this.isModel = isModel;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}
