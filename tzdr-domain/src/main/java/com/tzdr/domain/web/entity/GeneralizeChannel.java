package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name="w_generalize_channel")
public class GeneralizeChannel extends BaseEntity{

	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;

	private String typeOneTitle;

	private String typeOneParam;

	private String typeTwoTitle;

	private String typeTwoParam;

	private String typeThreeTitle;

	private String typeThreeParam;

	private String param;

	private String urlKey;

	private String title;

	private Integer sort;

	private Long createDate;
	

	@Column(name = "type_one_title")
	public String getTypeOneTitle() {
		return typeOneTitle;
	}

	public void setTypeOneTitle(String typeOneTitle) {
		this.typeOneTitle = typeOneTitle;
	}

	@Column(name = "type_one_param")
	public String getTypeOneParam() {
		return typeOneParam;
	}

	public void setTypeOneParam(String typeOneParam) {
		this.typeOneParam = typeOneParam;
	}

	@Column(name = "type_two_title")
	public String getTypeTwoTitle() {
		return typeTwoTitle;
	}

	public void setTypeTwoTitle(String typeTwoTitle) {
		this.typeTwoTitle = typeTwoTitle;
	}

	@Column(name = "type_two_param")
	public String getTypeTwoParam() {
		return typeTwoParam;
	}

	public void setTypeTwoParam(String typeTwoParam) {
		this.typeTwoParam = typeTwoParam;
	}

	@Column(name = "type_three_title")
	public String getTypeThreeTitle() {
		return typeThreeTitle;
	}

	public void setTypeThreeTitle(String typeThreeTitle) {
		this.typeThreeTitle = typeThreeTitle;
	}

	@Column(name = "type_three_param")
	public String getTypeThreeParam() {
		return typeThreeParam;
	}

	public void setTypeThreeParam(String typeThreeParam) {
		this.typeThreeParam = typeThreeParam;
	}

	@Column(name = "param")
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name="url_key")
	public String getUrlKey() {
		return urlKey;
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}

	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
}
