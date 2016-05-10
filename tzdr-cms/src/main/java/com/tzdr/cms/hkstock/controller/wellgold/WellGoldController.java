package com.tzdr.cms.hkstock.controller.wellgold;

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

import com.tzdr.business.hkstock.service.HkUserTradeExtendService;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.hkstock.entity.HkUserTradeExtend;
import com.tzdr.domain.hkstock.vo.HkUserTradeExtendVo;

/**
 * 港股开户管理
 * @author WangPinQun
 * 2015年10月19日 上午11:07:23
 */
@Controller
@RequestMapping("/admin/hkstock/wellgold")
public class WellGoldController extends BaseCmsController<HkUserTradeExtend> {
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(WellGoldController.class);
	
	@Autowired
	private HkUserTradeExtendService  hkUserTradeExtendService;
	
	@Override
	public BaseService<HkUserTradeExtend> getBaseService() {
		return hkUserTradeExtendService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:hkwellgold");
	}
	
	/**
	 * 进入手工配资页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public  String  drawlist(HttpServletRequest request){
		return HkViewConstants.WellGoldJsp.LIST_VIEW;
	}
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @param type
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		
		String auditStatus = searchParams == null || !searchParams.containsKey("EQ_auditStatus") ? null : (searchParams.get("EQ_auditStatus") == null ? null : searchParams.get("EQ_auditStatus").toString());
		
		if(type == 0 && StringUtil.isBlank(auditStatus)){
			searchParams.put("EQ_auditStatus", 0);
		}else if(type == 1 && StringUtil.isBlank(auditStatus)){
			if(searchParams != null && searchParams.containsKey("EQ_auditStatus")){
				searchParams.remove("EQ_auditStatus");
			}
			searchParams.put("IN_auditStatus", new Object[]{0,1,2});
		}
		
		//查询数据
		PageInfo<Object> pageInfo = hkUserTradeExtendService.queryWellGoldDatas(easyUiPage, searchParams);
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"已审核记录.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		
		return new EasyUiPageData(pageInfo);
	}
	
	/**
	 * 访问开户审核编辑页面
	 * @param request
	 * @param tradeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam(value="tradeId") String tradeId) throws Exception 
	{
		request.setAttribute("tradeId",tradeId);		
		request.setAttribute("handTrade",hkUserTradeExtendService.findByTradeId(tradeId));
		return HkViewConstants.WellGoldJsp.WELLGOLD_EDIT_VIEW;
	}
	
	/**
	 * 审核通过
	 * @param request
	 * @param tradeId
	 * @param handTradeVo
	 * @return
	 */
	@RequestMapping(value = "/auditpass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  auditPass(HttpServletRequest request,@RequestParam("tradeId") String tradeId,HkUserTradeExtendVo hkUserTradeExtendVo){
			
			if (StringUtil.isBlank(hkUserTradeExtendVo.getParentAccountId())// 母账户
					|| StringUtil.isBlank(hkUserTradeExtendVo.getAccountNo()) // 账户名称
					|| StringUtil.isBlank(hkUserTradeExtendVo.getAccountName())// 交易账户号
					|| StringUtil.isBlank(hkUserTradeExtendVo.getPassword())){// 交易密码
				 return new JsonResult(false,"请将账户信息填写完整！");
			}

			HkUserTradeExtend hkUserTradeExtend = hkUserTradeExtendService.findByTradeId(tradeId);
			if (ObjectUtil.equals(hkUserTradeExtend, null)) {
				return new JsonResult(false,"审核失败，未找到该记录！");
			}
			
			if(hkUserTradeExtend.getAuditStatus() != 0){
				return new JsonResult(false,"审核失败，该记录已经审核过，不能重复审核！");
			}
			
			hkUserTradeExtendService.auditWellGoldTrade(tradeId,true, hkUserTradeExtendVo,hkUserTradeExtend);
			return new JsonResult("审核成功！");
	}
	
	/**
	 * 审核通过
	 * @param request
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "/notpass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  notPass(HttpServletRequest request,@RequestParam("tradeId") String tradeId){
		
		HkUserTradeExtend hkUserTradeExtend = hkUserTradeExtendService.findByTradeId(tradeId);
		if (ObjectUtil.equals(hkUserTradeExtend, null)) {
			return new JsonResult(false,"审核失败，未找到该记录！");
		}
		
		if(hkUserTradeExtend.getAuditStatus() != 0){
			return new JsonResult(false,"审核失败，该记录已经审核过，不能重复审核！");
		}
		hkUserTradeExtendService.auditWellGoldTrade(tradeId,false,null,hkUserTradeExtend);
		return new JsonResult("操作成功！");
	}
}
