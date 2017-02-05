package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

public class FSimpleUserTradeVo implements Serializable{

	
	private static final long serialVersionUID = -5990266616970178037L;
	
	@SqlColumn
	private String id;
	/**
	 * 用户编号
	 */
	
	private String uid;
	
	/**
	 * 手机号码
	 */
	@AllowExcel(name="手机号码")
	@SqlColumn(name="mobile")
	private String mobile;
	
	/**
	 * 客户姓名
	 */
	@AllowExcel(name="客户姓名")
	@SqlColumn(name="tname")
	private String tname;
	
	/**
	 * 交易账号
	 */
	@AllowExcel(name="股指期货账户")
	@SqlColumn(name="tran_account")
	private String tranAccount;
	
	@AllowExcel(name="账户启用日期")
	private String appStarttimeStr;
	
	
	@AllowExcel(name="账户结束日期")
	private String accountEndTimeStr;
	
	/**
	 * 交易日天数
	 */
	@AllowExcel(name="申请操盘时间")
	@SqlColumn(name="tran_day")
	private Integer tranDay;
	
	/**
	 * 已操盘时间
	 */
	@AllowExcel(name="已操盘时间")
	@SqlColumn(name="use_tran_day")
	private Integer useTradeDay;
	
	/**
	 * 操盘保证金
	 */
	@SqlColumn(name="trader_bond")
	@AllowExcel(name="操盘保证金")
	private BigDecimal traderBond;

	/**
	 * 总操盘金额
	 */
	@SqlColumn(name="trader_total")
	@AllowExcel(name="总操盘金额")
	private BigDecimal traderTotal;
	
	/**
	 * 融资金额
	 */
	@AllowExcel(name="融资金额")
	private BigDecimal traderMoney;
	
	/**
	 * 手数
	 */
	@AllowExcel(name="可开仓手数")
	@SqlColumn(name="tran_lever")
	private Integer tranLever;
	
	/**
	 * 追加保证金额
	 */
	@SqlColumn(name="append_trader_bond")
	@AllowExcel(name="补充保证金")
	private BigDecimal appendTraderBond;
	
	/**
	 * 交易盈亏
	 */
	@SqlColumn(name="tran_profit_loss")
	@AllowExcel(name="交易盈亏")
	private BigDecimal tranProfitLoss;
	
	/**
	 * 交易佣金
	 */
	@SqlColumn(name="tran_commission")
	@AllowExcel(name="交易佣金")
	private BigDecimal tranCommission;
	
	/**
	 * 实际使用管理费
	 */
	@AllowExcel(name="已使用管理费")
	private BigDecimal useFeeManage;
	
	/**
	 * 返还管理费
	 */
	@SqlColumn(name="return_fee_manage")
	@AllowExcel(name="返还管理费")
	private BigDecimal returnFeeManage;
	
	/**
	 * 结算金额
	 */
	@SqlColumn(name="end_amount")
	@AllowExcel(name="结算金额")
	private BigDecimal endAmount;
	
	@AllowExcel(name="结算时间")
	private String endTimeStr;
	
	/**
	 * 方案号TG+ID号
	 */
	@SqlColumn(name="program_no")
	@AllowExcel(name="方案编号")
	private String programNo;
	
	@AllowExcel(name="结算状态")
	private String stateTypeStrTwo; //账户结算时状态显示名称
	
	/**
	 * 帐号结束日期
	 */
	private String appEndtimeStr;
	
	
	/**
	 * 倒计时间（根据交易日天数计算出结束时间）
	 */
	private String appCountdowntimeStr;
	
	/**
	 * 亏损平仓线
	 */
	@SqlColumn(name="line_loss")
	private BigDecimal lineLoss;

	/**
	 * 账户管理费
	 */
	@SqlColumn(name="fee_manage")
	private BigDecimal feeManage;

	/**
	 * 交易手续费
	 */
	private BigDecimal tranFees;

