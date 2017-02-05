package com.tzdr.domain.vo.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;

public class FSimpleUserTradeApplyVo implements Serializable {

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
	 * 操盘保证金
	 */
	@AllowExcel(name = "操盘保证金（元）")
	private BigDecimal traderBond;
	
	/**
	 * 代金券使用金额
	 */
    @AllowExcel(name="实际代金券(元)")
	private BigDecimal voucherActualMoney;
	
	/**
	 * 亏损平仓线
	 */
	@AllowExcel(name = "亏损平仓线（元）")
	private BigDecimal lineLoss;
	
	/**
	 * 融资金额=总操盘金额 - 操盘保证金
	 */
	@AllowExcel(name = "融资金额（元）")
	private BigDecimal financeMoney;

	/**
	 * 总操盘金额
	 */
	@AllowExcel(name = "总操盘金额（元）")
	private BigDecimal traderTotal;

	/**
	 * 手数
	 */
	@AllowExcel(name = "可开仓手数")
	private Integer tranLever;

	/**
	 * 交易账号
	 */
	@AllowExcel(name = "操盘账号")
	private String tranAccount;

	/**
	 * 账号密码
	 */
	@AllowExcel(name = "操盘密码")
	private String tranPassword;

	/**
	 * 申请时间
	 */
	private BigInteger appTime;
	@AllowExcel(name = "申请时间")
	private String appTimeValue;

	/**
	 * 更新时间
	 */
	private BigInteger updateTime;
	@AllowExcel(name = "处理时间")
	private String updateTimeValue;

	/**
	 * 方案号TG+ID号
	 */
	@AllowExcel(name = "方案编号")
	private String programNo;

	/**
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中 5.审核不通过 、6.已结算】
	 */
	private Integer stateType;
	@AllowExcel(name = "状态")
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

	public BigDecimal getVoucherActualMoney() {
		return voucherActualMoney;
	}

	public void setVoucherActualMoney(BigDecimal voucherActualMoney) {
		this.voucherActualMoney = voucherActualMoney;
	}

	public BigDecimal getLineLoss() {
		return lineLoss;
	}

	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
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

	public String getTranPassword() {
		return tranPassword;
	}

	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
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
	
	public BigDecimal getFinanceMoney() {
		financeMoney=traderTotal.subtract(traderBond);
		return financeMoney;
	}

	public void setFinanceMoney(BigDecimal financeMoney) {
		this.financeMoney = financeMoney;
	}

}
