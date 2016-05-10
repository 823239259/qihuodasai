package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月12日下午3:54:40
 */
public class SumSingleVo implements Serializable {
	
	private static final long serialVersionUID = 329407365004258063L;
	
	@SqlColumn
	private BigInteger total;

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}
	
	

}
