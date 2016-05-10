package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年1月29日下午5:52:30
 */
public class TransferVo implements Serializable {
	
	private static final long serialVersionUID = -5357039825226682524L;
	
	@SqlColumn
	private String id;

	/**
	 * 母账户的 编号
	 */
	@SqlColumn
	private String parentAccountNo;
	
	/**
	 * 组合编号
	 */
	@SqlColumn
	private String combineId;
	
	@SqlColumn
	private Double finishedMoney;

	public String getParentAccountNo() {
		return parentAccountNo;
	}

	public void setParentAccountNo(String parentAccountNo) {
		this.parentAccountNo = parentAccountNo;
	}

	public String getCombineId() {
		return combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

}
