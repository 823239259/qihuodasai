package com.tzdr.business.cms.service.permission;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.tzdr.business.cms.exception.RoleException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.permission.Role;
import com.tzdr.domain.cms.entity.permission.RoleResourcePermission;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.permission.RoleDao;

/**
 * <p>
 * User: Lin Feng
 * <p>
 * Version: 1.0
 */
@Service
public class RoleService extends BaseServiceImpl<Role, RoleDao> {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private RoleResourcePermissionService  roleResourcePermissionService;

	public void setRoleResourcePermissionService(RoleResourcePermissionService  roleResourcePermissionService) {
		this.roleResourcePermissionService = roleResourcePermissionService;
	}
	
	@Override
	public void update(Role role) {
		List<RoleResourcePermission> localResourcePermissions = role
				.getResourcePermissions();
		for (int i = 0, l = localResourcePermissions.size(); i < l; i++) {
			RoleResourcePermission localResourcePermission = localResourcePermissions
					.get(i);
			localResourcePermission.setRole(role);
			RoleResourcePermission dbResourcePermission = findRoleResourcePermission(localResourcePermission);
			if (dbResourcePermission != null) {// 出现在先删除再添加的情况
				dbResourcePermission.setRole(localResourcePermission.getRole());
				dbResourcePermission.setResourceId(localResourcePermission
						.getResourceId());
				dbResourcePermission.setPermissionIds(localResourcePermission
						.getPermissionIds());
				localResourcePermissions.set(i, dbResourcePermission);
			}
		}
		
		setOperateLog(role,"更新角色","edit");
		super.update(role);
	}

	private RoleResourcePermission findRoleResourcePermission(
			RoleResourcePermission roleResourcePermission) {
		return getEntityDao().findRoleResourcePermission(
				roleResourcePermission.getRole(),
				roleResourcePermission.getResourceId());
	}

	/**
	 * 获取可用的角色列表
	 *
	 * @param roleIds
	 * @return
	 */
	public Set<Role> findShowRoles(Set<Long> roleIds) {
		Set<Role> roles = Sets.newHashSet();
		if (roleIds != null&&!roleIds.isEmpty()) {
			roles.addAll(getEntityDao().findByDeletedFalseAndShowTrueAndIdIn(roleIds));
		}
		return roles;
	}
	
	
	/**
	 * 获取可用的角色列表
	 *
	 * @param roleIds
	 * @return
	 */
	public List<Role> findAvailableRoles() {
		return getEntityDao().findByDeletedFalseAndShowTrue();
	}
	
	@Override
    public void removes(Serializable... ids) throws BusinessException {
    	for (Serializable id : ids){
    		Role  role = getEntityDao().get(Long.valueOf(id.toString()));
    		if (ObjectUtil.equals(role, null)){
    			throw new RoleException("business.delete.not.found.data",null);
    			
    		}
    		role.setDeleted(Boolean.TRUE);
    		setOperateLog(role,"删除角色","edit");
    		super.update(role);
    	}
    }
	

	private void setOperateLog(Role  role,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		role.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			role.setUpdateTime(Dates.getCurrentLongDate());
    		role.setUpdateUser(loginUser.getRealname());
    		role.setUpdateUserOrg(loginUser.getOrganization().getName());
    		role.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		role.setCreateTime(Dates.getCurrentLongDate());
		role.setCreateUser(loginUser.getRealname());
		role.setCreateUserId(loginUser.getId());
		role.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
}
