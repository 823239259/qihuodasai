package com.tzdr.domain.web.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

@Entity
@Table(name="w_customer")
public class Customer extends BaseCrudEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 客户姓名
	 */
	private String name;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 所属销售
	 */
	private Long belongMarket;
	
	/**
	 * 所属组织code
	 */
	private String organizationCode;
	
	/**
	 * 分派时间
	 */
	private Long assignTime;
	
	public void add(CustomerDetails details) {
		if (customerDetails == null) {
			this.customerDetails = new ArrayList<CustomerDetails>();
		}
		details.setCustomer(this);
		this.customerDetails.add(details);
	}
	
    /**
     * 客户联系信息
     */
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="customer")
	private List<CustomerDetails> customerDetails;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getBelongMarket() {
		return belongMarket;
	}

	public void setBelongMarket(Long belongMarket) {
		this.belongMarket = belongMarket;
	}

	public Long getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Long assignTime) {
		this.assignTime = assignTime;
	}

	public List<CustomerDetails> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(List<CustomerDetails> customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
}
