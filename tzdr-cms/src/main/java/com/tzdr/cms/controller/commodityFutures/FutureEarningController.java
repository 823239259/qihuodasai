package com.tzdr.cms.controller.commodityFutures;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;

/**
 * 资金管理——商品期货收益报表控制层
 * @Description: 
 * @author liuhaichuan
 * @date 2015年9月24日
 *
 */
@Controller
@RequestMapping("/admin/commodityFutureEarning")
public class FutureEarningController extends BaseCmsController<FSimpleFtseUserTrade> {
	
	@Autowired
	private FSimpleProductUserTradeService fSimpleUserTradeService;

	@Override
	public BaseService<FSimpleFtseUserTrade> getBaseService() {
		// TODO Auto-generated method stub
		return fSimpleUserTradeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:accountant:commodityFutureEarning");
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
		return ViewConstants.CommodityFutureEarningJsp.LIST_VIEW;
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
	public Object getData(EasyUiPageInfo easyUiPage, HttpServletRequest req, HttpServletResponse resp)
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

		PageInfo<Object> pageInfo = fSimpleUserTradeService.getEarningData(easyUiPage, searchParams);
		if (connVo.isNotExcel(pageInfo.getPageResults(), resp, "商品期货收益报表.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}


}
