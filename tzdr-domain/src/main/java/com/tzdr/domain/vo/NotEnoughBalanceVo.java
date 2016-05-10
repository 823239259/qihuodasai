package com.tzdr.domain.vo;

import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月12日 下午1:54:20
 * 对应余额不足提醒方案的列表
 */
public class NotEnoughBalanceVo {

	/**
	 * 用户id
	 */
	private String uid;
	
	/**
	 * 用户对应的所有配资方案id
	 */
	private String userTradeIds;
	
	/**
	 * 用户名
	 */
	private String uname;
	
	/**
	 * 用户手机号码
	 */
	private String mobile;
	
	/**
	 * 每天的利息
	 */
	private Double interest;
	
	/**
	 * 每天的管理费
	 */
	private Double fee;
	
	/**
	 * 账户余额
	 */
	private Double balance;
	
	
	/**
	 * 余额不足 提醒状态0：未通知   1：已通知 2：未接通
	 */
	private Integer  noticeStatus;


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Double getInterest() {
		return interest;
	}


	public void setInterest(Double interest) {
		this.interest = interest;
	}


	public Double getFee() {
		return fee;
	}


	public void setFee(Double fee) {
		this.fee = fee;
	}


	public Double getBalance() {
		return balance;
	}


	public void setBalance(Double balance) {
		this.balance = balance;
	}


	public String getNoticeStatus() {
		return  CacheManager.getDataMapByKey(DataDicKeyConstants.CALL_NOTICE_STATUS,String.valueOf(this.noticeStatus));

	}


	public void setNoticeStatus(Integer noticeStatus) {
		this.noticeStatus = noticeStatus;
	}


	public String getUname() {
		return uname;
	}


	public void setUname(String uname) {
		this.uname = uname;
	}


	public String getUserTradeIds() {
		return userTradeIds;
	}


	public void setUserTradeIds(String userTradeIds) {
		this.userTradeIds = userTradeIds;
	}
	
	
	
}
