package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;

/**
 * 
 * <p>设置预警值</p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年1月12日下午8:47:24
 */
public class RechargeAdditionalVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * 记录id
	 */
	private String id;
	
	/**
	 * 手机号
	 */
	private String  mobile;
	
	/**
	 * 用户姓名
	 */
	private String  username;
	
	/**
	 * 支付宝账号
	 */
	private String  alipayNo;
	
	/**
	 * 银行卡号
	 */
	private String bankCard;
	
	/**
	 * 交易号
	 */
	private String tradeNo;
	
	/**
	 * 充值表单金额
	 */
	private double money=0.0;
	/**
	 * 创建人
	 */
	private String createUser;
	
	
	/**
	 * 创建时间
	 */
	private BigInteger  createTime;

	
	/**
	 * 充值记录id
	 */
	private String rechargeId;

	
	/**
	 * 银行 类型
	 */
	private String  tradeAccount;

	/**
	 * 用户id
	 */
	private String  uid;
	
	
	/**
	 *  充值状态
	 */
	private int  status;
	
	/**
	 *  充值状态 显示
	 */
	private String  statusValue;
	
	/**
	 *  充值类型
	 */
	private String  type;
	
	/**
	 *  ip
	 */
	private String  ip;

	/**
	 * 充值来源（1:维胜 2：配股宝）
	 */
	private Integer  source;
	
	/**
	 * 显示充值来源
	 */
	private String  showSource;
	
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


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getAlipayNo() {
		return alipayNo;
	}


	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}


	public String getBankCard() {
		return bankCard;
	}


	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}


	public String getTradeNo() {
		return tradeNo;
	}


	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}


	public double getMoney() {
		return money;
	}


	public void setMoney(double money) {
		this.money = money;
	}


	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getCreateTime() {
		if (ObjectUtil.equals(null,this.createTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.createTime,Dates.CHINESE_DATETIME_FORMAT_LINE);
	}


	public void setCreateTime(BigInteger createTime) {
		this.createTime = createTime;
	}


	public String getRechargeId() {
		return rechargeId;
	}


	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}


	


	public String getTradeAccount() {
		return tradeAccount;
	}


	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getStatusValue() {
		this.statusValue = CacheManager.getDataMapByKey(DataDicKeyConstants.PAYSTATUS,String.valueOf(this.status));
		return statusValue;
	}


	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}


	public Integer getSource() {
		return source;
	}


	public void setSource(Integer source) {
		this.source = source;
	}


	public String getShowSource() {
		if (!ObjectUtil.equals(null, this.source) 
				&& Constant.Source.PGB==this.source){
			return "配股宝";
		}
		return "维胜";
	}


	public void setShowSource(String showSource) {
		this.showSource = showSource;
	}
	
	
	
}
