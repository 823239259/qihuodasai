package com.tzdr.domain.vo;

import java.io.Serializable;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;

/**
 * 恒生资金报表 Vo
 * @author zhouchen
 *
 */
public class HundSunFundVo implements Serializable{

	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;
	
	/**
	 * 恒生账号开户券商
	 */
	@AllowExcel(name="所属券商户")
	private String hsBelongBroker;
	
	/**
	 * 用户姓名
	 */
	@AllowExcel(name="用户名")
	private String uname;
	
	/**
	 * 实名
	 */
	@AllowExcel(name="实名")
	private String realName;
	

	/**
	 * 恒生账户名
	 */
	@AllowExcel(name="恒生账户名")
	private String accountName;
	
	
	
	/**
	 * 配资组合号
	 */
	@AllowExcel(name="方案号")
	private String groupId;
	
	/**
	 * 保证金
	 */
	@AllowExcel(name="保证金")
	private Double LeverMoney=0.0;
	
	/**
	 * 杠杆  倍数
	 */
	@AllowExcel(name="倍数")
	private String lever;
	
	
	/**
	 * 配资天数
	 */
	@AllowExcel(name="天数")
	private String  naturalDays;
	
	
	/**
	 * 股票成交金额
	 */
	@AllowExcel(name="股票成交金额")
	private Double stockAssets;
	
	/**
	 * 股票成交股数
	 */
	@AllowExcel(name="股票成交股数")
	private String stockNumber;
	
	/**
	 *  上一日的账户余额
	 */
	@AllowExcel(name="上一日的账户余额")
	private Double yestodayBalance=0.0;
	
	/**
	 * 资金划拨 金额
	 */
	@AllowExcel(name="资金划拨")
	private Double changeFund=0.0;
	
	
	/**
	 * 佣金差
	 */
	@AllowExcel(name="佣金差")
	private Double commission=0.0;

	/**
	 * 过户费
	 */
	@AllowExcel(name="过户费差")
	private Double transferFee =0.0;
	
	/**
	 * 恒生某帐号盈亏
	 */
	@AllowExcel(name="当日累计盈亏")
	private Double accrual=0.0;
	
	/**
	 *  当前的账户余额
	 */
	@AllowExcel(name="账户余额")
	private Double currentBalance=0.0;
	
	/**
	 * 单元序号
	 */
	private String assetId;

	/**
	 * 组合号
	 */
	private String combineId;
	
	
	/**
	 * 恒生账户
	 */
	private String account;
	
	/**
	 * 状态
	 */
	private int status;
	
	
	/**
	 * 恒生目账户名
	 */
	private String parentAccountName;
	
	/**
	 * 恒生母账户编号
	 */
	private String parentAccountNo;
	
	
	
	/**
	 * 0是母账户 1：子账户
	 */
	private int  type=0;

	public String getNaturalDays() {
		return naturalDays;
	}

	public void setNaturalDays(String naturalDays) {
		this.naturalDays = naturalDays;
	}

	public String getCombineId() {
		return combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	public String getLever() {
		return lever;
	}

	public void setLever(String lever) {
		this.lever = lever;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(Double transferFee) {
		this.transferFee = transferFee;
	}

	public Double getLeverMoney() {
		return LeverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		LeverMoney = leverMoney;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getChangeFund() {
		return changeFund;
	}

	public void setChangeFund(Double changeFund) {
		this.changeFund = changeFund;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Double getAccrual() {
		return accrual;
	}

	public void setAccrual(Double accrual) {
		this.accrual = accrual;
	}

	public String getHsBelongBroker() {
		return hsBelongBroker;
	}

	public void setHsBelongBroker(String hsBelongBroker) {
		this.hsBelongBroker = hsBelongBroker;
	}

	public Double getYestodayBalance() {
		if (ObjectUtil.equals(null, this.yestodayBalance)){
			return 0.0;
		}
		return yestodayBalance;
	}

	public void setYestodayBalance(Double yestodayBalance) {
		this.yestodayBalance = yestodayBalance;
	}

	public Double getStockAssets() {
		return stockAssets;
	}

	public void setStockAssets(Double stockAssets) {
		this.stockAssets = stockAssets;
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getParentAccountName() {
		return parentAccountName;
	}

	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getParentAccountNo() {
		return parentAccountNo;
	}

	public void setParentAccountNo(String parentAccountNo) {
		this.parentAccountNo = parentAccountNo;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public HundSunFundVo() {
		// TODO Auto-generated constructor stub
	}

	public HundSunFundVo(String naturalDays, String lever,
			Double transferFee, Double leverMoney, Double commission,
			Double changeFund, Double accrual, Double yestodayBalance,
			Double stockAssets, String stockNumber, String groupId,
			Double currentBalance) {
		super();
		this.naturalDays = naturalDays;
		this.lever = lever;
		this.transferFee = transferFee;
		LeverMoney = leverMoney;
		this.commission = commission;
		this.changeFund = changeFund;
		this.accrual = accrual;
		this.yestodayBalance = yestodayBalance;
		this.stockAssets = stockAssets;
		this.stockNumber = stockNumber;
		this.groupId = groupId;
		this.currentBalance = currentBalance;
	}
	
	
	
}
