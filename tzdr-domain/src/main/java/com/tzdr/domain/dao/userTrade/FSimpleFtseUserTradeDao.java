package com.tzdr.domain.dao.userTrade;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see 富时A50抢险版
 * FSimpleFtseUserTradeDao
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
public interface FSimpleFtseUserTradeDao extends BaseJpaDao<FSimpleFtseUserTrade, String> {
	List<FSimpleFtseUserTrade> findById(String id);
	List<FSimpleFtseUserTrade> findByUidAndBusinessType(String id,int type);
}
