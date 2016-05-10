package com.tzdr.common.api.umpay.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class FastPayInfo {
	
	/**
	 * 卡号
	 */
	private String cardId="";
	
	/**
	 * 媒介标识 手机号
	 */
	private String mediaId="";

	/**
	 * 媒介类型 MOBILE
	 */
	private String mediaType="";
	
	/**
	 * 信用卡有效期
	 */
	private String validDate="";
	
	/**
	 * 信用卡CVV2/CVN2
	 */
	private String cvv2="";
	
	/**
	 * 证件类型 仅支持身份证 IDENTITY_CARD
	 */
	private String identityType="";
	
	/**
	 * 证件号
	 */
	private String identityCode="";
	
	/**
	 * 持卡人姓名
	 */
	private String cardHolder="";

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

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

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

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

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
