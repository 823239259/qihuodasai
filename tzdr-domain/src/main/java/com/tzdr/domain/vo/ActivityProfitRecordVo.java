package com.tzdr.domain.vo;

import java.math.BigInteger;


/**
 * 8800 活动用户VO
 * @zhouchen
 * 2015年2月9日
 */
public class ActivityProfitRecordVo{

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	/**
	 * 账户id
	 */
	private String uid;
	private Double profit;
	private String profit_rate;
	private String grownum;
	private BigInteger countnum;//热门股用户数
	private String stockname;//股票名称
	private String stock_code;//股票编号
	

	public String getStock_code() {
		return stock_code;
	}

	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}

	public String getStockname() {
		return stockname;
	}

	public void setStockname(String stockname) {
		this.stockname = stockname;
	}

	public String getGrownum() {
		return grownum;
	}

	public BigInteger getCountnum() {
		return countnum;
	}

	public void setCountnum(BigInteger countnum) {
		this.countnum = countnum;
	}

	public void setGrownum(String grownum) {
		this.grownum = grownum;
	}

	public String getProfit_rate() {
		return profit_rate;
	}

	public void setProfit_rate(String profit_rate) {
		this.profit_rate = profit_rate;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	/**
	 * 账户手机号
	 */
	private String mobile;
	
	
	/**
	 * 配资groupId
	 */
	private String tradeGroupId;
	
	
	/**
	 * 母账户编号
	 */
	private String parentAccountNo;

	/**
	 * 组合编号
	 */
	private String combineId;
	
	/**
	 * 状态
	 */
	private short status;

	/**
	 * 盈利
	 */
	private double  accrual=0.0;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTradeGroupId() {
		return tradeGroupId;
	}

	public void setTradeGroupId(String tradeGroupId) {
		this.tradeGroupId = tradeGroupId;
	}

	public String getParentAccountNo() {
		return parentAccountNo;
	}

	public void setParentAccountNo(String parentAccountNo) {
		this.parentAccountNo = parentAccountNo;
	}

	public String getCombineId() {
		return combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	public double getAccrual() {
		return accrual;
	}

	public void setAccrual(double accrual) {
		this.accrual = accrual;
	}
	
	
	
}