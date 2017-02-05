package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

@Entity
@Table(name = "w_trade_config")
public class TradeConfig extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 	期限开始
	 */
	private Integer dayRangeStart=0;
	
	
	/**
	 * 	期限结束
	 */
	private Integer dayRangeEnd=0;
	
	
	/**
	 * 保证金开始
	 */
	private Double  depositRangeStart=0.0;
	
	
	/**
	 * 保证金结束
	 */
	private Double  depositRangeEnd=0.0;
	

	/**
	 * 倍数开始值
	 */
	private Integer multipleRangeStart=0;
	
	/**
	 * 倍数结束
	 */
	private Integer multipleRangeEnd=0;
	
	/**
	 * 年利息
	 */
	private Double  yearInterest=0.0;
	
	/**
	 * 日利息
	 */
	private Double  dailyInterest=0.0;
	
	
	/**
	 * 年管理费
	 */
	private Double  yearManagementFee=0.0;
	
	
	/**
	 * 日管理费
	 */
	private Double  dailyManagementFee=0.0;

	
	/**
	 * 默认计算 管理费天数
	 */
	private Double  managementFeeDay=360.0;
	
	
	/**
	 * 默认计算 利息天数
	 */
	private Double  interestDay=360.0;

	public Integer getDayRangeStart() {
		return dayRangeStart;
	}


	public void setDayRangeStart(Integer dayRangeStart) {
		this.dayRangeStart = dayRangeStart;
	}


	public Integer getDayRangeEnd() {
		return dayRangeEnd;
	}


	public void setDayRangeEnd(Integer dayRangeEnd) {
		this.dayRangeEnd = dayRangeEnd;
	}


	public Double getDepositRangeStart() {
		return depositRangeStart;
	}


	public void setDepositRangeStart(Double depositRangeStart) {
		this.depositRangeStart = depositRangeStart;
	}


	public Double getDepositRangeEnd() {
		return depositRangeEnd;
	}


	public void setDepositRangeEnd(Double depositRangeEnd) {
		this.depositRangeEnd = depositRangeEnd;
	}


	public Integer getMultipleRangeStart() {
		return multipleRangeStart;
	}


	public void setMultipleRangeStart(Integer multipleRangeStart) {
		this.multipleRangeStart = multipleRangeStart;
	}


	public Integer getMultipleRangeEnd() {
		return multipleRangeEnd;
	}


	public void setMultipleRangeEnd(Integer multipleRangeEnd) {
		this.multipleRangeEnd = multipleRangeEnd;
	}


	public Double getYearInterest() {
		return yearInterest;
	}


	public void setYearInterest(Double yearInterest) {
		this.yearInterest = yearInterest;
	}


	public Double getDailyInterest() {
		return dailyInterest;
	}


	public void setDailyInterest(Double dailyInterest) {
		this.dailyInterest = dailyInterest;
	}


	public Double getYearManagementFee() {
		return yearManagementFee;
	}


	public void setYearManagementFee(Double yearManagementFee) {
		this.yearManagementFee = yearManagementFee;
	}


	public Double getDailyManagementFee() {
		return dailyManagementFee;
	}


	public void setDailyManagementFee(Double dailyManagementFee) {
		this.dailyManagementFee = dailyManagementFee;
	}


	public Double getManagementFeeDay() {
		return managementFeeDay;
	}


	public void setManagementFeeDay(Double managementFeeDay) {
		this.managementFeeDay = managementFeeDay;
	}


	public Double getInterestDay() {
		return interestDay;
	}


	public void setInterestDay(Double interestDay) {
		this.interestDay = interestDay;
	}
	
	
	
}