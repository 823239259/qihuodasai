package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 
 * <p>
 * 设置预警值
 * </p>
 * 
 * @author LiuQing
 * @see
 * @version 2.0 2015年1月12日下午8:47:24
 */
public class EndProgramVo implements Serializable {

	private static final long serialVersionUID = -6469817179539585307L;

	/**
	 * Account ID
	 */
	@SqlColumn
	private String accountId;

	// 恒生账户
	@SqlColumn
	@SqlOrder("account")
	private String account;
	
	
	// 恒生账户
	@SqlColumn
	@SqlOrder
	private String accountName;

	@SqlColumn
	@SqlOrder("mobile")
	private String mobile;
	// 组ID
	@SqlColumn
	private String groupId;

	// 欠费金额
	@SqlColumn
	@SqlOrder("money")
	private Double money;

	/**
	 * 买入状态false 未限制
	 */
	@SqlColumn
	private Boolean buyStatus;

	@SqlOrder("buyStatus")
	private String buyStatusStr;

	/**
	 * 卖出状态false 未限制
	 */
	@SqlColumn
	private Boolean sellStatus;

	@SqlOrder("sellStatus")
	private String sellStatusStr;

	@SqlColumn
	@SqlOrder("tname")
	private String tname;

	@SqlColumn
	@SqlOrder("programNo")
	private String programNo;

	@SqlColumn
	private long addtime;

	@SqlOrder("addtime")
	private String addtimeStr;

	/**
	 * 钱江版1 | 涌金版2
	 */
	private Integer type;

	/**
	 * 结算金额
	 */
	private Double finishedMoney;

	/**
	 * 可用余额
	 */
	@SqlColumn
	@SqlOrder("avlBal")
	private Double avlBal;

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
	@SqlOrder("activityType")
	private String activityTypeStr;

	public String getActivityTypeStr() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.ACTIVITY_TYPE, String.valueOf(this.activityType));
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}

	public EndProgramVo() {
		super();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Double getMoney() {
		return BigDecimalUtils.round2(money, 2);
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(Boolean buyStatus) {
		this.buyStatus = buyStatus;
	}

	public String getBuyStatusStr() {
		if (this.buyStatus != null && this.buyStatus) {
			String value = CacheManager.getDataMapByKey("limitStatus", "true");
			this.buyStatusStr = value;
		} else {
			String value = CacheManager.getDataMapByKey("limitStatus", "false");
			this.buyStatusStr = value;
		}
		return buyStatusStr;
	}

	public void setBuyStatusStr(String buyStatusStr) {
		this.buyStatusStr = buyStatusStr;
	}

	public Boolean getSellStatus() {
		return sellStatus;
	}

	public void setSellStatus(Boolean sellStatus) {
		this.sellStatus = sellStatus;
	}

	public String getSellStatusStr() {

		if (this.sellStatus != null && this.sellStatus) {
			String value = CacheManager.getDataMapByKey("limitStatus", "true");
			this.sellStatusStr = value;
		} else {
			String value = CacheManager.getDataMapByKey("limitStatus", "false");
			this.sellStatusStr = value;
		}
		return sellStatusStr;
	}

	public void setSellStatusStr(String sellStatusStr) {
		this.sellStatusStr = sellStatusStr;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	public long getAddtime() {
		return addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}

	public String getAddtimeStr() {
		if (this.addtime != 0) {
			this.addtimeStr = TypeConvert.long1000ToDatetimeStr(this.addtime);
		}
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

	public Double getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(Double avlBal) {
		this.avlBal = avlBal;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
