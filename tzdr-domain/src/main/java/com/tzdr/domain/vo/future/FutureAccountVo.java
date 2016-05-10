package com.tzdr.domain.vo.future;

import java.math.BigDecimal;

/**
 * 股指期货的账户信息
 * @Description: 
 * @author liuhaichuan
 * @date 2015年8月5日
 *
 */
public class FutureAccountVo {
	
	// 子账户总资产
	private BigDecimal totalMoney;
	// 股指保证金
	private BigDecimal cautionMoney;
	// 股指期货的账户余额
	private BigDecimal balanceMoney;	
	// 累计盈亏
	private BigDecimal cumulativeProfit;
	// 累计支出交易费
	private BigDecimal cumulativeTrans;
	// 累计获得利润
	private BigDecimal cumulativeTotal;
	// 实际盈利
	private BigDecimal actualProfit;
	// 盈亏率
	private BigDecimal profitRate;
	//交易个数
	private int transactions;
	
	public BigDecimal getCumulativeProfit() {
		return cumulativeProfit;
	}
	public void setCumulativeProfit(BigDecimal cumulativeProfit) {
		this.cumulativeProfit = cumulativeProfit;
	}
	public BigDecimal getCumulativeTrans() {
		return cumulativeTrans;
	}
	public void setCumulativeTrans(BigDecimal cumulativeTrans) {
		this.cumulativeTrans = cumulativeTrans;
	}
	public BigDecimal getCumulativeTotal() {
		return cumulativeTotal;
	}
	public void setCumulativeTotal(BigDecimal cumulativeTotal) {
		this.cumulativeTotal = cumulativeTotal;
	}
	public BigDecimal getCautionMoney() {
		return cautionMoney;
	}
	public void setCautionMoney(BigDecimal cautionMoney) {
		this.cautionMoney = cautionMoney;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public BigDecimal getBalanceMoney() {
		return balanceMoney;
	}
	public void setBalanceMoney(BigDecimal balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	public BigDecimal getActualProfit() {
		return actualProfit;
	}
	public void setActualProfit(BigDecimal actualProfit) {
		this.actualProfit = actualProfit;
	}
	public BigDecimal getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}
	public int getTransactions() {
		return transactions;
	}
	public void setTransactions(int transactions) {
		this.transactions = transactions;
	}

}
