package com.tzdr.domain.dao.activity;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.activity.ActivityUser;

/**
 * 参赛活动用户信息Dao
 * @WangPinQun
 * 2016年01月08日
 */
public interface ActivityUserDao extends BaseJpaDao<ActivityUser, String>{

	/**
	 * 获取用户参赛信息
	 * @param userId  用户编号
	 * @param activityType  活动类型：1-微信抽奖；2-web抽奖
	 * @return
	 */
	public List<ActivityUser> findByUserIdAndActivityTypeAndDeletedFalse(String userId,Integer activityType);
}
