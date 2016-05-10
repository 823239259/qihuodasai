package com.tzdr.domain.web.entity;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;

/**
 * 
 * 
 * <p> 富时A50和商品期货抢先版操作表 </p>
 * 
 * @author WangPinQun
 * @see FHandleFtseUserTrade
 * @version 2.0 2015年9月16日上午10:33:13
 */
@Entity
@Table(name = "f_handle_ftse_user_trade")
public class FHandleFtseUserTrade extends BaseEntity {

	private static final long serialVersionUID = -7154685207993844604L;

	/**
	 * 用户编号
	 */
	private String uid;

	/**
	 * 方案号TG+ID号
	 */
	private String programNo;
	
	/**
	 * 总保证金额
	 */
	private BigDecimal traderBond;
	
	/**
	 * 追加保证金额
	 */
	private BigDecimal appendTraderBond;
	
	/**
	 * 开仓手数
	 */
	private Integer tranLever;

	/**
	 * 总操盘金额
	 */
	private BigDecimal traderTotal;

	/**
	 * 亏损平仓线
	 */
	private BigDecimal lineLoss;

	/**
	 * 账户管理费
	 */
	private BigDecimal feeManage;

	/**
	 * 交易手续费
	 */
	private BigDecimal tranFees;

	/**
	 * 申请时间
	 */
	private Long appTime=Dates.getCurrentLongDate();
	
	/**
	 * 开始时间（使用日期）
	 */
	private Long appStarttime;
	
	/**
	 * 交易账号
	 */
	private String tranAccount;
	
	/**
	 * 账号密码
	 */
	private String tranPassword;
	
	
	/**
	 * 申请终结时间
	 */
	private Long appEndTime;
	
	/**
	 * 交易手数
	 */
	private Integer tranActualLever;

	/**
	 * 交易手续费总额
	 */
	private BigDecimal tranFeesTotal;
	
	/**
	 * 交易盈亏
	 */
	private BigDecimal tranProfitLoss;
	
	/**
	 * 结算金额
	 */
	private BigDecimal endAmount;
	
	/**
	 * 汇率【美元($)-人民币(¥)】
	 */
	private BigDecimal endParities;
	
	/**
	 * 结算时间
	 */
	private Long endTime;
	
	/**
	 * 实际操盘时间
	 */
	private Integer useTranDay;
	
	/**  
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
	 */
	private Integer stateType;
	
	/**
	 * 更改时间
	 */
	private Long updateTime;
	
	/**  
	 * 业务状态业务类型【0.富时A50  1.沪金     2.沪银   3.沪铜   4.橡胶  6.原油    7. 恒指】
	 */
	private Integer businessType;
	
	/**
	 * 入金金额(美元)
	 */
	private BigDecimal goldenMoney;
	
	public FHandleFtseUserTrade() {
		
	}
	
	@Column(name = "uid", nullable = false, length = 32)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "program_no", length = 200)
	public String getProgramNo() {
		return this.programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}
	
	@Column(name = "trader_bond")
	public BigDecimal getTraderBond() {
		return this.traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}
	
	@Column(name = "append_trader_bond")
	public BigDecimal getAppendTraderBond() {
		if(this.appendTraderBond == null){
			this.appendTraderBond = new BigDecimal(0);
		}
		return appendTraderBond;
	}

	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
	}
	
	@Column(name = "tran_lever")
	public Integer getTranLever() {
		return tranLever;
	}

	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}

	@Column(name = "trader_total")
	public BigDecimal getTraderTotal() {
		return this.traderTotal;
	}

	public void setTraderTotal(BigDecimal traderTotal) {
		this.traderTotal = traderTotal;
	}

	@Column(name = "line_loss")
	public BigDecimal getLineLoss() {
		return this.lineLoss;
	}

	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
	}

	@Column(name = "fee_manage")
	public BigDecimal getFeeManage() {
		return this.feeManage;
	}

	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}

	@Column(name = "tran_fees")
	public BigDecimal getTranFees() {
		return this.tranFees;
	}

	public void setTranFees(BigDecimal tranFees) {
		this.tranFees = tranFees;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "app_time", length = 19)
	public Long getAppTime() {
		return this.appTime;
	}

	public void setAppTime(Long appTime) {
		this.appTime = appTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "app_starttime", length = 19)
	public Long getAppStarttime() {
		return this.appStarttime;
	}

	public void setAppStarttime(Long appStarttime) {
		this.appStarttime = appStarttime;
	}

	@Column(name = "tran_account", length = 10)
	public String getTranAccount() {
		return this.tranAccount;
	}

	public void setTranAccount(String tranAccount) {
		this.tranAccount = tranAccount;
	}

	@Column(name = "tran_password", length = 10)
	public String getTranPassword() {
		return this.tranPassword;
	}

	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "app_endtime", length = 19)
	public Long getAppEndTime() {
		return appEndTime;
	}

	public void setAppEndTime(Long appEndTime) {
		this.appEndTime = appEndTime;
	}
	
	@Column(name = "tran_actual_lever")
	public Integer getTranActualLever() {
		return tranActualLever;
	}

	public void setTranActualLever(Integer tranActualLever) {
		this.tranActualLever = tranActualLever;
	}
	
	@Column(name = "tran_feestotal")
	public BigDecimal getTranFeesTotal() {
		return tranFeesTotal;
	}

	public void setTranFeesTotal(BigDecimal tranFeesTotal) {
		this.tranFeesTotal = tranFeesTotal;
	}

	@Column(name = "tran_profit_loss")
	public BigDecimal getTranProfitLoss() {
		return this.tranProfitLoss;
	}

	public void setTranProfitLoss(BigDecimal tranProfitLoss) {
		this.tranProfitLoss = tranProfitLoss;
	}

	@Column(name = "end_amount")
	public BigDecimal getEndAmount() {
		return this.endAmount;
	}

	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}

	@Column(name = "end_time")
	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@Column(name = "use_tran_day")
	public Integer getUseTranDay() {
		return useTranDay;
	}

	public void setUseTranDay(Integer useTranDay) {
		this.useTranDay = useTranDay;
	}

	@Column(name = "end_parities")
	public BigDecimal getEndParities() {
		return endParities;
	}

	public void setEndParities(BigDecimal endParities) {
		this.endParities = endParities;
	}
	
	@Column(name = "state_type")
	public Integer getStateType() {
		return this.stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", length = 19)
	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "business_type")
	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	@Column(name = "golden_money")
	public BigDecimal getGoldenMoney() {
		return goldenMoney;
	}

	public void setGoldenMoney(BigDecimal goldenMoney) {
		this.goldenMoney = goldenMoney;
	}
}
