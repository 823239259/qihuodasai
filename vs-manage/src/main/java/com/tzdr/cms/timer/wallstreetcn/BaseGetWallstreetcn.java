package com.tzdr.cms.timer.wallstreetcn;

public class BaseGetWallstreetcn {
	
	public static BaseWallstreetnTask get(String type){
		BaseWallstreetnTask task = null;
		BaseWallstreetcnHandle handle = null;
		switch (type) {
		case "0":
			WallstreetcnTask wallstreetcnTask = new WallstreetcnTask();
			task = wallstreetcnTask;
			WallstreetcnHandle wallstreetcnHandle = new WallstreetcnHandle();
			handle = wallstreetcnHandle;
			wallstreetcnTask.setWallstreetcnHandle(wallstreetcnHandle);
			break;
		case "1":
			WallstreetcnCalendarTask calendarTask = new WallstreetcnCalendarTask();
			task = calendarTask;
			WallstreetcnCalendarHandle calendarHandle = new WallstreetcnCalendarHandle();
			handle = calendarHandle;
			calendarTask.setCalendarHandle(calendarHandle);
			break;
		default:
			break;
		}
		if(task != null){
			task.setBaseWallstreetcnHandle(handle);
		}
		return task;
	}
}
