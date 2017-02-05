package com.tzdr.business.pay.gopay;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static Date date = new Date();
	/**
	 * 获取时间
	 * @param arg 需要转的时间格式
	 * @return
	 */
	public static String getDate(String arg){
		SimpleDateFormat df = new SimpleDateFormat(arg);
		return df.format(date);
	}
}
