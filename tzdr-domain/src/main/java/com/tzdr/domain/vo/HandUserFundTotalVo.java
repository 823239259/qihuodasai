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
public class HandUserFundTotalVo implements Serializable {
	
    private static final long serialVersionUID = 6997408108243390441L;
	
	
	//充值金额
	@SqlColumn
	private Double money;
	
	private String moneyStr;

	public HandUserFundTotalVo() {
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
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
	

}
