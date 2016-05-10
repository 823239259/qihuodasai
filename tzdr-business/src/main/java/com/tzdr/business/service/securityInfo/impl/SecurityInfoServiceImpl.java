package com.tzdr.business.service.securityInfo.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.service.api.yjfinance.IdentityCard;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.thread.SMSPgbSenderThread;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.business.service.userInfo.UserInfoService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.dao.securityInfo.SecurityInfoDao;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;


/**
 * 
 * <P>title:@SecurityInfoService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月23日
 * @version 1.0
 */
@Service("securityInfoService")
@Transactional
public class SecurityInfoServiceImpl  extends BaseServiceImpl<UserVerified,SecurityInfoDao> implements SecurityInfoService  {
	public static final Logger log = LoggerFactory.getLogger(SecurityInfoServiceImpl.class);

	@Autowired
	private WUserService wUserService;
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public boolean vilidateCard(String cardNo, String name) {
		boolean flag=false;
		try {
			 flag=IdentityCard.getInstance().idSimpleCheckByJson(cardNo, name);
		} catch (AxisFault e) {
			log.error("身份验证错误"+e.getMessage());
		} catch (MalformedURLException e) {
			log.error("身份验证错误"+e.getMessage());
		}
		return flag;
	}

	/**
	 * 检查手机号码
	 * @param mobile
	 * @return
	 * @date 2014年12月27日
	 * @author zhangjun
	 */
	public WUser getUsesrbyMobile(String mobile){
		WUser user=this.wUserService.getWUserByMobile(mobile);
		return user;
	}
	
	/**
	 * 根据用户id查询用户
	 * @param userId
	 * @return
	 * @date 2015年1月5日
	 * @author zhangjun
	 */
	public WUser getUsesrbyId(String userId){
		WUser user=this.wUserService.getUser(userId);
		return user;
	}
	/**
	 * 更新手机号
	 * @param newmobile 
	 * @date 2014年12月27日
	 * @author zhangjun
	 */
	public void updatUserMobile(WUser wUser,String newmobile) {
		WUser user=wUserService.getUser(wUser.getId());
		user.setMobile(newmobile);
		wUserService.updateUser(user);
		userInfoService.saveInfoByUrl(null, user);
	}	
	/*
	 * 发送短信验证码
	 * @see com.tzdr.business.service.securityInfo.SecurityInfoService#sendSms(java.lang.String, java.lang.String, java.lang.String)
	 */

	public void sendSms(String mobile,String module,String code,int source) {
		Map<String,String> map= new HashMap<String,String>();
		map.put("module", module);
		map.put("code", code);
		if (Constant.Source.PGB==source){
			new SMSPgbSenderThread(mobile,
					"ihuyi.verification.code.template", map).start();
			return ;
		}
		new SMSSenderThread(mobile,
				"ihuyi.verification.code.template", map).start();

		//SMSSender.getInstance().sendByTemplate(mobile, "ihuyi.verification.code.template", map);
		
	}
	/*
	 * 发送邮件验证码
	 * @see com.tzdr.business.service.securityInfo.SecurityInfoService#sendEmail(java.lang.String, java.lang.String, java.lang.String)
	 */

	public boolean sendEmail(String name,String email, String code, String module) {
		boolean flag=false;
		List<String> pramas = new ArrayList<String>();
		name=name==null?"用户":name;
		pramas.add(name);
		pramas.add(code);
		pramas.add(module);
		try {
			if (StringUtils.equals(module,"配股宝")){
				EmailUtils.getInstance().sendMailTemp(email,"配股宝邮箱验证码",null,"pgb-secemail",null, pramas,null);
			}else{
				EmailUtils.getInstance().sendMailTemp(email,"投资达人邮箱验证码",null,"secemail",null, pramas,null);
			}
			//EmailUtils.getInstance().sendMailTemp(email, "邮箱验证码",pramas);
			flag=true;
		} catch (Exception e) {
			log.error("发送邮件失败"+email+e.getMessage());
		}
		return flag;
	}

