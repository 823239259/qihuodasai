package com.tzdr.cms.timer.wallstreetcn;

import java.util.TimerTask;

public abstract class BaseWallstreetnTask extends TimerTask{
	private Wallstreetn wallstreetn;
	/**
	 * 数据处理类
	 */
	private BaseWallstreetcnHandle baseWallstreetcnHandle;
	public Wallstreetn getWallstreetn() {
		return wallstreetn;
	}
	public BaseWallstreetnTask() {
	}
	
	public BaseWallstreetcnHandle getBaseWallstreetcnHandle() {
		return baseWallstreetcnHandle;
	}

	public void setBaseWallstreetcnHandle(BaseWallstreetcnHandle baseWallstreetcnHandle) {
		this.baseWallstreetcnHandle = baseWallstreetcnHandle;
	}

	public void setWallstreetn(Wallstreetn wallstreetn) {
		this.wallstreetn = wallstreetn;
	}
}
