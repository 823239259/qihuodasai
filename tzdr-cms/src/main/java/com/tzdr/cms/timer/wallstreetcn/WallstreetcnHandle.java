package com.tzdr.cms.timer.wallstreetcn;

import com.tzdr.cms.utils.HttpUrl;

public class WallstreetcnHandle {
	/**
	 * 获取实时行情数据
	 * @return
	 */
	public static String getWallstreetcn(Wallstreetn wallstreetn){
		String method = wallstreetn.getMethod();
		String result = "";
		if(method != null && method.length() > 0){
			if(method.equals("GET")){
				result = WallstreetcnHandle.httpGetWalls(wallstreetn);
			}else if(method.equals("POST")){
				result = WallstreetcnHandle.httpPostWalls(wallstreetn);
			}
		}else{
			throw new RuntimeException("请求方式错误");
		}
		return result;
	}
	public static String httpGetWalls(Wallstreetn wallstreetn){
		return HttpUrl.httpGet(wallstreetn.getUrl(), wallstreetn.getParam());
	}
	public static String httpPostWalls(Wallstreetn wallstreetn){
		return HttpUrl.httpPost(wallstreetn.getUrl(), wallstreetn.getParam());
	}
}
