package com.tzdr.cms.controller.ftse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.userTrade.FHandleFtseUserTradeService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
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
import com.tzdr.domain.web.entity.FHandleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;

/**
 * <p>@author wuchaolinag
 * <p>date:2015-09-25
 * <p>A50收益报表
 *
 */
@Controller
@RequestMapping("/admin/profitReportA50")
public class ProfitReportA50Controller extends BaseCmsController<FSimpleFtseUserTrade> {
	private static Logger log = LoggerFactory.getLogger(ProfitReportA50Controller.class);

	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;

	@Override
	public BaseService<FSimpleFtseUserTrade> getBaseService() {
		return fSimpleFtseUserTradeService;
	}
	public ProfitReportA50Controller() {
		setResourceIdentity("sys:accountant:profitReportA50");
	}
	
	/**
	 * 显示列表列表
	 * @param request
	 * @throws T2SDKException 
	 */
	@RequestMapping(value="list")
	public String marginRemindList(HttpServletRequest request){
		log.info("A50收益报表显示列表也没.......");
		return ViewConstants.WithdrawalViewJsp.PROFIT_REPORT_A50_LIST_VIEW;
	}
	/**
	 * 获取数据
	 * @param easyUiPage
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object getDatas(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,HttpServletRequest request) throws Exception{
		try {
			//判断是否具有查看权限
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
			
			//查询数据
			PageInfo<Object> pageInfo =fSimpleFtseUserTradeService.queryPageDatas(easyUiPage, searchParams,0);
			String fileName = "富时A50收益报表.xls";
			if (connVo.isNotExcel(pageInfo.getPageResults(), response,fileName)) {
				return new EasyUiPageData<Object>(pageInfo);
			}
			
			return new EasyUiPageData<Object>(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("A50收益报表查询数据异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
}