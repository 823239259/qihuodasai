package com.tzdr.domain.vo;

import java.io.Serializable;



public class CommodityVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private byte index;
	private String commodityNo;
	private String commodityName;
	private String exchangeNo;
	private String exchangeName;
	private String timeBucket;
	private Double contractSize;
	private Double miniTikeSize;
	private Double dotSize;
	private String currencyNo;
	private String mainContract;
	private Double depositRatio;
	private Double tradeFee;
	private String delflag;
	
	

	

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public byte getIndex() {
		return index;
	}

	public void setIndex(byte index) {
		this.index = index;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getTimeBucket() {
		return timeBucket;
	}

	public void setTimeBucket(String timeBucket) {
		this.timeBucket = timeBucket;
	}

	public Double getContractSize() {
		return contractSize;
	}

	public void setContractSize(Double contractSize) {
		this.contractSize = contractSize;
	}

	

	public Double getMiniTikeSize() {
		return miniTikeSize;
	}

	public void setMiniTikeSize(Double miniTikeSize) {
		this.miniTikeSize = miniTikeSize;
	}

	public Double getDotSize() {
		return dotSize;
	}

	public void setDotSize(Double dotSize) {
		this.dotSize = dotSize;
	}

	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}

	public String getMainContract() {
		return mainContract;
	}

	public void setMainContract(String mainContract) {
		this.mainContract = mainContract;
	}

	public Double getDepositRatio() {
		return depositRatio;
	}

	public void setDepositRatio(Double depositRatio) {
		this.depositRatio = depositRatio;
	}

	public Double getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(Double tradeFee) {
		this.tradeFee = tradeFee;
	}
	
	
}
