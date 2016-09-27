package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;


/**
 * 提现记录表
 * @zhouchen
 * 2015年1月3日
 */
@Entity
@Table(name="w_draw_list")
public class DrawList extends BaseCrudEntity {
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * 用户信息
	 */
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "uid")
	@JsonIgnore
	private WUser user;
	
	/**
	 * 提现金额
	 */
	private double money;

	/**
	 * 订单号
	 */
	private String no;
	
	/**
	 * 提现手续费
	 */
	private double fee;
	
	
	/**
	 * 提现人 姓名
	 */
	private String name;
	
	/**
	 * 提现人银行
	 */
	private String bank;
	
	/**
	 * 提现支行名称--存储银行简称
	 */
	private String subbank;
	
	/**
	 * 提现人卡号
	 */
	private String card;
	
	/**
	 * 提现方式：1：汇付提现，2：预约提现
	 */
	private short method;
	
	/**
	 * 使用积分抵手续费：扣多少积分
	 */
	private Integer score;
	/**
	 * 使用积分抵手续费：抵多少手续费
	 */
	private double sfee;
	
	/**
	 * 交易号
	 */
	private String tradeNo;
	
	/**
	 * 提现状态：1:失败,2:无效,3:已取消,4:汇付提现失败,11:等待处理,21:提现处理中,22:汇付提现成功,31:提现成功
	 */
	private short status;
	
	/**
	 * 说明
	 */
	private String remark;
	/**
	 * 现存储 审核失败原因  
	 */
	private String aremark;
	
	/**
	 * 提现提交时间
	 */
	private Long addtime;
	
	/**
	 * 提现完成时间
	 */
	private Long oktime;
	/**
	 * 提现人ip
	 */
	private String addip;
	/**
	 * 管理员确认时间
	 */
	private Long uptime;
	
	/**
	 * 管理员ip
	 */
	private String upip;
	
	/**
	 * 转帐凭证图片
	 */
	private String img;
	/**
	 * 易支付时 NBank 存储商户号
	 */
	private String NBank;
	/**
	 * 易支付时 NArea 存储商户秘钥
	 */
	private String NArea;
	/**
	 * 银行卡开户地址
	 */
	private String accAddress;
	
	private String NCity;
	//初审人
	private String firstAuditUser;
	private Long firstAuditTime;
	private Double operateMoney;
	
	public Double getOperateMoney() {
		return operateMoney;
	}

	public void setOperateMoney(Double operateMoney) {
		this.operateMoney = operateMoney;
	}

	public Long getFirstAuditTime() {
		return firstAuditTime;
	}

	public void setFirstAuditTime(Long firstAuditTime) {
		this.firstAuditTime = firstAuditTime;
	}

	public String getFirstAuditUser() {
		return firstAuditUser;
	}

	public void setFirstAuditUser(String firstAuditUser) {
		this.firstAuditUser = firstAuditUser;
	}

	/**
	 * 默认的UpdateUser 为审核人---
	 * 是否审核 0：未审核  1：已审核，审核通过   2:审核不通过-1初审核通过
	 */
	private Integer isAudit=0;
	
	/**
	 * 线下划账标记 默认走线上为：0，超出金额走线下为：1
	 */
	private int belowLine=0;
	/**
	 * 人工审核提现规则id
	 */
	private String auditId;
	
	
	/**
	 * 充值渠道 1:联动优势 2：币币支付 3:易支付
	 */
	private Integer paymentChannel=0;
	
	
	/**
	 * 来源：1:维胜 2：配股宝
	 */
	private Integer source=1;
	
	
	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	@Transient
	private  String  statusValue;

	public WUser getUser() {
		return user;
	}

	public void setUser(WUser user) {
		this.user = user;
	}
	@Column(name="no")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name="money", nullable=false, precision=10)
	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Column(name="fee", nullable=false, precision=10)
	public double getFee() {
		return this.fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}


	@Column(name="name", nullable=false, length=100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="bank", nullable=false, length=100)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name="subbank")
	public String getSubbank() {
		return this.subbank;
	}

	public void setSubbank(String subbank) {
		this.subbank = subbank;
	}

	@Column(name="card", nullable=false, length=100)
	public String getCard() {
		return this.card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Column(name="method", nullable=false)
	public short getMethod() {
		return this.method;
	}

	public void setMethod(short method) {
		this.method = method;
	}

	@Column(name="score", nullable=false)
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name="sfee", nullable=false, precision=10)
	public double getSfee() {
		return this.sfee;
	}

	public void setSfee(double sfee) {
		this.sfee = sfee;
	}

	@Column(name="trade_no", nullable=false, length=30)
	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name="status", nullable=false)
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	@Column(name="remark", nullable=false)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="aremark", nullable=false)
	public String getAremark() {
		return this.aremark;
	}

	public void setAremark(String aremark) {
		this.aremark = aremark;
	}

	@Column(name="addip", nullable=false, length=64)
	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	

	public Long getAddtime() {
		return addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	public Long getOktime() {
		return oktime;
	}

	public void setOktime(Long oktime) {
		this.oktime = oktime;
	}

	public Long getUptime() {
		return uptime;
	}

	public void setUptime(Long uptime) {
		this.uptime = uptime;
	}

	@Column(name="upip", nullable=false, length=64)
	public String getUpip() {
		return this.upip;
	}

	public void setUpip(String upip) {
		this.upip = upip;
	}

	@Column(name="img")
	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Column(name="n_bank", nullable=false, length=20)
	public String getNBank() {
		return this.NBank;
	}

	public void setNBank(String NBank) {
		this.NBank = NBank;
	}

	@Column(name="n_area", nullable=false, length=50)
	public String getNArea() {
		return this.NArea;
	}

	public void setNArea(String NArea) {
		this.NArea = NArea;
	}

	@Column(name="n_city", nullable=false, length=50)
	public String getNCity() {
		return this.NCity;
	}

	public void setNCity(String NCity) {
		this.NCity = NCity;
	}

	public String getStatusValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAWAL_STATUS,String.valueOf(this.status));
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public int getBelowLine() {
		return belowLine;
	}

	public void setBelowLine(int belowLine) {
		this.belowLine = belowLine;
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

	@Column(name="acc_address", length=255)
	public String getAccAddress() {
		return accAddress;
	}

	public void setAccAddress(String accAddress) {
		this.accAddress = accAddress;
	}
	
	
}