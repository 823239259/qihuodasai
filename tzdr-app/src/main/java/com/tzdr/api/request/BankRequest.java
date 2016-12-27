package com.tzdr.api.request;


/**
 * 
 * <B>说明: </B> 绑定银行卡请求
 * @zhouchen
 * 2016年1月20日
 */
/**
 * @author gc
 *
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
	/**
	 * 确认银行卡号码
	 */
	private String agincard;
	
	/**
	 * 省
	 */
	private String prov;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 具体地址
	 */
	private String address;

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

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAgincard() {
		return agincard;
	}

	public void setAgincard(String agincard) {
		this.agincard = agincard;
	}
	
}
