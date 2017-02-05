package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.tzdr.common.domain.BaseEntityCpp;
@Entity
@Table(name = "a_currency_list")
public class Currency extends BaseEntityCpp{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String 	currencyNo;
	
	private String  currencyName;
	
	private Double  exchangeRate;
	
	
	@Id
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column(name = "CurrencyNo")
	public String getCurrencyNo() {
		return currencyNo;
	}
	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}
	@Column(name = "CurrencyName")
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	@Column(name = "ExchangeRate")
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	
}
