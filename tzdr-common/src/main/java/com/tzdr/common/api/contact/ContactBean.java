package com.tzdr.common.api.contact;

public class ContactBean {

	private String souceRegId;//来源企业用户ID
	private double amout;//合同金额
	private String contactNumber;//合同编号
	private String phone;//用户电话
	private String email;//用户email
	private String title;//标题
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSouceRegId() {
		return souceRegId;
	}
	public void setSouceRegId(String souceRegId) {
		this.souceRegId = souceRegId;
	}
	public double getAmout() {
		return amout;
	}
	public void setAmout(double amout) {
		this.amout = amout;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
