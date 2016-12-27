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
 * <p> 配置 </p>
 * 
 * @author WangPinQun
 * @see FSimpleFtseUserTrade
 * @version 2.0 2015年9月16日上午10:33:13
 */
@Entity
@Table(name = "f_simple_ftse_user_trade")
public class FSimpleFtseUserTrade extends BaseEntity {

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
	 * 备注：当businessType=8 国际综合时，此字段表示A50 交易手续费，否则为当前类型 交易手续费
	 */
	private BigDecimal tranFees;
	
	/**
	 * 国际原油交易手续费
	 */
	private BigDecimal crudeTranFees;
	
	/**
	 * 恒生指数交易手续费
	 */
	private BigDecimal hsiTranFees;
	


	/**
	 * 迷你纳指交易手续费
	 */
	private BigDecimal mnTranFees;
	
	/**
	 * 迷你标普交易手续费
	 */
	private BigDecimal mbTranFees;
	
	/**
	 * 德国DAX交易手续费
	 */
	private BigDecimal daxTranFees;
	
	
	
	/**
	 * 日经225交易手续费
	 */
	private BigDecimal nikkeiTranFees;
	
	/**
	 * 迷你道指交易手续费
	 */
	private BigDecimal mdTranFees;

	/**
	 * 小恒指交易手续费
	 */
	private BigDecimal lhsiTranFees;
	
	/**
	 * 美黄金交易手续费
	 */
	private BigDecimal agTranFees;
	
	/**
	 * H股指交易手续费
	 */
	private  BigDecimal hSTranFees;
	
	/**
	 * 小H股指交易手续费
	 */
	private  BigDecimal xHSTranFees;
	
	/**
	 * 美铜交易手续费
	 */
	private  BigDecimal ameCTranFees;
	
	/**
	 * 美白银交易手续费
	 */
	private  BigDecimal ameSTranFees;
	
	/**
	 * 小原油交易手续费
	 */
	private  BigDecimal smallCTranFees;
	/**
	 * 迷你德国DAX指数交易手续费
	 */
	private BigDecimal daxMinTranFees;
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
	 * 备注：当businessType=8 国际综合时，此字段表示A50交易手数，否则为当前类型交易手数 
	 */
	private Integer tranActualLever;
	
	/**
	 * 国际原油交易手数
	 */
	private Integer crudeTranActualLever;
	
	/**
	 * 恒生指数交易手数
	 */
	private Integer hsiTranActualLever;

	
	/**
	 * 迷你纳指交易手数
	 */
	private Integer mntranActualLever;
	
	/**
	 * 迷你标普交易手数
	 */
	private Integer mbtranActualLever;
	
	/**
	 * 德国DAX交易手数
	 */
	private Integer daxtranActualLever;
	
	
	
	/**
	 * 日经225交易手数
	 */
	private Integer nikkeiTranActualLever;
	
	/**
	 * 迷你道指交易手数
	 */
	private Integer mdtranActualLever;

	/**
	 * 小恒指交易手数
	 */
	private Integer lhsiTranActualLever;

	/**
	 * 美黄金交易手数
	 */
	private Integer agTranActualLever;
	
	/**
	 * H股指交易手数
	 */
	private Integer hStockMarketLever;
	
	/**
	 * 小H股指交易手数
	 */
	private Integer xHStockMarketLever;
	
	/**
	 * 美铜交易手数
	 */
	private Integer AmeCopperMarketLever;
	
	/**
	 * 美白银交易手数
	 */
	private Integer AmeSilverMarketLever;
	
	/**
	 * 小原油交易手数
	 */
	private Integer smallCrudeOilMarketLever;
	/**
	 * 迷你德国DAX指数交易手数
	 */
	private Integer daxtranMinActualLever;

	/**
	 * 交易手续费总额
	 */
	private BigDecimal tranFeesTotal;
	
	/**
	 * 交易盈亏
	 */
	private BigDecimal tranProfitLoss;
	
	/**
	 * 实际结算金额=结算金额-优惠金额
	 */
	private BigDecimal endAmount;
	
	/**
	 * 结算金额-未扣除优惠券
	 */
	private BigDecimal endAmountCal;
	
	
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
	 * 入金金额(美元)
	 */
	private BigDecimal goldenMoney;
	
	/**  
	 * 业务类型【0.富时A50  1.沪金     2.沪银   3.沪铜   4.橡胶  6.原油    7. 恒指   8.国际综合  9.小恒指     20.商品综合】
	 */
	private Integer businessType;
	
