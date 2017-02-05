package com.tzdr.web.utils;

import java.util.Date;

public class DrawMoneyFailBean {

	private String id;
	
	private int drawFailCount = 0;
	
	private String userName;
	
	private Date validDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public int getDrawFailCount() {
		return drawFailCount;
	}

	public void setDrawFailCount(int drawFailCount) {
		this.drawFailCount = drawFailCount;
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
}
