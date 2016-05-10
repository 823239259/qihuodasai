package com.tzdr.cms.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.freediff.FreeDiffService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.FreeDiffVo;
import com.tzdr.domain.web.entity.FreeDiff;

/**
 * 佣金差过户费差
 * @author 张军
 *
 */

@Controller
@RequestMapping("/admin/freediff")
public class FreeDiffController extends BaseCmsController<FreeDiff>{
	private static Logger log = LoggerFactory.getLogger(FreeDiffController.class);
	public FreeDiffController() {
		setResourceIdentity("sys:accountant:freediff");
	}
	
	@Autowired
	private FreeDiffService freeDiffService;
	@Autowired
	private DataMapService  dataMapService;
	private static Object lock = new Object();
	private static String messageText;
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		return ViewConstants.FreeDiffViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/listData")
	public void listData(@ModelAttribute FreeDiffVo freeDiffVo,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			ConnditionVo connVo = new ConnditionVo(request);
			DataGridVo<FreeDiffVo> grid = new DataGridVo<FreeDiffVo>();
			PageInfo<FreeDiffVo> dataPage = new PageInfo<FreeDiffVo>(request);
			
			if (connVo.isExcel()) {
				dataPage.setCurrentPage(1);
				dataPage.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			
			PageInfo<FreeDiffVo> datas = this.freeDiffService.queryData(dataPage,freeDiffVo);
			if(datas.getPageResults().size()>0){
				FreeDiffVo totalInfo=this.freeDiffService.queryTotalData(freeDiffVo);
				totalInfo.setGroupid("合计");
				datas.getPageResults().add(totalInfo);
			}
				
			if (connVo.isNotExcel(datas.getPageResults(), resp, "日报表录入.xls") ) {
				grid.add(datas.getPageResults());
				grid.setTotal(datas.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception {
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.FreeDiffViewJsp.EDIT_FREEDIFF;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			FreeDiff  entity  = freeDiffService.get(id);
			Long addtime=entity.getAddtime();
			if(addtime!=null){
				Date date=Dates.parseLong2Date(addtime);
				String time=Dates.format(date, "yyyy-MM-dd");
				request.setAttribute("time",time);
			}
			request.setAttribute("entity",entity);
			return ViewConstants.FreeDiffViewJsp.EDIT_FREEDIFF;	
		}
		return ViewConstants.ERROR_VIEW;
	}

	/**
	 * 检查数据
	 * @param account 恒生子账户
	 * @param money 金额
	 * @param addtime 添加时间
	 * @param type 类型
	 * @param id 主键id
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkData")
	@ResponseBody
	public JsonResult checkData(String account,String money,String addtime,
			String type,String id,HttpServletResponse response,HttpServletRequest request){
		boolean flag=freeDiffService.checkAccount(account);
		JsonResult json=new JsonResult();
		json.setSuccess(true);
		if(flag){
			FreeDiff entity=freeDiffService.getEntity(account,addtime,type);
			if(StringUtil.isNotBlank(id)){
				//修改的数据检查
				if(entity!=null){
					if(!id.equals(entity.getId())){
						json.setSuccess(false);
						json.setMessage("此账户"+addtime+"已存在数据");
						return json;
					}
				}
			}else{
				if(entity!=null){
					json.setSuccess(false);
					json.setMessage("此账户"+addtime+"已存在数据");
					return json;
				}
			}
		}else{
			json.setSuccess(false);
			json.setMessage("恒生子账户不存在");
		}
		return json;
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
	            File file = new File(request.getServletContext().getRealPath("/")+File.separator+"upload/freereportTemplate.xls");
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
	                e.printStackTrace();  
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
	 * 数据导入
	 * @param file
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile",method=RequestMethod.POST)
	public void uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		InputStream is=file.getInputStream();
		List<FreeDiffVo> vos=excelToObjs(is);
		List<DataMap> list=dataMapService.findByTypeKey(DataDicKeyConstants.FREETYPE);
		Map<String,String> map=new HashMap<String,String>();
		for(DataMap datamap:list){
			String key=datamap.getValueKey();
			String name=datamap.getValueName();
			map.put(name, key);
		}
		if(vos!=null && vos.size()>0){
			for(FreeDiffVo vo:vos){
				String type=vo.getQtype();
				String key=map.get(type);
				vo.setQtype(key);
				if(StringUtil.isBlank(vo.getAccount())||vo.getMoney()==null||
						StringUtil.isBlank(vo.getCreatedate())||StringUtil.isBlank(vo.getQtype())){
							messageText="数据有空值";
							return;
				}else{
					if(!this.freeDiffService.checkAccount(vo.getAccount())){
						messageText="恒生子账号"+vo.getAccount()+"不存在";
						return;
					}
				}
			}
		}
		messageText=freeDiffService.savesByVo(vos);
	}
	@RequestMapping("/queryImportExcel")
	public void importResult(HttpServletResponse resp) {
		synchronized(lock) {
			WebUtil.printText(messageText, resp);
		}
	}
	/**
	 * excel 转化为对象
	 * @param file File
	 * @return List<ActivityUserVo>
	 * @throws Exception
	 */
	public static List<FreeDiffVo> excelToObjs(InputStream input) throws Exception {

		List<FreeDiffVo> vos = new ArrayList<FreeDiffVo>();
		 Workbook wb = WorkbookFactory.create(input);
		    Sheet sheet = wb.getSheetAt(0);
		    int row_num = sheet.getPhysicalNumberOfRows();  
		       for (int i = 1; i < row_num; i++) {  
		    	     FreeDiffVo vo = new FreeDiffVo();
		    	     Row r = sheet.getRow(i);  
		    	     int cell_num = r.getLastCellNum();  
		    	    for (int j = 1; j < cell_num; j++) {  
		    	    	Cell cell = r.getCell(j);
		    	    	if(j==1){
		    	    		vo.setQtype(cell.getStringCellValue());
		    	    	}else if(j==2){
		    	    		vo.setAccount(cell.getStringCellValue());
		    	    	}else if (j == 3) {
		    	    		int cellType = cell.getCellType();
				    		if (cellType == Cell.CELL_TYPE_NUMERIC) {
				    			double money=cell.getNumericCellValue();
				    			vo.setMoney(money);
				    		}else if (cellType == Cell.CELL_TYPE_STRING) {
				    			String moneystr=cell.getStringCellValue();
			    				vo.setMoney(Double.valueOf(moneystr));
			    			}
		    	    	}else if (j== 4) {
		    				vo.setCreatedate(cell.getStringCellValue());
			    		}
	    			}
		    	    vos.add(vo);
		       }
		return vos;
	}
	
	
	
	@Override
	public BaseService<FreeDiff> getBaseService() {
		return freeDiffService;
	}
	
	
}
