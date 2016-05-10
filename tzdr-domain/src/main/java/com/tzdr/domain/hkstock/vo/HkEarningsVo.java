package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 港股收益日数据VO
 * 
 * @Description:
 * @author liuhaichuan
 * @date 2015年10月23日
 *
 */
public class HkEarningsVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 手机号
	 */
	@SqlColumn
	@AllowExcel(name = "手机号")
	private String mobile;

	/**
	 * 
	 */
	@SqlColumn
	@AllowExcel(name = "实名")
	private String tname;

	/**
	 * 交易账户
	 */
	@SqlColumn
	@AllowExcel(name = "交易账户")
	private String account;

	/**
	 * 所属券商户
	 */
	@SqlColumn
	@AllowExcel(name = "所属券商户")
	private String hsBelongBroker;

	/**
	 * 方案号
	 */
	@SqlColumn
	@AllowExcel(name = "方案号")
	private String groupId;

	/**
	 * 保证金
	 */
	@SqlColumn
	@AllowExcel(name = "保证金")
	private Double leverMoney;

	/**
	 * 倍数
	 */
	@SqlColumn
	@AllowExcel(name = "倍数")
	private Integer lever;

	/**
	 * 天数（申请操盘的天数）
	 */
	@SqlColumn
	@AllowExcel(name = "天数")
	private Integer opDay;

	/**
	 * 管理费
	 */
	@SqlColumn
	@AllowExcel(name = "管理费")
	private BigDecimal managerMoney;

	/**
	 * 配资管理费撤回
	 */
	@SqlColumn
	@AllowExcel(name = "配资管理费撤回")
	private BigDecimal revokeManagerMoney;

	/**
	 * 应收利息
	 */
	@SqlColumn
	@AllowExcel(name = "应收利息")
	private BigDecimal interestMoney;

	/**
	 * 配资利息撤回
	 */
	@SqlColumn
	@AllowExcel(name = "配资利息撤回")
	private BigDecimal revokeInterest;

	/**
	 * 抵扣金额
	 */
	@SqlColumn
	@AllowExcel(name = "抵扣金额")
	private BigDecimal deductMoney;

	/**
	 * 实收利息
	 */
	@SqlColumn
	@AllowExcel(name = "实收利息")
	private BigDecimal realIncomeMoney;

	/**
	 * 佣金差
	 */
	@SqlColumn
	@AllowExcel(name = "佣金差")
	private BigDecimal freemoney;

	/**
	 * 过户费差
	 */
	@SqlColumn
	@AllowExcel(name = "过户费差")
	private BigDecimal transmoney;

	/**
	 * 收益小计
	 */
	@SqlColumn
	@AllowExcel(name = "收益小计")
	private BigDecimal totalmoney;
	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getTransmoney() {
		this.transmoney = TypeConvert.scale(this.transmoney, TypeConvert.SCALE_VALUE);
		return transmoney;
	}

	public void setTransmoney(BigDecimal transmoney) {
		this.transmoney = transmoney;
	}

	public BigDecimal getTotalmoney() {
		this.totalmoney = TypeConvert.scale(this.totalmoney, TypeConvert.SCALE_VALUE);
		return totalmoney;
	}

	public void setTotalmoney(BigDecimal totalmoney) {
		this.totalmoney = totalmoney;
	}

	public BigDecimal getFreemoney() {
		this.freemoney = TypeConvert.scale(this.freemoney, TypeConvert.SCALE_VALUE);
		return freemoney;
	}

	public BigDecimal getDeductMoney() {
		return deductMoney;
	}

	public void setDeductMoney(BigDecimal deductMoney) {
		this.deductMoney = deductMoney;
	}

	public void setFreemoney(BigDecimal freemoney) {
		this.freemoney = freemoney;
	}

	public BigDecimal getRealIncomeMoney() {
		if (this.interestMoney != null && this.deductMoney != null) {
			this.realIncomeMoney = this.interestMoney.subtract(this.deductMoney, MathContext.DECIMAL32);
			this.realIncomeMoney = TypeConvert.scale(this.realIncomeMoney, TypeConvert.SCALE_VALUE);
		}
		return realIncomeMoney;
	}

	public void setRealIncomeMoney(BigDecimal realIncomeMoney) {
		this.realIncomeMoney = realIncomeMoney;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHsBelongBroker() {
		return hsBelongBroker;
	}

	public void setHsBelongBroker(String hsBelongBroker) {
		this.hsBelongBroker = hsBelongBroker;
	}

	public Double getLeverMoney() {
		if (this.leverMoney != null) {
			BigDecimal leverMoneyBig = TypeConvert.scale(new BigDecimal(this.leverMoney), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.leverMoney = leverMoneyBig.doubleValue();
			}
		}
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public Integer getOpDay() {
		return opDay;
	}

	public void setOpDay(Integer opDay) {
		this.opDay = opDay;
	}

	public BigDecimal getManagerMoney() {
		this.managerMoney = TypeConvert.scale(this.managerMoney, TypeConvert.SCALE_VALUE);
		return managerMoney;
	}

	public void setManagerMoney(BigDecimal managerMoney) {
		this.managerMoney = managerMoney;
	}

	public BigDecimal getInterestMoney() {
		this.interestMoney = TypeConvert.scale(this.interestMoney, TypeConvert.SCALE_VALUE);
		return interestMoney;
	}

	public void setInterestMoney(BigDecimal interestMoney) {
		this.interestMoney = interestMoney;
	}

	public BigDecimal getRevokeManagerMoney() {
		return revokeManagerMoney;
	}

	public void setRevokeManagerMoney(BigDecimal revokeManagerMoney) {
		this.revokeManagerMoney = revokeManagerMoney;
	}

	public BigDecimal getRevokeInterest() {
		return revokeInterest;
	}

	public void setRevokeInterest(BigDecimal revokeInterest) {
		this.revokeInterest = revokeInterest;
	}

}
