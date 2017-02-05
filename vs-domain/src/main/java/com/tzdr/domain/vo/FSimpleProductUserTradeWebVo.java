package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * @author LiuYang
 *
 * 2015年9月21日 下午4:47:25
 */
public class FSimpleProductUserTradeWebVo implements Serializable {

	
	private static final long serialVersionUID = 3881885021490286200L;
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
	 * 操盘保证金
	 */
	@SqlColumn(name="trader_bond")
	private BigDecimal traderBond = new BigDecimal("0");

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
	 * 总操盘金额
	 */
	@SqlColumn(name="trader_total")
	private BigDecimal traderTotal = new BigDecimal("0");

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
	 * 结束时间（根据交易日天数计算出结束时间）
	 */
	@SqlColumn(name="app_endtime")
	private Long appEndtime;
	private String appEndtimeStr = "";

	/**
	 * 交易手数
	 */
	@SqlColumn(name="tran_actual_lever")
	private Integer tranActualLever = new Integer(0);

	/**
	 * 交易手续费总额
	 */
	@SqlColumn(name="tran_fees_total")
	private BigDecimal tranFeesTotal = new BigDecimal("0");

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
	 * 结算时间
	 */
	@SqlColumn(name="end_time")
	private Long endTime;
	private String endTimeStr="";

	/**
	 * 已操盘时间
	 */
	@SqlColumn(name="use_tran_day")
	private Integer useTradeDay;

	/**
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
	 */
	@SqlColumn(name="state_type")
	private Integer stateType;
	private String stateTypeStr = "";

	/**  
	 * 业务类型【0.富时A50  1.沪金     2.沪银   3.沪铜   4.橡胶】
	 */
	@SqlColumn(name="business_type")
	private Integer businessType;
	private String  businessTypeStr="";

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

	public Integer getTranLever() {
		if(tranLever == null){
			tranLever = new Integer(0);
		}
		return tranLever;
	}
	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}
	
	public BigDecimal getTraderBond() {
		return traderBond;
	}
	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}
	
	public BigDecimal getAppendTraderBond() {
		return appendTraderBond;
	}
	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
	}
	public BigDecimal getTraderTotal() {
		return traderTotal;
	}
	public void setTraderTotal(BigDecimal traderTotal) {
		this.traderTotal = traderTotal;
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

	public Long getAppTime() {
		return appTime;
	}
	public void setAppTime(Long appTime) {
		this.appTime = appTime;
	}

	public String getAppTimeStr() {
		if (this.appTime != null) {
			this.appTimeStr = TypeConvert.long1000ToDatetimeStr(this.appTime);
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
	
	public Integer getTranActualLever() {
		return tranActualLever;
	}
	public void setTranActualLever(Integer tranActualLever) {
		this.tranActualLever = tranActualLever;
	}
	
	public BigDecimal getTranFeesTotal() {
		return tranFeesTotal;
	}
	public void setTranFeesTotal(BigDecimal tranFeesTotal) {
		this.tranFeesTotal = tranFeesTotal;
	}
	
	
	public Long getAppEndtime() {
		return appEndtime;
	}
	public void setAppEndtime(Long appEndtime) {
		this.appEndtime = appEndtime;
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
	
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		if (this.endTime != null) {
			this.endTimeStr = TypeConvert.long1000ToDateStr(this.endTime);
		}
		else {
			this.endTimeStr = "";
		}
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
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
	
	public Integer getStateType() {
		return stateType;
	}
	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	public String getStateTypeStr() {
		if (this.stateType != null) {
			//状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
			if (this.stateType == 1) {
				this.stateTypeStr = "开户中";
			}
			else if (this.stateType == 2 || this.stateType == 4) {
				this.stateTypeStr = "操盘中";
			}
			else if (this.stateType == 3) {
				this.stateTypeStr = "待结算";
			}
			else if (this.stateType == 5) {
				this.stateTypeStr = "审核不通过";
			}
			else if (this.stateType == 6) {
				this.stateTypeStr = "已完结";
			}
		}
		return stateTypeStr;
	}
	public void setStateTypeStr(String stateTypeStr) {
		this.stateTypeStr = stateTypeStr;
	}

	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	
	public String getBusinessTypeStr() {
		if(this.businessType != null){
			if(this.businessType == 0){
				this.businessTypeStr = "富时A50";
			}else if(this.businessType == 1){
				this.businessTypeStr = "沪金";
			}else if(this.businessType == 2){
				this.businessTypeStr = "沪银";
			}else if(this.businessType == 3){
				this.businessTypeStr = "沪铜";
			}else if(this.businessType == 4){
				this.businessTypeStr = "橡胶";
			}else if(this.businessType == 20){
				this.businessTypeStr = "商品综合";
			}
		}else{
			businessTypeStr = "";
		}
		return businessTypeStr;
	}
	public void setBusinessTypeStr(String businessTypeStr) {
		this.businessTypeStr = businessTypeStr;
	}
}
