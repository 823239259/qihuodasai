package com.tzdr.common.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityConfig {
	/**
	 * 上线活动开始时间
	 */
	private static String activity_onLineStartTime = "0";
	/**
	 * 上线活动结束时间
	 */
	private static String activity_onLineEndTime = "0";
	/**
	 * 当前时间
	 */
	public static long now_time = new Date().getTime();
	
	public static Map<String, String> map = new HashMap<>();
	public static boolean comparTo(){
		Long end = new Long(map.get("activity_onLineEndTime"));
		Long now = new Long(now_time);
		return end.longValue() >  now.longValue();
	}
	public static void setActivity_onLineStartTime(String activity_onLineStartTime) {
		ActivityConfig.activity_onLineStartTime = activity_onLineStartTime;
	}
	public static void setActivity_onLineEndTime(String activity_onLineEndTime) {
		ActivityConfig.activity_onLineEndTime = activity_onLineEndTime;
	}
	
}
