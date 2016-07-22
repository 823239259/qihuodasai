package com.tzdr.cms.controller.hsi;

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

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.userTrade.FSimpleConfigService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.ftse.FSimpleConfigVo;
import com.tzdr.domain.web.entity.FSimpleConfig;

import jodd.util.StringUtil;

/**
 * 
 * @author GuoXingyou
 * <p>小恒指方案参数设置
 * 2016年6月14日 10:03
 */
@Controller
@RequestMapping("/admin/lhsiSetParameter")
public class LhsiSetParameterController extends BaseCmsController<FSimpleConfig> {
	private static Logger log = LoggerFactory.getLogger(LhsiSetParameterController.class);
	
	@Autowired
	private FSimpleConfigService simpleConfigService;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public BaseService<FSimpleConfig> getBaseService() {
		return simpleConfigService;
	}
	
	public LhsiSetParameterController() {
		setResourceIdentity("sys:settingParams:lhsiSetParameter");
	}

	/**
	 * 参数页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		log.info("小恒指方案参数设置.....");
		return ViewConstants.HSIJsp.PARAMETER_LIST_VIEW;
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
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
			
			//获取模糊搜索参数
			Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
			
			//查询数据
			PageInfo<Object> pageInfo = this.simpleConfigService.getPageDatas(easyUiPage, searchParams,9);//小恒指type = 9
			
			return new EasyUiPageData<Object>(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("恒生指数设置查询数据异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	
	/**
	 * 新增或跟新方案配资
	 * @param response
	 * @param request
	 * @param simpleConfig
	 * @return
	 */
	@RequestMapping(value="saveOrUpdateConfig")
	@ResponseBody
	public JsonResult saveOrUpdateConfig(HttpServletResponse response,HttpServletRequest request,FSimpleConfigVo simpleConfig){
		try {
			if(StringUtil.isBlank(simpleConfig.getTranLever()) || 
					simpleConfig.getTranFees()==null ||
					simpleConfig.getFeeManage()==null||
					simpleConfig.getTraderBond()==null||
					simpleConfig.getTraderMoney()==null||
					simpleConfig.getLineLoss()==null || 
					simpleConfig.getGoldenMoney()==null){
				return new JsonResult(false,"请完整填写参数后，提交数据！");
			}
			return this.simpleConfigService.saveOrUpdateConfig(simpleConfig,9);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("新增方案配置，或者更新方案配置：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	/**
	 * 删除方案配置
	 * @param response
	 * @param request
	 * @param simpleConfig
	 * @return
	 */
	@RequestMapping(value="deleteConfig")
	@ResponseBody
	public JsonResult deleteConfig(HttpServletResponse response,HttpServletRequest request,FSimpleConfigVo simpleConfig){
		try {
			if(StringUtil.isBlank(simpleConfig.getId())){
				return new JsonResult(false,"请先选择你要删除的数据！");
			}
			return this.simpleConfigService.deleteConfig(simpleConfig);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除一条方案配置信息：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
}
