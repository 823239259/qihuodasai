package com.tzdr.cms.controller.commodityFutures;

import java.math.BigDecimal;
import java.util.Map;

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
import com.tzdr.business.service.userTrade.FSimpleProductConfigService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.cms.FSimpleConfigVo;
import com.tzdr.domain.web.entity.FSimpleConfig;

import jodd.util.StringUtil;

/**
 * 商品期货参数设置控制层
 * 
 * @Description:
 * @author liuhaichuan
 * @date 2015年9月17日
 *
 */
@Controller
@RequestMapping("/admin/commodityFutureParam")
public class FutureParamController extends BaseCmsController<FSimpleConfig> {

	@Autowired
	private FSimpleProductConfigService fSimpleConfigService;
	
	@Autowired
	private AuthService authService;

	private static Logger logger = LoggerFactory.getLogger(FutureParamController.class);

	@Override
	public BaseService<FSimpleConfig> getBaseService() {
		// TODO Auto-generated method stub
		return fSimpleConfigService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:settingParams:commodityFutureParam");
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
		return ViewConstants.CommodityFutureParamJsp.LIST_VIEW;
	}

	/**
	 * 获取数据
	 * 
	 * @param easyUiPage
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public Object getData(EasyUiPageInfo easyUiPage, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);

		PageInfo<Object> pageInfo = fSimpleConfigService.getData(easyUiPage,
				searchParams);
		
		return new EasyUiPageData<>(pageInfo);
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param username
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/toEdit")
	public String toSuccess(String id, HttpServletRequest req,
			HttpServletResponse resp) {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasUpdatePermission();
		}
		FSimpleConfig fSimpleConfig=fSimpleConfigService.get(id);
		req.setAttribute("fSimpleConfig", fSimpleConfig);
		return ViewConstants.CommodityFutureParamJsp.EDIT_VIEW;
	}
	
	/**
	 * 保存新增的数据
	 * @param fSimpleConfigVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateParam" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updateParam(FSimpleConfigVo fSimpleConfigVo)
			throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasUpdatePermission();
		}
		JsonResult result = new JsonResult(false);
		try {
			if (checkData(fSimpleConfigVo)) {
				FSimpleConfig fSimpleConfig=getByVo(fSimpleConfigVo);
				fSimpleConfigService.update(fSimpleConfig);
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
	 * 验证商品期货参数的数据
	 * @param fSimpleConfig
	 * @return
	 */
	private boolean checkData(FSimpleConfigVo fSimpleConfigVo){
		if(fSimpleConfigVo==null){
			return false;
		}
		//编号
		if(fSimpleConfigVo.getId()==null){
			return false;
		}
//		//手续费
//		if(fSimpleConfigVo.getTranFees()==null){
//			return false;
//		}
		//手续费配资组
		if(StringUtil.isBlank(fSimpleConfigVo.getTranFeesArray())){
			return false;
		}
//		//管理费
//		if(fSimpleConfigVo.getFeeManage()==null){
//			return false;
//		}
		//开仓手数
		if(fSimpleConfigVo.getTranLever()==null){
			return false;
		}
		//单手保证金
		if(fSimpleConfigVo.getTraderBond()==null){
			return false;
		}
		//单手操盘金
		if(fSimpleConfigVo.getTraderMoney()==null){
			return false;
		}
		//单手平仓保留金额
		if(fSimpleConfigVo.getLineLoss()==null){
			return false;
		}
		return true;
	}


	private FSimpleConfig getByVo(FSimpleConfigVo fSimpleConfigVo){
		User loginUser = authService.getCurrentUser();
		FSimpleConfig fSimpleConfig=fSimpleConfigService.get(fSimpleConfigVo.getId());
//		fSimpleConfig.setTranFees(fSimpleConfigVo.getTranFees());
		fSimpleConfig.setTranFeesArray(fSimpleConfigVo.getTranFeesArray());
		//因暂不收管理费，所以现改为0
//		fSimpleConfig.setFeeManage(fSimpleConfigVo.getFeeManage());
		fSimpleConfig.setFeeManage(new BigDecimal("0"));
		fSimpleConfig.setTranLever(fSimpleConfigVo.getTranLever());
		fSimpleConfig.setTraderBond(fSimpleConfigVo.getTraderBond());
		fSimpleConfig.setTraderMoney(fSimpleConfigVo.getTraderMoney());
		fSimpleConfig.setLineLoss(fSimpleConfigVo.getLineLoss());
		fSimpleConfig.setUpdateUserId(loginUser.getId());
		fSimpleConfig.setUpdateUser(loginUser.getRealname());
		fSimpleConfig.setUpdateTime(Dates.getCurrentLongDate());
		return fSimpleConfig;
	}

}
