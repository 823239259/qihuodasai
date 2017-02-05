package com.tzdr.cms.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tzdr.business.cms.service.permission.PermissionService;
import com.tzdr.business.cms.service.permission.RoleService;
import com.tzdr.business.cms.service.resource.ResourceService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.permission.Permission;
import com.tzdr.domain.cms.entity.permission.Role;
import com.tzdr.domain.cms.entity.permission.RoleResourcePermission;
import com.tzdr.domain.cms.entity.resource.Resource;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseCmsController<Role> {
	private static Logger log = LoggerFactory
			.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public BaseService<Role> getBaseService() {
		return roleService;
	}
	public RoleController() {
		setResourceIdentity("sys:rolemanager:rolelist");
	}
	
	/**
	 * 
	 * @param request
	 * @param role
	 * @param result
	 * @param resourceData 如 1-2,3;2-3,4,9;3-3,9 资源id-权限id
	 * @return
	 */
	@RequestMapping(value="saveRole")
	@ResponseBody
	public JsonResult  saveRole(HttpServletRequest request,@Valid Role role,BindingResult result,
			@RequestParam("resourceData") String resourceData){
		
		 if (permissionList != null) {
	            this.permissionList.assertHasCreatePermission();
	     }
		 
		if (result.hasErrors())
		{
			return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
		}
		
		String [] resourcePermissions = resourceData.split(Constants.SEPERATOR_SEMICOLON);
		if (ArrayUtils.isEmpty(resourcePermissions))
		{
			return new JsonResult(false,MessageUtils.message("role.save.not.select.resource"));
		}
		
		List<RoleResourcePermission> roleResourcePermissions = getRoleResourcePermissions(role, resourcePermissions);
		if (CollectionUtils.isEmpty(roleResourcePermissions))
		{
			return new JsonResult(false,MessageUtils.message("role.save.not.select.resource"));
		}
		role.setResourcePermissions(roleResourcePermissions);
		roleService.save(role);
		return new JsonResult(MessageUtils.message("save.success"));
	}
	
	
	/**
	 * 
	 * @param request
	 * @param role
	 * @param result
	 * @param resourceData 如 1-2,3;2-3,4,9;3-3,9 资源id-权限id
	 * @return
	 */
	@RequestMapping(value="updateRole")
	@ResponseBody
	public JsonResult  updateRole(HttpServletRequest request,@Valid Role role,BindingResult result,
			@RequestParam("resourceData") String resourceData){
		

		 if (permissionList != null) {
	            this.permissionList.assertHasEditPermission();
	     }
		 
		if (result.hasErrors())
		{
			return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
		}
		
		String [] resourcePermissions = resourceData.split(Constants.SEPERATOR_SEMICOLON);
		if (ArrayUtils.isEmpty(resourcePermissions))
		{
			return new JsonResult(false,MessageUtils.message("role.save.not.select.resource"));
		}
		List<RoleResourcePermission> roleResourcePermissions = getRoleResourcePermissions(role, resourcePermissions);
		if (CollectionUtils.isEmpty(roleResourcePermissions))
		{
			return new JsonResult(false,MessageUtils.message("role.save.not.select.resource"));
		}
		role.setResourcePermissions(roleResourcePermissions);
		roleService.update(role);
		return new JsonResult(MessageUtils.message("update.success"));
	}
	
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request) throws Exception 
	{
		return ViewConstants.PermissionViewJsp.ROLE_LIST_VIEW;
	}
	

	@RequestMapping(value = "/editRole")
	public String editRole(HttpServletRequest request,
			@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) Long id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		request.setAttribute("permissions",permissionService.getAll());
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.PermissionViewJsp.EDIT_ROLE_VIEW;
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			
			request.setAttribute("role",roleService.get(id));
			return ViewConstants.PermissionViewJsp.EDIT_ROLE_VIEW;
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
	@RequestMapping(value = "/getRoleResources")
	@ResponseBody
	public Object getRoleResources(HttpServletRequest request,
			@RequestParam(value="id",required=false) Long id) throws Exception 
	{
		JSONObject  jsonResult  = new JSONObject();
		JSONArray  array  = new JSONArray();
		jsonResult.put("rows",array);
		jsonResult.put("total",0);
		
		if (ObjectUtil.equals(null, id)){
			return jsonResult;
		}
		
		Role role = roleService.get(id);
		if (ObjectUtil.equals(null, role)){
			return jsonResult;
		}
		
		List<RoleResourcePermission>  roleResourcePermissions = role.getResourcePermissions();
		if (CollectionUtils.isEmpty(roleResourcePermissions)){
			return jsonResult;
		}		
		for (RoleResourcePermission roleResourcePermission : roleResourcePermissions){
			 JSONObject  jsonObject = new JSONObject();
			 jsonObject.put("id",roleResourcePermission.getResourceId());
			 Set<Long> permissionIds = roleResourcePermission.getPermissionIds();
			 jsonObject.put("permissionIds",WebUtil.convertSetToString(permissionIds));
			 Resource  resource = resourceService.findById(roleResourcePermission.getResourceId());
			 String resourceName = resource.getName();
			 if (resource.isLeaf()){
				   resource = resourceService.findById(resource.getParentId());
				   resourceName = resource.getName()+"->>"+resourceName;
			 }
			 jsonObject.put("resource",resourceName);
			 jsonObject.put("permission",getPermissionsName(permissionIds));
			 array.add(jsonObject);
		}
		jsonResult.put("rows",array);
		jsonResult.put("total",array.size());
		return jsonResult;
	}
	
	
	public  String  getPermissionsName(Set<Long> permissionIds){
		if (CollectionUtils.isEmpty(permissionIds)){
			return null;
		}
		
		Set<String> permissionsName = Sets.newHashSet();
		for (Long permissionId : permissionIds){
			Permission  permission  = permissionService.get(permissionId);
			permissionsName.add(permission.getName()+"("+ permission.getPermission()+")");
		}
		return WebUtil.convertSetToString(permissionsName);
	}
	
	
	public List<RoleResourcePermission>  getRoleResourcePermissions(Role role,String [] resourcePermissions){
		List<RoleResourcePermission> roleResourcePermissions = Lists.newArrayList();
		for (String resourcePermission : resourcePermissions){
			String [] tempArray = resourcePermission.split(Constants.SEPERATOR_LINE);
			if (tempArray.length !=2  || StringUtil.isBlank(tempArray[0])
					|| StringUtil.isBlank(tempArray[1])){
				log.debug(MessageUtils.message("role.save.not.select.resource"));
				return null;
			}
			roleResourcePermissions.add(new RoleResourcePermission(role, Long.valueOf(tempArray[0]),  WebUtil.convertStringToSet(tempArray[1])));
		}
		return roleResourcePermissions;
	}
}
