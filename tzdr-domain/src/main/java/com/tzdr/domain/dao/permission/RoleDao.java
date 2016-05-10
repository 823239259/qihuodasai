package com.tzdr.domain.dao.permission;


import java.util.List;
import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.permission.Role;
import com.tzdr.domain.cms.entity.permission.RoleResourcePermission;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public interface RoleDao extends BaseJpaDao<Role, Long> {

    @Query("from RoleResourcePermission where role=?1 and resourceId=?2")
    RoleResourcePermission findRoleResourcePermission(Role role, Long resourceId);
    
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<Role> findByDeletedFalseAndShowTrueAndIdIn(Set<Long> roleIds);
    
    
    /**
	 * 查找可用的角色
	 * @return
	 */
	List <Role> findByDeletedFalseAndShowTrue();
	
    
}
