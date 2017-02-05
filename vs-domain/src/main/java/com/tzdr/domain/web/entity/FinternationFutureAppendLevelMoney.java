package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.Dates;

/**
 * 国际期货 A50 、恒指、原油 追加保证金记录列表
 * @zhouchen
 * 2015年11月20日
 */
@Entity
@Table(name = "f_internation_future_append_level_money")
public class FinternationFutureAppendLevelMoney extends BaseCrudEntity {

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
	 * 商品类型【 0.A50   6.国际原油   7.恒生指数  8.国际综合】
	 */
	private int type;

	/**
	 *  追加时间
	 */
	private long appendDate=Dates.getCurrentLongDate();
	
	/**
	 * 处理状态  默认0:未处理      1：已处理
	 */
	private int status=0;
	
	/**
	 * 追加保证金换算美元金额
	 */
	private Double  dollarMoney=0.0;
	
	/**
	 * 追加保证金时固定汇率
	 */
	private Double  parities=0.0;
	
	/**
	 * 操作员
	 * @return
	 */
	private String operator;

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
	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public FinternationFutureAppendLevelMoney() {

	}

	public FinternationFutureAppendLevelMoney(String uid, String programNo, double appendMoney,double parities,double dollarMoney,int type) {
		super();
		this.uid = uid;
		this.programNo = programNo;
		this.appendMoney = appendMoney;
		this.type = type;
		this.parities = parities;
		this.dollarMoney = dollarMoney;
	}

	public Double getDollarMoney() {
		return dollarMoney;
	}

	public void setDollarMoney(Double dollarMoney) {
		this.dollarMoney = dollarMoney;
	}

	public Double getParities() {
		return parities;
	}

	public void setParities(Double parities) {
		this.parities = parities;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}