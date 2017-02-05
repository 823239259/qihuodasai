package com.tzdr.domain.dao.permission;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.permission.Permission;



/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public interface PermissionDao extends BaseJpaDao<Permission, Long> {

		@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
		public Permission findById(Long id);

}
