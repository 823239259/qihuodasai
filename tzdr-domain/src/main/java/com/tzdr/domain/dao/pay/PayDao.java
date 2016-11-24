package com.tzdr.domain.dao.pay;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.RechargeList;

/**
 * 充值dao
 * <P>title:@SecurityInfoDao.java</p>																								
 * <P>Description：</p>userId
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月24日
 * @version 1.0
 */
public interface PayDao extends BaseJpaDao<RechargeList, String> {

	/**
	 * 根据用户id查询充值记录
	 * @param uid 用户id
	 * @return
	 * @date 2014年12月31日
	 * @author zhangjun
	 */
	RechargeList findByUid(String uid);

	/**
	 * 根据交易号获取充值记录表数据
	 * @param tradeNo
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	RechargeList findByTradeNo(String tradeNo);
	
	/**
	 * 根据订单号获取充值记录表数据
	 * @param tradeNo
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	RechargeList findByNo(String orderId);
	
	/**
	 * 根据交易号获取充值记录表数据
	 * @param tradeNo
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	@Query("from RechargeList where tradeNo = ?1")
	List<RechargeList> findByTradeNoList(String tradeNo);
}
