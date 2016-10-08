package com.tzdr.domain.vo;

import java.io.Serializable;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.constants.Constant;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年4月8日上午11:43:40
 */
public class RechargeBankListVo implements Serializable {
	
	private static final long serialVersionUID = -5464074310157687605L;

	@SqlColumn
	private String id;
	
	@SqlColumn(name="mobile")
	private String mobileNo;
	
	@SqlColumn
	private String tname;
	
	@SqlColumn(name="trade_no")
	private String tradeNo;
	
	@SqlColumn(name="trade_account")
	private String tradeAccount;
	
	@SqlColumn
	private Double money;
	
	@SqlColumn(name="actual_money")
	private Double actualMoney;
	
	@SqlColumn
	private Integer status;
	
	@SqlColumn
	private Integer uptime;
	
	@SqlOrder("uptime")
	private String uptimeStr;
	
	private Integer addtime;
	
	@SqlOrder("addtime")
	private String addtimeStr;
	
	private String tradeAccountBank;
	
	private String statusStr;
	
	private String account;
	@SqlColumn
	private String no;
	/**
	 * 充值来源
	 */
	@SqlColumn
	private Integer source;

	@SqlColumn
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@SqlColumn
	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	public String getTradeAccountBank() {
		
		if (this.tradeAccount != null) {
			this.tradeAccountBank = CacheManager.getDataMapByKey("bankname",this.tradeAccount);
		}
		return tradeAccountBank;
	}

	public void setTradeAccountBank(String tradeAccountBank) {
		//所属银行
		this.tradeAccountBank = tradeAccountBank;
	}

	public String getStatusStr() {
		if (this.status != null) {
			this.statusStr = CacheManager.getDataMapByKey("paystatus",this.status + "");
		}
		return statusStr;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getAddtimeStr() {
		if (this.addtime != null && this.addtime != 0) {
			this.addtimeStr = TypeConvert.long1000ToDatetimeStr(this.addtime.longValue());
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTradeAccount() {
		return tradeAccount;
	}

	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUptime() {
		return uptime;
	}
	
	public void setUptime(Integer uptime) {
		this.uptime = uptime;
	}

	public String getUptimeStr() {
		if (uptime != null && uptime != 0) {
			this.uptimeStr = TypeConvert.long1000ToDatetimeStr(this.uptime.longValue());
		}
		return uptimeStr;
	}

	public void setUptimeStr(String uptimeStr) {
		this.uptimeStr = uptimeStr;
	}

	public String getSource() {
		
		if (!ObjectUtil.equals(null, this.source) 
				&& Constant.Source.PGB==this.source){
			return "配股宝";
		}
		return "维胜";
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
	

}
