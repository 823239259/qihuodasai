package com.tzdr.cms.controller.commodityFutures;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.userTrade.FSimpleProductAppendLevelMoneyService;
import com.tzdr.business.service.userTrade.FSimpleProductUserTradeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.cms.FSimpleUserTradePlanVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;


import jodd.util.StringUtil;

/**
 * 商品期货风控管理
 * 
 * @Description:
 * @author liuhaichuan
 * @date 2015年9月18日
 *
 */
@Controller
@RequestMapping("/admin/commodityFutureRisk")
public class FutureRiskController extends BaseCmsController<FSimpleFtseUserTrade> {

	@Autowired
	private FSimpleProductUserTradeService fSimpleUserTradeService;
	
	@Autowired
	private FSimpleProductAppendLevelMoneyService fSimpleAppendLevelMoneyService;

	private static Logger logger = LoggerFactory.getLogger(FutureRiskController.class);

	@Override
	public BaseService<FSimpleFtseUserTrade> getBaseService() {
		// TODO Auto-generated method stub
		return fSimpleUserTradeService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:riskmanager:commodityFutureRisk");
	}

	/**
	 * 跳转到信息列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String getNoticeList(HttpServletRequest request, HttpServletResponse response) {
		return ViewConstants.CommodityFutureRiskJsp.LIST_VIEW;
	}

	/**
	 * 获取数据
	 * 
	 * @param easyUiPage
	 * @param req
	 * @param resp
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getData")
	@ResponseBody
	public Object getData(int type, EasyUiPageInfo easyUiPage, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}

		ConnditionVo connVo = new ConnditionVo(req);
		if (connVo.isExcel()) {
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}

		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);

		PageInfo<Object> pageInfo = null;

		switch (type) {
		case 1:
			pageInfo = fSimpleUserTradeService.getApplyData(easyUiPage, searchParams);
			if (connVo.isNotExcel(pageInfo.getPageResults(), resp, "商品期货申请报表.xls")) {
				return new EasyUiPageData(pageInfo);
			}
			break;
		case 2:
			pageInfo = fSimpleAppendLevelMoneyService.getData(easyUiPage, searchParams);
			if (connVo.isNotExcel(pageInfo.getPageResults(), resp, "商品期货补充保证金报表.xls")) {
				return new EasyUiPageData(pageInfo);
			}
			break;
		case 3:
			pageInfo = fSimpleUserTradeService.getPlanData(easyUiPage, searchParams);
			if (connVo.isNotExcel(pageInfo.getPageResults(), resp, "商品期货方案管理报表.xls")) {
				return new EasyUiPageData(pageInfo);
			}
			break;
		default:
			break;
		}

		return null;
	}
	
	/**
	 * 拒绝申请
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/refuseApply", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult refuseApply(@RequestParam(value = "id", required = true) String id) throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasAuditPermission();
		}
		JsonResult result = new JsonResult(false);
		try {
			fSimpleUserTradeService.refuseApply(id);
			result.setSuccess(true);
			result.setMessage("操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
			result.setMessage("操作失败");
		}
		return result;
	}

	/**
	 * 商品期货操盘账号管理页
	 * @param id
	 * @param username
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/toAccount")
	public String toSuccess(String id, int type, HttpServletRequest req, HttpServletResponse resp) {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasAuditPermission();
		}
		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleUserTradeService.get(id);
		req.setAttribute("fSimpleFtseUserTrade", fSimpleFtseUserTrade);
		//操作类型：1：新增；2：修改
		req.setAttribute("type", type);
		return ViewConstants.CommodityFutureRiskJsp.ACCOUNT_VIEW;
	}
	
	/**
	 * 保存新增的数据
	 * @param fSimpleConfigVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editAccount" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editAccount(String id, String tranAccount, String tranPassword, int type)
			throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasAuditPermission();
		}
		
		JsonResult result = new JsonResult(false);
		try {
			if(checkAccountData(id, tranAccount, tranPassword)){
				FSimpleFtseUserTrade userTrade=fSimpleUserTradeService.get(id);
				if(userTrade==null){
					result.setMessage("此记录不存在");
				}else if(userTrade.getStateType()==5){
					result.setMessage("此记录审核不通过，不能分配操盘账号");
				}else if(type==1&&userTrade.getStateType()==4){
					result.setMessage("账号已分配，请刷新后查看");
				}else{
					fSimpleUserTradeService.editAccount(id, tranAccount, tranPassword, type);
					result.setSuccess(true);
					result.setMessage("操作成功");
				}
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
	 * 补充保证金——已处理
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult changeStatus(@RequestParam(value = "id", required = true) String id) throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasAuditPermission();
		}
		JsonResult result = new JsonResult(false);
		try {
			String message=fSimpleAppendLevelMoneyService.changeStatus(id);
			result.setSuccess(true);
			result.setMessage(message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
			result.setMessage("操作失败");
		}
		return result;
	}
	

	/**
	 * 商品期货操盘方案管理结果页
	 * @param id
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/toResult")
	public String toResult(String id, String mobile, HttpServletRequest req, HttpServletResponse resp) {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasAuditPermission();
		}
		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleUserTradeService.get(id);
		req.setAttribute("mobile", mobile);
		req.setAttribute("fSimpleFtseUserTrade", fSimpleFtseUserTrade);
		return ViewConstants.CommodityFutureRiskJsp.RESULT_VIEW;
	}
	
	/**
	 * 保存新增的数据
	 * @param fSimpleConfigVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editResult" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editResult(FSimpleUserTradePlanVo plan)
			throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasAuditPermission();
		}

		JsonResult result = new JsonResult(false);
		try {
			if(checkPlanData(plan)){
				fSimpleUserTradeService.editResult(plan.getId(), plan.getTranProfitLoss(), plan.getTranFeesTotal());
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
	 * 方案管理——结算
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/settle", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult settle(@RequestParam(value = "id", required = true) String id) throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasAuditPermission();
		}
		JsonResult result = new JsonResult(false);
		try {
			String message=fSimpleUserTradeService.settle(id);
			if(message!=null&&message!=""){
				result.setSuccess(false);
				result.setMessage(message);
				return result;
			}
			result.setSuccess(true);
			result.setMessage("操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
			result.setMessage("操作失败");
		}
		return result;
	}
	
	/**
	 * 验证提交的操盘账号
	 * @param id
	 * @param tranAccount
	 * @param tranPassword
	 * @return
	 */
	private boolean checkAccountData(String id, String tranAccount, String tranPassword){
		if(StringUtil.isBlank(id)){
			return false;
		}
		if(StringUtil.isBlank(tranAccount)){
			return false;
		}
		if(StringUtil.isBlank(tranPassword)){
			return false;
		}
		return true;
	}
	
	private boolean checkPlanData(FSimpleUserTradePlanVo plan){
		if(StringUtil.isBlank(plan.getId())){
			return false;
		}
		if(plan.getTranProfitLoss()==null){
			return false;
		}
		if(plan.getTranFeesTotal()==null){
			return false;
		}
		return true;
	}

}
