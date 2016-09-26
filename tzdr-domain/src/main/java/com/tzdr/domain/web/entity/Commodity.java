package com.tzdr.domain.web.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "a_commodity_list")
public class Commodity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private byte index;
	
	@Id
	@Column(name = "CommodityNo")
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
	
	public Commodity(){};

	public Commodity(byte index, String commodityNo, String commodityName, String exchangeNo, String exchangeName,
			String timeBucket, Double contractSize, Double miniTikeSize, Double dotSize, String currencyNo,
			String mainContract, Double depositRatio, Double tradeFee) {
		this.index = index;
		this.commodityNo = commodityNo;
		this.commodityName = commodityName;
		this.exchangeNo = exchangeNo;
		this.exchangeName = exchangeName;
		this.timeBucket = timeBucket;
		this.contractSize = contractSize;
		this.miniTikeSize = miniTikeSize;
		this.dotSize = dotSize;
		this.currencyNo = currencyNo;
		this.mainContract = mainContract;
		this.depositRatio = depositRatio;
		this.tradeFee = tradeFee;
	}

	@Column(name = "Index")
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

	@Column(name = "CommodityName")
	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	@Column(name = "ExchangeNo")
	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	@Column(name = "ExchangeName")
	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	@Column(name = "TimeBucket")
	public String getTimeBucket() {
		return timeBucket;
	}

	public void setTimeBucket(String timeBucket) {
		this.timeBucket = timeBucket;
	}

	@Column(name = "ContractSize")
	public Double getContractSize() {
		return contractSize;
	}

	public void setContractSize(Double contractSize) {
		this.contractSize = contractSize;
	}

	@Column(name = "MiniTikeSize")
	public Double getMiniTikeSize() {
		return miniTikeSize;
	}

	public void setMiniTikeSize(Double miniTikeSize) {
		this.miniTikeSize = miniTikeSize;
	}

	@Column(name = "DotSize")
	public Double getDotSize() {
		return dotSize;
	}

	public void setDotSize(Double dotSize) {
		this.dotSize = dotSize;
	}

	@Column(name = "CurrencyNo")
	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}

	@Column(name = "MainContract")
	public String getMainContract() {
		return mainContract;
	}

	public void setMainContract(String mainContract) {
		this.mainContract = mainContract;
	}

	@Column(name = "DepositRatio")
	public Double getDepositRatio() {
		return depositRatio;
	}

	public void setDepositRatio(Double depositRatio) {
		this.depositRatio = depositRatio;
	}

	@Column(name = "TradeFee")
	public Double getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(Double tradeFee) {
		this.tradeFee = tradeFee;
	}
	
	
}
