package com.tzdr.domain.web.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name = "w_activity_reward")
public class ActivityReward extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reward_type;//0-抽奖，1-亏损补贴
	private String type;//期货类型
	private Boolean isvalid;
	private String uid;//用户id
	private Boolean istip;//是否给用户提示
	private Double money;//补贴金额,抽奖的用户此处默认为0
	private String activity;
	private Long createTime;
	private String ftseTradeId;
	@Column(name = "ftse_trade_id")
	public String getFtseTradeId() {
		return ftseTradeId;
	}
	public void setFtseTradeId(String ftseTradeId) {
		this.ftseTradeId = ftseTradeId;
	}
	@Column(name = "create_time")
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	@Column(name = "activity")
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "money")
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Column(name="reward_type")
	public String getReward_type() {
		return reward_type;
	}
	public void setReward_type(String reward_type) {
		this.reward_type = reward_type;
	}
	@Column(name = "isvalid")
	public Boolean getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}
	@Column(name = "uid")
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Column(name = "istip")
	public Boolean getIstip() {
		return istip;
	}
	public void setIstip(Boolean istip) {
		this.istip = istip;
	}
	
	
	public ActivityReward(String reward_type, String type, Boolean isvalid, String uid, Boolean istip, Double money,
			String activity, Long createTime, String ftseTradeId) {
		super();
		this.reward_type = reward_type;
		this.type = type;
		this.isvalid = isvalid;
		this.uid = uid;
		this.istip = istip;
		this.money = money;
		this.activity = activity;
		this.createTime = createTime;
		this.ftseTradeId = ftseTradeId;
	}
	public ActivityReward() {
		super();
	}
	
}
