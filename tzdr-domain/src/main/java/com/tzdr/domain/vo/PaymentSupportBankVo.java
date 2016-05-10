package com.tzdr.domain.vo;

import java.io.Serializable;


import com.tzdr.common.utils.SqlColumn;

/**
 * 平台支付支持的银行列表
 * 
 * 2015年11月30日
 */
public class PaymentSupportBankVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 银行名称	
	 */
	@SqlColumn
	private String bankName;
	
	/**
	 * 银行简称
	 * 如：中国银行 -boc
	 */
	@SqlColumn
	private String abbreviation;

	/**
	 * 币币支付 银行代码
	 * 30018 - 中国工商银行
	 */
	@SqlColumn
	private String bbpayCode;
	/**
	 * 币币支付提现 联行号
	 * 中国工商银行-102100099996
	 */
	@SqlColumn
	private String bbpayContactNumber;

	/**
	 * 银行前端显示顺序
	 */
	@SqlColumn
	private Integer weight;

	/**
	 * 是否支持币币支付
	 * 0 不支持
	 * 1 支持
	 */
	@SqlColumn
	private Integer supportBbpay=0;

	/**
	 * 是否支持联动优势
	 * 0 不支持
	 * 1 支持
	 */
	@SqlColumn
	private Integer supportUmpay=0;
	
	/**
	 * 银行图标路径 供页面显示专用
	 */
	@SqlColumn
	private String iconPath;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getBbpayCode() {
		return bbpayCode;
	}

	public void setBbpayCode(String bbpayCode) {
		this.bbpayCode = bbpayCode;
	}

	public String getBbpayContactNumber() {
		return bbpayContactNumber;
	}

	public void setBbpayContactNumber(String bbpayContactNumber) {
		this.bbpayContactNumber = bbpayContactNumber;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getSupportBbpay() {
		return supportBbpay;
	}

	public void setSupportBbpay(Integer supportBbpay) {
		this.supportBbpay = supportBbpay;
	}

	public Integer getSupportUmpay() {
		return supportUmpay;
	}

	public void setSupportUmpay(Integer supportUmpay) {
		this.supportUmpay = supportUmpay;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
	
	
	
}