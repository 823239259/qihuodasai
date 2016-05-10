package com.tzdr.common.utils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 简单 Date 相关静态工具
 * 
 * @author LinFeng
 */
public class Dates {
	
	private static final Logger logger = LoggerFactory.getLogger(Dates.class);
	
	public static final String CHINE_DATE_FORMAT_TO_MINUTE="yyyy-MM-dd HH:mm";
	public static final String CHINESE_DATE_FORMAT_LONG = "yyyyMMdd";
	public static final String CHINESE_DATE_FORMAT_LINE = "yyyy-MM-dd";
	public static final String CHINESE_DATETIME_FORMAT_LINE = "yyyy-MM-dd HH:mm:ss";
	public static final String CHINESE_DATE_FORMAT_SLASH = "yyyy/MM/dd";
	public static final String CHINESE_DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
	public static final String DATETIME_NOT_SEPARATOR="yyyyMMddHHmmssSSS";
	public static final long DAY_LONG = 60*60*24l;

	private static final String[] SUPPORT_ALL_FORMATS = new String[]{CHINESE_DATE_FORMAT_LINE,CHINESE_DATE_FORMAT_LONG,
			CHINESE_DATETIME_FORMAT_LINE, CHINESE_DATE_FORMAT_SLASH, CHINESE_DATETIME_FORMAT_SLASH};

	private static final String DEFAULT_DATE_FORMAT = CHINESE_DATETIME_FORMAT_LINE;
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

	public synchronized static String format(Date date, String pattern) {
		sdf.applyPattern(pattern);
		return sdf.format(date);
	}

	public static String format(Date date) {
		return format(date, DEFAULT_DATE_FORMAT);
	}

	public static String format(String pattern) {
		return format(new Date(), pattern);
	}

	public synchronized static Date parse(String dateString, String pattern) {
		sdf.applyPattern(pattern);
		try {
			return sdf.parse(dateString);
		} catch (Exception e) {
			throw new RuntimeException("parse String[" + dateString + "] to Date faulure with pattern[" + pattern
					+ "].");
		}
	}

	public synchronized static Date parse(String dateString, String[] patterns) {
		for (String pattern : patterns) {
			if (StringUtil.isBlank(pattern)) {
				continue;
			}
			sdf.applyPattern(pattern);
			try {
				return sdf.parse(dateString);
			} catch (Exception e) {
				// ignore exception
				continue;
			}
		}
		throw new UnsupportedOperationException("Parse String[" + dateString + "] to Date faulure with patterns["
				+ Arrays.toString(patterns) + "]");

	}

	public static Date parse(String dateString) {
		return parse(dateString, SUPPORT_ALL_FORMATS);
	}

	public static Date addDay(Date date) {
		long oneDayMillisecond = 24 * 60 * 60 * 1000l;
		return addDate(date, oneDayMillisecond);
	}

	public static Date minusDay(Date date) {
		long oneDayMillisecond = 24 * 60 * 60 * 1000l;
		return addDate(date, -oneDayMillisecond);
	}

	public static Date addDate(Date date, long millisecond) {
		return new Date(date.getTime() + millisecond);
	}


	public static Long getCurrentLongDate() {
		return new Date().getTime()/1000;
	}

	/**
	 * 时间相加
	 * @param date
	 * @param day
	 * @return
	 */
	public static String dateAddDay(Date date,int day) {
		    Calendar cl = Calendar.getInstance();
		    cl.setTime(date);
		    cl.add(Calendar.DATE,day);
		    cl.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat(CHINESE_DATETIME_FORMAT_LINE);
			return sdf.format(cl.getTime());
	}
	/**
	 * 将通过 getCurrentLongDate 方法得到的值，转换为Date
	 * @param date
	 * @return
	 */
	public static Date parseLong2Date(Long date) {
		return new Date(date*1000);
	}
	
	/**
	 * 获取当前天 的long值  即：2015-01-01 00：00：00的 long值
	 * @return
	 */
	public static  Long getCurrentLongDay(){
		Calendar  calendar  = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTimeInMillis()/1000;
	}
	
	/**
	 * 获取当前天 的long值  即：2016-01-01 00：00：00的 long值/2016-01-01 hour_of_day：minute：second
	 * @param hour_of_day 小时
	 * @param minute  分钟
	 * @param second  秒钟
	 * @return
	 */
	public static  Long getCurrentLongDay(int hour_of_day,int minute,int second){
		Calendar  calendar  = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
		calendar.set(Calendar.MINUTE,minute);
		calendar.set(Calendar.SECOND,second);
		return calendar.getTimeInMillis()/1000;
	}
	
	/**
	 * 获取当前天 的long值  即：2015-01-01 23：59：59的 long值
	 * @return
	 */
	public static  Long getLastLongDay(){
		Calendar  calendar  = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return calendar.getTimeInMillis()/1000;
	}
	
