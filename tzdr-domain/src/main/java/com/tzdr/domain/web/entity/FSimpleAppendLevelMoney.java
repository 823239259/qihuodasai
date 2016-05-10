package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.Dates;

@Entity
@Table(name = "f_simple_append_level_money")
public class FSimpleAppendLevelMoney extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private String uid;
	
	/**
	 * 方案号TG+ID号
	 */
	private String programNo;

	/**
	 * 追加金额
	 */
	private double appendMoney=0.0;

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

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
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
	
	
	public FSimpleAppendLevelMoney() {

	}

	public FSimpleAppendLevelMoney(String uid, String programNo, double appendMoney) {
		super();
		this.uid = uid;
		this.programNo = programNo;
		this.appendMoney = appendMoney;
	}
}