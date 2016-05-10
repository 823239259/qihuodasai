package com.tzdr.api.request;

/**
 * 
 * <B>说明: </B>提现接口请求
 * @zhouchen
 * 2016年1月20日 
 */
public class WithDrawRequest extends BaseRequet {

	/**
	 * 银行名称关键字
	 */
	private String bank;
	
	/**
	 * 银行卡号
	 */
	private String card;
	
	/**
	 * 提现金额
	 */
	private Double money;
	
	/**
	 * 提现密码
	 */
	private String withdrawPwd;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getWithdrawPwd() {
		return withdrawPwd;
	}

	public void setWithdrawPwd(String withdrawPwd) {
		this.withdrawPwd = withdrawPwd;
	}
	
}
