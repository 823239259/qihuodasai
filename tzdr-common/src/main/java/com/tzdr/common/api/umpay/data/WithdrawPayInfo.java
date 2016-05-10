package com.tzdr.common.api.umpay.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @Description:
 * @ClassName: WithdrawPayInfo.java
 * @author Lin Feng
 * @date 2014年12月29日
 */
public class WithdrawPayInfo {
	
	private String  orderId="";
	
	private String  merDate="";
	
	private String  amount="";
	
	private String  recvAccount="";
	
	private String  recvUserName="";
	
	private String  recvGateId="";
	
	private String  purpose="";
	
	private String  bankBrhname="";

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

	public String getRecvAccount() {
		return recvAccount;
	}

	public void setRecvAccount(String recvAccount) {
		this.recvAccount = recvAccount;
	}

	public String getRecvUserName() {
		return recvUserName;
	}

	public void setRecvUserName(String recvUserName) {
		this.recvUserName = recvUserName;
	}

	public String getRecvGateId() {
		return recvGateId;
	}

	public void setRecvGateId(String recvGateId) {
		this.recvGateId = recvGateId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getBankBrhname() {
		return bankBrhname;
	}

	public void setBankBrhname(String bankBrhname) {
		this.bankBrhname = bankBrhname;
	}
	
	
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


}
