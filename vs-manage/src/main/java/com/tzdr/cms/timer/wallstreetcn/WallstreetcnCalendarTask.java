package com.tzdr.cms.timer.wallstreetcn;


public class WallstreetcnCalendarTask extends BaseWallstreetnTask{
	private WallstreetcnCalendarHandle calendarHandle;
	public WallstreetcnCalendarHandle getCalendarHandle() {
		return calendarHandle;
	}
	public void setCalendarHandle(WallstreetcnCalendarHandle calendarHandle) {
		this.calendarHandle = calendarHandle;
	}
	@Override
	public void run() {
		calendarHandle.getWallstreetcnCalendar(getWallstreetn());
	}
}
