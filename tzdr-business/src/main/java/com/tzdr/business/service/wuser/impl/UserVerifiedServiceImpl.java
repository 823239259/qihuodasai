package com.tzdr.business.service.wuser.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.wuser.UserVerifiedDao;
import com.tzdr.domain.web.entity.UserVerified;

/**
 * 
 * <p>用户校验Service</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2014年12月30日下午2:29:53
 */
@Service("userVerifiedService")
public class UserVerifiedServiceImpl extends BaseServiceImpl<UserVerified, UserVerifiedDao> 
     implements UserVerifiedService {

	@Override
	public UserVerified queryUserVerifiedByUid(String uid) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("wuser.id", uid);
		List<UserVerified> userVerifieds = this.getEntityDao().queryBySimple(equals, null,null);
		if (userVerifieds != null && userVerifieds.size() > 0) {
			return userVerifieds.get(0);
		}
		return null;
	}

	/**
	 * 根据支付宝账户查询 userVerified
	 */
	@Override
	public UserVerified queryUserVerifiedByAliAccount(String aliAccount) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("alipayAccount", aliAccount);
		List<UserVerified> userVerifieds = this.getEntityDao().queryBySimple(equals, null, null);
		if (!CollectionUtils.isEmpty(userVerifieds) && 1 == userVerifieds.size()) {
			return userVerifieds.get(0);
		}
		return null;
	}
	/**
	 * 根据微信账户查询 userVerified
	 */
	@Override
	public UserVerified queryUserVerifiedByWechatAccount(String aliAccount) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("wxAccount", aliAccount);
		List<UserVerified> userVerifieds = this.getEntityDao().queryBySimple(equals, null, null);
		if (!CollectionUtils.isEmpty(userVerifieds) && 1 == userVerifieds.size()) {
			return userVerifieds.get(0);
		}
		return null;
	}
}
