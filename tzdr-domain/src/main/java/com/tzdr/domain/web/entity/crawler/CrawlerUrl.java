package com.tzdr.domain.web.entity.crawler;

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
	 * 请求备注
	 */
	private String urlRemarks;
	/**
	 * 请求方式
	 */
	private String urlMethod;
	/**
	 * 请求创建时间
	 */
	private String urlCreatetime;
	/**
	 * 请求修改时间
	 */
	private String urlUpdatetime;

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
	public String getUrlCreatetime() {
		return urlCreatetime;
	}
	public void setUrlCreatetime(String urlCreatetime) {
		this.urlCreatetime = urlCreatetime;
	}
	public String getUrlUpdatetime() {
		return urlUpdatetime;
	}
	public void setUrlUpdatetime(String urlUpdatetime) {
		this.urlUpdatetime = urlUpdatetime;
	}
}
