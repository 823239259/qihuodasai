package com.tzdr.cms.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.tzdr.business.service.futureMatchAccount.FutureMatchAccountService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.ExcelUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.FutureMatchAccountVO;
import com.tzdr.domain.web.entity.FutureMatchAccount;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

/**
 * Created by huangkai on 2016/4/19. 期货账户
 */

@Controller
@RequestMapping("/admin/futureMatch")
public class FutureMatchController extends
		BaseCmsController<FutureMatchAccount> {

	private static Logger log = LoggerFactory
			.getLogger(FutureMatchController.class);
	@Autowired
	private FutureMatchAccountService futureMatchAccountService;
	@Autowired
	private UserTradeService userTradeService;

	@Override
	public BaseService<FutureMatchAccount> getBaseService() {
		return futureMatchAccountService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:futureAccount");
	}

	@RequestMapping(value = "/list")
	public String list() {
		return ViewConstants.futureAccount.LIST_VIEW;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(value = "/getAccountDate", method = RequestMethod.POST)
	public Object getAccountDate(EasyUiPageInfo easyUiPage,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}

		ConnditionVo connVo = new ConnditionVo(request);
		if (connVo.isExcel()) {
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}
		
		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil
				.getParametersStartingWith(request, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);

		PageInfo<Object> pageInfo = futureMatchAccountService.queryAccountDate(
				easyUiPage, searchParams);
		String fileName = "秒配帐号.xls";
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,fileName)) {
			return new EasyUiPageData<Object>(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}

	@ResponseBody
	@RequestMapping(value = "/getAccountStatistics", method = RequestMethod.POST)
	public JsonResult getAccountStatistics() {
		Map<String, List<FutureMatchAccountVO>> map = futureMatchAccountService
				.futureAccountStatistical();
		JsonResult jr = new JsonResult(true);
		jr.setObj(map);
		return jr;
	}

	private static Object lock = new Object();

	@RequestMapping(value = "/saveImport")
	@ResponseBody
	public JsonResult saveImport(HttpServletRequest request,
			@RequestParam(value = "fileUrl") String fileUrl) {
		synchronized (lock) {
			File file = new File(request.getServletContext().getRealPath("/")
					+ fileUrl.replace("/", File.separator));
			if (ObjectUtil.equals(null, file)) {
				return new JsonResult(false, "系统异常，请重新上传EXCEL文件试试。。。");
			}
			BufferedInputStream inputStream = null;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));
				Workbook workbook = ExcelUtils.create(inputStream);
				Sheet sheet = workbook.getSheetAt(0);
				if (ObjectUtil.equals(null, sheet)) {
					return new JsonResult(false,
							"excel 格式有误，请重新上传EXCEL文件试试，或者直接下载模版填充数据。");
				}

				int rows = sheet.getPhysicalNumberOfRows();
				if (rows < 2) {
					return new JsonResult(false,
							"excel中数据不存在，请重新上传EXCEL文件试试,或者直接下载模版填充数据。");
				}

				Row row = sheet.getRow(0);
				int titleCells = row.getPhysicalNumberOfCells();
				if (5 != titleCells) {
					return new JsonResult(false,
							"excel数据不符合模版要求，请重新上传EXCEL文件试试,或者直接下载模版填充数据。");
				}

				List<FutureMatchAccount> batchHandRecharges = Lists
						.newArrayList();
				for (int i = 1; i < rows; i++) {
					Row tempRow = sheet.getRow(i);
					int cells = tempRow.getPhysicalNumberOfCells();
					if (5 != cells) {
						return new JsonResult(false, "您上传的表格中第" + (i + 1)
								+ "行数据有误，请修改后重新上传。");
					}

					FutureMatchAccount futureMatchAccount = new FutureMatchAccount();
					futureMatchAccount
							.setCreateTime(Dates.getCurrentLongDate());
					futureMatchAccount.setIsUse(0);
					for (int j = 0; j < cells; j++) {
						Cell cell = tempRow.getCell(j);
						String result = cell.toString();
						if (StringUtil.isBlank(result)) { // 最后一列不验证空
							return new JsonResult(false, "您上传的表格中第" + (i + 1)
									+ "行,第" + (j + 1) + "列数据未填写，请修改后重新上传。");
						}
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							DecimalFormat formater = new DecimalFormat();

							formater.setMaximumFractionDigits(2);
							formater.setGroupingSize(0);
							formater.setRoundingMode(RoundingMode.FLOOR);
							result = formater
									.format(cell.getNumericCellValue());
							// result = String.format("%.0f",
							// cell.getNumericCellValue());
							break;
						case HSSFCell.CELL_TYPE_STRING:
							result = cell.getRichStringCellValue().getString();
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							result = cell.getCellFormula();
							break;

						}
						// 将列值设置到对应的 字段属性中
						switch (j) {
						case 0:
							if (result.trim().equals("商品综合")) {
								futureMatchAccount.setBusinessType(1);
							} else if (result.trim().equals("国际综合")) {
								futureMatchAccount.setBusinessType(2);
							} else if (result.trim().equals("富时A50")) {
								futureMatchAccount.setBusinessType(3);
							} else if (result.trim().equals("国际原油")) {
								futureMatchAccount.setBusinessType(4);
							} else if (result.trim().equals("恒生指数")) {
								futureMatchAccount.setBusinessType(5);
							}  else if (result.trim().equals("小恒指")) {
								futureMatchAccount.setBusinessType(6);
							} else {
								return new JsonResult(false, "您上传的表格中第"
										+ (i + 1) + "行数据中类型不符合规范，请修改后重新上传。");
							}
							break;
						case 1:
							if (!futureMatchAccountService.isUse(result.trim())) {
								futureMatchAccount.setAccount(result.trim());
							} else {
								return new JsonResult(false, "您上传的表格中"
										+ result.trim() + "已存在");
							}
							break;
						case 2:
							futureMatchAccount.setPassword(result.trim());
							break;
						case 3:
							if (futureMatchAccount.getBusinessType() == 1
									|| futureMatchAccount.getBusinessType() == 2) {
								futureMatchAccount.setLever(0);
							} else {
								futureMatchAccount.setLever(NumberUtils
										.toInt(result));
							}
							break;
						case 4:
							futureMatchAccount.setTradeMoney(NumberUtils
									.toDouble(result));
							break;
						}
					}
					batchHandRecharges.add(futureMatchAccount);
				}

				if (CollectionUtils.isEmpty(batchHandRecharges)) {
					return new JsonResult(false, "表格中可导入的数据为空。");
				}

				// 调用service 导入数据
				futureMatchAccountService.saves(batchHandRecharges);
			} catch (Exception e) {
				log.error("数据导入失败", e);
				return new JsonResult(false, "系统异常，数据导入失败；请联系技术人员。");
			} finally {
				if (!ObjectUtil.equals(null, inputStream)) {
					try {
						inputStream.close();
					} catch (IOException e) {
						log.error("关闭输入流失败", e);
					}
				}
				file.delete();
			}
			return new JsonResult("导入成功");
		}
	}

	@RequestMapping(value = "/getAssignRecord", method = RequestMethod.POST)
	@ResponseBody
	public Object getAssignRecord(EasyUiPageInfo easyUiPage, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		// 判断是否具有查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		
		ConnditionVo conditionVo = new ConnditionVo(request);
		if(conditionVo.isExcel()){
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}

		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil
				.getParametersStartingWith(request, EasyuiUtil.SEARCH_PREFIX);

		// 查询数据
		PageInfo<Object> pageInfo = futureMatchAccountService
				.getAssignFutureAccountList(easyUiPage, searchParams);
		if(conditionVo.isNotExcel(pageInfo.getPageResults(), response, "帐号分配记录.xls")){
		}
		return new EasyUiPageData<Object>(pageInfo);
	}

	/**
	 * 下载 模版
	 * @param
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/downloadTemplate")
	public void download(HttpServletResponse response, HttpServletRequest request) {
		try {
			// path是指欲下载的文件的路径。
			response.setContentType("text/html;charset=UTF-8");
			BufferedInputStream in = null;
			BufferedOutputStream out = null;
			File file = new File(request.getServletContext().getRealPath("/")+File.separator+"upload/futureAccount.xls");

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
