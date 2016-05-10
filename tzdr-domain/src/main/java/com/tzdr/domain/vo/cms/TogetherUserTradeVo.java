package com.tzdr.domain.vo.cms;

import java.io.Serializable;
import java.math.BigInteger;

public class TogetherUserTradeVo implements Serializable {

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
	 * 总操盘资金
	 */
	private double totalLeverMoney;

	/**
	 * 合买者出资
	 */
	private double money;

	/**
	 * 操盘周期
	 */
	private String naturalDays;

	/**
	 * 合买者收益(年化利率)
	 */
	private String annualizeRate;

	/**
	 * 盈利分成比例
	 */
	private String profitRatio;

	/**
	 * 方案状态
	 */
	private int tStatus;

	/**
	 * 方案申请时间
	 */
	private BigInteger addTime;

	/**
	 * 交易开始时间
	 */
	private BigInteger startTime;

	/**
	 * 交易预计结束时间
	 */
	private BigInteger estimateEndtime;

	/**
	 * 操盘天数
	 */
	private int tradeDays;

	/**
	 * 延期交易日
	 */
	private int delayDays;

	/**
	 * 收益率
	 */
	private double returnRate;

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
	 * @return the totalLeverMoney
	 */
	public double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	/**
	 * @param totalLeverMoney
	 *            the totalLeverMoney to set
	 */
	public void setTotalLeverMoney(double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
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
	 * @return the naturalDays
	 */
	public String getNaturalDays() {
		return naturalDays;
	}

	/**
	 * @param naturalDays
	 *            the naturalDays to set
	 */
	public void setNaturalDays(String naturalDays) {
		this.naturalDays = naturalDays;
	}

	/**
	 * @return the annualizeRate
	 */
	public String getAnnualizeRate() {
		return annualizeRate;
	}

	/**
	 * @param annualizeRate
	 *            the annualizeRate to set
	 */
	public void setAnnualizeRate(String annualizeRate) {
		this.annualizeRate = annualizeRate;
	}

	/**
	 * @return the profitRatio
	 */
	public String getProfitRatio() {
		return profitRatio;
	}

	/**
	 * @param profitRatio
	 *            the profitRatio to set
	 */
	public void setProfitRatio(String profitRatio) {
		this.profitRatio = profitRatio;
	}

	/**
	 * @return the tStatus
	 */
	public int gettStatus() {
		return tStatus;
	}

	/**
	 * @param tStatus
	 *            the tStatus to set
	 */
	public void settStatus(int tStatus) {
		this.tStatus = tStatus;
	}

	/**
	 * @return the addTime
	 */
	public BigInteger getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set
	 */
	public void setAddTime(BigInteger addTime) {
		this.addTime = addTime;
	}

	/**
	 * @return the startTime
	 */
	public BigInteger getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(BigInteger startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the estimateEndtime
	 */
	public BigInteger getEstimateEndtime() {
		return estimateEndtime;
	}

	/**
	 * @param estimateEndtime
	 *            the estimateEndtime to set
	 */
	public void setEstimateEndtime(BigInteger estimateEndtime) {
		this.estimateEndtime = estimateEndtime;
	}

	/**
	 * @return the tradeDays
	 */
	public int getTradeDays() {
		return tradeDays;
	}

	/**
	 * @param tradeDays
	 *            the tradeDays to set
	 */
	public void setTradeDays(int tradeDays) {
		this.tradeDays = tradeDays;
	}

	/**
	 * @return the delayDays
	 */
	public int getDelayDays() {
		return delayDays;
	}

	/**
	 * @param delayDays
	 *            the delayDays to set
	 */
	public void setDelayDays(int delayDays) {
		this.delayDays = delayDays;
	}

	/**
	 * @return the returnRate
	 */
	public double getReturnRate() {
		return returnRate;
	}

	/**
	 * @param returnRate
	 *            the returnRate to set
	 */
	public void setReturnRate(double returnRate) {
		this.returnRate = returnRate;
	}

}
