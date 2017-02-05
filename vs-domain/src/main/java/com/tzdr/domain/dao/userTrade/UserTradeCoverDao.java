package com.tzdr.domain.dao.userTrade;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.UserTradeCover;

public interface UserTradeCoverDao extends BaseJpaDao<UserTradeCover, String>,JpaSpecificationExecutor<UserTradeCover> {

}
