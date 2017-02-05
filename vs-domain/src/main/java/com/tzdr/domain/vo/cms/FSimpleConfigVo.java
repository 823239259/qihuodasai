package com.tzdr.domain.vo.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.tzdr.common.utils.Dates;

/**
 * 商品期货参数VO
 * @Description: 
 * @author liuhaichuan
 * @date 2015年9月18日
 *
 */
public class FSimpleConfigVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	/**
	 * 交易品种类型【0.富时A50 1.沪金 2.沪银 3.沪铜 4.橡胶】
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
	 * 单手操盘金(人民币)
	 */
	private BigDecimal traderMoney;

	/**
	 * 单手平仓线(人民币)
	 */
	private BigDecimal lineLoss;

	/**
	 * 更新时间
	 */
	private BigInteger updateTime;
	private String updateTimeValue;
	
	/**
	 * 更新人username（包括删除）
	 */
	private String updateUser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigDecimal getTranFees() {
		return tranFees;
	}

	public void setTranFees(BigDecimal tranFees) {
		this.tranFees = tranFees;
	}

	public BigDecimal getFeeManage() {
		return feeManage;
	}

	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}

	public String getTranLever() {
		return tranLever;
	}

	public void setTranLever(String tranLever) {
		this.tranLever = tranLever;
	}

	public BigDecimal getTraderBond() {
		return traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}

	public BigDecimal getTraderMoney() {
		return traderMoney;
	}

	public void setTraderMoney(BigDecimal traderMoney) {
		this.traderMoney = traderMoney;
	}

	public BigDecimal getLineLoss() {
		return lineLoss;
	}

	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public BigInteger getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(BigInteger updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTimeValue() {
		if (this.updateTime != null) {
			this.updateTimeValue = Dates.format(Dates.parseLong2Date(updateTime.longValue()));
		}
		return updateTimeValue;
	}

	public void setUpdateTimeValue(String updateTimeValue) {
		this.updateTimeValue = updateTimeValue;
	}

	public String getTranFeesArray() {
		return tranFeesArray;
	}

	public void setTranFeesArray(String tranFeesArray) {
		this.tranFeesArray = tranFeesArray;
	}

	
}