package com.tzdr.domain.vo;

import java.io.Serializable;

/**
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2014年12月30日上午11:28:03
 */
public class WuserParentVo implements Serializable  {
	
	/**
	 * 用户姓名
	 */
	private String  tname;
	
	/**
	 * 用户手机号
	 */
	private String mobile;
	
	/**
	 * 用户返点
	 */
	private Double rebate;
	
	/**
	 * 用户层级
	 */
	private Integer level;
	
	/**
	 * 用户上级id
	 */
	private String parentid;

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	public WuserParentVo() {
		// TODO Auto-generated constructor stub
	}

	public WuserParentVo(String tname) {
		super();
		this.tname = tname;
	}
	
	
	
	
}
