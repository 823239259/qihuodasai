package com.tzdr.business.cms.service.auth;

import java.util.List;
import java.util.Set;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.tzdr.business.cms.service.permission.PermissionService;
import com.tzdr.business.cms.service.permission.RoleResourcePermissionService;
import com.tzdr.business.cms.service.permission.RoleService;
import com.tzdr.business.cms.service.resource.ResourceService;
import com.tzdr.domain.cms.entity.permission.Permission;
import com.tzdr.domain.cms.entity.permission.Role;
import com.tzdr.domain.cms.entity.permission.RoleResourcePermission;
import com.tzdr.domain.cms.entity.resource.Resource;
import com.tzdr.domain.cms.entity.user.User;

/**
 * 分组、组织机构、用户、新增、修改、删除时evict缓存
 * <p/>
 * 获取用户授权的角色及组装好的权限
 * <p>
 * User: Lin Feng
 * <p>
 * Version: 1.0
 */
@Service
public class UserAuthService {

	@Autowired
	private AuthService authService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RoleResourcePermissionService roleResourcePermissionService;

	public Set<Role> findRoles(User user) {
		if (user == null) {
			return Sets.newHashSet();
		}
		// 获取用户角色
		Set<Long> roleIds = authService.findRoleIds(user.getId());
		Set<Role> roles = roleService.findShowRoles(roleIds);
		return roles;
	}

	public Set<String> findStringRoles(User user) {
		Set<Role> roles = this.findRoles(user);
		return Sets.newHashSet(Collections2.transform(roles,
				new Function<Role, String>() {
					@Override
					public String apply(Role input) {
						return input.getRole();
					}
				}));
	}

	/**
	 * 根据角色获取 权限字符串 如sys:admin
	 *
	 * @param user
	 * @return
	 */
	public Set<String> findStringPermissions(User user) {
		Set<String> permissions = Sets.newHashSet();

		Set<Role> roles = this.findRoles(user);
		List<RoleResourcePermission> rrpList=roleResourcePermissionService.findByRoleIn(roles);
			for (RoleResourcePermission rrp : rrpList) {

				Resource resource = resourceService.findById(rrp.getResourceId());

				String actualResourceIdentity = resourceService
						.findActualResourceIdentity(resource);

				// 不可用 即没查到 或者标识字符串不存在
				if (resource == null
						|| StringUtil.isEmpty(actualResourceIdentity)
						|| Boolean.FALSE.equals(resource.getShow())) {
					continue;
				}

				for (Long permissionId : rrp.getPermissionIds()) {
					Permission permission = permissionService.findById(permissionId);

					// 不可用
					if (permission == null
							|| Boolean.FALSE.equals(permission.getShow())) {
						continue;
					}
					permissions.add(actualResourceIdentity + ":"
							+ permission.getPermission());

				}
			}
		return permissions;
	}

}
