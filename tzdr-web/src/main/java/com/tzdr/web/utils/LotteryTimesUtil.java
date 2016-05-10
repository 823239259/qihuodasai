/**
 * 
 */
package com.tzdr.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽奖时间工具类
 * 
 * @author uvanix
 *
 */
public class LotteryTimesUtil {

	private static final Logger logger = LoggerFactory.getLogger(LotteryTimesUtil.class);

	/**
	 * 配资文件路径
	 */
	private static final String FILE_PATH = "webconf.properties";

	/**
	 * 获取下次抽奖开箱时间
	 * 
	 * @return
	 */
	public static String getNextLotteryTimes() {
		StringBuffer buf = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK);

		if (w > 1 && w < 4) {
			buf.append("周二(");
			cal.clear();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, (2 - w + 1) < 0 ? (2 - w + 8) : (2 - w + 1));
			buf.append(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			buf.append(" ");
			buf.append(PropertiesUtils.getValueByKey(FILE_PATH, "weixin.lottery.tuesday.time.start"));
			buf.append("-");
			buf.append(PropertiesUtils.getValueByKey(FILE_PATH, "weixin.lottery.tuesday.time.end"));
			buf.append(")");
		} else {
			buf.append("周日(");
			cal.clear();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, (7 - w + 1) == 7 ? 0 : (7 - w + 1));
			buf.append(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			buf.append(" ");
			buf.append(PropertiesUtils.getValueByKey(FILE_PATH, "weixin.lottery.sunday.time.start"));
			buf.append("-");
			buf.append(PropertiesUtils.getValueByKey(FILE_PATH, "weixin.lottery.sunday.time.end"));
			buf.append(")");
		}

		return buf.toString();
	}

	/**
	 * 是否抽奖开箱时间
	 * 
	 * @return
	 */
	public static boolean isLotteryTime() {
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer currDateStart = new StringBuffer(sdf.format(cal.getTime()));
		StringBuffer currDateEnd = new StringBuffer(sdf.format(cal.getTime()));
		currDateStart.append(" ");
		currDateEnd.append(" ");

		try {
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
			if (w == 3) {
				Date start = sdf.parse(currDateStart.append(PropertiesUtils.getValueByKey(FILE_PATH, "weixin.lottery.tuesday.time.start")).toString());
				Date end = sdf.parse(currDateEnd.append(PropertiesUtils.getValueByKey(FILE_PATH, "weixin.lottery.tuesday.time.end")).toString());
				long s1 = cal.getTime().getTime() - start.getTime();
				long s2 = end.getTime() - cal.getTime().getTime();
				flag = (s1 > 0 && s2 > 0);
			} else if (w == 1) {
				Date start = sdf.parse(currDateStart.append(PropertiesUtils.getValueByKey(FILE_PATH, "weixin.lottery.sunday.time.start")).toString());
				Date end = sdf.parse(currDateEnd.append(PropertiesUtils.getValueByKey(FILE_PATH, "weixin.lottery.sunday.time.end")).toString());
				long s1 = cal.getTime().getTime() - start.getTime();
				long s2 = end.getTime() - cal.getTime().getTime();
				flag = (s1 > 0 && s2 > 0);
			}
		} catch (ParseException e) {
			logger.error("ParseException.", e.getMessage());
		}

		return flag;
	}
	
	public static String getValueByKey(String key){
		return PropertiesUtils.getValueByKey(FILE_PATH, key);
	}

	public static void main(String[] args) {
		System.out.println(getNextLotteryTimes());
		System.err.println(isLotteryTime());
	}

}
