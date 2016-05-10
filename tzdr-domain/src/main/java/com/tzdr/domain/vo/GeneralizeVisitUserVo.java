package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
* @Description: TODO(推广用户列表Vo)
* @ClassName: GeneralizeVisitUserVo
* @author wangpinqun
* @date 2015年1月13日 下午6:04:31
 */
public class GeneralizeVisitUserVo implements Serializable {
	
	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;
	

	/**
	 * 编号
	 */
	private String id;
	
	/**
	 * 用户手机号码
	 */
	private String mobile;
	
	private String tname;

	/**
	 * 累计佣金
	 */
	private Double totalCommission;
	
	/**
	 * 下级用户总数量
	 */
	private BigInteger totalChild;
	
	/**
	 * 所有下线
	 */
	private Integer allChildNumber;
	
	/**
	 * 返点
	 */
	private Double rebate;

	/**
	 * 默认返点
	 */
	private Double subordinateDefaultRebate;
	
	/**
	 * 用户级别
	 */
	private Integer userGrade;
	
	/**
	 * 创建时间
	 */
	private BigInteger ctime;
	
	/**
	 * 累计返回佣金
	 */
	private Double totalReturnCommission;
	
	
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(Double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getTotalChild() {
		return totalChild;
	}

	public void setTotalChild(BigInteger totalChild) {
		this.totalChild = totalChild;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Double getSubordinateDefaultRebate() {
		return subordinateDefaultRebate;
	}

	public void setSubordinateDefaultRebate(Double subordinateDefaultRebate) {
		this.subordinateDefaultRebate = subordinateDefaultRebate;
	}

	public Integer getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(Integer userGrade) {
		this.userGrade = userGrade;
	}

	public BigInteger getCtime() {
		return ctime;
	}

	public void setCtime(BigInteger ctime) {
		this.ctime = ctime;
	}

	public Double getTotalReturnCommission() {
		return totalReturnCommission;
	}

	public void setTotalReturnCommission(Double totalReturnCommission) {
		this.totalReturnCommission = totalReturnCommission;
	}

	public String getTname() {
		if (tname == null) {
			tname = "";
		}
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Integer getAllChildNumber() {
		return allChildNumber;
	}

	public void setAllChildNumber(Integer allChildNumber) {
		this.allChildNumber = allChildNumber;
	}
	
	
	
}
