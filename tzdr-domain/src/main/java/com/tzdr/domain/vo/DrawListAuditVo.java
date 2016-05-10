package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.constants.Constant;

public class DrawListAuditVo implements Serializable{
	@SqlColumn
	private Integer source;
	@SqlColumn
	private Integer paymentChannel;
	@SqlColumn
	private Integer belowLine;
	@SqlColumn
	private Integer  isAudit;
	@SqlColumn
	private String uid;
	@SqlColumn
	private String id;
	@SqlColumn
	private String mobile;
	@SqlColumn
	private String bank;
	@SqlColumn
	private String card;
	@SqlColumn
	private Short status; 
	@SqlColumn
	private Double money;
	@SqlColumn
	private String addtime;
	@SqlColumn
	private Long oktime;
	@SqlColumn
	private String tname;
	@SqlColumn
	private Double balance;
	@SqlColumn
	private Long auditTime; 
	@SqlColumn
	private String  auditUser ;
	
	private String starttime;
	private String endtime;
	private String auditstarttime;
	private String auditendtime;
	private String minmoney;
	private String maxmoney;
	private String auditstatus;
	
	private String paymentChannelStr;
	private String sourceStr;
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getSourceStr() {
		if (ObjectUtil.equals(null,this.source)){
			return sourceStr;
		}
		if (Constant.Source.TZDR==this.source){
			return "投资达人";
		}
		if (Constant.Source.PGB==this.source){
			return "配股宝";
		}
		return sourceStr;
	}
	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}
	public String getAuditstatus() {
		return auditstatus;
	}
	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}
	public String getMinmoney() {
		return minmoney;
	}
	public void setMinmoney(String minmoney) {
		this.minmoney = minmoney;
	}
	public String getMaxmoney() {
		return maxmoney;
	}
	public void setMaxmoney(String maxmoney) {
		this.maxmoney = maxmoney;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getAuditstarttime() {
		return auditstarttime;
	}
	public void setAuditstarttime(String auditstarttime) {
		this.auditstarttime = auditstarttime;
	}
	public String getAuditendtime() {
		return auditendtime;
	}
	public void setAuditendtime(String auditendtime) {
		this.auditendtime = auditendtime;
	}
	public Integer getBelowLine() {
		return belowLine;
	}
	public void setBelowLine(Integer belowLine) {
		this.belowLine = belowLine;
	}
	public Integer getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
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
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Double getMoney() {
		if (this.money != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.money), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.money = leverMoneyBig.doubleValue();
			}
		}
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public Long getOktime() {
		return oktime;
	}
	public void setOktime(Long oktime) {
		this.oktime = oktime;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public Integer getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(Integer paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	
	public String getPaymentChannelStr() {
		Integer tempChannel = this.getPaymentChannel();
		if (ObjectUtil.equals(null, tempChannel)){
			return paymentChannelStr;
		}
		if (Constant.PaymentChannel.BB_PAY==tempChannel){
			return "币币支付";
		}
		if (Constant.PaymentChannel.UM_PAY==tempChannel){
			return "联动优势";
		}
		return paymentChannelStr;
	}


	public void setPaymentChannelStr(String paymentChannelStr) {
		this.paymentChannelStr = paymentChannelStr;
	}
	
	
}
