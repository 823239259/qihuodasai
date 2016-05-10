package com.tzdr.domain.dao.future;


import java.util.List;

import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.UserTrade;
import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.vo.future.FSimpleCouponManageVo;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
/**
 * 期货优惠券
 * 类说明
 * @author  zhaozhao
 * @date    2016年3月1日下午1:47:15
 * @version 1.0
 */
public interface FSimpleCouponDao extends BaseJpaDao<FSimpleCoupon, String>{
	@Query(value="select count(*) from FSimpleCoupon where name=?1")		
	public Integer checkName(String name);
	
	
	public List<FSimpleCoupon> findByName(String name);
	
	@Query(value="from FSimpleCoupon where status=1 group by name")
	public List<FSimpleCoupon> queryCouponToGive();
	
	/**
	* @Title: findIdAndUserId    
	* @Description: 根据条件获取用户某张优惠券信息 
	* @param id   优惠券编号
	* @param userId  用户编号
	* @return
	 */
	public List<FSimpleCoupon> findByIdAndUserId(String id,String userId);

	/**
	 * @Title: findByUserIdAndName
	 * @Description: 根据条件获取用户是否拥有该活动优惠卷信息
	 * @param userId 用户id
	 * @param name 优惠卷名称
	 * @return
	 */
	@Query(value = "from FSimpleCoupon where userId = ?1 and name like ?2")
	public List<FSimpleCoupon> findByUserIdAndName(String userId,String name);



	/**
	 * 判断用户是否有过操盘期货的记录
	 * @param uid
	 * @return
	 */
	@Query(value = "from FSimpleFtseUserTrade f where f.uid=?1 and f.stateType = ?2")
	public List<FSimpleFtseUserTrade> findByUidAndStateType(String uid,Integer stateType);

	/**
	 * 判断用户是否持有操盘记录
	 * @param uid
	 * @param status
	 * @return
	 */
	@Query(value = "from UserTrade u where u.wuser.id = ?1 and u.status =?2  ")
	public List<UserTrade> findByWUserAndStatus(String uid,short status);

	/**
	 * 判断用户是否有过操盘期货的记录
	 * @param uid
	 * @return
	 */
	@Query(value = "from FSimpleFtseUserTrade f where f.uid=?1 ")
	public List<FSimpleFtseUserTrade> findByUidAndStateType(String uid);

	/**
	 * 判断用户是否持有操盘记录
	 * @param uid
	 * @return
	 */
	@Query(value = "from UserTrade u where u.wuser.id = ?1 ")
	public List<UserTrade> findByWUserAndStatus(String uid);

	
	/**
	 * 查询未发放的优惠券
	 */
	public List<FSimpleCoupon> findByStatusAndName(short status,String name);
}
