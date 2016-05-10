package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;


public class ArrearsEndDetail implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * 欠费时间
	 */
	@SqlOrder
	@SqlColumn(name="time")
	private String time;
	
	/**
	 *  欠费金额
	 */
	@SqlColumn(name="money")
	private double  money;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
	
	
	
	
}
