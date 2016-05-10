package com.tzdr.business.service.securityInfo;

import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;


/**
 * 安全信息service
 * <P>title:@SecurityInfoService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月23日
 * @version 1.0
 */
public interface SecurityInfoService {

	/**
	 * 身份证验证
	 * @param cardNo 身份证号码
	 * @param name 姓名
	 * @return boolean
	 * @date 2014年12月23日
	 * @author zhangjun
	 */
	public boolean vilidateCard(String cardNo,String name);
	
	
	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param module 标题
	 * @param code 验证码
	 * @date 2014年12月23日
	 * @author zhangjun
	 */
	public void sendSms(String mobile,String module,String code,int source);
	
	/**
	 * 发送邮件验证码
	 * @param mobile
	 * @param module
	 * @param code
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	public boolean sendEmail(String name,String email,String code,String module);
	
	
	/**
	 * 通过用户id查用户验证信息
	 * @param userId
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	public UserVerified findByUserId(String userId);
	
	/**
	 * 根据身份证查用户验证信息
	 * @param cardNo
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	public UserVerified findByIdCard(String cardNo);


	/**
	 * 构建email成*号的数据
	 * @param email
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	public String buildEmail(String email);
	
	/**
	 * 将字符串构建成*号
	 * @param val
	 * @param prefixlength
	 * @param mlength
	 * @param suffixlength
	 * @return
	 * @date 2014年12月27日
	 * @author zhangjun
	 */
	public  String buildStr(String val,int prefixlength,int mlength,int suffixlength);


	public void update(UserVerified userverified);


	public WUser getUsesrbyId(String id);


	public void save(UserVerified userverified);


	public WUser getUsesrbyMobile(String mobile);


	public void updatUserMobile(WUser user, String newmobile);


	public WUser getUserByEmail(String email);


	public void updateEmail(WUser user, String email);


	public void updatUserMoneyPwd(String password, WUser user,
			UserVerified userverified);


	public String getBuildPwd(String olddrawmoneypwd, WUser user);


	public void updatUserPwd(WUser wuser, String newpassword);
}
