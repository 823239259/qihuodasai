package com.tzdr.cms.controller;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.organization.OrganizationService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.organization.Organization;
import com.tzdr.domain.cms.entity.user.User;


/**
 * 
 * <B>说明: </B> 系统用户管理
 * @zhouchen
 * 2016年1月21日 上午9:28:20
 */
@Controller
@RequestMapping("/admin/user")
public class UserController  extends BaseCmsController<User>
{
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	public UserController() {
		setResourceIdentity("sys:system:userlist");
	}

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request) throws Exception 
	{
		return ViewConstants.UserViewJsp.LIST_VIEW;
	}


	/**
	 * 编辑系统用户信息
	 * @param request
	 * @param fromType
	 * @param id
	 * @param orgID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) Long id,
			@RequestParam(value="orgID",required=false) Long orgID) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.UserViewJsp.EDIT_VIEW;			
		}
		
		if (StringUtil.equals(fromType,Constants.ORGANIZATION_ADD_USER)){
			Organization  organization = organizationService.get(orgID);
			request.setAttribute("organization",organization);
			return ViewConstants.UserViewJsp.EDIT_VIEW;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			User  user  = userService.get(id);
			request.setAttribute("user",user);
			return ViewConstants.UserViewJsp.EDIT_VIEW;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
	@Override
	public BaseService<User> getBaseService() {
		return userService;
	}
	
	
	/**
	 * 重置系统用户密码
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPasswod", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult resetPasswod(@RequestParam("ids") String ids) throws Exception  {
		//重置权限	
		 if (permissionList != null) {
	           this.permissionList.assertHasResetPermission();
	     }
		 
		 Set<Long>  idSet = WebUtil.convertStringToSet(ids);
		if (CollectionUtils.isEmpty(idSet)){
			return new JsonResult(false,MessageUtils.message("user.reset.password.ids.null"));
		}
		
		userService.resetPassword(idSet,MessageUtils.message("user.default.password",new Object()));
		return new JsonResult(MessageUtils.message("user.reset.password.success"));
	}
	
	
	/**
	 * 测试代码：含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/testmultilist", method = RequestMethod.POST)
	@ResponseBody
	public Object testmultilist(EasyUiPageInfo easyUiPage, Model model,
			HttpServletRequest request) throws Exception{
		
		  //查看权限
		  if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	        }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		//Map<String, Boolean> sortMap = EasyuiUtil.getSortMap(easyUiPage);
		//查询数据
		
		PageInfo<Object> pageInfo = userService.multilistPageQuery(easyUiPage, searchParams);
		
		return new EasyUiPageData(pageInfo);
	}
}
