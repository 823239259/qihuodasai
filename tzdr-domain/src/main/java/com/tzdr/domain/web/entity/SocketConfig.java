package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name = "socket_config")
public class SocketConfig extends BaseEntity{
	private static final long serialVersionUID = 8851196369092853081L;
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
