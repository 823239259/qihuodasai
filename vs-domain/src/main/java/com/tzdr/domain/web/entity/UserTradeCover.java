package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name="w_user_trade_cover")
public class UserTradeCover  extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 配资组合号
	 */
	private String groupId;
	
	/**
	 * 补仓金额
	 */
	private Double coverMoney;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)               
	@JoinColumn(name="uid", nullable=true)
	private WUser wuser;

	/**
	 * 审核状态
	 * 0：未审核，1：通过    2：未通过
	 */
	private Short status = 0;
	
	/**
	 * 创建时间
	 */
	private Long ctime;
	
	/**
	 * 创建人
	 */
	private String cuid;
	
	/**
	 * 更新时间
	 */
	private Long uptime;
	
	/**
	 * 更新人
	 */
	private String upuid;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Double getCoverMoney() {
		return coverMoney;
	}

	public void setCoverMoney(Double coverMoney) {
		this.coverMoney = coverMoney;
	}

	public WUser getWuser() {
		return wuser;
	}

	public void setWuser(WUser wuser) {
		this.wuser = wuser;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public Long getUptime() {
		return uptime;
	}

	public void setUptime(Long uptime) {
		this.uptime = uptime;
	}

	public String getUpuid() {
		return upuid;
	}

	public void setUpuid(String upuid) {
		this.upuid = upuid;
	}
}
