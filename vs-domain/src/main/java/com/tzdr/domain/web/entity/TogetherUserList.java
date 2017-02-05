package com.tzdr.domain.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import jodd.util.ObjectUtil;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.Dates;
/**
 * 虚拟合买者信息表
 * @author LiuYang
 *
 * 2015年12月25日 上午10:49:51
 */
@Entity
@Table(name = "w_together_user_list")
public class TogetherUserList extends BaseCrudEntity{

	private static final long serialVersionUID = 3144964304300623936L;
	/**
	 * 合买方案ID
	 */
	private String tid;
	/**
	 * 合买方案groupID
	 */
	private String gid;
	/**
	 * 合买者ID
	 */
	private String uid;
	/**
	 * 合买者号码
	 */
	private String uphone;
	/**
	 * 合买金额
	 */
	private double money;
	/**
	 * 利息收益
	 */
	private double interestIncome;
	/**
	 * 盈利分成
	 */
	private double profitShare;
	/**
	 * 合买时间
	 */
	private Long buyTime;
	/**
	 * 合买时间String
	 */
	@Transient
	private String buyTimeStr;
	
	/**
	 * 结算时间
	 */
	private Long endTime;
	
	/**
	 * 结算时间String
	 */
	@Transient
	private String endTimeStr;
	@Column(name="tid")
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	@Column(name="gid")
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
	@Column(name="uid")
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Column(name="uphone")
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	@Column(name="money")
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	@Column(name="interest_income")
	public double getInterestIncome() {
		return interestIncome;
	}
	public void setInterestIncome(double interestIncome) {
		this.interestIncome = interestIncome;
	}
	@Column(name="profit_share")
	public double getProfitShare() {
		return profitShare;
	}
	public void setProfitShare(double profitShare) {
		this.profitShare = profitShare;
	}
	@Column(name="buy_time")
	public Long getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Long buyTime) {
		this.buyTime = buyTime;
	}
	@Column(name="end_time")
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getBuyTimeStr() {
		if (ObjectUtil.equals(null, this.buyTime)) {
			buyTimeStr = "";
		}else{
			buyTimeStr = Dates.format(new Date(this.buyTime.longValue() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE);
		}
		return buyTimeStr;
		
	}
	public void setBuyTimeStr(String buyTimeStr) {
		this.buyTimeStr = buyTimeStr;
	}
	public String getEndTimeStr() {
		if (ObjectUtil.equals(null, this.endTime)) {
			 endTimeStr = "";
		}else{
			endTimeStr =  Dates.format(new Date(this.endTime.longValue() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE);
		}return	endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
	
	
}
