package com.tzdr.domain.pgb.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 平台支付支持的银行列表
 * @zhouchen
 * 2015年11月30日
 */
@Entity
@Table(name = "pgb_payment_support_bank")
public class PGBPaymentSupportBank extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 银行名称	
	 */
	private String bankName;
	
	/**
	 * 银行简称
	 * 如：中国银行 -boc
	 */
	private String abbreviation;

	/**
	 * 币币支付 银行代码
	 * 30018 - 中国工商银行
	 */
	private String bbpayCode;
	
	/**
	 * 易支付支付 银行代码
	 * 3 -招商银行  4-建设银行
	 */
	private String payeaseCode;
	
	/**
	 * 币币支付提现 联行号
	 * 中国工商银行-102100099996
	 */
	private String bbpayContactNumber;

	/**
	 * 银行前端显示顺序
	 */
	private Integer weight;

	/**
	 * 是否支持币币支付
	 * 0 不支持
	 * 1 支持
	 */
	private Integer supportBbpay=0;

	/**
	 * 是否支持联动优势
	 * 0 不支持
	 * 1 支持
	 */
	private Integer supportUmpay=0;
	
	/**
	 * 是否支持易支付
	 * 0 不支持
	 * 1 支持
	 */
	private Integer supportPayEase=0;
	
	/**
	 * 是否支持币币支付提现
	 * 0 不支持
	 * 1 支持
	 */
	private Integer supportBbdraw=1;

	/**
	 * 是否支持联动优势提现
	 * 0 不支持
	 * 1 支持
	 */
	private Integer supportUmdraw=1;
	
	/**
	 * 是否支持易支付提现
	 * 0 不支持
	 * 1 支持
	 */
	private Integer supportEasedraw=1;
	
	
	/**
	 * 银行图标路径 供页面显示专用
	 */
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

	public Integer getSupportBbdraw() {
		return supportBbdraw;
	}

	public void setSupportBbdraw(Integer supportBbdraw) {
		this.supportBbdraw = supportBbdraw;
	}

	public Integer getSupportUmdraw() {
		return supportUmdraw;
	}

	public void setSupportUmdraw(Integer supportUmdraw) {
		this.supportUmdraw = supportUmdraw;
	}

	public String getPayeaseCode() {
		return payeaseCode;
	}

	public void setPayeaseCode(String payeaseCode) {
		this.payeaseCode = payeaseCode;
	}

	public Integer getSupportPayEase() {
		return supportPayEase;
	}

	public void setSupportPayEase(Integer supportPayEase) {
		this.supportPayEase = supportPayEase;
	}

	public Integer getSupportEasedraw() {
		return supportEasedraw;
	}

	public void setSupportEasedraw(Integer supportEasedraw) {
		this.supportEasedraw = supportEasedraw;
	}
	
	
	
	
}