package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
* @Description: TODO(配资信息VO类)
* @ClassName: UserTradeVo
* @author wangpinqun
* @date 2015年1月5日 下午3:39:20
 */
public class UserTradeVo implements Serializable{

	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;
	@SqlColumn
	private String id;
	
	/**
	 * 用户编号
	 */
	@SqlColumn
	private String uid;
	
	/**
	 * 信闳帐号
	 */
	@SqlColumn
	private String groupId;
	
	/**
	 * 恒生账户
	 */
	@SqlColumn
	private String account;
	
	/**
	 * 恒生账户密码
	 */
	@SqlColumn
	private String password;
	
	/**
	 * 恒生账号开户券商
	 */
	@SqlColumn
	private String hsBelongBroker;
	
	/**
	 * 恒生某帐号总操盘资金
	 */
	@SqlColumn
	private Double totalAccountLeverMoney;
	
	/**
	 * 恒生某帐号(总配资金额)
	 */
	@SqlColumn
	private Double totalMoney;
	
	/**
	 * 恒生某帐号总亏损警告线
	 */
	@SqlColumn
	private Double totalWarning;
	
	/**
	 * 恒生某帐号总亏损平仓线
	 */
	@SqlColumn
	private Double totalOpen;
	
	/**
	 * 恒生某帐号总风险保证金
	 */
	@SqlColumn
	private Double totalLeverMoney;
	
	/**
	 * 恒生某帐号总追加保证金
	 */
	@SqlColumn
	private Double totalAppendLeverMoney;
	
	/**
	 * 开始交易时间
	 */
	@SqlColumn
	private Long starttime;
	
	/**
	 * 状态
	 */
	@SqlColumn
	private Short status;
	
	/**
	 * 恒生某帐号总盈亏
	 */
	@SqlColumn
	private Double totalAccrual;
	
	public UserTradeVo(String uid, String groupId, String account,
			String password, String hsBelongBroker,
			Double totalAccountLeverMoney, Double totalMoney,
			Double totalWarning, Double totalOpen, Double totalLeverMoney,
			Double totalAppendLeverMoney, Long starttime, Short status,String parentAccountNo,String assetId) {
		super();
		this.uid = uid;
		this.groupId = groupId;
		this.account = account;
		this.password = password;
		this.hsBelongBroker = hsBelongBroker;
		this.totalAccountLeverMoney = totalAccountLeverMoney;
		this.totalMoney = totalMoney;
		this.totalWarning = totalWarning;
		this.totalOpen = totalOpen;
		this.totalLeverMoney = totalLeverMoney;
		this.totalAppendLeverMoney = totalAppendLeverMoney;
		this.starttime = starttime;
		this.status = status;
		this.parentAccountNo = parentAccountNo;
		this.assetId = assetId;
	}

	/**
	 * 用户姓名
	 */
	private String uname;
	
	
	/**
	 * 用户手机号码
	 */
	private String mobile;
	
	/**
	 * 某帐号总配资金额
	 */
	private Double totalLending;
	
	/**
	 * 恒生某帐号总操盘资金
	 */
	private Double totalOperateMoney;
	
	/**
	 * 亏损警告线
	 */
	private Double warning;
	
	/**
	 * 亏损平仓线
	 */
	private Double open;
	
	/**
	 * 结束时间
	 */
	private Long endtime;
	
	/**
	 * 母账户的 编号
	 */
	@SqlColumn
	private String parentAccountNo;
	
	
	/**
	 * 母账户管理单元序号
	 */
	private String unitNumber;
	
	/**
	 * 补仓提醒通知状态
	 */
	private Short noticeStatus;
	
	/**
	 *  资产总值
	 */
	private double assetTotalValue;
	
	/**
	 * 单元序号
	 */
	@SqlColumn
	private String assetId;
	
	
	/**
	 * 组合号
	 */
	private String combineId;
	
	
	/**
	 * 0:自动开户(钱江版)，1:手工开户(钱江版)，2：手工开户(涌金版)
	 */ 
	@SqlColumn
	private Integer feeType;
	
