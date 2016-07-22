package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * @Description: TODO(配资信息VO类)
 * @ClassName: UserTradeVo
 * @author wangpinqun
 * @date 2015年1月5日 下午3:39:20
 */
public class UserTradeCmsVo implements Serializable {
	
	
	
	
	private String id;
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public BigInteger getNaturalDays() {
		return naturalDays;
	}

	public void setNaturalDays(BigInteger naturalDays) {
		this.naturalDays = naturalDays;
	}

	/**
	 * account ID
	 */
	private String accountId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * 组合号
	 */
	private String combineId;

	/**
	 * 杠杆
	 */
	private Integer lever;

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public String getCombineId() {
		return combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	/**
	 * 用户编号
	 */
	private String uid;

	/**
	 * 配资方案id
	 */
	private String userTradeId;

	/**
	 * 用户手机号码
	 */
	@AllowExcel(name = "手机号码")
	private String mobile;

	/**
	 * 用户姓名
	 */
	@AllowExcel(name = "真实姓名")
	private String uname;

	/**
	 * 恒生账户名
	 */
	@AllowExcel(name = "交易账户名")
	private String accountName;

	/**
	 * 恒生账户
	 */
	@AllowExcel(name = "交易账号")
	private String account;

	/**
	 * 配资组合号
	 */
	@AllowExcel(name = "方案编号")
	private String groupId;

	/**
	 * 恒生账户密码
	 */
	private String password;

	/**
	 * 恒生某帐号总风险保证金
	 */
	@AllowExcel(name = "配资保证金")
	private Double totalLeverMoney = 0.0;

	/**
	 * 某帐号总配资金额
	 */
	@AllowExcel(name = "配资金额")
	private Double totalLending = 0.0;

	/**
	 * 恒生某帐号总追加保证金
	 */
	@AllowExcel(name = "累计追加保证金")
	private Double totalAppendLeverMoney = 0.0;

	/**
	 * 累计提取盈利
	 */
	// @AllowExcel(name="累计提取利润")
	private Double allExtractableProfit = 0.0;

	@AllowExcel(name = "方案状态")
	private String tradeStatus;

	/**
	 * 预计自然结束天数(算利息)
	 */
	@AllowExcel(name = "配资时长")
	private String naturalDaysStr;
	
	
	private BigInteger naturalDays;

	/**
	 * 已交易天数
	 */
	@AllowExcel(name = "已使用天数")
	private Long tradingDays = 0l;
	
	
	
	
	/**
	 * 追加方案时长
	 */
	private BigInteger prolongMonth;
	@AllowExcel(name = "追加方案时长")
	private String  prolongMonthStr;
	

	@AllowExcel(name = "最短保留时长(交易日)")
	private Integer shortestDuration;

	/**
	 * 方案创建时间 导出
	 */
	@AllowExcel(name = "方案创建时间")
	private String exportAddtime;

	/**
	 * 账户类型
	 */
	@AllowExcel(name = " 账户类型")
	private String feeTypeValue;

	/**
	 * 方案开始时间 导出
	 */
	@AllowExcel(name = "方案开始时间")
	private String exportStarttime;

	/**
	 * 方案终止时间 导出
	 */
	@AllowExcel(name = "方案终止时间")
	private String exportEndtime;

	/**
	 * 预计交易结束时间 String
	 */
	@AllowExcel(name = "预计结束时间")
	private String exportEstimateEndtime;

	/**
	 * 预计交易结束时间 String
	 */
	private String estimateEndtimeString;

	/**
	 * 恒生某帐号总操盘资金
	 */
	private Double totalOperateMoney = 0.0;

	/**
	 * 亏损补仓线
	 */
	private Double warning = 0.0;

	/**
	 * 亏损平仓线
	 */
	private Double open = 0.0;

	/**
	 * 开始交易时间
	 */
	private BigInteger starttime;
	
	/**
	 * 审核通过时间
	 */
	private BigInteger auditPassTime;
	
	/**
	 * 审核通过时间String
	 */
	private String auditPassTimeStr;
	

	/**
	 * 创建时间
	 */
	private BigInteger addtime;

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
	 * 状态
	 */
	private int status;

	/**
	 * 恒生某帐号总盈亏
	 */
	private Double totalAccrual;

	/**
	 * 恒生账号开户券商
	 */
	private String hsBelongBroker;

	/**
	 * 母账户的 编号
	 */
	private String parentAccountNo;

	/**
	 * 母账户管理单元序号
	 */
	private String unitNumber;

	/**
	 * 补仓提醒通知状态
	 */
	private Short noticeStatus;

	/**
	 * 资产总值
	 */
	private double assetTotalValue = 0.0;

	/**
	 * 单元序号
	 */
	private String assetId;

	/**
	 * 最低补保证金
	 */
	private double minimumDeposit = 0.0;

	/**
	 * 买入状态false 未限制
	 */
	private Boolean buyStatus = Boolean.FALSE;

	/**
	 * 卖出状态false 未限制
	 */
	private Boolean sellStatus = Boolean.FALSE;

	/**
	 * 是否欠费false 未欠费
	 */
	private Boolean isArrearage = Boolean.FALSE;

	/**
	 * 平仓距离
	 */
	private double openDistance = 0.0;

	/**
	 * 穿仓距离
	 */
	private double wearingDistance = 0.0;

	/**
	 * 股票资产
	 */
	private double stockAssets;
	
	
	/**
	 * 现金余额
	 */
	private double cashBalance;

	/**
	 * 可提取盈利
	 */
	private double extractableProfit;

	/**
	 * 预计交易结束时间
	 */
	private BigInteger estimateEndtime;

	/**
	 * 结算金额=终结方案后总金额
	 */
	private Double finishedMoney;

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

	public String getEstimateEndtimeString() {
		return this.estimateEndtimeString;
	}

	public void setEstimateEndtimeString(String estimateEndtimeString) {
		this.estimateEndtimeString = estimateEndtimeString;
	}

	/**
	 * 利息
	 */
	private double apr = 0.0;

	/**
	 * 借款期限
	 */
	private Integer startdays = 0;

	/**
	 * 方案编号
	 */
	private String programNo;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public double getApr() {
		return apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	/**
	 * 天息（管理费）
	 */
	private Double feeDay = 0.0;

	/**
	 * 月息（利息）
	 */
	private Double feeMonth = 0.0;

	/**
	 * 0:自动开户(钱江版)，1:手工开户(钱江版)，2：手工开户(涌金版)
	 */
	private int feeType = 0;

	/**
	 * 保险单号
	 */
	private String insuranceNo;

	/**
	 * 欠费金额
	 */
	private Double arrearageMoney;

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

	public Long getTradingDays() {
		return tradingDays;
	}

	public void setTradingDays(Long tradingDays) {
		this.tradingDays = tradingDays;
	}

	public BigInteger getEstimateEndtime() {
		return estimateEndtime;
	}

	public void setEstimateEndtime(BigInteger estimateEndtime) {
		this.estimateEndtime = estimateEndtime;
	}

	public Integer getStartdays() {
		return startdays;
	}

	public void setStartdays(Integer startdays) {
		this.startdays = startdays;
	}

	public double getExtractableProfit() {
		return extractableProfit;
	}

	public void setExtractableProfit(double extractableProfit) {
		this.extractableProfit = extractableProfit;
	}

	public double getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(double cashBalance) {
		this.cashBalance = cashBalance;
	}

	public double getStockAssets() {
		return stockAssets;
	}

	public void setStockAssets(double stockAssets) {
		this.stockAssets = stockAssets;
	}

	public UserTradeCmsVo() {
		
	}

	public UserTradeCmsVo(String uid, String account, String password, Double totalLending, Double totalLeverMoney, Double totalAppendLeverMoney, Double warning, Double open, BigInteger starttime,
			BigInteger endtime, Short status, String hsBelongBroker) {
		super();
		this.uid = uid;
		this.account = account;
		this.password = password;
		this.totalLending = totalLending;
		this.totalLeverMoney = totalLeverMoney;
		this.totalAppendLeverMoney = totalAppendLeverMoney;
		this.warning = warning;
		this.open = open;
		this.starttime = starttime;
		this.endtime = endtime;
		this.status = status;
		this.hsBelongBroker = hsBelongBroker;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getTotalLending() {
		return totalLending;
	}

	public void setTotalLending(Double totalLending) {
		this.totalLending = totalLending;
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}

	public Double getTotalAppendLeverMoney() {
		return totalAppendLeverMoney;
	}

	public void setTotalAppendLeverMoney(Double totalAppendLeverMoney) {
		this.totalAppendLeverMoney = totalAppendLeverMoney;
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

	public BigInteger getStarttime() {
		return starttime;
	}

	public void setStarttime(BigInteger starttime) {
		this.starttime = starttime;
	}
 
	public BigInteger getAuditPassTime() {
		return auditPassTime;
	}

	public void setAuditPassTime(BigInteger auditPassTime) {
		this.auditPassTime = auditPassTime;
	}
    
	public String getAuditPassTimeStr() {
		if (ObjectUtil.equals(null, this.auditPassTime)) {
			auditPassTimeStr =  "";
		}else{
			auditPassTimeStr =  Dates.format(new Date(this.auditPassTime.longValue() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE);
		}
		return auditPassTimeStr;
	}

	public void setAuditPassTimeStr(String auditPassTimeStr) {
		this.auditPassTimeStr = auditPassTimeStr;
	}

	public BigInteger getEndtime() {
		return endtime;
	}

	public void setEndtime(BigInteger endtime) {
		this.endtime = endtime;
	}

	public int getStatus() {
		return this.status;
	}

	public String getTradeStatus() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.TRADE_STATUS, String.valueOf(this.status));
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Double getTotalAccrual() {
		return totalAccrual;
	}

	public void setTotalAccrual(Double totalAccrual) {
		this.totalAccrual = totalAccrual;
	}

	public String getHsBelongBroker() {
		return hsBelongBroker;
	}

	public void setHsBelongBroker(String hsBelongBroker) {
		this.hsBelongBroker = hsBelongBroker;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNoticeStatus() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.CALL_NOTICE_STATUS, String.valueOf(this.noticeStatus));
	}

	public void setNoticeStatus(Short noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getParentAccountNo() {
		return parentAccountNo;
	}

	public void setParentAccountNo(String parentAccountNo) {
		this.parentAccountNo = parentAccountNo;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public double getAssetTotalValue() {
		return assetTotalValue;
	}

	public void setAssetTotalValue(double assetTotalValue) {
		this.assetTotalValue = assetTotalValue;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public double getMinimumDeposit() {
		return minimumDeposit;
	}

	public void setMinimumDeposit(double minimumDeposit) {
		this.minimumDeposit = minimumDeposit;
	}

	public Double getTotalOperateMoney() {

		this.totalOperateMoney = BigDecimalUtils.add2(BigDecimalUtils.addRound(this.totalLending, this.totalLeverMoney), this.totalAppendLeverMoney);
		return totalOperateMoney;
	}

	public void setTotalOperateMoney(Double totalOperateMoney) {
		this.totalOperateMoney = totalOperateMoney;
	}

	public String getUserTradeId() {
		return userTradeId;
	}

	public void setUserTradeId(String userTradeId) {
		this.userTradeId = userTradeId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getBuyStatus() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.LIMIT_STATUS, String.valueOf(this.buyStatus));
	}

	public void setBuyStatus(Boolean buyStatus) {
		this.buyStatus = buyStatus;
	}

	public String getSellStatus() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.LIMIT_STATUS, String.valueOf(this.sellStatus));
	}

	public void setSellStatus(Boolean sellStatus) {
		this.sellStatus = sellStatus;
	}

	public Boolean getIsArrearage() {
		return isArrearage;
	}

	public void setIsArrearage(Boolean isArrearage) {
		this.isArrearage = isArrearage;
	}

	public String getOpenDistance() {
		if (this.stockAssets == 0) {
			return "未持仓";
		}
		this.openDistance = BigDecimalUtils.div(BigDecimalUtils.subRound(this.assetTotalValue, this.open), this.stockAssets, 2);
		return BigDecimalUtils.mulRound(this.openDistance, 100.0, 2) + "%";
	}

	public void setOpenDistance(double openDistance) {
		this.openDistance = openDistance;
	}

	public String getWearingDistance() {
		if (this.stockAssets == 0) {
			return "未持仓";
		}
		this.wearingDistance = BigDecimalUtils.div(BigDecimalUtils.subRound(this.assetTotalValue, this.totalLending), this.stockAssets, 2);
		return BigDecimalUtils.mulRound(this.wearingDistance, 100.0, 2) + "%";
	}

	public void setWearingDistance(double wearingDistance) {
		this.wearingDistance = wearingDistance;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAllExtractableProfit() {
		return allExtractableProfit;
	}

	public void setAllExtractableProfit(Double allExtractableProfit) {
		this.allExtractableProfit = allExtractableProfit;
	}

	public BigInteger getAddtime() {
		return addtime;
	}

	public void setAddtime(BigInteger addtime) {
		this.addtime = addtime;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 补仓提醒上次通知时间
	 */
	private BigInteger lastNoticeTime;

	public BigInteger getLastNoticeTime() {
		return lastNoticeTime;
	}

	public void setLastNoticeTime(BigInteger lastNoticeTime) {
		this.lastNoticeTime = lastNoticeTime;
	}

	public String getExportAddtime() {
		if (ObjectUtil.equals(null, this.addtime)) {
			return "";
		}
		return Dates.format(new Date(this.addtime.longValue() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE);

	}

	public void setExportAddtime(String exportAddtime) {
		this.exportAddtime = exportAddtime;
	}

	public String getExportStarttime() {
		if (ObjectUtil.equals(null, this.starttime)) {
			return "";
		}
		return Dates.format(new Date(this.starttime.longValue() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE);

	}

	public void setExportStarttime(String exportStarttime) {
		this.exportStarttime = exportStarttime;
	}

	public String getExportEndtime() {
		if (ObjectUtil.equals(null, this.endtime)) {
			return "";
		}
		return Dates.format(new Date(this.endtime.longValue() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE);

	}

	public void setExportEndtime(String exportEndtime) {
		this.exportEndtime = exportEndtime;
	}

	/**
	 * 
	 * 开户审核状态------ 0：开户待审核 1：开户通过（变为操盘中） 2：审核不通过 （开户失败）
	 */
	private Integer auditStatus;

	/**
	 * 终结审核状态----- 0：终结待审核 1：方案终结审核通过 2：方案终结审核不通过
	 */
	private Integer auditEndStatus;

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

	public int getFeeType() {
		return feeType;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	private String estimatedTime;

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public Double getArrearageMoney() {
		return arrearageMoney;
	}

	public void setArrearageMoney(Double arrearageMoney) {
		this.arrearageMoney = arrearageMoney;
	}

	/**
	 * 用户类型（0、1、2）
	 */
	private int userType;

	/**
	 * 用户类型str（普通用户、8800用户、6600用户）
	 */
	private String userTypeStr;

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserTypeStr() {
		return userTypeStr = 1 == userType ? "8800用户" : 2 == userType ? "6600用户" : "普通用户";
	}

	public void setUserTypeStr(String userTypeStr) {
		this.userTypeStr = userTypeStr;
	}

	public Integer getShortestDuration() {
		if (shortestDuration == null) {
			this.shortestDuration = 0;
		}
		return shortestDuration;
	}

	public void setShortestDuration(Integer shortestDuration) {
		this.shortestDuration = shortestDuration;
	}

	public String getFeeTypeValue() {
		String tempFeeType = String.valueOf(this.feeType);
		if (this.feeType == Constant.FeeType.AUTO_OPEN_CASH_RIVER || this.feeType == Constant.FeeType.HAND_OPEN_CASH_RIVER) {
			tempFeeType = Constant.FeeType.CASH_RIVER_FEETYPE;
		}
		return CacheManager.getDataMapByKey(DataDicKeyConstants.TRADE_FEETYPE, tempFeeType);
	}

	public void setFeeTypeValue(String feeTypeValue) {
		this.feeTypeValue = feeTypeValue;
	}

	public String getExportEstimateEndtime() {
		if (ObjectUtil.equals(null, this.estimateEndtime)) {
			return "";
		}
		return Dates.format(new Date(this.estimateEndtime.longValue() * 1000), Dates.CHINESE_DATE_FORMAT_LINE);
	}

	public void setExportEstimateEndtime(String exportEstimateEndtime) {
		this.exportEstimateEndtime = exportEstimateEndtime;
	}

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
	@AllowExcel(name = "方案类型")
	private String activityTypeStr;

	public String getActivityTypeStr() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.ACTIVITY_TYPE, String.valueOf(this.activityType));
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
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
	 * 实收利息
	 */
	private Double dapr;

	public Double getDapr() {
		return dapr;
	}

	public void setDapr(Double dapr) {
		
		this.dapr = dapr;
		
	}

	public BigInteger getProlongMonth() {
		return prolongMonth;
	}

	public void setProlongMonth(BigInteger prolongMonth) {
		this.prolongMonth = prolongMonth;
	}

	public String getNaturalDaysStr() {
		if (UserTrade.ActivityType.MONTH_TRADE==activityType  
				&& !ObjectUtil.equals(null,this.naturalDays)){
			return  (this.naturalDays.intValue()/Constant.MONTH_TRADE_MONTH_DAYS)+"月";
		}
		return naturalDays+"天";
	}

	public void setNaturalDaysStr(String naturalDaysStr) {
		this.naturalDaysStr = naturalDaysStr;
	}

	public String getProlongMonthStr() {
		if (UserTrade.ActivityType.MONTH_TRADE==activityType  
				&& !ObjectUtil.equals(null,this.prolongMonth)
				&& this.prolongMonth.intValue()>Constant.FtogetherGame.ZERO){
			return prolongMonth+"个月";
		}
		return prolongMonthStr;
	}

	public void setProlongMonthStr(String prolongMonthStr) {
		this.prolongMonthStr = prolongMonthStr;
	}

	
}