	/**
	 * 平台来源   1：网站平台    2：APP平台
	 */
	private Integer source=1;
	
	/**
	 * 操作员
	 */
	private String operator;
	
	

	public FSimpleFtseUserTrade() {
	}
	@Column(name = "source")
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
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

	public Integer getCrudeTranActualLever() {
		return crudeTranActualLever;
	}

	public void setCrudeTranActualLever(Integer crudeTranActualLever) {
		this.crudeTranActualLever = crudeTranActualLever;
	}

	public Integer getHsiTranActualLever() {
		return hsiTranActualLever;
	}

	public void setHsiTranActualLever(Integer hsiTranActualLever) {
		this.hsiTranActualLever = hsiTranActualLever;
	}

	public BigDecimal getCrudeTranFees() {
		return crudeTranFees;
	}

	public void setCrudeTranFees(BigDecimal crudeTranFees) {
		this.crudeTranFees = crudeTranFees;
	}

	public BigDecimal getHsiTranFees() {
		return hsiTranFees;
	}

	public void setHsiTranFees(BigDecimal hsiTranFees) {
		this.hsiTranFees = hsiTranFees;
	}

	public BigDecimal getMnTranFees() {
		return mnTranFees;
	}

	public void setMnTranFees(BigDecimal mnTranFees) {
		this.mnTranFees = mnTranFees;
	}

	

	public BigDecimal getMbTranFees() {
		return mbTranFees;
	}

	public void setMbTranFees(BigDecimal mbTranFees) {
		this.mbTranFees = mbTranFees;
	}

	public BigDecimal getDaxTranFees() {
		return daxTranFees;
	}

	public void setDaxTranFees(BigDecimal daxTranFees) {
		this.daxTranFees = daxTranFees;
	}

	public BigDecimal getNikkeiTranFees() {
		return nikkeiTranFees;
	}

	public void setNikkeiTranFees(BigDecimal nikkeiTranFees) {
		this.nikkeiTranFees = nikkeiTranFees;
	}

	public BigDecimal getLhsiTranFees() {
		return lhsiTranFees;
	}
	public void setLhsiTranFees(BigDecimal lhsiTranFees) {
		this.lhsiTranFees = lhsiTranFees;
	}
	public BigDecimal getAgTranFees() {
		return agTranFees;
	}
	public void setAgTranFees(BigDecimal agTranFees) {
		this.agTranFees = agTranFees;
	}
	public BigDecimal getMdTranFees() {
		return mdTranFees;
	}

	public void setMdTranFees(BigDecimal mdTranFees) {
		this.mdTranFees = mdTranFees;
	}

	public Integer getMntranActualLever() {
		return mntranActualLever;
	}

	public void setMntranActualLever(Integer mntranActualLever) {
		this.mntranActualLever = mntranActualLever;
	}

	public Integer getMbtranActualLever() {
		return mbtranActualLever;
	}

	public void setMbtranActualLever(Integer mbtranActualLever) {
		this.mbtranActualLever = mbtranActualLever;
	}

	public Integer getDaxtranActualLever() {
		return daxtranActualLever;
	}

	public void setDaxtranActualLever(Integer daxtranActualLever) {
		this.daxtranActualLever = daxtranActualLever;
	}

	public Integer getNikkeiTranActualLever() {
		return nikkeiTranActualLever;
	}

	public void setNikkeiTranActualLever(Integer nikkeiTranActualLever) {
		this.nikkeiTranActualLever = nikkeiTranActualLever;
	}

	public Integer getMdtranActualLever() {
		return mdtranActualLever;
	}

	public void setMdtranActualLever(Integer mdtranActualLever) {
		this.mdtranActualLever = mdtranActualLever;
	}
	
	public Integer getLhsiTranActualLever() {
		return lhsiTranActualLever;
	}
	public void setLhsiTranActualLever(Integer lhsiTranActualLever) {
		this.lhsiTranActualLever = lhsiTranActualLever;
	}
	public Integer getAgTranActualLever() {
		return agTranActualLever;
	}
	public void setAgTranActualLever(Integer agTranActualLever) {
		this.agTranActualLever = agTranActualLever;
	}
	@Column(name = "end_amount_cal")
	public BigDecimal getEndAmountCal() {
		return endAmountCal;
	}

	public void setEndAmountCal(BigDecimal endAmountCal) {
		this.endAmountCal = endAmountCal;
	}
	
	
	
	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public BigDecimal getVoucherMoney() {
		return voucherMoney;
	}

