package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
/**
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2014年12月30日上午11:28:03
 */
public class ActivityWuserVo implements Serializable  {
	
	private static final long serialVersionUID = -3390139639987375733L;

	public ActivityWuserVo() {
		
	}
    
    @SqlColumn
	private String id;
    @SqlColumn
	private String mobile;//手机号
    @SqlColumn
	private String uname;//用户姓名
    @SqlColumn
	private String idcard;
    @SqlColumn
	private Integer activityType;
    @SqlColumn
	private String userType;
    @SqlColumn
	private java.math.BigInteger ctime;
    @SqlColumn
	private java.math.BigInteger lastLoginTime;
    
    private String ctimeStr;
    
    private String lastLoginTimeStr;
    
    private String activityTypeStr;
    
    private String userTypeStr;
    
    
    private String lastLoginStateValue;
    
    private String assetStateValue;
    
    /**
     * 活动类型(查询条件)
     */
    private String eventTypeStateValue;

    /**
     * 所属销售
     */
    @SqlColumn
    private String sname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public java.math.BigInteger getCtime() {
		return ctime;
	}

	public void setCtime(java.math.BigInteger ctime) {
		this.ctime = ctime;
	}

	public java.math.BigInteger getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.math.BigInteger lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginStateValue() {
		return lastLoginStateValue;
	}

	public void setLastLoginStateValue(String lastLoginStateValue) {
		this.lastLoginStateValue = lastLoginStateValue;
	}

	public String getAssetStateValue() {
		return assetStateValue;
	}

	public void setAssetStateValue(String assetStateValue) {
		this.assetStateValue = assetStateValue;
	}

	public String getCtimeStr() {
		if (this.ctime != null) {
			this.ctimeStr = TypeConvert.long1000ToDatetimeStr(this.ctime.longValue());
		}
		return ctimeStr;
	}

	public void setCtimeStr(String ctimeStr) {
		this.ctimeStr = ctimeStr;
	}

	public String getLastLoginTimeStr() {
		if (this.lastLoginTime != null) {
			this.lastLoginTimeStr = 
					TypeConvert.long1000ToDatetimeStr(this.lastLoginTime.longValue());
		}
		return lastLoginTimeStr;
	}

	public void setLastLoginTimeStr(String lastLoginTimeStr) {
		this.lastLoginTimeStr = lastLoginTimeStr;
	}

	public String getActivityTypeStr() {
		if (this.activityType != null) {
			this.activityTypeStr = CacheManager.getDataMapByKey("eventType", this.activityType + "");
		}
		return activityTypeStr;
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}

	public String getUserTypeStr() {
		if (this.getUserType() != null && !"".equals(this.getUserType())) {
			this.userTypeStr = CacheManager.getDataMapByKey("isAsset",this.getUserType());
		}
		return userTypeStr;
	}

	public void setUserTypeStr(String userTypeStr) {
		this.userTypeStr = userTypeStr;
	}

	public String getEventTypeStateValue() {
		return eventTypeStateValue;
	}

	public void setEventTypeStateValue(String eventTypeStateValue) {
		this.eventTypeStateValue = eventTypeStateValue;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
}
