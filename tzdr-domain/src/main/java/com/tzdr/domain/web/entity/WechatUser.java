package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
@Entity
@Table(name = "w_wechat_user")
public class WechatUser extends BaseEntity{

	private static final long serialVersionUID = -233314830896044012L;
	/**
	 * 用户OPENID
	 */
	private String wechatOpenId;
	/**
	 * 微信昵称
	 */
	private String wechatNickName;
	/**
	 * 微信性别  1时是男性，值为2时是女性，值为0时是未知
	 */
	private String wechatSex;
	/**
	 * 微信语言
	 */
	private String wechatLanguage;
	/**
	 * 微信城市
	 */
	private String wechatCity;
	/**
	 * 微信省份
	 */
	private String wechatProvince;
	/**
	 * 微信国家
	 */
	private String wechatCountry;
	/**
	 * 微信头像地址
	 */
	private String wechatHeadimgurl;
	/**
	 * 微信分组
	 */
	private String wechatGroupid;
	/**
	 * 用户是否关注(0-未关注，1-已关注)
	 */
	private String wechatSubscribe;
	/**
	 * 用户系统ID
	 */
	private String userId;
	public String getWechatOpenId() {
		return wechatOpenId;
	}
	public void setWechatOpenId(String wechatOpenId) {
		this.wechatOpenId = wechatOpenId;
	}
	public String getWechatNickName() {
		return wechatNickName;
	}
	public void setWechatNickName(String wechatNickName) {
		this.wechatNickName = wechatNickName;
	}
	public String getWechatSex() {
		return wechatSex;
	}
	public void setWechatSex(String wechatSex) {
		this.wechatSex = wechatSex;
	}
	public String getWechatLanguage() {
		return wechatLanguage;
	}
	public void setWechatLanguage(String wechatLanguage) {
		this.wechatLanguage = wechatLanguage;
	}
	public String getWechatCity() {
		return wechatCity;
	}
	public void setWechatCity(String wechatCity) {
		this.wechatCity = wechatCity;
	}
	public String getWechatProvince() {
		return wechatProvince;
	}
	public void setWechatProvince(String wechatProvince) {
		this.wechatProvince = wechatProvince;
	}
	public String getWechatCountry() {
		return wechatCountry;
	}
	public void setWechatCountry(String wechatCountry) {
		this.wechatCountry = wechatCountry;
	}
	public String getWechatHeadimgurl() {
		return wechatHeadimgurl;
	}
	public void setWechatHeadimgurl(String wechatHeadimgurl) {
		this.wechatHeadimgurl = wechatHeadimgurl;
	}
	public String getWechatGroupid() {
		return wechatGroupid;
	}
	public void setWechatGroupid(String wechatGroupid) {
		this.wechatGroupid = wechatGroupid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWechatSubscribe() {
		return wechatSubscribe;
	}
	public void setWechatSubscribe(String wechatSubscribe) {
		this.wechatSubscribe = wechatSubscribe;
	}
	
	
}
