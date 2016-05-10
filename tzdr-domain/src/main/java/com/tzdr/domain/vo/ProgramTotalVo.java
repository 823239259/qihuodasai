package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年4月17日下午5:37:11
 */
public class ProgramTotalVo  implements Serializable {

	private static final long serialVersionUID = 2184541443927372029L;

	
	@SqlColumn
	private Integer programNum;
	
	@SqlColumn
	private Double money;

	public Integer getProgramNum() {
		return programNum;
	}

	public void setProgramNum(Integer programNum) {
		this.programNum = programNum;
	}

	public Double getMoney() {
		if (money != null) {
			money = TypeConvert.scale(new BigDecimal(money), TypeConvert.SCALE_VALUE).doubleValue();
		}
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

}