package com.tzdr.domain.dao.userTrade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
* @Description: TODO(用户配资业务信息管理持久层)
* @ClassName: TradeDao
* @author wangpinqun
* @date 2015年1月4日 下午7:31:20
 */
public interface UserTradeDao extends BaseJpaDao<UserTrade, String>,JpaSpecificationExecutor<UserTrade> {
	
	/**
	* @Description: TODO(根据条件获取配资列表信息)
	* @Title: findByWuserAndStatusOrderByAddtimeDesc
	* @param wuser   用户账户信息
	* @param status  配资状态
	* @return
	* @return List<UserTrade>    返回类型
	 */
	public List<UserTrade> findByWuserAndStatusOrderByAddtimeDesc(WUser wuser,short status);
	
	/**
	* @Description: TODO(根据条件获取用户配资累计盈亏)只有终结了才会有盈亏值
	* @Title: getSumAccrualByWuserAndStatus
	* @param uid  用户编号
	* @param status  配资状态
	* @return Double    返回类型
	 */
	@Query("select sum(accrual) from UserTrade where wuser=?1 and status=?2")
	public Double getSumAccrualByWuserAndStatus(WUser wuser,short status); 

	@Query("from UserTrade where uid=?1")
	public List<UserTrade> getTradeByUid(String uid); 
	
	/**
	* @Description: TODO(根据条件获取用户配资信息)
	* @Title: queryUserTradeVoByWuserAndStatus
	* @param wuser   用户信息
	* @param status  状态
	* @return List<UserTradeVo>    返回类型
	 */
	@Query("select new com.tzdr.domain.vo.UserTradeVo(wuser.id,groupId,account,password,hsBelongBroker,"
			+ "sum(totalLeverMoney),sum(money),sum(warning),sum(open),sum(leverMoney),sum(appendLeverMoney),min(starttime),status,parentAccountNo,assetId) "
			+ "from UserTrade where wuser=?1 and (status=?2 or status=?3) GROUP BY groupId ORDER BY starttime DESC")
	public List<UserTradeVo> queryUserTradeVoByWuserAndStatus(WUser wuser,short status,short status2);
	
	/**
	 * 根据账户查找 方案
	 * @param account
	 * @return
	 */
	public List<UserTrade> findByAccount(String account);
	
	/**
	 * 根据组合id查找 方案
	 * @param groupid
	 * @return
	 */
	public List<UserTrade> findByGroupId(String groupId);
	
	/**
	 * 根据组合id查找 方案
	 * @param groupid
	 * @return
	 */
	public List<UserTrade> findByGroupIdOrderByAddtimeAsc(String groupId);
	

	
	/**
	 * 查找满足扣费的 方案
	 * @param status
	 * @param Starttime 交易开始时间
	 * @return
	 */
	@Query("from UserTrade  where activityType != 5 and status=?1 and starttime<=?2")
	public List<UserTrade> findUserTrades(short status,Long Starttime);
	
	/*@Query(
			value=" SELECT w.group_id,w.account AS ' 恒生账号',w.lever_money AS '配资保证金(元)' "
					+ " ,w.`open` AS '亏损平仓线(元)' ,w.money AS '配资金额'"
					+ " ,w.total_lever_money AS '总操盘资金'"
					+ " FROM w_user_trade w "
					+ " GROUP BY w.group_id",nativeQuery=true)
	public PageRequest queryList();
	
	@Query(
			value=" SELECT count(*)"
					+ " FROM w_user_trade w "
					+ " GROUP BY w.group_id",nativeQuery=true)
	public void queryCount();*/
	public List<UserTrade> findByGroupIdAndWuser(String groupId,WUser wuser);
	
	/**
	 * 
	 * @param type Short
	 * @param status Short
	 * @return List<UserTrade>
	 */
	public List<UserTrade> findByTypeAndStatusAndFeeType(Short type,Short status,Short feeType);
	
	/**
	 * 获取某方案信息
	 * @param programNo 方案编号
	 * @param wuser   用户信息
	 * @return
	 */
	public UserTrade findByProgramNoAndWuser(String programNo,WUser wuser);
	
	
	
	/**
	* @Description: TODO(根据条件获取配资列表信息)
	* @Title: findByWuserAndStatusOrderByAddtimeDesc
	* @param wuser   用户账户信息
	* @param groupId 
	* @return
	* @return List<UserTrade>    返回类型
	 */
	public List<UserTrade> findByWuserAndGroupIdOrderByAddtimeAsc(WUser wuser,String groupId);
	/**
	 * 通过用户ID查找配资方案
	 * @param userID
	 * @return
	 */
	public  List<UserTrade> findByWuserAndActivityTypeIn(WUser user,List<Integer> activityTypeList) ;	
	
	
	/**
	 * 根据活动类型、状态、预计结束时间查询
	 * @param status
	 * @param andActivityType
	 * @param estimateEndtime
	 * @return
	 */
	public List<UserTrade> findByStatusAndActivityTypeAndEstimateEndtime(Short status,int andActivityType,Long estimateEndtime);

	
	/**
	 * 查询即将到期的月月配方案
	 * @param status
	 * @param andActivityType
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("from UserTrade  where status=?1 and activityType=?2 and estimateEndtime>=?3 and estimateEndtime<=?4")
	public List<UserTrade> findSoonExpireMonthTrades(Short status,int andActivityType,Long start,Long end);

	
}
