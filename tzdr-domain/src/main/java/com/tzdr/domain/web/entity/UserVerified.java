package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

/**
 * 用户认证资料信息
 * <P>title:@UserVerified.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月24日
 * @version 1.0
 */
@Entity
@Table(name="w_user_verified")
public class UserVerified extends BaseEntity{ 
	
	// 身份证状态
	public static class Idcard {
		/**
		 * 未认证
		 */
		public static final short NOAUTH = 5;
		/**
		 * 未完成
		 */
		public static final short NOCOMPLETE = 3;
		/**
		 * 未通过
		 */
		public static final short NOPASS = 4;
		/**
		 * 待审
		 */
		public static final short PENDING = 0;
		/**
		 * 照片未认证通过
		 */
		public static final short NOAUTHPASS = 1;
		/**
		 * 照片认证通过
		 */
		public static final short AUTHPASS = 2;
		/**
		 * 身份证验证通过后并上传照片
		 */
		public static final short UPLOADSTATUS=6;
	}
	//private String uid;
	@OneToOne
	@JoinColumn(name="uid",insertable=true,unique=true)  
	private WUser wuser;
	private String tname;
	private String idcard;
	private String idcardFront;
	private String idcardBack;
	private Short moblieActive;
	private Short emailActive;
	private String emailActivecode;
	private Short status;
	private Integer addtime;
	private String mainotice;
	private String validateemail;
	
	private String idcardPath;//手持身份证正面
	private Integer validatenum;//验证次数
	private String phonecode;//手机验证码
	private Long  validatePhoneTime;//验证码有效开始时间
	private Long  validateEmailTime;//验证有效开始时间
	private String drawMoneyPwd;//提款密码
	
	private String notByReason;//不通过原因
	
	private Long lastSubmitVerifiedTime;
	
	private Long updatUserId;//修改人
	
	private String moneyActive;//是否设置了提现密码0未设置,1设置
	/**
	 * 微信账号
	 */
	private String wxAccount;
	/**
	 * 支付宝账号
	 */
	private String alipayAccount;
	
	public String getValidateemail() {
		return validateemail;
	}

	public void setValidateemail(String validateemail) {
		this.validateemail = validateemail;
	}

	
	public String getDrawMoneyPwd() {
		return drawMoneyPwd;
	}

	public void setDrawMoneyPwd(String drawMoneyPwd) {
		this.drawMoneyPwd = drawMoneyPwd;
	}

	public String getMoneyActive() {
		return moneyActive;
	}

	public void setMoneyActive(String moneyActive) {
		this.moneyActive = moneyActive;
	}

	public Integer getValidatenum() {
		return validatenum;
	}

	public String getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}

	public Long getValidatePhoneTime() {
		return validatePhoneTime;
	}

	public void setValidatePhoneTime(Long validatePhoneTime) {
		this.validatePhoneTime = validatePhoneTime;
	}

	public Long getValidateEmailTime() {
		return validateEmailTime;
	}

	public void setValidateEmailTime(Long validateEmailTime) {
		this.validateEmailTime = validateEmailTime;
	}

	public void setValidatenum(Integer validatenum) {
		this.validatenum = validatenum;
	}

	public String getIdcardPath() {
		return idcardPath;
	}

	public void setIdcardPath(String idcardPath) {
		this.idcardPath = idcardPath;
	}

//	@Column(name="uid", length=32)
//	public String getUid() {
//		return this.uid;
//	}
//
//	public void setUid(String uid) {
//		this.uid = uid;
//	}

	@Column(name="tname")
	public String getTname() {
		return this.tname;
	}
	

	public WUser getWuser() {
		return wuser;
	}

	public void setWuser(WUser wuser) {
		this.wuser = wuser;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Column(name="idcard")
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name="idcard_front")
	public String getIdcardFront() {
		return this.idcardFront;
	}

	public void setIdcardFront(String idcardFront) {
		this.idcardFront = idcardFront;
	}

	@Column(name="idcard_back")
	public String getIdcardBack() {
		return this.idcardBack;
	}

	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}

	@Column(name="moblie_active")
	public Short getMoblieActive() {
		return this.moblieActive;
	}

	public void setMoblieActive(Short moblieActive) {
		this.moblieActive = moblieActive;
	}

	@Column(name="email_active")
	public Short getEmailActive() {
		return this.emailActive;
	}

	public void setEmailActive(Short emailActive) {
		this.emailActive = emailActive;
	}

	@Column(name="email_activecode")
	public String getEmailActivecode() {
		return this.emailActivecode;
	}

	public void setEmailActivecode(String emailActivecode) {
		this.emailActivecode = emailActivecode;
	}

	@Column(name="status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name="addtime")
	public Integer getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	@Column(name="mainotice", length=20)
	public String getMainotice() {
		return this.mainotice;
	}

	public void setMainotice(String mainotice) {
		this.mainotice = mainotice;
	}

	@Column(length=3000)
	public String getNotByReason() {
		return notByReason;
	}

	public void setNotByReason(String notByReason) {
		this.notByReason = notByReason;
	}

	@Column
	public Long getLastSubmitVerifiedTime() {
		return lastSubmitVerifiedTime;
	}

	public void setLastSubmitVerifiedTime(Long lastSubmitVerifiedTime) {
		this.lastSubmitVerifiedTime = lastSubmitVerifiedTime;
	}

	@Column
	public Long getUpdatUserId() {
		return updatUserId;
	}

	public void setUpdatUserId(Long updatUserId) {
		this.updatUserId = updatUserId;
	}
	
	@Column(name = "alipay_account", unique = true)
	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getWxAccount() {
		return wxAccount;
	}

	public void setWxAccount(String wxAccount) {
		this.wxAccount = wxAccount;
	}
	
}