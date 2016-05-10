package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.Dates;

@Entity
@Table(name = "w_append_level_money_fail")
public class AppendLevelMoneyFail extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private String uid;
	
	/**
	 * 方案组编号
	 */
	private String groupId;

	/**
	 * 追加金额
	 */
	private double appendMoney=0.0;
	/**
	 * 恒生子账户id
	 */
	private String accountId;

	/**
	 *  追加时间
	 */
	private long appendDate=Dates.getCurrentLongDate();
	
	/**
	 * 处理状态  默认0:未处理      1：已处理
	 */
	private int status=0;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public double getAppendMoney() {
		return appendMoney;
	}

	public void setAppendMoney(double appendMoney) {
		this.appendMoney = appendMoney;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getAppendDate() {
		return appendDate;
	}

	public void setAppendDate(long appendDate) {
		this.appendDate = appendDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public AppendLevelMoneyFail() {

	}

	public AppendLevelMoneyFail(String uid, String groupId, double appendMoney,
			String accountId) {
		super();
		this.uid = uid;
		this.groupId = groupId;
		this.appendMoney = appendMoney;
		this.accountId = accountId;
	}
	
	
	
	
}