	/**
	 * 获取下前天 的long值  即：2015-01-01 00：00：00的 long值
	 * @return
	 */
	public static  Long getNextLongDay(){
		Calendar  calendar  = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTimeInMillis()/1000;
	}
	
	/**
	 * 获取 传人的日期的 0：0：0对应的long值
	 * @return
	 */
	public static  Long getDateZeroLong(Long date){
		Calendar  calendar  = Calendar.getInstance();
		calendar.setTime(parseLong2Date(date));
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTimeInMillis()/1000;
	}
	
	/**
	 * 获取昨天最早的时间long值
	 * @return
	 */
	public static Long getYestodayZeroLong(){
		Calendar  calendar  = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,1);
		return calendar.getTimeInMillis()/1000;
	}
	
	/**
	 * 根据天数获取几天前的最早时间
	 * @param daynum 天数 
	 * @return
	 * @date 2015年2月9日
	 * @author zhangjun
	 */
	public static Long getDatebyDaynum(int daynum){
		Calendar  calendar  = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,daynum);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTimeInMillis()/1000;
	}
	
	
	/**
	 * 获取当前天 的long值  即：2015-01-01 23：59：59的 long值
	 * @return
	 */
	public static  Long getLastLongDayNum(int daynum){
		Calendar  calendar  = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,daynum);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return calendar.getTimeInMillis()/1000;
	}
	
	
	/**
	 * 获取前两天的时间long值  如：20150114
	 * @return
	 */
	public static Long getBeforeTwoDaysLong(){
		Calendar  calendar  = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-2);
		String daysLong = format(calendar.getTime(), CHINESE_DATE_FORMAT_LONG);
		return NumberUtils.toLong(daysLong);
	}
	
	public static String parseBigInteger2Date(BigInteger date,String format) {
		return Dates.format(Dates.parseLong2Date(NumberUtils.toLong(String.valueOf(date))), format);
	}
	
	/**
     * 格式化日期，
     * 
     * @param date
     * @return
     */
    public static Date toDate(String datestr){
        if (datestr == null || "".equals(datestr.trim()))
        {
            return null;
        }
        SimpleDateFormat sf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        if (datestr.indexOf(":") == -1)
            sf.applyPattern(CHINESE_DATE_FORMAT_LINE);
        else
            sf.applyPattern(CHINESE_DATETIME_FORMAT_LINE);
        Date date = null;
        try
        {
            date = sf.parse(datestr);
        }
        catch (ParseException e)
        {
            logger.error("日期格式化错误:" + e.getMessage());
        }
        return date;
    }
    
    /**
     * 相差天数
     * 
     * @param date
     * @return
     */
    public static int daysBetween(Date date1,Date date2){  

		Calendar cal = Calendar.getInstance();  

		cal.setTime(date1);  

		long time1 = cal.getTimeInMillis();               

		cal.setTime(date2);  

		long time2 = cal.getTimeInMillis();       

		long between_days=(time2-time1)/(1000*3600*24);  

		return Integer.parseInt(String.valueOf(between_days));        
		
	} 
    
    /**
     * 相差天数,不足一天按一天算
     * 
     * @param date
     * @return
     */
    public static int daysBetween2(Date date1,Date date2){
    	Calendar cal = Calendar.getInstance();  

		cal.setTime(date1);  

		long time1 = cal.getTimeInMillis();               

		cal.setTime(date2);  

		long time2 = cal.getTimeInMillis();       

		long between_days=(time2-time1)/(1000*3600*24);  
		
		between_days = (time2-time1)%(1000*3600*24) > 0 ? between_days + 1 : between_days;

		return Integer.parseInt(String.valueOf(between_days)); 
    }
    
    /**
     * 获取指定天
     * @param dateStr 时间
     * @param pattern 格式
     * @param subtractDate  减的天数
     * @return
     */
    public static long getAssignDateLong(String dateStr, String pattern,long subtractDate){
    	SimpleDateFormat df = new SimpleDateFormat(pattern);
        long dateLong = 0;
		try {
			dateLong = (df.parse(dateStr).getTime()-subtractDate*1000)/1000;
		} catch (ParseException e) {
			logger.error("日期格式化错误:" + e.getMessage());
		}
        return dateLong;
    }
    
    /**
     * 验证是否日期
     * @param str
     * @param pattern
     * @return
     */
	public static boolean isValidDate(String str,String pattern) {
    	  // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
          SimpleDateFormat format = new SimpleDateFormat(pattern);
    	    try {
    	    	//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
    	       format.setLenient(false);
               format.parse(str);
    	    } catch (ParseException e) {
               // e.printStackTrace();
    	       // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
               return false;
            } 
          return true;
     }
}
