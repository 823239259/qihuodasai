package com.tzdr.common.api.umpay.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class PayOrderInfo {
	
	/**
	 * 商户唯一订单号
	 */
	private String orderId="";
	
	/**
	 * 商户订单日期
	 */
	private String merDate="";
	/**
	 * 付款金额 分为单位
	 */
	private String amount="";
	
	/**
	 * 支付银行
	 */
	private String gateId="";

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerDate() {
		return merDate;
	}

	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
