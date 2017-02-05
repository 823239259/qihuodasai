package com.tzdr.domain.web.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

@Entity
@Table(name="w_customer_detail")
public class CustomerDetails extends BaseCrudEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 联系时间
	 */
	private Long contactTime;
	
	/**
	 * 描述
	 */
	private String remark;
	
	/**
	 * 客户信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_id")
	private Customer customer;
	
	public Long getContactTime() {
		return contactTime;
	}

	public void setContactTime(Long contactTime) {
		this.contactTime = contactTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
