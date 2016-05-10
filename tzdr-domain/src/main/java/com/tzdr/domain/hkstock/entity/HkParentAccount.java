package com.tzdr.domain.hkstock.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;


/**
 * 港股母账户
 * @author zhouchen
 * 2015年10月16日 上午11:03:36
 */
@Entity
@Table(name = "hk_parent_account")
public class HkParentAccount extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 帐号
	 */
	private String accountNo;
	
	
    /**
     *账户名称
     */
    private  String name;
  
	/**
	 * 账户交易通道 0:TTS
	 */
	private int  tradeChannel=0;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTradeChannel() {
		return tradeChannel;
	}

	public void setTradeChannel(int tradeChannel) {
		this.tradeChannel = tradeChannel;
	}
	
	
	
}