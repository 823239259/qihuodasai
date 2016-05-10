package com.tzdr.cms.support;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.AbstractEntity;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;

/**
 * base控制器以后备用
 * 
 * 
 * @author Lin Feng
 * 
 * @param <T>
 * @param <M>
 */
@SuppressWarnings("rawtypes")
public abstract class BaseCmsController<M extends AbstractEntity> {
	
	
	/**
	 * 注入service
	 * @return
	 */
	public abstract BaseService<M> getBaseService();
	
	  protected PermissionList permissionList = null;
	  /**
	     * 权限前缀：如sys:user
	     * 则生成的新增权限为 sys:user:create
	     */
	    public void setResourceIdentity(String resourceIdentity) {
	        if (!StringUtil.isEmpty(resourceIdentity)) {
	            permissionList = PermissionList.newPermissionList(resourceIdentity);
	        }
	    }
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/easyuiPage", method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,
			ServletRequest request) throws Exception{
		
		  //查看权限
		  if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	        }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		Map<String, Boolean> sortMap = EasyuiUtil.getSortMap(easyUiPage);
		//查询数据
		PageInfo<M> pageInfo = new PageInfo<M>(easyUiPage.getRows(), easyUiPage.getPage());
		pageInfo = getBaseService().query(pageInfo,searchParams,sortMap);
		
		return new EasyUiPageData(pageInfo);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@Valid @ModelAttribute("m") M m,BindingResult result) throws Exception {
		//增加权限
		 if (permissionList != null) {
		     this.permissionList.assertHasCreatePermission();
		 }

		if (result.hasErrors())
		{
			return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
		}
		
		
		
		getBaseService().save(m);
		return new JsonResult(MessageUtils.message("create.success"));
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult update(@Valid @ModelAttribute("m") M m,BindingResult result) throws Exception {
		//修改权限	
		if (permissionList != null) {
	         this.permissionList.assertHasUpdatePermission();
	     }
		if (result.hasErrors())
		{
			return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
		}	
		getBaseService().update(m);
		return new JsonResult(MessageUtils.message("update.success"));
	}

	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@RequestParam("id") String id) throws Exception  {
		//刪除权限	
		 if (permissionList != null) {
	           this.permissionList.assertHasDeletePermission();
	     }

		getBaseService().removeById(id);
		return new JsonResult(MessageUtils.message("delete.success"));
	}
	
	
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult batchDelete(@RequestParam("ids") String ids) throws Exception  {
		//刪除权限	
		 if (permissionList != null) {
	           this.permissionList.assertHasDeletePermission();
	     }
		 
		Serializable [] arrayIds = ids.split(",");
		if (ArrayUtils.isEmpty(arrayIds)){
			return new JsonResult(false,MessageUtils.message("delete.not.select.data"));
		}
		
		getBaseService().removes(arrayIds);
		return new JsonResult(MessageUtils.message("delete.success"));
	}
	

}
