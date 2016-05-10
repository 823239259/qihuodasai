package com.tzdr.domain.vo;

import java.io.Serializable;

public class CustomerDetailsVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 详情编号
	 */
	private String id;
	
	/**
	 * 客户编号
	 */
	private String customerId;

	/**
	 * 联系时间
	 */
	private Long contactTime;
	
	/**
	 * 描述
	 */
	private String remark;

	/**
	 * 创建人id
	 */
	private Long  createUserId;
	/**
	 * 创建人username
	 */
	private  String createUser;

	/**
	 * 创建时间
	 */
	private  Long  createTime;

	/**
	 * 更新人（包括删除） id
	 */
	private  Long updateUserId;
	/**
	 * 更新人username（包括删除）
	 */
	private  String updateUser;

	/**
	 * 更新时间
	 */
	private  Long  updateTime;
	
	/**
	 * 联系时间(字符串)
	 */
	private String contactTimeStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Long getContactTime() {
		return contactTime;
	}

	public void setContactTime(Long contactTime) {
		this.contactTime = contactTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getContactTimeStr() {
		return contactTimeStr;
	}

	public void setContactTimeStr(String contactTimeStr) {
		this.contactTimeStr = contactTimeStr;
	}
}
