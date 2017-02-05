package com.tzdr.domain.web.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tzdr.common.domain.BaseEntity;

/**
 * 
 * 
 * <p> 配置 </p>
 * 
 * @author QingLiu
 * @see FSimpleUserTrade
 * @version 2.0 2015年2月5日下午7:33:13
 */
@Entity
@Table(name = "f_simple_user_trade")
public class FSimpleUserTrade extends BaseEntity {

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
	 * 总操盘金额
	 */
	private BigDecimal traderTotal;

	/**
	 * 保证金额
	 */
	private BigDecimal traderBond;
	
	/**
	 * 追加保证金额
	 */
	private BigDecimal appendTraderBond = new BigDecimal(0.00);

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
	 * 交易日天数
	 */
	private Integer tranDay;

	/**
	 * 申请时间
	 */
	private Long appTime;
	/**
	 * 开始时间（使用日期）
	 */
	private Long appStarttime;
	/**
	 * 结束时间（根据交易日天数计算出结束时间）
	 */
	private Long appEndtime;
	/**
	 * 交易账号
	 */
	private String tranAccount;
	/**
	 * 账号密码
	 */
	private String tranPassword;
	/**
	 * 状态【1.审核中、-1.未通过、2.操盘中、3.已完结、4.申请终结】
	 */
	private Integer stateType;
	/**
	 * 交易佣金
	 */
	private BigDecimal tranCommission;
	/**
	 * 交易盈亏
	 */
	private BigDecimal tranProfitLoss;
	/**
	 * 结算金额
	 */
	private BigDecimal endAmount;
	
	/**
	 * 结算时间
	 */
	private Long endTime;
	
	/**
	 * 手数
	 */
	private Integer tranLever;
	
	/**
	 * 股指业务类型 1:随心乐  2：天天乐
	 */
	private Integer businessType;
	
	/**
	 * 返还管理费
	 */
	private BigDecimal returnFeeManage;
	
	/**
	 * 实际操盘时间
	 */
	private Integer useTranDay;

	public FSimpleUserTrade() {
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

	@Column(name = "trader_total")
	public BigDecimal getTraderTotal() {
		return this.traderTotal;
	}

	public void setTraderTotal(BigDecimal traderTotal) {
		this.traderTotal = traderTotal;
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
		return appendTraderBond;
	}

	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
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

	@Column(name = "tran_day")
	public Integer getTranDay() {
		return this.tranDay;
	}

	public void setTranDay(Integer tranDay) {
		this.tranDay = tranDay;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "app_endtime", length = 19)
	public Long getAppEndtime() {
		return this.appEndtime;
	}

	public void setAppEndtime(Long appEndtime) {
		this.appEndtime = appEndtime;
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

	@Column(name = "state_type")
	public Integer getStateType() {
		return this.stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	@Column(name = "tran_commission")
	public BigDecimal getTranCommission() {
		return this.tranCommission;
	}

	public void setTranCommission(BigDecimal tranCommission) {
		this.tranCommission = tranCommission;
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

	@Column(name = "tran_lever")
	public Integer getTranLever() {
		return tranLever;
	}

	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}
	@Column(name = "business_type")
	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	@Column(name = "return_fee_manage")
	public BigDecimal getReturnFeeManage() {
		return returnFeeManage;
	}

	public void setReturnFeeManage(BigDecimal returnFeeManage) {
		this.returnFeeManage = returnFeeManage;
	}

	@Column(name = "use_tran_day")
	public Integer getUseTranDay() {
		return useTranDay;
	}

	public void setUseTranDay(Integer useTranDay) {
		this.useTranDay = useTranDay;
	}
}
