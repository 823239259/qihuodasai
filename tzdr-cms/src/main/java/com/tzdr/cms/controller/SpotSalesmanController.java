package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.spot.SpotSalesmanService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.SpotSalesman;

/**
 * 现货销售员
 * @ClassName SpotSalesmanController
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
@Controller
@RequestMapping("/admin/spotSalesman")
public class SpotSalesmanController extends BaseCmsController<SpotSalesman> {
//	private static Logger logger = LoggerFactory.getLogger(SpotSalesmanController.class);
	@Autowired
	private SpotSalesmanService spotSalesmanService;

	@Override
	public BaseService<SpotSalesman> getBaseService() {
		return spotSalesmanService;
	}
	
	public SpotSalesmanController() {
		super.setResourceIdentity("sys:operationalConfig:spot");
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

		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil
				.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);

		PageInfo<Object> pageInfo = spotSalesmanService.getData(
				easyUiPage, searchParams);

		return new EasyUiPageData(pageInfo);
	}
	
	/**
	 * 添加或修改
	 * @MethodName edit
	 * @author L.Y
	 * @param request
	 * @param fromType 表单类型
	 * @param id 记录id
	 * @date 2015年10月8日
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, @RequestParam("fromType") String fromType,
			@RequestParam(value="id", required=false) String id) throws Exception 
	{
		request.setAttribute("fromType", fromType);
		if (StringUtil.equals(fromType, Constants.ADD)) { //添加
			return ViewConstants.SpotJsp.EDIT_VIEW;
		}
		
		if (StringUtil.equals(fromType, Constants.EDIT)) { //更新
			SpotSalesman ss = spotSalesmanService.get(id);
			if(ObjectUtil.equals(ss, null)) {
				return ViewConstants.ERROR_VIEW;
			}
			
			request.setAttribute("dataMap", ss);
			return ViewConstants.SpotJsp.EDIT_VIEW;
		}
		
		return ViewConstants.ERROR_VIEW;
	}
}