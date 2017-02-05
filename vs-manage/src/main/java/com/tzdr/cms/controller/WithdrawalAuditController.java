package com.tzdr.cms.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.drawMoney.DrawMoneyService;
import com.tzdr.business.service.drawMoneyData.DrawMoneyDataService;
import com.tzdr.business.service.pay.PaymentSupportBankService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.withdrawal.WithdrawalService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.api.bbpay.Bibipay;
import com.tzdr.common.api.bbpay.util.BbUtil;
import com.tzdr.common.api.payease.PayEase;
import com.tzdr.common.api.payease.util.PayEaseUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.DrawListAuditVo;
import com.tzdr.domain.vo.DrawMoneyListVo;
import com.tzdr.domain.web.entity.DrawList;
import com.tzdr.domain.web.entity.DrawMoneyData;
import com.tzdr.domain.web.entity.PaymentSupportBank;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月3日 上午10:10:05 提现记录 管理
 */
@Controller
@RequestMapping("/admin/withdrawAudit")
public class WithdrawalAuditController extends BaseCmsController<DrawList> {
	private static Logger log = LoggerFactory.getLogger(WithdrawalAuditController.class);

	@Autowired
	private WithdrawalService withdrawalService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private DrawMoneyDataService drawMoneyDataService;

	@Autowired
	private DataMapService dataMapService;

	@Autowired
	private DrawMoneyService drawMoneyService;

	@Autowired
	private PaymentSupportBankService paymentSupportBankService;

	@Autowired
	private AuthService authService;

	@Override
	public BaseService<DrawList> getBaseService() {
		return withdrawalService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:finance:withdrawAudit");
	}

	@RequestMapping(value = "/list")
	public String drawlist(HttpServletRequest request) {
		DataMap dataMap = dataMapService.getWithDrawMoney();
		request.setAttribute("auditMoney", ObjectUtil.equals(null, dataMap)
				? DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME : dataMap.getValueName());
		return ViewConstants.WithdrawalViewJsp.AUDIT_LIST_VIEW;
	}

