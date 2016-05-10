package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.web.entity.WUser;

public class AgentVo implements Serializable {
	
	private static final long serialVersionUID = -3390139639987375733L;

	public AgentVo() {
		
	}
	
    public AgentVo( WUser wuser) {
    	this.id = wuser.getId();
    	this.rebate = wuser.getRebate();
		this.wuser = (WUser)TypeConvert.createBaseTypeClone(wuser);
		//this.uname = wuser.getUname();
		this.email = wuser.getEmail();
		this.uname = this.wuser.getUname();
		this.mobile = wuser.getMobile();
		this.level = wuser.getLevel();
		if (ctime != null && !"".equals(ctime)) {
			this.ctime = TypeConvert.long1000ToDatetimeStr(wuser.getCtime());
		}
		String userType = CacheManager.getDataMapByKey("userType",wuser.getUserType());
		this.userType = userType;
		
		this.ctime = TypeConvert.long1000ToDatetimeStr(wuser.getCtime());
		this.lastLoginTime = TypeConvert.long1000ToDatetimeStr(wuser.getLastLoginTime());
		//this.totalInterestMo = wuser.getTotalInterestMo().doubleValue();
		//this.totalManagerMo = wuser.getTotalManagerMo().doubleValue();
		//UserVerified userVerified = wuser.getUserVerified();
		//this.idCard = userVerified != null ?userVerified.getIdcard():"";
		this.rebate = wuser.getRebate();
		if (this.rebate == null) {
			this.rebate = 0D;
		}
		this.rebateStr = TypeConvert.scale(new BigDecimal(this.rebate), 2).toString() + "%";
		
	}
	
	private WUser wuser;
	
	private String uname;//用户姓名
	private String email;//邮箱
	private String mobile;//手机号
	private String ctime="";//注册时间
	
	private String lastLoginTime;//最后登陆时间
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 * 累计管理费
	 */
	private Double totalManagerMo;
	
	/**
	 * 累计利息
	 */
	private Double totalInterestMo;
	
	private String idCard;//身份证号
	
	private String password;
	
	private String repassword;
	
	private Double rebate;
	
	private String rebateStr;
	
	private String chTitle;//称号
	
	private String createUsername;
	
	private String id;
	
	private Integer level;
	
	private Integer childNumber;
	
	private Integer allChildNumber;
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}

	public Integer getAllChildNumber() {
		return allChildNumber;
	}

	public void setAllChildNumber(Integer allChildNumber) {
		this.allChildNumber = allChildNumber;
	}

	public WUser getWuser() {
		return wuser;
	}

	public void setWuser(WUser wuser) {
		this.wuser = wuser;
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

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Double getTotalManagerMo() {
		return totalManagerMo;
	}

	public void setTotalManagerMo(Double totalManagerMo) {
		this.totalManagerMo = totalManagerMo;
	}

	public Double getTotalInterestMo() {
		return totalInterestMo;
	}

	public void setTotalInterestMo(Double totalInterestMo) {
		this.totalInterestMo = totalInterestMo;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public String getRebateStr() {
		return rebateStr;
	}

	public void setRebateStr(String rebateStr) {
		this.rebateStr = rebateStr;
	}

	public String getChTitle() {
		return chTitle;
	}

	public void setChTitle(String chTitle) {
		this.chTitle = chTitle;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
