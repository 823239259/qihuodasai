package com.tzdr.cms.hkstock.controller.hkParentAccount;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.hkstock.service.HkParentAccountService;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.hkstock.entity.HkParentAccount;

import jodd.util.StringUtil;

/**
 * 港股母账户列表
 * @Description: 
 * @author liuhaichuan
 * @date 2015年10月20日
 *
 */
@Controller
@RequestMapping("/admin/hkstock/hkParentAccount")
public class HkParentAccountController extends BaseCmsController<HkParentAccount> {
	
	private static Logger logger = LoggerFactory.getLogger(HkParentAccountController.class);

	@Autowired
	private HkParentAccountService hkParentAccountService;
	
	@Autowired
	private AuthService authService;

	@Override
	public BaseService<HkParentAccount> getBaseService() {
		// TODO Auto-generated method stub
		return hkParentAccountService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:accountmanager:hkParentAccount");
	}
	
	/**
	 * 查询可用的 未被删除的 母账户
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getComboboxData")
	@ResponseBody
	public Object getComboboxData(HttpServletRequest  request){
		return hkParentAccountService.findAvailableDatas();
	}

	/**
	 * 跳转到数据列表页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return HkViewConstants.HkParentAccountJsp.LIST_VIEW;
	}

	/**
	 * 获取数据
	 * @param easyUiPage
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public Object getData(EasyUiPageInfo easyUiPage, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		PageInfo<Object> pageInfo = hkParentAccountService.getData(easyUiPage, searchParams);
		return new EasyUiPageData<>(pageInfo);
	}

	/**
	 * 跳转到编辑页
	 * @param id
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/toEdit")
	public String toEdit(String id, HttpServletRequest req, HttpServletResponse resp) {
		// true为新增操作，false为修改操作
		boolean isCreate = StringUtil.isBlank(id);
		if (permissionList != null) {
			if (isCreate) {
				this.permissionList.assertHasCreatePermission();
			} else {
				this.permissionList.assertHasUpdatePermission();
			}
		}
		HkParentAccount parentAccount = new HkParentAccount();
		if (!isCreate) {
			parentAccount = hkParentAccountService.get(id);
		}
		req.setAttribute("parentAccount", parentAccount);
		return HkViewConstants.HkParentAccountJsp.EDIT_VIEW;
	}

	/**
	 * 编辑数据
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(HkParentAccount vo) {

		// true为新增操作，false为修改操作
		boolean isCreate = vo==null || StringUtil.isBlank(vo.getId());
		if (permissionList != null) {
			if (isCreate) {
				this.permissionList.assertHasCreatePermission();
			} else {
				this.permissionList.assertHasUpdatePermission();
			}
		}
		
		JsonResult result = new JsonResult(false);
		try {
			if(checkData(vo)){
				User loginUser=authService.getCurrentUser();
				HkParentAccount parentAccount=new HkParentAccount();
				if(isCreate){
					parentAccount.setAccountNo(vo.getAccountNo());
					parentAccount.setName(vo.getName());
					parentAccount.setOperateContent(vo.getOperateContent());
					parentAccount.setCreateUser(loginUser.getRealname());
					parentAccount.setCreateUserId(loginUser.getId());
					parentAccount.setCreateTime(Dates.getCurrentLongDate());
					hkParentAccountService.save(parentAccount);
				}else{
					parentAccount=hkParentAccountService.get(vo.getId());
					parentAccount.setAccountNo(vo.getAccountNo());
					parentAccount.setName(vo.getName());
					parentAccount.setOperateContent(vo.getOperateContent());
					parentAccount.setUpdateUser(loginUser.getRealname());
					parentAccount.setUpdateUserId(loginUser.getId());
					parentAccount.setUpdateTime(Dates.getCurrentLongDate());
					hkParentAccountService.update(parentAccount);
				}
				result.setSuccess(true);
				result.setMessage("操作成功");
			}else{
				result.setMessage("传入的数据不正确");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
			result.setMessage("操作失败");
		}
		
		return result;
	}
	
	/**
	 * 检测提交的数据
	 * @param parentAccount
	 * @return
	 */
	private boolean checkData(HkParentAccount parentAccount){
		if(parentAccount==null){
			return false;
		}
		if(StringUtil.isBlank(parentAccount.getAccountNo())){
			return false;
		}
		if(StringUtil.isBlank(parentAccount.getName())){
			return false;
		}
		return true;
	}
}
