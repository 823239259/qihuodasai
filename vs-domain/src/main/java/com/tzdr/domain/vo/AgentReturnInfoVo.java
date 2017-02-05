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
public class AgentReturnInfoVo  implements Serializable {

	private static final long serialVersionUID = 2184541443927372029L;

	@SqlColumn	
	private String uid;
	
	@SqlColumn(name="add_date")
	private Integer addDate;
	
	private String addDateStr;

	@SqlColumn(name="child_number")
	private Integer childNumber;
	
	@SqlColumn(name="all_child_number")
	private Integer allChildNumber;
	
	@SqlColumn(name="total_amount")
	private BigDecimal totalAmount;
	
	private String totalAmountStr;
	
	@SqlColumn(name="return_amount")
	private BigDecimal returnAmount;
	
	private String returnAmountStr;
	
	public String getAddDateStr() {
		if (this.addDate != null) {
			this.addDateStr = TypeConvert.long1000ToDateStr(this.addDate.longValue());
		}
		return addDateStr;
	}

	public void setAddDateStr(String addDateStr) {
		this.addDateStr = addDateStr;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getAddDate() {
		return addDate;
	}

	public void setAddDate(Integer addDate) {
		this.addDate = addDate;
	}

	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}

	public Integer getAllChildNumber() {
		return allChildNumber;
	}

	public void setAllChildNumber(Integer allChildNumber) {
		this.allChildNumber = allChildNumber;
	}

	public BigDecimal getTotalAmount() {
		if (totalAmount == null) {
			totalAmount = new BigDecimal("0.00");
		}
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getReturnAmount() {
		if (returnAmount == null) {
			returnAmount = new BigDecimal("0.00");
		}
		
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getTotalAmountStr() {
		return totalAmountStr = TypeConvert.scale(this.totalAmount, 2).toString();
	}

	public void setTotalAmountStr(String totalAmountStr) {
		this.totalAmountStr = totalAmountStr;
	}

	public String getReturnAmountStr() {
		return returnAmountStr = TypeConvert.scale(this.returnAmount, 2).toString();
	}

	public void setReturnAmountStr(String returnAmountStr) {
		this.returnAmountStr = returnAmountStr;
	}
	

}