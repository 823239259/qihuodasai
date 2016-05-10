package com.tzdr.domain.dao.withdrawal;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.DrawList;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月3日 上午10:04:03
 * 类说明
 */
public interface WithdrawalDao extends BaseJpaDao<DrawList, String> {

	/**
	 * 根据订单号查询数据
	 * @param orderId
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	DrawList findByNo(String orderId);

	/**
	 * 查询当天的次数
	 * @param userid 用户id
	 * @param nowdate 当天时间
	 * @return
	 * @date 2015年1月13日
	 * @author zhangjun
	 */
	@Query("from DrawList where user.id=?1 and addtime>=?2 and addtime<?3")
	List<DrawList> findDrawCount(String userid, long mintime,long maxtime);

	@Query("from DrawList where user.id=?1 and card=?2 and status=?3")
	List<DrawList> findDrawBycard(String userId,String card,short status);

}
