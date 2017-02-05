package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.BigDecimalUtils;

public class PositionDetailsVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -396256414926751283L;
    
	/**
	 * 证券代码
	 */
	private String stockCode;
	

	/**
	 * 成本价

	 */
	private double stockCost=0.0;
	
	/**
	 * 持仓数量

	 */
	private double currentAmount=0.0;
	
	/**
	 * 持仓市值
	 */
	private double marketValue=0.0;
	
	/**
	 * 持仓成本
	 */
	private double costBalance=0.0;
	
	/**
	 * 浮动盈亏
	 */
	private double profitAndLoss=0.0;
	
	/**
	 * 浮盈率(%)
	 */
	private double profitAndLossPer=0.0;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public double getStockCost() {
		return BigDecimalUtils.div(costBalance,currentAmount,3);
	}

	public void setStockCost(double stockCost) {
		this.stockCost = stockCost;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	public double getCostBalance() {
		return costBalance;
	}

	public void setCostBalance(double costBalance) {
		this.costBalance = costBalance;
	}

	public double getProfitAndLoss() {
		return BigDecimalUtils.round(BigDecimalUtils.sub(marketValue, costBalance), 2); 
	}

	public void setProfitAndLoss(double profitAndLoss) {
		this.profitAndLoss =profitAndLoss;
	}

	public double getProfitAndLossPer() {
		return BigDecimalUtils.mul(BigDecimalUtils.div(BigDecimalUtils.round(BigDecimalUtils.sub(marketValue, costBalance), 2), costBalance, 4),100) ; 
	}

	public void setProfitAndLossPer(double profitAndLossPer) {
		this.profitAndLossPer =profitAndLossPer;
	}	
}
