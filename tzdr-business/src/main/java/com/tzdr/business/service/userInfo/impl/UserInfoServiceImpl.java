package com.tzdr.business.service.userInfo.impl;

import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jodd.util.StringUtil;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tzdr.business.service.userInfo.UserInfoService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.HttpClientUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.domain.dao.userInfo.UserInfoDao;
import com.tzdr.domain.web.entity.UserInfo;
import com.tzdr.domain.web.entity.WUser;

/**
* @Description: TODO(用户基本信息业务管理业务实现层)
* @ClassName: UserInfoServiceImpl
* @author wangpinqun
* @date 2014年12月27日 下午5:15:30
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, UserInfoDao> implements UserInfoService {
	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	@Autowired
	private WUserService wUserService;
	
	@Override
	public UserInfo getUserInfoByUId(String uId) {
		
		if(StringUtil.isBlank(uId)){
			return null;
		}
		return getEntityDao().findByWuser(wUserService.getUser(uId));
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		super.update(userInfo);
		this.saveInfoByUrl(userInfo,null);
	}

	@Override
	public void saveUserInfo(UserInfo userInfo) {
		save(userInfo);
		this.saveInfoByUrl(userInfo,null);
	}
	
	public boolean saveInfoByUrl(UserInfo userInfo,WUser wuser){
		boolean reuslt = false;
		if(!Boolean.valueOf(ConfUtil.getContext("lg.isOpen"))){
			return reuslt;
		}
		try {
			String url=ConfUtil.getContext("userInfo.url");
			String key=ConfUtil.getContext("userinfo.key");
			StringBuffer sb=new StringBuffer("{");
			sb.append("\"sourceCode\":\"5\",");
			sb.append("\"key\":\""+key+"\",");
			sb.append("\"uid\":\""+(userInfo == null ? wuser.getId() : userInfo.getWuser().getId())+"\",");
			if(userInfo != null){
				if(StringUtil.isNotBlank(userInfo.getTname())){
					sb.append("\"uname\":\""+URLEncoder.encode(userInfo.getTname(), "utf-8")+"\",");
				}else{
					sb.append("\"uname\":\""+"\",");
				}
				sb.append("\"marriage\":\""+userInfo.getMarriage()+"\",");
				sb.append("\"education\":\""+userInfo.getEducation()+"\",");
				sb.append("\"province\":\""+userInfo.getProvince()+"\",");
				sb.append("\"city\":\""+userInfo.getCity()+"\",");
				if(StringUtil.isNotBlank(userInfo.getAddress())){
					sb.append("\"address\":\""+URLEncoder.encode(userInfo.getAddress(), "utf-8")+"\",");
				}else{
					sb.append("\"address\":\""+"\",");
				}
				sb.append("\"industry\":\""+userInfo.getIndustry()+"\",");
				sb.append("\"position\":\""+userInfo.getPosition()+"\"");
			}else{
				if(StringUtil.isNotBlank(wuser.getMobile())){
					String userMobile = null;  
					String mobile = wuser.getMobile();
					userMobile = StringCodeUtils.buildMobile(mobile);
					sb.append("\"mobile\":\""+userMobile+"\",");
				}
				if(StringUtil.isNotBlank(wuser.getEmail())){
					String userEmail = null;  
					String email = wuser.getEmail();
					userEmail = StringCodeUtils.buildEmail(email);
					sb.append("\"email\":\""+userEmail+"\"");
				}
			}
			sb.append("}");
			String infourl=url+"?key="+sb.toString();
			logger.info("infourl:"+infourl);
			String returnStr = HttpClientUtils.httpRequest2Json(infourl, "");
			 Object obj = JSONObject.parse(returnStr);
			 JSONObject json =  obj == null ? null : (JSONObject)obj;
			 String message=json.getString("message");
			 Boolean issuccess=json.getBoolean("success");
			 logger.info("json:"+json.toString());
			 if(!issuccess){
				 EmailExceptionHandler.getInstance().HandleExceptionWithData(new Exception(), "调用用户修改接口失败", this.getClass().getName()+":moreSuccess", infourl+"message:"+message);
			 }
			 reuslt = issuccess.booleanValue();
		} catch (Exception e) {
			try {
				this.HandleException(e, "修改用户信息到论坛接口异常", "saveInfoByUrl");
			} catch (Exception ex) {
				logger.error("修改用户信息到论坛接口异常", ex.getMessage());
			}
		}
		return reuslt;
	}
	
	/**
	 * @Description: 异常
	 * @Title: HandleException
	 * @param e
	 * @param detailed  明细
	 * @param method    方法
	 * @return void    返回类型
	 */
	private void HandleException(Exception e, String detailed, String method) {
		// 发送邮件
		String devEmail = ConfUtil.getContext("mail.to.dev");
		List<String> pramas = Lists.newArrayList();
		String methodName = this.getClass().getName() + "." + method;
		String exception = Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(detailed);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,
					"UserInfoServiceExceptionEmail", pramas);
		} catch (Exception ex) {
			logger.error("email:", ex.getMessage());
		}
		logger.error(methodName + ",error::{}", exception);
	}
}
