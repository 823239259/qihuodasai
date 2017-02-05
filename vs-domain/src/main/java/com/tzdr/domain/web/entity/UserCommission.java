package com.tzdr.domain.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name="w_user_commission")
public class UserCommission extends BaseEntity {

	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;

	/**
	 * 用户编号
	 */
	private String uid;
	
	/**
	 * 管理费
	 */
	private Double manageMoney = 0.00;
	
	/**
	 * 用户返点
	 */
	private Double rebate = 0.00;
	
	/**
	 * 预算佣金
	 */
	private Double budgetMoney = 0.00;
	
	/**
	 * 下级返点值
	 */
	private Double subordinateRebate = 0.00;
	
	/**
	 * 收入佣金
	 */
	private Double money = 0.00;
	
	/**
	 * 收入来源用户编号
	 */
	private String incomeSourceUid;
	
	/**
	 * 扣除下级收入佣金用户编号
	 */
	private String deductChildUid;
	
	/**
	 * 流水号
	 */
	private String serialNumber;
	
	/**
	 * 描述
	 */
	private String remark;
	
	/**
	 * 创建时间
	 */
	private Long createTime = new Date().getTime()/1000;
	
	/**
	 * 数据状态，0：自动，1：手动
	 */
	private Integer type = 0;
	
	/**
	 * 管理费时间【手动】
	 */
	private Long manageFeeTime;

	public String getUid() {
		return uid;
	}

	@Column(name="uid")
	public void setUid(String uid) {
		this.uid = uid;
	}

	public Double getManageMoney() {
		return manageMoney;
	}

	@Column(name="manage_money")
	public void setManageMoney(Double manageMoney) {
		this.manageMoney = manageMoney;
	}

	public Double getRebate() {
		return rebate;
	}

	@Column(name="rebate")
	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	@Column(name="budget_money")
	public Double getBudgetMoney() {
		return budgetMoney;
	}

	public void setBudgetMoney(Double budgetMoney) {
		this.budgetMoney = budgetMoney;
	}
	
	@Column(name="subordinate_rebate")
	public Double getSubordinateRebate() {
		return subordinateRebate;
	}

	public void setSubordinateRebate(Double subordinateRebate) {
		this.subordinateRebate = subordinateRebate;
	}
	
	@Column(name="money")
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name="income_source_uid")
	public String getIncomeSourceUid() {
		return incomeSourceUid;
	}

	public void setIncomeSourceUid(String incomeSourceUid) {
		this.incomeSourceUid = incomeSourceUid;
	}

	@Column(name="deduct_child_uid")
	public String getDeductChildUid() {
		return deductChildUid;
	}

	public void setDeductChildUid(String deductChildUid) {
		this.deductChildUid = deductChildUid;
	}

	@Column(name="serial_number")
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="createtime")
	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	@Column(name="type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name="manage_fee_time")
	public Long getManageFeeTime() {
		return manageFeeTime;
	}

	public void setManageFeeTime(Long manageFeeTime) {
		this.manageFeeTime = manageFeeTime;
	}
}
