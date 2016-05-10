package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;

/**
 * 港股系统扣费VO
 * @author WangPinQun
 * 2015年10月19日 上午11:23:50
 */
public class HkUserTradFeeDuductionVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * 用户操盘方案id 集合
	 */
	private String userTradeIds;
	
	/**
	 * 用户id
	 */
	private String uid;

	public String getUserTradeIds() {
		return userTradeIds;
	}

	public void setUserTradeIds(String userTradeIds) {
		this.userTradeIds = userTradeIds;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
