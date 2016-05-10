package com.tzdr.api.request;


/**
 * 
 * <B>说明: </B>提现审核接口请求
 * @zhouchen
 * 2016年1月20日
 */
public class AuditWithdrawRequest extends BaseRequet {

	
	/**
	 * 提现记录id
	 */
	private String withdrawId;

	public String getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(String withdrawId) {
		this.withdrawId = withdrawId;
	}
	
	
	
}