	/**
	 * 
	 * 开户审核状态------ 0：开户待审核    1：开户通过（变为操盘中）   2：审核不通过 （开户失败） 
	 */
	@SqlColumn
	private Integer auditStatus;
	
	
	 /**
	  * 终结审核状态----- 0：终结待审核    1：方案终结审核通过          2：方案终结审核不通过
	  */
	@SqlColumn
	 private Integer auditEndStatus;


	public String getCombineId() {
		return combineId;
	}

	public void setCombineId(String combineId) {
		this.combineId = combineId;
	}

	/**
	 * 最低补保证金
	 */
	private double  minimumDeposit;
	
	/**
	 * 保险单号
	 */
	@SqlColumn
	private String insuranceNo;
	
	public UserTradeVo(){
		
	}

	public UserTradeVo(String uid, String account, String password,Double totalLending, Double totalLeverMoney,
			Double totalAppendLeverMoney, Double warning, Double open,
			Long starttime, Long endtime, Short status,String hsBelongBroker,String combineId) {
		super();
		this.uid = uid;
		this.account = account;
		this.password = password;
		this.totalLending = totalLending;
		this.totalLeverMoney = totalLeverMoney;
		this.totalAppendLeverMoney = totalAppendLeverMoney;
		this.warning = warning;
		this.open = open;
		this.starttime = starttime;
		this.endtime = endtime;
		this.status = status;
		this.hsBelongBroker = hsBelongBroker;
		this.combineId = combineId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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

	public Double getTotalLending() {
		return totalLending;
	}

	public void setTotalLending(Double totalLending) {
		this.totalLending = totalLending;
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}

	public Double getTotalAppendLeverMoney() {
		return totalAppendLeverMoney;
	}

	public void setTotalAppendLeverMoney(Double totalAppendLeverMoney) {
		this.totalAppendLeverMoney = totalAppendLeverMoney;
	}

	public Double getWarning() {
		return warning;
	}

	public void setWarning(Double warning) {
		this.warning = warning;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	
	public Long getStarttime() {
		return starttime;
	}

	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Double getTotalAccrual() {
		return totalAccrual;
	}

	public void setTotalAccrual(Double totalAccrual) {
		this.totalAccrual = totalAccrual;
	}

	public String getHsBelongBroker() {
		return hsBelongBroker;
	}

	public void setHsBelongBroker(String hsBelongBroker) {
		this.hsBelongBroker = hsBelongBroker;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNoticeStatus() {
		return  CacheManager.getDataMapByKey(DataDicKeyConstants.CALL_NOTICE_STATUS,String.valueOf(this.noticeStatus));
	}

	public void setNoticeStatus(Short noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getParentAccountNo() {
		return parentAccountNo;
	}

	public void setParentAccountNo(String parentAccountNo) {
		this.parentAccountNo = parentAccountNo;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public double getAssetTotalValue() {
		return assetTotalValue;
	}

	public void setAssetTotalValue(double assetTotalValue) {
		this.assetTotalValue = assetTotalValue;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public double getMinimumDeposit() {
		return minimumDeposit;
	}

	public void setMinimumDeposit(double minimumDeposit) {
		this.minimumDeposit = minimumDeposit;
	}

	public Double getTotalOperateMoney() {
		this.totalOperateMoney=BigDecimalUtils.add(BigDecimalUtils.add(this.totalMoney, this.totalLeverMoney),this.totalAppendLeverMoney);
		return totalOperateMoney;
	}

	public void setTotalOperateMoney(Double totalOperateMoney) {
		this.totalOperateMoney = totalOperateMoney;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Double getTotalAccountLeverMoney() {
		return totalAccountLeverMoney;
	}

	public void setTotalAccountLeverMoney(Double totalAccountLeverMoney) {
		this.totalAccountLeverMoney = totalAccountLeverMoney;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getTotalWarning() {
		return totalWarning;
	}

	public void setTotalWarning(Double totalWarning) {
		this.totalWarning = totalWarning;
	}

	public Double getTotalOpen() {
		return totalOpen;
	}

	public void setTotalOpen(Double totalOpen) {
		this.totalOpen = totalOpen;
	}

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getAuditEndStatus() {
		return auditEndStatus;
	}

	public void setAuditEndStatus(Integer auditEndStatus) {
		this.auditEndStatus = auditEndStatus;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}
}
