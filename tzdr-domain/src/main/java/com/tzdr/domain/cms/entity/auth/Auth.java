package com.tzdr.domain.cms.entity.auth;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.google.common.collect.Sets;
import com.tzdr.common.dao.support.hibernate.CollectionToStringUserType;
import com.tzdr.common.domain.BaseAuthEntity;

/**
 * 组织机构 工作职位 用户 角色 关系表
 * <p>
 * User: Lin Feng
 * <p>
 * Version: 1.0
 */
@TypeDef(name = "SetToStringUserType", typeClass = CollectionToStringUserType.class, parameters = {
		@Parameter(name = "separator", value = ","),
		@Parameter(name = "collectionType", value = "java.util.HashSet"),
		@Parameter(name = "elementType", value = "java.lang.Long") })
@Entity
@Table(name = "sys_user_role")
@Cacheable
public class Auth extends BaseAuthEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4751448262111610983L;

	/**
	 * 用户
	 */
	@Column(name = "user_id")
	private Long userId = 0L;

	@Type(type = "SetToStringUserType")
	@Column(name = "role_ids")
	private Set<Long> roleIds;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<Long> getRoleIds() {
		if (roleIds == null) {
			roleIds = Sets.newHashSet();
		}
		return roleIds;
	}

	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public void addRoleId(Long roleId) {
		getRoleIds().add(roleId);
	}

	public void addRoleIds(Set<Long> roleIds) {
		getRoleIds().addAll(roleIds);
	}

	public Auth(Long userId, Set<Long> roleIds) {
		super();
		this.userId = userId;
		this.roleIds = roleIds;
	}

	public Auth() {
		// TODO Auto-generated constructor stub
	}

}
