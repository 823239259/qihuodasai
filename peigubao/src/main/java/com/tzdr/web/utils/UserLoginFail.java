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
public class UserLoginFail {

	/**
	* @Description: TODO(添加或修改登录失败信息)
	* @Title: addUserLoginFailBean
	* @param userLoginFailBean
	* @param request
	* @param response
	* @return void    返回类型
	 */
	public static  void addUserLoginFailBean(UserLoginFailBean userLoginFailBean,HttpServletRequest request,HttpServletResponse response){

		Object obj =  request.getServletContext().getAttribute(Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY);
		ConcurrentHashMap<String, UserLoginFailBean> userLoginFailMap =  (ConcurrentHashMap<String, UserLoginFailBean>) (obj == null ? null:obj);
		if(userLoginFailMap == null || userLoginFailMap.isEmpty()){
			userLoginFailMap = new ConcurrentHashMap<String, UserLoginFailBean>();
		}
		userLoginFailMap.put(userLoginFailBean.getUserName(), userLoginFailBean);
		request.getServletContext().setAttribute(Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY,userLoginFailMap);
	}
	
	/**
	* @Description: TODO(删除登录失败(包含无效)信息)
	* @Title: removeUserLoginFailBean
	* @param userLoginFailBean
	* @param request
	* @param response
	* @return void    返回类型
	 */
	public static  void removeUserLoginFailBean(UserLoginFailBean userLoginFailBean,HttpServletRequest request,HttpServletResponse response){

		Object obj =  request.getServletContext().getAttribute(Constants.LOGIN_FAIL_MAX_COUNT_SESSION_KEY);
		ConcurrentHashMap<String, UserLoginFailBean> userLoginFailMap =  (ConcurrentHashMap<String, UserLoginFailBean>) (obj == null ? null:obj);
		if(userLoginFailMap != null && !userLoginFailMap.isEmpty()){
			userLoginFailMap.remove(userLoginFailBean.getUserName());
			if(userLoginFailMap.size() >= 200){
				for (Map.Entry<String, UserLoginFailBean> loginFailMap : userLoginFailMap.entrySet()) {
					UserLoginFailBean userLoginFailData= loginFailMap.getValue();
					Date date = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					cal.add(Calendar.DATE, 1);
					if(userLoginFailData.getValidDate().getTime() / 1000  >=  cal.getTime().getTime() / 1000) continue;
					userLoginFailMap.remove(userLoginFailData.getUserName());
				}
			}
		}
	}
}
