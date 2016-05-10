package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.GeneralException;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.ParentAccountVo;
import com.tzdr.domain.vo.RechargeListVo;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.WUser;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 恒生母账户管理controller
 */
@Controller
@RequestMapping("/admin/parentAccount")
public class ParentAccountController extends BaseCmsController<ParentAccount> {
	 
	private static Logger log = LoggerFactory.getLogger(ParentAccountController.class);
	@Autowired
	private ParentAccountService parentAccountService;

	@Override
	public BaseService<ParentAccount> getBaseService() {
		return parentAccountService;
	}
	
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<ParentAccountVo> grid = new DataGridVo<ParentAccountVo>();
			PageInfo<ParentAccountVo> dataPage = new PageInfo<ParentAccountVo>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			
			PageInfo<ParentAccountVo> parentVoes = this.parentAccountService.queryDataByPageInfo(dataPage,connVo);
			grid.add(parentVoes.getPageResults());
			grid.setTotal(parentVoes.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@RequestMapping(value = "/createSave", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(ParentAccount m,String newAndOldStateStr,String allocationDateStr, BindingResult result)
			throws Exception {
		if (newAndOldStateStr != null && !"".equals(newAndOldStateStr)) {
			if (newAndOldStateStr.indexOf("1") > -1 
					&& newAndOldStateStr.indexOf("2") > -1) {
				m.setNewAndOldState(3);
			}
			else if (newAndOldStateStr.indexOf("1") > -1) {
				m.setNewAndOldState(1);
			}
			else if (newAndOldStateStr.indexOf("2") > -1 ) {
				m.setNewAndOldState(2);
			}
		}
		if (allocationDateStr != null) {
			Long allocationDate = TypeConvert.strToDatetime1000Long(allocationDateStr);
			m.setAllocationDate(allocationDate);
		}
		return super.create(m, result);
	}
	
	@RequestMapping(value = "/updateSave", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult update(ParentAccount m,String newAndOldStateStr,String allocationDateStr,BindingResult result) throws Exception {
		//修改权限	
		if (permissionList != null) {
	         this.permissionList.assertHasUpdatePermission();
	     }
		if (result.hasErrors())
		{
			return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
		}
		if (newAndOldStateStr != null && !"".equals(newAndOldStateStr)) {
			if (newAndOldStateStr.indexOf("1") > -1 
					&& newAndOldStateStr.indexOf("2") > -1) {
				m.setNewAndOldState(3);
			}
			else if (newAndOldStateStr.indexOf("1") > -1) {
				m.setNewAndOldState(1);
			}
			else if (newAndOldStateStr.indexOf("2") > -1 ) {
				m.setNewAndOldState(2);
			}
		}
		if (allocationDateStr != null) {
			Long allocationDate = TypeConvert.strToZeroDate1000(allocationDateStr,0);
			m.setAllocationDate(allocationDate);
		}
		
		getBaseService().update(m);
		return new JsonResult(MessageUtils.message("update.success"));
	}
	

	public ParentAccountController() {
		setResourceIdentity("sys:accountmanager:parentaccount");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		return ViewConstants.HundsunAccountViewJsp.PARENT_ACCOUNT_LIST_VIEW;
	}
	
	@RequestMapping(value="changePassword")
	public String changePassword(HttpServletRequest  request,@RequestParam("id") String id){
		if (permissionList != null) {
		     this.permissionList.assertHasResetPermission();
		 }
		if (StringUtil.isBlank(id)){
			log.debug(MessageUtils.message("parent.account.change.password.id.null"));
			throw new GeneralException("parent.account.change.password.id.null");
		}
		request.setAttribute("id",id);
		return ViewConstants.HundsunAccountViewJsp.CHANGE_PARENT_ACCOUNT_PASSWORD_VIEW;
	}
	
	
	@RequestMapping(value="getComboboxData")
	@ResponseBody
	public Object getComboboxData(HttpServletRequest  request){
		return parentAccountService.findAvailableDatas();
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.HundsunAccountViewJsp.PARENT_ACCOUNT_EDIT_VIEW;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			ParentAccount  parentAccount  = parentAccountService.get(id);
			request.setAttribute("parentAccount",parentAccount);
			Long allocationDate = parentAccount.getAllocationDate();
			if (allocationDate != null) {
				String allocationStr = TypeConvert.long1000ToDateStr(allocationDate);
				request.setAttribute("allocationStr", allocationStr);
			}
			Integer newAndOld = parentAccount.getNewAndOldState();
			if (newAndOld != null) {
				if (newAndOld == 1) {
					request.setAttribute("newAnd001", 1);
				}
				else if (newAndOld == 2) {
					request.setAttribute("newAnd002", 1);
				}
				else if (newAndOld == 3) {
					request.setAttribute("newAnd001", 1);
					request.setAttribute("newAnd002", 1);
				}
			}
			return ViewConstants.HundsunAccountViewJsp.PARENT_ACCOUNT_EDIT_VIEW;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
	
	@RequestMapping(value = "/toUpdateBalance")
	public String toUpdateBalance(HttpServletRequest request,@RequestParam(value="id",required=false) String id) throws Exception 
	{
			ParentAccount  parentAccount  = parentAccountService.get(id);
			request.setAttribute("parentAccount",parentAccount);
			return ViewConstants.HundsunAccountViewJsp.EIDT_PARENT_BALANCE_VIEW;	
	}
	
	
	@RequestMapping(value = "/updateBalance")
	@ResponseBody
	public JsonResult updateBalance(HttpServletRequest request,@RequestParam(value="id") String id,
			@RequestParam(value="fundsBalance") Double fundsBalance) throws Exception 
	{
			ParentAccount   parentAccount  = parentAccountService.get(id);
			parentAccount.setFundsBalance(fundsBalance);
			parentAccountService.updateParentAccount(parentAccount);
			return new JsonResult("修改成功！");
	}
	
	
	@RequestMapping(value="getByAccountGenre")
	@ResponseBody
	public Object getComboboxData(HttpServletRequest  request,
			@RequestParam(value="accountGenre") int accountGenre){
		return parentAccountService.findByAccountGenre(accountGenre);
	}
}
