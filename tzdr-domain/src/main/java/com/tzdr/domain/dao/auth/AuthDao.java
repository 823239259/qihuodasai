package com.tzdr.domain.dao.auth;


import java.util.Set;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.auth.Auth;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public interface AuthDao extends BaseJpaDao<Auth, Long> {

    Auth findByUserId(Long userId);
    ///////////委托给AuthDaoImpl实现
    public Set<Long> findRoleIds(Long userId);

}
