package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
@Entity
@Table(name = "crawler_url")
public class CrawlerUrl extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 请求标题
	 */
	private String urlTitle;
	/**
	 * 请求url
	 */
	private String urlUrl;
	/**
	 * 状态（0-未执行，1-已执行）
	 */
	private String status;
	/**
	 * 请求备注
	 */
	private String urlRemarks;
	/**
	 * 请求方式
	 */
	private String urlMethod;
	/**
	 * 任务执行规则
	 */
	private String  execRule;
	/**
	 * 上一次开启时间（任务开启）
	 */
	private Long lastOpentime;
	/**
	 * 请求创建时间
	 */
	private Long urlCreatetime;
	/**
	 * 请求修改时间
	 */
	private Long urlUpdatetime;
	/**
	 * 最新一条数据的id
	 */
	private String lastWallstreetnId;
	/**
	 * 第三方最新一条数据的时间
	 */
	private String lastWallstreetnTime;
	/**
	 * 开始截取数据的时间
	 */
	private String startTime;
	/**
	 * 获取数据类型（0-实时，1-日历）
	 */
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrlTitle() {
		return urlTitle;
	}
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}
	public String getUrlUrl() {
		return urlUrl;
	}
	public void setUrlUrl(String urlUrl) {
		this.urlUrl = urlUrl;
	}
	public String getUrlRemarks() {
		return urlRemarks;
	}
	public void setUrlRemarks(String urlRemarks) {
		this.urlRemarks = urlRemarks;
	}
	public String getUrlMethod() {
		return urlMethod;
	}
	public void setUrlMethod(String urlMethod) {
		this.urlMethod = urlMethod;
	}
	public Long getUrlCreatetime() {
		return urlCreatetime;
	}
	public void setUrlCreatetime(Long urlCreatetime) {
		this.urlCreatetime = urlCreatetime;
	}
	public Long getUrlUpdatetime() {
		return urlUpdatetime;
	}
	public void setUrlUpdatetime(Long urlUpdatetime) {
		this.urlUpdatetime = urlUpdatetime;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getExecRule() {
		return execRule;
	}
	public void setExecRule(String execRule) {
		this.execRule = execRule;
	}
	public Long getLastOpentime() {
		return lastOpentime;
	}
	public void setLastOpentime(Long lastOpentime) {
		this.lastOpentime = lastOpentime;
	}
	
	public String getLastWallstreetnId() {
		return lastWallstreetnId;
	}
	public void setLastWallstreetnId(String lastWallstreetnId) {
		this.lastWallstreetnId = lastWallstreetnId;
	}
	
	public String getLastWallstreetnTime() {
		return lastWallstreetnTime;
	}
	public void setLastWallstreetnTime(String lastWallstreetnTime) {
		this.lastWallstreetnTime = lastWallstreetnTime;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public CrawlerUrl() {
		super();
	}
	
}
