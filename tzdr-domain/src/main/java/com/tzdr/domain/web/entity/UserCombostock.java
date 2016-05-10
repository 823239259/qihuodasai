package com.tzdr.domain.web.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tzdr.common.utils.Dates;

@Entity
@Table(name="w_user_combostock")
public class UserCombostock {
	
	@Id
	@Column(name = "id", length = 32, nullable = false)
	protected String id;
	
	/**
	 * 保存日期
	 */
	private long saveTime=Dates.getCurrentLongDate();
	
	/**
	 * 日期
	 */
	private long initDate;
	
	/**
	 * 账户编号
	 */
	private String fundAccount;
	
	/**
	 * 组合编号
	 */
	private String combineId;
	
	/**
	 * 股东代码
	 */
	private String stockAccount;
	
	/**
	 * 托管代码
	 */
	private String seatNo;
	
	/**
	 * 市场类别
	 */
	private String exchangeType;
	
	/**
	 * 证券代码
	 */
	private String stockCode;
	
	/**
	 * 投资类型
	 */
	private String investWay;
	
	/**
	 * 多空标志
	 */
	private String pupilFlag;
	
	/**
	 * 期初数量
	 */
	private double beginAmount;
	
	/**
	 * 当前数量
	 */
	private double currentAmount;
	
	/**
	 * 可用数量
	 */
	private double enableAmount;
	
	/**
	 * 当前成本
	 */
	private double costBalance;
	
	/**
	 * 当前市值
	 */
	private double marketValue;
	
	/**
	 * 当日买入数量
	 */
	private double buyAmount;
	
	/**
	 * 当日卖出数量
	 */
	private double sellAmount;

	public long getInitDate() {
		return initDate;
	}

	public void setInitDate(long initDate) {
		this.initDate = initDate;
	}

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

	public String getStockAccount() {
		return stockAccount;
	}

	public void setStockAccount(String stockAccount) {
		this.stockAccount = stockAccount;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getInvestWay() {
		return investWay;
	}

	public void setInvestWay(String investWay) {
		this.investWay = investWay;
	}

	public String getPupilFlag() {
		return pupilFlag;
	}

	public void setPupilFlag(String pupilFlag) {
		this.pupilFlag = pupilFlag;
	}

	public double getBeginAmount() {
		return beginAmount;
	}

	public void setBeginAmount(double beginAmount) {
		this.beginAmount = beginAmount;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public double getEnableAmount() {
		return enableAmount;
	}

	public void setEnableAmount(double enableAmount) {
		this.enableAmount = enableAmount;
	}

	public double getCostBalance() {
		return costBalance;
	}

	public void setCostBalance(double costBalance) {
		this.costBalance = costBalance;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	public double getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}

	public double getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(double sellAmount) {
		this.sellAmount = sellAmount;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(long saveTime) {
		this.saveTime = saveTime;
	}

	public  Object[] getObjectArray(){
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		return  new Object[]{this.saveTime,uuid,this.beginAmount,this.buyAmount,this.combineId,this.costBalance,this.currentAmount,this.enableAmount,
				this.exchangeType,this.fundAccount,this.initDate,this.investWay,this.marketValue,this.pupilFlag,
				this.seatNo,this.sellAmount,this.stockAccount,this.stockCode};
	}
	
		
}
