package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.spot.SpotBookingService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.SpotBooking;

/**
 * 现货预约
 * @ClassName SpotBookingController
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
@Controller
@RequestMapping("/admin/spotBooking")
public class SpotBookingController extends BaseCmsController<SpotBooking> {
//	private static Logger logger = LoggerFactory.getLogger(SpotBookingController.class);
	@Autowired
	private SpotBookingService spotBookingService;

	@Override
	public BaseService<SpotBooking> getBaseService() {
		return spotBookingService;
	}
	
	public SpotBookingController() {
		super.setResourceIdentity("sys:operationalConfig:spot");
	}

	/**
	 * to 现货预约列表
	 * @MethodName list
	 * @author L.Y
	 * @date 2015年10月9日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public String list() throws Exception {
		return ViewConstants.SpotJsp.LIST_VIEW;
	}
	
	/**
	 * 获取列表页数据
	 * @MethodName getData
	 * @author L.Y
	 * @date 2015年10月8日
	 * @param easyUiPage easyUI对象(分页等参数)
	 * @param req
	 * @param resp
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getData")
	@ResponseBody
	public Object getData(EasyUiPageInfo easyUiPage, HttpServletRequest req,
			HttpServletResponse resp) {
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
		Map<String, Object> searchParams = EasyuiUtil
				.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		
		easyUiPage.setSort("createTime");
		easyUiPage.setOrder("DESC");
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);

		PageInfo<Object> pageInfo = spotBookingService.getData(
				easyUiPage, searchParams);

		if (!ObjectUtil.equals(null, pageInfo) 
				&& connVo.isNotExcel(pageInfo.getPageResults(), resp, "现货开户报名名单.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return null;
	}
}