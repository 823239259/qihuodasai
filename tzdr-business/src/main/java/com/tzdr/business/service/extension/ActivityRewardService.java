package com.tzdr.business.service.extension;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.ActivityReward;

public interface ActivityRewardService extends BaseService<ActivityReward>{
	/**
	 * 新增抽奖/奖励
	 */
	public void doSave(ActivityReward activityReward);
	/**
	 * 活动奖励执行
	 * @param startTime
	 * @param endTime
	 */
	public void doSaveActivityReward(Long startTime,Long endTime);
	/**
	 * 验证用户抽奖/奖励权限
	 * @param uid
	 * @param activity
	 * @param isvalid
	 * @param rewardType
	 * @return
	 */
	public ActivityReward findByUidAndActivity(String uid,String activity,Boolean isvalid,String rewardType);
	/**
	 * 查询是否有新的补贴
	 * @param uid
	 * @param activity
	 * @param isvalid
	 * @param rewardType
	 * @param tip
	 * @return
	 */
	public List<ActivityReward> doGetActivitySubsidy(String uid, String activity,Boolean isvalid,String rewardType ,Boolean istip);
	/**
	 * 验证用户是否已增加到补贴列表中
	 * @param uid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ActivityReward doGetValidationIsReward(String uid , String activity, Long startTime, Long endTime,String rewardType);
	/**
	 * 修改奖励
	 * @return
	 */
	public boolean doUpdateReward(ActivityReward activityReward);
	/**
	 * 获取奖励记录
	 * @param id
	 * @return
	 */
	public ActivityReward doGetById(String id);
}
