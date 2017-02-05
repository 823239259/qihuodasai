package com.tzdr.common.api.umpay.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @Description:
 * @ClassName: WithdrawPayExtendInfo.java
 * @author Lin Feng
 * @date 2014年12月29日
 */
public class WithdrawPayExtendInfo {
	
	private String identityType="";
	
	private String identityCode="";
	
	private String identityHolder="";
	
	private String mediaType="";
	
	private String mediaId="";
	
	private String provName="";
	
	private String cityName="";
	
	private String checkFlag="";

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getIdentityHolder() {
		return identityHolder;
	}

	public void setIdentityHolder(String identityHolder) {
		this.identityHolder = identityHolder;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
