package com.tzdr.domain.web.entity.reports;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import jodd.util.StringUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.SqlColumn;

/**
 * 用户资金记录
 * 
 * @zhouchen 2015年4月7日
 */
@Entity
@Table(name = "w_userfund_record")
public class UserFundsRecord implements Serializable {

	@Id
	@Column(name = "id", length = 32, nullable = false)
	protected String id;

	/**
	 * 资金记录时间
	 */
	private Long fundDate = Dates.getDatebyDaynum(-1);

	/**
	 * 
	 */
	private static final long serialVersionUID = 678269563751545625L;

	/**
	 * 用户名
	 */
	@SqlColumn
	@AllowExcel(name = "用户名")
	private String mobile;

	/**
	 * 真实姓名
	 */
	@SqlColumn
	@AllowExcel(name = "真实姓名")
	private String realName;

	/**
	 * 保证金（卡）
	 */
	@SqlColumn
	@AllowExcel(name = "保证金（卡）")
	private double cardCapitalMargin;

	/**
	 * 保证金（余额）
	 */
	@SqlColumn
	@AllowExcel(name = "保证金（余额）")
	private double balanceCapitalMargin;

	/**
	 * 配资金额
	 */
	@SqlColumn
	@AllowExcel(name = "配资金额")
	private double amountCapital;

	/**
	 * 前一日余额
	 */
	@SqlColumn
	@AllowExcel(name = "前一日余额")
	private double lastdayBalance;

	/**
	 * 收入（充值）
	 */
	@SqlColumn
	@AllowExcel(name = "收入（充值）")
	private double incomeRecharge;

	/**
	 * 收入（返利）profit and loss
	 */
	@SqlColumn
	@AllowExcel(name = "收入（返利）")
	private double incomeRebate;

	/**
	 * 收入（其它）
	 */
	@SqlColumn
	@AllowExcel(name = "收入（其它）")
	private double incomeOther = 0d;

	/**
	 * 盈利
	 */
	@SqlColumn
	@AllowExcel(name = "盈利")
	private double profit;

	/**
	 * 管理费
	 */
	@SqlColumn
	@AllowExcel(name = "管理费")
	private double managementFee;

	/**
	 * 盈利分成
	 */
	@SqlColumn
	@AllowExcel(name = "盈利分成")
	private double profitMoney;

	// '配资管理费撤回',
	@SqlColumn
	@AllowExcel(name = "配资管理费撤回")
	private double revokeManagerMoney;

	/**
	 * 利息
	 */
	@SqlColumn
	@AllowExcel(name = "利息")
	private double interestFee;

	@SqlColumn
	@AllowExcel(name = "配资利息撤回")
	private double revokeInterest;

	/**
	 * 抵扣金额
	 */
	@SqlColumn
	@AllowExcel(name = "抵扣金额")
	private double deductionFee;

	/**
	 * 补仓欠费
	 */
	@SqlColumn
	@AllowExcel(name = "补仓欠费")
	private double coverMoney;

	/**
	 * 实际利息
	 */
	@SqlColumn
	@AllowExcel(name = "实际利息")
	private double actualFee;

	/**
	 * 提现
	 */
	@SqlColumn
	@AllowExcel(name = "提现")
	private double drawFee;

	/**
	 * 提现(冻结中)
	 */
	@SqlColumn
	@AllowExcel(name = "提现(冻结中)")
	private double drawingFee;

	/**
	 * 账户资产
	 */
	@SqlColumn
	@AllowExcel(name = "账户资产")
	private double allMoney;

	/**
	 * 平台余额
	 */
	@SqlColumn
	@AllowExcel(name = "平台余额")
	private double platBalance;

	/**
	 * 用户id
	 */
	@SqlColumn
	private String uid;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public double getBalanceCapitalMargin() {
		return BigDecimalUtils.round(balanceCapitalMargin, 2);
	}

	public void setBalanceCapitalMargin(double balanceCapitalMargin) {
		this.balanceCapitalMargin = balanceCapitalMargin;
	}

	public double getAmountCapital() {
		return BigDecimalUtils.round(amountCapital, 2);
	}

