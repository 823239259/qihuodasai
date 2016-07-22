package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 手工开户 Vo
 * @author zhouchen
 * 2015年4月27日 上午11:23:50
 */
public class WellGoldHandVo implements Serializable {
	
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
	private String account;
	
	

	/**
	 * 恒生账户名
	 */
	@AllowExcel(name="交易账户名")
	private String accountName;
	
	
	
	/**
	 * 配资组合号
	 */
	@AllowExcel(name="方案编号")
	private String groupId;
	
	/**
	 * 单元序号
	 */
	private String assetId;
	
	/**
	 * 母帐号对应交易通道类型。0：钱江版，1：涌金版，2：同花顺
	 */
	private Integer accountGenre;
	
	/**
	 * 保证金
	 */
	@AllowExcel(name="配资保证金")
	private Double leverMoney;
	
	/**
	 * 配资金额
	 */
	@AllowExcel(name="配资金额")
	private Double money;
	
	
	/**
	 * 总操盘金额
	 */
	@AllowExcel(name="总操盘金额")
	private Double totalLeverMoney;
	
	/**
	 * 补仓线
	 */
	@AllowExcel(name="亏损补仓线")
	private Double  warning;
	
	
	/**
	 * 补仓线
	 */
	@AllowExcel(name="亏损平仓线")
	private Double  openline;
	
	
	/**
	 * 补仓线
	 */
	@AllowExcel(name="账户类型")
	private int  feeType;
	
	
	/**
	 * 审核显示状态 0:未审核 1：通过 2：未通过
	 */
	@AllowExcel(name="审核状态")
	private String  auditStatusValue;
	
	
	/**
	 * 创建时间 显示
	 */
	@AllowExcel(name="提交时间")
	private String  createTimeValue;
	
	

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
	
	
	/**
	 * 开始交易时间
	 */
	private int  tradeStart;
	
	/**
	 * 交易账户密码
	 */
	private String password;
	
	
	/**
	 * 母账户id
	 */
	private String  parentAccountId;
	
	
	/**
	 * 创建时间
	 */
	private BigInteger  createTime;
	
	
	
	/**
	 * 审核时间
	 */
	private BigInteger  auditTime;
	
	
	/**
	 * 审核状态 0:未审核 1：通过 2：未通过
	 */
	private int  auditStatus;
	
	/**
	 * 保险单号
	 */
	private String insuranceNo;

	/**
	 * 活动类型 0：没有活动，1：8800活动
	 */
	private int activityType;
	
	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	/**
	 * 方案类型
	 */
	@AllowExcel(name="方案类型")
	private String activityTypeStr;
	
	public String getActivityTypeStr() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.ACTIVITY_TYPE,String.valueOf(this.activityType));
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}

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

	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getTradeStart() {
		if (this.tradeStart==0){
			return "立即交易";
		}
		return "下个交易日";
	}

	public void setTradeStart(int tradeStart) {
		this.tradeStart = tradeStart;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getFeeType() {
		if(this.auditStatus==1){
			return CacheManager.getDataMapByKey(DataDicKeyConstants.TRADE_FEETYPE, this.feeType == 0 || this.feeType == 1 ? "0,1": String.valueOf(this.feeType));
		}
		return null;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public Integer getAccountGenre() {
		return accountGenre;
	}

	public void setAccountGenre(Integer accountGenre) {
		this.accountGenre = accountGenre;
	}
	
	/**
	 * 是否是第一个股票配资 0:不是 1：是(不包括合买)
	 */
	private Integer newStatus = 0;
	@AllowExcel(name="是否首个配资")
	private String newStatusStr;
	public Integer getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Integer newStatus) {
		this.newStatus = newStatus;
	}

	public String getNewStatusStr() {
		if(this.newStatus!=null){
			if(this.newStatus == 1){
				this.newStatusStr ="是";
			}else{
				this.newStatusStr ="否";
			}
		}
		return newStatusStr;
	}

	public void setNewStatusStr(String newStatusStr) {
		this.newStatusStr = newStatusStr;
	}
	
	/**
	 * 代金券使用金额
	 */
	private BigDecimal voucherActualMoney;

	public BigDecimal getVoucherActualMoney() {
		return voucherActualMoney;
	}

	public void setVoucherActualMoney(BigDecimal voucherActualMoney) {
		this.voucherActualMoney = voucherActualMoney;
	}
	
	/**
	 * 账户类型
	 */
	private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
