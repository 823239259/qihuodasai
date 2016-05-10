package com.tzdr.common.api.hundsun.data;

public class Combofund {
	
	private String fundAccount;
	
	private String combineId;
	
	private double enableBalance;
	
	private double enableBalanceT1;
	
	private long date;
	
	private String currencyNo;

	public String getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}

	public String getCombineId() {
		return combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	public double getEnableBalance() {
		return enableBalance;
	}

	public void setEnableBalance(double enableBalance) {
		this.enableBalance = enableBalance;
	}


	public double getEnableBalanceT1() {
		return enableBalanceT1;
	}

	public void setEnableBalanceT1(double enableBalanceT1) {
		this.enableBalanceT1 = enableBalanceT1;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}
	

}