	public void setAmountCapital(double amountCapital) {
		this.amountCapital = amountCapital;
	}

	public double getIncomeRecharge() {
		return BigDecimalUtils.round(incomeRecharge, 2);
	}

	public void setIncomeRecharge(double incomeRecharge) {
		this.incomeRecharge = incomeRecharge;
	}

	public double getIncomeRebate() {
		return BigDecimalUtils.round(incomeRebate, 2);
	}

	public void setIncomeRebate(double incomeRebate) {
		this.incomeRebate = incomeRebate;
	}

	public double getProfit() {
		return BigDecimalUtils.round(profit, 2);
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getManagementFee() {
		return BigDecimalUtils.round(managementFee, 2);
	}

	public void setManagementFee(double managementFee) {
		this.managementFee = managementFee;
	}

	public double getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(double profitMoney) {
		this.profitMoney = profitMoney;
	}

	public double getInterestFee() {
		return BigDecimalUtils.round(interestFee, 2);
	}

	public void setInterestFee(double interestFee) {
		this.interestFee = interestFee;
	}

	public double getDrawFee() {
		return BigDecimalUtils.round(drawFee, 2);
	}

	public void setDrawFee(double drawFee) {
		this.drawFee = drawFee;
	}

	public double getDrawingFee() {
		return BigDecimalUtils.round(drawingFee, 2);
	}

	public void setDrawingFee(double drawingFee) {
		this.drawingFee = drawingFee;
	}

	public double getCardCapitalMargin() {
		return cardCapitalMargin;
	}

	public void setCardCapitalMargin(double cardCapitalMargin) {
		this.cardCapitalMargin = cardCapitalMargin;
	}

	public double getLastdayBalance() {
		return BigDecimalUtils.round(lastdayBalance, 2);
	}

	public void setLastdayBalance(double lastdayBalance) {
		this.lastdayBalance = lastdayBalance;
	}

	public double getAllMoney() {
		return BigDecimalUtils.round(allMoney, 2);
	}

	public void setAllMoney(double allMoney) {
		this.allMoney = allMoney;
	}

	public double getPlatBalance() {
		return BigDecimalUtils.round(platBalance, 2);
	}

	public void setPlatBalance(double platBalance) {
		this.platBalance = platBalance;
	}

	public double getIncomeOther() {
		return incomeOther;
	}

	public void setIncomeOther(double incomeOther) {
		this.incomeOther = incomeOther;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getFundDate() {
		return fundDate;
	}

	public void setFundDate(Long fundDate) {
		this.fundDate = fundDate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public double getDeductionFee() {
		return BigDecimalUtils.round(deductionFee, 2);
	}

	public void setDeductionFee(double deductionFee) {
		this.deductionFee = deductionFee;
	}

	public double getActualFee() {
		return BigDecimalUtils.round(actualFee, 2);
	}

	public void setActualFee(double actualFee) {
		this.actualFee = actualFee;
	}

	public Object[] getObjectArray() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		if (StringUtil.isBlank(this.realName) || "null".equals(this.realName)) {
			this.realName = " ";
		}
		return new Object[] { uuid, this.allMoney, this.amountCapital,
				this.balanceCapitalMargin, this.cardCapitalMargin,
				this.drawFee, this.drawingFee, this.fundDate, this.incomeOther,
				this.incomeRebate, this.incomeRecharge, this.interestFee,
				this.lastdayBalance, this.managementFee, this.mobile,
				this.platBalance, this.profit, this.realName, this.uid,
				this.deductionFee, this.actualFee, this.revokeManagerMoney,
				this.revokeInterest, this.coverMoney,this.profitMoney };
	}

	public double getRevokeManagerMoney() {
		return revokeManagerMoney;
	}

	public void setRevokeManagerMoney(double revokeManagerMoney) {
		this.revokeManagerMoney = revokeManagerMoney;
	}

	public double getRevokeInterest() {
		return revokeInterest;
	}

	public void setRevokeInterest(double revokeInterest) {
		this.revokeInterest = revokeInterest;
	}

	public double getCoverMoney() {
		return coverMoney;
	}

	public void setCoverMoney(double coverMoney) {
		this.coverMoney = coverMoney;
	}
}
