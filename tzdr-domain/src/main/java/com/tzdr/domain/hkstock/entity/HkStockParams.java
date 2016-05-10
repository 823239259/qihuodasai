package com.tzdr.domain.hkstock.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;


/**
 * 港股配资参数配置
 * @author zhouchen
 * 2015年10月16日 上午11:01:06
 */
@Entity
@Table(name = "hk_stock_params")
public class HkStockParams extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	 * 港股配资倍数
	 * 格式：1;2;3
	 */
	private String leverConfig;
	
	
	/**
	 * 警戒线风控系数 
	 * 格式：1.10;1.20;1.30
	 */
	private String warningCoefficient;
	
	
	
	/**
	 * 平仓线风控系数 格式：1.07;1.08;1.09
	 */
	private String openCoefficient;

	/**
	 * 保证金计算系数
	 */
	private Double bailCoefficient=0.0;
	
	
	/**
	 * 最小超盘天数
	 */
	private Integer minHoldDays=0;
	
	/**
	 * 最大超盘天数
	 */
	private Integer maxHoldDays=0;

	/**
	 * 推荐操盘天数
	 * 格式：5;8;10;20
	 */
	private String recommendHoldDays;
			
	/**
	 * 港股利息系数
	 */
	private Double interestCoefficient=0.0;
	
	
	/**
	 * 港股管理费系数
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


	public String getWarningCoefficient() {
		return warningCoefficient;
	}


	public void setWarningCoefficient(String warningCoefficient) {
		this.warningCoefficient = warningCoefficient;
	}


	public String getOpenCoefficient() {
		return openCoefficient;
	}


	public void setOpenCoefficient(String openCoefficient) {
		this.openCoefficient = openCoefficient;
	}


	public Double getBailCoefficient() {
		return bailCoefficient;
	}


	public void setBailCoefficient(Double bailCoefficient) {
		this.bailCoefficient = bailCoefficient;
	}


	public Integer getMinHoldDays() {
		return minHoldDays;
	}


	public void setMinHoldDays(Integer minHoldDays) {
		this.minHoldDays = minHoldDays;
	}


	public Integer getMaxHoldDays() {
		return maxHoldDays;
	}


	public void setMaxHoldDays(Integer maxHoldDays) {
		this.maxHoldDays = maxHoldDays;
	}


	public String getRecommendHoldDays() {
		return recommendHoldDays;
	}


	public void setRecommendHoldDays(String recommendHoldDays) {
		this.recommendHoldDays = recommendHoldDays;
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