package com.tzdr.cms.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.cms.service.organization.OrganizationService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.cms.entity.organization.Organization;
import com.tzdr.domain.cms.entity.user.User;


@Controller
@RequestMapping("/admin/org")
public class OrganizationController  extends BaseCmsController<Organization>
{
	private static Logger log = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UserService  userService;
	
	public OrganizationController() {
		setResourceIdentity("sys:system:orglist");
	}
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request) throws Exception 
	{
		return ViewConstants.OrganizationViewJsp.LIST_VIEW;
	}
	
	/**
	 * 获取所有组织机构数据  用于新建用户时选择
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getComboboxData")
	@ResponseBody
	public Object getComboboxData(HttpServletRequest request) throws Exception 
	{
		return organizationService.findAvailableOrganizations();
	}
	
	@RequestMapping(value = "/getTreeData")
	@ResponseBody
	public Object getTreeData(HttpServletRequest request,@RequestParam(value="parentId") Long parentId) throws Exception 
	{
		List<Organization>  orgs  = organizationService.findDeleteFalseOrganizations(parentId);
		if (orgs.size()==0){
			return null;
		}
		JSONArray  array  = new JSONArray();
		for (Organization organization:orgs){
			JSONObject  jsonObject  = new JSONObject();
			jsonObject.put("id", organization.getId());
			jsonObject.put("text", organization.getName());
			if (organization.isHasChildren()){
				jsonObject.put("state", "closed");	
			}else
			{
				jsonObject.put("iconCls", "icon-org");
			}
			array.add(jsonObject);
		}
		return array;		
	}
	
	
	@RequestMapping(value = "/getUserTreeData")
	@ResponseBody
	public Object getUserTreeData(HttpServletRequest request,@RequestParam(value="parentId") Long parentId) throws Exception 
	{
		JSONArray  array  = new JSONArray();
		List<Organization>  orgs  = organizationService.findByParentId(parentId);
		if (!CollectionUtils.isEmpty(orgs)){
			for (Organization organization:orgs){
				JSONObject  jsonObject  = new JSONObject();
				jsonObject.put("id",organization.getId());
				jsonObject.put("text", organization.getName());
				List<User>  users  = userService.findAvailableUsers(organization.getId());
				
				if (organization.isHasChildren() || users.size()>0){
					jsonObject.put("state", "closed");	
					array.add(jsonObject);
				}				
			}
		}
		
		// 查找组织下面的用户
		List<User>  users  = userService.findAvailableUsers(parentId);
		if (users.size()==0){
			return array;
		}
		for(User user : users){
			JSONObject  jsonObject  = new JSONObject();
			jsonObject.put("id", "U"+user.getId());
			jsonObject.put("text", user.getRealname());
			array.add(jsonObject);
		}		
		return array;		
	}
	
	
	
	@RequestMapping(value = "/nodeList")
	public String nodeList(HttpServletRequest request,@RequestParam(value="nodeID") Long nodeID) throws Exception 
	{
		request.setAttribute("nodeID",nodeID);
		return ViewConstants.OrganizationViewJsp.NODE_LIST_VIEW;
	}
	
	@RequestMapping(value = "/getNodeData")
	@ResponseBody
	public Object getNodeData(HttpServletRequest request,@RequestParam(value="parentId") Long parentId){
		/**
		 * 1 - 查找组织下面的部门
		 * 2 - 查找组织下面的用户
		 */
		JSONArray  array  = new JSONArray();
		List<Organization>  orgs  = organizationService.findDeleteFalseOrganizations(parentId);
		if (orgs.size() > 0){
			for (Organization organization:orgs){
				JSONObject  jsonObject  = new JSONObject();
				jsonObject.put("id", organization.getId());
				jsonObject.put("orgShow", organization.getShow()?"是":"否");
				jsonObject.put("name", organization.getName());
				jsonObject.put("email",null);
				jsonObject.put("phone",null);
				List<User>  users  = userService.findByOrganization(organization.getId());
				if (organization.isHasChildren() || users.size()>0){
					jsonObject.put("state", "closed");	
				}else
				{
					jsonObject.put("iconCls", "icon-org");
				}
				array.add(jsonObject);
			}
		}
		
		List<User>  users  = userService.findByOrganization(parentId);
		if (users.size()==0){
			return array;
		}
		for(User user : users){
			JSONObject  jsonObject  = new JSONObject();
			jsonObject.put("id","U"+user.getId());
			jsonObject.put("orgShow",null);
			jsonObject.put("name", user.getRealname());
			jsonObject.put("email",user.getEmail());
			jsonObject.put("phone",user.getMobilePhoneNumber());
			array.add(jsonObject);
		}
		
		return array;	
	}
	
	@Override
	public BaseService<Organization> getBaseService() {
		return organizationService;
	}

	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) Long id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			request.setAttribute("parentID",id);
			return ViewConstants.OrganizationViewJsp.EDIT_VIEW;			
		}
		if (StringUtil.equals(fromType,Constants.EDIT)){
			Organization  organization  = organizationService.get(id);
			request.setAttribute("organization",organization);
			return ViewConstants.OrganizationViewJsp.EDIT_VIEW;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
	@RequestMapping("/getDepUserDatas")
	@ResponseBody
	public Object getDepUserDatas(@RequestParam(value="depId") Long depId){
		
		List<User>  users  = userService.findByOrganization(depId);
		
		if (CollectionUtils.isEmpty(users)){
			return null;
		}
		
		JSONArray  jsonArray = new JSONArray();
		for (User user : users) {
			JSONObject  jsonObject  = new JSONObject();
			jsonObject.put("valueField", user.getId());
			jsonObject.put("textField", user.getRealname());
			jsonArray.add(jsonObject);
		}
		
		this.addOrgsUser(depId, jsonArray);
		
		return jsonArray;
	}

	/**
	 * 查询某组织下的所有组织
	 * @param depId
	 * @return
	 */
	private void addOrgsUser(Long depId,JSONArray  jsonArray){
		List<Organization>  orgs  = organizationService.findDeleteFalseOrganizations(depId);
		if (!CollectionUtils.isEmpty(orgs)){
			for (Organization organization : orgs) {
				List<User>  users  = userService.findByOrganization(organization.getId());
				
				if (CollectionUtils.isEmpty(users)){
					continue;
				}
				
				for (User user : users) {
					JSONObject  jsonObject  = new JSONObject();
					jsonObject.put("valueField", user.getId());
					jsonObject.put("textField", user.getRealname());
					jsonArray.add(jsonObject);
				}
				this.addOrgsUser(organization.getId(), jsonArray);
			}
		}
	}
}
