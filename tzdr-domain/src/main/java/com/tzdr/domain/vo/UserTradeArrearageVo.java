package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
* @Description: 用户方案欠费类
* @ClassName: UserTradeArrearageVo
* @author wangpinqun
* @date 2015年05月21日 下午3:39:20
 */
public class UserTradeArrearageVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 方案编号
	 */
	private String tradeId;
	
	/**
	 * 欠费钱
	 */
	private Double money;
	
	/**
	 * 欠费天数
	 */
	private BigInteger days;
	
	/** 
	 * 最小时间
	 */
	private BigInteger minTime;
	
	/**
	 * 最大时间
	 */
	private BigInteger maxTime;
	
	/**
	 * 方案状态： 0->验资中/开户中    1->操盘中    2->已完结 
	 */
	private Short status;

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public BigInteger getDays() {
		return days;
	}

	public void setDays(BigInteger days) {
		this.days = days;
	}

	public BigInteger getMinTime() {
		return minTime;
	}

	public void setMinTime(BigInteger minTime) {
		this.minTime = minTime;
	}

	public BigInteger getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(BigInteger maxTime) {
		this.maxTime = maxTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
}
