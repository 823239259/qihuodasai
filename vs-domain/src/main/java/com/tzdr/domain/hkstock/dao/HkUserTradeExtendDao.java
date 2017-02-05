package com.tzdr.domain.hkstock.dao;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.hkstock.entity.HkUserTradeExtend;

/**
 * 港股操盘方案扩展
 * @author zhouchen
 * 2015年10月16日 上午11:18:57
 */
public interface HkUserTradeExtendDao extends BaseJpaDao<HkUserTradeExtend, String> {

	/**
	 * 根据方案号，获取方案审核信息
	 * @param tradeId
	 * @return
	 */
	public List<HkUserTradeExtend> findByTradeId(String tradeId);
}
