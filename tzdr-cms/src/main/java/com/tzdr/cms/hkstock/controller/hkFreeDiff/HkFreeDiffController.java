package com.tzdr.cms.hkstock.controller.hkFreeDiff;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.hkstock.service.HkFreeDiffService;
import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.hkstock.entity.HkFreeDiff;
import com.tzdr.domain.hkstock.vo.HkFreeDiffVo;

import jodd.util.StringUtil;

/**
 * 港股佣金过户费
 * @Description: 
 * @author liuhaichuan
 * @date 2015年11月4日
 *
 */
@Controller
@RequestMapping("/admin/hkstock/hkFreeDiff")
public class HkFreeDiffController extends BaseCmsController<HkFreeDiff> {

	Logger logger = LoggerFactory.getLogger(HkFreeDiffController.class);

	@Autowired
	private HkFreeDiffService hkFreeDiffService;

	@Autowired
	private AuthService authService;

	@Autowired
	private HkUserTradeService hkUserTradeService;

	@Autowired
	private DataMapService dataMapService;
	
	private static Object lock = new Object();
	
	private static String messageText="";

	@Override
	public BaseService<HkFreeDiff> getBaseService() {
		// TODO Auto-generated method stub
		return hkFreeDiffService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:multipleQuery:freediff");
	}

	/**
	 * 获取数据
	 * 
	 * @param easyUiPage
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public Object getData(EasyUiPageInfo easyUiPage, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		ConnditionVo connVo = new ConnditionVo(req);
		if (connVo.isExcel()) {
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}
		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		PageInfo<Object> pageInfo = hkFreeDiffService.getData(easyUiPage, searchParams);
		this.createTotalData(pageInfo.getPageResults());
		if (connVo.isNotExcel(pageInfo.getPageResults(), resp, "港股佣金过户费差报表.xls")) {
			return new EasyUiPageData<>(pageInfo);
		}
		return new EasyUiPageData<>(pageInfo);
	}

	/**
	 * 跳转编辑页
	 * 
	 * @param request
	 * @param fromType
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, @RequestParam("fromType") String fromType,
			@RequestParam(value = "id", required = false) String id) throws Exception {
		request.setAttribute("fromType", fromType);
		if (StringUtil.equals(fromType, Constants.ADD)) {
			return HkViewConstants.HkFreeDiffJsp.EDIT_VIEW;
		}

		if (StringUtil.equals(fromType, Constants.EDIT)) {
			HkFreeDiff entity = hkFreeDiffService.get(id);
			Long addtime = entity.getAddtime();
			if (addtime != null) {
				Date date = Dates.parseLong2Date(addtime);
				String time = Dates.format(date, "yyyy-MM-dd");
				request.setAttribute("time", time);
			}
			request.setAttribute("entity", entity);
			return HkViewConstants.HkFreeDiffJsp.EDIT_VIEW;
		}
		return ViewConstants.ERROR_VIEW;
	}

	/**
	 * 编辑数据
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult saveOrUpdate(HkFreeDiff vo) {
		// true为新增操作，false为修改操作
		boolean isCreate = vo == null || StringUtil.isBlank(vo.getId());
		if (permissionList != null) {
			if (isCreate) {
				this.permissionList.assertHasCreatePermission();
			} else {
				this.permissionList.assertHasUpdatePermission();
			}
		}
		JsonResult result = new JsonResult(false);
		try {
			if (!checkData(vo)) {
				result.setMessage("传入的数据不正确");
				return result;
			} else if (!hkUserTradeService.exists(vo.getAccount())) {
				result.setMessage("交易账户不存在");
				return result;
			}
			User loginUser = authService.getCurrentUser();
			HkFreeDiff freeDiff = new HkFreeDiff();
			if (isCreate) {
				if (hkFreeDiffService.exists(vo.getAccount(), vo.getAddtime().longValue(), vo.getType())) {
					result.setMessage("此记录已存在");
					return result;
				}
				freeDiff.setType(vo.getType());
				freeDiff.setMoney(vo.getMoney());
				freeDiff.setAccount(vo.getAccount());
				freeDiff.setAddtime(vo.getAddtime());
				freeDiff.setCreateUser(loginUser.getRealname());
				freeDiff.setCreateUserId(loginUser.getId());
				freeDiff.setCreateTime(Dates.getCurrentLongDate());
				hkFreeDiffService.save(freeDiff);
			} else {
				freeDiff = hkFreeDiffService.get(vo.getId());
				freeDiff.setType(vo.getType());
				freeDiff.setMoney(vo.getMoney());
				freeDiff.setAccount(vo.getAccount());
				freeDiff.setAddtime(vo.getAddtime());
				freeDiff.setUpdateUser(loginUser.getRealname());
				freeDiff.setUpdateUserId(loginUser.getId());
				freeDiff.setUpdateTime(Dates.getCurrentLongDate());
				hkFreeDiffService.update(freeDiff);
			}
			result.setSuccess(true);
			result.setMessage("操作成功");

		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
			result.setMessage("操作失败");
		}
		return result;
	}

	/**
	 * 下载 模版
	 * 
	 * @param path
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "downloadTemplate")
	public void download(HttpServletResponse response, HttpServletRequest request) {
		try {
			// path是指欲下载的文件的路径。
			response.setContentType("text/html;charset=UTF-8");
			BufferedInputStream in = null;
			BufferedOutputStream out = null;
			File file = new File(
					request.getServletContext().getRealPath("/") + File.separator + "upload/HkFreeReportTemplate.xls");
			try {
				String contentType = "application/vnd.ms-excel";
				response.setContentType(contentType);
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + new String(file.getName().getBytes("gbk"), "iso-8859-1") + "\"");
				response.setHeader("Content-Length", String.valueOf(file.length()));
				in = new BufferedInputStream(new FileInputStream(file));
				out = new BufferedOutputStream(response.getOutputStream());
				byte[] data = new byte[1024];
				int len = 0;
				while (-1 != (len = in.read(data, 0, data.length))) {
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
			logger.error("下载模版失败", ex);
		}
	}

	/**
	 * 数据导入
	 * 
	 * @param file
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse resp) throws Exception {
		InputStream is = file.getInputStream();
		List<HkFreeDiff> list = excelToObjs(is);
		if(list!=null && list.size()>0){
			for(HkFreeDiff freeDiff:list){
				if(!checkData(freeDiff)){
					messageText = "数据有空值";
					return;
				}else if(!hkUserTradeService.exists(freeDiff.getAccount())){
					messageText = "交易账户" + freeDiff.getAccount() + "不存在";
					return;
				}
			}
			User loginUser = authService.getCurrentUser();
			for(HkFreeDiff freeDiff:list){
				freeDiff.setCreateUser(loginUser.getRealname());
				freeDiff.setCreateUserId(loginUser.getId());
				freeDiff.setCreateTime(Dates.getCurrentLongDate());
			}
			messageText=hkFreeDiffService.saveBatch(list);
		}
	}
	
	@RequestMapping("/queryImportExcel")
	public void importResult(HttpServletResponse resp) {
		synchronized(lock) {
			WebUtil.printText(messageText, resp);
		}
	}

	/**
	 * 求合计
	 * 
	 * @param data
	 */
	private void createTotalData(List<Object> data) {
		if (data == null || data.size() == 0) {
			return;
		}
		BigDecimal totalMoney = new BigDecimal(0);
		for (Object obj : data) {
			HkFreeDiffVo vo = (HkFreeDiffVo) obj;
			if (vo.getMoney() != null) {
				totalMoney = TypeConvert.add(totalMoney.doubleValue(), vo.getMoney().doubleValue());
			}
		}
		HkFreeDiffVo vo = new HkFreeDiffVo();
		vo.setGroupid("合计");
		vo.setMoney(totalMoney.doubleValue());
		data.add(vo);
	}

