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
 * 终结方案审核列表
 * </p>
 * 
 * @author LiuQing
 * @see
 * @version 2.0 2015年5月11日上午10:13:17
 */
public class EndPlanVo implements Serializable {

	private static final long serialVersionUID = -6469817179539585307L;

	@SqlColumn(name = "group_id")
	private String groupId;

	@SqlColumn
	@AllowExcel(name = "手机号")
	private String mobile;

	@SqlColumn
	@AllowExcel(name = "用户姓名")
	private String tname;
	// 恒生账户
	@SqlColumn
	@AllowExcel(name = "交易账号")
	private String account;

	// 恒生账户
	@AllowExcel(name = "交易账户名")
	@SqlOrder
	@SqlColumn
	private String accountName;

	@SqlColumn(name = "program_no")
	@AllowExcel(name = "方案编号")
	private String programNo;

	@SqlColumn(name = "lever_money")
	@AllowExcel(name = "保证金")
	private Double leverMoney;

	@SqlColumn(name = "voucher_actual_money")
	@AllowExcel(name = "代金券")
	private BigDecimal voucherMoney;

	// 总操盘资金
	@AllowExcel(name = "总操盘资金")
	@SqlOrder("total_lever_money")
	@SqlColumn(name = "total_lever_money")
	private Double totalLeverMoney;

	@SqlColumn(name = "finished_money")
	private Double finishedMoney;

	@AllowExcel(name = "结算余额")
	private String finishedMoneyStr;

	// 审核状态
	@SqlColumn(name = "audit_end_status")
	private Long auditEndStatus;

	@AllowExcel(name = "审核状态")
	private String auditEndStatusStr;

	@SqlColumn
	@AllowExcel(name = "账户类型")
	private int feeType;

	// 审核人
	@SqlColumn
	@AllowExcel(name = "审核人")
	private String endAuditUserIdStr;

	@SqlColumn(name = "end_audit_time")
	private Long endAuditTime;

	// 审核时间
	@AllowExcel(name = "审核时间")
	private String endAuditTimeStr;

	@SqlColumn(name = "end_sub_time")
	private Long endSubTime;

	@AllowExcel(name = "提交时间")
	@SqlOrder("end_sub_time")
	private String endSubTimeStr;

	/**
	 * 活动类型 0：没有活动，1：8800活动
	 */
	@SqlColumn
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
	@AllowExcel(name = "方案类型")
	@SqlOrder("activityType")
	private String activityTypeStr;

	public String getActivityTypeStr() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.ACTIVITY_TYPE, String.valueOf(this.activityType));
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

	public String getFinishedMoneyStr() {
		if (this.finishedMoney != null) {
			BigDecimal finished = TypeConvert.scale(new BigDecimal(this.finishedMoney.toString()), TypeConvert.SCALE_VALUE);
			if (finished != null) {
				finishedMoneyStr = finished.toString();
			}
		}
		return finishedMoneyStr;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setFinishedMoneyStr(String finishedMoneyStr) {
		this.finishedMoneyStr = finishedMoneyStr;
	}

	public Long getAuditEndStatus() {
		return auditEndStatus;
	}

	public void setAuditEndStatus(Long auditEndStatus) {
		this.auditEndStatus = auditEndStatus;
	}

	public String getAuditEndStatusStr() {
		if (this.auditEndStatus != null) {
			this.auditEndStatusStr = CacheManager.getDataMapByKey("auditStaus", this.auditEndStatus.toString());
		}
		return auditEndStatusStr;
	}

	public void setAuditEndStatusStr(String auditEndStatusStr) {
		this.auditEndStatusStr = auditEndStatusStr;
	}

	public String getEndAuditUserIdStr() {
		return endAuditUserIdStr;
	}

	public void setEndAuditUserIdStr(String endAuditUserIdStr) {
		this.endAuditUserIdStr = endAuditUserIdStr;
	}

	public Long getEndSubTime() {
		return endSubTime;
	}

	public void setEndSubTime(Long endSubTime) {
		this.endSubTime = endSubTime;
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

	public BigDecimal getVoucherMoney() {
		return voucherMoney;
	}

	public void setVoucherMoney(BigDecimal voucherMoney) {
		this.voucherMoney = voucherMoney;
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}

	public String getEndAuditTimeStr() {
		if (this.endAuditTime != null) {
			this.endAuditTimeStr = TypeConvert.long1000ToDatetimeStr(this.endAuditTime);
		}

		return endAuditTimeStr;
	}

	public void setEndAuditTimeStr(String endAuditTimeStr) {
		this.endAuditTimeStr = endAuditTimeStr;
	}

	public String getEndSubTimeStr() {
		if (this.endSubTime != null) {
			this.endSubTimeStr = TypeConvert.long1000ToDatetimeStr(this.endSubTime);
		}
		return endSubTimeStr;
	}

	public void setEndSubTimeStr(String endSubTimeStr) {
		this.endSubTimeStr = endSubTimeStr;
	}

	public Long getEndAuditTime() {
		return endAuditTime;
	}

	public void setEndAuditTime(Long endAuditTime) {
		this.endAuditTime = endAuditTime;
	}

	public String getFeeType() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.TRADE_FEETYPE, String.valueOf(this.feeType));
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}

}
