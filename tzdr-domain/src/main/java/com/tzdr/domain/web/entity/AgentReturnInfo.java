package com.tzdr.domain.web.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see 代理收益统计表
 * @version 2.0
 * 2015年4月18日上午9:31:30
 */
@Entity
@Table(name="w_agent_return_info")
public class AgentReturnInfo  extends BaseEntity {
	
	private static final long serialVersionUID = -1575985282262307090L;
	
	@Column	
	private String uid;
	
	@Column(name="add_date")
	private Integer addDate;

	@Column(name="child_number")
	private Integer childNumber;
	
	@Column(name="all_child_number")
	private Integer allChildNumber;
	//总金额 收取下级 + 自身的总金额
	@Column(name="total_amount")
	private BigDecimal totalAmount;
	//当天的收入佣金
	@Column(name="return_amount")
	private BigDecimal returnAmount;
	
	//方案数
	@Column(name="scheme_number")
	private Integer schemeNumber;
	
	public Integer getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(Integer schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getAddDate() {
		return addDate;
	}

	public void setAddDate(Integer addDate) {
		this.addDate = addDate;
	}

	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}

	public Integer getAllChildNumber() {
		return allChildNumber;
	}

	public void setAllChildNumber(Integer allChildNumber) {
		this.allChildNumber = allChildNumber;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}
	

}
