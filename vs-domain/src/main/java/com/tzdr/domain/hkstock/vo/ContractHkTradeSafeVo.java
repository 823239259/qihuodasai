package com.tzdr.domain.hkstock.vo;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.tzdr.domain.web.entity.WUser;


public class ContractHkTradeSafeVo {
	// 活动类型  活动类型 0：没有活动，1：8800活动
	public static class ActivityType {
		/**
		 * 没有活动
		 */
		public static final  int NO = 0;
		/**
		* 8800活动
		*/
		public static final int ACTIVITY_8800  = 1;
			
	}
	/**
	 * 0:按天配资(随心配)，1:按月配资,2:追加按天配资
	 */ 
	private Short type;
	private Short feeType;
	private Double feeLever=0.0;
	private Integer feePayDay;
	
	/**
	*帐户管理费收取方式：1下个交易日，0本交易日（立刻生效）
	*/	
	private Short tradeStart;
	/**
	*(配资金额)公司出的钱
	*/
	private Double money=0.0;
	/**
	 * 首次总利息
	*/
	private Double apr=0.0;
	/**
	 * 结算金额=终结方案后总金额
	*/
	private Double finishedMoney=0.0;
	/**
	 * 赢亏多少(+-)（在配资过程中是0,终结方案后会记录本次的赢亏）
	*/
	private Double accrual=0.0;
	/**
	 * 亏损警告线,亏损补仓线
	*/
	private Double warning=0.0;
	/**
	 * 亏损平仓线
	*/
	private Double open=0.0;
	/**
	 * 比例（目前没有用）
	*/
	private Double interest=0.0;
	
	/**
	 *  放大倍数，杠杆
	*/
	private Integer lever;
	/**
	 * 风险保证金(配资保证金)用户出的钱
	*/
	private Double leverMoney=0.0;
	/**
	 * 配资创建时间（和开始交易时间不同）
	*/
	private Long addtime;
	/**
	 * 配资终结时间
	*/
	private Long endtime;
	/**
	 * 配资开始交易时间
	*/
	private Long starttime;
	/**
	 * 配资交易天数（交易日）
	*/
	private Integer startdays;
	/**
	 * 审核通过时间
	*/
	private Long auditime;
	/**
	 * 配资周期（为月配资准备）
	*/
	private Long deadline;
	/**
	 * 预计交易结束时间
	 */
	private Long estimateEndtime;
	
	/**
	 * 预计自然结束天数(算利息)
	 */
	private Long  naturalDays;
	

	public Long getNaturalDays() {
		return naturalDays;
	}

	public void setNaturalDays(Long naturalDays) {
		this.naturalDays = naturalDays;
	}

	/**
	 * 天息（管理费）
	 */
	private Double feeDay=0.0;

	/**
	 * 月息（利息）一天的利息
	 */
	private Double feeMonth=0.0;
	/**
	 * 单元序号
	 */
	private String assetId;
	/**
	 * 组合编号
	 */
	private String combineId;
	/**
	 * 恒生账户
	 */
	private String account;
	/**
	 * 恒生账户密码
	 */
	private String password;
	
	/**
	 * 0：验资中，1操盘中，2方案结束（终结）
	 */
	private Short status;
	
	/**
	 * 买入状态false 未限制
	 */
	private Boolean buyStatus=Boolean.FALSE;
	
	/**
	 * 卖出状态false 未限制
	 */
	private Boolean sellStatus=Boolean.FALSE;
	
	/**
	 * 补仓提醒通知 状态
	 * 0:未通知
	 * 1:已通知
	 * 2:未接通
	 */
	private Short noticeStatus=0;

	/**
	 * 追加保证金
	 */
	private Double appendLeverMoney=0.0;
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
	 * 总操盘资金
	 */
	private Double totalLeverMoney=0.0;
	
	/**
	 * 配资组合号
	 */
	private String groupId;
	
	/**
	 * 预警处理时间 //预警值标记 0：未预警 1：未预警
	 */
	private Long warningProcessTime;
	
	/**
	 * 方案号随机8位
	 */
	private String programNo;
	/**
	 * 划款状态：0:终结划款失败、1 终结划款成功
	 */
	private Integer transferState;
	
	
	/**
	 * Account ID
	 */
	private String accountId;
	
	
	/**
	 * 活动类型 0：没有活动，1：8800活动
	 */
	private int activityType=0;
	
	/**
	 * 拥金状态 1：为成功 、2 为失败
	 */
	private String commissionState;
	
	//合同保存日期
	private String seckey;//数字指纹;
	private String savedate; //保存日期
	private Long saveid;//保全id 
	private String linkUrl;//链接地址
	
	public String getSeckey() {
		return seckey;
	}

	public void setSeckey(String seckey) {
		this.seckey = seckey;
	}

	public String getSavedate() {
		return savedate;
	}

	public void setSavedate(String savedate) {
		this.savedate = savedate;
	}

