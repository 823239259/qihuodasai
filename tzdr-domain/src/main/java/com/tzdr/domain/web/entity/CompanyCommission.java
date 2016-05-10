package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name="w_company_commission")
public class CompanyCommission  extends BaseEntity{

	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;
	

	/**
	 * 用户手机号码
	 */
	private String mobile;

	/**
	 * 累计佣金
	 */
	private Double totalCommission;

	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "total_commission")
	public Double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(Double totalCommission) {
		this.totalCommission = totalCommission;
	}
}
