package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月27日上午10:51:50
 */
public class WuserListVo implements Serializable {
	
	private static final long serialVersionUID = 5413198153714337951L;
	
	@SqlColumn
	private String id;
	
	@SqlColumn
	private String tname;
	
	@SqlColumn
	private String uname;//用户昵称
	
	@SqlColumn
	private String email;//邮箱
	
	@SqlColumn
	private String mobile;//手机号
	
	@SqlColumn
	private BigInteger ctime;//注册时间
	
	private String ctimeStr;
	
	/**
	 * 用户类型
	 */
	@SqlColumn
	private String userType;
	
	@SqlColumn
	private String idcard;//身份证号
	
	@SqlColumn
	private BigInteger lastLoginTime;//最后登陆时间
	
	private String lastLoginTimeStr;
	
	/**
	 * 账户余额
	 */
	@SqlColumn
	private Double avlBal;
	
	/**
	 * 配资保证金
	 */
	@SqlColumn
	private Double allocationMoney;
	//冻结金额
	@SqlColumn
	private Double frzBal;
	
	/**
	 * 支付宝帐号
	 */
	@SqlColumn
	private String alipayAccount;
	
	/**
	 * 来源网站
	 */
	@SqlColumn
	private Integer source;
	private String sourceName;
	@SqlColumn
	private String channel;
	@SqlColumn
	private String keyword;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTname() {
		return tname == null ? this.uname:tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigInteger getCtime() {
		return ctime;
	}

	public void setCtime(BigInteger ctime) {
		this.ctime = ctime;
	}

	public String getUserType() {
		return CacheManager.getDataMapByKey("userType",this.userType);
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public BigInteger getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(BigInteger lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Double getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(Double avlBal) {
		this.avlBal = avlBal;
	}

	public Double getAllocationMoney() {
		return allocationMoney;
	}

	public void setAllocationMoney(Double allocationMoney) {
		this.allocationMoney = allocationMoney;
	}

	public Double getFrzBal() {
		return frzBal;
	}

	public void setFrzBal(Double frzBal) {
		this.frzBal = frzBal;
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
			this.lastLoginTimeStr = TypeConvert.long1000ToDatetimeStr(this.lastLoginTime.longValue());
		}
		return lastLoginTimeStr;
	}

	public void setLastLoginTimeStr(String lastLoginTimeStr) {
		this.lastLoginTimeStr = lastLoginTimeStr;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSourceName() {
		if(this.source == 2) {
			sourceName = "配股宝Wap";
		} else if(this.source == 5) {
			sourceName = "配股宝Web";
		} else if(this.source == 6) {
			sourceName = "喊单直播间";
		} else if(this.source == 7) {
			sourceName = "维胜APP";
		} else {
			sourceName = "维胜Web";
		}
		
		/*
		if(this.source == 2 || this.source == 5){
			sourceName = "配股宝";
		}else{
			sourceName = "投资达人";
		}
		*/
		
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

}