	/**
	 * 申请时间
	 */
	@SqlColumn(name="app_time")
	private Long appTime;
	private String appTimeStr;
	/**
	 * 开始时间（使用日期）
	 */
	@SqlColumn(name="app_starttime")
	private Long appStarttime;
	
	/**
	 * 结束时间（根据交易日天数计算出结束时间）
	 */
	@SqlColumn(name="app_endtime")
	private Long appEndtime;
	
	/**
	 * 账号密码
	 */
	@SqlColumn(name="tran_password")
	private String tranPassword;
	/**
	 * 状态【1.审核中、-1.未通过、2.操盘中、3.已完结、4.申请终结】
	 */
	@SqlColumn(name="state_type")
	private Integer stateType;
	private String stateTypeStrOne; //账号审核时状态显示名称
	
	/**
	 * 结算时间
	 */
	@SqlColumn(name="end_time")
	private Long endTime;
	
	/**
	 * 业务类型
	 */
	@SqlColumn(name="business_type")
	private Integer businessType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProgramNo() {
		return programNo;
	}
	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}
	public BigDecimal getTraderTotal() {
		return traderTotal;
	}
	public void setTraderTotal(BigDecimal traderTotal) {
		this.traderTotal = traderTotal;
	}
	public BigDecimal getTraderBond() {
		if(this.businessType != null && this.businessType == 1){
			traderBond = this.tranLever == null || this.tranLever <= 0 ? traderBond : this.traderBond.multiply(new BigDecimal(this.tranLever), MathContext.DECIMAL32);
		}
		return traderBond;
	}
	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}
	public BigDecimal getLineLoss() {
		return lineLoss;
	}
	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
	}
	public BigDecimal getFeeManage() {
		return feeManage;
	}
	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}
	public BigDecimal getTranFees() {
		return tranFees;
	}
	public void setTranFees(BigDecimal tranFees) {
		this.tranFees = tranFees;
	}
	public Integer getTranDay() {
		return tranDay;
	}
	public void setTranDay(Integer tranDay) {
		this.tranDay = tranDay;
	}
	public Long getAppTime() {
		return appTime;
	}
	public void setAppTime(Long appTime) {
		this.appTime = appTime;
	}
	public String getAppTimeStr() {
		if(appTime!=null){
			appTimeStr=TypeConvert.long1000ToDatetimeStr(this.appTime);
		}
		return appTimeStr;
	}
	public void setAppTimeStr(String appTimeStr) {
		this.appTimeStr = appTimeStr;
	}
	public Long getAppStarttime() {
		return appStarttime;
	}
	public void setAppStarttime(Long appStarttime) {
		this.appStarttime = appStarttime;
	}
	public String getAppStarttimeStr() {
		if(appStarttime!=null){
			appStarttimeStr=TypeConvert.long1000ToDateStr(this.appStarttime);
		}
		return appStarttimeStr;
	}
	public void setAppStarttimeStr(String appStarttimeStr) {
		this.appStarttimeStr = appStarttimeStr;
	}
	public Long getAppEndtime() {
		return appEndtime;
	}
	public void setAppEndtime(Long appEndtime) {
		this.appEndtime = appEndtime;
	}
	public String getAppEndtimeStr() {
		if(appEndtime!=null){
			appEndtimeStr=TypeConvert.long1000ToDateStr(this.appEndtime);
		}
		return appEndtimeStr;
	}
	public void setAppEndtimeStr(String appEndtimeStr) {
		this.appEndtimeStr = appEndtimeStr;
	}
	public String getAppCountdowntimeStr() {
		
		if(appEndtime!=null){
			long endTime = appEndtime+3600*24-1;
			long nowDate =  TypeConvert.dbDefaultDate();
			long between_days=(endTime-nowDate)/(3600*24);  
			Integer day=null;
			day = Integer.parseInt(String.valueOf(between_days));    
			if(day >= 0){
				appCountdowntimeStr =day.toString()+"天";
			}else{
				appCountdowntimeStr ="已过期";
			}
		}
		return appCountdowntimeStr;
	}
	public void setAppCountdowntimeString(String appCountdowntimeStr) {
		this.appCountdowntimeStr = appCountdowntimeStr;
	}
	public String getTranAccount() {
		return tranAccount;
	}
	public void setTranAccount(String tranAccount) {
		this.tranAccount = tranAccount;
	}
	public String getTranPassword() {
		return tranPassword;
	}
	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}
	public Integer getStateType() {
		return stateType;
	}
	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}
	
	public String getStateTypeStrOne() {
		if(stateType!=null){
			if(stateType==1){
				stateTypeStrOne="待审核";
			}else if(stateType==-1){
				stateTypeStrOne="申请已拒绝";
			}else if(stateType==2){
				stateTypeStrOne="操盘中";
			}else{
				stateTypeStrOne="";
			}
		}
		return stateTypeStrOne;
	}
	public void setStateTypeStrOne(String stateTypeStrOne) {
		this.stateTypeStrOne = stateTypeStrOne;
	}
	public String getStateTypeStrTwo() {
		if(stateType!=null){
			if(stateType==2){
				stateTypeStrTwo="待结算";
			}else if(stateType==3){
				stateTypeStrTwo="已结算";
			}else if(stateType==4){
				stateTypeStrTwo="申请结算";
			}else{
				stateTypeStrTwo="";
			}
		}
		return stateTypeStrTwo;
	}
	public void setStateTypeStrTwo(String stateTypeStrTwo) {
		this.stateTypeStrTwo = stateTypeStrTwo;
	}
	public BigDecimal getTranCommission() {
		return tranCommission;
	}
	public void setTranCommission(BigDecimal tranCommission) {
		this.tranCommission = tranCommission;
	}
	public BigDecimal getTranProfitLoss() {
		return tranProfitLoss;
	}
	public void setTranProfitLoss(BigDecimal tranProfitLoss) {
		this.tranProfitLoss = tranProfitLoss;
	}
	public BigDecimal getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}
	public BigDecimal getAppendTraderBond() {
		return appendTraderBond;
	}
	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getEndTimeStr() {
		if(endTime!=null){
			endTimeStr=TypeConvert.dateToDatetimeStr(new Date(this.endTime*1000));
		}
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public Integer getTranLever() {
		return tranLever;
	}
	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}
	public BigDecimal getTraderMoney() {
		return new BigDecimal("0").add(this.traderTotal).subtract(this.traderBond);
	}
	public void setTraderMoney(BigDecimal traderMoney) {
		this.traderMoney = traderMoney;
	}
	public Integer getUseTradeDay() {
		if(this.useTradeDay == null){
			useTradeDay = 0;
		}
		return useTradeDay;
	}
	public void setUseTradeDay(Integer useTradeDay) {
		this.useTradeDay = useTradeDay;
	}
	public BigDecimal getReturnFeeManage() {
		if(returnFeeManage == null){
			returnFeeManage = new BigDecimal("0");
		}
		return returnFeeManage;
	}
	public void setReturnFeeManage(BigDecimal returnFeeManage) {
		this.returnFeeManage = returnFeeManage;
	}
	public BigDecimal getUseFeeManage() {
		useFeeManage = this.getFeeManage().subtract(this.getReturnFeeManage());
		return useFeeManage;
	}
	public void setUseFeeManage(BigDecimal useFeeManage) {
		this.useFeeManage = useFeeManage;
	}
	public String getAccountEndTimeStr() {
		if(endTime!=null){
			accountEndTimeStr=TypeConvert.dateToDatetimeStr(new Date(this.endTime*1000));
		}
		return accountEndTimeStr;
	}
	public void setAccountEndTimeStr(String accountEndTimeStr) {
		this.accountEndTimeStr = accountEndTimeStr;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
}
