package com.tzdr.cms.timer.wallstreetcn;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WallstreetcnTimer{
	private static Logger logger = LoggerFactory.getLogger(WallstreetcnTimer.class);
	/**
	 * 正在执行的定时任务
	 */
	private static Map<String, BaseWallstreetnTask> map = new HashMap<>();
	private static Timer timer = new Timer();
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
	public static boolean start(BaseWallstreetnTask baseWallstreetnTask){
		WallstreetcnTimer wallstreetcnTimer = WallstreetcnTimer.getInstance();
		Wallstreetn wallstreetn = baseWallstreetnTask.getWallstreetn();
		String url = String.valueOf(wallstreetcnTimer.getMap().get(wallstreetn.getId()));
		if(url == null || url.equals("null")){
			wallstreetcnTimer.getTimer().schedule(baseWallstreetnTask, 1000,Long.parseLong(wallstreetn.getRule()));
			wallstreetcnTimer.addTimer(wallstreetn.getId(),baseWallstreetnTask);
			logger.info("任务加入"+wallstreetn.getUrl()+wallstreetn.getParam());
		}
		return true;
	}
	/**
	 * 停止指定任务
	 * @param key
	 * @return
	 */
	public static void stop(String key){
		map.get(key).cancel();
		timer.purge();
		removeTimer(key);
		logger.info("任务停止：" + key);
	}
	/**
	 * 将任务加入到任务列表中
	 */
	public  void addTimer(String key,BaseWallstreetnTask value){
		map.put(key, value);
		logger.info("任务加入:" + key);
	}
	/**
	 * 将任务从任务列表移除
	 */
	public static void removeTimer(String key){
		map.remove(key);
	}
	
	
	public  Map<String, BaseWallstreetnTask> getMap() {
		return map;
	}


	public  void setMap(Map<String, BaseWallstreetnTask> map) {
		WallstreetcnTimer.map = map;
	}

	public  Timer getTimer() {
		return timer;
	}


	public  void setTimer(Timer timer) {
		WallstreetcnTimer.timer = timer;
	}
}
