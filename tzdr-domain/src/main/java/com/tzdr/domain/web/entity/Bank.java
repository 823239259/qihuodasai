package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_bank")
public class Bank  extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String bank;
	private String province;
	private String city;
	private String subbank;
	private Integer orderNo;



	@Column(name="bank", nullable=false, length=100)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name="province", nullable=false, length=100)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name="city", nullable=false, length=100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="subbank", nullable=false)
	public String getSubbank() {
		return this.subbank;
	}

	public void setSubbank(String subbank) {
		this.subbank = subbank;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	
}