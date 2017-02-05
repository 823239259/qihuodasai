package com.tzdr.common.api.bbpay.vo;

import java.io.Serializable;

/** SendObject概要说明：商户给币币的参数对象
 * <br>@author Administrator
 */
public class PayParamsObject implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商户订单号 唯一订单号
	 */
	private String order;
	/**
	 * 交易时间 
	 */
	private long transtime;
	
	/**
	 * 交易金额(单位分)
	 */
	private int amount;
	/**
	 * 商品种类
	 */
	private String productcategory="1";
	/**
	 * 商品名称
	 */
	private String productname="投资达人充值";
	/**
	 * 商品描述
	 */
	private String productdesc="投资达人充值";
	/**
	 * 商品单价
	 */
	private int productprice;
	/**
	 * 商品数量
	 */
	private int productcount=1;
	
	private String merrmk="信闳";


	/**
	 * 用户使用的移动终端的UA信息，最大长度200
	 */
	private String userua="终端";
	/**
	 * 用户支付时使用的网络终端IP
	 */
	private String userip;
	/**
	 * 用来通知商户支付结果，后台发送post请求，前后台回调地址的回调内容相同，长度100位 ，商户收到请求必须回复内容，内容不能为空
	 */
	private String areturl;
	/**
	 * 用来通知商户支付结果，前后台回调地址的回调内容相同。用户在网页支付成功页面，点击“返回商户”时的回调地址,长度100位
	 */
	private String sreturl;
	/**
	 * 银行卡编号 
	 * 代码
	 * 1开头为所有权限支持（例如：10018，工商银行），
	 * 2开头为只开信用卡支付（例如：20018，工商银行），
	 * 3开头为只开储蓄卡支付（例如：30018，工商银行）
	 */
	private String pnc;
	/**
	 * 商户使用自己生成的RSA私钥对参数除“sign”外的其他参数进行字母排序后串成的字符串进行签名后的字符串
	 */
	private String sign;
	
	/**
	 * 银行简称
	 */
	private String abbreviation;
	/**
	 * 支付类型
	 */
	private String payType="2";
	
	/**
	 * 支付状态
	 */
	private Integer status;
	
	/**
	 * 支付金额（元）
	 */
	private String payMoney;
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public long getTranstime() {
		return transtime;
	}
	public void setTranstime(long transtime) {
		this.transtime = transtime;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getProductcategory() {
		return productcategory;
	}
	public void setProductcategory(String productcategory) {
		this.productcategory = productcategory;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductdesc() {
		return productdesc;
	}
	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}
	public int getProductprice() {
		return productprice;
	}
	public void setProductprice(int productprice) {
		this.productprice = productprice;
	}
	public int getProductcount() {
		return productcount;
	}
	public void setProductcount(int productcount) {
		this.productcount = productcount;
	}
	public String getMerrmk() {
		return merrmk;
	}
	public void setMerrmk(String merrmk) {
		this.merrmk = merrmk;
	}
	public String getUserua() {
		return userua;
	}
	public void setUserua(String userua) {
		this.userua = userua;
	}
	public String getUserip() {
		return userip;
	}
	public void setUserip(String userip) {
		this.userip = userip;
	}
	public String getAreturl() {
		return areturl;
	}
	public void setAreturl(String areturl) {
		this.areturl = areturl;
	}
	public String getSreturl() {
		return sreturl;
	}
	public void setSreturl(String sreturl) {
		this.sreturl = sreturl;
	}
	public String getPnc() {
		return pnc;
	}
	public void setPnc(String pnc) {
		this.pnc = pnc;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	
	

	public PayParamsObject() {
		// TODO Auto-generated constructor stub
	}
	public PayParamsObject(String userip, String abbreviation,
			Integer status, String payMoney,String pnc) {
		super();
		this.userip = userip;
		this.abbreviation = abbreviation;
		this.status = status;
		this.payMoney = payMoney;
		this.pnc=pnc;
	}
	
	
	
	
	
}
