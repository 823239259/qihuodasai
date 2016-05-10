package com.tzdr.domain.dao.activity;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.activity.ActivityKudo;

/**
 * 
 * @WangPinQun
 * 2016年01月08日
 */
public interface ActivityKudoDao extends BaseJpaDao<ActivityKudo, String>{
	
	/**
	 * 抽取【外盘开箱壕礼奖品】
	 * @param time
	 * @param kudoType
	 * @return
	 */
	@Query("from ActivityKudo  where createTime>=:beginTime and createTime<=:endTime and activityType=2 and deleted=false and (userId IS NULL OR userId='') and kudoType NOT IN(:kudoTypes) ORDER BY RAND()  ")
	public List<ActivityKudo> findActivityKudoOfWeb(@Param("beginTime")long beginTime,@Param("endTime")long endTime,@Param("kudoTypes")List<Integer> kudoTypes);



	/**
	 * 获取用户领取到的实物奖品
	 * @param activityType
	 * @param userId
	 * @return
	 */
	@Query("from ActivityKudo where activityType=:activityType and kudoType=1  and deleted=false and userId =:userId ")
	public List<ActivityKudo> findActivityKudoSW(@Param("activityType")int activityType,@Param("userId")String userId);
	
	
	/**
	 * 获取微信奖品
	 * @param activityType
	 * @param userId
	 * @return
	 */
	@Query("from ActivityKudo where activityType=:activityType and kudoType !=:kudoType and kudoStatus=0 and deleted=false and userId is null ORDER BY RAND()")
	public List<ActivityKudo> findActivityKudoOfWeixin(@Param("activityType")int activityType,@Param("kudoType")int  kudoType);
}
