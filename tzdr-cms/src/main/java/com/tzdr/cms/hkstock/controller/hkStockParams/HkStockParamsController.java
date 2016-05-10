package com.tzdr.cms.hkstock.controller.hkStockParams;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.hkstock.service.HkStockParamsService;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.hkstock.entity.HkStockParams;


import jodd.util.StringUtil;

/**
 * 港股操盘参数维护
 * 
 * @Description:
 * @author liuhaichuan
 * @date 2015年10月19日
 *
 */
@Controller
@RequestMapping("/admin/hkstock/hkStockParams")
public class HkStockParamsController extends BaseCmsController<HkStockParams> {

	private static Logger logger = LoggerFactory.getLogger(HkStockParamsController.class);

	@Autowired
	private HkStockParamsService hkStockParamsService;
	
	@Autowired
	private AuthService authService;

	@Override
	public BaseService<HkStockParams> getBaseService() {
		// TODO Auto-generated method stub
		return hkStockParamsService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:settingParams:hkStockParams");
	}

	/**
	 * 跳转到参数页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		HkStockParams hkStockParams = new HkStockParams();
		List<HkStockParams> list = hkStockParamsService.getAll();
		if (list != null && list.size() > 0) {
			hkStockParams = list.get(0);
		}
		request.setAttribute("hkStockParams", hkStockParams);
		return HkViewConstants.HkStockParamsJsp.LIST_VIEW;
	}

	/**
	 * 修改参数
	 * @param hkStockParams
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateParams", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updateParams(HkStockParams hkStockParams) throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasUpdatePermission();
		}
		JsonResult result = new JsonResult(false);
		try {
			if (checkData(hkStockParams)) {
				User loginUser=authService.getCurrentUser();
				hkStockParams.setUpdateUserId(loginUser.getId());
				hkStockParams.setUpdateUser(loginUser.getRealname());
				hkStockParams.setUpdateTime(Dates.getCurrentLongDate());
				hkStockParamsService.update(hkStockParams);
				result.setSuccess(true);
				result.setMessage("操作成功");
			} else {
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
	 * 检测提交的数据
	 * @param hkStockParams
	 * @return
	 */
	private boolean checkData(HkStockParams hkStockParams) {
		if(hkStockParams==null){
			return false;
		}
		//最小总操盘资金
		if(hkStockParams.getMinTotalMoney()<0){
			return false;
		}
		//最大总操盘资金
		if(hkStockParams.getMaxTotalMoney()<0){
			return false;
		}
		//推荐操盘金额
		if(StringUtil.isBlank(hkStockParams.getRecommendHoldMoney())){
			return false;
		}
		//港股配资倍数
		if(StringUtil.isBlank(hkStockParams.getLeverConfig())){
			return false;
		}
		//警戒线风控系数 
		if(StringUtil.isBlank(hkStockParams.getWarningCoefficient())){
			return false;
		}
		// 平仓线风控系数
		if(StringUtil.isBlank(hkStockParams.getOpenCoefficient())){
			return false;
		}
		//保证金计算系数
		if(hkStockParams.getBailCoefficient()<0){
			return false;
		}
		// 最小超盘天数
		if(hkStockParams.getMinHoldDays()<0){
			return false;
		}
		// 最大超盘天数
		if(hkStockParams.getMaxHoldDays()<0){
			return false;
		}
		// 推荐操盘天数
		if(StringUtil.isBlank(hkStockParams.getRecommendHoldDays())){
			return false;
		}	
		// 港股利息系数
		if(hkStockParams.getInterestCoefficient()<0){
			return false;
		}
		// 港股管理费系数
		if(hkStockParams.getManageFeeCoefficient()<0){
			return false;
		}
		return true;
	}
}
