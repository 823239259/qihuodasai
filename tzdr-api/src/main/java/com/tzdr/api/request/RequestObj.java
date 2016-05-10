package com.tzdr.api.request;

/**
 * 
 * <B>说明: </B>用户注册接口请求
 * @zhouchen
 * 2016年1月20日
 */
public class RequestObj {

	/**
	 * 用户id
	 */
	private String uid;

	/**
	 * 方案编号
	 */
	private String groupId;
	
	/**
	 * 是否立即交易
	 */
	private int tradeStart;
	
	/**
	 * 杠杆倍数
	 */
	private int lever;
	/**
	 * 保证金
	 */
	private double capitalMargin;
	/**
	 * 借款天数
	 */
	private int borrowPeriod;
	
	/**
	 * 金额
	 */
	private double money; 
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 身份证号
	 */
	private String card;
	
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 新密码
	 */
	private  String newPassword;
	
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * type
	 */
	private int type;
	/**
	 * 验证码
	 */
	private String code;
	
	/**
	 * 帐号
	 */
	private String account;
	
	/**
	 * 推广码
	 */
	private String parentGeneralizeId;
	
	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 渠道
	 */
	private String channel;
			
			
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getTradeStart() {
		return tradeStart;
	}

	public void setTradeStart(int tradeStart) {
		this.tradeStart = tradeStart;
	}

	public int getLever() {
		return lever;
	}

	public void setLever(int lever) {
		this.lever = lever;
	}

	public double getCapitalMargin() {
		return capitalMargin;
	}

	public void setCapitalMargin(double capitalMargin) {
		this.capitalMargin = capitalMargin;
	}

	public int getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(int borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getParentGeneralizeId() {
		return parentGeneralizeId;
	}

	public void setParentGeneralizeId(String parentGeneralizeId) {
		this.parentGeneralizeId = parentGeneralizeId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}
