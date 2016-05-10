package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_security_code")
public class SecurityCode  extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String mobile;

	private Long createdate;

	private String securityCode;


	@Column(name="mobile", nullable=false, length=32)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name="createdate", nullable=false)
	public Long getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	@Column(name="security_code", nullable=false, length=20)
    public String getSecurityCode() {
        return this.securityCode;
    }
    
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
    
    public SecurityCode() {
		// TODO Auto-generated constructor stub
	}

	public SecurityCode(String mobile, Long createdate, String securityCode) {
		super();
		this.mobile = mobile;
		this.createdate = createdate;
		this.securityCode = securityCode;
	}
    
    
    
}