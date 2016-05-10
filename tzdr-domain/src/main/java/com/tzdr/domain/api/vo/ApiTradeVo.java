package com.tzdr.domain.api.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 用户配资vo
 * @author zhouchen
 * 2015年5月26日 上午9:57:05
 */
public class ApiTradeVo implements Serializable  {
	
	
	private static final long serialVersionUID = -3390139639987375733L;
	/**
	 * 方案编号
	 */
	private String groupId;
	/**
	 * 操盘资金
	 */
	private  Double operateFund;

	/**
	 * 配资金额
	 */
	private Double tradeFund; 
	/**
	 * 借款期限
	 */
	private BigInteger loanDays;
	/**
	 * 实现盈亏
	 */
	private Double accrual;
	/**
	 * 方案状态
	 */
	private int status; 
	/**
	 * 是否欠费
	 */
	private boolean isArrearage;
	
	/**
	 * 0:自动开户(钱江版)，1:手工开户(钱江版)，2：手工开户(涌金版)
	 */ 
	private int feeType=0;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Double getOperateFund() {
		return operateFund;
	}

	public void setOperateFund(Double operateFund) {
		this.operateFund = operateFund;
	}

	public Double getTradeFund() {
		return tradeFund;
	}

	public void setTradeFund(Double tradeFund) {
		this.tradeFund = tradeFund;
	}

	public BigInteger getLoanDays() {
		return loanDays;
	}

	public void setLoanDays(BigInteger loanDays) {
		this.loanDays = loanDays;
	}

	public Double getAccrual() {
		return accrual;
	}

	public void setAccrual(Double accrual) {
		this.accrual = accrual;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isArrearage() {
		return isArrearage;
	}

	public void setArrearage(boolean isArrearage) {
		this.isArrearage = isArrearage;
	}

	public int getFeeType() {
		return feeType;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	};

	
}
