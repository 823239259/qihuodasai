package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;

import jodd.util.ObjectUtil;

/**
 * Created by Administrator on 2016/4/19.
 */
public class FutureMatchAccountVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	@AllowExcel(name = "期货品种")
	private String bueinessTypeStr = "";

	/**
	 * 用户账户
	 */
	@AllowExcel(name = "交易账号")
	private String account;

	/**
	 * 账户密码
	 */
	@AllowExcel(name = "交易密码")
	private String password;

	/**
	 * 分配时间
	 */
	private BigInteger assignTime;

	@SuppressWarnings("unused")
	private String assignTimeStr;

	/**
	 * 开仓手数
	 */
	@AllowExcel(name = "持仓手数")
	private Integer lever;
	/**
	 * 操盘金额
	 */
	@AllowExcel(name = "总操盘资金")
	private Double tradeMoney;

	private String username;

	private String mobile;

	private Integer number;

	/**
	 * 期货类型(1,商品期货综合，2国际期货综合，3富时A50，4国际原油，5恒生指数)
	 */
	private Integer businessType;

	/**
	 * 创建时间
	 */
	private BigInteger createTime;
	
	@AllowExcel(name = "导入时间")
	private String createTimeStr;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigInteger getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(BigInteger assignTime) {
		this.assignTime = assignTime;
	}

	public Integer getLever() {
		if (businessType == 1) {
			lever = 0;
		} else if (businessType == 2) {
			lever = 0;
		}
		return lever;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getBueinessTypeStr() {
		if (businessType == 1) {
			bueinessTypeStr = "商品综合";
		} else if (businessType == 2) {
			bueinessTypeStr = "国际综合";
		} else if (businessType == 3) {
			bueinessTypeStr = "富时A50";
		} else if (businessType == 4) {
			bueinessTypeStr = "国际原油";
		} else if (businessType == 5) {
			bueinessTypeStr = "恒生指数";
		} else if (businessType == 6) {
			bueinessTypeStr = "小恒指";
		}
		return bueinessTypeStr;
	}

	public void setBueinessTypeStr(String bueinessTypeStr) {
		this.bueinessTypeStr = bueinessTypeStr;
	}

	public BigInteger getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BigInteger createTime) {
		this.createTime = createTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getAssignTimeStr() {
		if (ObjectUtil.equals(null, getAssignTime())) {
			return "";
		}
		return Dates.parseBigInteger2Date(getAssignTime(),
				Dates.CHINE_DATE_FORMAT_TO_MINUTE);
	}

	public void setAssignTimeStr(String assignTimeStr) {
		this.assignTimeStr = assignTimeStr;
	}

	public String getCreateTimeStr() {
		if (ObjectUtil.equals(null, getCreateTime())) {
			return "";
		}
		return Dates.parseBigInteger2Date(getCreateTime(),
				Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

}
