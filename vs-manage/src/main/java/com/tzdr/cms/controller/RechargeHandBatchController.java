package com.tzdr.cms.controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tzdr.domain.vo.cms.BatchHandRechargeVo;
import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.fund.BatchHandRechargeService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.exception.WuserDoesNotExistException;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.ExcelUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.fund.BatchHandRecharge;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.WUser;


/**
 * 
 * <B>说明:手工批量现金充值 </B>
 * @zhouchen
 * 2016年2月17日 上午10:46:56
 */
@Controller
@RequestMapping("/admin/rechargeHandBatch")
public class RechargeHandBatchController  extends BaseCmsController<BatchHandRecharge> {
	
	private static Logger log = LoggerFactory.getLogger(RechargeHandBatchController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private  BatchHandRechargeService  batchHandRechargeService;
	
	public RechargeHandBatchController() {
		setResourceIdentity("sys:finance:rechargeHandBatch");
	}
	
	@Autowired
	private RechargeListService rechargeListService;

	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private UserVerifiedService userVerifiedService;
	
	@Autowired
	private UserFundService userFundService;
		
	private static Object lock = new Object();
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.RechargeViewJsp.BATCH_HAND_RECHARGE_LIST;
	}
	
	
	@RequestMapping(value="saveImport")
	@ResponseBody
	public JsonResult saveImport(HttpServletRequest request,@RequestParam(value="fileUrl") String fileUrl){
	  synchronized(lock) {
		  File file = new File(request.getServletContext().getRealPath("/")+fileUrl.replace("/",File.separator));
		  if (ObjectUtil.equals(null, file)){
			  return new JsonResult(false,"系统异常，请重新上传EXCEL文件试试。。。");
		  }
		  BufferedInputStream inputStream = null;
		  try {
			  inputStream = new BufferedInputStream(new FileInputStream(file));
			  Workbook workbook =  ExcelUtils.create(inputStream);
			  Sheet  sheet = workbook.getSheetAt(0);
			  if (ObjectUtil.equals(null, sheet)){
				  return new JsonResult(false,"excel 格式有误，请重新上传EXCEL文件试试，或者直接下载模版填充数据。");
			  }
			  
			  int rows = sheet.getPhysicalNumberOfRows();
			  if (rows < 2){
				  return new JsonResult(false,"excel中数据不存在，请重新上传EXCEL文件试试,或者直接下载模版填充数据。");
			  }
			  
			  Row row = sheet.getRow(0);  
			  int   titleCells = row.getPhysicalNumberOfCells();
			  if (5 != titleCells){
				  return new JsonResult(false,"excel数据不符合模版要求，请重新上传EXCEL文件试试,或者直接下载模版填充数据。");
			  }
			  
			  List<BatchHandRecharge>  batchHandRecharges  = Lists.newArrayList();
			  for (int i=1; i<rows; i++){
				  Row tempRow = sheet.getRow(i);  
				  int  cells = tempRow.getPhysicalNumberOfCells();
				  if (5 !=cells){
					  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行数据有误，请修改后重新上传。");
				  }
				  
				  BatchHandRecharge batchHandRecharge   =  new BatchHandRecharge();
				  batchHandRecharge.setImportTime(Dates.getCurrentLongDate());
				  
				  for (int j=0; j<cells; j++) {
					  Cell cell = tempRow.getCell(j);
					  String result = cell.toString();
					  if (StringUtil.isBlank(result)){ //最后一列不验证空
						  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行,第"+(j+1)+"列数据未填写，请修改后重新上传。");
					  }
					  switch (cell.getCellType()) {
	                     case HSSFCell.CELL_TYPE_NUMERIC :
	                    	 DecimalFormat formater = new DecimalFormat();

	            			 formater.setMaximumFractionDigits(2);
	            			 formater.setGroupingSize(0);
	            			 formater.setRoundingMode(RoundingMode.FLOOR);
	            			 result = formater.format(cell.getNumericCellValue());
//	                         result = String.format("%.0f", cell.getNumericCellValue());
	                         break;
	                     case HSSFCell.CELL_TYPE_STRING :
	                         result = cell.getRichStringCellValue().getString();
	                         break;
	                     case HSSFCell.CELL_TYPE_FORMULA :
	                         result = cell.getCellFormula();
	                         break;
	                         
				     }
					 // 将列值设置到对应的 字段属性中
					  switch (j) {
	                     case 0 :
	                    	WUser wuser = this.wuserService.findByMobileOrEmail(result.trim());
	     					if (null == wuser) {
	  						  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行数据中手机号【"+result+"】在系统中未找到相应用户信息，请修改后重新上传。");
	     					}
	                    	 batchHandRecharge.setMobile(result.trim());
	                         break;
	                     case 1 :
	                    	 int fundType = NumberUtils.toInt(result);
	                    	 if (TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE !=fundType 
	                    			 && TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_DEDUCTIONS !=fundType){
		  						  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行数据中调账类型不符合规范，请修改后重新上传。");
	                    	 }
	                    	 batchHandRecharge.setType(fundType);
	                         break;
	                     case 2 :
	                    	 batchHandRecharge.setSerialNumber(result);
	                         break;
	                     case 3 :
	                    	 if (!NumberUtils.isNumber(result)){
	   						  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行,第"+(j+1)+"列金额不是数字，请修改后重新上传。");
	                    	 }
	                    	 batchHandRecharge.setMoney(Double.valueOf(result));
	                         break;
	                     case 4 :
	                    	 batchHandRecharge.setReason(result); //保险单号
	                    	 break;
				     }
				  }
				  batchHandRecharges.add(batchHandRecharge);
			  }
			  
			  if  (CollectionUtils.isEmpty(batchHandRecharges)){
					return new JsonResult(false,"表格中可导入的数据为空。");
				}
				
			// 调用service 导入数据
			batchHandRechargeService.saves(batchHandRecharges);
		  }catch (Exception e) {
			  log.error("数据导入失败",e);
			  return new JsonResult(false,"系统异常，数据导入失败；请联系技术人员。");
		  }finally{
			  if (!ObjectUtil.equals(null, inputStream)){
				  try {
					inputStream.close();
					} catch (IOException e) {
						log.error("关闭输入流失败",e);
					}
			  }
			  file.delete();
		  }
		  
		  return new JsonResult("导入成功");
	  }
	}
	
