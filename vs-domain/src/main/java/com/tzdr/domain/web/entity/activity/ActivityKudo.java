/**
 * 
 */
package com.tzdr.domain.web.entity.activity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * <B>说明: </B>活动奖品表
 * @chen.ding
 * 2016年1月20日
 */
@Entity
@Table(name = "w_activity_kudo")
public class ActivityKudo extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6065923374705014306L;

	/**
	 * 活动类型：1-微信抽奖；2-web抽奖（开箱壕礼）；3-新春礼包
	 */
	private Integer activityType;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户帐号/手机号
	 */
	private String userPhone;

	/**
	 * 奖品名称
	 */
	private String kudoName;

	/**
	 * 奖品类型：1-实物，2-红包，3-抵扣券，4-免单，5-折扣券，6-黑名单折扣券，7-新春大礼包
	 */
	private Integer kudoType;

	/**
	 * 奖品状态：0-未获得；1-已获得/立即使用；2-已使用/已寄出；3-申请使用；默认0
	 */
	private Integer kudoStatus = 0;

	/**
	 * 奖品获得时间
	 */
	private Long kudoGetTime;

	/**
	 * 奖品已使用/已寄出时间
	 */
	private Long kudoUseTime;
	
	/**
	 * 金额
	 */
	private Double kudoMoney;
	
	/**
	 * 使用条件
	 */
	private String kudoUseCondition;

	/**
	 * @return the activityType
	 */
	public Integer getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType
	 *            the activityType to set
	 */
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}

	/**
	 * @param userPhone
	 *            the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @return the kudoName
	 */
	public String getKudoName() {
		return kudoName;
	}

	/**
	 * @param kudoName
	 *            the kudoName to set
	 */
	public void setKudoName(String kudoName) {
		this.kudoName = kudoName;
	}

	/**
	 * @return the kudoType
	 */
	public Integer getKudoType() {
		return kudoType;
	}

	/**
	 * @param kudoType
	 *            the kudoType to set
	 */
	public void setKudoType(Integer kudoType) {
		this.kudoType = kudoType;
	}

	/**
	 * @return the kudoStatus
	 */
	public Integer getKudoStatus() {
		return kudoStatus;
	}

	/**
	 * @param kudoStatus
	 *            the kudoStatus to set
	 */
	public void setKudoStatus(Integer kudoStatus) {
		this.kudoStatus = kudoStatus;
	}

	/**
	 * @return the kudoGetTime
	 */
	public Long getKudoGetTime() {
		return kudoGetTime;
	}

	/**
	 * @param kudoGetTime
	 *            the kudoGetTime to set
	 */
	public void setKudoGetTime(Long kudoGetTime) {
		this.kudoGetTime = kudoGetTime;
	}

	/**
	 * @return the kudoUseTime
	 */
	public Long getKudoUseTime() {
		return kudoUseTime;
	}

	/**
	 * @param kudoUseTime
	 *            the kudoUseTime to set
	 */
	public void setKudoUseTime(Long kudoUseTime) {
		this.kudoUseTime = kudoUseTime;
	}

	/**
	 * 
	 */
	public ActivityKudo() {
		super();
	}

	public Double getKudoMoney() {
		return kudoMoney;
	}

	public void setKudoMoney(Double kudoMoney) {
		this.kudoMoney = kudoMoney;
	}

	public String getKudoUseCondition() {
		return kudoUseCondition;
	}

	public void setKudoUseCondition(String kudoUseCondition) {
		this.kudoUseCondition = kudoUseCondition;
	}

}
