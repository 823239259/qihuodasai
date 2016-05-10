package com.tzdr.domain.dao.rechargelist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.RechargeList;

/**
* @Description: TODO(用户帐号业务信息管理持久层)
* @ClassName: WUserDao
* @author wangpinqun
* @date 2014年12月25日 下午1:34:26
 */
public interface RechargeListDao extends BaseJpaDao<RechargeList, Long>,JpaSpecificationExecutor<RechargeList>{

	/**
	 * 根据流水号 查询充值记录
	 * @param auditorId
	 * @return
	 */
	public List<RechargeList> findByTradeNo(String tradeNo);
}
