package com.tzdr.domain.dao.user;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.cms.entity.user.UserExtend;

/**
 * @ClassName UserExtendDao
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年6月15日
 */
public interface UserExtendDao extends BaseJpaDao<UserExtend, Long> {
	List<UserExtend> findByUid(String uid);
	
	List<UserExtend> findByUidAndDeletedFalse(String uid);
	
	/**
	 * 根据活动类型和用户id查询 且未删除
	 * @MethodName findByUidAndActivityTypeAndDeletedFalse
	 * @author L.Y
	 * @date 2015年6月17日
	 * @param uid 用户id
	 * @param activityType 活动类型
	 * @return
	 */
	List<UserExtend> findByUidAndActivityTypeAndDeletedFalse(String uid, Integer activityType);
	
	/**
	 * 根据活动类型和用户id & 系统用户id 查询 且未删除
	 * @MethodName findByUidAndSysUserAndActivityTypeAndDeletedFalse
	 * @author L.Y
	 * @date 2015年6月17日
	 * @param uid 用户id
	 * @param sysUser 系统用户
	 * @param activityType 活动类型
	 * @return
	 */
	List<UserExtend> findByUidAndSysUserAndActivityTypeAndDeletedFalse(String uid, User sysUser, Integer activityType);
	
	/**
	 * 根据活动类型 用户id查询
	 * @MethodName findByUidAndActivityType
	 * @author L.Y
	 * @date 2015年6月17日
	 * @param uid 用户id
	 * @param activityType 活动类型
	 * @return
	 */
	UserExtend findByUidAndActivityType(String uid, Integer activityType);
}