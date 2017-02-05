package com.tzdr.business.service.securitycode;

import com.tzdr.domain.web.entity.SecurityCode;

/**
* @Description: TODO(验证码业务信息管理业务接口层)
* @ClassName: SecurityCodeService
* @author wangpinqun
* @date 2014年12月25日 下午2:48:37
 */
public interface SecurityCodeService {

	/**
	* @Description: TODO(根据验证码信息，删除验证码信息)
	* @Title: delSecurityCode
	* @param securityCode   删除验证码信息
	* @return void    返回类型
	 */
	public void delSecurityCode(SecurityCode securityCode);
	
	/**
	* @Description: TODO(保存手机号码验证码信息)
	* @Title: saveSecurityCode
	* @param securityCode     验证码信息
	* @param mobile     手机号码
	* @return void    返回类型
	 */
	public void saveSecurityCode(SecurityCode securityCode,String mobile);
	
	/**
	* @Description: TODO(根据手机号码获取验证码信息)
	* @Title: getSecurityCodeByMobile
	* @param mobile  手机号码
	* @return SecurityCode    返回类型
	 */
	public SecurityCode getSecurityCodeByMobile(String mobile); 
}