	/**
	 * 更新邮箱
	 * @param user
	 * @param email
	 * @date 2014年12月27日
	 * @author zhangjun
	 */
	public void updateEmail(WUser user, String email) {
		user=this.wUserService.getUser(user.getId());
		UserVerified  veruser=this.findByUserId(user.getId());
		veruser.setEmailActive((short)1);
		user.setEmail(email);
		this.update(veruser);
		
		this.wUserService.updateUser(user);
		userInfoService.saveInfoByUrl(null, user);
	}

	/**
	 * 根据邮箱获取用户
	 * @param email
	 * @return
	 * @date 2014年12月27日
	 * @author zhangjun
	 */
	public WUser getUserByEmail(String email) {
		
		return wUserService.getUserByEmail(email);
	}
	/**
	 * 根据用户id获取用户安全信息
	 * @param userId
	 * @return
	 * @date 2014年12月27日
	 * @author zhangjun
	 */
	public UserVerified findByUserId(String userId) {
		WUser user=this.wUserService.getUser(userId);
		return getEntityDao().findByWuser(user);
	}
	

	/**
	 * 根据身份证查询用户安全信息
	 * @param cardNo
	 * @return
	 * @date 2014年12月29日
	 * @author zhangjun
	 */
	public UserVerified findByIdCard(String cardNo) {
		return getEntityDao().findByIdcard(cardNo);
	}


	/**
	 * 新修改用户的密码
	 * @param wuser
	 * @param newpassword
	 * @date 2014年12月29日
	 * @author zhangjun
	 */
	public void updatUserPwd(WUser wuser, String newpassword) {
		String pwd=passwordService.encryptPassword(newpassword, wuser.getLoginSalt());
		wuser.setPassword(pwd);
		this.wUserService.updateUser(wuser);
		
	}

	
	/**
	 * 生成加密密码
	 * @param password
	 * @param wuser
	 * @return
	 * @date 2014年12月29日
	 * @author zhangjun
	 */
	public String getBuildPwd(String password, WUser wuser) {
		String pwd=passwordService.encryptPassword(password, wuser.getLoginSalt());
		return pwd;
	}

	/**
	 * 密码加密
	 * @param newpassword 密码
	 * @param wuser 用户
	 * @param userverified 用户安全信息
	 * @return
	 * @date 2014年12月29日
	 * @author zhangjun
	 */
	public void updatUserMoneyPwd(String moneypassword,WUser wuser,UserVerified userverified){
		String moneypwd=this.getBuildPwd(moneypassword, wuser);
		userverified.setDrawMoneyPwd(moneypwd);
		getEntityDao().update(userverified);;
		
	}
	/**
	 * 将字符串构建成*号
	 * @param val
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */

	public   String buildStr(String val,int prefixlength,int mlength,int suffixlength){
		String prefix=val.substring(0,prefixlength);
		String str="";
		for(int i=0;i<val.length()-mlength;i++){
			str+="*";
		}
		String suffix=val.substring(val.length()-suffixlength,val.length());
		String card=prefix+str+suffix;
		return card;
	}

	/**
	 * 邮箱用星号显示
	 * @param email
	 * @return
	 * @date 2014年12月29日
	 * @author zhangjun
	 */
	public String buildEmail(String email) {
		String prefix=email.substring(0,3);
		String str="";
		if(email.length()>3){
			for(int i=0;i<email.lastIndexOf("@")-3;i++){
				str+="*";
			}
		}
		if(str.length()>8){
			str="********";
		}
		String suffix=email.substring(email.lastIndexOf("@"),email.length());
		return prefix+str+suffix;
	}

	
	/**
	* @Description: TODO(保存安全验证信息)
	* @Title: saveUserVerified
	* @param userVerified   安全验证信息
	 */
	public void saveUserVerified(UserVerified userVerified){
		super.save(userVerified);
	}

	public UserVerified getUserVerified(String id) {
		
		return this.getEntityDao().get(id);
	}


}
