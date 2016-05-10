package com.tzdr.domain.vo.activity;

import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * 类说明
 * @author  zhaozhao
 * @date    2016年1月11日上午11:48:23
 * @version 1.0
 */
public class ActivityCmsVo {
	
	/**
	 * 活动类型：1-微信抽奖；2-web抽奖
	 */
	@SqlColumn(name="activity_type")
	private Integer activityType;

	/**
	 * 用户ID
	 */
	@SqlColumn(name="user_id")
	private String uid;
	
	/**
	 * 奖品编号
	 */
	@SqlColumn(name="id")
	private String id;
	
	/**
	 * 奖品名称
	 */
	@SqlColumn(name="kudo_name")
	private String kudoName;

	/**
	 * 奖品状态：0-未获得；1-已获得；2-已使用/已寄出；默认0
	 */
	@SqlColumn(name="kudo_status")
	private Integer kudoStatus;
	

	/**
	 * 奖品获得时间
	 */
	@SqlColumn(name="kudo_get_time")
	private BigInteger kudoGetTime;

	/**
	 * 手机号
	 */
	@SqlColumn(name="user_phone")
	private String userPhone;
	
	/**
	 * 奖品使用时间
	 */
	@SqlColumn(name="kudo_use_time")
	private BigInteger kudoUseTime;
	
	/**
	 * 中奖用户姓名
	 */
	@SqlColumn(name="tname")
	private String realName;

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKudoName() {
		return kudoName;
	}

	public void setKudoName(String kudoName) {
		this.kudoName = kudoName;
	}

	public Integer getKudoStatus() {
		return kudoStatus;
	}

	public void setKudoStatus(Integer kudoStatus) {
		this.kudoStatus = kudoStatus;
	}

	public String getKudoGetTime() {
		if (ObjectUtil.equals(null,this.kudoGetTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.kudoGetTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setKudoGetTime(BigInteger kudoGetTime) {
		this.kudoGetTime = kudoGetTime;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getKudoUseTime() {
		if (ObjectUtil.equals(null,this.kudoUseTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.kudoUseTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setKudoUseTime(BigInteger kudoUseTime) {
		this.kudoUseTime = kudoUseTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
