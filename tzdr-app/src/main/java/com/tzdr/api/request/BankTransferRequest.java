package com.tzdr.api.request;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;


/**
 * <B>说明: </B>银行转账 请求
 * @zhouchen
 * 2015年12月15日
 */


public class BankTransferRequest extends BaseRequet {

	/**
	 * 银行简称icbc/abc ..
	 */
	private String abbreviation;
	
	/**
	 * 流水号
	 */
	private String serialnum;
	
	/**
	 *  充值金额
	 */
	private Double money;

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * 校验参数是否有效 
	 * @return
	 */
	public boolean isInvalid(){
		if (StringUtil.isBlank(this.abbreviation)
				|| StringUtil.isBlank(this.serialnum)
				|| ObjectUtil.equals(null, this.money)){
			return true;
		}
		
		return false;
	}
	
}
