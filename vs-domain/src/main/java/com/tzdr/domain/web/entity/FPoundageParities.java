package com.tzdr.domain.web.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.Dates;

@Entity
@Table(name = "f_poundage_parities")
public class FPoundageParities  extends BaseCrudEntity{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 汇率兑换类型【1：美元,2：港元,3:欧元,4:日元,5：人民币】
	 */
	private Integer type;
	
	/**
	 * 货币简称
	 */
	private String currencyNo;
	
	/**
	 * 货币名称
	 */
	private String typeName;

	/**
	 * 对美元汇率
	 */
	private BigDecimal parities;
	
	/**
	 * 添加时间
	 */
	private Long  addTime = Dates.getCurrentLongDate();
	
	
	
	@Column(name = "type_name")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "parities")
	public BigDecimal getParities() {
		return parities;
	}

	public void setParities(BigDecimal parities) {
		this.parities = parities;
	}
	@Column(name = "add_time")
	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}
	@Column(name = "currency_no")
	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	} 
	
	
}