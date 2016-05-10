package com.tzdr.business.service.securitycode.impl;

import org.springframework.stereotype.Service;

import jodd.util.StringUtil;

import com.tzdr.business.service.securitycode.SecurityCodeService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.securitycode.SecurityCodeDao;
import com.tzdr.domain.web.entity.SecurityCode;

/**
* @Description: TODO(验证码业务信息管理业务实现层)
* @ClassName: SecurityCodeServiceImpl
* @author wangpinqun
* @date 2014年12月25日 下午2:56:26
 */
@Service("securityCodeService")
public class SecurityCodeServiceImpl extends BaseServiceImpl<SecurityCode, SecurityCodeDao> implements SecurityCodeService {

	@Override
	public void delSecurityCode(SecurityCode securityCode) {
		super.remove(securityCode);
	}

	@Override
	public void saveSecurityCode(SecurityCode securityCode,String mobile) {
		SecurityCode oldSecurityCode = this.getSecurityCodeByMobile(mobile); //获取老的验证码信息
		if(oldSecurityCode != null){
			super.remove(oldSecurityCode);  //删除老的验证码信息
		}
		super.save(securityCode);
	}

	@Override
	public SecurityCode getSecurityCodeByMobile(String mobile) {
		if(StringUtil.isBlank(mobile)){
			return null;
		}
		return getEntityDao().findByMobile(mobile);
	}
}
