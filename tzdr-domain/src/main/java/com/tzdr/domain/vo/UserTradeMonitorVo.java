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

/**
 * @Description: TODO(配资信息VO类)
 * @ClassName: UserTradeVo
 * @author wangpinqun
 * @date 2015年1月5日 下午3:39:20
 */
public class UserTradeMonitorVo implements Serializable {

	private String id;
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * account ID
	 */
	private String accountId;

	/**
	 * 组合号
	 */
	private String combineId;

	/**
	 * 杠杆
	 */
	private Integer lever;

	/**
	 * 活动类型 0：没有活动，1：8800活动
	 */
	private int activityType;

	/**
	 * 用户编号
	 */
	private String uid;

	/**
	 * 配资方案id
	 */
	private String userTradeId;

	/**
	 * 恒生账户名
	 */
	@AllowExcel(name = "恒生账户名")
	private String accountName;

	/**
	 * 恒生账户
	 */
	@AllowExcel(name = "恒生账号")
	private String account;

	/**
	 * 用户手机号码
	 */
	@AllowExcel(name = "手机号码")
	private String mobile;

	/**
	 * 用户姓名
	 */
	@AllowExcel(name = "用户姓名")
	private String uname;

	/**
	 * 恒生某帐号总风险保证金
	 */
	@AllowExcel(name = "风险保证金")
	private Double totalLeverMoney = 0.0;

	/**
	 * 某帐号总配资金额
	 */
	@AllowExcel(name = "配资金额")
	private Double totalLending = 0.0;

	/**
	 * 恒生某帐号总操盘资金
	 */
	@AllowExcel(name = "总操盘资金")
	private Double totalOperateMoney = 0.0;

	/**
	 * 亏损补仓线
	 */
	@AllowExcel(name = "亏损补仓线")
	private Double warning = 0.0;

	/**
	 * 亏损平仓线
	 */
	@AllowExcel(name = "亏损平仓线")
	private Double open = 0.0;

	/**
	 * 资产总值
	 */
	@AllowExcel(name = "资产总值")
	private double assetTotalValue = 0.0;
	
	/**
	 * 股票资产
	 */
	@AllowExcel(name="持仓市值")
	private double stockAssets;
	
	/**
	 * 持仓占比
	 * 持仓占比算法：[(持仓市值/资产总值)X100]%
	 */
	@AllowExcel(name="持仓占比")
	private Double stockAssetsProportion=0.0;

	/**
	 * 平仓距离
	 */
	@AllowExcel(name = "平仓距离")
	private String openDistanceValue;

	/**
	 * 穿仓距离
	 */
	@AllowExcel(name = "穿仓距离")
	private String wearingDistanceValue;

	@AllowExcel(name = "买入状态")
	private String buyStatusValue;

	@AllowExcel(name = "卖出状态")
	private String sellStatusValue;

	/**
	 * 配资组合号（方案编号）
	 */
	private String groupId;

	/**
	 * 恒生账户密码
	 */
	private String password;

	/**
	 * 恒生某帐号总追加保证金
	 */
	private Double totalAppendLeverMoney = 0.0;

	/**
	 * 累计提取盈利
	 */
	private Double allExtractableProfit = 0.0;

	/**
	 * 方案状态
	 */
	private String tradeStatus;

	/**
	 * 预计自然结束天数(算利息)
	 */
	private BigInteger naturalDays;

	/**
	 * 已交易天数
	 */
	private Long tradingDays = 0l;

	/**
	 * 方案创建时间 导出
	 */
	private String exportAddtime;

	/**
	 * 方案开始时间 导出
	 */
	private String exportStarttime;

	/**
	 * 方案终止时间 导出
	 */
	private String exportEndtime;

	/**
	 * 平仓距离
	 */
	private double openDistance = 0.0;

	/**
	 * 穿仓距离
	 */
	private double wearingDistance = 0.0;

	/**
	 * 开始交易时间
	 */
	private BigInteger starttime;

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
	 * 预计交易结束时间 String
	 */
	private String estimateEndtimeString;

	/**
	 * 结算金额=终结方案后总金额
	 */
	private Double finishedMoney;

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

	/**
	 * 天息（管理费）
	 */
	private Double feeDay = 0.0;

	/**
	 * 月息（利息）
	 */
	private Double feeMonth = 0.0;

	/**
	 * 补仓提醒上次通知时间
	 */
	private BigInteger lastNoticeTime;
	
	/**
	 * 实时的方案盈亏（=资产总值-总操盘资金）
	 */
	@AllowExcel(name="方案盈亏")
	private Double tradeAccrual=0.0;
	
	/**
	 * 是否穿仓
	 */
	@AllowExcel(name="是否穿仓")
	private String isBreakStore;

	public UserTradeMonitorVo() {

	}

