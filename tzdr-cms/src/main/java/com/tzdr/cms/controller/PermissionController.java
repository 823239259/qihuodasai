package com.tzdr.cms.controller;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.business.cms.service.permission.PermissionService;
import com.tzdr.business.cms.service.permission.RoleService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.cms.entity.permission.Permission;


@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseCmsController<Permission>
{
	private static Logger log = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RoleService roleService;
	
	public PermissionController() {
		setResourceIdentity("sys:rolemanager:permisionlist");
	}
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request) throws Exception 
	{
		return ViewConstants.PermissionViewJsp.PERMISSION_LIST_VIEW;
	}
	
	@RequestMapping(value = "/resource")
	public String toResource(HttpServletRequest request) throws Exception 
	{
	
		return ViewConstants.PermissionViewJsp.RESOURCE_LIST_VIEW;
	}
	
	@RequestMapping(value = "/toGrantRole")
	public String toGrantRole(HttpServletRequest request) throws Exception 
	{
		request.setAttribute("roles",roleService.findAvailableRoles());
		return ViewConstants.PermissionViewJsp.GRANT_ROLE_VIEW;
	}

	@Override
	public BaseService<Permission> getBaseService() {
		return permissionService;
	}
}
