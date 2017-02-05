package com.tzdr.domain.dao.bespokeTrade;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.BespokeTrade;

/**
 * 
 * @author LiuYang
 *
 * 2015年6月11日 下午8:18:15
 */
public interface BespokeTradeDao extends BaseJpaDao<BespokeTrade, String> {

	@Query("from BespokeTrade where state=?1")
	List<BespokeTrade> findData(int status);
}
