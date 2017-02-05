package com.tzdr.business.pay.gopay.model;

import com.tzdr.business.pay.gopay.GoTakeConfig;

/**
 * 交易数据对象
 * @author HEDAOQING
 * 2016.07.27
 *
 */
public class GoTakeRequestModel extends GoTakeConfig{
	/**
	 * 订单号 
	 */
	private String merOrderNum;
	/**
	 * 企业后台 URL 
	 */
	private String merURL;
	/**
	 * 交易金额 
	 */
	private String tranAmt;
	/**
	 * 收款人银行 开户名
	 */
	private String recvBankAcctName;
	/**
	 * 收款方银行 帐号 
	 */
	private String recvBankAcctNum;
	/**
	 * 收款方银行 所在省份 
	 */
	private String recvBankProvince;
	/**
	 * 收款方银行 所在城市
	 */
	private String recvBankCity;
	/**
	 * 收款方银行 名称 
	 */
	private String recvBankName;
	/**
	 * 收款方银行 网点名称
	 */
	private String recvBankBranchName;
	/**
	 * 交易时间
	 */
	private String tranDateTime;
	/**
	 * 交易备注
	 */
	private String description;
	/**
	 * 密文串
	 */
	private String signValue;
	
	public String getSignValue() {
		return signValue;
	}
	public void setSignValue(String signValue) {
		this.signValue = signValue;
	}
	public String getMerOrderNum() {
		return merOrderNum;
	}
	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}
	public String getMerURL() {
		return merURL;
	}
	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getRecvBankAcctName() {
		return recvBankAcctName;
	}
	public void setRecvBankAcctName(String recvBankAcctName) {
		this.recvBankAcctName = recvBankAcctName;
	}
	public String getRecvBankAcctNum() {
		return recvBankAcctNum;
	}
	public void setRecvBankAcctNum(String recvBankAcctNum) {
		this.recvBankAcctNum = recvBankAcctNum;
	}
	public String getRecvBankProvince() {
		return recvBankProvince;
	}
	public void setRecvBankProvince(String recvBankProvince) {
		this.recvBankProvince = recvBankProvince;
	}
	public String getRecvBankCity() {
		return recvBankCity;
	}
	public void setRecvBankCity(String recvBankCity) {
		this.recvBankCity = recvBankCity;
	}
	public String getRecvBankName() {
		return recvBankName;
	}
	public void setRecvBankName(String recvBankName) {
		this.recvBankName = recvBankName;
	}
	public String getRecvBankBranchName() {
		return recvBankBranchName;
	}
	public void setRecvBankBranchName(String recvBankBranchName) {
		this.recvBankBranchName = recvBankBranchName;
	}
	public String getTranDateTime() {
		return tranDateTime;
	}
	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public GoTakeRequestModel(String merOrderNum, String merURL, String tranAmt, String recvBankAcctName,
			String recvBankAcctNum, String recvBankProvince, String recvBankCity, String recvBankName,
			String recvBankBranchName, String tranDateTime, String description, String signValue) {
		super();
		this.merOrderNum = merOrderNum;
		this.merURL = merURL;
		this.tranAmt = tranAmt;
		this.recvBankAcctName = recvBankAcctName;
		this.recvBankAcctNum = recvBankAcctNum;
		this.recvBankProvince = recvBankProvince;
		this.recvBankCity = recvBankCity;
		this.recvBankName = recvBankName;
		this.recvBankBranchName = recvBankBranchName;
		this.tranDateTime = tranDateTime;
		this.description = description;
		this.signValue = signValue;
	}
	public GoTakeRequestModel() {
		super();
	}
}
