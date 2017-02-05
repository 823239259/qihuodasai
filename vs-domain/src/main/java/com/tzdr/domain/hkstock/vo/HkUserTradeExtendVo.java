package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 港股开户 Vo
 * @author WangPinQun
 * 2015年10月19日 上午11:23:50
 */
public class HkUserTradeExtendVo implements Serializable {
	
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
	 * 交易账号
	 */
	@AllowExcel(name="交易账户")
	private String accountNo;

	/**
	 * 交易账户名
	 */
	@AllowExcel(name="交易账户名")
	private String accountName;
	
	/**
	 * 配资组合号
	 */
	@AllowExcel(name="方案编号")
	private String groupId;
	
	/**
	 * 操盘保证金（元）
	 */
	@AllowExcel(name="操盘保证金（元）")
	private Double leverMoney;
	
	/**
	 * 配资金额（港元）
	 */
	@AllowExcel(name="配资金额（港元）")
	private Double money;
	
	/**
	 * 总操盘金额（港元）
	 */
	@AllowExcel(name="总操盘金额（港元）")
	private Double totalLeverMoney;
	
	/**
	 * 亏损警戒线（港元）
	 */
	@AllowExcel(name="亏损警戒线（港元）")
	private Double  warning;
	
	/**
	 * 亏损平仓线（港元）
	 */
	@AllowExcel(name="亏损平仓线（港元）")
	private Double  openline;
	
	/**
	 * 账户交易通道
	 */
	@AllowExcel(name="账户交易通道")
	private String  tradeChannelValue;
	private Integer tradeChannel;
	
	/**
	 * 审核显示状态 0:未审核 1：通过 2：未通过
	 */
	@AllowExcel(name="审核状态")
	private String  auditStatusValue;
	private int  auditStatus;
	
	/**
	 * 1:下个交易日，0:本交易日（立刻生效）
	 */
	private int  tradeStart;
	
	/**
	 * 配资开始交易时间
	*/
	@SuppressWarnings("unused")
	private String  startTimeValue;
	private BigInteger starttime;
	
	/**
	 * 提交时间
	 */
	@AllowExcel(name="提交时间")
	private String  createTimeValue;
	private BigInteger  createTime;
	
	/**
	 * 审核人
	 */
	@AllowExcel(name="审核人")
	private String  auditUser;
	
	/**
	 * 审核时间 显示
	 */
	@AllowExcel(name="审核时间")
	private String  auditTimeValue;
	private BigInteger  auditTime;
	
	/**
	 * 交易账户密码
	 */
	private String password;
	
	/**
	 * 母账户id
	 */
	private String  parentAccountId;
	
	/**
	 * 保险单号
	 */
	private String policyNo;
	
	/**
	 * 不通过原因
	 */
	private String  failCause;
	

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
	
	public BigInteger getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BigInteger createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeValue() {
		if (ObjectUtil.equals(null,this.createTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.createTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setCreateTimeValue(String createTimeValue) {
		this.createTimeValue = createTimeValue;
	}

	public BigInteger getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(BigInteger auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditTimeValue() {
		if (ObjectUtil.equals(null,this.auditTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.auditTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAuditTimeValue(String auditTimeValue) {
		this.auditTimeValue = auditTimeValue;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditStatusValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.AUDIT_STATUS,String.valueOf(this.auditStatus));
	}

	public void setAuditStatusValue(String auditStatusValue) {
		this.auditStatusValue = auditStatusValue;
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

	public Double getWarning() {
		return warning;
	}

	public void setWarning(Double warning) {
		this.warning = warning;
	}

	public Double getOpenline() {
		return openline;
	}

	public void setOpenline(Double openline) {
		this.openline = openline;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getTradeStart() {
		if (this.tradeStart==0){
			return "立即交易";
		}
		return "下个交易日";
	}

	public String getStartTimeValue() {
		if (ObjectUtil.equals(null,this.starttime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.starttime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setStartTimeValue(String startTimeValue) {
		this.startTimeValue = startTimeValue;
	}

	public BigInteger getStarttime() {
		return starttime;
	}

	public void setStarttime(BigInteger starttime) {
		this.starttime = starttime;
	}

	public void setTradeStart(int tradeStart) {
		this.tradeStart = tradeStart;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getParentAccountId() {
		return parentAccountId;
	}

	public void setParentAccountId(String parentAccountId) {
		this.parentAccountId = parentAccountId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getFailCause() {
		return failCause;
	}

	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}
}
