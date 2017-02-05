package com.tzdr.domain.api.vo;

import java.math.BigInteger;

/**
 * 方案详情Vo
 * @author zhouchen
 * 2015年5月27日 下午1:07:37
 */
public class TradeDetail {
	  
	  /**
	   * 方案编号
	   */
	  private String groupId;
	  /**
	   * 总操盘资金
	   */
      private Double totalFund;
      /**
       * 配资金额
       */
      private Double tradeFund;
      
      /**
       * 保证金
       */
      private Double cashFund;
       
      /**
       * 管理费
       */
      private Double mangerFee;
      
      /**
       * 借款期限
       */
      private BigInteger  loanDays;
      /**
       * 利息
       */
      private Double interest;
      
      /**
       * 实现盈亏
       */
      private Double accrual;
      
      
      /**
       * 开始时间
       */
       private String  startTime;
       
       /**
        * 结束时间
        */
       private String endTime;
       
       /**
        * 方案状态
        */
       private int status;


       /**
        * 交易账户
        */
       private String account;
       
       /**
        * 交易密码
        */
       private String password;
       
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

	public Double getTotalFund() {
		return totalFund;
	}

	public void setTotalFund(Double totalFund) {
		this.totalFund = totalFund;
	}

	public Double getTradeFund() {
		return tradeFund;
	}

	public void setTradeFund(Double tradeFund) {
		this.tradeFund = tradeFund;
	}

	public Double getCashFund() {
		return cashFund;
	}

	public void setCashFund(Double cashFund) {
		this.cashFund = cashFund;
	}

	public Double getMangerFee() {
		return mangerFee;
	}

	public void setMangerFee(Double mangerFee) {
		this.mangerFee = mangerFee;
	}

	public BigInteger getLoanDays() {
		return loanDays;
	}

	public void setLoanDays(BigInteger loanDays) {
		this.loanDays = loanDays;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getAccrual() {
		return accrual;
	}

	public void setAccrual(Double accrual) {
		this.accrual = accrual;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFeeType() {
		return feeType;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}
   		
   		
}
