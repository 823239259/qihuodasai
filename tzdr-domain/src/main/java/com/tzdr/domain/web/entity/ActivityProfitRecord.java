package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;

/**
 * 8800 活动收益记录
 * @zhouchen
 * 2015年2月9日
 */
@Entity
@Table(name = "w_activity_profit_record")
public class ActivityProfitRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 账户id
	 */
	private String uid;
	
	
	/**
	 * 账户手机号
	 */
	private String mobile;
	
	
	/**
	 * 配资groupId
	 */
	private String tradeGroupId;
	
	
	/**
	 * 收益
	 */
	private Double profit=0.0;

	/**
	 * 收益率
	 */
	private String profitRate;
	
	/**
	 * 收益时间
	 */
	private Long  profitDate=Dates.getCurrentLongDay();

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	

	public String getTradeGroupId() {
		return tradeGroupId;
	}

	public void setTradeGroupId(String tradeGroupId) {
		this.tradeGroupId = tradeGroupId;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public String getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}

	public Long getProfitDate() {
		return profitDate;
	}

	public void setProfitDate(Long profitDate) {
		this.profitDate = profitDate;
	}
	
    
}