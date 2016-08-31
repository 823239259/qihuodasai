package com.tzdr.api.request;


/**
 * 
 * <B>说明: </B> 绑定银行卡请求
 * @zhouchen
 * 2016年1月20日
 */
public class BankRequest extends BaseRequet {

	/**
	 * 银行卡id
	 */
	private String bankId;
	
	/**
	 * 银行名称关键字  icbc/abc ...
	 */
	private String bank;

	/**
	 * 银行卡号
	 */
	private String card;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

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
}
