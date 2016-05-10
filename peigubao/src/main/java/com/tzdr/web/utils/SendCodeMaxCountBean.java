package com.tzdr.web.utils;

import java.util.Date;

public class SendCodeMaxCountBean {

	private int sendCodeCount = 0;
	
	private String userName;
	
	private Date validDate;
	
	private Date endDate;
	
	private String ip;

	public int getSendCodeCount() {
		return sendCodeCount;
	}

	public void setSendCodeCount(int sendCodeCount) {
		this.sendCodeCount = sendCodeCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
