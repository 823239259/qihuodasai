package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;
import java.math.BigInteger;


/**
 * 港股欠费方案的欠费明细VO
 * @Description: 
 * @author liuhaichuan
 * @date 2015年10月20日
 *
 */
public class HkArrearsEndDetailVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 欠费时间
	 */
	private BigInteger addTime;
	
	/**
	 *  欠费金额
	 */
	private Double  money;

	/**
	 * 方案编号
	 */
	private String groupId;


	public BigInteger getAddTime() {
		return addTime;
	}

	public void setAddTime(BigInteger addTime) {
		this.addTime = addTime;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	
}
