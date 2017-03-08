package com.tzdr.cms.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.tzdr.business.service.wuser.impl.WUserServiceImpl;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.vo.AllRechargeListVo;
import com.tzdr.domain.vo.ComboboxVo;
import com.tzdr.domain.vo.HandUserFundVo;
import com.tzdr.domain.vo.RechargeBankListVo;
import com.tzdr.domain.vo.RechargeListVo;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p>充值列表</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年1月5日下午5:13:38
 */
@Controller
@RequestMapping("/admin/rechargeReview")
public class RechargeReviewController  extends BaseCmsController<RechargeList> {
	
	private static Logger log = LoggerFactory.getLogger(RechargeReviewController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	public RechargeReviewController() {
		setResourceIdentity("sys:finance:rechargeReview");
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
	public String list(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.RechargeViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/handlerList")
	public String handlerList(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.RechargeViewJsp.LIST_VIEW_HANDLER;
	}
	
	
	/**
	 * 数据字典内容
	 * @param request
	 * @param resp
	 */
	@RequestMapping(value = "/dataMapCombobox")
	public void dataMapCombobox(HttpServletRequest request,HttpServletResponse resp) {
		String key = request.getParameter("key");
		String excludeStr = request.getParameter("excludes");
		String[] excludes = null;
		if (excludeStr != null && excludeStr.length() > 0) {
			excludes = excludeStr.split(",");
		}
		Map<String,String> list = CacheManager.getDataMapByTypeKey(key);
		List<ComboboxVo> voes = new ArrayList<ComboboxVo>();
		if (list != null && list.size() > 0) {
			for (Map.Entry<String, String> combo:list.entrySet()) {
				ComboboxVo comboboxVo = new ComboboxVo();
				comboboxVo.setId(combo.getKey());
				comboboxVo.setText(combo.getValue());
				boolean isAdd = true;
				if (excludes != null) {
					for (String value:excludes) {
						if (value.equals(comboboxVo.getId())) {
							isAdd = false;
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
	public String listCheck(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_CHECK_VIEW;
	}
	
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeBankListVo> grid = new DataGridVo<RechargeBankListVo>();
			PageInfo<RechargeBankListVo> dataPage = new PageInfo<RechargeBankListVo>(request);
			dataPage = this.rechargeListService.queryAlipayListRecharge(dataPage,new ConnditionVo(request));
			/**
			PageInfo<RechargeList> recharges = this.rechargeListService.queryAlipayRecharge(dataPage);
			for (RechargeList re:recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re,wuser);
				grid.add(rechargeListVo);
			}
			**/
			grid.add(dataPage.getPageResults());
			grid.setTotal(dataPage.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/listDataBank")
	public void listDataBank(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			/*DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);
			
			PageInfo<RechargeList> recharges = this.rechargeListService.queryBankRecharge(dataPage);
			for (RechargeList re:recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re,wuser);
				grid.add(rechargeListVo);
			}*/
			DataGridVo<RechargeBankListVo> grid = new DataGridVo<RechargeBankListVo>();
			PageInfo<RechargeBankListVo> dataPage= new PageInfo<RechargeBankListVo>(request);
			dataPage = this.rechargeListService.queryBankListRecharge(dataPage);
			grid.add(dataPage.getPageResults());
			grid.setTotal(dataPage.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/listDataNetBank")
	public void listDataNetBank(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			/*DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);
			
			PageInfo<RechargeList> recharges = this.rechargeListService.queryBankRecharge(dataPage);
			for (RechargeList re:recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re,wuser);
				grid.add(rechargeListVo);
			}*/
			DataGridVo<RechargeBankListVo> grid = new DataGridVo<RechargeBankListVo>();
			PageInfo<RechargeBankListVo> dataPage= new PageInfo<RechargeBankListVo>(request);
			dataPage = this.rechargeListService.queryNetBankListRecharge(dataPage);
			grid.add(dataPage.getPageResults());
			grid.setTotal(dataPage.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/listDataWechat")
	public void listDataWechat(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			/*DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);
			
			PageInfo<RechargeList> recharges = this.rechargeListService.queryBankRecharge(dataPage);
			for (RechargeList re:recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re,wuser);
				grid.add(rechargeListVo);
			}*/
			DataGridVo<RechargeBankListVo> grid = new DataGridVo<RechargeBankListVo>();
			PageInfo<RechargeBankListVo> dataPage= new PageInfo<RechargeBankListVo>(request);
			dataPage = this.rechargeListService.queryWechatListRecharge(dataPage);
			grid.add(dataPage.getPageResults());
			grid.setTotal(dataPage.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/listRecharge")
	public void listRecharge(@ModelAttribute RechargeListVo rechargeList,HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<AllRechargeListVo> grid = new DataGridVo<AllRechargeListVo>();
			PageInfo<AllRechargeListVo> dataPage = new PageInfo<AllRechargeListVo>(request);
			PageInfo<AllRechargeListVo> recharges = this.rechargeListService.queryRecharge(dataPage,null);
			/*for (RechargeList re:recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re,wuser);
				grid.add(rechargeListVo);
			}*/
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value = "/listDataRecharge")
	public void listDataRecharge(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);
			
			PageInfo<RechargeList> recharges = this.rechargeListService.queryBankRecharge(dataPage);
			for (RechargeList re:recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re,wuser);
				grid.add(rechargeListVo);
			}
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/listDataHandler")
	public void listDataHandler(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<HandUserFundVo> grid = new DataGridVo<HandUserFundVo>();
			PageInfo<HandUserFundVo> dataPage = new PageInfo<HandUserFundVo>(request);
			PageInfo<HandUserFundVo> userFunds = userFundService.queryHandlerRecharge(dataPage,null);
			for (HandUserFundVo uf:userFunds.getPageResults()) {
				grid.add(uf);
			}
			grid.setTotal(userFunds.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/failUpdateRechargeState")
	public void failUpdateRechargeState(HttpServletRequest request,HttpServletResponse resp) {
		
		try {
			String id = request.getParameter("id");
			String stateValue = request.getParameter("stateValue");
			RechargeList rechargeList = this.rechargeListService.get(id);
			if (rechargeList == null || stateValue == null || "".equals(stateValue)) {
				WebUtil.printText("请选择一条记录!", resp);
				return;
			}
			//已经失败的
			if (1 == rechargeList.getStatus()) {
				WebUtil.printText("已标记为\"充值失败\"不能重复提交!", resp);
				return;
			}
			else if (TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS == rechargeList.getStatus()) {
				WebUtil.printText("\"充值成功\"后不能修改为\"充值失败\"!", resp);
				return;
			}
			
			//状态
			rechargeList.setStatus(new Integer(stateValue));
			rechargeList.setOktime(TypeConvert.dbDefaultDate());
			this.rechargeListService.update(rechargeList);
			WebUtil.printText("success", resp);
		} 
		catch (WuserDoesNotExistException e) {
			WebUtil.printText("用户不存", resp);
		}
		catch (WuserConcurrencyUpdateException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙", resp);
		}
	}
	
	
	
	@RequestMapping(value = "/updateRechargeState")
	public void updateRechargeState(HttpServletRequest request,HttpServletResponse resp) {
		
		try {
			String id = request.getParameter("id");
			String stateValue = request.getParameter("stateValue");
			String tradeNo = request.getParameter("tradeNo");
			String remark = request.getParameter("remark");
			String actualMoneyStr = request.getParameter("actualMoney");
			String rechargeType = request.getParameter("rechargeType");
			RechargeList rechargeList = this.rechargeListService.get(id);
			
		    if (rechargeList.getStatus() != null 
		    		&& rechargeList.getStatus() == TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS){
		    	WebUtil.printText("不能重复充值审核", resp);
		    	return;
		    }
		    
		    if (rechargeList == null || stateValue == null || "".equals(stateValue)) {
				WebUtil.printText("请选择一条记录!", resp);
				return;
			}
			//已经失败的
			if ( 1 == rechargeList.getStatus()) {
				WebUtil.printText("已标记为\"充值失败\"不能修改为充值成功!", resp);
				return;
			}
			
			//状态
			rechargeList.setStatus(new Integer(stateValue));
			if (tradeNo != null && !"".equals(tradeNo)) {
				//交易号
				rechargeList.setTradeNo(tradeNo);
			}
			rechargeList.setRemark(remark == null ? "":remark);
			rechargeList.setActualMoney(new BigDecimal(actualMoneyStr)
					.doubleValue());
			rechargeList.setOktime(TypeConvert.dbDefaultDate());
			//支付宝
			if (rechargeType != null && "aliba".equals(rechargeType)) {
				this.rechargeListService.addUpdateRechargeList(rechargeList,
						TypeConvert.USER_FUND_C_TYPE_RECHARGE,
						TypeConvert.payRemark("支付宝", rechargeList.getActualMoney()));
			}
			else {
				this.rechargeListService.addUpdateRechargeList(rechargeList,
						TypeConvert.USER_FUND_C_TYPE_RECHARGE,
						TypeConvert.payRemark("银行转账", rechargeList.getActualMoney()));
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("money", String.valueOf(rechargeList.getActualMoney()));
			String mobile = wuserService.get(rechargeList.getUid()).getMobile();
			SMSSender.getInstance().sendByTemplate(1, mobile, "ihuyi.recharge.success.template", map);
			WebUtil.printText("success", resp);
		} 
		catch (WuserDoesNotExistException e) {
			WebUtil.printText("用户不存在者不能充值", resp);
		}
		catch (WuserConcurrencyUpdateException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙", resp);
		}
	}
	
	@RequestMapping(value = "/handlerSaveRechargeState")
	public void handlerSaveRechargeState(HttpServletRequest request,HttpServletResponse resp) {
		
		try {
			User user = authService.getCurrentUser();
			if (user == null) {
				throw new WuserDoesNotExistException("com.tzdr.business.not.login",null);
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
				throw new WuserDoesNotExistException("com.tzdr.business.not.phonenumber",null);
			}
			rechargeList.setUid(wuser.getId());
			rechargeList.setStatus(new Integer(status));
			rechargeList.setStatusvalue(status);
			rechargeList.setTradeNo(tradeNo);
			rechargeList.setReAccountId(user.getId().toString());
			rechargeList.setMoney(new BigDecimal(actualMoneyStr).doubleValue());
			rechargeList.setActualMoney(new BigDecimal(actualMoneyStr).doubleValue());
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
			rechargeList.setActualMoney(new BigDecimal(actualMoneyStr)
					.doubleValue());
			rechargeList.setOktime(TypeConvert.dbDefaultDate());
			rechargeList.setUptime((int)TypeConvert.dbDefaultDate());
			rechargeList.setAddtime(TypeConvert.dbDefaultDate());
			this.rechargeListService.addUpdateRechargeList(rechargeList,null,remark);
			WebUtil.printText("success", resp);
		} 
		catch (WuserDoesNotExistException e) {
			WebUtil.printText(e.getResourceMessage(), resp);
		}
		catch (WuserConcurrencyUpdateException e) {
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
	public void alibaPaylistData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);
			
			PageInfo<RechargeList> recharges = this.rechargeListService.queryAlipayHaveRecharge(dataPage);
			for (RechargeList re:recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re,wuser);
				grid.add(rechargeListVo);
			}
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/bankListData")
	public void bankListData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<RechargeListVo> grid = new DataGridVo<RechargeListVo>();
			PageInfo<RechargeList> dataPage = new PageInfo<RechargeList>(request);
			
			PageInfo<RechargeList> recharges = this.rechargeListService.queryBankHaveRecharge(dataPage);
			for (RechargeList re:recharges.getPageResults()) {
				WUser wuser = wuserService.getUser(re.getUid());
				RechargeListVo rechargeListVo = new RechargeListVo(re,wuser);
				grid.add(rechargeListVo);
			}
			grid.setTotal(recharges.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override 
	public BaseService<RechargeList> getBaseService() {
		return null;
	}
	
}
