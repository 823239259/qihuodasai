package com.tzdr.cms.controller.crude;

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

import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
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
import com.tzdr.domain.vo.ftse.FSimpleFtseVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;

/**
 * * <B>说明: </B>国际原油开户审核
 * @author LiuYang
 * 2015年9月16日 
 */
@Controller
@RequestMapping("admin/crude")
public class FSimpleCrudeController extends BaseCmsController<FSimpleFtseUserTrade> {
	private static Logger log = LoggerFactory.getLogger(FSimpleCrudeController.class);
	
	@Autowired
	private FSimpleFtseUserTradeService  simpleFtseUserTradeService;
	
	
	@Override
	public BaseService<FSimpleFtseUserTrade> getBaseService() {
		return simpleFtseUserTradeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:crude");
	}
	
	/**
	 * 国际原油开户审核列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public  String  list(HttpServletRequest request){
		return ViewConstants.CrudeJsp.TRADE_LIST_VIEW;
	}
	
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,HttpServletRequest request,@RequestParam(value="type") int type) throws Exception{
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
			
			//查询数据
			PageInfo<Object> pageInfo =simpleFtseUserTradeService.queryWellGoldDatas(easyUiPage, searchParams,type,6);
			String fileName = type==0?"国际原油申请列表.xls":"国际原油方案管理.xls";
			if (connVo.isNotExcel(pageInfo.getPageResults(), response,fileName)) {
				return new EasyUiPageData<Object>(pageInfo);
			}
			
			return new EasyUiPageData<Object>(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("国际原油查询数据异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	

	/**
	 * 审核不通过
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/notPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  notPass(HttpServletRequest  request,@RequestParam("id") String id){
		try {
			if(StringUtil.isBlank(id)){
				return new JsonResult(false,"你没有选择需要操作的记录，请选择后重试！");
			}
			return simpleFtseUserTradeService.auditNotPass(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("国际原油 审核不通过异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	
	
	/**
	 * 审核通过 保存信息
	 * @param request
	 * @param handTradeVo
	 * @return
	 */
	@RequestMapping(value = "/auditPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  auditPass(HttpServletRequest  request,@RequestParam("id") String id,FSimpleFtseUserTrade simpleFtseUserTrade){
		try {
			if(StringUtil.isBlank(id)){
				return new JsonResult(false,"没有找到具体的方案ID号，请重试！");
			}
			if (StringUtil.isBlank(simpleFtseUserTrade.getTranAccount())// 交易账户号
					|| StringUtil.isBlank(simpleFtseUserTrade.getTranPassword())){// 交易密码
				return new JsonResult(false,"请将账户信息填写完整！");
			}
			return simpleFtseUserTradeService.auditPass(id, simpleFtseUserTrade);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("国际原油 审核通过 保存信息异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	/**
	 * 录入交易信息
	 * @param request
	 * @param resp
	 * @param crude
	 * @return
	 */
	@RequestMapping(value="input")
	@ResponseBody
	public JsonResult input(HttpServletRequest request,HttpServletResponse resp,FSimpleFtseVo crude){
		try {
			if(StringUtil.isBlank(crude.getId())){
				return new JsonResult(false,"没有找到具体的方案ID号，请重试！");
			}
			if (crude.getTranProfitLoss()==null// 交易盈亏
					|| crude.getTranActualLever()==null){// 交易手数
				return new JsonResult(false,"交易盈亏、交易手数,填写完整！");
			}
			
			return this.simpleFtseUserTradeService.inputResult(crude);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("国际原油录入交易信息异常：",e);
			return new JsonResult(false,"服务器忙!");
		}
	}
	/**
	 * 结算
	 * @param request
	 * @param resp
	 * @param crude
	 * @return
	 */
	@RequestMapping(value="end")
	@ResponseBody
	public JsonResult end(HttpServletRequest request,HttpServletResponse resp,FSimpleFtseVo crude){
		try {
			if(StringUtil.isBlank(crude.getId())){
				return new JsonResult(false,"没有找到具体的方案ID号，请重试！");
			}
			return this.simpleFtseUserTradeService.settlementA50(crude);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("国际原油结算异常：",e);
			return new JsonResult(false,"服务器忙!");
		}
	}
	
}
