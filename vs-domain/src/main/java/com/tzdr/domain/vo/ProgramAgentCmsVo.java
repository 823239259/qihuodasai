package com.tzdr.domain.vo;

import java.io.Serializable;

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
public class ProgramAgentCmsVo  implements Serializable {

	private static final long serialVersionUID = 2184541443927372029L;

	
	@SqlColumn
	private String programId;
	
	@SqlColumn
	private Double leverMoney;
	
	@SqlColumn
	private Integer lever;
	
	@SqlColumn
	private Integer starttime;
	
	private String starttimeStr;
	
	@SqlColumn
	private Integer endtime;
	
	private String endtimeStr;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public Double getLeverMoney() {
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public Integer getStarttime() {
		return starttime;
	}

	public void setStarttime(Integer starttime) {
		this.starttime = starttime;
	}

	public Integer getEndtime() {
		return endtime;
	}

	public void setEndtime(Integer endtime) {
		this.endtime = endtime;
	}

	public String getStarttimeStr() {
		if (this.starttime != null) {
			this.starttimeStr = TypeConvert.long1000ToDateStr(this.starttime.longValue());
		}
		return starttimeStr;
	}

	public void setStarttimeStr(String starttimeStr) {
		this.starttimeStr = starttimeStr;
	}

	public String getEndtimeStr() {
		if (this.endtime != null) {
			this.endtimeStr = TypeConvert.long1000ToDateStr(this.endtime.longValue());
		}
		return endtimeStr;
	}

	public void setEndtimeStr(String endtimeStr) {
		this.endtimeStr = endtimeStr;
	}
	

}