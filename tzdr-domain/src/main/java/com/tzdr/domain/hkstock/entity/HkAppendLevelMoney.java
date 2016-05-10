package com.tzdr.domain.hkstock.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.Dates;

@Entity
@Table(name = "hk_append_level_money")
public class HkAppendLevelMoney extends BaseCrudEntity {

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
	private Double appendMoney=0.0;

	/**
	 * 追加金额 对应的港币金额
	 */
	private Double hkDollarMoney=0.0;

	
	/**
	 * 追加时对应的汇率
	 */
	private Double exchangeRate=0.0;


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
	
	
	public HkAppendLevelMoney() {

	}

	public HkAppendLevelMoney(String uid, String groupId, double appendMoney){
		super();
		this.uid = uid;
		this.groupId = groupId;
		this.appendMoney = appendMoney;
	}

	public Double getHkDollarMoney() {
		return hkDollarMoney;
	}

	public void setHkDollarMoney(Double hkDollarMoney) {
		this.hkDollarMoney = hkDollarMoney;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setAppendMoney(Double appendMoney) {
		this.appendMoney = appendMoney;
	}
	
	
	
	
}