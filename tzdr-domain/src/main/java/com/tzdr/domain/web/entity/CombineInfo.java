package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.api.hundsun.data.Combinfo;
import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;

/**
 * 恒生 组合编号id 和 单元序号 对应表
 * @zhouchen
 * 2015年2月11日
 */
@Entity
@Table(name = "w_combine_info")
public class CombineInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 单元序号
	 */
	@Column(unique=true)
	private Long assetId;
	
	
	/**
	 * 组合编号
	 */
	private String combineId;
	
	
	/**
	 * 账户编号
	 */
	private String fundAccount;
	
	/**
	 *  录入时间
	 */
	private Long  entryTime;

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public String getCombineId() {
		return combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	public String getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}

	public Long getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Long entryTime) {
		this.entryTime = entryTime;
	}
	
	public CombineInfo() {
	
	}
	
	public CombineInfo(Combinfo combinfo) {
		this.assetId = combinfo.getAssetId();
		this.combineId = combinfo.getCombineId();
		this.fundAccount = combinfo.getFundAccount();
		this.entryTime = Dates.getCurrentLongDate();
	}
	
}