package com.tzdr.common.api.hundsun.data;

public class EntrustResult {
	
	private long entrustNo;
	
	private long batchNo;
	
	private boolean success;

	public long getEntrustNo() {
		return entrustNo;
	}

	public void setEntrustNo(long entrustNo) {
		this.entrustNo = entrustNo;
	}

	public long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(long batchNo) {
		this.batchNo = batchNo;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
