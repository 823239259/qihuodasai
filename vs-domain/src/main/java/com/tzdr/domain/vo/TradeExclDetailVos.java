package com.tzdr.domain.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TradeExclDetailVos implements Serializable{
	private String tradeDate;
	private String username;
	private String userNo;
	private String currencyNo;
	private String exchangeNo;
	private String commodityNo;
	private String buyNum;
	private String sellNum;
	private String tradePrice;
	private String free;
	private String orderType;
	private String orderUserno;
	private String orderUsername;
	private String tradeType;
	private String fastId;  //方案Id
	private String tradeNo;  //成交号
	
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getCurrencyNo() {
		return currencyNo;
	}
	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}
	public String getExchangeNo() {
		return exchangeNo;
	}
	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getSellNum() {
		return sellNum;
	}
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
	}
	public String getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderUserno() {
		return orderUserno;
	}
	public void setOrderUserno(String orderUserno) {
		this.orderUserno = orderUserno;
	}
	public String getOrderUsername() {
		return orderUsername;
	}
	public void setOrderUsername(String orderUsername) {
		this.orderUsername = orderUsername;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getFastId() {
		return fastId;
	}
	public void setFastId(String fastId) {
		this.fastId = fastId;
	}
	
	
	
}