	/**
	 * 检测数据
	 * 
	 * @param vo
	 * @return
	 */
	private boolean checkData(HkFreeDiff vo) {
		if (vo == null) {
			return false;
		}
		if (StringUtil.isBlank(vo.getType())) {
			return false;
		}
		if (StringUtil.isBlank(vo.getAccount())) {
			return false;
		}
		if(StringUtil.isBlank(vo.getCreatedate())){
			return false;
		}
		return true;
	}


	/**
	 * Excel转换为对象集合
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public List<HkFreeDiff> excelToObjs(InputStream input) throws Exception {

		List<HkFreeDiff> list = new ArrayList<HkFreeDiff>();
		Workbook wb = WorkbookFactory.create(input);
		Sheet sheet = wb.getSheetAt(0);
		int row_num = sheet.getPhysicalNumberOfRows();
		Map<String,String> map=getDataMap();
		for (int i = 1; i < row_num; i++) {
			HkFreeDiff freeDiff = new HkFreeDiff();
			Row r = sheet.getRow(i);
			int cell_num = r.getLastCellNum();
			for (int j = 1; j < cell_num; j++) {
				Cell cell = r.getCell(j);
				if (j == 1) {
					freeDiff.setType(map.get(cell.getStringCellValue()));
				} else if (j == 2) {
					String account="";
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						account=cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						account=String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue());
						break;
					default:
						break;
					}
					freeDiff.setAccount(account);
				} else if (j == 3) {
					double money=0;
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						money = cell.getNumericCellValue();
						break;
					case Cell.CELL_TYPE_STRING:
						money=Double.valueOf(cell.getStringCellValue()).doubleValue();
						break;
					default:
						break;
					}
					freeDiff.setMoney(money);
				} else if (j == 4) {
					freeDiff.setCreatedate(cell.getStringCellValue());
				}
			}
			list.add(freeDiff);
		}
		return list;
	}
	
	/**
	 * 获取类型的数据映射
	 * @return
	 */
	public Map<String,String> getDataMap(){
		List<DataMap> list = dataMapService.findByTypeKey(DataDicKeyConstants.FREETYPE);
		Map<String, String> map = new HashMap<String, String>();
		for (DataMap datamap : list) {
			String key = datamap.getValueKey();
			String name = datamap.getValueName();
			map.put(name, key);
		}
		return map;
	}
}
