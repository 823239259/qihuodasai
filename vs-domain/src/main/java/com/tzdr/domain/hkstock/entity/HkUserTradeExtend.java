package com.tzdr.domain.hkstock.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;


/**
 * 港股配资方案扩展表
 * @author zhouchen
 * 2015年10月16日 上午11:01:06
 */
@Entity
@Table(name = "hk_user_trade_extend")
public class HkUserTradeExtend extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 开户审核状态------ 0：开户待审核    1：开户通过（变为操盘中）   2：审核不通过 （开户失败） 
	 */
	private int auditStatus=0;
	
	
	 /**
	  * 终结审核状态----- 0：终结待审核    1：方案终结审核通过          2：方案终结审核不通过
	  */
	 private int auditEndStatus=-1;
	 
	 
	/**
     * 终结提交时间
     */
    private  Long  endSubTime;
    
    /**
     * 终结审核人Id
     */
    private  Long endAuditUserId;
    /**
     * 终结审核人姓名
     */
    private  String endAuditUserName;
    
    /**
     * 终结审核时间
     */
    private  Long  endAuditTime;
    
    /**
     * 终结审核时间【审1时间】
     */
    private  Long  endAuditFirsteTime;
	    
	/**
	 * 原有配资id
	 */
	private String  tradeId;
	
	/**
	 * 结算审核金额
	 */
	private Double finishedMoney;
	
	/**
	 * 不通过原因
	 */
	private String  failCause;
	

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getFailCause() {
		return failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}

	public int getAuditEndStatus() {
		return auditEndStatus;
	}

	public void setAuditEndStatus(int auditEndStatus) {
		this.auditEndStatus = auditEndStatus;
	}

	public Long getEndSubTime() {
		return endSubTime;
	}

	public void setEndSubTime(Long endSubTime) {
		this.endSubTime = endSubTime;
	}

	public Long getEndAuditUserId() {
		return endAuditUserId;
	}

	public void setEndAuditUserId(Long endAuditUserId) {
		this.endAuditUserId = endAuditUserId;
	}

	public String getEndAuditUserName() {
		return endAuditUserName;
	}

	public void setEndAuditUserName(String endAuditUserName) {
		this.endAuditUserName = endAuditUserName;
	}

	public Long getEndAuditTime() {
		return endAuditTime;
	}

	public void setEndAuditTime(Long endAuditTime) {
		this.endAuditTime = endAuditTime;
	}

	public HkUserTradeExtend() {

	}

	public Long getEndAuditFirsteTime() {
		return endAuditFirsteTime;
	}

	public void setEndAuditFirsteTime(Long endAuditFirsteTime) {
		this.endAuditFirsteTime = endAuditFirsteTime;
	}
	
	
	public HkUserTradeExtend(String tradeId) {
		super();
		this.tradeId = tradeId;
	}
}