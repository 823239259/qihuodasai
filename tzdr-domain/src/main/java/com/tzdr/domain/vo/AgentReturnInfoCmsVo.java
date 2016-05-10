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
public class AgentReturnInfoCmsVo  implements Serializable {

	private static final long serialVersionUID = 2184541443927372029L;

	
	@SqlColumn
	private String id;
	
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
	
	@SqlColumn(name="return_amount")
	private BigDecimal returnAmount;
	
	@SqlColumn(name="scheme_number")
	private Integer schemeNumber;
	
	@SqlColumn
	private String tname;
	@SqlColumn
	private String mobile;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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

	public Integer getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(Integer schemeNumber) {
		this.schemeNumber = schemeNumber;
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
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

}