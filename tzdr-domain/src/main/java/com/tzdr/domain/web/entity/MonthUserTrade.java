package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name="month_user_trade")
public class MonthUserTrade extends BaseEntity {
	/**
	 * 方案id
	 */
	private String tradeId;
	/**
	 * 延长月数
	 */
	private Integer prolongMonth=0;
	
	/**
	 * 方案月数
	 */
	private Integer tradeMonth;
	/**
	 * 是否手动延期 1:到期当天自动延期失败，手动延期成功  2:已逾期 再手动延期成功
	 */
	private Integer isManualDelay=0;
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public Integer getProlongMonth() {
		return prolongMonth;
	}
	public void setProlongMonth(Integer prolongMonth) {
		this.prolongMonth = prolongMonth;
	}
	public Integer getTradeMonth() {
		return tradeMonth;
	}
	public void setTradeMonth(Integer tradeMonth) {
		this.tradeMonth = tradeMonth;
	}
	public Integer getIsManualDelay() {
		return isManualDelay;
	}
	public void setIsManualDelay(Integer isManualDelay) {
		this.isManualDelay = isManualDelay;
	}
	public MonthUserTrade() {
		// TODO Auto-generated constructor stub
	}
	public MonthUserTrade(String tradeId, Integer tradeMonth) {
		super();
		this.tradeId = tradeId;
		this.tradeMonth = tradeMonth;
	}
	
	
	
	
}
