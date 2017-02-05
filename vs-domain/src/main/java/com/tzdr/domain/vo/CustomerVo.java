package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;

public class CustomerVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@SqlColumn
	private String id;
	
	/**
	 * 姓名
	 */
	@SqlColumn
	private String name;
	
	/**
	 * 手机号码
	 */
	@SqlColumn
	private String mobile;
	
	/**
	 * 是否注册 0：否  1：是
	 */
	@SqlColumn
	private Integer isSignIn;
	
	/**
	 * 注册时间
	 */
	@SqlColumn
	private Long signInTime;
	
	/**
	 * 是否配资
	 */
	@SqlColumn
	private Integer isTrade;
	
	/**
	 * 首次配资时间
	 */
	@SqlColumn
	private Long firstTradeTime;
	
	/**
	 * 最后配资时间
	 */
	@SqlColumn
	private Long lastTradeTime;
	
	/**
	 * 最后联系时间
	 */
	@SqlColumn
	private Long lastContactTime;
	
	/**
	 * 联系时间
	 */
	@SqlColumn
	public Long contactTime;

	/**
	 * 所属人
	 */
	@SqlColumn
	private Long belongMarket;

	/**
	 *  所属人名称
	 */
	@SqlColumn
	public String realName;
	
	/**
	 *  所属组织code
	 */
	@SqlColumn
	public String organizationCode;
	
	/**
	 * 创建时间
	 */
	@SqlColumn
	private Long createTime;
	
	/**
	 * 分配时间
	 */
	@SqlColumn
	private Long assignTime;
	
	
	/**
	 * 联系情况
	 */
	public String remark;
	
	/**
	 * 联系时间(字符串)
	 */
	public String contactTimeStr;
	
	/**
	 * 查询条件
	 */
	public String startSignInTime;
	
	public String endSignInTime;
	
	public String startFirstTradeTime;
	
	public String endFirstTradeTime;
	
	public String startLastTradeTime;
	
	public String endLastTradeTime;
	
	public String startLastContactTime;
	
	public String endLastContactTime;
	
	public String startCreateTime;
	
	public String endCreateTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsSignIn() {
		return isSignIn;
	}

	public void setIsSignIn(Integer isSignIn) {
		this.isSignIn = isSignIn;
	}

	public Long getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Long signInTime) {
		this.signInTime = signInTime;
	}

	public Integer getIsTrade() {
		return isTrade;
	}

	public void setIsTrade(Integer isTrade) {
		this.isTrade = isTrade;
	}

	public Long getFirstTradeTime() {
		return firstTradeTime;
	}

	public void setFirstTradeTime(Long firstTradeTime) {
		this.firstTradeTime = firstTradeTime;
	}

	public Long getLastTradeTime() {
		return lastTradeTime;
	}

	public void setLastTradeTime(Long lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}

	public Long getLastContactTime() {
		return lastContactTime;
	}

	public void setLastContactTime(Long lastContactTime) {
		this.lastContactTime = lastContactTime;
	}

	public Long getBelongMarket() {
		return belongMarket;
	}

	public void setBelongMarket(Long belongMarket) {
		this.belongMarket = belongMarket;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public Long getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Long assignTime) {
		this.assignTime = assignTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getStartSignInTime() {
		return startSignInTime;
	}

	public void setStartSignInTime(String startSignInTime) {
		this.startSignInTime = startSignInTime;
	}

	public String getEndSignInTime() {
		return endSignInTime;
	}

	public void setEndSignInTime(String endSignInTime) {
		this.endSignInTime = endSignInTime;
	}

	public String getStartFirstTradeTime() {
		return startFirstTradeTime;
	}

	public void setStartFirstTradeTime(String startFirstTradeTime) {
		this.startFirstTradeTime = startFirstTradeTime;
	}

	public String getEndFirstTradeTime() {
		return endFirstTradeTime;
	}

	public void setEndFirstTradeTime(String endFirstTradeTime) {
		this.endFirstTradeTime = endFirstTradeTime;
	}

	public String getStartLastTradeTime() {
		return startLastTradeTime;
	}

	public void setStartLastTradeTime(String startLastTradeTime) {
		this.startLastTradeTime = startLastTradeTime;
	}

	public String getEndLastTradeTime() {
		return endLastTradeTime;
	}

	public void setEndLastTradeTime(String endLastTradeTime) {
		this.endLastTradeTime = endLastTradeTime;
	}

	public String getStartLastContactTime() {
		return startLastContactTime;
	}

	public void setStartLastContactTime(String startLastContactTime) {
		this.startLastContactTime = startLastContactTime;
	}

	public String getEndLastContactTime() {
		return endLastContactTime;
	}

	public void setEndLastContactTime(String endLastContactTime) {
		this.endLastContactTime = endLastContactTime;
	}

	public String getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(String startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getContactTimeStr() {
		return contactTimeStr;
	}

	public void setContactTimeStr(String contactTimeStr) {
		this.contactTimeStr = contactTimeStr;
	}
}
