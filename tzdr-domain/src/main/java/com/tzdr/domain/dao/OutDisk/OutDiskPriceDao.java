package com.tzdr.domain.dao.OutDisk;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.OutDiskPrice;
/**
 * 国际综合价格设置
 * 类说明
 * @author  zhaozhao
 * @date    2016年2月22日下午6:02:13
 * @version 1.0
 */
public interface OutDiskPriceDao extends BaseJpaDao<OutDiskPrice, String>{
	@Query("from OutDiskPrice order by tradeType")
	List<OutDiskPrice> findAllOrderByTradeType();

}
