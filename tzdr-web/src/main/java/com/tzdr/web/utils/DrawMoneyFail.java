package com.tzdr.web.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tzdr.web.constants.Constants;

/**
* @Description: TODO(登录失败信息管理类)
* @ClassName: UserLoginFail
* @author wangpinqun
* @date 2015年2月9日 下午6:05:52
 */
@SuppressWarnings("unchecked")
public class DrawMoneyFail {

	
	/**
	 * 加入取现失败错误次数记录
	 * @param drawFailBean
	 * @param request
	 * @param response
	 */
	public static  void addDrawFailBean(DrawMoneyFailBean drawFailBean,HttpServletRequest request,HttpServletResponse response){
		Object obj =  request.getServletContext().getAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY);
		ConcurrentHashMap<String, DrawMoneyFailBean> userLoginFailMap =  (ConcurrentHashMap<String, DrawMoneyFailBean>) (obj == null ? null:obj);
		if(userLoginFailMap == null || userLoginFailMap.isEmpty()){
			userLoginFailMap = new ConcurrentHashMap<String, DrawMoneyFailBean>();
		}
		userLoginFailMap.put(drawFailBean.getUserName(), drawFailBean);
		request.getServletContext().setAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY,userLoginFailMap);
	}
	
	/**
	 * 删除取现错误次数记录
	 * @param drawFailBean
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public static  void removeDrawFailBean(DrawMoneyFailBean drawFailBean,HttpServletRequest request,HttpServletResponse response) {

		Object obj =  request.getServletContext().getAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY);
		ConcurrentHashMap<String, DrawMoneyFailBean> drawFailMap =  (ConcurrentHashMap<String, DrawMoneyFailBean>) (obj == null ? null:obj);
		if(drawFailMap != null && !drawFailMap.isEmpty()){
			drawFailMap.remove(drawFailBean.getUserName());
			if(drawFailMap.size() >= 200){
				for (Map.Entry<String, DrawMoneyFailBean> loginFailMap : drawFailMap.entrySet()) {
					DrawMoneyFailBean failData= loginFailMap.getValue();
					Date date = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					cal.add(Calendar.DATE, 1);
					if(failData.getValidDate().getTime() / 1000  >=  cal.getTime().getTime() / 1000) continue;
					drawFailMap.remove(drawFailBean.getUserName());
				}
			}
		}
	
	}
}
