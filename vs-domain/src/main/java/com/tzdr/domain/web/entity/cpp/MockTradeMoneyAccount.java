package com.tzdr.domain.web.entity.cpp;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tzdr.cpp.DataSource;

@Entity
@Table(name = "mock_trade_money_account")
public class MockTradeMoneyAccount implements Serializable{
	private static final long serialVersionUID = -981212837386219580L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer accountId;
	private String  tradeAccountUsername;
	private String  currencyNo;
	private Double  oldCanUse;
	private Double  oldBalance;
	private Double  oldCanCashout;
	private Double  oldAmount;
	private Double  inMoney;
	private Double  outMoney;
	private Double  todayBalance;
	private Double  todayCanUse;
	private Double  todayCanCashout;
	private Double  todayAmount;
	private Double  counterFee;
	private Double  CloseProfit;
	private Double  dayCloseProfit;
	private Double  floatingProfit;
	private Double  dayFloatingProfit;
	private Double  premium;
	private Double  deposit;
	private Double  totalProfit;
	private Double  keepDeposit;
	private Double  frozenMoney;
	private Double  unexpiredProfit;
	private Double  unaccountProfit;
	private Double  riskRate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getTradeAccountUsername() {
		return tradeAccountUsername;
	}
	public void setTradeAccountUsername(String tradeAccountUsername) {
		this.tradeAccountUsername = tradeAccountUsername;
	}
	public String getCurrencyNo() {
		return currencyNo;
	}
	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}
	public Double getOldCanUse() {
		return oldCanUse;
	}
	public void setOldCanUse(Double oldCanUse) {
		this.oldCanUse = oldCanUse;
	}
	public Double getOldBalance() {
		return oldBalance;
	}
	public void setOldBalance(Double oldBalance) {
		this.oldBalance = oldBalance;
	}
	public Double getOldCanCashout() {
		return oldCanCashout;
	}
	public void setOldCanCashout(Double oldCanCashout) {
		this.oldCanCashout = oldCanCashout;
	}
	public Double getOldAmount() {
		return oldAmount;
	}
	public void setOldAmount(Double oldAmount) {
		this.oldAmount = oldAmount;
	}
	public Double getInMoney() {
		return inMoney;
	}
	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}
	public Double getOutMoney() {
		return outMoney;
	}
	public void setOutMoney(Double outMoney) {
		this.outMoney = outMoney;
	}
	public Double getTodayBalance() {
		return todayBalance;
	}
	public void setTodayBalance(Double todayBalance) {
		this.todayBalance = todayBalance;
	}
	public Double getTodayCanUse() {
		return todayCanUse;
	}
	public void setTodayCanUse(Double todayCanUse) {
		this.todayCanUse = todayCanUse;
	}
	public Double getTodayCanCashout() {
		return todayCanCashout;
	}
	public void setTodayCanCashout(Double todayCanCashout) {
		this.todayCanCashout = todayCanCashout;
	}
	public Double getTodayAmount() {
		return todayAmount;
	}
	public void setTodayAmount(Double todayAmount) {
		this.todayAmount = todayAmount;
	}
	public Double getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(Double counterFee) {
		this.counterFee = counterFee;
	}
	public Double getCloseProfit() {
		return CloseProfit;
	}
	public void setCloseProfit(Double closeProfit) {
		CloseProfit = closeProfit;
	}
	public Double getDayCloseProfit() {
		return dayCloseProfit;
	}
	public void setDayCloseProfit(Double dayCloseProfit) {
		this.dayCloseProfit = dayCloseProfit;
	}
	public Double getFloatingProfit() {
		return floatingProfit;
	}
	public void setFloatingProfit(Double floatingProfit) {
		this.floatingProfit = floatingProfit;
	}
	public Double getDayFloatingProfit() {
		return dayFloatingProfit;
	}
	public void setDayFloatingProfit(Double dayFloatingProfit) {
		this.dayFloatingProfit = dayFloatingProfit;
	}
	public Double getPremium() {
		return premium;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}
	public Double getKeepDeposit() {
		return keepDeposit;
	}
	public void setKeepDeposit(Double keepDeposit) {
		this.keepDeposit = keepDeposit;
	}
	public Double getFrozenMoney() {
		return frozenMoney;
	}
	public void setFrozenMoney(Double frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	public Double getUnexpiredProfit() {
		return unexpiredProfit;
	}
	public void setUnexpiredProfit(Double unexpiredProfit) {
		this.unexpiredProfit = unexpiredProfit;
	}
	public Double getUnaccountProfit() {
		return unaccountProfit;
	}
	public void setUnaccountProfit(Double unaccountProfit) {
		this.unaccountProfit = unaccountProfit;
	}
	public Double getRiskRate() {
		return riskRate;
	}
	public void setRiskRate(Double riskRate) {
		this.riskRate = riskRate;
	}
	
}
