package com.tzdr.cms.controller.internationFuture;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.tradeDetail.TradeDetailService;
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
import com.tzdr.domain.vo.TradeExclDetailVos;
import com.tzdr.domain.vo.ftse.FSimpleFtseVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.TradeDetail;


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
	
	@Autowired
	private TradeDetailService tradeDetailService;
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
		JsonResult jsonResult  = new JsonResult();
		try {
			if(StringUtil.isBlank(hsi.getId())){
				return new JsonResult(false,"没有找到具体的方案ID号，请重试！");
			}
			if (hsi.getTranProfitLoss()==null// 交易盈亏
					|| hsi.getTranActualLever()==null){// 交易手数
				return new JsonResult(false,"交易盈亏、交易手数,填写完整！");
			}
			jsonResult = this.simpleFtseUserTradeService.inputResult(hsi);
			String tradeDetail = request.getParameter("tradeDetail");
			if(tradeDetail != null && tradeDetail.length() > 0){
				tradeDetailService.doSaveTradeExclDetail(tradeDetail,hsi.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("恒生指数录入交易信息异常：",e);
			return new JsonResult(false,"服务器忙!");
		}
		return jsonResult;
	}
	/**
	 * 拒绝结算
	 * @param request
	 * @param id
	 * @param stateType
	 * @return
	 */
	@RequestMapping(value = "/refuseInput",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult refuseInput(HttpServletRequest request,@RequestParam("id")String id,@RequestParam("stateType")Integer stateType){
		FSimpleFtseUserTrade fSimpleFtseUserTrade = simpleFtseUserTradeService.get(id);
		JsonResult jsonResult = new JsonResult();
		if(fSimpleFtseUserTrade == null){
			jsonResult.setSuccess(false);
			jsonResult.setMessage("没有操盘信息");
		}else{
			fSimpleFtseUserTrade.setStateType(stateType);
			fSimpleFtseUserTrade.setTranProfitLoss(null);
			fSimpleFtseUserTrade.setEndParities(null);
			fSimpleFtseUserTrade.setTranFeesTotal(null);
			fSimpleFtseUserTrade.setDaxtranMinActualLever(null);
			fSimpleFtseUserTrade.setSmallCrudeOilMarketLever(null);
			fSimpleFtseUserTrade.setAmeSilverMarketLever(null);
			fSimpleFtseUserTrade.setAmeCopperMarketLever(null);
			fSimpleFtseUserTrade.setxHStockMarketLever(null);
			fSimpleFtseUserTrade.sethStockMarketLever(null);
			fSimpleFtseUserTrade.setAgTranActualLever(null);
			fSimpleFtseUserTrade.setLhsiTranActualLever(null);
			fSimpleFtseUserTrade.setMdtranActualLever(null);
			fSimpleFtseUserTrade.setNikkeiTranActualLever(null);
			fSimpleFtseUserTrade.setDaxtranActualLever(null);
			fSimpleFtseUserTrade.setMbtranActualLever(null);
			fSimpleFtseUserTrade.setMntranActualLever(null);
			fSimpleFtseUserTrade.setHsiTranActualLever(null);
			fSimpleFtseUserTrade.setCrudeTranActualLever(null);
			fSimpleFtseUserTrade.setTranActualLever(null);
			fSimpleFtseUserTrade.setAppEndTime(null);
			fSimpleFtseUserTrade.setDaxMinTranFees(null);
			fSimpleFtseUserTrade.setSmallCTranFees(null);
			fSimpleFtseUserTrade.setAmeSTranFees(null);
			fSimpleFtseUserTrade.setAmeCTranFees(null);
			fSimpleFtseUserTrade.setxHSTranFees(null);
			fSimpleFtseUserTrade.sethSTranFees(null);
			fSimpleFtseUserTrade.setAgTranFees(null);
			fSimpleFtseUserTrade.setLhsiTranFees(null);
			fSimpleFtseUserTrade.setMdTranFees(null);
			fSimpleFtseUserTrade.setNikkeiTranFees(null);
			fSimpleFtseUserTrade.setDaxTranFees(null);
			fSimpleFtseUserTrade.setMbTranFees(null);
			fSimpleFtseUserTrade.setMnTranFees(null);
			fSimpleFtseUserTrade.setHsiTranFees(null);
			fSimpleFtseUserTrade.setCrudeTranFees(null);
			fSimpleFtseUserTrade.setTranFees(null);
			fSimpleFtseUserTrade.setEndTime(null);
			fSimpleFtseUserTrade.setEndAmountCal(null);
			fSimpleFtseUserTrade.setEndAmount(null);
			simpleFtseUserTradeService.update(fSimpleFtseUserTrade);
			jsonResult.setSuccess(true);
			jsonResult.setMessage("拒绝成功");
		}
		return jsonResult;
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
	 * 根据id获取方案
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFtse",method = RequestMethod.GET)
	public JsonResult getFtse(HttpServletRequest request,@RequestParam("id")String id){
		FSimpleFtseUserTrade fSimpleFtseUserTrade = simpleFtseUserTradeService.get(id);
		List<TradeDetail> tradeDetail = tradeDetailService.doGetByFtseId(id);
		JsonResult resultJson = new JsonResult();
		resultJson.setSuccess(true);
		resultJson.appendData("fste", fSimpleFtseUserTrade);
		resultJson.appendData("tradeDetail", tradeDetail);
		return resultJson;
	}
	/**
	 * 读取excl的资金明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/importExclDetail",method = RequestMethod.POST)
	public JsonResult importExclDetail(HttpServletRequest request,@RequestParam("input_file") MultipartFile multipartFile){
		
		boolean flag = true;
		JsonResult resultJson = new JsonResult();
		if(multipartFile.getSize() > Integer.MAX_VALUE){
			resultJson.setSuccess(false);
			resultJson.setMessage("文件长度过大");
			return resultJson;
		}
		List<TradeExclDetailVos> detailVos = new ArrayList<>();
		Map<String, Double> map = new HashMap<String,Double>();
		InputStream inputStream = null;
		try {
			inputStream = multipartFile.getInputStream();
			UUID uuid = UUID.randomUUID();
			String randomUuid = uuid.toString();
			FileProcessed fileProcessed = new FileProcessed();
			String ctxPath = request.getSession().getServletContext()  
	                .getRealPath("/") ;
	        String filePath = ctxPath + "/template/"+randomUuid+".xlsx";  
			fileProcessed.uploadFile(filePath, inputStream);
			ReadExclPOI readExclPOI = new ReadExclPOI();
			detailVos = (List<TradeExclDetailVos>)readExclPOI.readExcl2007(filePath, TradeExclDetailVos.class);
			File file = new File(filePath);
			file.delete();
			for (int i = 1; i < detailVos.size(); i++) {
				TradeExclDetailVos detailVo = detailVos.get(i);
				String contractNo = detailVo.getCommodityNo();
				if(contractNo == null || contractNo.equalsIgnoreCase("null")){
					continue;
				}
				String buyNum = detailVo.getBuyNum();
				String sellNum = detailVo.getSellNum();
				Double tradeNum = Double.parseDouble(buyNum == null ? "0" : buyNum)+Double.parseDouble(sellNum == null ? "0" : sellNum);
				Double lever = 0.0;
				Double number = 0.0;
				Double tradeNumbDob = tradeNum;
				if(contractNo.startsWith("CL")){
					lever = map.get("crudeTranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("crudeTranActualLever", number);
				}else if(contractNo.startsWith("CN")){
					lever = map.get("tranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("tranActualLever", number);
				}else if(contractNo.startsWith("HSI")){
					lever = map.get("hsiTranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("hsiTranActualLever", number);
				}else if(contractNo.startsWith("YM")){
					lever = map.get("mdtranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("mdtranActualLever", number);
				}else if(contractNo.startsWith("NQ")){
					lever = map.get("mntranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("mntranActualLever", number);
				}else if(contractNo.startsWith("ES")){
					lever = map.get("mbtranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("mbtranActualLever", number);
				}else if(contractNo.startsWith("FDAX")){
					lever = map.get("daxtranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("daxtranActualLever", number);
				}else if(contractNo.startsWith("NK")){
					lever = map.get("nikkeiTranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("nikkeiTranActualLever", number);
				}else if(contractNo.startsWith("MHI")){
					lever = map.get("lhsiTranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("lhsiTranActualLever", number);
				}else if(contractNo.startsWith("GC")){
					lever = map.get("agTranActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("agTranActualLever", number);
				}else if(contractNo.startsWith("HHI")){
					lever = map.get("heStockMarketLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("heStockMarketLever", number);
				}else if(contractNo.startsWith("MCH")){
					lever = map.get("xhStockMarketLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("xhStockMarketLever", number);
				}else if(contractNo.startsWith("HG")){
					lever = map.get("AmeCopperMarketLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("AmeCopperMarketLever", number);
				}else if(contractNo.startsWith("SI")){
					lever = map.get("AmeSilverMarketLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("AmeSilverMarketLever", number);
				}else if(contractNo.startsWith("QM")){
					lever = map.get("smallCrudeOilMarketLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("smallCrudeOilMarketLever", number);
				}else if(contractNo.startsWith("FDXM")){
					lever = map.get("daxtranMinActualLever");
					if(lever != null){
						number = tradeNumbDob + lever;
					}else{
						number = tradeNumbDob;
					}
					map.put("daxtranMinActualLever", number);
				}
			}
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
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		resultJson.setSuccess(flag);
		resultJson.appendData("data",detailVos);
		resultJson.appendData("dataLever",map);
		return resultJson;
	}
	/**
	 * 下载模板文件
	 */
	@RequestMapping(value = "/downLoadTempleExcl")
	public String downLoadTempleExcl(HttpServletResponse response,HttpServletRequest request){
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        String ctxPath = request.getSession().getServletContext()  
                .getRealPath("/") ;
        String downLoadPath = ctxPath + "/template/mb.xlsx";  
        long fileLength = new File(downLoadPath).length();  
        try {
        	response.setContentType("text/html;charset=UTF-8");  
    		request.setCharacterEncoding("UTF-8"); 
    		 response.setContentType("application/octet-stream");  
			response.setHeader("Content-disposition", "attachment; filename="  
			        + new String("数据模板.xlsx".getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));  
			  
	        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
	        bos = new BufferedOutputStream(response.getOutputStream());  
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff, 0, bytesRead);  
	        }  
	        bis.close();  
	        bos.close();  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        return null; 
	}
}		
