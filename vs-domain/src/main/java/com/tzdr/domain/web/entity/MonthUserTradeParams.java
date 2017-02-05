package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="month_user_trade_params")
public class MonthUserTradeParams extends BaseEntity {
	
	/**
	 * 最小总操盘资金
	 */
	private Double minTotalMoney=0.0;
	
	/**
	 * 最大总操盘资金
	 */
	private Double maxTotalMoney=0.0;
	
	/**
	 * 推荐操盘金额 
	 * 格式：50000;150000;300000;500000
	 */
	private String recommendHoldMoney;
	
	
	/**
	 * 月月配配资倍数
	 * 格式：1;2;3
	 */
	private String leverConfig;
	
	/**
	 * 推荐操盘月数
	 * 格式：5;8;10;20
	 */
	private String recommendHoldMonths;
			
	/**
	 * 月月配利息系数
	 */
	private Double interestCoefficient=0.0;
	
	
	/**
	 * 月月配管理费系数
	 */
	private Double manageFeeCoefficient=0.0;


	public Double getMinTotalMoney() {
		return minTotalMoney;
	}


	public void setMinTotalMoney(Double minTotalMoney) {
		this.minTotalMoney = minTotalMoney;
	}


	public Double getMaxTotalMoney() {
		return maxTotalMoney;
	}


	public void setMaxTotalMoney(Double maxTotalMoney) {
		this.maxTotalMoney = maxTotalMoney;
	}


	public String getRecommendHoldMoney() {
		return recommendHoldMoney;
	}


	public void setRecommendHoldMoney(String recommendHoldMoney) {
		this.recommendHoldMoney = recommendHoldMoney;
	}


	public String getLeverConfig() {
		return leverConfig;
	}


	public void setLeverConfig(String leverConfig) {
		this.leverConfig = leverConfig;
	}


	public String getRecommendHoldMonths() {
		return recommendHoldMonths;
	}


	public void setRecommendHoldMonths(String recommendHoldMonths) {
		this.recommendHoldMonths = recommendHoldMonths;
	}


	public Double getInterestCoefficient() {
		return interestCoefficient;
	}


	public void setInterestCoefficient(Double interestCoefficient) {
		this.interestCoefficient = interestCoefficient;
	}


	public Double getManageFeeCoefficient() {
		return manageFeeCoefficient;
	}


	public void setManageFeeCoefficient(Double manageFeeCoefficient) {
		this.manageFeeCoefficient = manageFeeCoefficient;
	}
	
	
}
