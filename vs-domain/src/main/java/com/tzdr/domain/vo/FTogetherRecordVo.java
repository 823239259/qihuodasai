package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.SqlColumn;

public class FTogetherRecordVo implements Serializable {
	
	
	  /**
     * 方案号
     */
	@SqlColumn
	@AllowExcel(name = "方案号")
    private	String tradeNo;
    /**
     * 方案名称
     */
	@SqlColumn
	@AllowExcel(name = "方案名称")
    private String tradeName;
	
	/**
     * 用户姓名
     */
	@SqlColumn
	@AllowExcel(name = "用户姓名")
    private String userName;
    
    /**
     * 手机
     */
	@SqlColumn
	@AllowExcel(name = "手机")
    private String  mobile;
	
	
	 /**
     * 购买份数
     */
	 @SqlColumn
	@AllowExcel(name = "购买份数")
    private Integer copies;
    
    /**
     * 实际支付金额
     */
	@SqlColumn
	@AllowExcel(name = "实际支付金额")
    private BigDecimal payMoney;
    
    /**
     * 手续费
     */
	@SqlColumn
	@AllowExcel(name = "手续费")
    private BigDecimal poundage;
    
    /**
     * 盈亏点数
     */
	@SqlColumn
    private BigDecimal profitLossPoint;
	
	@AllowExcel(name = "盈亏点数")
	private String profitLossPointStr;
    /**
     * 实现盈亏
     */
	@SqlColumn
    private BigDecimal achieveProfitLoss;
	@AllowExcel(name = "实现盈亏")
	private String achieveProfitLossStr;
	
    /**
     * 预计结算金额
     */
	@SqlColumn
	@AllowExcel(name = "预计结算金额")
    private BigDecimal expectSettlementMoney;

    /**
     * 实际结算金额
     */
	@SqlColumn
	@AllowExcel(name = "实际结算金额")
    private BigDecimal actualSettlementMoney;
    
    /**
     * 结算时间
     */
	@SqlColumn
	@AllowExcel(name = "结算时间")
    private Long settlementTime;
    
  
    /**
     * 品种
     */
	@SqlColumn
    private Integer species;


	public String getSettlementTime() {
		if (null != this.settlementTime){
			return Dates.format(new Date(settlementTime*1000), Dates.CHINE_DATE_FORMAT_TO_MINUTE);
		}
		return "";

	}
	public void setSettlementTime(Long settlementTime) {
		this.settlementTime = settlementTime;
	}
	public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public BigDecimal getProfitLossPoint() {
		return profitLossPoint;
	}

	public void setProfitLossPoint(BigDecimal profitLossPoint) {
		this.profitLossPoint = profitLossPoint;
	}
	
	public String getProfitLossPointStr() {
		if(this.profitLossPoint != null && this.profitLossPoint.intValue()>0){
			return "+"+this.profitLossPoint;
		}
		if(null == this.profitLossPoint){
			return "";
		}
		return this.profitLossPoint+"";
	}
	
	public void setProfitLossPointStr(String profitLossPointStr) {
		this.profitLossPointStr = profitLossPointStr;
	}
	
	
	public BigDecimal getAchieveProfitLoss() {
		
		return achieveProfitLoss;
	}

	public void setAchieveProfitLoss(BigDecimal achieveProfitLoss) {
		this.achieveProfitLoss = achieveProfitLoss;
	}
	
	public String getAchieveProfitLossStr() {
		if(null != this.achieveProfitLoss && this.achieveProfitLoss.intValue()>0){
			return "+"+this.achieveProfitLoss;
		}
		if(null == this.achieveProfitLoss){
			return "";
		}
		return this.achieveProfitLoss+"";
	}
	public void setAchieveProfitLossStr(String achieveProfitLossStr) {
		this.achieveProfitLossStr = achieveProfitLossStr;
	}
	public BigDecimal getExpectSettlementMoney() {
		return expectSettlementMoney;
	}

	public void setExpectSettlementMoney(BigDecimal expectSettlementMoney) {
		this.expectSettlementMoney = expectSettlementMoney;
	}

	public BigDecimal getActualSettlementMoney() {
		return actualSettlementMoney;
	}

	public void setActualSettlementMoney(BigDecimal actualSettlementMoney) {
		this.actualSettlementMoney = actualSettlementMoney;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public Integer getSpecies() {
		return species;
	}

	public void setSpecies(Integer species) {
		this.species = species;
	}
}
