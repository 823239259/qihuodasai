package com.tzdr.cms.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.exception.WuserConcurrencyUpdateException;
import com.tzdr.business.service.exception.WuserDoesNotExistException;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.OperationLogVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.AllRechargeListVo;
import com.tzdr.domain.vo.AllRechargeTotalVo;
import com.tzdr.domain.vo.ComboboxVo;
import com.tzdr.domain.vo.HandUserFundVo;
import com.tzdr.domain.vo.RechargeListVo;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p>
 * 充值列表
 * </p>
 * 
 * @author LiuQing
 * @see
 * @version 2.0 2015年1月5日下午5:13:38
 */
@Controller
@RequestMapping("/admin/recharge")
public class RechargeListController extends BaseCmsController<RechargeList> {

	private static Logger log = LoggerFactory.getLogger(RechargeListController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	public RechargeListController() {
		setResourceIdentity("sys:finance:recharge");
	}

	@Autowired
	private RechargeListService rechargeListService;

	@Autowired
	private WUserService wuserService;

	@Autowired
	private UserVerifiedService userVerifiedService;

	@Autowired
	private UserFundService userFundService;

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		return ViewConstants.RechargeViewJsp.LIST_VIEW;
	}

	@RequestMapping(value = "/handlerList")
	public String handlerList(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		return ViewConstants.RechargeViewJsp.LIST_VIEW_HANDLER;
	}

	/**
	 * 数据字典内容
	 * 
	 * @param request
	 * @param resp
	 */
	@RequestMapping(value = "/dataMapCombobox")
	public void dataMapCombobox(HttpServletRequest request, HttpServletResponse resp) {
		String key = request.getParameter("key");
		String excludeStr = request.getParameter("excludes");
		String includeStr = request.getParameter("includes");
		String defaultValue = request.getParameter("defaultValue");
		String[] excludes = null;
		String[] includes = null;
		boolean includeHas = false;
		if (excludeStr != null && excludeStr.length() > 0) {
			excludes = excludeStr.split(",");
		}
		if (includeStr != null && includeStr.length() > 0) {
			includes = includeStr.split(",");
			includeHas = true;
		}
		Map<String, String> list = CacheManager.getDataMapByTypeKey(key);
		List<ComboboxVo> voes = new ArrayList<ComboboxVo>();
		if (defaultValue != null) {
			ComboboxVo comboboxVo = new ComboboxVo();
			comboboxVo.setId(defaultValue);
			comboboxVo.setText("请选择");
			voes.add(comboboxVo);
		}
		if (list != null && list.size() > 0) {
			for (Map.Entry<String, String> combo : list.entrySet()) {
				ComboboxVo comboboxVo = new ComboboxVo();
				comboboxVo.setId(combo.getKey());
				comboboxVo.setText(combo.getValue());
				boolean isAdd = true;
				if (!includeHas && excludes != null) {
					for (String value : excludes) {
						if (value.equals(comboboxVo.getId())) {
							isAdd = false;
							break;
						}
					}
				} else if (includeHas && includes != null) {
					isAdd = false;
					for (String value : includes) {
						if (value.equals(comboboxVo.getId())) {
							isAdd = true;
							break;
						}
					}
				}
				if (isAdd) {
					voes.add(comboboxVo);
				}
			}
		}
		WebUtil.printText(JSON.toJSONString(voes), resp);
	}

