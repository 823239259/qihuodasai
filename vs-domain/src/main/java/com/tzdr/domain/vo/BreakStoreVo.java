package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
/**
 * 
 * @ClassName BreakStoreVo
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年7月7日
 */
public class BreakStoreVo implements Serializable{
	 
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8292539202388073483L;
	/**
	 * 用户手机
	 */
	@AllowExcel(name="手机号")
	private String mobile;
	
	/**
	 * 真实姓名
	 */
	@AllowExcel(name="真实姓名")
	private String tname;
	
	/**
	 * 方案编号
	 */
	@AllowExcel(name="方案编号")
	private String programNo;
	
	/**
	 * 交易账户名
	 */
	@AllowExcel(name="交易账户名")
	private String accountName;
	
	/**
	 * 终结穿仓金额
	 */
	@AllowExcel(name="终结穿仓金额")
	private Double accrual;
	
	/**
	 * 补仓欠费金额
	 */
	@AllowExcel(name="补仓欠费金额")
	private Double amount;
	
	/**
	 * 已付穿仓金额
	 */
	@AllowExcel(name="已付穿仓金额")
	private Double paid;
	
	/**
	 * 未付欠费
	 */
	@AllowExcel(name="未付欠费")
	private Double unpayment;
	
	/**
	 * 账户余额
	 */
	@AllowExcel(name="账户余额")
	private Double avlBal;
	
	/**
	 * 方案终结时间
	 */
	private BigInteger endTime;
	
	/**
	 * 方案终结时间 Str
	 */
	@AllowExcel(name="方案终结时间")
	private String endTimeStr;
	
	/**
	 * 活动类型 0：没有活动，1：8800活动
	 */
	private int activityType;
	
	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	/**
	 * 方案类型
	 */
	@AllowExcel(name="方案类型")
	private String activityTypeStr;

	public String getActivityTypeStr() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.ACTIVITY_TYPE,String.valueOf(this.activityType));
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

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Double getAccrual() {
		return accrual;
	}

	public void setAccrual(Double accrual) {
		this.accrual = accrual;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPaid() {
		return paid;
	}

	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public Double getUnpayment() {
		return unpayment;
	}

	public void setUnpayment(Double unpayment) {
		this.unpayment = unpayment;
	}

	public Double getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(Double avlBal) {
		this.avlBal = avlBal;
	}

	public BigInteger getEndTime() {
		return endTime;
	}

	public void setEndTime(BigInteger endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		if (ObjectUtil.equals(null, this.endTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.endTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}