package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 出资人信息
 * @zhouchen
 * 2015年2月12日
 */
@Entity
@Table(name = "w_investor")
public class Investor extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 身份证号
	 */
	private String idCard;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	
	
}