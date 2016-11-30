package com.tzdr.cms.controller.internationFuture;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.userTrade.FinternationFutureAppendLevelMoneyService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.FileProcessed;
import com.tzdr.common.utils.ReadExclPOI;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.TradeExclDetailVo;
import com.tzdr.domain.vo.ftse.FSimpleFtseVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;


/**
 * 国际期货 A50、原油、恒指、小恒指 合并处理
 * @zhouchen
 * 2015年11月20日
 */
@Controller
@RequestMapping("admin/internation/future")
public class FinternationFutureController extends BaseCmsController<FSimpleFtseUserTrade> {
	private static Logger log = LoggerFactory.getLogger(FinternationFutureController.class);
	
	@Autowired
	private FSimpleFtseUserTradeService  simpleFtseUserTradeService;
	
	@Autowired
	private FinternationFutureAppendLevelMoneyService  internationFutureAppendMoneyService;
	
	@Override
	public BaseService<FSimpleFtseUserTrade> getBaseService() {
		return simpleFtseUserTradeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:internationFuture");
	}
	
	/**
	 * 恒生指数开户审核列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public  String  list(HttpServletRequest request){
		return ViewConstants.InternationFutureViewJsp.LIST_VIEW;
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
			PageInfo<Object> pageInfo =simpleFtseUserTradeService.queryInternationFutureDatas(easyUiPage, searchParams,type);
			String fileName = type==0?"国际期货方案申请列表.xls":"国际期货方案管理列表.xls";
			if (connVo.isNotExcel(pageInfo.getPageResults(), response,fileName)) {
				return new EasyUiPageData<Object>(pageInfo);
			}
			
			return new EasyUiPageData<Object>(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("恒生指数查询数据异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	

	@RequestMapping(value = "/getAppendMoneyDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object getAppendMoneyDatas(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,HttpServletRequest request) throws Exception{
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
 			if(StringUtil.isNotBlank(easyUiPage.getSort()) && StringUtil.equals(easyUiPage.getSort(),"appendDateValue")){
 				easyUiPage.setSort("appendDate");
			}
 			if(StringUtil.isNotBlank(easyUiPage.getSort()) && StringUtil.equals(easyUiPage.getSort(),"updateTimeValue")){
 				easyUiPage.setSort("updateTime");
			}
 			if(StringUtil.isNotBlank(easyUiPage.getSort()) && StringUtil.equals(easyUiPage.getSort(),"statusValue")){
 				easyUiPage.setSort("status");
			}
			//查询数据
			PageInfo<Object> pageInfo =internationFutureAppendMoneyService.getData(easyUiPage, searchParams);
			if (connVo.isNotExcel(pageInfo.getPageResults(), response,"国际期货补充保证金列表.xls")) {
				return new EasyUiPageData<Object>(pageInfo);
			}
			
			return new EasyUiPageData<Object>(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("国际期货补充保证金列表查询数据异常：",e);
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
			log.error("恒生指数 审核不通过异常：",e);
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
	public JsonResult  auditPass(HttpServletRequest  request,@RequestParam("id") String id,FSimpleFtseUserTrade simpleFtseUserTrade,String type){
		try {
			if(StringUtil.isBlank(id)){
				return new JsonResult(false,"没有找到具体的方案ID号，请重试！");
			}
			if (StringUtil.isBlank(simpleFtseUserTrade.getTranAccount())// 交易账户号
					|| StringUtil.isBlank(simpleFtseUserTrade.getTranPassword())){// 交易密码
				return new JsonResult(false,"请将账户信息填写完整！");
			}
			if("1".equals(type)){
				String message=simpleFtseUserTradeService.passSaveAccount(id);
				if(message!=""){
					return new JsonResult(false,message);
				}
			}
			return simpleFtseUserTradeService.auditPass(id, simpleFtseUserTrade);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("恒生指数审核通过 保存信息异常：",e);
			return new JsonResult(false,"服务器忙！");
		}
	}
	/**
	 * 录入交易信息
	 * @param request
	 * @param resp
	 * @param hsi
	 * @return
	 */
	@RequestMapping(value="input")
	@ResponseBody
	public JsonResult input(HttpServletRequest request,HttpServletResponse resp,FSimpleFtseVo hsi){
		try {
			if(StringUtil.isBlank(hsi.getId())){
				return new JsonResult(false,"没有找到具体的方案ID号，请重试！");
			}
			if (hsi.getTranProfitLoss()==null// 交易盈亏
					|| hsi.getTranActualLever()==null){// 交易手数
				return new JsonResult(false,"交易盈亏、交易手数,填写完整！");
			}
			
			return this.simpleFtseUserTradeService.inputResult(hsi);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("恒生指数录入交易信息异常：",e);
			return new JsonResult(false,"服务器忙!");
		}
	}
	/**
	 * 结算
	 * @param request
	 * @param resp
	 * @param hsi
	 * @return
	 */
	@RequestMapping(value="end")
	@ResponseBody
	public JsonResult end(HttpServletRequest request,HttpServletResponse resp,FSimpleFtseVo internationFuture){
		try {
			if(StringUtil.isBlank(internationFuture.getId())){
				return new JsonResult(false,"没有找到具体的方案ID号，请重试！");
			}
			return this.simpleFtseUserTradeService.settlementA50(internationFuture);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("恒生指数结算异常：",e);
			return new JsonResult(false,"服务器忙!");
		}
	}

	/**
	 * 补充保证金——已处理
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult changeStatus(@RequestParam(value = "id", required = true) String id) throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasAuditPermission();
		}
		JsonResult result = new JsonResult(false);
		try {
			String message=internationFutureAppendMoneyService.changeStatus(id);
			if(message!=""){
				result.setSuccess(false);
				result.setMessage(message);
				return result; 
			}
			result.setSuccess(true);
			result.setMessage("操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.debug(e.getMessage());
			e.printStackTrace();
			result.setMessage("操作失败");
		}
		return result;
	}
	/**
	 * 读取excl的资金明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/importExclDetail",method = RequestMethod.GET)
	public JsonResult importExclDetail(HttpServletRequest request,@RequestParam MultipartFile[] multipartFile){
		
		boolean flag = true;
		JsonResult resultJson = new JsonResult();
		if(multipartFile[0].getSize() > Integer.MAX_VALUE){
			resultJson.setSuccess(false);
			resultJson.setMessage("文件长度过大");
			return resultJson;
		}
		List<TradeExclDetailVo> detailVos = new ArrayList<>();
		try {
			InputStream inputStream = multipartFile[0].getInputStream();
			UUID uuid = UUID.randomUUID();
			String randomUuid = uuid.toString();
			FileProcessed fileProcessed = new FileProcessed();
			String filePath = "C:\\+"+randomUuid+".xlsx";
			fileProcessed.uploadFile(filePath, inputStream);
			ReadExclPOI readExclPOI = new ReadExclPOI();
			detailVos = (List<TradeExclDetailVo>)readExclPOI.readExcl2007(filePath, TradeExclDetailVo.class);
			System.out.println(detailVos);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultJson.setSuccess(flag);
		resultJson.appendData("data",detailVos);
		return resultJson;
	}
}
