package com.tzdr.domain.dao.permission;

import java.util.List;
import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.permission.Role;
import com.tzdr.domain.cms.entity.permission.RoleResourcePermission;



/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public interface RoleResourcePermissionDao extends BaseJpaDao<RoleResourcePermission, Long> {
		//为了使用缓存，不用get方法,因为默认的JPA方法是没有打开缓存
		@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
		public List<RoleResourcePermission> findByRoleIn(Set<Role> roles);

}
