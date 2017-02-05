package com.tzdr.cms.timer.wallstreetcn;

public class WallstreetcnTask extends BaseWallstreetnTask{
	private WallstreetcnHandle wallstreetcnHandle;
	
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
}
