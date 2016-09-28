package com.tzdr.domain.vo;
import java.io.Serializable;
public class CppVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String 	currencyNo;
	
	private String  currencyName;
	
	private Double  exchangeRate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCurrencyNo() {
		return currencyNo;
	}
	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	
}
