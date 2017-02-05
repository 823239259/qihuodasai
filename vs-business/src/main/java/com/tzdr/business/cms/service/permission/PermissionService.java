package com.tzdr.business.cms.service.permission;


import org.springframework.stereotype.Service;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.cms.entity.permission.Permission;
import com.tzdr.domain.dao.permission.PermissionDao;

/**
 * <p>User:Lin Feng
 * <p>Version: 1.0
 */
@Service
public class PermissionService extends BaseServiceImpl<Permission, PermissionDao> {
	
	 public Permission findById(Long id){
	    	return getEntityDao().findById(id);
	 }
}
