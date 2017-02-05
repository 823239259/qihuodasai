package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.userTrade.UserTradeCoverService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.CoverAuditVo;
import com.tzdr.domain.web.entity.UserTradeCover;

@Controller
@RequestMapping("/admin/coveraudit")
public class CoverAuditController extends BaseCmsController<UserTradeCover> {
	
	@Autowired
	private UserTradeCoverService userTradeCoverService;
	
	@Override
	public BaseService<UserTradeCover> getBaseService() {

		return userTradeCoverService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:coveraudit");
	}
	
	/**
	 * 补仓审核列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(HttpServletRequest request){
		return ViewConstants.MatchFundsViewJsp.MONITOR_SCHEME_COVER_AUDIT_LIST_VIEW;
	}

	/**
	 * 补仓待审核信息
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/unauditlistdata", method = RequestMethod.POST)
	@ResponseBody
	public Object unAuditListData(EasyUiPageInfo easyUiPage, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查看权限
		if (permissionList != null) {
	      this.permissionList.assertHasViewPermission();
	    }
		
		ConnditionVo connVo = new ConnditionVo(request);
		if (connVo.isExcel()) {
		  easyUiPage.setPage(1);
		  easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		
		PageInfo<Object>  pageInfo  = userTradeCoverService.queryUnUserTradeCoverVoList(easyUiPage, searchParams);
		
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"补仓待审核方案列表.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}
	
	/**
	 * 补仓审核记录信息
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditListdata", method = RequestMethod.POST)
	public void auditListData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			ConnditionVo connVo = new ConnditionVo(request);
			DataGridVo<CoverAuditVo> grid = new DataGridVo<CoverAuditVo>();
			PageInfo<CoverAuditVo> dataPage = new PageInfo<CoverAuditVo>(request);
			PageInfo<CoverAuditVo> datas = userTradeCoverService.queryUserTradeCoverVoList(dataPage, connVo);
			if (connVo.isNotExcel(datas.getPageResults(), resp, "补仓审核方案记录.xls") ) {
				grid.add(datas.getPageResults());
				grid.setTotal(datas.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 补仓审核
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult audit(HttpServletRequest request,Integer status,String id,HttpServletResponse resp) throws Exception {
		JsonResult result = new JsonResult(true);
		if(StringUtil.isBlank(id) || status == null || !(status == Constants.coverStatus.AUDITPASS || status == Constants.coverStatus.AUDITUNPASS)){
			result.setSuccess(false);
			result.setMessage(MessageUtils.message("no.cover.data"));
			return result;
		}
		userTradeCoverService.updateUserTradeCover(id,(short)status.intValue());
		result.setMessage(MessageUtils.message("submit.cover.audit.succee"));
		return result;
	}
	
}
