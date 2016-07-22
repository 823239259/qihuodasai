package com.tzdr.domain.vo.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;

public class FSimpleUserTradePlanVo implements Serializable {

	private static final long serialVersionUID = -5990266616970178037L;

	private String id;

	/**
	 * 用户编号
	 */
	private String uid;

	/**
	 * 手机号码
	 */
	@AllowExcel(name = "手机号码")
	private String mobile;

	/**
	 * 客户姓名
	 */
	@AllowExcel(name = "客户姓名")
	private String username;

	/**
	 * 交易品种类型【0.富时A50 1.沪金 2.沪银 3.沪铜 4.橡胶】
	 */
	private Integer businessType;
	@AllowExcel(name = "交易品种")
	private String businessTypeValue;

	/**
	 * 交易账号
	 */
	@AllowExcel(name = "操盘账号")
	private String tranAccount;

	/**
	 * 方案申请时间
	 */
	private BigInteger appTime;
	@AllowExcel(name = "方案申请时间")
	private String appTimeValue;

	/**
	 * 启用时间
	 */
	private BigInteger appStartTime;
	@AllowExcel(name = "启用时间")
	private String appStartTimeValue;

	/**
	 * 手数
	 */
	@AllowExcel(name = "可开仓手数")
	private Integer tranLever;

	/**
	 * 已操盘时间按交易日计算，1个交易日为前一交易日晚上21：00至本交易日15：00；例：2015-9-10 交易日为9月9日21：00至15：00
	 */
	@AllowExcel(name = "已操盘时间")
	private BigInteger useTranDay;

	/**
	 * 操盘保证金
	 */
	@AllowExcel(name = "操盘保证金（元）")
	private BigDecimal traderBond;

	/**
	 * 代金券使用金额
	 */
	@AllowExcel(name = "实际代金券(元)")
	private BigDecimal voucherActualMoney;

	/**
	 * 总操盘金额
	 */
	@AllowExcel(name = "总操盘金额（元）")
	private BigDecimal traderTotal;

	/**
	 * 融资金额=总操盘金额 - 操盘保证金
	 */
	@AllowExcel(name = "融资金额（元）")
	private BigDecimal financeMoney;

	/**
	 * 追加保证金额
	 */
	@AllowExcel(name = "补充保证金（元）")
	private BigDecimal appendTraderBond;

	/**
	 * 交易盈亏
	 */
	@AllowExcel(name = "交易盈亏（元）")
	private BigDecimal tranProfitLoss;

	// /**
	// * 交易手数
	// */
	// @AllowExcel(name = "交易手数")
	// private Integer tranActualLever;

	/**
	 * 交易手续费总额
	 */
	@AllowExcel(name = "交易手续费（元）")
	private BigDecimal tranFeesTotal;

	/**
	 * 折扣券面值
	 */

	private BigDecimal discountMoney;
		@AllowExcel(name = "折扣券")

	private String discountMoneyStr;
	/**
	 * 抵扣券抵扣手续费
	 */
	@AllowExcel(name = "抵扣手续费(元)")
	private BigDecimal discountActualMoney;

	/**
	 * 结算金额
	 */
	@AllowExcel(name = "结算金额(元)")
	private BigDecimal endAmountCal;

	/**
	 * 实际结算金额
	 */
	@AllowExcel(name = "实际结算金额（元）")
	private BigDecimal endAmount;

	/**
	 * 结算时间
	 */
	private BigInteger appEndTime;

	@AllowExcel(name = "申请结算时间")
	private String appEndTimeValue;

	/**
	 * 结算时间
	 */
	private BigInteger endTime;
	@AllowExcel(name = "结算时间")
	private String endTimeValue;
	/**
	 * 优惠券类型
	 */
	private String  type;
	/**
	 * 方案号TG+ID号
	 */
	@AllowExcel(name = "方案编号")
	private String programNo;

	/**
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中 5.审核不通过 、6.已结算】
	 */
	private Integer stateType;
	@AllowExcel(name = "结算状态")
	private String stateTypeValue;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getBusinessTypeValue() {
		if (businessType != null) {
			switch (businessType.intValue()) {
			case 0:
				businessTypeValue = "富时A50";
				break;
			case 1:
				businessTypeValue = "沪金";
				break;
			case 2:
				businessTypeValue = "沪银";
				break;
			case 3:
				businessTypeValue = "沪铜";
				break;
			case 4:
				businessTypeValue = "橡胶";
				break;
			case 20:
				businessTypeValue = "商品综合";
				break;
			default:
				break;
			}
		}
		return businessTypeValue;
	}

	public void setBusinessTypeValue(String businessTypeValue) {
		this.businessTypeValue = businessTypeValue;
	}

	public BigDecimal getTraderBond() {
		return traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}

	public BigDecimal getTraderTotal() {
		return traderTotal;
	}

	public void setTraderTotal(BigDecimal traderTotal) {
		this.traderTotal = traderTotal;
	}

