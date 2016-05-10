package com.tzdr.domain.api.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * wuser vo
 * @author zhouchen
 * 2015年5月26日 上午9:57:05
 */
public class UserInfoVo implements Serializable  {
	
	
	private static final long serialVersionUID = -3390139639987375733L;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 账户余额
	 */
	private double balance;
	/**
	 * 账户总资产
	 */
	private double totalFund;
	/**
	 * 配资金额
	 */
	private double tradeFund; 
	/**
	 * 保证金
	 */
	private double cashFund;
	/**
	 * 冻结金额
	 */
	private double freezeFund;
	/**
	 * “补仓方案个数”
	 */
	private int marginCallNum; 
	/**
	 * 是否足够扣取管理费
	 */
	private boolean isEnough;
	
	/**
	 * 支付宝帐号
	 */
	private String alipayAccount;
	
	
	/**
	 * 推广码
	 */
	private String generalizeId;
	
	/**
	 * 提现密码设置  默认0 未设置  1：已设置
	 */
	private BigInteger withDrawPwd;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getTotalFund() {
		return totalFund;
	}
	public void setTotalFund(double totalFund) {
		this.totalFund = totalFund;
	}
	public double getTradeFund() {
		return tradeFund;
	}
	public void setTradeFund(double tradeFund) {
		this.tradeFund = tradeFund;
	}
	public double getCashFund() {
		return cashFund;
	}
	public void setCashFund(double cashFund) {
		this.cashFund = cashFund;
	}
	public double getFreezeFund() {
		return freezeFund;
	}
	public void setFreezeFund(double freezeFund) {
		this.freezeFund = freezeFund;
	}
	public int getMarginCallNum() {
		return marginCallNum;
	}
	public void setMarginCallNum(int marginCallNum) {
		this.marginCallNum = marginCallNum;
	}
	public boolean isEnough() {
		return isEnough;
	}
	public void setEnough(boolean isEnough) {
		this.isEnough = isEnough;
	}
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	public String getGeneralizeId() {
		return generalizeId;
	}
	public void setGeneralizeId(String generalizeId) {
		this.generalizeId = generalizeId;
	}
	public BigInteger getWithDrawPwd() {
		return withDrawPwd;
	}
	public void setWithDrawPwd(BigInteger withDrawPwd) {
		this.withDrawPwd = withDrawPwd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
