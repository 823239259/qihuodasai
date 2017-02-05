package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.domain.web.entity.UserTrade;

/**
 * @Description:
 * @ClassName: UserTradeTransfer.java
 * @author Lin Feng
 * @date 2015年3月26日
 */
public class UserTradeTransfer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 769620461154505519L;
	
	private UserTrade userTrade;
	
	private String accountNo;
	
	private String parentCombineId;
	
	private String childrenCombineId;
	
	private String parentEndTime;

	public String getParentEndTime() {
		return parentEndTime;
	}

	public void setParentEndTime(String parentEndTime) {
		this.parentEndTime = parentEndTime;
	}

	public UserTrade getUserTrade() {
		return userTrade;
	}

	public void setUserTrade(UserTrade userTrade) {
		this.userTrade = userTrade;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getParentCombineId() {
		return parentCombineId;
	}

	public void setParentCombineId(String parentCombineId) {
		this.parentCombineId = parentCombineId;
	}

	public String getChildrenCombineId() {
		return childrenCombineId;
	}

	public void setChildrenCombineId(String childrenCombineId) {
		this.childrenCombineId = childrenCombineId;
	}
	
	

}
