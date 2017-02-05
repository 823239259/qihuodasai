package com.tzdr.business.service.extension;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.LuckDraw;

public interface LuckDrawService extends BaseService<LuckDraw>{
	/**
	 * 查询用户抽奖活动
	 * @param uid
	 * @param activity
	 * @return
	 */
	public LuckDraw findByUid(String uid,String activity);
}
