package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * FSimpleUserTradeWebVo
 * @version 2.0
 * 2015年2月5日下午7:33:13
 */
public class FSimpleUserTradeWebVo implements Serializable {
	
	private static final long serialVersionUID = 9049563732662184912L;
	@SqlColumn
	private String id;
	/**
	 * 用户编号
	 */
	
	private String uid;
	
	/**
	 * 方案号TG+ID号
	 */
	@SqlColumn(name="program_no")
	private String programNo = "";

	/**
	 * 总操盘金额
	 */
	@SqlColumn(name="trader_total")
	private BigDecimal traderTotal = new BigDecimal("0");

	/**
	 * 操盘保证金
	 */
	@SqlColumn(name="trader_bond")
	private BigDecimal traderBond = new BigDecimal("0");

	/**
	 * 亏损平仓线
	 */
	@SqlColumn(name="line_loss")
	private BigDecimal lineLoss = new BigDecimal("0");

	/**
	 * 账户管理费
	 */
	@SqlColumn(name="fee_manage")
	private BigDecimal feeManage = new BigDecimal("0");

	/**
	 * 交易手续费
	 */
	@SqlColumn(name="tran_fees")
	private BigDecimal tranFees = new BigDecimal("0");

	/**
	 * 交易日天数
	 */
	@SqlColumn(name="tran_day")
	private Integer tranDay;

	/**
	 * 申请时间
	 */
	@SqlColumn(name="app_time")
	private Long appTime;
	
	private String appTimeStr = "";
	
	/**
	 * 开始时间（使用日期）
	 */
	@SqlColumn(name="app_starttime")
	private Long appStarttime;
	
	private String appStarttimeStr = "";
	/**
	 * 结束时间（根据交易日天数计算出结束时间）
	 */
	@SqlColumn(name="app_endtime")
	private Long appEndtime;
	
	private String appEndtimeStr = "";
	
	/**
	 * 交易账号
	 */
	@SqlColumn(name="tran_account")
	private String tranAccount = "";
	/**
	 * 账号密码
	 */
	@SqlColumn(name="tran_password")
	private String tranPassword = "";
	/**
	 * 状态【1.审核中、-1.未通过、2.操盘中、3.已完结】
	 */
	@SqlColumn(name="state_type")
	private Integer stateType;
	
	private String stateTypeStr = "";
	/**
	 * 交易佣金
	 */
	@SqlColumn(name="tran_commission")
	private BigDecimal tranCommission = new BigDecimal("0");
	/**
	 * 交易盈亏
	 */
	@SqlColumn(name="tran_profit_loss")
	private BigDecimal tranProfitLoss = new BigDecimal("0");
	/**
	 * 结算金额
	 */
	@SqlColumn(name="end_amount")
	private BigDecimal endAmount = new BigDecimal("0");
	
	/**
	 * 追加保证金
	 */
	@SqlColumn(name="append_trader_bond")
	private BigDecimal appendTraderBond = new BigDecimal("0");
	
	/**
	 * 手数
	 */
	@SqlColumn(name="tran_lever")
	private Integer tranLever = new Integer(0);
	
	/**
	 * 结算时间
	 */
	@SqlColumn(name="end_time")
	private Long endTime;
	
	/**
	 * 已操盘时间
	 */
	@SqlColumn(name="use_tran_day")
	private Integer useTradeDay;
	
	/**
	 * 返还管理费
	 */
	@SqlColumn(name="return_fee_manage")
	private BigDecimal returnFeeManage;
	
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
		if(tranDay == null){
			tranDay = 0;
		}
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
		if (this.appTime != null) {
			this.appTimeStr = TypeConvert.long1000ToDateStr(this.appTime);
		}
		else {
			this.appTimeStr = "";
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
	public Long getAppEndtime() {
		return appEndtime;
	}
	public void setAppEndtime(Long appEndtime) {
		this.appEndtime = appEndtime;
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
	public String getAppStarttimeStr() {
		if (this.appStarttime != null) {
			this.appStarttimeStr = TypeConvert.long1000ToDateStr(this.appStarttime);
		}
		else {
			this.appStarttimeStr = "";
		}
		return appStarttimeStr;
	}
	public void setAppStarttimeStr(String appStarttimeStr) {
		this.appStarttimeStr = appStarttimeStr;
	}
	public String getAppEndtimeStr() {
		if (this.appEndtime != null) {
			this.appEndtimeStr = TypeConvert.long1000ToDateStr(this.appEndtime);
		}
		else {
			this.appEndtimeStr = "";
		}
		return appEndtimeStr;
	}
	
	public void setAppEndtimeStr(String appEndtimeStr) {
		this.appEndtimeStr = appEndtimeStr;
	}
	public String getStateTypeStr() {
	    if (this.stateType != null) {
	    	//状态【1.审核中、-1.未通过、2.操盘中、3.已完结】
	    	if (this.stateType == 1) {
	    		this.stateTypeStr = "审核中";
	    	}
	    	else if (this.stateType == 2 || this.stateType == 4) {
	    		this.stateTypeStr = "操盘中";
	    	}
	    	else if (this.stateType == -1) {
	    		this.stateTypeStr = "未通过";
	    	}
	    	else if (this.stateType == 3) {
	    		this.stateTypeStr = "已完结";
	    	}
	    }
		return stateTypeStr;
	}
	public void setStateTypeStr(String stateTypeStr) {
		this.stateTypeStr = stateTypeStr;
	}
	public BigDecimal getAppendTraderBond() {
		return appendTraderBond;
	}
	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
	}
	public Integer getTranLever() {
		if(tranLever == null){
			tranLever = new Integer(0);
		}
		return tranLever;
	}
	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
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
		return returnFeeManage;
	}
	public void setReturnFeeManage(BigDecimal returnFeeManage) {
		this.returnFeeManage = returnFeeManage;
	}
}
