package com.tzdr.cms.hkstock.controller.endtrade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.hkstock.service.HkEndTradeService;
import com.tzdr.business.hkstock.service.HkTradeCalendarService;
import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.hkstock.entity.HkUserTradeExtend;
import com.tzdr.domain.hkstock.vo.HkEndTradeVo;

/**
 * 
 * 
 * <p>终结方案审核</p>
 * @author WangPinQun
 * @see
 * @version 2.0
 * 2015年10月20日上午9:28:08
 */
@Controller
@RequestMapping("/admin/hkstock/endtrade")
public class EndTradeController  extends BaseCmsController<HkUserTradeExtend> {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(EndTradeController.class);

	@Autowired
	private HkEndTradeService  hkEndTradeService;
	
	@Autowired
	private HkUserTradeService hkUserTradeService;
	
	@Autowired
	private HkTradeCalendarService hkTradeCalendarService;

	@Override
	public BaseService<HkUserTradeExtend> getBaseService() {
		return hkEndTradeService;
	}

	public EndTradeController() {
		setResourceIdentity("sys:riskmanager:hkendtrade");
	}

	@RequestMapping(value = "/list")
	public String listReport(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return HkViewConstants.EndTradeViewJsp.LIST_VIEW;
	}

	/**
	 * 终结方案列表（待审核列表【审1】、待审核列表【审2】、审核记录）
	 * @param easyUiPage
	 * @param model
	 * @param response
	 * @param request
	 * @param type   类型  0：待审核列表【审1】   1：待审核列表【审2】   2：审核记录
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,
			HttpServletRequest request,@RequestParam(value="type") int type) throws Exception {

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
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);

		if(searchParams != null && searchParams.containsKey("EQ_auditEndStatus") && "-1".equals(searchParams.get("EQ_auditEndStatus").toString())){
			searchParams.remove("EQ_auditEndStatus");
		}
		
		String sort =  "endSubTime";
		String order = EasyuiUtil.DESC;
		
		if(type == 0){
			sort = "endSubTime";
			order = EasyuiUtil.ASC;
		}else if(type == 1){
			sort = "endAuditFirsteTime";
			order = EasyuiUtil.ASC;
		}
		
		//排序设置
		if (StringUtil.isBlank(easyUiPage.getSort()) || StringUtil.isBlank(easyUiPage.getOrder())){
			easyUiPage.setSort(sort);
			easyUiPage.setOrder(order);
		}
		
		
		//查询数据
		PageInfo<Object> pageInfo = hkEndTradeService.queryHkEndTradeVo(easyUiPage, searchParams, type);
		
		if(type == 1 && pageInfo != null && pageInfo.getPageResults() != null && !pageInfo.getPageResults().isEmpty()){
			List<Object> objectList = pageInfo.getPageResults();
			List<Object> objects = new ArrayList<Object>();
			for (Object object : objectList) {
				HkEndTradeVo hkEndTradeVo = (HkEndTradeVo)object;
				String startDay = Dates.parseBigInteger2Date(
						hkEndTradeVo.getEndAuditFirsteTime(), Dates.CHINESE_DATE_FORMAT_LONG);
				String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
				hkEndTradeVo.setSubmitDays(hkTradeCalendarService.getTradeDays(startDay, today));
				objects.add(hkEndTradeVo);
			}
			pageInfo.setPageResults(objects);
		}
		
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"已审核记录.xls")) {
			return new EasyUiPageData(pageInfo);
		}

		return new EasyUiPageData(pageInfo);
	}
	
	/**
	 * 终结方案审核通过，包括初、复审以tabType为标识【tabType 1:初审  2:复审】
	 * @param request
	 * @param resp
	 */
	@RequestMapping("/endTradeAuditPass")
	@ResponseBody
	public JsonResult updateEndPlanFail(HttpServletRequest request,HttpServletResponse resp) {
		
		ConnditionVo connVo = new ConnditionVo(request);
		String groupId = connVo.getValueStr("groupId"); //获取方案组合号
		String amount = connVo.getValueStr("amount");   //获取方案结算金额
		String tabType = connVo.getValueStr("tabType"); //获取审核类型
		if (StringUtil.isBlank(groupId)){ // 方案组合号
			 return new JsonResult(false,"组合ID不能为空，执行失败!");
		}else if(StringUtil.isBlank(amount)){
			return new JsonResult(false,"结算金额不能为空，执行失败!");
		}else if(StringUtil.isBlank(tabType)){
			return new JsonResult(false,"审核类型不能为空，执行失败!");
		}
		
		BigDecimal amountValue = new BigDecimal(amount);
		
		int tabTypeInt = Integer.valueOf(tabType);
		
		if(tabTypeInt != 1 && tabTypeInt !=2){
			return new JsonResult(false,"审核类型错误，执行失败!");
		}
		
		if(tabTypeInt == 1){  //初审
			hkEndTradeService.updateEndTradePassFirste(groupId, amountValue);
		}else{  //复审
			hkEndTradeService.updateEndTradePassRecheck(groupId, amountValue);
		}
		return new JsonResult("审核成功");
	}
	
	/**
	 * 终结方案审核不通过，包括初、复审以tabType为标识【tabType 1:初审  2:复审】
	 * @param request
	 * @param resp
	 */
	@RequestMapping("/endTradeAuditNoPass")
	@ResponseBody
	public JsonResult updateEndPlanSuccessful(HttpServletRequest request,HttpServletResponse resp) {
		ConnditionVo connVo = new ConnditionVo(request);

		String groupId = connVo.getValueStr("groupId"); //获取方案组合号
		String failCause = connVo.getValueStr("failCause");   //获取方案结算金额
		String tabType = connVo.getValueStr("tabType"); //获取审核类型
		if (StringUtil.isBlank(groupId)){ // 方案组合号
			 return new JsonResult(false,"组合ID不能为空，执行失败!");
		}else if(StringUtil.isBlank(failCause)){
			return new JsonResult(false,"审核不通过原因不能为空，执行失败!");
		}else if(StringUtil.isBlank(tabType)){
			return new JsonResult(false,"审核类型不能为空，执行失败!");
		}
		
		int tabTypeInt = Integer.valueOf(tabType);
		
		if(tabTypeInt != 1 && tabTypeInt !=2){
			return new JsonResult(false,"审核类型错误，执行失败!");
		}
		
		//终结方案审核不通过
		hkEndTradeService.updateEndTradeNoPass(groupId, failCause, tabTypeInt);
		
		return new JsonResult("审核成功");
	}
}
