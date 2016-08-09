package com.tzdr.domain.dao.extension;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.ActivityReward;

public interface ActivityRewaryDao extends  BaseJpaDao<ActivityReward, String>,JpaSpecificationExecutor<ActivityReward>{
	@Query("from ActivityReward where uid = ?1 and activity = ?2 and isvalid = ?3 and reward_type=?4")
	ActivityReward doGetActivityLuckDraw(String uid,String activity,Boolean isvalid,String rewardType);
	/**
	 * 查询是否有新的补贴
	 * @param uid
	 * @param activity
	 * @param isvalid
	 * @param rewardType
	 * @param tip
	 * @return
	 */
	@Query("from ActivityReward where uid = ?1 and activity = ?2 and isvalid = ?3 and reward_type=?4 and istip = ?5")
	List<ActivityReward> doGetActivitySubsidy(String uid,String activity,Boolean isvalid,String rewardType,Boolean tip);
	/**
	 * 根据时间查询补贴用户
	 * @param uid
	 * @param activity
	 * @param startTime
	 * @param endTime
	 * @param rewardType
	 * @return
	 */
	@Query("from  ActivityReward  where uid = ?1 and activity = ?2 and create_time between ?3 and ?4 and reward_type = ?5")
	ActivityReward doGetValidationIsReward(String uid , String activity, Long startTime, Long endTime,String rewardType);
	/**
	 * 获取奖励记录
	 * @param id
	 * @return
	 */
	ActivityReward findById(String id);
	/**
	 * 用户查询是否有抽奖
	 */
	@Query("from ActivityReward  uid = ?1 and activity = ?2")
	List<ActivityReward> doGetByUidAndActivity(String uid,String activity);
}
