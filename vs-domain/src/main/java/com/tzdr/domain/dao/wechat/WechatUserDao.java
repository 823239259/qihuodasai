package com.tzdr.domain.dao.wechat;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.WechatUser;

public interface WechatUserDao extends BaseJpaDao<WechatUser, String>{
	/**
	 * 根据OPENid获取用户信息
	 * @param openId
	 * @return
	 */
	@Query("from WechatUser where wechatOpenId = ?1")
	public List<WechatUser> findWechatUserByOpenId(String openId);
}
