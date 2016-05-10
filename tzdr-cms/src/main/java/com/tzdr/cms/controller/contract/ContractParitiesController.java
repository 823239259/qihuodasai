package com.tzdr.cms.controller.contract;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.contract.ContractParitiesService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.ContractParitiesVo;
import com.tzdr.domain.web.entity.ContractParities;


/**
 * 主力合约维护
 * @author zhouchen
 * 2016年3月14日 下午2:33:32
 */
@Controller
@RequestMapping("/admin/contractParities")
public class ContractParitiesController extends BaseCmsController<ContractParities>{
	private static Logger log = LoggerFactory.getLogger(ContractParitiesController.class);
	
	@Autowired
	private ContractParitiesService contractParitiesService;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public BaseService<ContractParities> getBaseService() {
		return contractParitiesService;
	}
	
	public ContractParitiesController() {
		setResourceIdentity("sys:settingParams:contractParities");
	}
	
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		log.info("主力合约参数设置.....");
		return ViewConstants.ContractParitiesJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object getDatas(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,HttpServletRequest request) throws Exception{
		try {
			//判断是否具有查看权限
			if (permissionList != null) {
				this.permissionList.assertHasViewPermission();
			}
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
			
			//获取模糊搜索参数
			Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
			
			//查询数据
			PageInfo<Object> pageInfo = this.contractParitiesService.getPageDatas(easyUiPage, searchParams);
			
			return new EasyUiPageData<Object>(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("主力合约查询数据异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	
	@RequestMapping(value="saveOrUpdateConfig")
	@ResponseBody
	public JsonResult saveOrUpdateConfig(HttpServletResponse response,HttpServletRequest request,ContractParitiesVo contractParities){
		try {
			if(StringUtil.isBlank(contractParities.getContract())){
				return new JsonResult(false,"请完整填写参数后，提交数据！");
			}
			return this.contractParitiesService.saveOrUpdateConfig(contractParities);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改主力合约配置：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	
}
