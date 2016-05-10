package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 股票维护  停牌股   限制股
 * @zhouchen
 * 2015年2月12日
 */
@Entity
@Table(name = "w_stock")
public class Stock extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 股票代码
	 */
	private String code;
	
	/**
	 * 股票名称
	 */
	private String name;

	
	/**
	 * 生效日期  限制日期/停牌日期
	 */
	private String  effectiveDate;

	/**
	 * 1:限制股  2：停牌股
	 */
	private Integer  type=0;

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEffectiveDate() {
		return effectiveDate;
	}


	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
}