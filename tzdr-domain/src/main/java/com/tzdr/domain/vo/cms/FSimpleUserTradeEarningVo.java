package com.tzdr.domain.vo.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;

public class FSimpleUserTradeEarningVo implements Serializable {

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
	 * 申请时间
	 */
	private BigInteger appTime;
	@AllowExcel(name = "申请时间")
	private String appTimeValue;
	
	/**
	 * 操盘保证金
	 */
	@AllowExcel(name = "操盘保证金（元）")
	private BigDecimal traderBond;
	
	/**
	 * 结算时实际抵扣金额
	 */
	@AllowExcel(name = "抵扣保证金")
	private BigDecimal endActualMoney;
	
	/**
	 * 手数
	 */
	@AllowExcel(name = "可开仓手数")
	private Integer tranLever;
	/**
	 * 开户处理
	 */
	private BigInteger updateTime;
	@AllowExcel(name = "开户处理时间")
	private String updateTimeValue;
	
	/**
	 * 启用时间
	 */
	private BigInteger appStartTime;
	
	private String appStartTimeValue;
	
	

	/**
	 * 追加保证金额
	 */
	@AllowExcel(name = "补充保证金（元）")
	private BigDecimal appendTraderBond;
	/**
	 * 申请终结时间
	 */
	@AllowExcel(name = "申请结算时间")
	private BigInteger appEndTime;
	
	/**
	 * 结算时间
	 */
	private BigInteger endTime;
	@AllowExcel(name = "结算时间")
	private String endTimeValue;
	
	/**
	 * 交易盈亏
	 */
	private BigDecimal tranProfitLoss;
	
	/**
	 * 交易手续费总额
	 */
	@AllowExcel(name = "交易手续费（元）")
	private BigDecimal tranFeesTotal;
	
	
	/**
	 * 抵扣券抵扣手续费
	 */
	@AllowExcel(name = "抵扣手续费")
	private BigDecimal discountActualMoney;
	
	/**
	 * 折扣券面值
	 */
	@AllowExcel(name = "折扣券（折）")
	private BigDecimal discountMoney;
	

	
	/**
	 * 实际盈亏=交易盈亏 - 交易手续费
	 */
	@AllowExcel(name="实际盈亏（元）")
	private BigDecimal actualProfitLoss;
	
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
		if(businessType!=null){
			switch (businessType.intValue()) {
			case 0:
				businessTypeValue="富时A50";
				break;
			case 1:
				businessTypeValue="沪金";
				break;
			case 2:
				businessTypeValue="沪银";
				break;
			case 3:
				businessTypeValue="沪铜";
				break;
			case 4:
				businessTypeValue="橡胶";
				break;
			case 20:
				businessTypeValue="商品综合";
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

	public Integer getStateType() {
		return stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	public String getStateTypeValue() {
		if(stateType!=null){
			switch (stateType.intValue()) {
			case 1:
				stateTypeValue="开户中";
				break;
			case 2:
				stateTypeValue="申请结算";
				break;
			case 3:
				stateTypeValue="待结算";
				break;
			case 4:
				stateTypeValue="操盘中";
				break;
			case 5:
				stateTypeValue="审核不通过";
				break;
			case 6:
				stateTypeValue="已结算";
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

	public BigInteger getAppTime() {
		return appTime;
	}

	public void setAppTime(BigInteger appTime) {
		this.appTime = appTime;
	}

	public String getAppTimeValue() {
		if(appTime!=null){
			appTimeValue=Dates.format(Dates.parseLong2Date(appTime.longValue()));
		}
		return appTimeValue;
	}

	public void setAppTimeValue(String appTimeValue) {
		this.appTimeValue = appTimeValue;
	}

	public BigInteger getAppStartTime() {
		return appStartTime;
	}

	public void setAppStartTime(BigInteger appStartTime) {
		this.appStartTime = appStartTime;
	}

	public String getAppStartTimeValue() {
		if(appStartTime!=null){
			appStartTimeValue=Dates.format(Dates.parseLong2Date(appStartTime.longValue()));
		}
		return appStartTimeValue;
	}

	public void setAppStartTimeValue(String appStartTimeValue) {
		this.appStartTimeValue = appStartTimeValue;
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

	public BigInteger getEndTime() {
		return endTime;
	}

	public void setEndTime(BigInteger endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeValue() {
		if(endTime!=null){
			endTimeValue=Dates.format(Dates.parseLong2Date(endTime.longValue()));
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

	public BigDecimal getActualProfitLoss() {
		if(tranProfitLoss!=null && tranFeesTotal!=null){
			actualProfitLoss=tranProfitLoss.subtract(tranFeesTotal);
		}
		return actualProfitLoss;
	}

	public void setActualProfitLoss(BigDecimal actualProfitLoss) {
		this.actualProfitLoss = actualProfitLoss;
	}
	
	
	public BigInteger getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(BigInteger updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTimeValue() {
		if(updateTime!=null){
			updateTimeValue=Dates.format(Dates.parseLong2Date(updateTime.longValue()));
		}
		return updateTimeValue;
	}

	public void setUpdateTimeValue(String updateTimeValue) {
		this.updateTimeValue = updateTimeValue;
	}

	public BigDecimal getEndActualMoney() {
		return endActualMoney;
	}

	public void setEndActualMoney(BigDecimal endActualMoney) {
		this.endActualMoney = endActualMoney;
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
}
