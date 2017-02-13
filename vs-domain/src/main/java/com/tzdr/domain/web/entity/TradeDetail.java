package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
/**
 * 交易明细表
 */
@Entity
@Table(name = "w_trade_detail")
public class TradeDetail extends BaseEntity{

	private static final long serialVersionUID = 2953235263723340197L;
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
	private Long createTime;
	private Long updateTime;
	private String fastId;
	
	public String getFastId() {
		return fastId;
	}
	public void setFastId(String fastId) {
		this.fastId = fastId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
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
	
	 public TradeDetail() {
		// TODO Auto-generated constructor stub
	}
	public TradeDetail(String tradeDate, String username, String userNo, String currencyNo, String exchangeNo,
			String commodityNo, String buyNum, String sellNum, String tradePrice, String free, String orderType,
			String orderUserno, String orderUsername, String tradeType, Long createTime, Long updateTime,String fastId) {
		super();
		this.tradeDate = tradeDate;
		this.username = username;
		this.userNo = userNo;
		this.currencyNo = currencyNo;
		this.exchangeNo = exchangeNo;
		this.commodityNo = commodityNo;
		this.buyNum = buyNum;
		this.sellNum = sellNum;
		this.tradePrice = tradePrice;
		this.free = free;
		this.orderType = orderType;
		this.orderUserno = orderUserno;
		this.orderUsername = orderUsername;
		this.tradeType = tradeType;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.fastId = fastId;
	}
	
	
	
}
