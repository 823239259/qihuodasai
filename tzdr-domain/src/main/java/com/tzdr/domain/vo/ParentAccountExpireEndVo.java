package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * <p>母账户到期须平仓和终止方案列表</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年5月11日上午10:13:17
 */
public class ParentAccountExpireEndVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	@SqlColumn(name="group_id")
	private String groupId;
	
	@SqlColumn
	private String mobile;
	
	@SqlColumn
	private String tname;
	
	//方案编号
	@SqlColumn(name="program_no")
	private String programNo;
	
	//恒生账户
	@SqlColumn
	private String account;
	
	//所属母账户
	@SqlColumn
	private String parentAccountName;
	
	@SqlColumn(name="allocation_date")
	private Long allocationDate;
	
	private String allocationDateStr;
	
	/**
	 * 方案开始时间
	*/
	@SqlColumn
	private Long starttime;
	
	private String starttimeStr;
	
	/**
	 * 方案配资天数
	*/
	@SqlColumn
	private Integer naturalDays;
	
	@SqlColumn
	private String parentAccountId;
	
	public String getParentAccountId() {
		return parentAccountId;
	}
	public void setParentAccountId(String parentAccountId) {
		this.parentAccountId = parentAccountId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getProgramNo() {
		return programNo;
	}
	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getParentAccountName() {
		return parentAccountName;
	}
	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}
	public Long getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(Long allocationDate) {
		this.allocationDate = allocationDate;
	}
	public Long getStarttime() {
		return starttime;
	}
	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}
	
	public Integer getNaturalDays() {
		return naturalDays;
	}
	public void setNaturalDays(Integer naturalDays) {
		this.naturalDays = naturalDays;
	}
	
	public String getAllocationDateStr() {
		if (this.allocationDate != null) {
			this.allocationDateStr = TypeConvert.long1000ToDateStr(this.allocationDate);
		}
		return allocationDateStr;
	}
	
	public void setAllocationDateStr(String allocationDateStr) {
		this.allocationDateStr = allocationDateStr;
	}
	
	public String getStarttimeStr() {
		if (this.starttime != null) {
			this.starttimeStr = TypeConvert.long1000ToDateStr(starttime);
		}
		return starttimeStr;
	}
	
	public void setStarttimeStr(String starttimeStr) {
		this.starttimeStr = starttimeStr;
	}

	
}
