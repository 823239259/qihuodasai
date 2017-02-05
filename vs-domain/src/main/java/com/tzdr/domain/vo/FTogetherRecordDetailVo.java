package com.tzdr.domain.vo;

import java.math.BigInteger;

public class FTogetherRecordDetailVo {
	
	private BigInteger addTime;
	
	
	private String mobile;
	
	private Integer isBack;
	
	private String tradeId;
	
	private Integer direction;
	
	public BigInteger getAddTime() {
		return addTime;
	}

	public void setAddTime(BigInteger addTime) {
		this.addTime = addTime;
	}



	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	
	
}
