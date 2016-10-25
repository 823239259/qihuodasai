package com.tzdr.cms.timer.wallstreetcn;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WallstreetcnTimer{
	/**
	 * 正在执行的定时任务
	 */
	private static Map<String, TimerTask> map = new HashMap<>();
	private static Timer timer = new Timer();
	
	public static Map<String, TimerTask> getMap() {
		return map;
	}
	public static void setMap(Map<String, TimerTask> map) {
		WallstreetcnTimer.map = map;
	}
	public static Timer getTimer() {
		return timer;
	}
	public static void setTimer(Timer timer) {
		WallstreetcnTimer.timer = timer;
	}
	/**
	 * 停止指定任务
	 * @param key
	 * @return
	 */
	public static boolean stop(String key){
		map.get(key).cancel();
		return true; 
	}
	/**
	 * 将任务加入到任务列表中
	 */
	public static void addTimer(String key,WallstreetcnTask value){
		map.put(key, value);
	}
}
