package com.tzdr.business.pay.gopay.model;

public class GoPayCallBackModel {
	/**
	 * 第三方流水号
	 */
	private String orderId;
	/**
	 * 网关发往银 行的流水号
	 */
	private String gopayOutOrderId;
	/**
	 * 返回状态码
	 */
	private String respCode;
	/**
	 * 国付宝返回加密串
	 */
	private String callBackSign;
	/**
	 * 订单号
	 */
	private String merOrderNum;
	/**
	 * 交易金额
	 */
	private String tranAmt;
	/**
	 * 商户提取佣金金额
	 */
	private String feeAmt;
	/**
	 * 交易时间
	 */
	private String tranDateTime;
	/**
	 * 用户浏览器的IP
	 */
	private String tranIP;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品详情
	 */
	private String goodsDetail;
	/**
	 * 买方姓名
	 */
	private String buyerName;
	/**
	 * 买方联系方式
	 */
	private String buyerContact;
	/**
	 * 密文串
	 */
	private String signValue;
	/**
	 * 防钓鱼，从国付宝获取时间,请求时间地址:https://gateway.gopay.com.c n/time.do
	 */
	private String gopayServerTime;
	/**
	 * 银行代码
	 */
	private String bankCode;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 网关版本号
	 */
	private  String version;
	/**
	 * 交易代码
	 */
	private  String tradCode;
	/**
	 * 报文加密方式
	 */
	private  String signType;
	/**
	 * 商户前台通知地址
	 */
	private  String frontMerUrl;
	/**
	 * 商户后台通知地址
	 */
	private  String backgroundMerUrl;
	private String merchantID;
	
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGopayOutOrderId() {
		return gopayOutOrderId;
	}
	public void setGopayOutOrderId(String gopayOutOrderId) {
		this.gopayOutOrderId = gopayOutOrderId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getCallBackSign() {
		return callBackSign;
	}
	public void setCallBackSign(String callBackSign) {
		this.callBackSign = callBackSign;
	}
	public String getMerOrderNum() {
		return merOrderNum;
	}
	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getTranDateTime() {
		return tranDateTime;
	}
	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}
	public String getTranIP() {
		return tranIP;
	}
	public void setTranIP(String tranIP) {
		this.tranIP = tranIP;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}
	public String getSignValue() {
		return signValue;
	}
	public void setSignValue(String signValue) {
		this.signValue = signValue;
	}
	public String getGopayServerTime() {
		return gopayServerTime;
	}
	public void setGopayServerTime(String gopayServerTime) {
		this.gopayServerTime = gopayServerTime;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTradCode() {
		return tradCode;
	}
	public void setTradCode(String tradCode) {
		this.tradCode = tradCode;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getFrontMerUrl() {
		return frontMerUrl;
	}
	public void setFrontMerUrl(String frontMerUrl) {
		this.frontMerUrl = frontMerUrl;
	}
	public String getBackgroundMerUrl() {
		return backgroundMerUrl;
	}
	public void setBackgroundMerUrl(String backgroundMerUrl) {
		this.backgroundMerUrl = backgroundMerUrl;
	}
	
	
}
