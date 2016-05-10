package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name="w_fund_config")
public class
		FundConfig extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4853475024805803080L;
	
	/**
	 * 倍数
	 */
	private Integer times;
	/**
	 * 配资金额
	 */
	private Double fundAmount;
	/**
	 * 创建人
	 */
	private Long createUser;
	/**
	 * 创建时间
	 */
	private Long createDate;
	/**
	 * 修改时间
	 */
	private Long modifyDate;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 保证金
	 */
	private Double cashDeposit;
	
	@Column(name="times", nullable=false)
	public Integer getTimes() {
		return times;
	}
	
	public void setTimes(Integer times) {
		this.times = times;
	}
	
	@Column(name="fund_amount", nullable=false, precision=20, scale=2)
	public Double getFundAmount() {
		return fundAmount;
	}
	
	public void setFundAmount(Double fundAmount) {
		this.fundAmount = fundAmount;
	}
	
	@Column(name="create_user") //, nullable=false
	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
	@Column(name="create_date", nullable=false)
	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	
	@Column(name="modify_date")
	public Long getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Long modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name="remark", length=200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="cash_deposit", nullable=false, precision=20, scale=2)
	public Double getCashDeposit() {
		return cashDeposit;
	}

	public void setCashDeposit(Double cashDeposit) {
		this.cashDeposit = cashDeposit;
	}
}