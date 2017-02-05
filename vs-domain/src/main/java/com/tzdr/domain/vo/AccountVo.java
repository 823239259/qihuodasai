package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;

/**
 * 
 * <p>设置预警值</p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年1月12日下午8:47:24
 */
public class AccountVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * 账户id
	 */
	private String id;
	
	/**
	 * 手机号
	 */
	@AllowExcel(name="手机号")
	private String  mobile;
	
	/**
	 * 恒生账号
	 */
	@AllowExcel(name="交易账号")
	private String  account;
	
	/**
	 * 恒长帐户所属单元信息
	 */
	@AllowExcel(name="单元序号")
	private String assetId;
	
	/**
	 * 账户类型
	 * 0:自动开户(钱江版)，1:手工开户(钱江版)，2：手工开户(涌金版) 3。同花顺手动版
	 */ 
	@AllowExcel(name="账户类型")
	private int feeType;
	
	/**
	 * 恒生账户状态  2：（已终结）未注销  3：已注销
	 */
	@AllowExcel(name="账户状态")
	private short status;
	
	/**
	 * 终结时间的格式化值
	 */
	@AllowExcel(name="终结时间")
	private String endTimeValue;
	
	/**
	 * 方案终结时间
	 */
	private BigInteger  endtime;
	
	/**
	 * 账户使用状态
	 */
	private short useStatus;
	
	/**
	 * 恒生账户名
	 */
	private String accountName;
	
	
	/**
	 * 恒生母账户名
	 */
	private String parentAccountName;
	
	
	/**
	 * 创建时间
	 */
	private BigInteger  createTime;
	
	/**
	 * 创建人
	 */
	private String  createUser;

	/**
	 * 客户姓名
	 */
	private String tname;
	
	/**
	 * 保险单号
	 */
	private String insuranceNo;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigInteger getEndtime() {
		return endtime;
	}

	public void setEndtime(BigInteger endtime) {
		this.endtime = endtime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStatus() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.CANCEL_STATUS,String.valueOf(this.status));
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getParentAccountName() {
		return parentAccountName;
	}

	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}

	public BigInteger getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BigInteger createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUseStatus() {
		 return CacheManager.getDataMapByKey(DataDicKeyConstants.SUB_ACCOUNT_STATUS,String.valueOf(this.useStatus));
	}

	public void setUseStatus(short useStatus) {
		this.useStatus = useStatus;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getEndTimeValue() {
		if (ObjectUtil.equals(null, this.endtime)) {
			return "";
		}
		return Dates.format(new Date(this.endtime.longValue() * 1000),
				Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setEndTimeValue(String endTimeValue) {
		this.endTimeValue = endTimeValue;
	}
	
	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getFeeType() {
		String tempFeeType =  String.valueOf(this.feeType);
		if (this.feeType==Constant.FeeType.AUTO_OPEN_CASH_RIVER
				|| this.feeType==Constant.FeeType.HAND_OPEN_CASH_RIVER){
			tempFeeType = Constant.FeeType.CASH_RIVER_FEETYPE;
		}
		return CacheManager.getDataMapByKey(DataDicKeyConstants.TRADE_FEETYPE,tempFeeType);

	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}
	
}
