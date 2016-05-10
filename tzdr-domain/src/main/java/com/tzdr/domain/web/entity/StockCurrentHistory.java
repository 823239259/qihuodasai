package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;

/**
 * 每日恒生资产数据
 * @author Administrator
 *
 */
@Entity
@Table(name = "w_stock_current_history")
public class StockCurrentHistory  extends BaseEntity{
	
   /**
    * 恒生子账户名
    */
   private  String accountName;
   
   /**
    * 配资组合id
    */
   private  String groupId;
	/**
	 * 母账号
	 */
	private String fundAccount;
	
	/**
	 * 组合编号
	 */
	private String combineId;
	
	/**
	 * 单元序号
	 */
	private long assetId;
	
	/**
	 * 当前现金余额
	 */
	private double currentCash;
	
	/**
	 * 股票资产值
	 */
	private double marketValue;

	/**
	 * 当前数据时间
	 */
	private Long historyDate;
	/**
	 * 获取数据时间
	 */
	private Long createDate=Dates.getCurrentLongDate();
	
	public String getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}

	public String getCombineId() {
		return combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

	public double getCurrentCash() {
		return currentCash;
	}

	public void setCurrentCash(double currentCash) {
		this.currentCash = currentCash;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	public Long getHistoryDate() {
		return historyDate;
	}

	public void setHistoryDate(Long historyDate) {
		this.historyDate = historyDate;
	}
		

	public StockCurrentHistory() {

	}

	public StockCurrentHistory(StockCurrent stockCurrent,String accountName,String groupId) {
		this.assetId=stockCurrent.getAssetId();
		this.combineId=stockCurrent.getCombineId();
		this.currentCash=stockCurrent.getCurrentCash();
		this.fundAccount=stockCurrent.getFundAccount();
		this.marketValue=stockCurrent.getMarketValue();
		this.historyDate=Dates.getDatebyDaynum(-1);
		this.accountName=accountName;
		this.groupId=groupId;
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

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	
	
}
