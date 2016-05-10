package com.tzdr.cms.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.CombineInfoException;
import com.tzdr.business.service.account.AccountService;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.ExcelUtils;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.ParentAccount;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 恒生子账户管理controller
 */
@Controller
@RequestMapping("/admin/subAccount")
public class SubAccountController extends BaseCmsController<Account>{

	private static Logger log = LoggerFactory.getLogger(SubAccountController.class);

	@Autowired
	private AccountService  accountService;
	
	@Autowired
	private ParentAccountService  parentAccountService;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public BaseService<Account> getBaseService() {
		return accountService;
	}

	public SubAccountController() {
		setResourceIdentity("sys:accountmanager:subaccount");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		return ViewConstants.HundsunAccountViewJsp.SUB_ACCOUNT_LIST_VIEW;
	}

	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.HundsunAccountViewJsp.SUB_ACCOUNT_EDIT_VIEW;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			Account  account  =  accountService.get(id);
			request.setAttribute("account",account);
			return ViewConstants.HundsunAccountViewJsp.SUB_ACCOUNT_EDIT_VIEW;	
		}
		
		return ViewConstants.ERROR_VIEW;
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
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,
			ServletRequest request) throws Exception{
		
		  //查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		
		PageInfo<Object> pageInfo = accountService.queryList(easyUiPage, searchParams);
		
		return new EasyUiPageData(pageInfo);
	}
	
	
	
	@RequestMapping(value="toImport")
	public String toImport(HttpServletRequest  request){
		return ViewConstants.HundsunAccountViewJsp.IMPORT_ACCOUNT_VIEW;
	}

	private static Object lock = new Object();
	
	@RequestMapping(value="saveImport")
	@ResponseBody
	public JsonResult  saveImport(HttpServletRequest request,@RequestParam(value="fileUrl") String fileUrl,
			@RequestParam(value="parentAccountID") String parentAccountID)
	{
		
		  ParentAccount  parentAccount =parentAccountService.get(parentAccountID);
		  if (ObjectUtil.equals(null, parentAccount)){
			  return new JsonResult(false,"母账户信息有误！");
		  }
		  
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
			  //校验表格标题是否与模版一致
			  if (!StringUtil.equals("恒生账户名",row.getCell(0).getStringCellValue())
					  || !StringUtil.equals("恒生账号", row.getCell(1).getStringCellValue())
					  || !StringUtil.equals("恒生密码", row.getCell(2).getStringCellValue())
					  || !StringUtil.equals("单元序号", row.getCell(3).getStringCellValue())
					  || !StringUtil.equals("保险单号", row.getCell(4).getStringCellValue())){
				  return new JsonResult(false,"excel数据不符合模版要求，请重新上传EXCEL文件试试,或者直接下载模版填充数据。");
			  }

			  List<Account>  accounts  = Lists.newArrayList();
			  List<String> tempAccountNos = Lists.newArrayList();
			  List<String> tempAssetIds = Lists.newArrayList();
			  
			  User   loginUser = authService.getCurrentUser();	 
			  // 获取表格数据
			  for (int i=1; i<rows; i++){
				  Row tempRow = sheet.getRow(i);  
				  int  cells = tempRow.getPhysicalNumberOfCells();
				  if (5 !=cells){
					  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行数据有误，请修改后重新上传。");
				  }
				  // 初始化account数据
				  Account  account  =  new Account(parentAccount);
				  account.setCreateTime(Dates.getCurrentLongDate());
				  account.setCreateUser(loginUser.getRealname());
				  account.setCreateUserId(loginUser.getId());
				  account.setCreateUserOrg(loginUser.getOrganization().getName());
				  
				  for (int j=0; j<cells; j++) {
					  Cell cell = tempRow.getCell(j);
					  String result = cell.toString();
					  if (StringUtil.isBlank(result) && j != 4){ //最后一列不验证空
						  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行,第"+(j+1)+"列数据未填写，请修改后重新上传。");
					  }
					  switch (cell.getCellType()) {
	                     case HSSFCell.CELL_TYPE_NUMERIC :
	                         result = String.format("%.0f", cell.getNumericCellValue());
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
	                    	 account.setAccountName(result);
	                         break;
	                     case 1 :
	                    	 if (tempAccountNos.contains(result)){
	                    		 return new JsonResult(false,"您上传的表格中第"+(i+1)+"行,恒生帐号在表格中有重复，请修改后重新上传。"); 
	                    	 }
	                    	 
	                    	 List<Account> list = accountService.findByAccountNo(result);
	                    	 if (!CollectionUtils.isEmpty(list)){
	   						  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行,恒生帐号已经存在，请修改后重新上传。"); 
	                    	 }
	                    	 account.setAccount(result);
	                    	 tempAccountNos.add(result);
	                         break;
	                     case 2 :
	                         account.setPassword(result);
	                         break;
	                     case 3 :
	                    	 if (tempAssetIds.contains(result)){
	                    		 return new JsonResult(false,"您上传的表格中第"+(i+1)+"行,单元序号在表格中有重复，请修改后重新上传。"); 
	                    	 }
	                    	 
	                    	 List<Account> assetIdList = accountService.findByAssetId(result);
	                    	 if (!CollectionUtils.isEmpty(assetIdList)){
	   						  return new JsonResult(false,"您上传的表格中第"+(i+1)+"行,单元序号已经存在，请修改后重新上传。"); 
	                    	 }
	                    	 account.setAssetId(result);
	                    	 tempAssetIds.add(result);
	                         break;
	                     case 4 :
	                    	 account.setInsuranceNo(result); //保险单号
	                    	 break;
				     }
					 
			  }
				  accounts.add(account);
			} 
			if  (CollectionUtils.isEmpty(accounts)){
				return new JsonResult(false,"表格中可导入的数据为空。");
			}
			
			// 调用service 导入数据
			accountService.batchImportData(accounts);
		  }catch (CombineInfoException ce) {
			  log.error(ce.getResourceMessage(),ce);
			  return new JsonResult(false,ce.getResourceMessage());
		  }
		  catch (Exception e) {
			  log.error("子账户批量导入失败",e);
			  return new JsonResult(false,"系统异常，请联系技术人员。");
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
		  }
		  return new JsonResult("导入成功");
		
	}	
	
	 /**
	  * 下载 模版
	  * @param path
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
            File file = new File(request.getServletContext().getRealPath("/")+File.separator+"upload/AccountTemplate.xls");
      
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
}
