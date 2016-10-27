package com.tzdr.cms.timer.wallstreetcn;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WallstreetcnTimer{
	/**
	 * 正在执行的定时任务
	 */
	private  Map<String, TimerTask> map = new HashMap<>();
	private  Timer timer = new Timer();
	private static WallstreetcnTimer wallstreetcnTimer = null;
	/**
	 * 获取对象
	 * @return
	 */
	public static WallstreetcnTimer getInstance() {
		if(wallstreetcnTimer == null){
			synchronized(Timer.class){
				if(wallstreetcnTimer == null){
					wallstreetcnTimer = new WallstreetcnTimer();
				}
			}
		}
		return wallstreetcnTimer;
	}
	public Map<String, TimerTask> getMap() {
		return map;
	}


	public void setMap(Map<String, TimerTask> map) {
		this.map = map;
	}


	public Timer getTimer() {
		return timer;
	}


	public void setTimer(Timer timer) {
		this.timer = timer;
	}


	/**
	 * 停止指定任务
	 * @param key
	 * @return
	 */
	public  void stop(String key){
		map.get(key).cancel();
		timer.purge();
		removeTimer(key);
	}
	/**
	 * 将任务加入到任务列表中
	 */
	public  void addTimer(String key,WallstreetcnTask value){
		map.put(key, value);
	}
	/**
	 * 将任务从任务列表移除
	 */
	public  void removeTimer(String key){
		map.remove(key);
	}
}
