package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_trade")
public class Trade   extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String type;
	private double money;
	private double apr;
	private double warning;
	private double open;
	private double lever1;
	private double lever2;
	private double lever3;
	private double feeDay;
	private double feeMonth;
	private Integer orders;
	private short status;


	@Column(name="type", nullable=false, length=10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="money", nullable=false, precision=10)
	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Column(name="apr", nullable=false, precision=10)
	public double getApr() {
		return this.apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	@Column(name="warning", nullable=false, precision=10)
	public double getWarning() {
		return this.warning;
	}

	public void setWarning(double warning) {
		this.warning = warning;
	}

	@Column(name="open", nullable=false, precision=10)
	public double getOpen() {
		return this.open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	@Column(name="lever1", nullable=false, precision=10)
	public double getLever1() {
		return this.lever1;
	}

	public void setLever1(double lever1) {
		this.lever1 = lever1;
	}

	@Column(name="lever2", nullable=false, precision=10)
	public double getLever2() {
		return this.lever2;
	}

	public void setLever2(double lever2) {
		this.lever2 = lever2;
	}

	@Column(name="lever3", nullable=false, precision=10)
	public double getLever3() {
		return this.lever3;
	}

	public void setLever3(double lever3) {
		this.lever3 = lever3;
	}

	@Column(name="fee_day", precision=10)
	public double getFeeDay() {
		return this.feeDay;
	}

	public void setFeeDay(double feeDay) {
		this.feeDay = feeDay;
	}

	@Column(name="fee_month", precision=10)
	public double getFeeMonth() {
		return this.feeMonth;
	}

	public void setFeeMonth(double feeMonth) {
		this.feeMonth = feeMonth;
	}

	@Column(name="orders")
	public Integer getOrders() {
		return this.orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	@Column(name="status", nullable=false)
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
}