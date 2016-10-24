package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
@Entity
@Table(name = "crawler_url_param")
public class CrawlerUrlParam extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * url ID
	 */
	private String urlParamUrlId;
	/**
	 * 参数key
	 */
	private String urlParamKey;
	/**
	 * 参数value
	 */
	private String urlParamValue;
	/**
	 * 创建时间
	 */
	private Long urlParamCreatetime;
	/**
	 * 修改时间
	 */
	private Long urlParamUpdatetime;
	public String getUrlParamUrlId() {
		return urlParamUrlId;
	}
	public void setUrlParamUrlId(String urlParamUrlId) {
		this.urlParamUrlId = urlParamUrlId;
	}
	public String getUrlParamKey() {
		return urlParamKey;
	}
	public void setUrlParamKey(String urlParamKey) {
		this.urlParamKey = urlParamKey;
	}
	public String getUrlParamValue() {
		return urlParamValue;
	}
	public void setUrlParamValue(String urlParamValue) {
		this.urlParamValue = urlParamValue;
	}
	public Long getUrlParamCreatetime() {
		return urlParamCreatetime;
	}
	public void setUrlParamCreatetime(Long urlParamCreatetime) {
		this.urlParamCreatetime = urlParamCreatetime;
	}
	public Long getUrlParamUpdatetime() {
		return urlParamUpdatetime;
	}
	public void setUrlParamUpdatetime(Long urlParamUpdatetime) {
		this.urlParamUpdatetime = urlParamUpdatetime;
	}
	public CrawlerUrlParam(String urlParamUrlId, String urlParamKey, String urlParamValue, Long urlParamCreatetime,
			Long urlParamUpdatetime) {
		super();
		this.urlParamUrlId = urlParamUrlId;
		this.urlParamKey = urlParamKey;
		this.urlParamValue = urlParamValue;
		this.urlParamCreatetime = urlParamCreatetime;
		this.urlParamUpdatetime = urlParamUpdatetime;
	}
	public CrawlerUrlParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
