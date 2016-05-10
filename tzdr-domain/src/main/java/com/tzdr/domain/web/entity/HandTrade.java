package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 手工配资关联表
 * upadteUser 作为审核人
 * updateTime 作为审核时间
 * @author zhouchen
 */
@Entity
@Table(name = "w_hand_trade")
public class HandTrade extends BaseCrudEntity {

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
	 * 划款状态 0：失败  1：成功
	 */
	private int transferStatus=0;
	
	/**
	 * 原有配资id
	 */
	private String  tradeId;
	
	/**
	 * 结算审核金额 TODO 加入字段 liuqing
	 */
	private Double finishedMoney;
	
	/**
	 * 不通过原因
	 */
	private String  failCause;
	
	
	/**
	 * 类型： 0：钱江版   1：涌金版  2：同花顺手动
	 */
	private int  type=0;
	

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

	public int getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(int transferStatus) {
		this.transferStatus = transferStatus;
	}

	public String getFailCause() {
		return failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public HandTrade() {
		// TODO Auto-generated constructor stub
	}

	public HandTrade(String tradeId, int type) {
		super();
		this.tradeId = tradeId;
		this.type = type;
	}
	
	
	
}