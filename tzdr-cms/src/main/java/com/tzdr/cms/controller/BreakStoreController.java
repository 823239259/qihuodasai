package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.breakStore.BreakStoreService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * 查询穿仓相关
 * @ClassName BreakStoreController
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年7月6日
 */
@Controller
@RequestMapping("admin/breakStore")
public class BreakStoreController extends BaseCmsController<UserTrade> {
	
	@Autowired
	private BreakStoreService breakStoreService;
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:breakStore");
	}
	
	/**
	 * to 穿仓列表页
	 * @MethodName list
	 * @author L.Y
	 * @date 2015年7月6日
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list() {
		return ViewConstants.BreakStoreJsp.LIST_VIEW;
	}
	
	/**
	 * 穿仓列表数据
	 * @MethodName easyuiPage
	 * @author L.Y
	 * @date 2015年7月7日
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
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
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request, EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		//查询数据
		PageInfo<Object> pageInfo = breakStoreService.queryListNew(easyUiPage, searchParams);
		
		if (connVo.isNotExcel(pageInfo.getPageResults(), response, "穿仓记录.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		
		return new EasyUiPageData(pageInfo);
	}

	@Override
	public BaseService<UserTrade> getBaseService() {
		return breakStoreService;
	}
}
