package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 港股终结方案 Vo
 * @author WangPinQun
 * 2015年10月19日 上午11:23:50
 */
public class HkEndTradeVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * 手工开户 id
	 */
	private String id;
	
	/**
	 * 配资 id
	 */
	private String tradeId;
	
	/**
	 * 手机号
	 */
	@AllowExcel(name="手机号码")
	private String  mobile;
	
	/**
	 * 客户姓名
	 */
	@AllowExcel(name="客户姓名")
	private String  tname;

	/**
	 * 账户交易通道
	 */
	@AllowExcel(name="账户交易通道")
	private String  tradeChannelValue;
	private Integer tradeChannel;
	
	/**
	 * 交易账户名
	 */
	@AllowExcel(name="交易账户名")
	private String accountName;
	
	/**
	 * 交易账号
	 */
	@AllowExcel(name="交易账户")
	private String accountNo;
	
	/**
	 * 配资组合号
	 */
	@AllowExcel(name="方案编号")
	private String groupId;
	
	/**
	 * 操盘保证金（元）
	 */
	@AllowExcel(name="保证金（元）")
	private Double leverMoney;
	
	/**
	 * 总操盘金额（港元）
	 */
	@AllowExcel(name="总操盘金额（港元）")
	private Double totalLeverMoney;
	
	/**
	 * 结算申请金额（港元）
	 */
	@AllowExcel(name="结算金额（港元）")
	private Double finishedMoney;
	
	/**
	 * 审核显示状态 0:未审核 1：通过 2：未通过
	 */
	@AllowExcel(name="审核状态")
	private String  auditEndStatusValue;
	private int  auditEndStatus;
	
	/**
	 * 审2人姓名
	 */
	@AllowExcel(name="审核人")
	private String  endAuditUserName;
	
	/**
	 * 审2时间 
	 */
	@AllowExcel(name="审2时间 ")
	private String  endAuditTimeValue;
	private BigInteger  endAuditTime;
	
	/**
	 * 提交时间
	 */
	@AllowExcel(name="提交时间")
	private String  endSubTimeValue;
	private BigInteger  endSubTime;
	
	/**
	 * 审1时间 
	 */
	@SuppressWarnings("unused")
	private String  endAuditFirsteTimeValue;
	private BigInteger  endAuditFirsteTime;
	
	/**
	 * 审1人姓名
	 */
	private String  updateUser;
	
	/**
	 * 不通过原因
	 */
	private String  failCause;
	
	/**
	 * 提交间隔交易日时间
	 */
	private int  submitDays = 0;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getLeverMoney() {
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEndAuditUserName() {
		return endAuditUserName;
	}

	public void setEndAuditUserName(String endAuditUserName) {
		this.endAuditUserName = endAuditUserName;
	}

	public int getAuditEndStatus() {
		return auditEndStatus;
	}

	public void setAuditEndStatus(int auditEndStatus) {
		this.auditEndStatus = auditEndStatus;
	}

	public String getAuditEndStatusValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.AUDIT_STATUS,String.valueOf(this.auditEndStatus));
	}

	public void setAuditEndStatusValue(String auditEndStatusValue) {
		this.auditEndStatusValue = auditEndStatusValue;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}

	public String getTradeChannelValue() {
		if(!ObjectUtil.equals(null,this.tradeChannel)){
			return CacheManager.getDataMapByKey(DataDicKeyConstants.HK_TRADE_CHANNEL, String.valueOf(this.tradeChannel));
		}
		return null;
	}

	public void setTradeChannelValue(String tradeChannelValue) {
		this.tradeChannelValue = tradeChannelValue;
	}

	public Integer getTradeChannel() {
		return tradeChannel;
	}

	public void setTradeChannel(Integer tradeChannel) {
		this.tradeChannel = tradeChannel;
	}

	public String getFailCause() {
		return failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

	public String getEndAuditTimeValue() {
		if (ObjectUtil.equals(null,this.endAuditTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.endAuditTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setEndAuditTimeValue(String endAuditTimeValue) {
		this.endAuditTimeValue = endAuditTimeValue;
	}

	public BigInteger getEndAuditTime() {
		return endAuditTime;
	}

	public void setEndAuditTime(BigInteger endAuditTime) {
		this.endAuditTime = endAuditTime;
	}

	public String getEndSubTimeValue() {
		if (ObjectUtil.equals(null,this.endSubTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.endSubTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setEndSubTimeValue(String endSubTimeValue) {
		this.endSubTimeValue = endSubTimeValue;
	}

	public BigInteger getEndSubTime() {
		return endSubTime;
	}

	public void setEndSubTime(BigInteger endSubTime) {
		this.endSubTime = endSubTime;
	}

	public String getEndAuditFirsteTimeValue() {
		if (ObjectUtil.equals(null,this.endAuditFirsteTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.endAuditFirsteTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setEndAuditFirsteTimeValue(String endAuditFirsteTimeValue) {
		this.endAuditFirsteTimeValue = endAuditFirsteTimeValue;
	}

	public BigInteger getEndAuditFirsteTime() {
		return endAuditFirsteTime;
	}

	public void setEndAuditFirsteTime(BigInteger endAuditFirsteTime) {
		this.endAuditFirsteTime = endAuditFirsteTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public int getSubmitDays() {
		return submitDays;
	}

	public void setSubmitDays(int submitDays) {
		this.submitDays = submitDays;
	}
}
