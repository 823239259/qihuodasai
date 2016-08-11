package com.tzdr.business.pay.gopay;


public class GoPayConfig extends GoConfig{
	/**
	 * 编码方式
	 */
	private static String merchantEncod;
	/**
	 * 网关版本号
	 */
	private static String version;
	/**
	 * 交易代码
	 */
	private static String tradCode;
	/**
	 * 网关语言版本
	 */
	private static String language;
	/**
	 * 报文加密方式
	 */
	private static String signType;
	/**
	 * 订单是否允许重复提交
	 */
	private static String isRepeatSubmit;
	
	/**
	 * 商户前台通知地址
	 */
	private static String frontMerUrl;
	/**
	 * 商户后台通知地址
	 */
	private static String backgroundMerUrl;
	/**
	 *  币种
	 */
	private static String currencyType;

	private static String serverTimeUrl;


	




	public static String getServerTimeUrl() {
		return serverTimeUrl;
	}




	public static void setServerTimeUrl(String serverTimeUrl) {
		GoPayConfig.serverTimeUrl = serverTimeUrl;
	}




	public static String getCurrencyType() {
		return currencyType;
	}




	public static void setCurrencyType(String currencyType) {
		GoPayConfig.currencyType = currencyType;
	}




	public static String getFrontMerUrl() {
		return frontMerUrl;
	}




	public static void setFrontMerUrl(String frontMerUrl) {
		GoPayConfig.frontMerUrl = frontMerUrl;
	}




	public static String getBackgroundMerUrl() {
		return backgroundMerUrl;
	}




	public static void setBackgroundMerUrl(String backgroundMerUrl) {
		GoPayConfig.backgroundMerUrl = backgroundMerUrl;
	}




	public static String getLanguage() {
		return language;
	}




	public static String getSignType() {
		return signType;
	}






	public static String getIsRepeatSubmit() {
		return isRepeatSubmit;
	}




	public static void setLanguage(String language) {
		GoPayConfig.language = language;
	}




	public static void setSignType(String signType) {
		GoPayConfig.signType = signType;
	}








	public static void setIsRepeatSubmit(String isRepeatSubmit) {
		GoPayConfig.isRepeatSubmit = isRepeatSubmit;
	}




	public static String getMerchantEncod() {
		return merchantEncod;
	}




	public static void setMerchantEncod(String merchantEncod) {
		GoPayConfig.merchantEncod = merchantEncod;
	}




	public static String getVersion() {
		return version;
	}




	public static void setVersion(String version) {
		GoPayConfig.version = version;
	}




	public static String getTradCode() {
		return tradCode;
	}




	public static void setTradCode(String tradCode) {
		GoPayConfig.tradCode = tradCode;
	}

}
