package com.tzdr.domain.vo.activity;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;

public class OldAndNewVo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 电话
	 */
	@SqlColumn(name="mobile")
	private String mobile;
	/**
	 * 注册时间	
	 */
	@SqlColumn(name="ctime")
	private Long ctime;
	/**
	 * 实名
	 */
	@SqlColumn(name="tname")
	private String tname;
	/**
	 * 第一手交易结算时间
	 */
	@SqlColumn(name="end_time")
	private Long endTime;
	/**
	 * 第一手交易交易手数
	 */
	@SqlColumn(name="sumLever")
	private Integer sumLever;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getSumLever() {
		return sumLever;
	}
	public void setSumLever(Integer sumLever) {
		this.sumLever = sumLever;
	}
	
}
	
