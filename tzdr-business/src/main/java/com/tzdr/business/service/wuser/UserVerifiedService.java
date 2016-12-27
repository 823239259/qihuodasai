package com.tzdr.business.service.wuser;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;


/**
 * 
 * <p>用户校验</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2014年12月30日下午2:24:04
 */
public interface UserVerifiedService  extends BaseService<UserVerified>{
	
	public void update(UserVerified userVerified);
	
	public UserVerified queryUserVerifiedByUid(String uid);
	
	UserVerified queryUserVerifiedByAliAccount(String aliAccount);
	/**
	 * 微信账号查询
	 * @return
	 */
	UserVerified queryUserVerifiedByWechatAccount(String wechatAccount);
}
