package com.tzdr.web.constants;

public class HttpUrlConstants {
	private static final String QUOTATION = "http://192.168.0.213:8080";
	/**
	 * 获取K线图URL
	 */
	public static final String QUOTATION_QRYHISTORY = QUOTATION +"/qryHistory";
	/**
	 * 查询品种接口
	 */
	public static final String QUOTATION_QRYCOMMODITY = QUOTATION + "/qryContract";
	/**
	 * 获取实时数据
	 */
	public static final String QUOTATION_LAST = QUOTATION + "/last";
}
