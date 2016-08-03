package com.tzdr.domain.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name = "w_luckdraw")
public class LuckDraw extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private String uid;//用户id
	private Double money;//抽奖金额
	private Date crateTime;//抽奖时间
	private String activity;//抽奖来源的活动
	@Column(name="uid")
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Column(name = "money")
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Column(name = "createTime")
	public Date getCrateTime() {
		return crateTime;
	}
	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}
	@Column(name="activity")
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public LuckDraw(String uid, Double money, Date crateTime, String activity) {
		this.uid = uid;
		this.money = money;
		this.crateTime = crateTime;
		this.activity = activity;
	}
	public LuckDraw() {
		super();
	}
	
}
