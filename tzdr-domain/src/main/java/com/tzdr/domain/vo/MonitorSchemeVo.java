package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

public class MonitorSchemeVo implements Serializable {
	@SqlColumn 
    private String uid;

	@SqlColumn 
    private String groupId;
	@SqlColumn 
    private String stockAssets;
	@SqlColumn 
    private String accountName ;//恒生账户名
	@SqlColumn 
	private String account;//恒生账号
	@SqlColumn 
	private String mobile;//手机号码
	@SqlColumn 
	private String uname;//用户姓名
	@SqlColumn 
	private double totalLeverMoney;//风险保证金
	@SqlColumn 
	private double totalLending;//配资金额'
	@SqlColumn 
	private double totalOperateMoney;//总操盘资金'
	@SqlColumn 
	private double warning;//亏损补仓线'
	@SqlColumn 
	private double open;//亏损平仓线'
	@SqlColumn 
	private double assetTotalValue;//资产总值'
	@SqlColumn 
	private String openDistance;//平仓距离'
	@SqlColumn 
	private String wearingDistance;//穿仓距离'
	@SqlColumn 
	private Boolean buyStatus;//买入状态'
	@SqlColumn 
	private Boolean sellStatus;//卖出状态'
	
	/**
	 * 使用天数
	 */
	@SqlColumn
	private Integer useDays;
	
	/**
	 * 活动类型 0：没有活动，1：8800活动
	 */
	@SqlColumn
	private int activityType;
	
	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	/**
	 * 方案类型
	 */
	private String activityTypeStr;

	public String getActivityTypeStr() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.ACTIVITY_TYPE,String.valueOf(this.activityType));
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getStockAssets() {
		return stockAssets;
	}
	public void setStockAssets(String stockAssets) {
		this.stockAssets = stockAssets;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public double getTotalLeverMoney() {
		return totalLeverMoney;
	}
	public void setTotalLeverMoney(double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}
	public double getTotalLending() {
		return totalLending;
	}
	public void setTotalLending(double totalLending) {
		this.totalLending = totalLending;
	}
	public double getTotalOperateMoney() {
		return totalOperateMoney;
	}
	public void setTotalOperateMoney(double totalOperateMoney) {
		this.totalOperateMoney = totalOperateMoney;
	}
	public double getWarning() {
		return warning;
	}
	public void setWarning(double warning) {
		this.warning = warning;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getAssetTotalValue() {
		return assetTotalValue;
	}
	public void setAssetTotalValue(double assetTotalValue) {
		this.assetTotalValue = assetTotalValue;
	}
	public String getOpenDistance() {
		return openDistance;
	}
	public void setOpenDistance(String openDistance) {
		this.openDistance = openDistance;
	}
	public String getWearingDistance() {
		return wearingDistance;
	}
	public void setWearingDistance(String wearingDistance) {
		this.wearingDistance = wearingDistance;
	}
	public String getBuyStatus() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.LIMIT_STATUS,
				String.valueOf(this.buyStatus));
	}
	public void setBuyStatus(Boolean buyStatus) {
		this.buyStatus = buyStatus;
	}
	public Boolean getSellStatus() {
		return sellStatus;
	}
	public void setSellStatus(Boolean sellStatus) {
		this.sellStatus = sellStatus;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getUseDays() {
		return useDays;
	}
	public void setUseDays(Integer useDays) {
		this.useDays = useDays;
	}
	
	
}
