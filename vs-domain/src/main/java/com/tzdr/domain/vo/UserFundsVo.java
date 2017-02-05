package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.SqlColumn;


/**
 * 用户资金报表 Vo
 * @author Lin Feng
 *
 */
public class UserFundsVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 678269563751545625L;
	
	/**
	 * 用户名
	 */
	@SqlColumn
	@AllowExcel(name="用户名")
	private String mobile;
	
	/**
	 * 真实姓名
	 */
	@SqlColumn
	@AllowExcel(name="真实姓名")
	private String realName;
	
	/**
	 * 保证金（卡）
	 */
	@SqlColumn
	@AllowExcel(name="保证金(卡券)")
	private BigDecimal cardCapitalMargin;
	
	/**
	 * 保证金（余额）
	 */
	@SqlColumn
	@AllowExcel(name="保证金（余额）")
	private double balanceCapitalMargin;
	
	/**
	 * 配资金额
	 */
	@SqlColumn
	@AllowExcel(name="配资金额")
	private double amountCapital;
	
	/**
	 * 前一日余额
	 */
	@SqlColumn
	@AllowExcel(name="前一日余额")
	private double lastdayBalance;
	
	/**
	 * 收入（充值）
	 */
	@SqlColumn
	@AllowExcel(name="收入（充值）")
	private double incomeRecharge;
	
	/**
	 * 收入（返利）profit and loss
	 */
	@SqlColumn
	@AllowExcel(name="收入（返利）")
	private double incomeRebate;
	
	/**
	 * 收入（其它）
	 */
	@AllowExcel(name="收入（其它）")
	private double incomeOther=0d;
	
	/**
	 * 盈利
	 */
	@SqlColumn
	@AllowExcel(name="实现盈亏")
	private double profit;
	
	/**
	 * 管理费
	 */
	@SqlColumn
	@AllowExcel(name="管理费")
	private double managementFee;
	
	
	//'配资管理费撤回',
	@SqlColumn
	@AllowExcel(name="配资管理费撤回")
	private double revokeManagerMoney;
				
	
	/**
	 * 利息
	 */
	@SqlColumn
	@AllowExcel(name="利息")
	private double interestFee;
	
	
	
	@SqlColumn
	@AllowExcel(name="配资利息撤回")
	private double revokeInterest;
	
	/**
	 * 抵扣金额
	 */
	@SqlColumn
	@AllowExcel(name="抵扣金额")
	private double deductionFee;
	
	
	/**
	 * 实际利息
	 */
	@SqlColumn
	@AllowExcel(name="实际利息")
	private double actualFee;
	
	/**
	 * 提现
	 */
	@SqlColumn
	@AllowExcel(name="提现成功")
	private double drawFee;
	
	/**
	 * 提现(冻结中)
	 */
	@SqlColumn
	@AllowExcel(name="提现处理中")
	private double drawingFee;
	
	/**
	 * 补仓欠费
	 */
	@SqlColumn
	@AllowExcel(name="补仓欠费")
	private double coverMoney;
	
	/**
	 * 账户资产
	 */
	@SqlColumn
	@AllowExcel(name="账户总资产")
	private double allMoney;
	
	/**
	 * 平台余额
	 */
	@SqlColumn
	@AllowExcel(name="平台余额")
	private double platBalance;

	
	/**
	 * 资金记录时间
	 */
	@SqlColumn
	private BigInteger fundDate;
	
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



	public BigDecimal getCardCapitalMargin() {
		return cardCapitalMargin;
	}

	public void setCardCapitalMargin(BigDecimal cardCapitalMargin) {
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

	public BigInteger getFundDate() {
		return fundDate;
	}

	public void setFundDate(BigInteger fundDate) {
		this.fundDate = fundDate;
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
		return BigDecimalUtils.round(coverMoney, 2);
	}

	public void setCoverMoney(double coverMoney) {
		this.coverMoney = coverMoney;
	}
	
}
