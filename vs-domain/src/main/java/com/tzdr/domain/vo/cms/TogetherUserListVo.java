package com.tzdr.domain.vo.cms;

import java.io.Serializable;

public class TogetherUserListVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6880172297200599426L;

	/**
	 * w_user_trade表id
	 */
	private String tid;

	/**
	 * w_user_trade表groupid
	 */
	private String gid;

	/**
	 * 用户编号
	 */
	private String uid;

	/**
	 * 手机号码
	 */
	private String uphone;

	/**
	 * 合买者出资舍掉后四位
	 */
	private double money;

	/**
	 * 合买者出资后两位
	 */
	private double minusMoney;

	/**
	 * 合买时间
	 */
	private String buyTime;

	/**
	 * 利息收益
	 */
	private String interestIncome;

	/**
	 * 盈利分成
	 */
	private String profitShare;

	/**
	 * @return the tid
	 */
	public String getTid() {
		return tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * @return the gid
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * @param gid
	 *            the gid to set
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the uphone
	 */
	public String getUphone() {
		return uphone;
	}

	/**
	 * @param uphone
	 *            the uphone to set
	 */
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	/**
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
	}

	/**
	 * @return the minusMoney
	 */
	public double getMinusMoney() {
		return minusMoney;
	}

	/**
	 * @param minusMoney
	 *            the minusMoney to set
	 */
	public void setMinusMoney(double minusMoney) {
		this.minusMoney = minusMoney;
	}

	/**
	 * @return the buyTime
	 */
	public String getBuyTime() {
		return buyTime;
	}

	/**
	 * @param buyTime
	 *            the buyTime to set
	 */
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	/**
	 * @return the interestIncome
	 */
	public String getInterestIncome() {
		return interestIncome;
	}

	/**
	 * @param interestIncome
	 *            the interestIncome to set
	 */
	public void setInterestIncome(String interestIncome) {
		this.interestIncome = interestIncome;
	}

	/**
	 * @return the profitShare
	 */
	public String getProfitShare() {
		return profitShare;
	}

	/**
	 * @param profitShare
	 *            the profitShare to set
	 */
	public void setProfitShare(String profitShare) {
		this.profitShare = profitShare;
	}

}
