package com.tzdr.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 日期工具类
 * <P>title:@DateUtils.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月31日
 * @version 1.0
 */
public class DateUtils {
	public static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * 把包含日期值转换为字符串
	 * @param date 日期（日期+时间）
	 * @param pattern 输出类型
	 * @return 字符串
	 */
	public static String dateTimeToString(java.util.Date date, String pattern) {
		String DateString = "";
		if (date == null) {
			DateString = "";
		} else {
			SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
			DateString = formatDate.format(date);
		}
		return DateString;
	}
	
	/**
	 * 字符串转为日期
	 * @param datestr
	 * @param pattern
	 * @return
	 * @date 2014年12月31日
	 * @author zhangjun
	 */
	public static Date stringToDate(String datestr, String pattern) {
		Date date =new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		try {
			date = formatDate.parse(datestr);
		} catch (ParseException e) {
			logger.error("解析时间错误"+e.getMessage());
		}
		return date;
	}
	
	
}