	@RequestMapping(value = "/listCheck")
	public String listCheck(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_CHECK_VIEW;
	}

	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);

			PageInfo<RechargeList> recharges = this.rechargeListService.queryAlipayRecharge(dataPage);
			for (RechargeList re : recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re, wuser);
				grid.add(rechargeListVo);
			}
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/listDataBank")
	public void listDataBank(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);

			PageInfo<RechargeList> recharges = this.rechargeListService.queryBankRecharge(dataPage);
			for (RechargeList re : recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re, wuser);
				grid.add(rechargeListVo);
			}
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/listRecharge")
	public void listRecharge(@ModelAttribute RechargeListVo rechargeList, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<AllRechargeListVo> grid = new DataGridVo<AllRechargeListVo>();
			PageInfo<AllRechargeListVo> dataPage = new PageInfo<AllRechargeListVo>(request);
			ConnditionVo conn = new ConnditionVo(request);
			if (conn.isExcel()) {
				dataPage.setCurrentPage(1);
				dataPage.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			PageInfo<AllRechargeListVo> recharges = this.rechargeListService.queryRecharge(dataPage, conn);
			List<AllRechargeListVo> data = recharges.getPageResults();
			if (conn.isNotExcel(data, resp, "充值记录.xls")) {
				grid.add(data);
				grid.setTotal(recharges.getTotalCount());
				/**
				 * 计算所有合计
				 */
				AllRechargeTotalVo totalVo = this.rechargeListService.queryRechargeAllTotal(conn);
				AllRechargeListVo vo = new AllRechargeListVo();
				vo.setTradeAccountBankStr("合计");
				vo.setMoney(totalVo.getMoney());
				vo.setActualMoney(totalVo.getActualMoney());
				grid.add(vo);
				WebUtil.printText(JSON.toJSONString(grid), resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/listDataRecharge")
	public void listDataRecharge(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);

			PageInfo<RechargeList> recharges = this.rechargeListService.queryBankRecharge(dataPage);
			for (RechargeList re : recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re, wuser);
				grid.add(rechargeListVo);
			}
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/listDataHandler")
	public void listDataHandler(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<HandUserFundVo> grid = new DataGridVo<HandUserFundVo>();
			PageInfo<HandUserFundVo> dataPage = new PageInfo<HandUserFundVo>(request);
			PageInfo<HandUserFundVo> userFunds = userFundService.queryHandlerRecharge(dataPage, null);
			for (HandUserFundVo uf : userFunds.getPageResults()) {
				grid.add(uf);
			}
			grid.setTotal(userFunds.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/failUpdateRechargeState")
	public void failUpdateRechargeState(HttpServletRequest request, HttpServletResponse resp) {
		User u = authService.getCurrentUser();
		try {
			String id = request.getParameter("id");
			String stateValue = request.getParameter("stateValue");
			RechargeList rechargeList = this.rechargeListService.get(id);
			if (rechargeList == null || stateValue == null || "".equals(stateValue)) {
				WebUtil.printText("请选择一条记录!", resp);
				return;
			}
			// 已经失败的
			if (1 == rechargeList.getStatus()) {
				WebUtil.printText("已标记为\"充值失败\"不能重复提交!", resp);
				return;
			} else if (TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS == rechargeList.getStatus()) {
				WebUtil.printText("\"充值成功\"后不能修改为\"充值失败\"!", resp);
				return;
			}

			// 状态
			rechargeList.setStatus(new Integer(stateValue));
			rechargeList.setOktime(TypeConvert.dbDefaultDate());
			rechargeList.setReAccountId(u.getId().toString());
			this.rechargeListService.update(rechargeList);
			WebUtil.printText("success", resp);
		} catch (WuserDoesNotExistException e) {
			WebUtil.printText("用户不存", resp);
		} catch (WuserConcurrencyUpdateException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙", resp);
		}
	}

	@RequestMapping(value = "/updateRechargeState")
	public void updateRechargeState(HttpServletRequest request, HttpServletResponse resp, Model model) {
		User user = this.authService.getCurrentUser();
		try {
			String id = request.getParameter("id");
			String stateValue = request.getParameter("stateValue");
			String tradeNo = request.getParameter("tradeNo");
			String remark = request.getParameter("remark");
			String actualMoneyStr = request.getParameter("actualMoney");
			String rechargeType = request.getParameter("rechargeType");
			String bankname = request.getParameter("bankname");
			String mobile = request.getParameter("mobile");
			RechargeList rechargeList = this.rechargeListService.get(id);
			if (bankname != null && !"".equals(bankname)) {
				rechargeList.setTradeAccount(bankname);
			}

			if (rechargeList.getStatus() != null
					&& rechargeList.getStatus() == TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS) {
				WebUtil.printText("不能重复充值审核", resp);
				return;
			}

			if (rechargeList == null || stateValue == null || "".equals(stateValue)) {
				WebUtil.printText("请选择一条记录!", resp);
				return;
			}
			// 已经失败的
			if (1 == rechargeList.getStatus()) {
				WebUtil.printText("已标记为\"充值失败\"不能修改为充值成功!", resp);
				return;
			}

			// 状态
			// rechargeList.setStatus(new Integer(stateValue));
			// if (tradeNo != null && !"".equals(tradeNo)) {
			if (tradeNo != null && !"".equals(tradeNo) && (null == rechargeType || !"aliba".equals(rechargeType))) {
				// 交易号
				rechargeList.setTradeNo(tradeNo);
			}
			// rechargeList.setRemark(remark == null ? "":remark);
			// rechargeList.setActualMoney(new BigDecimal(actualMoneyStr)
			// .doubleValue());
			// rechargeList.setOktime(TypeConvert.dbDefaultDate());
			// 支付宝支付 不需要从页面取
			if ((null == rechargeType || !"aliba".equals(rechargeType))) {
				rechargeList.setActualMoney(new BigDecimal(actualMoneyStr).doubleValue());
			}
			// 支付宝
			if (rechargeType != null && "aliba".equals(rechargeType)) {
				Double oldAvlVal = 0D;
				// String wuserId = rechargeList.getUid();
				String account = "";
				// if (wuserId != null) {
				WUser oldWuser = this.wuserService.getWUserByMobile(mobile);
				if (ObjectUtil.equals(null, oldWuser)) {
					// WUser oldWuser = this.wuserService.get(wuserId);
					WebUtil.printText("用户信息不存在！", resp);
					return;
				}
				oldAvlVal = oldWuser.getAvlBal();
				account = oldWuser.getMobile();
				rechargeList.setUid(oldWuser.getId());
				// 状态
				rechargeList.setOktime(TypeConvert.dbDefaultDate());
				rechargeList.setStatus(new Integer(stateValue));
				rechargeList.setReAccountId(user.getId() + "");
				this.rechargeListService.addUpdateRechargeList(rechargeList, TypeConvert.USER_FUND_C_TYPE_RECHARGE,
						TypeConvert.payRemark("支付宝", rechargeList.getActualMoney()));

				// Log 信息
				OperationLogVo opVo = new OperationLogVo();
				User currentUser = this.authService.getCurrentUser();
				if (currentUser != null) {
					opVo.setOperationPeople(currentUser.getUsername());
				}
				opVo.setAccount(account);
				opVo.setTitle(TypeConvert.SYS_TYPE_ALIBABA_ACCOUNTS_NAME);
				opVo.add(oldAvlVal != null ? oldAvlVal.toString() : "");
				opVo.add(rechargeList.getMoney() != null ? rechargeList.getMoney().toString() : "");
				Double currentAvlVal = 0D;
				if (!ObjectUtil.equals(null, mobile)) {
					// WUser wuser = this.wuserService.get(wuserId);
					WUser wuser = this.wuserService.getWUserByMobile(mobile);
					if (wuser != null) {
						currentAvlVal = wuser.getAvlBal();
					}
				}
				opVo.add(currentAvlVal != null ? currentAvlVal.toString() : "");
				log.info(TypeConvert.printPaymentOperationLog(opVo));

			} else {

				Double oldAvlVal = 0D;
				String wuserId = rechargeList.getUid();
				String account = "";
				if (wuserId != null) {
					WUser oldWuser = this.wuserService.get(wuserId);
					if (oldWuser != null) {
						oldAvlVal = oldWuser.getAvlBal();
						account = oldWuser.getMobile();
					}
				}
				// 状态
				rechargeList.setOktime(TypeConvert.dbDefaultDate());
				rechargeList.setStatus(new Integer(stateValue));
				rechargeList.setRemark(remark == null ? "" : remark);
				rechargeList.setReAccountId(user.getId() + "");
				this.rechargeListService.addUpdateRechargeList(rechargeList, TypeConvert.USER_FUND_C_TYPE_RECHARGE,
						TypeConvert.payRemark("银行转账", rechargeList.getActualMoney()));

				// Log 信息
				OperationLogVo opVo = new OperationLogVo();
				User currentUser = this.authService.getCurrentUser();
				if (currentUser != null) {
					opVo.setOperationPeople(currentUser.getUsername());
				}
				opVo.setAccount(account);
				opVo.setTitle(TypeConvert.SYS_TYPE_BANK_ACCOUNTS_NAME);
				opVo.add(oldAvlVal != null ? oldAvlVal.toString() : "");
				opVo.add(rechargeList.getActualMoney() != null ? rechargeList.getActualMoney().toString() : "");
				Double currentAvlVal = 0D;
				if (wuserId != null) {
					WUser wuser = this.wuserService.get(wuserId);
					if (wuser != null) {
						currentAvlVal = wuser.getAvlBal();
					}
				}
				opVo.add(currentAvlVal != null ? currentAvlVal.toString() : "");
				log.info(TypeConvert.printPaymentOperationLog(opVo));

			}
			WebUtil.printText("success", resp);
		} catch (ObjectOptimisticLockingFailureException e) {
			WebUtil.printText("操作失败，请重试！如多次失败，请联系系统管理员。", resp);
		} catch (StaleObjectStateException e) {
			WebUtil.printText("操作失败，请重试！如多次失败，请联系系统管理员。", resp);
		} catch (WuserDoesNotExistException e) {
			WebUtil.printText("用户不存在者不能充值", resp);
		} catch (WuserConcurrencyUpdateException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙", resp);
		}
	}

	public static void main(String[] args) {
		System.out.println(TypeConvert.long1000ToDatetimeStr(1425470673L));
	}

	@RequestMapping(value = "/handlerSaveRechargeState")
	public void handlerSaveRechargeState(HttpServletRequest request, HttpServletResponse resp) {

		try {
			User user = authService.getCurrentUser();
			if (user == null) {
				throw new WuserDoesNotExistException("com.tzdr.business.not.login", null);
			}
			String status = TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS + "";
			String tradeNo = request.getParameter("tradeNo");
			String mobileNo = request.getParameter("mobileNo");
			String actualMoneyStr = request.getParameter("actualMoney");
			String remark = request.getParameter("remark");
			String sysType = request.getParameter("sysType");
			String no = request.getParameter("no");
			RechargeList rechargeList = new RechargeList();
			WUser wuser = this.wuserService.queryWuserByUsername(mobileNo);
			if (wuser == null) {
				throw new WuserDoesNotExistException("com.tzdr.business.not.phonenumber", null);
			}
			rechargeList.setUid(wuser.getId());
			rechargeList.setStatus(new Integer(status));
			rechargeList.setStatusvalue(status);
			rechargeList.setTradeNo(tradeNo);
			rechargeList.setReAccountId(user.getId().toString());
			rechargeList.setMoney(new BigDecimal(actualMoneyStr).doubleValue());
			rechargeList.setActualMoney(new BigDecimal(actualMoneyStr).doubleValue());
			rechargeList.setRemark(remark == null ? "" : remark);
			rechargeList.setSysType(sysType);
			rechargeList.setNo(no);
			rechargeList.setType("5");
			rechargeList.setIsRecharge(1);
			this.rechargeListService.save(rechargeList);
			// 状态
			rechargeList.setStatus(new Integer(status));
			if (tradeNo != null && !"".equals(tradeNo)) {
				// 交易号
				rechargeList.setTradeNo(tradeNo);
			}
			rechargeList.setActualMoney(new BigDecimal(actualMoneyStr).doubleValue());
			rechargeList.setOktime(TypeConvert.dbDefaultDate());
			rechargeList.setUptime((int) TypeConvert.dbDefaultDate());
			rechargeList.setAddtime(TypeConvert.dbDefaultDate());
			this.rechargeListService.addUpdateRechargeList(rechargeList, null, remark);
			WebUtil.printText("success", resp);
		} catch (WuserDoesNotExistException e) {
			WebUtil.printText(e.getResourceMessage(), resp);
		} catch (WuserConcurrencyUpdateException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙", resp);
		}
	}

	/**
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/rechargeQuery")
	public String rechargeQuery() {
		return ViewConstants.RechargeViewJsp.LIST_VIEW_RECHARGE_QUERY;
	}

	@RequestMapping(value = "/alibaPaylistData")
	public void alibaPaylistData(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);

			PageInfo<RechargeList> recharges = this.rechargeListService.queryAlipayHaveRecharge(dataPage);
			for (RechargeList re : recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re, wuser);
				String reAccountId = re.getReAccountId();
				if (StringUtil.isNotBlank(reAccountId)) {
					User uesr1 = userService.get(Long.valueOf(reAccountId));
					if (uesr1 != null) {
						rechargeListVo.setRechargeAccountName(uesr1.getRealname());
					}
				}
				grid.add(rechargeListVo);
			}
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/bankListData")
	public void bankListData(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);
			PageInfo<RechargeList> recharges = this.rechargeListService.queryBankHaveRecharge(dataPage);
			for (RechargeList re : recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re, wuser);
				String reAccountId = re.getReAccountId();
				if (StringUtil.isNotBlank(reAccountId)) {
					User uesr1 = userService.get(Long.valueOf(reAccountId));
					if (uesr1 != null) {
						rechargeListVo.setRechargeAccountName(uesr1.getRealname());
					}
				}
				grid.add(rechargeListVo);
			}
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public BaseService<RechargeList> getBaseService() {
		return null;
	}

}
