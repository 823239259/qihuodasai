package com.tzdr.web.constants;

public class HttpUrlConstants {
	private static final String QUOTATION = "http://socket.vs.com:9003";
	/**
	 * 获取K线图URL
	 */
	public static final String QUOTATION_QRYHISTORY = QUOTATION +"/qryHistory";
	/**
	 * 查询品种接口
	 */
	public static final String QUOTATION_QRYCOMMODITY = QUOTATION + "/qryCommodity";
	/**
	 * 查询合约接口
	 */
	public static final String QUOTATION_QRYCOMTRACT = QUOTATION + "/qryContract";
	/**
	 * 获取实时数据
	 */
	public static final String QUOTATION_LAST = QUOTATION + "/last";
}
