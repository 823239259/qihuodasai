package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 
 * <p>
 * 终结方案审核列表审【1】审【2】导excel特别Vo
 * </p>
 * 
 * @author LiuQing
 * @see
 * @version 2.0 2015年5月11日上午10:13:17
 */
public class EndPlanOneVo implements Serializable {

	private static final long serialVersionUID = -6469817179539585307L;


	
	@AllowExcel(name = "手机号")
	private String mobile;

	
	@AllowExcel(name = "用户姓名")
	private String tname;
	// 恒生账户
	
	@AllowExcel(name = "交易账号")
	private String account;

	// 恒生账户

	@AllowExcel(name = "交易账户名")
	private String accountName;


	@AllowExcel(name = "方案编号")
	private String programNo;


	@AllowExcel(name = "配资保证金")
	private Double leverMoney;

	// 总操盘资金

	@AllowExcel(name = "总操盘资金")
	private Double totalLeverMoney;



	@AllowExcel(name = "账户类型")
	private String feeType;

	
	@AllowExcel(name = "提交时间")
	private String endSubTimeStr;




	/**
	 * 方案类型
	 */
	@AllowExcel(name = "方案类型")
	private String activityTypeStr;

	public String getActivityTypeStr() {
		return activityTypeStr;
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}






	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public Double getLeverMoney() {
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}


	public String getEndSubTimeStr() {
		return endSubTimeStr;
	}

	public void setEndSubTimeStr(String endSubTimeStr) {
		this.endSubTimeStr = endSubTimeStr;
	}


	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

}
