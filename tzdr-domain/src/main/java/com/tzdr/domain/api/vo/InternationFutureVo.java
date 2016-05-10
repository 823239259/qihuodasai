package com.tzdr.domain.api.vo;

import java.io.Serializable;

import org.apache.commons.lang.math.NumberUtils;

/**
 * wuser vo
 * @author zhouchen
 * 2015年5月26日 上午9:57:05
 */
public class InternationFutureVo implements Serializable  {
	
	
	private static final long serialVersionUID = -3390139639987375733L;
	private String account;//账户
	private Integer businessType; // 业务类型
	private Integer channel;// 账户渠道  只有数字的是直达的， 带字母的是易盛的    1:直达 2:易盛
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public Integer getChannel() {
		if (NumberUtils.isNumber(account)){
			channel = 1;
		}
		else
		{
			channel = 2;
		}
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	
	
}