	@RequestMapping(value = "/listFirstAuditDrawData")
	public void listFirstAuditDrawData(@ModelAttribute DrawListAuditVo vo, @RequestParam(value = "type") String type,
			HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {

			DataGridVo<DrawListAuditVo> grid = new DataGridVo<DrawListAuditVo>();
			PageInfo<DrawListAuditVo> dataPage = new PageInfo<DrawListAuditVo>(request);
			PageInfo<DrawListAuditVo> datas = this.withdrawalService.findFirstAuditDrawList(dataPage, vo, type);
			DrawListAuditVo totalInfo = this.withdrawalService.queryTotalData(vo, type);
			totalInfo.setMobile("合计");
			datas.getPageResults().add(totalInfo);

			grid.add(datas.getPageResults());
			grid.setTotal(datas.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 含参数的搜索 search_EQ_name
	 * 
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model, HttpServletRequest request, String type,
			HttpServletResponse response) throws Exception {

		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		ConnditionVo connVo = new ConnditionVo(request);
		if (connVo.isExcel()) {
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}

		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request, EasyuiUtil.SEARCH_PREFIX);

		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		if (searchParams.containsKey("IN_isAudit") && searchParams.get("IN_isAudit") != null
				&& StringUtil.isNotBlank(searchParams.get("IN_isAudit").toString())) {

			String[] isAuditStrs = searchParams.get("IN_isAudit").toString().split(",");

			if (isAuditStrs != null && isAuditStrs.length > 0) {
				List<Object> isAudits = new ArrayList<Object>();
				for (String string : isAuditStrs) {
					isAudits.add(Integer.valueOf(string));
				}
				searchParams.put("IN_isAudit", isAudits);
			}
		}
		// 查询数据
		PageInfo<Object> pageInfo = withdrawalService.queryList(easyUiPage, searchParams);

		if (StringUtil.isNotBlank(type) && "auditList".equals(type)) {
			DrawMoneyListVo totalInfo = withdrawalService.getTotalAuditList(searchParams);
			totalInfo.setMobile("合计");
			pageInfo.getPageResults().add(totalInfo);
		}

		if (connVo.isNotExcel(pageInfo.getPageResults(), response, "线下待审核列表.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}

	@RequestMapping(value = "/getUserFundDetail")
	public String getUserFundDetail(HttpServletRequest request, @RequestParam(value = "uid") String uid) {
		request.setAttribute("userId", uid);
		return ViewConstants.WithdrawalViewJsp.USER_FUND_LIST_VIEW;
	}

	@RequestMapping(value = "/auditNotPass")
	public String auditNotPass(HttpServletRequest request, @RequestParam(value = "id") String id,
			@RequestParam(value = "fromType") int fromType, @RequestParam(value = "datagridId") String datagridId) {
		request.setAttribute("datagridId", datagridId);
		request.setAttribute("fromType", fromType);
		request.setAttribute("drawList", withdrawalService.get(id));
		return ViewConstants.WithdrawalViewJsp.NOT_PASS_VIEW;
	}
/**
 * 
 */
	@RequestMapping(value = "/lineAuthority")
	@ResponseBody
	public JsonResult lineAuthority(@RequestParam(value = "type")int type){
		DrawMoneyData draw = drawMoneyDataService.getAduitMoneyByType("3");
		User u = authService.getCurrentUser();
		JsonResult jsonResult=new JsonResult();
		if (type == 1) {// 为线下转账初审
			if(draw!=null){
				if (draw.getFirstAuditId() != u.getId()) {
					jsonResult.setMessage("对不起，你没有此项权限！");
					jsonResult.setSuccess(false);
				}else{
					jsonResult.setSuccess(true);
				}
			}else{
				jsonResult.setMessage("对不起，你没有此项权限！,请先配置线下提现规则");
				jsonResult.setSuccess(false);
			}
		}
		if(type==3){
			if(draw!=null){
				if (draw.getReAuditId() != u.getId()) {
					jsonResult.setMessage("对不起，你没有此项权限！");
					jsonResult.setSuccess(false);
				}else{
					jsonResult.setSuccess(true);
				}
			}else{
				jsonResult.setMessage("对不起，你没有此项权限！,请先配置线下提现规则");
				jsonResult.setSuccess(false);
			}
		}
		return jsonResult;
	}
	
	/**
	 * 含参数的搜索 search_EQ_name
	 * 
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userFundDetail", method = RequestMethod.POST)
	@ResponseBody
	public Object userFundDetail(EasyUiPageInfo easyUiPage, Model model, ServletRequest request) throws Exception {

		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}

		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		if (StringUtil.isBlank(easyUiPage.getSort()) || StringUtil.isBlank(easyUiPage.getOrder())) {
			easyUiPage.setSort("addtime");
			easyUiPage.setOrder(EasyuiUtil.DESC);
		}
		Map<String, Boolean> sortMap = EasyuiUtil.getSortMap(easyUiPage);
		// 查询数据
		PageInfo<UserFund> pageInfo = new PageInfo<UserFund>(easyUiPage.getRows(), easyUiPage.getPage());
		pageInfo = userFundService.query(pageInfo, searchParams, sortMap);

		// 设置footer(添加收入、支出的分页小计和总计)
		JSONArray array = new JSONArray();
		// 小计
		JSONObject subTotal = new JSONObject();
		subTotal.put("typevalue", "小计：");
		subTotal.put("money1", getSubTotalMoney(pageInfo, false));
		subTotal.put("money2", getSubTotalMoney(pageInfo, true));
		array.add(subTotal);
		// 总计
		JSONObject grandTotal = new JSONObject();
		grandTotal.put("typevalue", "总计：");
		grandTotal.put("money1", withdrawalService.getTotalMoneyByUserFund(searchParams, false));
		grandTotal.put("money2", withdrawalService.getTotalMoneyByUserFund(searchParams, true));
		array.add(grandTotal);

		return new EasyUiPageData(pageInfo, array);
	}

	@RequestMapping(value = "/firstAudit", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult firstAudit(HttpServletRequest request, @RequestParam("id") String id) {
		try {
			DrawList vdrawList = withdrawalService.findByDrawList(id);
			if (vdrawList.getIsAudit() != 0) {
				return new JsonResult(false, "该数据已经处理过审核操作，请勿多次提交！");
			}

			if (vdrawList.getStatus() == 3) {
				return new JsonResult(false, "该数据已经取消提现，请勿审核！");
			}
			withdrawalService.firstAuditStatus(id, -1);
		} catch (Exception e) {
			return new JsonResult("审核失败！" + e.getMessage());
		}
		return new JsonResult("审核成功！");
	}

	private static Object lock = new Object();

	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult audit(HttpServletRequest request, @RequestParam("id") String id) {

		synchronized (lock) {
			DrawList vdrawList = withdrawalService.findByDrawList(id);
			if (vdrawList.getIsAudit() != -1) {
				return new JsonResult(false, "该数据已经处理过审核操作，请勿多次提交！");
			}

			if (vdrawList.getStatus() == 3) {
				return new JsonResult(false, "该数据已经取消提现，请勿审核！");
			}

			// 如果该提现记录来源是配股宝则调用配股宝审核接口
			if (Constant.Source.PGB == vdrawList.getSource()) {
				log.info("配股宝提现记录进行审核操作：系统操作员【" + authService.getCurrentUser().getRealname() + "】," + "审核通过客户【手机号："
						+ vdrawList.getUser().getMobile() + ",姓名：" + vdrawList.getName() + "】提现，金额："
						+ vdrawList.getMoney());
				// 更新操作人信息
				withdrawalService.updateAuditUserInfo(id);
				return WebUtil.pgbAuditWithdraw(id);
			}

			// 更新审核为成功状态
			DrawList drawList = withdrawalService.updateAuditStatus(id, 1);

			// 易支付调用接口
			if (Constant.PaymentChannel.EASE_PAY == vdrawList.getPaymentChannel()) {
				return payeaseDrawMoney(vdrawList);
			}

			// 币币支付调用接口
			if (Constant.PaymentChannel.BB_PAY == vdrawList.getPaymentChannel()) {
				return bbDrawMoney(vdrawList);
			}

			// 联动优势调用接口
			JSONObject jsonObject = drawMoneyService.drawMoney(id);
			String loginfo = "用户：【" + drawList.getUser().getMobile() + "】提现,调用支付接口返回结果::::code="
					+ jsonObject.getString("retCode") + "," + "message=" + jsonObject.getString("retMsg") + ";提现记录ID:"
					+ drawList.getId() + ";提现金额：" + drawList.getMoney();
			log.info(loginfo);

			if (!StringUtil.equals("0000", jsonObject.getString("retCode"))) {
				EmailExceptionHandler.getInstance().HandleHintWithData("cms-提现审核接口返回异常",
						this.getClass().getSimpleName() + ":: audit()", loginfo);
			}
			return new JsonResult("审核成功！");
		}
		// 接口调用成功 返回0000
		/*
		 * if (StringUtil.equals("0000",jsonObject.getString("retCode"))){
		 * return new JsonResult("审核成功！"); }
		 * 
		 * try { withdrawalService.updateAuditStatus(id,0); } catch (Exception
		 * e) { EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
		 * "cms-后台提现审核接口调用失败后，更新审核状态为未审核失败！",
		 * this.getClass().getName()+":audit","详细数据：用户信息：【"+drawList.getUser().
		 * getMobile()+"】,提现记录ID="+id); return new
		 * JsonResult(false,"系统异常，请联系技术部！"); } return new
		 * JsonResult(false,jsonObject.getString("retMsg"));
		 */

	}

	/**
	 * 提现
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "doAuit", method = RequestMethod.POST)
	public JsonResult doAuit(HttpServletRequest request, @RequestParam("id") String id) {
		synchronized (lock) {
			JsonResult resultJson = null;
			DrawList vdrawList = withdrawalService.findByDrawList(id);
			if (vdrawList.getIsAudit() != -1) {
				resultJson = new JsonResult(false, "该数据已经处理过审核操作，请勿多次提交！");
			}
			if (vdrawList.getStatus() == 3) {
				resultJson = new JsonResult(false, "该数据已经取消提现，请勿审核！");
			}
			boolean result = withdrawalService.doAuit(id, 1);
			if (result) {
				resultJson = new JsonResult("审核成功！");
			} else {
				resultJson = new JsonResult("审核异常,请召唤技术");
			}
			return resultJson;
		}
	}

	/**
	 * 线下审核通过
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/lineAuditPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult lineAuditPass(HttpServletRequest request, @RequestParam("id") String id) {
		User u = authService.getCurrentUser();// 获取当前登录用户
		DrawMoneyData draw = drawMoneyDataService.getAduitMoneyByType("3");
		if (StringUtil.isBlank(id)) {
			return new JsonResult(Boolean.FALSE, "数据异常，没有对应的数据ID。");
		}
		if (u.getId() == draw.getReAuditId()) {
			DrawList vdrawList = withdrawalService.findByDrawList(id);
			if (vdrawList.getIsAudit() == -1) {
				if (vdrawList.getIsAudit() == 2) {
					return new JsonResult(false, "该数据已经处理为审核未通过，请勿多次提交！");
				}
				if (vdrawList.getStatus() == 3) {
					return new JsonResult(false, "该数据已经取消提现，请勿审核！");
				}
				withdrawalService.lineAuditPass(id);
				return new JsonResult("审核操作成功！");
			} else {
				return new JsonResult(false, "该数据未经过初步审核操作！");
			}
		} else {
			return new JsonResult(false, "对不起，你没有此项权限！");
		}
	}

	/**
	 * 线下审核(初审)通过
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/preLineAuditPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult preLineAuditPass(HttpServletRequest request, @RequestParam("id") String id) {
		User u = authService.getCurrentUser();// 获取当前登录用户
		DrawMoneyData draw = drawMoneyDataService.getAduitMoneyByType("3");
		if (StringUtil.isBlank(id)) {
			return new JsonResult(Boolean.FALSE, "数据异常，没有对应的数据ID。");
		}
		if (u.getId() != null && u.getId() == draw.getFirstAuditId()) {

			DrawList vdrawList = withdrawalService.findByDrawList(id);
			if (vdrawList.getIsAudit() != 0) {
				return new JsonResult(false, "该数据已经处理过审核操作，请勿多次提交！");
			}

			if (vdrawList.getStatus() == 3) {
				return new JsonResult(false, "该数据已经取消提现，请勿审核！");
			}
			withdrawalService.preLineAuditPass(id);
			return new JsonResult("初步审核操作成功！");
		} else {
			return new JsonResult(false, "对不起，你没有此项权限！");
		}

	}

	@RequestMapping(value = "/setNotPass", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult setNotPass(HttpServletRequest request, @RequestParam("type") String type, DrawList drawList) {

		if (StringUtil.isBlank(drawList.getAremark())) {
			return new JsonResult(false, "审核不通过原因不能为空！");
		}

		DrawList vdrawList = withdrawalService.findByDrawList(drawList.getId());

		if (StringUtil.isNotBlank(type) && "1".equals(type) && vdrawList.getIsAudit() != 0) {
			return new JsonResult(false, "该数据已经处理过审核操作，请勿多次提交！");
		} else if (StringUtil.isNotBlank(type) && "3".equals(type) && vdrawList.getIsAudit() != -1) {
			return new JsonResult(false, "该数据已经处理过审核操作，请勿多次提交！");
		}

		if (vdrawList.getStatus() == 3) {
			return new JsonResult(false, "该数据已经取消提现，请勿审核！");
		}

		withdrawalService.setNotPass(drawList, type);
		return new JsonResult("操作成功！");
	}

	/**
	 * 获取收入或支出的小计（绝对值）
	 * 
	 * @param pageInfo
	 *            分页结果
	 * @param isPay
	 *            true：支出；false：收入
	 * @return 小计
	 */
	private String getSubTotalMoney(PageInfo<UserFund> pageInfo, boolean isPay) {
		double result = 0;
		List<UserFund> list = pageInfo.getPageResults();
		if (list != null && list.size() > 0) {
			for (UserFund fund : list) {
				double money = fund.getMoney();
				if (isPay) {
					if (money < 0) {
						result = BigDecimalUtils.add(result, money);
					}
				} else {
					if (money > 0) {
						result = BigDecimalUtils.add(result, money);
					}
				}
			}
		}
		// result=Math.abs(result);//取绝对值
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");// 格式化设置
		return decimalFormat.format(result);
	}

	/**
	 * 币币支付提现接口调用
	 * 
	 * @param drawList
	 * @return
	 */
	private JsonResult bbDrawMoney(DrawList drawList) {
		// 提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,
				DataDicKeyConstants.BB_FEE);

		Double handleFee = 0.00;

		if (!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}

		Double dmoney = drawList.getMoney();
		if (BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			// 扣除手续费
			dmoney = BigDecimalUtils.subRound(dmoney, handleFee);
		}
		WUser user = drawList.getUser();
		// 校验是否支持该银行提现
		PaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(drawList.getSubbank());
		// 调用提现接口
		// 格式：联行号~|~银行卡号~|~开户人~|~结算金额~|~1：私人，2：公司
		JSONObject bbResult = BbUtil.withDrawMony(dmoney, paymentSupportBank.getBbpayContactNumber(),
				drawList.getCard(), user.getUserVerified().getTname(), drawList.getNo());

		String resultMsg = bbResult.getString("msg");
		if (Bibipay.HANDLE_SUCCESS_STATUS != bbResult.getIntValue("status")) {
			log.info("cms币币支付委托结算失败，返回结果：" + bbResult);
			EmailExceptionHandler.getInstance().HandleHintWithData("cms币币支付委托结算失败", "bbDrawMoney", resultMsg);
			return new JsonResult(false, resultMsg);
		}

		withdrawalService.updateAuditStatus(drawList.getId(), 1);
		// 更新币币订单号到提现记录中
		drawMoneyService.updatDrawBybbOrderId(drawList.getId(), bbResult.getString("order_number"));

		return new JsonResult(resultMsg);
	}

	/**
	 * 易支付提现处理
	 * 
	 * @param drawList
	 * @param dmoney
	 * @param paymentSupportBank
	 * @param user
	 * @param bankCard
	 * @return
	 */
	private JsonResult payeaseDrawMoney(DrawList drawList) {
		log.info("cms提现审核调用易支付支付接口参数：金额[" + drawList.getMoney() + "],卡号[" + drawList.getCard() + "]");

		// 提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,
				DataDicKeyConstants.PAYEASE_FEE);
		Double handleFee = 0.00;

		if (!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}

		Double dmoney = drawList.getMoney();
		if (BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			// 扣除手续费
			dmoney = BigDecimalUtils.subRound(dmoney, handleFee);
		}
		WUser user = drawList.getUser();
		// 校验是否支持该银行提现
		PaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(drawList.getSubbank());
		// 调用提现接口
		// 格式：分帐信息总行数|分帐总金额|$收方帐号|收方帐户名|收方开户行|收方省份|收方城市|付款金额| 客户标识|联行号
		JSONObject result = PayEaseUtil.tzdrDrawMony(dmoney, paymentSupportBank.getBbpayContactNumber(),
				drawList.getCard(), user.getUserVerified().getTname(), drawList.getNo(),
				paymentSupportBank.getBankName());
		if (PayEase.WITHDRAW_INTERFACE_SUCCESS != result.getIntValue("status")) {
			log.info("易支付调用取款接口失败，" + result.toJSONString());
			EmailExceptionHandler.getInstance().HandleHintWithData("cms调用易支付调用取款接口失败", "PayEaseDrawMoney",
					result.toJSONString());
			return new JsonResult(false, result.getString("desc"));
		}

		// withdrawalService.updateApiAuditStatus(drawList.getId(),1);
		// 更新商户号和商户秘钥 到提现记录中，以便获取提现状态时方便
		drawMoneyService.updatDrawPayeaseInfo(drawList.getId(), result.getString("vmid"), result.getString("secret"));
		return new JsonResult("审核提现成功！");
	}
}
