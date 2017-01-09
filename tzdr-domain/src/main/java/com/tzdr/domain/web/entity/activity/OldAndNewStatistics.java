package com.tzdr.domain.web.entity.activity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 老带新统计表
 *
 */
@Entity
@Table(name = "w_activity_oldAndNew_statistics")
public class OldAndNewStatistics implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * 推荐人 实名
	 */
	private String realName;
	/**
	 * 推荐人 电话
	 */
	private String mobile;
	
	/**
	 * 注册人数
	 */
	private Integer registNum;
	/**
	 * 交易人数
	 */
	private Integer ftradeNum;
	/**
	 * 交易满5手人数  
	 */
	@Column(name = "ftrade_gt_5")
	private Integer ftradeGt5;   
	/**
	 * 交易不满5手人数
	 */
	@Column(name = "ftrade_lt_5")
	private Integer ftradeLt5;
	
	/**
	 * 获得的奖励
	 */
	private Integer awardSum;
	
	/**
	 * 每日统计表生成时间
	 */
	private Date date = new Date();
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getRegistNum() {
		return registNum;
	}
	public void setRegistNum(Integer registNum) {
		this.registNum = registNum;
	}
	public Integer getFtradeNum() {
		return ftradeNum;
	}
	public void setFtradeNum(Integer ftradeNum) {
		this.ftradeNum = ftradeNum;
	}
	public Integer getFtradeGt5() {
		return ftradeGt5;
	}
	public void setFtradeGt5(Integer ftradeGt5) {
		this.ftradeGt5 = ftradeGt5;
	}
	public Integer getFtradeLt5() {
		return ftradeLt5;
	}
	public void setFtradeLt5(Integer ftradeLt5) {
		this.ftradeLt5 = ftradeLt5;
	}
	public Integer getAwardSum() {
		return awardSum;
	}
	public void setAwardSum(Integer awardSum) {
		this.awardSum = awardSum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
