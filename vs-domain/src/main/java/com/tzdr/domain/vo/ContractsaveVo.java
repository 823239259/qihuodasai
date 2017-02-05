package com.tzdr.domain.vo;

import com.tzdr.common.utils.SqlColumn;

public class ContractsaveVo {
	@SqlColumn 
	private String seckey;//数字指纹;
	@SqlColumn 
	private Long savedate; //保存日期
	public String getSavetime() {
		return savetime;
	}
	public Long getSavedate() {
		return savedate;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	public void setSavedate(Long savedate) {
		this.savedate = savedate;
	}
	@SqlColumn 
	private String savetime; //保存日期
	@SqlColumn 
	private String uid;//用户id
	@SqlColumn 
	private Long saveid;//保全id 
	@SqlColumn 
	private String programNo;//配资协议
	@SqlColumn 
	private String contactNo;//合同编号
	@SqlColumn 
	private String linkUrl;//链接地址
	@SqlColumn 
	private String tname;
	@SqlColumn 
	private String mobile;
	@SqlColumn
	private String tradetime;
	
	private String tradestarttime;
	private String tradeendtime;
	private String savestarttime;
	private String saveendtime;
	private String viewsaveinfo;
	public String getViewsaveinfo() {
		return viewsaveinfo;
	}
	public void setViewsaveinfo(String viewsaveinfo) {
		this.viewsaveinfo = viewsaveinfo;
	}
	public String getTradestarttime() {
		return tradestarttime;
	}
	public void setTradestarttime(String tradestarttime) {
		this.tradestarttime = tradestarttime;
	}
	public String getTradeendtime() {
		return tradeendtime;
	}
	public void setTradeendtime(String tradeendtime) {
		this.tradeendtime = tradeendtime;
	}
	public String getSavestarttime() {
		return savestarttime;
	}
	public void setSavestarttime(String savestarttime) {
		this.savestarttime = savestarttime;
	}
	public String getSaveendtime() {
		return saveendtime;
	}
	public void setSaveendtime(String saveendtime) {
		this.saveendtime = saveendtime;
	}
	public String getTradetime() {
		return tradetime;
	}
	public void setTradetime(String tradetime) {
		this.tradetime = tradetime;
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
	public String getSeckey() {
		return seckey;
	}
	public void setSeckey(String seckey) {
		this.seckey = seckey;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Long getSaveid() {
		return saveid;
	}
	public void setSaveid(Long saveid) {
		this.saveid = saveid;
	}
	public String getProgramNo() {
		return programNo;
	}
	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
}
