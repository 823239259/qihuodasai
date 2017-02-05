package com.tzdr.domain.dao.securitycode;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.SecurityCode;

/**
* @Description: TODO(验证码业务信息管理持久层)
* @ClassName: SecurityCodeDao
* @author wangpinqun
* @date 2014年12月25日 下午2:58:10
 */
public interface SecurityCodeDao extends BaseJpaDao<SecurityCode, Long> {

	/**
	* @Description: TODO(根据手机号码获取手机验证码信息)
	* @Title: findByMobile
	* @param mobile         手机号码
	* @return SecurityCode    返回类型
	 */
	public SecurityCode findByMobile(String mobile); 
}
