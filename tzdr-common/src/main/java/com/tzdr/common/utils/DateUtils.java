package com.tzdr.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
	// 获得当前日期与本周日相差的天数  
	public static int getMondayPlus(Date gmtCreate) {  
	    Calendar cd = Calendar.getInstance();  
	    cd.setTime(gmtCreate);  
	    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; 
	    if (dayOfWeek == 1) {  
	        return 0;  
	    } else {  
	        return 1 - dayOfWeek;  
	    }  
	}  
	/**
	 * 按照num加减来计算相应的时间
	 * @param gmtCreate
	 * @param num
	 * @return
	 */
	public static Date addDates(Date gmtCreate,Integer num) {  
	    int mondayPlus = getMondayPlus(gmtCreate);  
	    GregorianCalendar currentDate = new GregorianCalendar();  
	    currentDate.add(GregorianCalendar.DATE, mondayPlus + num);  
	    Date monday = currentDate.getTime();  
	    return monday;  
	}
}
