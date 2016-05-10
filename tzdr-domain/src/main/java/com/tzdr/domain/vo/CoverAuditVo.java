package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.tzdr.common.utils.SqlColumn;

public class CoverAuditVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SqlColumn 
	private String id ;//编号
	@SqlColumn 
	private String uid ;//用户编号
	@SqlColumn 
	private String mobile;//手机号码
	@SqlColumn 
	private String tname;//用户姓名
	@SqlColumn 
    private String groupId ;//方案组合号
	@SqlColumn 
    private String accountName ;//恒生账户名
	@SqlColumn 
	private String account;//恒生账号
	@SqlColumn
	private Double coverMoney;//补仓金额
	
	/**
	 * 审核状态
	 * 0：未审核，1：通过    2：未通过
	 */
	@SqlColumn
	private Short status = 0;
	
	/**
	 * 创建时间
	 */
	@SqlColumn
	private BigInteger ctime;
	
	@SqlColumn
	private Long longCtime;
	
	/**
	 * 更新时间
	 */
	@SqlColumn
	private BigInteger uptime;
	
	@SqlColumn
	private Long longUptime;
	
	/**
	 * 更新人
	 */
	@SqlColumn
	private String upuid;
	
	/**
	 * 更新人姓名
	 */
	@SqlColumn
	private String upuName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getCoverMoney() {
		return coverMoney;
	}

	public void setCoverMoney(Double coverMoney) {
		this.coverMoney = coverMoney;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public BigInteger getCtime() {
		return ctime;
	}

	public void setCtime(BigInteger ctime) {
		this.ctime = ctime;
	}

	public BigInteger getUptime() {
		return uptime;
	}

	public void setUptime(BigInteger uptime) {
		this.uptime = uptime;
	}

	public String getUpuid() {
		return upuid;
	}

	public void setUpuid(String upuid) {
		this.upuid = upuid;
	}

	public String getUpuName() {
		return upuName;
	}

	public void setUpuName(String upuName) {
		this.upuName = upuName;
	}

	public Long getLongCtime() {
		return longCtime;
	}

	public void setLongCtime(Long longCtime) {
		this.longCtime = longCtime;
	}

	public Long getLongUptime() {
		return longUptime;
	}

	public void setLongUptime(Long longUptime) {
		this.longUptime = longUptime;
	}
}
