package com.tzdr.domain.vo;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.SqlColumn;

public class MonthEndVo {
	/**
	 * 用户名
	 */
	@SqlColumn
	private String userName;

	@SqlColumn
	private String tradeId;
	/**
	 * 电话
	 */
	@SqlColumn
	private String mobile;
	/**
	 * 交易账号
	 */
	@SqlColumn
	private String account;
	/**
	 * 交易账号名
	 */
	@SqlColumn
	private String accountName;

	/**
	 * 方案号
	 */
	@SqlColumn
	private String groupId;
	/**
	 * 用户余额
	 */
	@SqlColumn
	private Double balance;
	/**
	 * 方案开始时间
	 */
	@SqlColumn
	private Long startTime;
	/**
	 * 方案时长
	 */
	@SqlColumn
	private Integer tradeMonth;
	/**
	 * 方案延长时间
	 */
	@SqlColumn
	private Integer prolongMonth;

	/**
	 * 预计自然结束天数(算利息)
	 */
	@SqlColumn
	private Long surplusDays;

	/**
	 * 预计交易结束时间
	 */
	@SqlColumn
	private Long estimateEndtime;

	/**
	 * 是否手动延期
	 */
	@SqlColumn
	private Integer isManualDelay;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public String getTradeMonth() {
		return tradeMonth+"个月";
	}

	public void setTradeMonth(Integer tradeMonth) {
		this.tradeMonth = tradeMonth;
	}

	public String getProlongMonth() {
		if (ObjectUtil.equals(null, this.prolongMonth)){
			return "";
		}
		if (this.prolongMonth.intValue()==0){
			return "";
		}
		return prolongMonth+"个月";
	}

	public void setProlongMonth(Integer prolongMonth) {
		this.prolongMonth = prolongMonth;
	}

	

	public Long getSurplusDays() {
		return surplusDays;
	}

	public void setSurplusDays(Long surplusDays) {
		this.surplusDays = surplusDays;
	}

	public Long getEstimateEndtime() {
		return estimateEndtime;
	}

	public void setEstimateEndtime(Long estimateEndtime) {
		this.estimateEndtime = estimateEndtime;
	}

	public Integer getIsManualDelay() {
		return isManualDelay;
	}

	public void setIsManualDelay(Integer isManualDelay) {
		this.isManualDelay = isManualDelay;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

}
