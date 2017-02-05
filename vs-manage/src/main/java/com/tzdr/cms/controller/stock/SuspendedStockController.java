package com.tzdr.cms.controller.stock;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tzdr.business.service.stock.StockService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.Stock;

/**
 * 停牌股
 * 
 * @zhouchen 2015年2月12日
 */
@Controller
@RequestMapping("/admin/stock/suspended")
public class SuspendedStockController extends BaseCmsController<Stock> {

	private static Logger log = LoggerFactory
			.getLogger(SuspendedStockController.class);
	private static Object lock = new Object();
	private static String messageText;

	@Autowired
	private StockService stockService;

	@Override
	public BaseService<Stock> getBaseService() {
		return stockService;
	}

	public SuspendedStockController() {
		setResourceIdentity("sys:riskmanager:suspendedStock");
	}

	@RequestMapping(value = "list")
	public String list(HttpServletRequest request) {
		request.setAttribute("type", 2);
		return ViewConstants.StockViewJsp.LIST_VIEW;
	}

	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,
			@RequestParam("fromType") String fromType,
			@RequestParam(value = "id", required = false) String id)
			throws Exception {
		request.setAttribute("type", 2);
		request.setAttribute("fromType", fromType);
		if (StringUtil.equals(fromType, Constants.ADD)) {
			return ViewConstants.StockViewJsp.EDIT_VIEW;
		}

		if (StringUtil.equals(fromType, Constants.EDIT)) {
			Stock stock = stockService.get(id);
			request.setAttribute("stock", stock);
			return ViewConstants.StockViewJsp.EDIT_VIEW;
		}

		return ViewConstants.ERROR_VIEW;
	}

	/**
	 * 下载 模版
	 * 
	 * @param path
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "downloadTemplate")
	public void download(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			// path是指欲下载的文件的路径。
			response.setContentType("text/html;charset=UTF-8");
			BufferedInputStream in = null;
			BufferedOutputStream out = null;
			File file = new File(request.getServletContext().getRealPath("/")
					+ File.separator + "upload/SuspendedStock.xls");
			try {
				String contentType = "application/vnd.ms-excel";
				response.setContentType(contentType);
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition",
						"attachment; filename=\""
								+ new String(file.getName().getBytes("gbk"),
										"iso-8859-1") + "\"");
				response.setHeader("Content-Length",
						String.valueOf(file.length()));
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
			log.error("下载模版失败", ex);
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
	public void uploadFile(
			@RequestParam(value = "uploadFile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse resp)
			throws Exception {
		InputStream is = file.getInputStream();
		List<Stock> data = excelToObjs(is);
		if (data != null && data.size() > 0) {
			for (Stock stock : data) {
				if (StringUtil.isBlank(stock.getCode())
						|| StringUtil.isBlank(stock.getName())
						|| StringUtil.isBlank(stock.getEffectiveDate())) {
					messageText = "数据有空值";
					return;
				}
			}
		} else {
			messageText = "数据有空值";
			return;
		}
		messageText = stockService.saveByExcelExport(data);

	}

	@RequestMapping("/queryImportExcel")
	public void importResult(HttpServletResponse resp) {
		synchronized (lock) {
			WebUtil.printText(messageText, resp);
		}
	}

	/**
	 * excel 转化为对象
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private static List<Stock> excelToObjs(InputStream input) throws Exception {
		List<Stock> vos = new ArrayList<Stock>();
		Workbook wb = WorkbookFactory.create(input);
		Sheet sheet = wb.getSheetAt(0);
		int row_num = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < row_num; i++) {
			Stock vo = new Stock();
			Row r = sheet.getRow(i);
			int cell_num = r.getLastCellNum();
			for (int j = 1; j < cell_num; j++) {
				Cell cell = r.getCell(j);
				if (j == 1) {
					vo.setName(cell.getStringCellValue());
				} else if (j == 2) {
					vo.setCode(cell.getStringCellValue());
				} else if (j == 3) {
					vo.setEffectiveDate(cell.getStringCellValue());
				}
			}
			vo.setType(2);
			vos.add(vo);
		}
		return vos;
	}
}
