package com.tzdr.common.api.hundsun.data;

/**
 * @Description:
 * @ClassName: OperatorInfo.java
 * @author Lin Feng
 * @date 2015年1月3日
 */
public class OperatorInfo {
	
	private long operatorNo; 
	
	private String operatorName; 
	
	private String operatorStatus; 
	
	private long expireDays; 
	
	private String forceChgPassword; 
	
	private String stockOpright; 
	
	private String futureOpright;

	public long getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(long operatorNo) {
		this.operatorNo = operatorNo;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorStatus() {
		return operatorStatus;
	}

	public void setOperatorStatus(String operatorStatus) {
		this.operatorStatus = operatorStatus;
	}

	public long getExpireDays() {
		return expireDays;
	}

	public void setExpireDays(long expireDays) {
		this.expireDays = expireDays;
	}

	public String getForceChgPassword() {
		return forceChgPassword;
	}

	public void setForceChgPassword(String forceChgPassword) {
		this.forceChgPassword = forceChgPassword;
	}

	public String getStockOpright() {
		return stockOpright;
	}

	public void setStockOpright(String stockOpright) {
		this.stockOpright = stockOpright;
	}

	public String getFutureOpright() {
		return futureOpright;
	}

	public void setFutureOpright(String futureOpright) {
		this.futureOpright = futureOpright;
	} 

}
