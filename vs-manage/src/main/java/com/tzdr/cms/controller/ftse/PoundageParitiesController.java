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

import com.tzdr.business.service.userTrade.FPoundageParitiesService;
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
import com.tzdr.domain.vo.ftse.FSimpleParitiesVo;
import com.tzdr.domain.web.entity.FPoundageParities;
import jodd.util.StringUtil;
/**
 * @author gc
 * @version 创建时间：2017年2月24日 
 * 计算手续费汇率维护controller
 */
@Controller
@RequestMapping("/admin/poundageParities")
public class PoundageParitiesController extends BaseCmsController<FPoundageParities> {
	private static Logger log = LoggerFactory.getLogger(PoundageParitiesController.class);
	@Autowired
	private FPoundageParitiesService poundageParitiesService;
	@Override
	public BaseService<FPoundageParities> getBaseService() {
		return poundageParitiesService;
	}
	
	public PoundageParitiesController() {
		setResourceIdentity("sys:finance:parities");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		log.info("进入每日汇率维护页面.....");
		return ViewConstants.TradeDayViewJsp.PARITIES_LIST_VIEW;
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, String id) throws Exception 
	{	
		try {
			if (!StringUtil.isBlank(id)){
				FPoundageParities poundageParities = poundageParitiesService.get(id);
				switch (poundageParities.getType()) {
				case 1:poundageParities.setTypeName("美元");break;
				case 2:poundageParities.setTypeName("港元");break;
				case 3:poundageParities.setTypeName("欧元");break;
				case 4:poundageParities.setTypeName("日元");break;
				case 5:poundageParities.setTypeName("人民币");break;
				default:break;
				}
				request.setAttribute("poundageParities", poundageParities);
			}
			return  ViewConstants.TradeDayViewJsp.POUN_PARITIES_EDIT_VIEW;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("页面-->新增/修改，汇率记录。异常：",e);
			return ViewConstants.ERROR_VIEW;
		}
	}
	
	/**
	 *  新增/修改，汇率记录
	 * @param request
	 * @param year
	 * @return
	 */
	@RequestMapping(value="createPoundageParities")
	@ResponseBody
	public JsonResult createPoundageParities(HttpServletRequest request,FSimpleParitiesVo simpleParitiesvo){
		try {
			if(simpleParitiesvo.getType()==null || simpleParitiesvo.getParities()==null){
				return new JsonResult(false,"请完整填写汇率信息！");
			}
			
			return poundageParitiesService.createParities(simpleParitiesvo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("新增/修改，汇率记录。异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	
	@RequestMapping(value = "/getPoundageDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object getPoundageDatas(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,HttpServletRequest request) throws Exception{
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
			
			//排序设置
			if (StringUtil.isBlank(easyUiPage.getSort())){
				easyUiPage.setSort("createTime");
				easyUiPage.setOrder(EasyuiUtil.DESC);
			}
			searchParams.put("EQ_deleted", false);
			//查询数据
			PageInfo<Object> pageInfo =poundageParitiesService.queryParitiesDatas(easyUiPage, searchParams);
			
			return new EasyUiPageData<Object>(pageInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询数据记录。异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	
}
