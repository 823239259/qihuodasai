package com.tzdr.domain.vo.activity;

import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * <p>
 * 活动用户
 * </p>
 * 
 * @author WangPinQun
 * @see
 * @version 1.0 2016年01月08日下午13:47:24
 */
public class ActivityUserWebVo {

	/**
	 * 主键ID
	 */
	@SqlColumn(name = "id")
	private String id;

	/**
	 * 活动类型：1-微信抽奖；2-web抽奖
	 */
	@SqlColumn(name = "activity_type")
	private Integer activityType;

	/**
	 * 用户ID
	 */
	@SqlColumn(name = "user_id")
	private String userId;

	/**
	 * 用户帐号/手机号
	 */
	@SqlColumn(name = "user_phone")
	private String userPhone;

	/**
	 * 奖品开箱日期（年月日20160108，使用英文逗号分隔）
	 */
	@SqlColumn(name = "unpacking_time")
	private String unpackingTime;

	/**
	 * 用户前一日交易手数
	 */
	@SqlColumn(name = "yesterday_trad_num")
	private Integer yesterdayTradNum;

	/**
	 * 奖品开箱次数：微信抽奖activityType=1时，默认为1
	 */
	@SqlColumn(name = "unpacking_num")
	private Integer unpackingNum;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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

}