	public Integer getTranLever() {
		return tranLever;
	}

	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}

	public String getTranAccount() {
		return tranAccount;
	}

	public void setTranAccount(String tranAccount) {
		this.tranAccount = tranAccount;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public Integer getStateType() {
		return stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	public String getStateTypeValue() {
		if (stateType != null) {
			switch (stateType.intValue()) {
			case 1:
				stateTypeValue = "开户中";
				break;
			case 2:
				stateTypeValue = "申请结算";
				break;
			case 3:
				stateTypeValue = "待结算";
				break;
			case 4:
				stateTypeValue = "操盘中";
				break;
			case 5:
				stateTypeValue = "审核不通过";
				break;
			case 6:
				stateTypeValue = "已结算";
				break;
			default:
				break;
			}
		}
		return stateTypeValue;
	}

	public void setStateTypeValue(String stateTypeValue) {
		this.stateTypeValue = stateTypeValue;
	}

	public BigInteger getAppStartTime() {
		return appStartTime;
	}

	public void setAppStartTime(BigInteger appStartTime) {
		this.appStartTime = appStartTime;
	}

	public String getAppStartTimeValue() {
		if (appStartTime != null) {
			appStartTimeValue = Dates.format(Dates.parseLong2Date(appStartTime
					.longValue()));
		}
		return appStartTimeValue;
	}

	public void setAppStartTimeValue(String appStartTimeValue) {
		this.appStartTimeValue = appStartTimeValue;
	}

	public BigInteger getAppTime() {
		return appTime;
	}

	public void setAppTime(BigInteger appTime) {
		this.appTime = appTime;
	}

	public String getAppTimeValue() {
		if (appTime != null) {
			appTimeValue = Dates.format(Dates.parseLong2Date(appTime.longValue()));
		}
		return appTimeValue;
	}

	public void setAppTimeValue(String appTimeValue) {
		this.appTimeValue = appTimeValue;
	}

	public BigInteger getUseTranDay() {
		return useTranDay;
	}

	public void setUseTranDay(BigInteger useTranDay) {
		this.useTranDay = useTranDay;
	}

	public BigDecimal getFinanceMoney() {
		financeMoney = traderTotal.subtract(traderBond);
		return financeMoney;
	}

	public void setFinanceMoney(BigDecimal financeMoney) {
		this.financeMoney = financeMoney;
	}

	public BigDecimal getAppendTraderBond() {
		return appendTraderBond;
	}

	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
	}

	public BigDecimal getTranProfitLoss() {
		return tranProfitLoss;
	}

	public void setTranProfitLoss(BigDecimal tranProfitLoss) {
		this.tranProfitLoss = tranProfitLoss;
	}

	public BigDecimal getTranFeesTotal() {
		return tranFeesTotal;
	}

	public void setTranFeesTotal(BigDecimal tranFeesTotal) {
		this.tranFeesTotal = tranFeesTotal;
	}

	public BigDecimal getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}

	public BigInteger getEndTime() {
		return endTime;
	}

	public void setEndTime(BigInteger endTime) {
		this.endTime = endTime;
	}

	public String getAppEndTimeValue() {
		if (appEndTime != null) {
			appEndTimeValue = Dates.format(Dates.parseLong2Date(appEndTime
					.longValue()));
		}
		return appEndTimeValue;
	}

	public void setAppEndTimeValue(String appEndTimeValue) {
		this.appEndTimeValue = appEndTimeValue;
	}

	public String getEndTimeValue() {
		if (endTime != null) {
			endTimeValue = Dates.format(Dates.parseLong2Date(endTime
					.longValue()));
		}
		return endTimeValue;
	}

	public void setEndTimeValue(String endTimeValue) {
		this.endTimeValue = endTimeValue;
	}

	public BigInteger getAppEndTime() {
		return appEndTime;
	}

	public void setAppEndTime(BigInteger appEndTime) {
		this.appEndTime = appEndTime;
	}

	public BigDecimal getVoucherActualMoney() {
		return voucherActualMoney;
	}

	public void setVoucherActualMoney(BigDecimal voucherActualMoney) {
		this.voucherActualMoney = voucherActualMoney;
	}

	// public Integer getTranActualLever() {
	// return tranActualLever;
	// }
	//
	// public void setTranActualLever(Integer tranActualLever) {
	// this.tranActualLever = tranActualLever;
	// }

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

	public BigDecimal getEndAmountCal() {
		return endAmountCal;
	}

	public void setEndAmountCal(BigDecimal endAmountCal) {
		this.endAmountCal = endAmountCal;
	}



	public String getDiscountMoneyStr() {
		if (null != discountMoney && null != type) {
			if ("3" .equals(type)) {
				discountMoneyStr = discountMoney.doubleValue() + "折";
			} else if ("6".equals(type)) {
				discountMoneyStr = discountMoney.toString() + "元";
			}
		}
		return discountMoneyStr;
	}
	public void setDiscountMoneyStr(String discountMoneyStr) {
		this.discountMoneyStr = discountMoneyStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
