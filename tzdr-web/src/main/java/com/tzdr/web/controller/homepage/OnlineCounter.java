package com.tzdr.web.controller.homepage;


/**
 * 在线人数
 * <P>title:@OnlineCounter.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年1月29日
 * @version 1.0
 */
public class OnlineCounter {
	private static final OnlineCounter onlineList = new OnlineCounter();
    private int maxUser;
    private int activeUser;
    private OnlineCounter(){
        
    }
    public static OnlineCounter getInstance(){
        return onlineList;
    }
    public void addUser(){
    	activeUser++;
	  if(activeUser>=maxUser){
		  maxUser=activeUser;
	  }
    }
    public void delUser(){
     if(activeUser   >   0)    
    	 activeUser--;  
    }
	public int getMaxUser() {
		return maxUser;
	}
	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}
	public int getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(int activeUser) {
		this.activeUser = activeUser;
	}
   
   


}
