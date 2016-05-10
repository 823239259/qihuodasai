package com.tzdr.domain.dao.rechargelist;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.RechargeAuditRule;

/**
 *  充值审核规则
 * @author zhouchen
 *
 */
public interface RechargeAuditRuleDao extends BaseJpaDao<RechargeAuditRule, String> 
{
	/**
	 * 根据审核人id 查询审核规则数据
	 * @param auditorId
	 * @return
	 */
	public List<RechargeAuditRule> findByAuditorId(String auditorId);
}
