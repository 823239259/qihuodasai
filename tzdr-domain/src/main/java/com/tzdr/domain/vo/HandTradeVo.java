package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 手工开户 Vo
 * @author zhouchen
 * 2015年4月27日 上午11:23:50
 */
public class HandTradeVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * 手工开户 id
	 */
	private String id;
	
	/**
	 * 配资 id
	 */
	private String tradeId;
	/**
	 * 手机号
	 */
	private String  mobile;
	
	
	/**
	 * 客户姓名
	 */
	private String  tname;
	
	
	/**
	 * 配资组合号
	 */
	private String groupId;
	
	
	/**
	 * 保证金
	 */
	private Double leverMoney;
	
	
	/**
	 * 杠杆
	 */
	private Integer lever;
	
	/**
	 * 使用期限
	 */
	private BigInteger  naturalDays;
	
	
	/**
	 * 交易账号
	 */
	private String account;
	
	

	/**
	 * 恒生账户名
	 */
	private String accountName;
	
	/**
	 * 交易账户密码
	 */
	private String password;
	
	
	/**
	 * 单元序号
	 */
	private String assetId;
	
	
	/**
	 * 母账户id
	 */
	private String  parentAccountId;
	
	
	/**
	 * 创建时间
	 */
	private BigInteger  createTime;
	
	/**
	 * 创建时间 显示
	 */
	private String  createTimeValue;
	
	/**
	 * 创建人
	 */
	private String  createUser;

	
	/**
	 * 审核时间
	 */
	private BigInteger  auditTime;
	
	/**
	 * 审核时间 显示
	 */
	private String  auditTimeValue;
	
	/**
	 * 审核人
	 */
	private String  auditUser;
	
	/**
	 * 审核状态 0:未审核 1：通过 2：未通过
	 */
	private int  auditStatus;
	


	/**
	 * 审核显示状态 0:未审核 1：通过 2：未通过
	 */
	private String  auditStatusValue;
	
	/**
	 * 保险单号
	 */
	private String insuranceNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getLeverMoney() {
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public BigInteger getNaturalDays() {
		return naturalDays;
	}

	public void setNaturalDays(BigInteger naturalDays) {
		this.naturalDays = naturalDays;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getParentAccountId() {
		return parentAccountId;
	}

	public void setParentAccountId(String parentAccountId) {
		this.parentAccountId = parentAccountId;
	}

	public BigInteger getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BigInteger createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeValue() {
		if (ObjectUtil.equals(null,this.createTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.createTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setCreateTimeValue(String createTimeValue) {
		this.createTimeValue = createTimeValue;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public BigInteger getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(BigInteger auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditTimeValue() {
		if (ObjectUtil.equals(null,this.auditTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.auditTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAuditTimeValue(String auditTimeValue) {
		this.auditTimeValue = auditTimeValue;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditStatusValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.AUDIT_STATUS,String.valueOf(this.auditStatus));
	}

	public void setAuditStatusValue(String auditStatusValue) {
		this.auditStatusValue = auditStatusValue;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}
}
