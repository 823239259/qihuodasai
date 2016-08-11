package com.tzdr.business.pay.gopay;

public class GoConfig {
	/**
	 * 企业id
	 */
	private static String customerId;
	/**
	 * 国付宝账户
	 */
	private static String payAcctId;
	/**
	 * 商户识别码
	 */
	private static String verficationCode;
	public static String getCustomerId() {
		return customerId;
	}
	public static String getPayAcctId() {
		return payAcctId;
	}
	public static String getVerficationCode() {
		return verficationCode;
	}
	public static void setCustomerId(String customerId) {
		GoConfig.customerId = customerId;
	}
	public static void setPayAcctId(String payAcctId) {
		GoConfig.payAcctId = payAcctId;
	}
	public static void setVerficationCode(String verficationCode) {
		GoConfig.verficationCode = verficationCode;
	}
	
	
}
