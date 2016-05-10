package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年4月14日下午4:36:51
 */
public class AllRechargeTotalVo implements Serializable {
	
    private static final long serialVersionUID = 6997408108243390441L;
	
	
	//充值金额
	@SqlColumn
	private Double money;
	
	private String moneyStr;
	
	//实际到账金额
	private Double actualMoney;
	
	private String actualMoneyStr;

	public AllRechargeTotalVo() {
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	@SqlColumn
	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	public String getMoneyStr() {
		
		if (this.getMoney() != null) {
			BigDecimal moneyVar = new BigDecimal(this.getMoney());
			BigDecimal moneyBig = 
					TypeConvert.scale(moneyVar, TypeConvert.SCALE_VALUE);
			this.moneyStr = moneyBig == null?"":moneyBig.toString();
		}
		
		return moneyStr;
	}

	public void setMoneyStr(String moneyStr) {
		this.moneyStr = moneyStr;
	}


	public String getActualMoneyStr() {
		if (this.getActualMoney() != null) {
			BigDecimal actualMoneyVar = new BigDecimal(this.getActualMoney());
			BigDecimal actualMoneyBig = 
					TypeConvert.scale(actualMoneyVar, TypeConvert.SCALE_VALUE);
			this.actualMoneyStr = actualMoneyBig == null?"":actualMoneyBig.toString();
		}
		return actualMoneyStr;
	}


	public void setActualMoneyStr(String actualMoneyStr) {
		this.actualMoneyStr = actualMoneyStr;
	}
	
	
	
	

}
