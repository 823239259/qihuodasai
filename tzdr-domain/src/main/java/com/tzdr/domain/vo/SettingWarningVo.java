package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * <p>设置预警值</p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年1月12日下午8:47:24
 */
public class SettingWarningVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	@SqlColumn
	private String id;
	
	@SqlColumn
	private String accountId;
	//恒生账户
	@SqlColumn
	private String account;
	//组ID
	@SqlColumn
	private String groupId;
	//配资金额
	@SqlColumn
	private Double money;
	@SqlColumn
	private Double leverMoney;//风险保证金
	@SqlColumn
	private Double open;//亏损平仓线
	
	/**
	 * 总操盘资金
	 */
	@SqlColumn
	private Double totalLeverMoney;
	
	@SqlColumn
	private Double warning;//亏损补仓线(元) 
	
	@SqlColumn
	private BigInteger warningProcessTime;
	@SqlColumn
	private BigInteger addtime;
	
	private String addtimeStr;
	
	private String warningProcessTimeStr;
	
	public SettingWarningVo() {
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
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getLeverMoney() {
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}

	public Double getWarning() {
		return warning;
	}

	public void setWarning(Double warning) {
		this.warning = warning;
	}

	

	public BigInteger getWarningProcessTime() {
		return warningProcessTime;
	}

	public void setWarningProcessTime(BigInteger warningProcessTime) {
		this.warningProcessTime = warningProcessTime;
	}

	public String getWarningProcessTimeStr() {
		if (this.warningProcessTime != null) {
			this.warningProcessTimeStr = 
					TypeConvert.long1000ToDatetimeStr(this.warningProcessTime.longValue());
		}
		else {
			this.warningProcessTimeStr = "";
		}
		return warningProcessTimeStr;
	}

	public void setWarningProcessTimeStr(String warningProcessTimeStr) {
		this.warningProcessTimeStr = warningProcessTimeStr;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public BigInteger getAddtime() {
		return addtime;
	}

	public void setAddtime(BigInteger addtime) {
		this.addtime = addtime;
	}

	public String getAddtimeStr() {
		
		if (this.addtime != null) {
			this.addtimeStr = 
					TypeConvert.long1000ToDatetimeStr(this.addtime.longValue());
		}
		else {
			this.addtimeStr = "";
		}
		
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
