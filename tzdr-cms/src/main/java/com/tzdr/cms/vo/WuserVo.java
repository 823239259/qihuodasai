package com.tzdr.cms.vo;

import java.io.Serializable;

import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2014年12月30日上午11:28:03
 */
public class WuserVo implements Serializable  {
	
	
	private static final long serialVersionUID = -3390139639987375733L;

	public WuserVo() {
		
	}
	
    public WuserVo( WUser wuser) {
    	this.id = wuser.getId();
    	//this.wuser = new WUser();
    	//this.wuser.set
		//this.wuser = wuser;
		this.uname = wuser.getUname();
		this.email = wuser.getEmail();
		this.mobile = wuser.getMobile();
		Long ctimeLong = wuser.getCtime();
		if (ctimeLong != null) {
			this.ctime = TypeConvert.long1000ToDatetimeStr(ctimeLong);
		}
		String userType = CacheManager.getDataMapByKey("userType",wuser.getUserType());
		this.userType = userType;
	
		this.lastLoginTime = TypeConvert.long1000ToDatetimeStr(wuser.getLastLoginTime());
		this.totalInterestMo = wuser.getTotalInterestMo();
		this.totalManagerMo = wuser.getTotalManagerMo();
		UserVerified userVerified = wuser.getUserVerified();
		if (userVerified != null) {
			this.idCard = userVerified != null ?userVerified.getIdcard():"";
			this.tname = userVerified.getTname();
			this.notByReason = userVerified.getNotByReason();
			this.lastSubmitVerifiedTime = TypeConvert.long1000ToDatetimeStr(userVerified.getLastSubmitVerifiedTime());
			/*if (userService != null) {
				User user = userService.get(wuser.getUserVerified().getUpdatUserId());
				if (user != null) {
					this.updateUsername = user.getUsername();
				}
			}*/
		}
		//this.lastSubmitVerifiedTime = wuser.getl
		UserVerified uv = wuser.getUserVerified();
		Object newUv = TypeConvert.createBaseTypeClone(uv);
		if (newUv != null) {
			this.userVerified = (UserVerified)newUv;
		}
		if (this.userVerified != null) {
			this.userVerified.getIdcardBack();
		}
		this.wuser = (WUser)TypeConvert.createBaseTypeClone(wuser);
		
		
	}
    
    private String id;
	
	private WUser wuser;
	
	private String uname;//用户姓名
	private String tname;
	private String email;//邮箱
	private String mobile;//手机号
	private String ctime;//注册时间
	
	private String lastLoginTime;//最后登陆时间
	
	private String lastSubmitVerifiedTime;
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
	
	private UserVerified userVerified;
	
	private String notByReason;//不通过原因
	
	private String updateUsername;//修改人
	//正面
	private String idcardFront;
	//背面
	private String idcardBack;
	//手持
	private String idcardPath;
	
	public String getIdcardFront() {
		return idcardFront;
	}

	public void setIdcardFront(String idcardFront) {
		this.idcardFront = idcardFront;
	}

	public String getIdcardBack() {
		return idcardBack;
	}

	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}

	public String getIdcardPath() {
		return idcardPath;
	}

	public void setIdcardPath(String idcardPath) {
		this.idcardPath = idcardPath;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserVerified getUserVerified() {
		return userVerified;
	}

	public void setUserVerified(UserVerified userVerified) {
		this.userVerified = userVerified;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getNotByReason() {
		return notByReason;
	}

	public void setNotByReason(String notByReason) {
		this.notByReason = notByReason;
	}

	public String getLastSubmitVerifiedTime() {
		return lastSubmitVerifiedTime;
	}

	public void setLastSubmitVerifiedTime(String lastSubmitVerifiedTime) {
		this.lastSubmitVerifiedTime = lastSubmitVerifiedTime;
	}

	public String getUpdateUsername() {
		return updateUsername;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}
}
