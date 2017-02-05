package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 合计VO
 * @version 2.0
 * 2015年1月17日下午2:58:21
 */
public class TotalMarginVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -285940015464262355L;
	
	@SqlColumn
	private Double totalAmount;

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
