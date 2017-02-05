package com.tzdr.cms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.CombineInfoException;
import com.tzdr.business.service.drawBlackList.DrawBlackListService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.ExcelUtils;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.DrawBlackListVo;
import com.tzdr.domain.web.entity.DrawBlackList;
import com.tzdr.domain.web.entity.WUser;

/**
 * 提现黑名单
 * <P>title:@DrawBlackListController.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年5月22日
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/drawBlackList")
public class DrawBlackListController extends BaseCmsController<DrawBlackList>{
	private static Logger log = LoggerFactory.getLogger(DrawBlackListController.class);
	private static String messageText;
	@Autowired
	private DrawBlackListService drawBlackListService;
	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private AuthService authService;
	
	public DrawBlackListController() {
		setResourceIdentity("sys:riskmanager:drawBlackList");
	}
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		return ViewConstants.DrawBlackListViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/listData")
	public void listData(@ModelAttribute DrawBlackListVo vo,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			ConnditionVo connVo = new ConnditionVo(request);
			DataGridVo<DrawBlackListVo> grid = new DataGridVo<DrawBlackListVo>();
			PageInfo<DrawBlackListVo> dataPage = new PageInfo<DrawBlackListVo>(request);
			//导出数据
			if (connVo.isExcel()) {
				dataPage.setCurrentPage(1);
				dataPage.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			PageInfo<DrawBlackListVo> datas = this.drawBlackListService.queryData(dataPage, vo);
			
			if (connVo.isNotExcel(datas.getPageResults(), resp, "提现黑名单列表.xls")) {
				grid.add(datas.getPageResults());
				grid.setTotal(datas.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			}
		} 
		catch (Exception e) {
			log.error("查询提现黑名单失败"+e.getMessage());
		}
	}
	@RequestMapping(value = "/edit")
	public String edit(ModelMap modelmap,HttpServletRequest request,String fromType,@RequestParam(required=false) String id ) throws Exception {
			if(!"".equals(id)&&id!=null){
				DrawBlackList drawBlackList = drawBlackListService.get(id);
				WUser wUser = wUserService.get(drawBlackList.getUid());
				modelmap.put("reason", drawBlackList.getReason());
				modelmap.put("mobile", wUser.getMobile());
				modelmap.put("id",id);
			}
			modelmap.put("fromType", fromType);
			return ViewConstants.DrawBlackListViewJsp.EDIT_DRAW_BLACK;			
		
	}
	
	@RequestMapping(value = "/checkData")
	@ResponseBody
	public JsonResult checkData(String mobile,HttpServletResponse response,HttpServletRequest request){
		WUser user=wUserService.getWUserByMobile(mobile);
		JsonResult json=new JsonResult();
		json.setSuccess(true);
		if(user==null){
			json.setSuccess(false);
			json.setMessage("用户手机号不存在");
		}else{
			String uid=user.getId();
			List<DrawBlackList> list=drawBlackListService.getEntityByUid(uid);
			if(list!=null && list.size()>0){
				json.setSuccess(false);
				json.setMessage("用户已经存在");
			}
		}
		return json;
	}
	/**
	 * 保存数据
	 * @param mobile
	 * @param reason
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doSave")
	@ResponseBody
	public JsonResult doSave(String mobile,String reason,HttpServletResponse response,HttpServletRequest request)
			throws Exception {
		WUser user=wUserService.getWUserByMobile(mobile);
		if(user!=null){
			this.drawBlackListService.saveEntity(user.getId(),reason);
			return new JsonResult(MessageUtils.message("create.success"));
		}
		return new JsonResult("手机号不存在");
	}	
	
	/**
	 * 修改数据
	 * @param mobile
	 * @param reason
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doEdit")
	@ResponseBody
	public JsonResult doEdit(String mobile,String reason,String e_id,HttpServletResponse response,HttpServletRequest request)
			throws Exception {
		
		if(mobile==null||e_id==null){
		
			return new JsonResult("修改失败,手机号不存在");
		}
		DrawBlackList drawBlackList = drawBlackListService.get(e_id);
		drawBlackList.setReason(reason);
		drawBlackListService.update(drawBlackList);
		return new JsonResult(MessageUtils.message("update.success"));
	}	
	
	@Override
	public BaseService<DrawBlackList> getBaseService() {
		return drawBlackListService;
	}
	
	private static Object lock = new Object();
	
	/**
	 * 数据导入
	 * @MethodName uploadFile
	 * @author L.Y
	 * @date 2015年7月22日
	 * @param file 文件
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
				HttpServletRequest request, HttpServletResponse resp) throws Exception {
//		InputStream is = file.getInputStream();
//		List<Stock> data = excelToObjs(is);
//		if (data != null && data.size() > 0) {
//			for (Stock stock : data) {
//				if (StringUtil.isBlank(stock.getCode())
//						|| StringUtil.isBlank(stock.getName())
//						|| StringUtil.isBlank(stock.getEffectiveDate())) {
//					messageText = "数据有空值";
//					return;
//				}
//			}
//		} else {
//			messageText = "数据有空值";
//			return;
//		}
		synchronized(lock) {
			if (ObjectUtil.equals(null, file)){
				messageText = "系统异常，请重新上传EXCEL文件试试...";
				return;
			}
			
			InputStream inputStream = null;
			
			try {
				inputStream = file.getInputStream();
				Workbook workbook =  ExcelUtils.create(inputStream);
				Sheet  sheet = workbook.getSheetAt(0);
				if (ObjectUtil.equals(null, sheet)) {
					messageText = "excel 格式有误，请重新上传EXCEL文件试试！";
					return;
				}
	  
				int rows = sheet.getPhysicalNumberOfRows();
				if (rows < 2) {
					messageText = "excel中数据不存在，请重新上传EXCEL文件试试！";
					return;
				}
	  
				Row row = sheet.getRow(0);  
				int   titleCells = row.getPhysicalNumberOfCells();
				if (2 != titleCells){
					messageText = "excel数据不符合要求，请重新上传EXCEL文件试试!";
					return;
				}
		
				//校验表格标题格式是否正确
				if(!StringUtil.equals("手机号",row.getCell(0).getStringCellValue())
						|| !StringUtil.equals("原因", row.getCell(1).getStringCellValue())) {
					messageText = "excel数据不符合模版要求，请重新上传EXCEL文件试试,或者直接下载模版填充数据。";
					return;
				}
				
				List<DrawBlackList> drawBlackLists = Lists.newArrayList();
				List<String> tmpMobiles = Lists.newArrayList();
	
				// 获取表格数据
				for (int i=1; i<rows; i++) {
					Row tempRow = sheet.getRow(i);  
					int cells = tempRow.getPhysicalNumberOfCells();
					
					if (2 != cells){
						messageText = "您上传的表格中第"+(i+1)+"行数据有误，请修改后重新上传。";
						return;
//						return new JsonResult(false,"您上传的表格中第"+(i+1)+"行数据有误，请修改后重新上传。");
					}
					
					// 初始化DrawBlackList数据
					DrawBlackList entity = new DrawBlackList();
					entity.setCreateTime(Dates.getCurrentLongDate());
	  
					for (int j=0; j<cells; j++) {
						Cell cell = tempRow.getCell(j);
						String result = cell.toString();
						
						if (StringUtil.isBlank(result)) {
							messageText = "您上传的表格中第"+(i+1)+"行,第"+(j+1)+"列数据未填写，请修改后重新上传。";
							return;
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
						
						WUser wuser = null;
						if(0 == j) {
							wuser = wUserService.getWUserByMobile(result);
							if(ObjectUtil.equals(null, wuser)) {
								messageText = "您上传的表格中第"+(i+1)+"行,用户不存在，请修改后重新上传。";
								return;
							}
							List<DrawBlackList> list = drawBlackListService.getEntityByUid(wuser.getId());
							//手机号是否加入黑名单列表
							if(!CollectionUtils.isEmpty(list)) {
								break;
							}
							entity.setUid(wuser.getId());
						}
						
						// 将列值设置到对应的 字段属性中
						switch (j) {
						case 0 :
							if (tmpMobiles.contains(result)) {
								messageText = "您上传的表格中第"+(i+1)+"行,用户手机号在表格中有重复，请修改后重新上传。";
								return; 
							}
							
							//加入临时 容器
							tmpMobiles.add(result);
							//通过 手机查询web端用户信息
							User user = authService.getCurrentUser();
							entity.setCreateUser(user.getRealname());
							entity.setCreateUserId(user.getId());
							break;
						case 1 :
							//设置原因
							entity.setReason(result);
							break;
						}
					}
					
					//用户id 是否为空
					if(!StringUtils.isBlank(entity.getUid())) {
						drawBlackLists.add(entity);
					}
				} 
				if(CollectionUtils.isEmpty(drawBlackLists)) {
					messageText = "表格中可导入的数据为空。";
					return;
//					return new JsonResult(false,"表格中可导入的数据为空。");
				}
	
				// 调用service 导入数据
				drawBlackListService.batchImportData(drawBlackLists);
				messageText = "";
			} catch (CombineInfoException ce) {
				log.error(ce.getResourceMessage(),ce);
				messageText = ce.getResourceMessage();
				return;
//				return new JsonResult(false,ce.getResourceMessage());
			} catch (Exception e) {
				log.error("提现黑名单批量导入失败",e);
				messageText = "系统异常，请联系技术人员。";
				return;
			} finally {
				if (!ObjectUtil.equals(null, inputStream)) {
					try {
						inputStream.close();
					} catch (IOException e) {
						log.error("关闭输入流失败",e);
					}
				}
			}
		}
	}
	
	private static Object lock1 = new Object();
	@RequestMapping("/queryImportExcel")
	public void importResult(HttpServletResponse resp) {
		synchronized (lock1) {
			WebUtil.printText(messageText, resp);
		}
	}
}