	public UserTradeMonitorVo(String uid, String account, String password,
			Double totalLending, Double totalLeverMoney,
			Double totalAppendLeverMoney, Double warning, Double open,
			BigInteger starttime, BigInteger endtime, Short status,
			String hsBelongBroker) {
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
		return CacheManager.getDataMapByKey(DataDicKeyConstants.TRADE_STATUS,
				String.valueOf(this.status));
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
		return CacheManager.getDataMapByKey(
				DataDicKeyConstants.CALL_NOTICE_STATUS,
				String.valueOf(this.noticeStatus));
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

		this.totalOperateMoney = BigDecimalUtils.add2(BigDecimalUtils.addRound(
				this.totalLending, this.totalLeverMoney),
				this.totalAppendLeverMoney);
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
		return CacheManager.getDataMapByKey(DataDicKeyConstants.LIMIT_STATUS,
				String.valueOf(this.buyStatus));
	}

	public void setBuyStatus(Boolean buyStatus) {
		this.buyStatus = buyStatus;
	}

	public String getSellStatus() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.LIMIT_STATUS,
				String.valueOf(this.sellStatus));
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
		this.openDistance = BigDecimalUtils.div(
				BigDecimalUtils.subRound(this.assetTotalValue, this.open),
				this.stockAssets, 2);
		return BigDecimalUtils.mulRound(this.openDistance, 100.0, 2) + "%";
	}

	public void setOpenDistance(double openDistance) {
		this.openDistance = openDistance;
	}

	public String getWearingDistance() {
		if (this.stockAssets == 0) {
			return "未持仓";
		}
		this.wearingDistance = BigDecimalUtils.div(BigDecimalUtils.subRound(
				this.assetTotalValue, this.totalLending), this.stockAssets, 2);
		return BigDecimalUtils.mulRound(this.wearingDistance, 100.0, 2) + "%";
	}

	public String getOpenDistanceValue() {
		return this.getOpenDistance();
	}

	public void setOpenDistanceValue(String openDistanceValue) {
		this.openDistanceValue = openDistanceValue;
	}

	public String getWearingDistanceValue() {
		return this.getWearingDistance();
	}

	public void setWearingDistanceValue(String wearingDistanceValue) {
		this.wearingDistanceValue = wearingDistanceValue;
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
		return Dates.format(new Date(this.addtime.longValue() * 1000),
				Dates.CHINESE_DATETIME_FORMAT_LINE);

	}

	public void setExportAddtime(String exportAddtime) {
		this.exportAddtime = exportAddtime;
	}

	public String getExportStarttime() {
		if (ObjectUtil.equals(null, this.starttime)) {
			return "";
		}
		return Dates.format(new Date(this.starttime.longValue() * 1000),
				Dates.CHINESE_DATE_FORMAT_LINE);

	}

	public void setExportStarttime(String exportStarttime) {
		this.exportStarttime = exportStarttime;
	}

	public String getExportEndtime() {
		if (ObjectUtil.equals(null, this.endtime)) {
			return "";
		}
		return Dates.format(new Date(this.endtime.longValue() * 1000),
				Dates.CHINESE_DATETIME_FORMAT_LINE);

	}

	public void setExportEndtime(String exportEndtime) {
		this.exportEndtime = exportEndtime;
	}

	public String getBuyStatusValue() {
		return this.getBuyStatus();
	}

	public void setBuyStatusValue(String buyStatusValue) {
		this.buyStatusValue = buyStatusValue;
	}

	public String getSellStatusValue() {
		return this.getSellStatus();
	}

	public void setSellStatusValue(String sellStatusValue) {
		this.sellStatusValue = sellStatusValue;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public BigInteger getNaturalDays() {
		return naturalDays;
	}

	public void setNaturalDays(BigInteger naturalDays) {
		this.naturalDays = naturalDays;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

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

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

	public String getEstimateEndtimeString() {
		return estimateEndtimeString;
	}

	public void setEstimateEndtimeString(String estimateEndtimeString) {
		this.estimateEndtimeString = estimateEndtimeString;
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

	public Double getTradeAccrual() {
		this.tradeAccrual=BigDecimalUtils.subRound(getAssetTotalValue(), getTotalOperateMoney());
		return this.tradeAccrual;
	}

	public void setTradeAccrual(Double tradeAccrual) {
		this.tradeAccrual = tradeAccrual;
	}

	public Double getStockAssetsProportion() {
		return stockAssetsProportion;
	}

	public void setStockAssetsProportion(Double stockAssetsProportion) {
		this.stockAssetsProportion = stockAssetsProportion;
	}

	public String getIsBreakStore() {
		return isBreakStore;
	}

	public void setIsBreakStore(String isBreakStore) {
		this.isBreakStore = isBreakStore;
	}
}
