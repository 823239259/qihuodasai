package com.tzdr.cms.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.cms.exception.UserException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.exception.RuntimeTzdrException;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.withdrawal.WithdrawalService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.cms.vo.WuserVo;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.AgentVo;
import com.tzdr.domain.vo.EndProgramVo;
import com.tzdr.domain.vo.HandUserFundVoNew;
import com.tzdr.domain.vo.SettingNotWarningVo;
import com.tzdr.domain.vo.SettingWarningVo;
import com.tzdr.domain.vo.WuserListVo;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p>
 * 用户近制器
 * </p>
 * 
 * @author LiuQing
 * @see
 * @version 2.0 2015年1月8日下午4:26:47
 */
@Controller
@RequestMapping("/admin/wUser")
public class WuserController extends BaseCmsController<WUser> {

	private static Logger log = LoggerFactory.getLogger(WuserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserTradeService userTradeService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private WithdrawalService withdrawalService;

	public WuserController() {
		setResourceIdentity("sys:customerService:wuser");
	}

	@Autowired
	private WUserService wuserService;

	@Autowired
	private UserVerifiedService userVerifiedService;

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_VIEW;
	}

	@RequestMapping(value = "/listCheck")
	public String listCheck(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_CHECK_VIEW;
	}

	@RequestMapping(value = "/listActivity")
	public String listActivity(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_ACTIVITY_VIEW;
	}

	@RequestMapping(value = "/listAgents")
	public String listAgents(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_AGENTS_VIEW;
	}

	@RequestMapping(value = "/listSettingWarning")
	public String listSettingWarning() {
		return ViewConstants.WuserViewJsp.LIST_SETTING_WARNING_VIEW;
	}

