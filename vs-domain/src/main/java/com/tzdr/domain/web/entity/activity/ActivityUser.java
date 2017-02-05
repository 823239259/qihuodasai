/**
 * 
 */
package com.tzdr.domain.web.entity.activity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * <B>说明: </B>活动参与用户表
 * @chen.ding
 * 2016年1月20日
 */
@Entity
@Table(name = "w_activity_user")
public class ActivityUser extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6065923374705014306L;

	/**
	 * 活动类型：1-微信抽奖；2-web抽奖
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
	 * 奖品开箱日期（年月日20160108，使用英文逗号分隔）
	 */
	private String unpackingTime;

	/**
	 * 用户前一日交易手数
	 */
	private Integer yesterdayTradNum;

	/**
	 * 奖品开箱次数：微信抽奖activityType=1时，默认为1
	 */
	private Integer unpackingNum;

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
	 * @return the unpackingTime
	 */
	public String getUnpackingTime() {
		return unpackingTime;
	}

	/**
	 * @param unpackingTime
	 *            the unpackingTime to set
	 */
	public void setUnpackingTime(String unpackingTime) {
		this.unpackingTime = unpackingTime;
	}

	/**
	 * @return the yesterdayTradNum
	 */
	public Integer getYesterdayTradNum() {
		return yesterdayTradNum;
	}

	/**
	 * @param yesterdayTradNum
	 *            the yesterdayTradNum to set
	 */
	public void setYesterdayTradNum(Integer yesterdayTradNum) {
		this.yesterdayTradNum = yesterdayTradNum;
	}

	/**
	 * @return the unpackingNum
	 */
	public Integer getUnpackingNum() {
		return unpackingNum;
	}

	/**
	 * @param unpackingNum
	 *            the unpackingNum to set
	 */
	public void setUnpackingNum(Integer unpackingNum) {
		this.unpackingNum = unpackingNum;
	}

	/**
	 * 
	 */
	public ActivityUser() {
		super();
	}

}
