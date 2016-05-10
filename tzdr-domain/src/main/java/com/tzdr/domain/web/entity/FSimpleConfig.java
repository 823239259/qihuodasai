package com.tzdr.domain.web.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

@Entity
@Table(name = "f_simple_config")
public class FSimpleConfig extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 交易品种类型【0.富时A50；5：富时A50方案参数设置  1.沪金     2.沪银   3.沪铜   4.橡胶   6.原油    7. 恒指】
	 */
	private Integer type;
	
	/**
	 * 交易品种名称
	 */
	private String typeName;
	
	/**
	 * 手续费
	 */
	private BigDecimal tranFees;
	
	/**
	 * 手续费配资组
	 */
	private String tranFeesArray;
	
	/**
	 * 管理费
	 */
	private BigDecimal feeManage;
	
	/**
	 * 推荐开仓手数
	 */
	private String tranLever;

	/**
	 * 单手保证金(人民币)
	 */
	private BigDecimal traderBond;
	
	/**
	 * 单手操盘金(A50 美元    其他 人民币)
	 */
	private BigDecimal traderMoney;
	
	/**
	 * 单手平仓线(A50 美元    其他 人民币)
	 */
	private BigDecimal lineLoss;

	
	/**
	 * 入金金额(美元)
	 */
	private BigDecimal goldenMoney;
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "type_name")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "tran_fees")
	public BigDecimal getTranFees() {
		return tranFees;
	}

	public void setTranFees(BigDecimal tranFees) {
		this.tranFees = tranFees;
	}
	@Column(name = "tran_fees_array")
	public String getTranFeesArray() {
		return tranFeesArray;
	}

	public void setTranFeesArray(String tranFeesArray) {
		this.tranFeesArray = tranFeesArray;
	}

	@Column(name = "fee_manage")
	public BigDecimal getFeeManage() {
		return feeManage;
	}

	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}

	@Column(name = "tran_lever")
	public String getTranLever() {
		return tranLever;
	}

	public void setTranLever(String tranLever) {
		this.tranLever = tranLever;
	}

	@Column(name = "trader_bond")
	public BigDecimal getTraderBond() {
		return traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}

	@Column(name = "trader_money")
	public BigDecimal getTraderMoney() {
		return traderMoney;
	}

	public void setTraderMoney(BigDecimal traderMoney) {
		this.traderMoney = traderMoney;
	}

	@Column(name = "line_loss")
	public BigDecimal getLineLoss() {
		return lineLoss;
	}

	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
	}
	
	@Column(name = "golden_money")
	public BigDecimal getGoldenMoney() {
		return goldenMoney;
	}

	public void setGoldenMoney(BigDecimal goldenMoney) {
		this.goldenMoney = goldenMoney;
	}
}