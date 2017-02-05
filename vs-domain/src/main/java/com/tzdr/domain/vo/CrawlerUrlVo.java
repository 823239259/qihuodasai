package com.tzdr.domain.vo;

import java.io.Serializable;


public class CrawlerUrlVo implements Serializable{
	private static final long serialVersionUID = -2930503022118437304L;
	private String id;
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
}
