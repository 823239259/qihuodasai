package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_setting")
public class Setting   extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String skey;
	private String svalue;

	@Column(name="skey", nullable=false)
	public String getSkey() {
		return this.skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	@Column(name="svalue", nullable=false, length=65535)
	public String getSvalue() {
		return this.svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}
}