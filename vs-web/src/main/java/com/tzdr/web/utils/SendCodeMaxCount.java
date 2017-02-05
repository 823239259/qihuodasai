package com.tzdr.web.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tzdr.common.utils.IpUtils;

/**
* @Description: 单个手机号码下发短信最大次数
* @ClassName: SendCodeMaxCount
* @author wangpinqun
* @date 2015年06月05日 下午17:40:52
 */
@SuppressWarnings("unchecked")
public class SendCodeMaxCount {
	
	/**
	 * 限制每个手机号码最多发送短信次数
	 */
	public static ConcurrentHashMap<String, SendCodeMaxCountBean> sendSMSCodeMaxCountMap = new ConcurrentHashMap<String, SendCodeMaxCountBean>();

	/**
	* @Description: 添加短信下发信息次数
	* @Title: addSendCodeMaxCountBean
	* @param sendCodeMaxCountBean
	* @param sessionKey
	* @param request
	* @param response
	* @return void    返回类型
	 */
	public static  void addSendCodeMaxCountBean(SendCodeMaxCountBean sendCodeMaxCountBean,String sessionKey,HttpServletRequest request,HttpServletResponse response){

		Object obj =  request.getServletContext().getAttribute(sessionKey);
		ConcurrentHashMap<String, SendCodeMaxCountBean> sendCodeMaxCountMap =  (ConcurrentHashMap<String, SendCodeMaxCountBean>) (obj == null ? null:obj);
		if(sendCodeMaxCountMap == null || sendCodeMaxCountMap.isEmpty()){
			sendCodeMaxCountMap = new ConcurrentHashMap<String, SendCodeMaxCountBean>();
		}
		sendCodeMaxCountMap.put(sendCodeMaxCountBean.getUserName(), sendCodeMaxCountBean);
		request.getServletContext().setAttribute(sessionKey,sendCodeMaxCountMap);
	}
	
	/**
	* @Description: 删除短信下发信息次数
	* @Title: removeSendCodeMaxCountBean
	* @param sendCodeMaxCountBean
	* @param sessionKey
	* @param request
	* @param response
	* @return void    返回类型
	 */
	public static  void removeSendCodeMaxCountBean(SendCodeMaxCountBean sendCodeMaxCountBean,String sessionKey,HttpServletRequest request,HttpServletResponse response){

		Object obj =  request.getServletContext().getAttribute(sessionKey);
		ConcurrentHashMap<String, SendCodeMaxCountBean> sendCodeMaxCountMap =  (ConcurrentHashMap<String, SendCodeMaxCountBean>) (obj == null ? null:obj);
		if(sendCodeMaxCountMap != null && !sendCodeMaxCountMap.isEmpty()){
			sendCodeMaxCountMap.remove(sendCodeMaxCountBean.getUserName());
			if(sendCodeMaxCountMap.size() >= 200){
				for (Map.Entry<String, SendCodeMaxCountBean> sendCodeMap : sendCodeMaxCountMap.entrySet()) {
					SendCodeMaxCountBean sendCodeMaxCountData= sendCodeMap.getValue();
					Date date = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					cal.add(Calendar.DATE, 1);
					if(sendCodeMaxCountData.getValidDate().getTime() / 1000  >=  cal.getTime().getTime() / 1000) continue;
					sendCodeMaxCountMap.remove(sendCodeMaxCountData.getUserName());
				}
			}
		}
	}
	
	/**
	* @Description: 添加限制短信下发信息次数
	* @Title: addSendSMSCodeMaxCountMap
	* @param sendCodeMaxCountBean
	* @param sessionKey
	* @param request
	* @param response
	* @return void    返回类型
	 */
	public static  void addSendSMSCodeMaxCountMap(String userName,HttpServletRequest request,HttpServletResponse response){
		if(!sendSMSCodeMaxCountMap.containsKey(userName)){
			SendCodeMaxCountBean sendSMSCodeMaxCountData = new SendCodeMaxCountBean();
			sendSMSCodeMaxCountData.setSendCodeCount(1);
			sendSMSCodeMaxCountData.setUserName(userName);
			String ip = IpUtils.getIpAddr(request);
			sendSMSCodeMaxCountData.setIp(ip);
			sendSMSCodeMaxCountData.setValidDate(new Date());
			sendSMSCodeMaxCountMap.put(sendSMSCodeMaxCountData.getUserName(), sendSMSCodeMaxCountData);
		}else{
			SendCodeMaxCountBean  sendSMSCodeMaxCountData = sendSMSCodeMaxCountMap.get(userName);
			sendSMSCodeMaxCountData.setEndDate(new Date());
			sendSMSCodeMaxCountData.setSendCodeCount(sendSMSCodeMaxCountData.getSendCodeCount()+1);
			String ip = IpUtils.getIpAddr(request);
			sendSMSCodeMaxCountData.setIp(ip);
			sendSMSCodeMaxCountMap.put(sendSMSCodeMaxCountData.getUserName(), sendSMSCodeMaxCountData);
		}
	}
}
