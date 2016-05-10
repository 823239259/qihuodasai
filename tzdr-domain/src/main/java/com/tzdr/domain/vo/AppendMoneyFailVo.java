package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;

/**
 * @author zhouchen
 * @version 创建时间：2015年4月11日 上午10:38:47
 * 类说明
 */
public class AppendMoneyFailVo  implements Serializable {

	
	private String id;
	
	/**
	 * 用户id
	 */
	private String uid;
	


	/**
	 * 恒生子账户id
	 */
	private String accountId;


	
	/**
	 * 处理状态  默认0:未处理      1：已处理
	 */
	private int status=0;
	
	/**
	 * 手机号
	 */
	@AllowExcel(name="手机号")
	private String mobile;

	/**
	 *真实姓名
	 */
	@AllowExcel(name="客户姓名")
	private String realName;
	
	/**
	 *恒生账户名
	 */
	@AllowExcel(name="交易账户名")
	private String accountName;
	
	/**
	 *恒生账号
	 */
	@AllowExcel(name="交易账户")
	private String accountNo;
	/**
	 * 方案组编号
	 */
	@AllowExcel(name="方案编号")
	private String groupId;
	/**
	 * 追加金额
	 */
	@AllowExcel(name="追加保证金金额")
	private double appendMoney=0.0;
	
	/**
	 *  追加时间
	 */
	@AllowExcel(name="追加时间")
	private BigInteger appendDate;
	/**
	 * 处理人
	 */
	@AllowExcel(name="处理人")
	private String handlerName;
	
	
	/**
	 * 处理时间
	 */
	@AllowExcel(name="处理时间")
	private BigInteger handleDate;

	
	/**
	 * 0:自动开户(钱江版)，1:手工开户(钱江版)，2：手工开户(涌金版)
	 */ 
	
	@AllowExcel(name="账户类型")
	private int feeType=0;
	
	/**
	 * 状态（字符串）
	 */
	@AllowExcel(name="状态")
	private String statusName;
	
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public double getAppendMoney() {
		return appendMoney;
	}

	public void setAppendMoney(double appendMoney) {
		this.appendMoney = appendMoney;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAppendDate() {
		if(ObjectUtil.equals(null, appendDate)){
			return "";
		}
		return Dates.parseBigInteger2Date(appendDate,Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAppendDate(BigInteger appendDate) {
		this.appendDate = appendDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getHandleDate() {
		if(ObjectUtil.equals(null, handleDate)){
			return "";
		}
		return Dates.parseBigInteger2Date(handleDate,Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setHandleDate(BigInteger handleDate) {
		this.handleDate = handleDate;
	}
	public String  getFeeType() {
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

	public String getStatusName() {
		if(0 == this.getStatus()){
			return "未处理";
		}
		return "已处理";
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
