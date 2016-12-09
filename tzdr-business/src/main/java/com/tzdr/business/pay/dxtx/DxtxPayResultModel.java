package com.tzdr.business.pay.dxtx;

import java.io.Serializable;

/**
 * 盾行天下支付成功返回数据的model
 * @author username
 *
 */
@SuppressWarnings("serial")
public class DxtxPayResultModel implements Serializable{
	/**
	 * 商户订单号
	 */
	private String tradeNo;
	/**
	 * 盾行支付平台流水号
	 */
	private String tradeCode;
	/**
	 * 第三方支付平台流水号(支付宝、微信)
	 */
	private String tradePaycode;
	/**
	 * 支付类型:1:支付宝，2：微信，3：银联，4:微信公众号，5:微信APP，6：微信扫码
	 */
	private String tradeType;
	/**
	 * 支付金额 单位 元
	 */
	private String tradePrice;
	/**
	 * 附加信息（中文请utf8编码），回调时我们会原样把附加信息返回，未传标识为：404
	 */
	private String tradePrivateinfo;
	/**
	 * 支付时间：yyyyMMddHHmmss
	 */
	private String tradeTime;
	/**
	 * 服务器验证签名
	 */
	private String tradeSign;
	/**
	 * 订单状态：TRADE_SUCCESS 支付成功
	 */
	private String tradeStatus;
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getTradePaycode() {
		return tradePaycode;
	}
	public void setTradePaycode(String tradePaycode) {
		this.tradePaycode = tradePaycode;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}
	public String getTradePrivateinfo() {
		return tradePrivateinfo;
	}
	public void setTradePrivateinfo(String tradePrivateinfo) {
		this.tradePrivateinfo = tradePrivateinfo;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getTradeSign() {
		return tradeSign;
	}
	public void setTradeSign(String tradeSign) {
		this.tradeSign = tradeSign;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	@Override
	public String toString() {
		return "DxtxPayResultModel [tradeNo=" + tradeNo + ", tradeCode=" + tradeCode + ", tradePaycode=" + tradePaycode
				+ ", tradeType=" + tradeType + ", tradePrice=" + tradePrice + ", tradePrivateinfo=" + tradePrivateinfo
				+ ", tradeTime=" + tradeTime + ", tradeSign=" + tradeSign + ", tradeStatus=" + tradeStatus + "]";
	}
	
}
