package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 补录充值记录中间表
 * @author zhouchen
 */
@Entity
@Table(name = "w_recharge_additional")
public class RechargeAdditional extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 客户手机号
	 */
	private String mobile;
	
	
	/**
	 * 充值记录id
	 */
	private String rechargeId;
	
	/**
	 * 用户id
	 */
	private String  uid;
	
	/**
	 * 银行卡号
	 */
	private String bankCard;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public RechargeAdditional() {
		// TODO Auto-generated constructor stub
	}

	public RechargeAdditional(String mobile, String rechargeId, String uid,
			String bankCard) {
		super();
		this.mobile = mobile;
		this.rechargeId = rechargeId;
		this.uid = uid;
		this.bankCard = bankCard;
	}
	
	
	
}