	public void setVoucherMoney(BigDecimal voucherMoney) {
		this.voucherMoney = voucherMoney;
	}

	public BigDecimal getVoucherActualMoney() {
		return voucherActualMoney;
	}

	public void setVoucherActualMoney(BigDecimal voucherActualMoney) {
		this.voucherActualMoney = voucherActualMoney;
	}

	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public BigDecimal getDiscountActualMoney() {
		return discountActualMoney;
	}

	public void setDiscountActualMoney(BigDecimal discountActualMoney) {
		this.discountActualMoney = discountActualMoney;
	}

	public BigDecimal getEndActualMoney() {
		return endActualMoney;
	}

	public void setEndActualMoney(BigDecimal endActualMoney) {
		this.endActualMoney = endActualMoney;
	}

	@Column
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}


	public Integer gethStockMarketLever() {
		return hStockMarketLever;
	}
	public void sethStockMarketLever(Integer hStockMarketLever) {
		this.hStockMarketLever = hStockMarketLever;
	}
	public Integer getxHStockMarketLever() {
		return xHStockMarketLever;
	}
	public void setxHStockMarketLever(Integer xHStockMarketLever) {
		this.xHStockMarketLever = xHStockMarketLever;
	}
	public Integer getAmeCopperMarketLever() {
		return AmeCopperMarketLever;
	}
	public void setAmeCopperMarketLever(Integer ameCopperMarketLever) {
		AmeCopperMarketLever = ameCopperMarketLever;
	}
	public Integer getAmeSilverMarketLever() {
		return AmeSilverMarketLever;
	}
	public void setAmeSilverMarketLever(Integer ameSilverMarketLever) {
		AmeSilverMarketLever = ameSilverMarketLever;
	}
	public Integer getSmallCrudeOilMarketLever() {
		return smallCrudeOilMarketLever;
	}
	public void setSmallCrudeOilMarketLever(Integer smallCrudeOilMarketLever) {
		this.smallCrudeOilMarketLever = smallCrudeOilMarketLever;
	}


	public BigDecimal gethSTranFees() {
		return hSTranFees;
	}
	public void sethSTranFees(BigDecimal hSTranFees) {
		this.hSTranFees = hSTranFees;
	}
	public BigDecimal getxHSTranFees() {
		return xHSTranFees;
	}
	public void setxHSTranFees(BigDecimal xHSTranFees) {
		this.xHSTranFees = xHSTranFees;
	}
	public BigDecimal getAmeCTranFees() {
		return ameCTranFees;
	}
	public void setAmeCTranFees(BigDecimal ameCTranFees) {
		this.ameCTranFees = ameCTranFees;
	}
	public BigDecimal getAmeSTranFees() {
		return ameSTranFees;
	}
	public void setAmeSTranFees(BigDecimal ameSTranFees) {
		this.ameSTranFees = ameSTranFees;
	}
	public BigDecimal getSmallCTranFees() {
		return smallCTranFees;
	}
	public void setSmallCTranFees(BigDecimal smallCTranFees) {
		this.smallCTranFees = smallCTranFees;
	}


	public BigDecimal getDaxMinTranFees() {
		return daxMinTranFees;
	}
	public void setDaxMinTranFees(BigDecimal daxMinTranFees) {
		this.daxMinTranFees = daxMinTranFees;
	}
	public Integer getDaxtranMinActualLever() {
		return daxtranMinActualLever;
	}
	public void setDaxtranMinActualLever(Integer daxtranMinActualLever) {
		this.daxtranMinActualLever = daxtranMinActualLever;
	}


	/**
	 * 优惠券ID（类型为代金券）
	 */
	@Column
	private String voucherId;
	
	/**
	 * 代金券面值
	 */
	@Column
	private BigDecimal voucherMoney;
	
	/**
	 * 代金券使用金额
	 */
	@Column
	private BigDecimal voucherActualMoney;
	
	/**
	 * 结算时实际抵扣金额
	 */
	@Column
	private BigDecimal endActualMoney;
	
	/**
	 * 优惠券ID（类型为折扣券）
	 */
	@Column
	private String discountId;
	
	/**
	 * 折扣券面值
	 */
	@Column
	private BigDecimal discountMoney;
	
	/**
	 * 抵扣券抵扣手续费
	 */
	@Column
	private BigDecimal discountActualMoney;
}