	@RequestMapping(value = "/listEnd")
	public String listEnd() {
		return ViewConstants.WuserViewJsp.LIST_END_VIEW;
	}

	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<WuserListVo> grid = new DataGridVo<WuserListVo>();
			PageInfo<WuserListVo> dataPage = new PageInfo<WuserListVo>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			if(connVo.isExcel()){
				dataPage.setCurrentPage(1);
				dataPage.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			dataPage = this.wuserService.queryDataPageWuserListVo(dataPage, connVo);
			if(connVo.isNotExcel(dataPage.getPageResults(), resp,"所有用戶列表.xls")){
				if (dataPage.getPageResults() != null) {
					for (WuserListVo wu : dataPage.getPageResults()) {
						grid.add(wu);
					}
					grid.setTotal(dataPage.getTotalCount());
				}
			
			}
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/activityListData")
	public void activityListData(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<WuserVo> grid = new DataGridVo<WuserVo>();
			PageInfo<WUser> dataPage = new PageInfo<WUser>(request);

			PageInfo<WUser> wuseres = this.wuserService.queryDataPage(dataPage);
			for (WUser wu : wuseres.getPageResults()) {
				WuserVo vo = new WuserVo(wu);
				// vo.setUserService(userService);
				grid.add(vo);
			}
			grid.setTotal(wuseres.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/updateWuserState")
	public void updateWuserState(HttpServletRequest request, HttpServletResponse resp) {
		User user = authService.getCurrentUser();
		String userId = request.getParameter("userId");
		String stateValue = request.getParameter("stateValue");
		String notByReason = request.getParameter("notByReason");
		WUser wuser = this.wuserService.getUser(userId);
		UserVerified userVerified = wuser.getUserVerified();
		if (userVerified != null) {
			Short status = userVerified.getStatus();
			if (status != null && 4 == status) {
				// 修改次数
				userVerified.setValidatenum(0);
			}
		}

		// UserVerified userVerified = wuser.getUserVerified();
		userVerified.setStatus(Short.valueOf(stateValue));
		userVerified.setNotByReason(notByReason);
		userVerified.setLastSubmitVerifiedTime(TypeConvert.dbDefaultDate());
		if (user != null) {
			userVerified.setUpdatUserId(user.getId());
		}
		this.userVerifiedService.update(userVerified);
		WebUtil.printText("success", resp);
	}

	@RequestMapping(value = "/isUsernameExist")
	@ResponseBody
	public JsonResult isUsernameExist(String username, HttpServletResponse resp) {
		WUser wuser = this.wuserService.queryWuserByUsername(username);
		
		JsonResult jsonResult = new JsonResult();
		if (wuser != null) {
			
			jsonResult.setObj(wuser.getAvlBal());
			jsonResult.setSuccess(true);
		} else {
			jsonResult.setSuccess(false);
		}
		return jsonResult;
	}

	@Override
	public BaseService<WUser> getBaseService() {
		return null;
	}

	@RequestMapping(value = "/wuserDataOne")
	public void wuserDataOne(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		DataGridVo<WuserVo> grid = new DataGridVo<WuserVo>();
		PageInfo<WUser> dataPage = new PageInfo<WUser>(request);
		// lik.put("mobile", "10");
		// orders.put("login", true);
		dataPage.setCurrentPage(dataPage.getCurrentPage() - 1);
		PageInfo<WUser> wuseres = this.wuserService.queryDataPageByWuserOne(dataPage, null);
		for (WUser wu : wuseres.getPageResults()) {
			WuserVo vo = new WuserVo(wu);
			grid.add(vo);
		}
		grid.setTotal(wuseres.getTotalCount());
		WebUtil.printText(JSON.toJSONString(grid), resp);
	}

	/**
	 * 照片认证未完成
	 * 
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/wuserDataTwo")
	public void wuserDataTwo(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		DataGridVo<WuserVo> grid = new DataGridVo<WuserVo>();
		PageInfo<WUser> dataPage = new PageInfo<WUser>(request);
		dataPage.setCurrentPage(dataPage.getCurrentPage() - 1);
		PageInfo<WUser> wuseres = this.wuserService.queryDataPageByWuserTwo(dataPage, null);
		for (WUser wu : wuseres.getPageResults()) {
			WuserVo vo = new WuserVo(wu);
			grid.add(vo);
		}
		grid.setTotal(wuseres.getTotalCount());
		WebUtil.printText(JSON.toJSONString(grid), resp);
	}

	/**
	 * 照片未认证通过
	 * 
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping(value = "/wuserDataThree")
	public void wuserDataThree(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		DataGridVo<WuserVo> grid = new DataGridVo<WuserVo>();
		PageInfo<WUser> dataPage = new PageInfo<WUser>(request);
		dataPage.setCurrentPage(dataPage.getCurrentPage() - 1);
		PageInfo<WUser> wuseres = this.wuserService.queryDataPageByWuserThree(dataPage, null);
		for (WUser wu : wuseres.getPageResults()) {
			WuserVo vo = new WuserVo(wu);
			grid.add(vo);
		}
		grid.setTotal(wuseres.getTotalCount());
		WebUtil.printText(JSON.toJSONString(grid), resp);
	}

	/**
	 * 代理商
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/agentsList")
	public void agentsList(@ModelAttribute AgentVo agentVo, HttpServletRequest request, HttpServletResponse resp)
			throws Exception {
		try {
			DataGridVo<AgentVo> grid = new DataGridVo<AgentVo>();
			PageInfo<WUser> dataPage = new PageInfo<WUser>(request);
			PageInfo<WUser> wuseres = this.wuserService.queryDataPageByAgents(dataPage, agentVo);
			for (WUser wu : wuseres.getPageResults()) {
				AgentVo vo = new AgentVo(wu);
				vo.setChTitle("股神");
				if (wu.getUserId() != null) {
					User user = this.userService.get(wu.getUserId());
					vo.setCreateUsername(user.getRealname());
					;
				}
				grid.add(vo);
			}
			// this.userTradeService.queryPageDataByConndition(null);
			grid.setTotal(wuseres.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/saveAgents")
	public void saveAgents(@ModelAttribute AgentVo vo, HttpServletRequest request, HttpServletResponse resp) {
		WUser parent = wuserService.getUser("-1");
		User user = this.authService.getCurrentUser();
		if (vo.getRebate() == null) {
			vo.setRebate(0D);
		} else if (vo.getRebate() > 100) {
			throw new RuntimeTzdrException("com.tzdr.business.value.not.greetmac", null);
		}
		WUser wuser = new WUser();
		wuser.setUserType(TypeConvert.AGENT_TYPE);
		wuser.setMobile(vo.getMobile());
		wuser.setPassword(vo.getPassword());
		wuser.setUname(vo.getUname());
		wuser.setRebate(vo.getRebate());
		wuser.setFund(0D);
		wuser.setIncome(0D);
		wuser.setIsDel((short) 0);
		wuser.setScore(0D);
		wuser.setParentNode(parent);
		wuser.setScore1(0D);
		wuser.setScore2(0D);
		wuser.setXumoney(0D);
		wuser.setCtime(TypeConvert.dbDefaultDate());
		if (user != null) {
			wuser.setUserId(user.getId());
		}
		this.wuserService.saveWUser(wuser);
		WebUtil.printText("success", resp);
	}

	@RequestMapping(value = "/updateAgents")
	public void updateAgents(@ModelAttribute AgentVo vo, HttpServletRequest request, HttpServletResponse resp) {
		WUser wuser = this.wuserService.getUser(vo.getId());
		wuser.setUname(vo.getUname());

		if (vo.getRebate() < (wuser.getRebate() == null ? 0 : wuser.getRebate())) {
			throw new RuntimeTzdrException("com.tzdr.business.value.greet.old.value", null);
		}
		User user = this.authService.getCurrentUser();
		if (user != null) {
			wuser.setUserId(user.getId());
		}
		wuser.setRebate(vo.getRebate());
		this.wuserService.updateUser(wuser);
		WebUtil.printText("success", resp);
	}

	/**
	 * 代理商
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/notSettingWarning")
	public void notSettingWarning(@ModelAttribute SettingNotWarningVo settingWarningVo, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<SettingNotWarningVo> grid = new DataGridVo<SettingNotWarningVo>();
			PageInfo<SettingNotWarningVo> dataPage = new PageInfo<SettingNotWarningVo>(request);
			PageInfo<SettingNotWarningVo> userTrades = this.userTradeService.queryNotSettingWarning(dataPage,
					settingWarningVo);
			grid.add(userTrades.getPageResults());
			grid.setTotal(userTrades.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 代理商
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/settingWarning")
	public void settingWarning(@ModelAttribute SettingWarningVo settingWarningVo, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<SettingWarningVo> grid = new DataGridVo<SettingWarningVo>();
			PageInfo<SettingWarningVo> dataPage = new PageInfo<SettingWarningVo>(request);
			PageInfo<SettingWarningVo> voes = this.userTradeService.querySettingWarning(dataPage, settingWarningVo);
			grid.add(voes.getPageResults());
			grid.setTotal(voes.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/updateSettingState")
	public void updateSettingState(HttpServletRequest request, HttpServletResponse resp) {
		String groupId = request.getParameter("groupId");
		this.userTradeService.updateUserTradeByGroupId(groupId, 1);
		WebUtil.printText("success", resp);
	}

	@RequestMapping(value = "/queryUserFundList")
	public void queryUserFundList(HttpServletRequest request, HttpServletResponse resp) {
		ConnditionVo conn = new ConnditionVo(request);
		DataGridVo<HandUserFundVoNew> grid = new DataGridVo<HandUserFundVoNew>();
		PageInfo<HandUserFundVoNew> dataPage = new PageInfo<HandUserFundVoNew>(request);
		PageInfo<HandUserFundVoNew> voes = this.userFundService.queryUserFundVoList(dataPage, conn);
		grid.add(voes.getPageResults());
		grid.setTotal(voes.getTotalCount());
		List<Map<String, Object>> footers = new ArrayList<Map<String, Object>>();
		// 小计
		Map<String, Object> footer1 = new HashMap<String, Object>(0);
		footer1.put("typeStr", "小计");
		footer1.put("inMoney", getSubTotalMoney(dataPage, false));
		footer1.put("outMoney", getSubTotalMoney(dataPage, true));
		footers.add(footer1);
		// 总计
		Map<String, Object> footer2 = new HashMap<String, Object>(0);
		footer2.put("typeStr", "总计");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("EQ_uid", conn.getValue("uid"));
		footer2.put("inMoney", withdrawalService.getTotalMoneyByUserFund(searchMap, false));
		footer2.put("outMoney", withdrawalService.getTotalMoneyByUserFund(searchMap, true));
		footers.add(footer2);
		grid.setFooter(footers);
		WebUtil.printText(JSON.toJSONString(grid), resp);

	}

	/**
	 * 获取收入或支出的小计（绝对值）
	 * 
	 * @param pageInfo
	 *            分页结果
	 * @param flag
	 *            true：支出；false：收入
	 * @return 小计
	 */
	private String getSubTotalMoney(PageInfo<HandUserFundVoNew> pageInfo, boolean flag) {
		double result = 0;
		List<HandUserFundVoNew> list = pageInfo.getPageResults();
		if (list != null && list.size() > 0) {
			for (HandUserFundVoNew fund : list) {
				double money = fund.getMoney();
				if (flag) {
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
	 * 代理商
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/endList")
	public void endList(@ModelAttribute EndProgramVo endVo, HttpServletRequest request, HttpServletResponse resp)
			throws Exception {
		try {
			DataGridVo<EndProgramVo> grid = new DataGridVo<EndProgramVo>();
			PageInfo<EndProgramVo> dataPage = new PageInfo<EndProgramVo>(request);
			PageInfo<EndProgramVo> voes = this.userTradeService.queryEnd(dataPage, endVo);
			grid.add(voes.getPageResults());
			grid.setTotal(voes.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/endSolation")
	public void endSolation(@ModelAttribute EndProgramVo vo, HttpServletRequest request, HttpServletResponse resp) {
		try {
			this.userTradeService.endOfProgram(vo.getGroupId());
			WebUtil.printText("success", resp);
		} catch (RuntimeTzdrException e) {
			e.printStackTrace();
			WebUtil.printText(e.getResourceMessage(), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 变为6600活动
	 * 
	 * @MethodName insteadActivityUser
	 * @author L.Y
	 * @date 2015年6月12日
	 * @param uid
	 *            用户id
	 */
	@RequestMapping(value = "/insteadActivityUser")
	@ResponseBody
	public JsonResult insteadActivityUser(String uid) throws Exception {
		// 查看权限
		if (null != permissionList) {
			permissionList.assertHasInsteadActivityUserPermission();
		}

		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setMessage("用户id不能为空！");
		if (!StringUtil.isBlank(uid)) {
			try {
				wuserService.updateActivityUser(uid);
				jsonResult.setSuccess(true);
				jsonResult.setMessage("已修改！");
			} catch (UserException e) {
				jsonResult.setMessage("用户已经是6600活动用户！");
				log.error(e.getMessage());
			} catch (Exception e) {
				jsonResult.setMessage("系统故障！");
				log.error(e.getMessage());
			}
		}
		return jsonResult;
	}
}
