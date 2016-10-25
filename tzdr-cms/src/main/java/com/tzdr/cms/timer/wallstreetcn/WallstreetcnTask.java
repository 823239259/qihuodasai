package com.tzdr.cms.timer.wallstreetcn;

import java.util.TimerTask;

public class WallstreetcnTask extends TimerTask{
	private Wallstreetn wallstreetn;
	
	public Wallstreetn getWallstreetn() {
		return wallstreetn;
	}
	public void setWallstreetn(Wallstreetn wallstreetn) {
		this.wallstreetn = wallstreetn;
	}
	@Override
	public void run() {
		WallstreetcnHandle.getWallstreetcn(wallstreetn);
	}
	/**
	 * 开启执行
	 * @return
	 */
	public boolean start(){
		WallstreetcnTask wallstreetcnTask = new WallstreetcnTask();
		Wallstreetn wallstreetn = wallstreetcnTask.getWallstreetn();
		String url = String.valueOf(WallstreetcnTimer.getMap().get(wallstreetn.getUrl()));
		if(url == null){
			WallstreetcnTimer.getTimer().schedule(wallstreetcnTask, 1000,Long.parseLong(wallstreetn.getRule()));
			WallstreetcnTimer.addTimer(url,wallstreetcnTask);
		}
		return true;
	}
}
