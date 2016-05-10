package com.tzdr.domain.dao.generalize;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.UserCommission;

public interface UserCommissionDao  extends BaseJpaDao<UserCommission, String>{

	public List<UserCommission> findByTypeAndManageFeeTimeBetween(int type,long startTime,long endTime);
}
