package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;
import java.math.BigInteger;


/**
 * 
 * <p></p>
 * @author WangPinQun
 * @see 
 * @version 2.0
 * 2015年10月23日下午5:01:27
 */
public class HKUserTradeWebVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 方案ID
	 */
	private String id;
	
	/**
	 * 配资组合号
	 */
	private String groupId;
	
	/**
	 * 某帐号总操盘资金
	 */
	private Double totalOperateMoney=0.0;
	
	/**
	 * 某帐号总风险保证金
	 */
	private Double totalLeverMoney=0.0;
	
	
	/**
	 * 某帐号总盈亏
	 */
	private Double totalAccrual=0.0;
	
	/**
	 * 开始交易时间
	 */
	private BigInteger starttime;
	/**
	 * 开始交易时间 String
	 */
	private String starttimeString;
	
	/**
	 * 交易结束时间
	 */
	private BigInteger endtime;
	/**
	 * 交易结束时间 String
	 */
	private String endtimeString;
	
	/**
	 * 某帐号总追加保证金
	 */
	private Double totalAppendLeverMoney=0.0;
	
	/**
	 * 配资状态
	 */
	private Short status;
	
	/**
	 * 亏损补仓线
	 */
	private Double warning=0.0;
	
	/**
	 * 亏损平仓线
	 */
	private Double open=0.0;
	
	/**
	 * 交易账户
	 */
	private String accountNo;
	
	/**
	 * 交易账户密码
	 */
	private String password;
	
	/**
	 * 
	 * 开户审核状态------ 0：开户待审核    1：开户通过（变为操盘中）   2：审核不通过 （开户失败） 
	 */
	private Integer auditStatus;
	
	
	 /**
	  * 终结审核状态----- 0：终结待审核    1：方案终结审核通过          2：方案终结审核不通过
	  */
	private Integer auditEndStatus;
	
	/**
	 * 用户编号
	 */
	private String uid;
	
	/**
	 * 预计自然结束天数(算利息)
	 */
	private BigInteger  naturalDays;
	
	/**
	 * 母账户的 编号
	 */
	private String parentAccountId;

	/**
	 * 借款期限
	*/
	private Integer startdays=0;
	
	/**
	 * 结算金额=终结方案后总金额
	*/
	private Double finishedMoney;
	
	/**
	 * 利息
	 */
	private double apr=0.0;
	
	/**
	 * 预计交易结束时间
	 */
	private BigInteger estimateEndtime;
	/**
	 * 预计交易结束时间 String
	*/
	private String estimateEndtimeString;
	
	/**
	 * 天息（管理费）
	 */
	private Double feeDay=0.0;
	
	/**
	 * 月息（利息）
	 */
	private Double feeMonth=0.0;
	
	/**
	 *  资产总值
	 */
	private double assetTotalValue=0.0;
	
	/**
	 * 已交易天数
	 */
	private Long tradingDays=0l;

	/**
	 * 杠杆
	 */
	private Integer lever;
	
	
	/**
	 * 总配资金额
	 */
	private Double totalLending;
	
	/**
	 * 保险单号
	 */
	private String policyNo;
	
	/**
	 * 港元汇率（方案终结时计算的汇率）
	 */
	private Double endExchangeRate=0.0;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public Double getTotalOperateMoney() {
		return totalOperateMoney;
	}


	public void setTotalOperateMoney(Double totalOperateMoney) {
		this.totalOperateMoney = totalOperateMoney;
	}


	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}


	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}


	public Double getTotalAccrual() {
		return totalAccrual;
	}


	public void setTotalAccrual(Double totalAccrual) {
		this.totalAccrual = totalAccrual;
	}


	public BigInteger getStarttime() {
		return starttime;
	}


	public void setStarttime(BigInteger starttime) {
		this.starttime = starttime;
	}

	public BigInteger getEndtime() {
		return endtime;
	}


	public void setEndtime(BigInteger endtime) {
		this.endtime = endtime;
	}

	public Double getTotalAppendLeverMoney() {
		return totalAppendLeverMoney;
	}


	public void setTotalAppendLeverMoney(Double totalAppendLeverMoney) {
		this.totalAppendLeverMoney = totalAppendLeverMoney;
	}


	public Short getStatus() {
		return status;
	}


	public void setStatus(Short status) {
		this.status = status;
	}

	public Double getWarning() {
		return warning;
	}


	public void setWarning(Double warning) {
		this.warning = warning;
	}


	public Double getOpen() {
		return open;
	}


	public void setOpen(Double open) {
		this.open = open;
	}


	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getAuditStatus() {
		return auditStatus;
	}


	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}


	public Integer getAuditEndStatus() {
		return auditEndStatus;
	}


	public void setAuditEndStatus(Integer auditEndStatus) {
		this.auditEndStatus = auditEndStatus;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public BigInteger getNaturalDays() {
		return naturalDays;
	}


	public void setNaturalDays(BigInteger naturalDays) {
		this.naturalDays = naturalDays;
	}


	public String getParentAccountId() {
		return parentAccountId;
	}


	public void setParentAccountId(String parentAccountId) {
		this.parentAccountId = parentAccountId;
	}


	public Integer getStartdays() {
		return startdays;
	}


	public void setStartdays(Integer startdays) {
		this.startdays = startdays;
	}


	public Double getFinishedMoney() {
		return finishedMoney;
	}


	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}


	public double getApr() {
		return apr;
	}


	public void setApr(double apr) {
		this.apr = apr;
	}


	public BigInteger getEstimateEndtime() {
		return estimateEndtime;
	}


	public void setEstimateEndtime(BigInteger estimateEndtime) {
		this.estimateEndtime = estimateEndtime;
	}


	public Double getFeeDay() {
		return feeDay;
	}


	public void setFeeDay(Double feeDay) {
		this.feeDay = feeDay;
	}


	public Double getFeeMonth() {
		return feeMonth;
	}


	public void setFeeMonth(Double feeMonth) {
		this.feeMonth = feeMonth;
	}


	public double getAssetTotalValue() {
		return assetTotalValue;
	}


	public void setAssetTotalValue(double assetTotalValue) {
		this.assetTotalValue = assetTotalValue;
	}


	public String getStarttimeString() {
		return starttimeString;
	}


	public void setStarttimeString(String starttimeString) {
		this.starttimeString = starttimeString;
	}


	public String getEndtimeString() {
		return endtimeString;
	}


	public void setEndtimeString(String endtimeString) {
		this.endtimeString = endtimeString;
	}


	public String getEstimateEndtimeString() {
		return estimateEndtimeString;
	}


	public void setEstimateEndtimeString(String estimateEndtimeString) {
		this.estimateEndtimeString = estimateEndtimeString;
	}


	public Long getTradingDays() {
		return tradingDays;
	}


	public void setTradingDays(Long tradingDays) {
		this.tradingDays = tradingDays;
	}


	public Integer getLever() {
		return lever;
	}


	public void setLever(Integer lever) {
		this.lever = lever;
	}


	public Double getTotalLending() {
		return totalLending;
	}


	public void setTotalLending(Double totalLending) {
		this.totalLending = totalLending;
	}


	public String getPolicyNo() {
		return policyNo;
	}


	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}


	public Double getEndExchangeRate() {
		return endExchangeRate;
	}


	public void setEndExchangeRate(Double endExchangeRate) {
		this.endExchangeRate = endExchangeRate;
	}
}
