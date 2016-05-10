package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;

/**
 *  cms 操作方案 记录   包括   限制买入  、限制卖出、  取消限制、平仓、终结   
 * @zhouchen
 * 2015年2月10日
 */
@Entity
@Table(name="w_scheme_lifecycle_record")
public class SchemeLifeCycleRecord  extends BaseEntity {

	/**
	 * 定时任务操作 sysUserId
	 */
	public static final String SYSTEM_OPERATOR="system";
	/**
	 * 
	 */
	private static final long serialVersionUID = 4549801714873047443L;

	/**
	 * 配资方案id
	 */
	private String tradeId;
	
	/**
	 * 用户id
	 */
	private String userId;
	
	
	/**
	 * 方案groupId
	 */
	private String groupId;
	
	/**
	 * CMS 平台操作者 如果是定时任务跑的任务，sysUserId=system
	 */
	private String  sysUserId;
	
	
	/**
	 *操作类型  0：取消限制  1：限制买入 2：限制卖出  3: 限制买入卖出  4：平仓  5：终结方案
	 */
	private int operationType=0;
	
	/**
	 * 操作时间
	 */
	private Long operationTime=Dates.getCurrentLongDate();

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}

	public Long getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Long operationTime) {
		this.operationTime = operationTime;
	}
	
	public SchemeLifeCycleRecord() {
		// TODO Auto-generated constructor stub
	}

	public SchemeLifeCycleRecord(UserTrade userTrade,Long sysUserId, int operationType) {
		super();
		this.tradeId = userTrade.getId();
		this.userId = userTrade.getWuser().getId();
		this.groupId = userTrade.getGroupId();
		this.sysUserId = String.valueOf(sysUserId);
		this.operationType = operationType;
	}
	
	
	public SchemeLifeCycleRecord(UserTrade userTrade,String sysUserId, int operationType) {
		super();
		this.tradeId = userTrade.getId();
		this.userId = userTrade.getWuser().getId();
		this.groupId = userTrade.getGroupId();
		this.sysUserId = sysUserId;
		this.operationType = operationType;
	}
}