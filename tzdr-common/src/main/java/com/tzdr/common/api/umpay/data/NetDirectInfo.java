package com.tzdr.common.api.umpay.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class NetDirectInfo {
	
	/**
	 * 媒介标识 手机号
	 */
	private String mediaId="";

	/**
	 * 媒介类型 MOBILE
	 */
	private String mediaType="";

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