	@RequestMapping(value = "/listData")
	@ResponseBody
	public Object listData(EasyUiPageInfo easyUiPage,HttpServletRequest request,HttpServletResponse resp) throws Exception {

		  //查看权限
		  if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	        }
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		
		//查询数据
		PageInfo<Object> pageInfo =batchHandRechargeService.queryDatas(easyUiPage, searchParams);

		double money =0;

		for(Object obj :pageInfo.getPageResults()){
			BatchHandRechargeVo vo = (BatchHandRechargeVo)obj;
			money += vo.getMoney();
		}
		BatchHandRechargeVo vo = new BatchHandRechargeVo();
		vo.setSerialNumber("当页合计：");
		vo.setMoney(money);
		vo.setImportTimeValue(null);
		pageInfo.getPageResults().add(vo);
		BatchHandRechargeVo v = new BatchHandRechargeVo();
		v.setSerialNumber("所有合计：");
		v.setImportTimeValue(null);
		Object type = searchParams.get("EQ_type");
		Double moneys = batchHandRechargeService.getMoney(type);

		v.setMoney(moneys == null ? 0 : moneys);
		pageInfo.getPageResults().add(v);
		return new EasyUiPageData(pageInfo);
	}
















	@RequestMapping(value = "/handleRecharge")
	@ResponseBody
	public JsonResult handleRecharge(HttpServletRequest request,HttpServletResponse resp,String sysType) {
		
			User user = authService.getCurrentUser();
			if (user == null) {
				throw new WuserDoesNotExistException("com.tzdr.business.not.login",null);
			}
			if (!StringUtil.equals(TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS,sysType) 
					&& !StringUtil.equals(TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS,sysType)){
				  return new JsonResult(false,"异常操作，请联系技术部门！");
			}
			int fundType = -1;
			if (StringUtil.equals(TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS,sysType)){
				fundType = TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE;
			}
			if (StringUtil.equals(TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS,sysType)){
				fundType = TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_DEDUCTIONS;
			}
			List<BatchHandRecharge> batchHandRecharges  = batchHandRechargeService.queryByType(fundType);
			if (CollectionUtils.isEmpty(batchHandRecharges)){
				 return new JsonResult(false,"您所选择的操作相关数据不存在，请先导入数据！");
			}
			for (BatchHandRecharge batchHandRecharge : batchHandRecharges){
				try {
					String status = TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS + "";
					String tradeNo = batchHandRecharge.getSerialNumber();
					String mobileNo =batchHandRecharge.getMobile();
					Double actualMoney =batchHandRecharge.getMoney();
					String remark = batchHandRecharge.getReason();
					String no = tradeNo;
					RechargeList rechargeList = new RechargeList();
					WUser wuser = this.wuserService.queryWuserByUsername(mobileNo);
					if (wuser == null) {
						throw new WuserDoesNotExistException("com.tzdr.business.not.phonenumber",null);
					}
					rechargeList.setUid(wuser.getId());
					rechargeList.setStatus(new Integer(status));
					rechargeList.setStatusvalue(status);
					rechargeList.setTradeNo(tradeNo);
					rechargeList.setReAccountId(user.getId().toString());
					rechargeList.setMoney(actualMoney);
					rechargeList.setActualMoney(actualMoney);
					rechargeList.setRemark(remark == null ? "":remark);
					rechargeList.setSysType(sysType);
					rechargeList.setNo(no);
					rechargeList.setType("5");
					this.rechargeListService.save(rechargeList);
					//状态
					rechargeList.setStatus(new Integer(status));
					if (tradeNo != null && !"".equals(tradeNo)) {
						//交易号
						rechargeList.setTradeNo(tradeNo);
					}
					rechargeList.setActualMoney(actualMoney);
					rechargeList.setOktime(TypeConvert.dbDefaultDate());
					rechargeList.setUptime((int)TypeConvert.dbDefaultDate());
					rechargeList.setAddtime(TypeConvert.dbDefaultDate());
					this.rechargeListService.addUpdateRechargeList(rechargeList,null,remark);
					// 删除记录
					batchHandRechargeService.remove(batchHandRecharge);		
				} 
				catch (WuserDoesNotExistException e) {
					batchHandRecharge.setHandleResult(e.getResourceMessage());
					batchHandRechargeService.update(batchHandRecharge);
				}
				catch (Exception e){
					batchHandRecharge.setHandleResult(Exceptions.getStackTraceAsString(e));
					batchHandRechargeService.update(batchHandRecharge);
				}
			}
		 return new JsonResult("处理成功！");
	}



	@Override
	public BaseService<BatchHandRecharge> getBaseService() {
		return batchHandRechargeService;
	}
	
	
	 /**
	  * 下载 模版
	  * @param
	  * @param response
	  * @return
	  */
	 @RequestMapping(value="downloadTemplate")
	 public void download(HttpServletResponse response, HttpServletRequest request) {
       try {
           // path是指欲下载的文件的路径。
           response.setContentType("text/html;charset=UTF-8");   
           BufferedInputStream in = null;  
           BufferedOutputStream out = null;  
           File file = new File(request.getServletContext().getRealPath("/")+File.separator+"upload/bacthHandRecharge.xls");
     
           try {  
           	String contentType = "application/vnd.ms-excel";
   			response.setContentType(contentType); 
               response.setCharacterEncoding("UTF-8");  
               response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(file.getName().getBytes("gbk"),"iso-8859-1") + "\"");  
               response.setHeader("Content-Length",String.valueOf(file.length()));  
               in = new BufferedInputStream(new FileInputStream(file));  
               out = new BufferedOutputStream(response.getOutputStream());  
               byte[] data = new byte[1024];  
               int len = 0;  
               while (-1 != (len=in.read(data, 0, data.length))) {  
                   out.write(data, 0, len);  
               }  
           } catch (Exception e) {  
           	log.error("文件读取或写出失败", e);
           } finally {  
               if (in != null) {  
                   in.close();  
               }  
               if (out != null) {  
                   out.close();  
               }  
           }  
       } catch (IOException ex) {
       	log.error("下载模版失败", ex);
       }
   }
	 
	 
	 
	 	/**
	 	 * 删除数据
	 	 * @param request
	 	 * @param resp
	 	 * @return
	 	 */
		@RequestMapping(value = "/deleteData")
		@ResponseBody
		public JsonResult deleteData(HttpServletRequest request,HttpServletResponse resp) {
			batchHandRechargeService.deleteDatas();
			 return new JsonResult("删除成功！");
		}
			
}
