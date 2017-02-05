package com.tzdr.cms.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
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

import com.tzdr.business.service.fundconfig.FundConfigService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
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
import com.tzdr.domain.vo.WellGoldHandVo;
import com.tzdr.domain.web.entity.HandTrade;

/**
 * 涌金版手工开户 待审核处理
 * @author zhouchen
 * 2015年4月27日 上午11:07:23
 */
@Controller
@RequestMapping("/admin/wellGold")
public class WellGoldHandController extends BaseCmsController<HandTrade> {
	private static Logger log = LoggerFactory.getLogger(WellGoldHandController.class);
	
	@Autowired
	private HandTradeService  handTradeService;
	
	@Autowired
	private  WUserService wUserService;
	
	@Autowired
	private  SecurityInfoService securityInfoService;
	
	
	@Autowired
	private FundConfigService fundConfigService;
	
	
	@Override
	public BaseService<HandTrade> getBaseService() {
		return handTradeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:wellGold");
	}
	
	/**
	 * 进入手工配资页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public  String  drawlist(HttpServletRequest request){
		return ViewConstants.MatchFundsViewJsp.WELLGOLD_LIST_VIEW;
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam(value="tradeId") String tradeId,@RequestParam(value="activityType") String activityType) throws Exception 
	{
		request.setAttribute("tradeId",tradeId);
		request.setAttribute("activityType",activityType);
		request.setAttribute("handTrade",handTradeService.findByTradeId(tradeId));
		return ViewConstants.MatchFundsViewJsp.WELLGOLD_EDIT_VIEW;

	}
	
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,
			HttpServletRequest request,@RequestParam(value="type") int type) throws Exception{
		
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
		
		// add by wuchaoliang 20150906 查询时，选择账户类型则默认该账户已经审核通过；在未审核、审核不通过的情况下账户是未分配账户类型的
		if(searchParams.containsKey("EQ_feeType") && searchParams.get("EQ_feeType")!=null
				&& !"".equals(searchParams.get("EQ_feeType").toString())){
			searchParams.put("EQ_auditStatus", "1");
		}
		//排序设置
		if (StringUtil.isBlank(easyUiPage.getSort()) && type != 1){
			easyUiPage.setSort("createTime");
			easyUiPage.setOrder(EasyuiUtil.ASC);
		}else if(StringUtil.isBlank(easyUiPage.getSort()) && type == 1){
			easyUiPage.setSort("auditTime");
			easyUiPage.setOrder(EasyuiUtil.DESC);
		}
		
		if(type == 1){ 
			String gte_auditTime = searchParams.containsKey("date_GTE_auditTime") && searchParams.get("date_GTE_auditTime")!=null ? searchParams.get("date_GTE_auditTime").toString() : null;
			
			String lte_auditTime = searchParams.containsKey("date_LTE_auditTime") && searchParams.get("date_LTE_auditTime")!=null ? searchParams.get("date_LTE_auditTime").toString() : null;
			String nowTimeStr = Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LINE);
			if(StringUtil.isBlank(gte_auditTime) && StringUtil.isBlank(lte_auditTime)){
				searchParams.put("date_GTE_auditTime", nowTimeStr);
				searchParams.put("date_LTE_auditTime", nowTimeStr);
			}
		}
		
		//查询数据
		PageInfo<Object> pageInfo =handTradeService.queryWellGoldDatas(easyUiPage, searchParams,type);
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"开户审核记录.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		
		return new EasyUiPageData(pageInfo);
	}
	

	/**
	 * 审核通过
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/notPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  notPass(HttpServletRequest  request,@RequestParam("tradeId") String tradeId) throws Exception{
		
		HandTrade handTrade = handTradeService.findByTradeId(tradeId);
		if (ObjectUtil.equals(handTrade, null)) {
			return new JsonResult(false,"审核失败，未找到该记录！");
		}
		
		if(handTrade.getAuditStatus() != 0){
			return new JsonResult(false,"审核失败，该记录已经审核过，不能重复审核！");
		}
		handTradeService.auditWellGoldTrade(tradeId,false,null);
		return new JsonResult("操作成功！");
	}
	
	
	/**
	 * 审核通过 保存信息
	 * @param request
	 * @param handTradeVo
	 * @return
	 */
	@RequestMapping(value = "/auditPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  auditPass(HttpServletRequest  request,@RequestParam("tradeId") String tradeId,WellGoldHandVo wellGoldHandVo) throws Exception{
			
			if (StringUtil.isBlank(wellGoldHandVo.getParentAccountId())// 母账户
					|| ObjectUtil.equals(wellGoldHandVo.getAccountGenre(),null) //母帐号对应交易通道类型。0：钱江版，1：涌金版，2：同花顺
					|| StringUtil.isBlank(wellGoldHandVo.getAccount()) // 账户名称
					|| StringUtil.isBlank(wellGoldHandVo.getAccountName())// 交易账户号
					|| StringUtil.isBlank(wellGoldHandVo.getPassword())){// 交易密码
				 return new JsonResult(false,"请将账户信息填写完整！");
			}

			HandTrade handTrade = handTradeService.findByTradeId(tradeId);
			if (ObjectUtil.equals(handTrade, null)) {
				return new JsonResult(false,"审核失败，未找到该记录！");
			}
			
			if(handTrade.getAuditStatus() != 0){
				return new JsonResult(false,"审核失败，该记录已经审核过，不能重复审核！");
			}
			
			handTradeService.auditWellGoldTrade(tradeId,true, wellGoldHandVo);
			return new JsonResult("审核成功！");
	}
	
}
