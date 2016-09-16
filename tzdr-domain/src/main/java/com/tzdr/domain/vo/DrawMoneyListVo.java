package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;

/**
 * 提现审核记录表
 * 
 * @zhouchen 2015年1月3日
 */
public class DrawMoneyListVo implements Serializable {
	/**
	 * 提现记录 id
	 */
	private String id;

	/**
	 * 用户id
	 */
	private String uid;

	/**
	 * 用户手机
	 */
	@AllowExcel(name = "手机号")
	private String mobile;

	/**
	 * 用户真实姓名
	 */
	@AllowExcel(name = "客户姓名")
	private String tname;

	/**
	 * 网银通道
	 */

	private Integer paymentChannel;

	@AllowExcel(name = "网银通道")
	private String paymentChannelStr;

	/**
	 * 提现银行
	 */
	@AllowExcel(name = "提现银行")
	private String bank;

	/**
	 * 提现卡号
	 */
	@AllowExcel(name = "银行卡号")
	private String card;

	/**
	 * 开户地址
	 */
	@AllowExcel(name = "开户地址")
	private String acc_address;

	
	/**
	 * 提现金额
	 */
	@AllowExcel(name = "提现金额")
	private double money;

	/**
	 * 用户平台余额
	 */
	@AllowExcel(name = "平台余额")
	private double balance;

	/**
	 * 提现时间
	 */
	private BigInteger addtime;

	/**
	 * 提现时间 导出
	 */
	@AllowExcel(name = "申请提现时间")
	private String exportAddtime;

	@AllowExcel(name = "提现手续费")
	private double fee;
	/**
	 * 提现成功时间
	 */
	private BigInteger oktime;

	/**
	 * 状态
	 */
	private short status;

	/**
	 * 提现成功时间 导出
	 */

	private String exportOktime;

	/**
	 * 审核人
	 */
	private String auditUser;

	/**
	 * 审核时间
	 */
	private BigInteger auditTime;

	/**
	 * 审核时间 显示
	 */
	private String auditTimeValue;

	/**
	 * 是否审核 0、null：未审核 1：已审核通过 2：审核未通过
	 */
	private Integer isAudit = 0;

	/**
	 * 线下划账标记 默认走线上为：0，超出金额走线下为：1
	 */
	private int belowLine = 0;

	/**
	 * 审核显示状态 0:未审核 1：通过 2：未通过
	 */
	private String auditStatusValue;

	private String firstAuditUser;// 初审人

	/**
	 * 初审时间
	 */
	private BigInteger firstAuditTime;

	/**
	 * 来源：1:维胜 2：配股宝
	 */
	private Integer source = 1;
	@AllowExcel(name = "来源网站")
	private String sourceStr;

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSourceStr() {
		if (ObjectUtil.equals(null, this.source)) {
			return sourceStr;
		}
		if (Constant.Source.TZDR == this.source) {
			return "维胜";
		}
		if (Constant.Source.PGB == this.source) {
			return "配股宝";
		}
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public String getFirstAuditUser() {
		return firstAuditUser;
	}

	public void setFirstAuditUser(String firstAuditUser) {
		this.firstAuditUser = firstAuditUser;
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

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public BigInteger getAddtime() {
		return addtime;
	}

	public void setAddtime(BigInteger addtime) {
		this.addtime = addtime;
	}

	public String getStatus() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAWAL_STATUS, String.valueOf(this.status));
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public BigInteger getOktime() {
		return oktime;
	}

	public void setOktime(BigInteger oktime) {
		this.oktime = oktime;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public BigInteger getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(BigInteger auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public String getExportAddtime() {
		if (ObjectUtil.equals(null, this.addtime)) {
			return "";
		}
		return Dates.format(new Date(this.addtime.longValue() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setExportAddtime(String exportAddtime) {
		this.exportAddtime = exportAddtime;
	}

	public String getExportOktime() {
		if (ObjectUtil.equals(null, this.oktime)) {
			return "";
		}
		return Dates.format(new Date(this.oktime.longValue() * 1000), Dates.CHINESE_DATETIME_FORMAT_LINE);

	}

	public void setExportOktime(String exportOktime) {
		this.exportOktime = exportOktime;
	}

	public int getBelowLine() {
		return belowLine;
	}

	public void setBelowLine(int belowLine) {
		this.belowLine = belowLine;
	}

	public String getAuditStatusValue() {
		if ("合计".equals(this.mobile) && this.isAudit == 0) {
			return "";
		}
		return CacheManager.getDataMapByKey(DataDicKeyConstants.AUDIT_STATUS, String.valueOf(this.isAudit));
	}

	public void setAuditStatusValue(String auditStatusValue) {
		this.auditStatusValue = auditStatusValue;
	}

	public String getAuditTimeValue() {
		if (ObjectUtil.equals(null, this.auditTime)) {
			return "";
		}
		return Dates.parseBigInteger2Date(this.auditTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAuditTimeValue(String auditTimeValue) {
		this.auditTimeValue = auditTimeValue;
	}

	public String getFirstAuditTime() {
		if (ObjectUtil.equals(null, this.firstAuditTime)) {
			return "";
		}
		return Dates.parseBigInteger2Date(this.firstAuditTime, Dates.CHINESE_DATETIME_FORMAT_LINE);

	}

	public void setFirstAuditTime(BigInteger firstAuditTime) {
		this.firstAuditTime = firstAuditTime;
	}

	public Integer getPaymentChannel() {

		return paymentChannel;
	}

	public void setPaymentChannel(Integer paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getPaymentChannelStr() {
		if (ObjectUtil.equals(null, this.paymentChannel)) {
			return paymentChannelStr;
		}
		if (Constant.PaymentChannel.BB_PAY == this.paymentChannel) {
			return "币币支付";
		}
		if (Constant.PaymentChannel.UM_PAY == this.paymentChannel) {
			return "联动优势";
		}
		if (Constant.PaymentChannel.EASE_PAY == this.paymentChannel) {
			return "易支付";
		}
		return paymentChannelStr;
	}

	public void setPaymentChannelStr(String paymentChannelStr) {
		this.paymentChannelStr = paymentChannelStr;
	}

	public String getAcc_address() {
		return acc_address;
	}

	public void setAcc_address(String acc_address) {
		this.acc_address = acc_address;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}





	

}