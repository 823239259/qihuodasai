package com.tzdr.domain.web.entity;

import com.tzdr.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by GuoXingyou on 2016/4/19.
 */
@Entity
@Table(name = "f_future_match_account")
public class FutureMatchAccount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户账户
	 */
	@Column(name = "account")
	private String account;

	/**
	 * 账户密码
	 */
	@Column(name = "password")
	private String password;
	/**
	 * 方案id
	 */
	@Column(name = "tid")
	private String tid;
	/**
	 * 分配时间
	 */
	@Column(name = "assign_time")
	private Long assignTime;

	/**
	 * 开仓手数
	 */
	@Column(name = "lever")
	private Integer lever;

	/**
	 * 操盘金额
	 */
	@Column(name = "trade_money")
	private Double tradeMoney;

	/**
	 * 期货类型(1,商品期货综合，2国际期货综合，3富时A50，4国际原油，5恒生指数 ,6小恒指)
	 */
	@Column(name = "business_type")
	private Integer businessType;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Long createTime;
	/**
	 * 是否使用 0 没使用 1已使用
	 */
	private Integer isUse;

	public Integer getIsUse() {
		return isUse;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Long getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Long assignTime) {
		this.assignTime = assignTime;
	}

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
