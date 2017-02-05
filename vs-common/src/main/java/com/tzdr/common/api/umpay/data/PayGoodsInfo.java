package com.tzdr.common.api.umpay.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class PayGoodsInfo {
	
	/**
	 * 商品号
	 */
	private String goodsId="";
	
	/**
	 * 商品描述信息
	 */
	private String goodsInf="";
	
	/**
	 * 商户私有域
	 */
	private String merPriv="";
	
	/**
	 * 业务扩展信息
	 */	
	private String expand="";
	
	/**
	 * 用户IP地址
	 */	
	private String userIp="";
	
	
	/**
	 * 订单过期时长
	 */
	private String expireTime="";
	
	/**
	 * 商户用户标识
	 */
	private String merCustId="";

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsInf() {
		return goodsInf;
	}

	public void setGoodsInf(String goodsInf) {
		this.goodsInf = goodsInf;
	}

	public String getMerPriv() {
		return merPriv;
	}

	public void setMerPriv(String merPriv) {
		this.merPriv = merPriv;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getMerCustId() {
		return merCustId;
	}

	public void setMerCustId(String merCustId) {
		this.merCustId = merCustId;
	}
	
	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
