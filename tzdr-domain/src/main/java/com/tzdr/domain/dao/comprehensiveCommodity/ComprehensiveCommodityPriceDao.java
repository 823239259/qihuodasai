package com.tzdr.domain.dao.comprehensiveCommodity;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import org.springframework.data.jpa.repository.Query;

import com.tzdr.domain.web.entity.ComprehensiveCommodityPrice;
import com.tzdr.domain.web.entity.OutDiskPrice;

/**
 * 商品综合价格设置
 * @author Guo Xingyou
 *
 */
public interface ComprehensiveCommodityPriceDao extends BaseJpaDao<ComprehensiveCommodityPrice, String> {
	@Query("from ComprehensiveCommodityPrice order by tradeType")
	List<ComprehensiveCommodityPrice> findAllOrderByTradeType();
}
