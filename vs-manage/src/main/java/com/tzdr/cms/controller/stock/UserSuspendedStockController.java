package com.tzdr.cms.controller.stock;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.stock.StockService;
import com.tzdr.business.service.stock.UserCombostockService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.Stock;

/**
 * 拥有停牌股的 用户信息
 * @zhouchen
 * 2015年2月12日
 */
@Controller
@RequestMapping("/admin/stock/userSuspended")
public class UserSuspendedStockController extends BaseCmsController<Stock> {
	 
	private static Logger log = LoggerFactory.getLogger(UserSuspendedStockController.class);
	@Autowired
	private UserCombostockService combostockService;

	@Autowired
	private StockService stockService;
	
	@Override
	public BaseService<Stock> getBaseService() {
		return stockService;
	}

	public UserSuspendedStockController() {
		setResourceIdentity("sys:multipleQuery:userSuspended");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		return ViewConstants.StockViewJsp.USER_SUSPENDED_VIEW;
	}
	
	
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
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
		EasyuiUtil.getMultilistSortMap(easyUiPage);
							
		PageInfo<Object> pageInfo = combostockService.queryDatas(easyUiPage,searchParams);
		return new EasyUiPageData(pageInfo);
	}
	
	
}
