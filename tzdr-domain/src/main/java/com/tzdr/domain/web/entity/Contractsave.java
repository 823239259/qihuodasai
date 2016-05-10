package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 第三方合同保存
 * @author 张军
 *
 */

@Entity
@Table(name="w_contract_list")
public class Contractsave extends BaseCrudEntity{
	private String seckey;//数字指纹;
	private Long savedate; //保存日期
	private String uid;//用户id
	private Long saveid;//保全id 
	private String programNo;//配资协议
	private String contactNo;//合同编号
	private String linkUrl;//链接地址
	public String getSeckey() {
		return seckey;
	}
	public void setSeckey(String seckey) {
		this.seckey = seckey;
	}
	public Long getSavedate() {
		return savedate;
	}
	public void setSavedate(Long savedate) {
		this.savedate = savedate;
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
