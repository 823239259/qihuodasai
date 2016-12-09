package com.tzdr.domain.vo;


public class TradeExclDetailVo{
	private String commodityNo;
	private String contractNo;
	private String drection;
	private String tradePrice;
	private String tradeNum;
	private String orderPrice;
	private String free;
	private String orderType;
	private String marketDate;
	private String marketTime;
	private String orderUser;
	private String flat;
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getDrection() {
		return drection;
	}
	public void setDrection(String drection) {
		this.drection = drection;
	}
	public String getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}
	public String getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
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
	public String getMarketDate() {
		return marketDate;
	}
	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}
	public String getMarketTime() {
		return marketTime;
	}
	public void setMarketTime(String marketTime) {
		this.marketTime = marketTime;
	}
	public String getOrderUser() {
		return orderUser;
	}
	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	public TradeExclDetailVo(String commodityNo, String contractNo, String drection, String tradePrice, String tradeNum,
			String orderPrice, String free, String orderType, String marketDate, String marketTime, String orderUser,
			String flat) {
		this.commodityNo = commodityNo;
		this.contractNo = contractNo;
		this.drection = drection;
		this.tradePrice = tradePrice;
		this.tradeNum = tradeNum;
		this.orderPrice = orderPrice;
		this.free = free;
		this.orderType = orderType;
		this.marketDate = marketDate;
		this.marketTime = marketTime;
		this.orderUser = orderUser;
		this.flat = flat;
	}
	public TradeExclDetailVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
