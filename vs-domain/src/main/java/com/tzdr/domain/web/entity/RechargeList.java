package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;


@Entity
@Table(name="w_recharge_list")
public class RechargeList   extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String uid;
	private String no;
	private Double fee;
	private Double money;
	private Double actualMoney;//实际到账金额
	// S001:系统调账、 S002：系统冲账  1：快捷支付  2：网银充值  3：支付宝支付？    4：银行转账
	private String type;
	private String tradeAccount;
	private String tradeNo;
	private Integer status;
	private String remark;
	private Long addtime;
	private Long oktime;
	private String addip;
	private Integer uptime;
	private String upip;
	private String account;//银行账号
	// S001:系统调账、 S002：系统冲账
	private String sysType;
	
	private String reAccountId;//充值人员编号
	
	/**
	 * 充值渠道 1:联动优势 2：币币支付 3:易支付 
	 */
	private Integer paymentChannel=0;
	
	
	/**
	 * 来源：1:维胜 2：配股宝
	 */
	private Integer source=1;
	private Integer isRecharge;
	
	
	
	

	public Integer getIsRecharge() {
		return isRecharge;
	}

	public void setIsRecharge(Integer isRecharge) {
		this.isRecharge = isRecharge;
	}

	@Column(name="account", length=32)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Transient 
	private String paytypevalue;
	public String getPaytypevalue() {
		 return CacheManager.getDataMapByKey(DataDicKeyConstants.PAYTYPE, String.valueOf(this.type));
		
	}

	public void setPaytypevalue(String paytypevalue) {
		this.paytypevalue = paytypevalue;
	}

	@Transient 
	private String statusvalue;
	public String getStatusvalue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.PAYSTATUS, String.valueOf(this.status));
		
	}

	public void setStatusvalue(String statusvalue) {
		this.statusvalue = statusvalue;
	}


	@Column(name="uid", length=32)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name="no", nullable=false, length=100)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name="fee", nullable=false, precision=10)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Column(name="money", nullable=false, precision=10)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name="type", nullable=false, length=100)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="trade_account", nullable=false, length=100)
	public String getTradeAccount() {
		return this.tradeAccount;
	}

	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}

	@Column(name="trade_no", nullable=false)
	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String
			tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name="status", nullable=false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="remark", nullable=false)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="addtime", nullable=false)
	public Long getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	@Column(name="oktime", nullable=false)
	public Long getOktime() {
		return this.oktime;
	}

	public void setOktime(Long oktime) {
		this.oktime = oktime;
	}

	@Column(name="addip", nullable=false, length=64)
	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	@Column(name="uptime", nullable=false)
	public Integer getUptime() {
		return this.uptime;
	}

	public void setUptime(Integer uptime) {
		this.uptime = uptime;
	}

	@Column(name="upip", nullable=false, length=64)
	public String getUpip() {
		return this.upip;
	}

	public void setUpip(String upip) {
		this.upip = upip;
	}

	@Column
	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	@Column
	public String getReAccountId() {
		return reAccountId;
	}

	public void setReAccountId(String reAccountId) {
		this.reAccountId = reAccountId;
	}

	@Column
	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public Integer getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(Integer paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
	
	
	
	
	
}