package com.tzdr.domain.vo;

import java.math.BigInteger;


/**
 * @author zhouchen
 * @version 创建时间：2015年1月12日 上午9:23:24
 * 类说明
 */
public class UserFundVo {

	/**
	 * 用户id
	 */
	private String uid;
	
	/**
	 * 金额
	 */
	private Double money;
	
	/**
	 * 支付更新时间
	 */
	private BigInteger upTime;
	
	/**
	 *  配资方案的 id
	 */
	private String tradeIds;
	
	/**
	 * 资金明细的总记录
	 */
	private BigInteger count;
	
	/**
	 * 支出总记录
	 */
	private BigInteger outcount;
	
	/**
	 * 汇总资金明细
	 */
	private Double summoney;
	
	/**
	 * 支出总数
	 */
	private Double outsummoney;
	
	private String tname;
	
	private String mobile;
	
	
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

	public BigInteger getOutcount() {
		return outcount;
	}

	public void setOutcount(BigInteger outcount) {
		this.outcount = outcount;
	}

	public Double getOutsummoney() {
		return outsummoney;
	}

	public void setOutsummoney(Double outsummoney) {
		this.outsummoney = outsummoney;
	}

	public Double getSummoney() {
		return summoney;
	}

	public void setSummoney(Double summoney) {
		this.summoney = summoney;
	}

	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTradeIds() {
		return tradeIds;
	}

	public void setTradeIds(String tradeIds) {
		this.tradeIds = tradeIds;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public BigInteger getUpTime() {
		return upTime;
	}

	public void setUpTime(BigInteger upTime) {
		this.upTime = upTime;
	}
	
	
	
}
