package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

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
		this.wuser = wuser;
		this.uname = wuser.getUname();
		this.email = wuser.getEmail();
		this.mobile = wuser.getMobile();
		String ctime = wuser.getCtime()+"";
		if (ctime != null && !"".equals(ctime)) {
			this.ctime = TypeConvert.longStrToDatetimeStr(ctime);
		}
		String userType = CacheManager.getDataMapByKey("userType",wuser.getUserType());
		this.userType = userType;
		
		this.ctime = TypeConvert.longToDatetimeStrNotNull(wuser.getCtime());
		this.lastLoginTime = TypeConvert.longToDatetimeStrNotNull(wuser.getLastLoginTime());
		this.totalInterestMo = wuser.getTotalInterestMo();
		this.totalManagerMo = wuser.getTotalManagerMo();
		UserVerified userVerified = wuser.getUserVerified();
		this.idCard = userVerified != null ?userVerified.getIdcard():"";
		List<WUser> childList = wuser.getChilds();
		this.totalChild = childList == null || childList.isEmpty() ? 0:childList.size();
		
	}
	
	private WUser wuser;
	private String uname;//用户姓名
	private String email;//邮箱
	private String mobile;//手机号
	private String ctime;//注册时间
	private BigInteger usercount;//配置用户总数
	private String totalLending;//配资金额
	private String avlBal;//可用金额
	private String isemail;//是否绑定email
	private String isidcard;//是否绑定身份证
	
	/**
	 * 有效劵数
	 */
	private Integer validVolumeNum = 0;
	
	/**
	 * 层次
	 */
	private Integer level;
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getIsemail() {
		return isemail;
	}

	public void setIsemail(String isemail) {
		this.isemail = isemail;
	}

	public String getIsidcard() {
		return isidcard;
	}

	public void setIsidcard(String isidcard) {
		this.isidcard = isidcard;
	}

	public String getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(String avlBal) {
		this.avlBal = avlBal;
	}

	public String getTotalLending() {
		return totalLending;
	}

	public void setTotalLending(String totalLending) {
		this.totalLending = totalLending;
	}

	public BigInteger getUsercount() {
		return usercount;
	}

	public void setUsercount(BigInteger usercount) {
		this.usercount = usercount;
	}

	private String lastLoginTime;//最后登陆时间
	/**
	 * 用户类型
	 */
	private String userType;
	private String accrual;//盈利
	public String getAccrual() {
		return accrual;
	}

	public void setAccrual(String accrual) {
		this.accrual = accrual;
	}

	/**
	 * 累计管理费
	 */
	private Double totalManagerMo;
	
	/**
	 * 累计利息
	 */
	private Double totalInterestMo;
	
	private String idCard;//身份证号
	
	/**
	 * 下级用户总数量
	 */
	private Integer totalChild;
	
	/**
	 * 返点
	 */
	private Double rebate;

	private Double accruals;
	public Double getAccruals() {
		return accruals;
	}

	public void setAccruals(Double accruals) {
		this.accruals = accruals;
	}

	/**
	 * 累计佣金
	 */
	private Double totalCommission;
	
	private Double totalMoney;
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
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

	public Integer getTotalChild() {
		return totalChild;
	}

	public void setTotalChild(Integer totalChild) {
		this.totalChild = totalChild;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(Double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public Integer getValidVolumeNum() {
		return validVolumeNum;
	}

	public void setValidVolumeNum(Integer validVolumeNum) {
		this.validVolumeNum = validVolumeNum;
	}
}
