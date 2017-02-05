package com.tzdr.business.service.wechat;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.WechatUser;

public interface WechatUserService extends BaseService<WechatUser>{
	/**
	 * 根据OPENID查询用户信息
	 * @param openId
	 * @return
	 */
	public List<WechatUser> doGetWechatUserByOpenId(String openId);
}
