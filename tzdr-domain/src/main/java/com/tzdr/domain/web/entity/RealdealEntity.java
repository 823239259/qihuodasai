package com.tzdr.domain.web.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import com.tzdr.common.api.hundsun.data.Realdeal;

/**
 * 历史成交查询
 * @zhouchen
 * 2015年1月17日
 */
@Entity
@Table(name="w_realdeal")
public class RealdealEntity{

	@Id
	@Column(name = "id", length = 32, nullable = false)
	protected String id;
    
	/**
	 * 成交日期
	 */
	private long initDate;
	
	private String positionStr;

	private long businessNo;

	private long batchNo;

	private long entrustNo;

	private String fundAccount;

	private String combineId;

	private String stockCode;

	private String exchangeType;

	private String stockAccount;

	private String seatNo;

	private String entrustDirection;
	
	private long businessTime;

	private double businessAmount;
	
	private double businessPrice;

	private double businessBalance;
	
	private String ampayoffType;
	
	private double totalFare;
	
	private double yjFare;
	
	private double ghFare;
	
	private double yhFare;
	
	private double jyFare;

	public String getPositionStr() {
		return positionStr;
	}

	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}

	public long getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(long businessNo) {
		this.businessNo = businessNo;
	}

	public long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(long batchNo) {
		this.batchNo = batchNo;
	}

	public long getEntrustNo() {
		return entrustNo;
	}

	public void setEntrustNo(long entrustNo) {
		this.entrustNo = entrustNo;
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

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
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

	public String getEntrustDirection() {
		return entrustDirection;
	}

	public void setEntrustDirection(String entrustDirection) {
		this.entrustDirection = entrustDirection;
	}

	public long getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(long businessTime) {
		this.businessTime = businessTime;
	}

	public double getBusinessAmount() {
		return businessAmount;
	}

	public void setBusinessAmount(double businessAmount) {
		this.businessAmount = businessAmount;
	}

	public double getBusinessPrice() {
		return businessPrice;
	}

	public void setBusinessPrice(double businessPrice) {
		this.businessPrice = businessPrice;
	}

	public double getBusinessBalance() {
		return businessBalance;
	}

	public void setBusinessBalance(double businessBalance) {
		this.businessBalance = businessBalance;
	}

	public String getAmpayoffType() {
		return ampayoffType;
	}

	public void setAmpayoffType(String ampayoffType) {
		this.ampayoffType = ampayoffType;
	}

	public double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}

	public double getYjFare() {
		return yjFare;
	}

	public void setYjFare(double yjFare) {
		this.yjFare = yjFare;
	}

	public double getGhFare() {
		return ghFare;
	}

	public void setGhFare(double ghFare) {
		this.ghFare = ghFare;
	}

	public double getYhFare() {
		return yhFare;
	}

	public void setYhFare(double yhFare) {
		this.yhFare = yhFare;
	}

	public double getJyFare() {
		return jyFare;
	}

	public void setJyFare(double jyFare) {
		this.jyFare = jyFare;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	public long getInitDate() {
		return initDate;
	}

	public void setInitDate(long initDate) {
		this.initDate = initDate;
	}

	public RealdealEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public  Object[] getObjectArray(){
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		return  new Object[]{uuid,this.ampayoffType,this.batchNo,this.businessAmount,this.businessBalance,
				this.businessNo,this.businessPrice,this.businessTime,this.combineId,this.entrustDirection,this.entrustNo,
				this.exchangeType,this.fundAccount,this.ghFare,this.initDate,this.jyFare,this.positionStr,this.seatNo,this.stockAccount,
				this.stockCode,this.totalFare,this.yhFare,this.yjFare};
	}
}
