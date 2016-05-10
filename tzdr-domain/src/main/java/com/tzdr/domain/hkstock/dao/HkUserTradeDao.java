package com.tzdr.domain.hkstock.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 港股操盘方案
 * @author zhouchen
 * 2015年10月16日 上午11:18:57
 */
public interface HkUserTradeDao extends BaseJpaDao<HkUserTrade, String> {
	
	/**
	 * 查找需要扣费的方案
	 * 即操盘中、开户中，开始时间小于当前时间的方案
	 * @return
	 */
	@Query("from HkUserTrade  where starttime<=?1 and status in ?2")
	public List<HkUserTrade> findDuductionTrades(Long startTime,List<Short> status);

	/**
	 * 根据组合号查找 方案
	 * @param groupid
	 * @return
	 */
	public List<HkUserTrade> findByGroupIdOrderByAddtimeAsc(String groupId);
	
	/**
	 * 根据组合id查找 方案
	 * @param groupid
	 * @return
	 */
	public List<HkUserTrade> findByGroupId(String groupId);
	
	/**
	* @Description: 根据条件获取配资列表信息
	* @Title: findByWuserAndStatusOrderByAddtimeDesc
	* @param wuser   用户账户信息
	* @param groupId 
	* @return
	* @return List<HkUserTrade>    返回类型
	 */
	public List<HkUserTrade> findByWuserAndGroupIdOrderByAddtimeAsc(WUser wuser,String groupId);
	
	
	/**
	 * 已账户为条件，获取记录数
	 * @param accountNo
	 * @return
	 */
	@Query(value="SELECT COUNT(0) FROM hk_user_trade WHERE account_no=:accountNo", nativeQuery=true)
	public int getCount(@Param("accountNo")String accountNo);  
	

}
