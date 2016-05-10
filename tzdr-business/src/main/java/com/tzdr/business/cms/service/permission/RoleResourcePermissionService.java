package com.tzdr.business.cms.service.permission;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.cms.entity.permission.Role;
import com.tzdr.domain.cms.entity.permission.RoleResourcePermission;
import com.tzdr.domain.dao.permission.RoleResourcePermissionDao;

/**
 * <p>
 * User:Lin Feng
 * <p>
 * Version: 1.0
 */
@Service
public class RoleResourcePermissionService extends
		BaseServiceImpl<RoleResourcePermission, RoleResourcePermissionDao> {

	public List<RoleResourcePermission> findByRoleIn(Set<Role> roles) {
		List<RoleResourcePermission> roleResourcePermissions = Lists
				.newArrayList();
		if (roles != null && !roles.isEmpty()) {
			roleResourcePermissions = getEntityDao().findByRoleIn(roles);
		}
		return roleResourcePermissions;
	}
}
