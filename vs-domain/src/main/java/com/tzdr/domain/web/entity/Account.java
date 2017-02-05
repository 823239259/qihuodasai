package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

@Entity
@Table(name = "w_account")
public class Account extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 恒生账户名
	 */
	private String accountName;
	
	/**
	 * 账户编号
	 */
	private String account;

	/**
	 * 账户密码
	 */
	private String password;
	/**
	 * 恒长帐户所属单元信息
	 */
	private String assetId;

	/**
	 * 恒长帐户所属组合编号
	 */
	private String combineId;

	/**
	 * 所属用户组
	 */
	private String groupid;

	/**
	 * 使用时间
	 */
	private Long usdtime;

	
	/**
	 * 子账户 终结时间  即 方案终结之后，第二天资金划转回母账户时间
	 */
	private Long endtime;
	
	
	/**
	 * 使用状态 0：未使用，1：已使用，2：已终结，3：已注销
	 */
	private short status=0;
	
	@Transient
	private  String statusValue;
	/**
	 * 母账户
	 */
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "parent_accound_id")
	private ParentAccount parentAccount;
	
	/**
	 * 保险单号
	 */
	private String insuranceNo;
	
	@Column(name = "account", nullable = false)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "asset_id", length = 32)
	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Column(name = "combine_id", length = 32)
	public String getCombineId() {
		return this.combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	@Column(name = "groupid", length = 32)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@Column(name = "status", nullable = false)
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Long getUsdtime() {
		return usdtime;
	}

	public void setUsdtime(Long usdtime) {
		this.usdtime = usdtime;
	}

	public ParentAccount getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(ParentAccount parentAccount) {
		this.parentAccount = parentAccount;
	}

	public String getStatusValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.SUB_ACCOUNT_STATUS,String.valueOf(this.status));

	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Account(ParentAccount parentAccount) {
		super();
		this.parentAccount = parentAccount;
	}	
	
	public Account() {
		
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	@Column(name = "insurance_no")
	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}
}