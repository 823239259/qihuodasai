package com.tzdr.domain.dao.rechargelist;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.fund.BatchHandRecharge;

/**
 * 
 * <B>说明: 手工批量充值</B>
 * @zhouchen
 * 2016年2月17日 上午10:42:17
 */
public interface BatchHandRechargeDao extends BaseJpaDao<BatchHandRecharge, String> 
{

	/**
	 * 根据调帐类型查询结果
	 * @param type
	 * @return
	 */
	public List<BatchHandRecharge> findByType(int type);
}
