package com.tzdr.business.service.wechat.imp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.wechat.WechatUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.wechat.WechatUserDao;
import com.tzdr.domain.web.entity.WechatUser;

@Service("WechatUserService")
@Transactional
public class WechatUserServiceImp extends BaseServiceImpl<WechatUser, WechatUserDao> implements WechatUserService{

	@Override
	public List<WechatUser> doGetWechatUserByOpenId(String openId) {
		return getEntityDao().findWechatUserByOpenId(openId);
	}
	
}