	public Long getSaveid() {
		return saveid;
	}

	public void setSaveid(Long saveid) {
		this.saveid = saveid;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}


	public String getCommissionState() {
		return commissionState;
	}

	public void setCommissionState(String commissionState) {
		this.commissionState = commissionState;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	
	
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	private Integer warningValueTag = 0;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)               
	@JoinColumn(name="uid", nullable=true)
	private WUser wuser;

	
	@Column(name="type")
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name="fee_type")
	public Short getFeeType() {
		return this.feeType;
	}

	public void setFeeType(Short feeType) {
		this.feeType = feeType;
	}

	@Column(name="fee_lever")
	public Double getFeeLever() {
		return this.feeLever;
	}

	public void setFeeLever(Double feeLever) {
		this.feeLever = feeLever;
	}

	@Column(name="fee_pay_day")
	public Integer getFeePayDay() {
		return this.feePayDay;
	}

	public void setFeePayDay(Integer feePayDay) {
		this.feePayDay = feePayDay;
	}

	@Column(name="tradeStart")
	public Short getTradeStart() {
		return this.tradeStart;
	}

	public void setTradeStart(Short tradeStart) {
		this.tradeStart = tradeStart;
	}

	@Column(name="money")
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name="apr")
	public Double getApr() {
		return this.apr;
	}

	public void setApr(Double apr) {
		this.apr = apr;
	}

	@Column(name="finished_money")
	public Double getFinishedMoney() {
		return this.finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

	@Column(name="accrual")
	public Double getAccrual() {
		return this.accrual;
	}

	public void setAccrual(Double accrual) {
		this.accrual = accrual;
	}

	@Column(name="warning")
	public Double getWarning() {
		return this.warning;
	}

	public void setWarning(Double warning) {
		this.warning = warning;
	}

	@Column(name="open")
	public Double getOpen() {
		return this.open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	@Column(name="interest")
	public Double getInterest() {
		return this.interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	@Column(name="lever")
	public Integer getLever() {
		return this.lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	@Column(name="lever_money")
	public Double getLeverMoney() {
		return this.leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	@Column(name="addtime")
	public Long getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	@Column(name="endtime")
	public Long getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	@Column(name="starttime")
	public Long getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}

	@Column(name="startdays")
	public Integer getStartdays() {
		return this.startdays;
	}

	public void setStartdays(Integer startdays) {
		this.startdays = startdays;
	}

	@Column(name="auditime")
	public Long getAuditime() {
		return this.auditime;
	}

	public void setAuditime(Long auditime) {
		this.auditime = auditime;
	}

	@Column(name="deadline")
	public Long getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}

	@Column(name="fee_day")
	public Double getFeeDay() {
		return this.feeDay;
	}

	public void setFeeDay(Double feeDay) {
		this.feeDay = feeDay;
	}

	@Column(name="fee_month")
	public Double getFeeMonth() {
		return this.feeMonth;
	}

	public void setFeeMonth(Double feeMonth) {
		this.feeMonth = feeMonth;
	}

	@Column(name="asset_id")
	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Column(name="combine_id")
	public String getCombineId() {
		return this.combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	@Column(name="account")
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name="password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public WUser getWuser() {
		return wuser;
	}

	public void setWuser(WUser wuser) {
		this.wuser = wuser;
	}

	@Column(name="append_lever_money")
	public double getAppendLeverMoney() {
		return this.appendLeverMoney;
	}

	public void setAppendLeverMoney(Double appendLeverMoney) {
		this.appendLeverMoney = appendLeverMoney;
	}

	@Column(name="hs_belong_broker")
	public String getHsBelongBroker() {
		return this.hsBelongBroker;
	}

	public void setHsBelongBroker(String hsBelongBroker) {
		this.hsBelongBroker = hsBelongBroker;
	}

	public Short getNoticeStatus() {
		return noticeStatus;
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

	

	public Boolean getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(Boolean buyStatus) {
		this.buyStatus = buyStatus;
	}

	public Boolean getSellStatus() {
		return sellStatus;
	}

	public void setSellStatus(Boolean sellStatus) {
		this.sellStatus = sellStatus;
	}

	public Long getEstimateEndtime() {
		return estimateEndtime;
	}

	public void setEstimateEndtime(Long estimateEndtime) {
		this.estimateEndtime = estimateEndtime;
	}

	public Long getWarningProcessTime() {
		return warningProcessTime;
	}

	public void setWarningProcessTime(Long warningProcessTime) {
		this.warningProcessTime = warningProcessTime;
	}

	public Integer getWarningValueTag() {
		return warningValueTag;
	}

	public void setWarningValueTag(Integer warningValueTag) {
		this.warningValueTag = warningValueTag;
	}

	@Column
	public Integer getTransferState() {
		return transferState;
	}

	public void setTransferState(Integer transferState) {
		this.transferState = transferState;
	}
}
