package com.tzdr.domain.dao.extension;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.LuckDraw;

public interface LuckDrawDao extends BaseJpaDao<LuckDraw, String>,JpaSpecificationExecutor<LuckDraw> {
	/**
	 * 用户Id查询抽奖记录
	 * @param uid
	 * @return
	 */
	LuckDraw findByUidAndActivity(String uid,String activity);
}
