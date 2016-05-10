package com.tzdr.common.api.hundsun.data;

/**
 * @Description:
 * @ClassName: StockCurrent.java
 * @author Lin Feng
 * @date 2015年2月5日
 */
public class StockCurrent {
	
	private String fundAccount;
	
	private String combineId;
	
	private long assetId;
	
	private double currentCash;
	
	private double marketValue;

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

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

	public double getCurrentCash() {
		return currentCash;
	}

	public void setCurrentCash(double currentCash) {
		this.currentCash = currentCash;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
		

}
