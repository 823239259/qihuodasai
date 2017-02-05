package com.tzdr.business.pay.dxtx;

/**
 * @author username
 *
 */
/**
 * @author username
 *
 */
public class DxtxConfig {
	private static String[] payUrl = new String[]{"http://payment.dunxingpay.com/Pay.ashx","http://payment1.dunxingpay.com/Pay.ashx"};
	private static Integer[] resCode = new Integer[]{101,102,103};
	/**
	 * 商品id
	 */
	private static Integer goodsId;
	/**
	 * 应用编号
	 */
	private static String appKey;
	/**
	 * 同步通知地址
	 */
	private static String syncnotifyingUrl;
	/**
	 * 异步通知地址
	 */
	private static String notifyingUrl;
	
	/**
	 * 应用签名
	 */
	private static String appSign;
	/**
	 * 验证签名路劲
	 */
	private static String signUrl;
	
	public static String getSignUrl() {
		return signUrl;
	}
	public static void setSignUrl(String signUrl) {
		DxtxConfig.signUrl = signUrl;
	}
	public static String getAppSign() {
		return appSign;
	}
	public static void setAppSign(String appSign) {
		DxtxConfig.appSign = appSign;
	}
	public static Integer getGoodsId() {
		return goodsId;
	}
	public static void setGoodsId(Integer goodsId) {
		DxtxConfig.goodsId = goodsId;
	}
	public static String getAppKey() {
		return appKey;
	}
	public static void setAppKey(String appKey) {
		DxtxConfig.appKey = appKey;
	}
	public static String getSyncnotifyingUrl() {
		return syncnotifyingUrl;
	}
	public static void setSyncnotifyingUrl(String syncnotifyingUrl) {
		DxtxConfig.syncnotifyingUrl = syncnotifyingUrl;
	}
	public static String getNotifyingUrl() {
		return notifyingUrl;
	}
	public static void setNotifyingUrl(String notifyingUrl) {
		DxtxConfig.notifyingUrl = notifyingUrl;
	}
	
	public static String[] getPayUrl() {
		return payUrl;
	}
	public static void setPayUrl(String[] payUrl) {
		DxtxConfig.payUrl = payUrl;
	}
	public static Integer[] getResCode() {
		return resCode;
	}
	public static void setResCode(Integer[] resCode) {
		DxtxConfig.resCode = resCode;
	}
	
}
