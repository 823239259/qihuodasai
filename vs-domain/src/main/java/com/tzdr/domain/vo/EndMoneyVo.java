package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 
 * @version 2.0
 * 2015年1月23日下午5:01:27
 */
public class EndMoneyVo implements Serializable {

	private static final long serialVersionUID = 8933919444128515078L;
	//配资金额
	@SqlColumn
	private Double money;
	//保证金
	@SqlColumn
	private Double leverMoney;
	
	/**
	 * 追加保证金
	 */
	@SqlColumn
	private Double appendLeverMoney;
	
	/**
	 * 配资金额
	 * @return Double
	 */
	public Double getMoney() {
		return money;
	}
	
	/**
	 * 配资金额
	 * @param money Double
	 */
	public void setMoney(Double money) {
		this.money = money;
	}
	
	/**
	 * 保证金
	 * @return Double
	 */
	public Double getLeverMoney() {
		return leverMoney;
	}
	
	/**
	 * 保证金
	 * @param leverMoney Double
	 */
	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	/**
	 * 追加保证金
	 * @return Double
	 */
	public Double getAppendLeverMoney() {
		return appendLeverMoney;
	}

	/**
	 * 追加保证金
	 * @param appendLeverMoney Double
	 */
	public void setAppendLeverMoney(Double appendLeverMoney) {
		this.appendLeverMoney = appendLeverMoney;
	}

}
