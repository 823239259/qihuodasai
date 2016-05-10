package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_article_category")
public class ArticleCategory  extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String fid;
	private String urlname;
	private String catname;
	private String keywords;
	private String description;
	private short orders;
	private short status;

	@Column(name="fid", length=32)
	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	@Column(name="urlname", length=100)
	public String getUrlname() {
		return this.urlname;
	}

	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}

	@Column(name="catname", nullable=false)
	public String getCatname() {
		return this.catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	@Column(name="keywords")
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name="description", length=65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="orders")
	public short getOrders() {
		return this.orders;
	}

	public void setOrders(short orders) {
		this.orders = orders;
	}

	@Column(name="status", nullable=false)
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
}