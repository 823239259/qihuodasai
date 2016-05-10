package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tzdr.business.service.fundconfig.FundConfigService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.FundConfig;

/**
 * 配资倍数和金额 Controller
 * @ClassName FundConfigController
 * @author L.Y
 * @email meiluohai@163.com
 * @date 2015年4月27日
 */
@Controller
@RequestMapping("/admin/fundConfig")
public class FundConfigController extends BaseCmsController<FundConfig> {
//	private static Logger logger = LoggerFactory.getLogger(FundConfigController.class);
	@Autowired
	private FundConfigService fundConfigService;

	@Override
	public BaseService<FundConfig> getBaseService() {
		return fundConfigService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:settingParams:fundConfig");
	}

	/**
	 * to 配资倍数和金额 页
	 * @MethodName list
	 * @author L.Y
	 * @return
	 * @throws Exception
	 * @date 2015年4月27日
	 * @return String
	 */
	@RequestMapping(value="/list")
	public String list() throws Exception {
		return ViewConstants.FundConfigJsp.LIST_VIEW;
	}
	
	/**
	 * 添加或修改
	 * @MethodName edit
	 * @author L.Y
	 * @param request
	 * @param fromType 表单类型
	 * @param id
	 * @return
	 * @throws Exception
	 * @date 2015年4月28日
	 * @return String
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, @RequestParam("fromType") String fromType,
			@RequestParam(value="id", required=false) String id) throws Exception 
	{
		request.setAttribute("fromType", fromType);
		if (StringUtil.equals(fromType, Constants.ADD)) { //添加
			return ViewConstants.FundConfigJsp.EDIT_VIEW;
		}
		
		if (StringUtil.equals(fromType, Constants.EDIT)) { //更新
			FundConfig fundConfig = fundConfigService.get(id);
			if(ObjectUtil.equals(fundConfig, null)) {
				return ViewConstants.ERROR_VIEW;
			}
			request.setAttribute("dataMap", fundConfig);
			return ViewConstants.FundConfigJsp.EDIT_VIEW;
		}
		
		return ViewConstants.ERROR_VIEW;
	}
}
