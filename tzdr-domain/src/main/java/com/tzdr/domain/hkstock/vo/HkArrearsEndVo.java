package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 港股欠费方案的VO
 * @Description: 
 * @author liuhaichuan
 * @date 2015年10月20日
 *
 */
public class HkArrearsEndVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 配资组合号
	 */
	private String groupId;

	/**
	 * 客户姓名
	 */
	private String tname;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 账户交易通道
	 */
	private Integer tradeChannel; 
	
	/**
	 * 交易账号
	 */
	private String accountNo;
	
	/**
	 * 方案编号
	 */
	private String programNo;
	
	/**
	 * 账户余额（元）
	 */
	private Double avlBal;

	/**
	 * 欠费金额（元）
	 */
	private Double money;
	
	/**
	 * 欠费时间
	 */
	private BigInteger addTime;
	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getTradeChannel() {
		return tradeChannel;
	}

	public void setTradeChannel(Integer tradeChannel) {
		this.tradeChannel = tradeChannel;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public Double getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(Double avlBal) {
		this.avlBal = avlBal;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public BigInteger getAddTime() {
		return addTime;
	}

	public void setAddTime(BigInteger addTime) {
		this.addTime = addTime;
	}
	
}
