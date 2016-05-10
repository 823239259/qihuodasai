package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

public class DrawMoneyDataVo implements Serializable {
	@SqlColumn
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@SqlColumn
	private String firstauditname;//初审人
	@SqlColumn
	private String reauditname;//复审人
	@SqlColumn
	private Double minmoney; //审核金额最小值
	@SqlColumn
	private Double maxmoney;//审核金额最大值
	@SqlColumn
	private String type;//1自动打开,2人工审核3 线下打款
	@SqlColumn
	private String money;
	@SqlColumn
	private String createUser;
	@SqlColumn
	private String createDate;
	@SqlColumn
	private String finalAuditname;
	@SqlColumn
	private String finalDate;
	public String getFinalAuditname() {
		return finalAuditname;
	}
	public void setFinalAuditname(String finalAuditname) {
		this.finalAuditname = finalAuditname;
	}
	public String getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getFirstauditname() {
		return firstauditname;
	}
	public void setFirstauditname(String firstauditname) {
		this.firstauditname = firstauditname;
	}
	public String getReauditname() {
		return reauditname;
	}
	public void setReauditname(String reauditname) {
		this.reauditname = reauditname;
	}
	public Double getMinmoney() {
		return minmoney;
	}
	public void setMinmoney(Double minmoney) {
		this.minmoney = minmoney;
	}
	public Double getMaxmoney() {
		return maxmoney;
	}
	public void setMaxmoney(Double maxmoney) {
		this.maxmoney = maxmoney;
	}
	public String getType() {
		 return CacheManager.getDataMapByKey(DataDicKeyConstants.AUDIT_TYPE, String.valueOf(this.type));
	}
	public void setType(String type) {
		this.type = type;
	}
}
