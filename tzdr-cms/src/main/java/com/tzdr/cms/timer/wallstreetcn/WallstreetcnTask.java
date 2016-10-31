package com.tzdr.cms.timer.wallstreetcn;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WallstreetcnTask extends TimerTask{
	private Logger logger = LoggerFactory.getLogger(WallstreetcnTask.class);
	private Wallstreetn wallstreetn;
	private WallstreetcnHandle wallstreetcnHandle;
	public Wallstreetn getWallstreetn() {
		return wallstreetn;
	}
	public void setWallstreetn(Wallstreetn wallstreetn) {
		this.wallstreetn = wallstreetn;
	}
	
	public WallstreetcnTask(Wallstreetn wallstreetn) {
		this.wallstreetn = wallstreetn;
	}
	
	
	public WallstreetcnHandle getWallstreetcnHandle() {
		return wallstreetcnHandle;
	}
	public void setWallstreetcnHandle(WallstreetcnHandle wallstreetcnHandle) {
		this.wallstreetcnHandle = wallstreetcnHandle;
	}
	public WallstreetcnTask() {
	}
	@Override
	public void run() {
		wallstreetcnHandle.getWallstreetcn(getWallstreetn());
	}
	/**
	 * 开启执行
	 * @return
	 */
	public boolean start(){
		WallstreetcnTimer wallstreetcnTimer = WallstreetcnTimer.getInstance();
		String url = String.valueOf(wallstreetcnTimer.getMap().get(wallstreetn.getId()));
		if(url == null || url.equals("null")){
			wallstreetcnTimer.getTimer().schedule(this, 1000,Long.parseLong(wallstreetn.getRule()));
			wallstreetcnTimer.addTimer(wallstreetn.getId(),this);
			logger.info("任务加入"+wallstreetn.getUrl()+wallstreetn.getParam());
		}
		return true;
	}
	public static void main(String[] args) {
		Wallstreetn wallstreetn = new Wallstreetn();
		wallstreetn.setMethod("GET");
		wallstreetn.setParam("status=published&order=-created_at&page=1&channelId=4&extractImg=0&extractText=0&limit=2");
		wallstreetn.setRule("5000");
		wallstreetn.setUrl("https://api.wallstreetcn.com/v2/livenews");
		WallstreetcnTask wallstreetcnTask = new WallstreetcnTask();
		wallstreetcnTask.setWallstreetn(wallstreetn);
		wallstreetcnTask.start();
		try {
			Thread.sleep(10000);
			WallstreetcnTimer.getInstance().stop(wallstreetn.getUrl());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
