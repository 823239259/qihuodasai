package com.tzdr.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.MonthUserTradeService.MonthUserTradeService;
import com.tzdr.business.service.exception.RuntimeTzdrException;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.ArrearsEndDetail;
import com.tzdr.domain.vo.EndProgramVo;
import com.tzdr.domain.vo.MonthEndVo;
import com.tzdr.domain.web.entity.MonthUserTrade;
import com.tzdr.domain.web.entity.UserTrade;

import freemarker.core.ReturnInstruction.Return;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月14日 上午11:01:04 欠费待终结
 */
@Controller
@RequestMapping("/admin/arrearsEnd")
public class ArrearsEndController extends BaseCmsController<UserTrade> {
	private static Logger log = LoggerFactory
			.getLogger(ArrearsEndController.class);

	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private MonthUserTradeService MonthUserTradeService;
	
	@Autowired
	private NoticeRecordService noticeRecordService;

	@Override
	public BaseService<UserTrade> getBaseService() {
		return userTradeService;
	}

	public ArrearsEndController() {
		setResourceIdentity("sys:riskmanager:arrearsEnd");
	}

	@RequestMapping(value = "/listEnd")
	public String listEnd() {
		return ViewConstants.WuserViewJsp.LIST_END_VIEW;
	}

	/**
	 * 欠费待终结方案列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/endList")
	public void endList(HttpServletRequest request, HttpServletResponse response){
		
		try {
			DataGridVo<EndProgramVo> grid = new DataGridVo<EndProgramVo>();
			PageInfo<EndProgramVo> dataPage = new PageInfo<EndProgramVo>(
					request);
			ConnditionVo connVo = new ConnditionVo(request);
			PageInfo<EndProgramVo> voes = this.userTradeService.queryEnd(
					dataPage, connVo);
			grid.add(voes.getPageResults());
			grid.setTotal(voes.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交欠费终结方案 审核(涌金版)
	 * 
	 * @MethodName endSolationReview
	 * @author L.Y
	 * @date 2015年5月13日
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/endSolationReview")
	@ResponseBody
	public JsonResult endSolationReview(@ModelAttribute EndProgramVo vo)
			throws Exception {
		JsonResult jsonResult = new JsonResult(false);
		this.userTradeService.endSolationReview(vo);
		jsonResult.setSuccess(true);
		return jsonResult;
	}

	@RequestMapping(value = "/endSolation")
	public void endSolation(@ModelAttribute EndProgramVo vo,
			HttpServletRequest request, HttpServletResponse resp) {
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

	@RequestMapping(value = "/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ConnditionVo connVo = new ConnditionVo(request);
		List<ArrearsEndDetail> arrearsEndDetailList = noticeRecordService
				.list(connVo);
		DataGridVo<ArrearsEndDetail> grid = new DataGridVo<ArrearsEndDetail>();
		grid.add(arrearsEndDetailList);
		WebUtil.printText(JSON.toJSONString(grid), response);
	}
	
	
	@RequestMapping("/monthNearEndValue")
	public void monthNearEnd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			DataGridVo<MonthEndVo> grid = new DataGridVo<MonthEndVo>();
			PageInfo<MonthEndVo> dataPage = new PageInfo<MonthEndVo>(
					request);
			ConnditionVo connVo = new ConnditionVo(request);
			PageInfo<MonthEndVo> voes = this.userTradeService.monthNearEndList(
					dataPage, connVo);
			grid.add(voes.getPageResults());
			grid.setTotal(voes.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/monthEndValue")
	public void monthEnd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			DataGridVo<MonthEndVo> grid = new DataGridVo<MonthEndVo>();
			PageInfo<MonthEndVo> dataPage = new PageInfo<MonthEndVo>(
					request);
			ConnditionVo connVo = new ConnditionVo(request);
			PageInfo<MonthEndVo> voes = this.userTradeService.monthEndList(
					dataPage, connVo);
			grid.add(voes.getPageResults());
			grid.setTotal(voes.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value="/changeType",method={RequestMethod.POST,RequestMethod.GET})
	public JsonResult changeType(String tradeId) {
		JsonResult jsonResult = new JsonResult(false);
		MonthUserTrade userTrade = MonthUserTradeService.findByTradeId(tradeId);
		if(null == userTrade){
			jsonResult.setMessage("找不到该方案");
			return jsonResult;
		}
		userTrade.setIsManualDelay(0);
		MonthUserTradeService.update(userTrade);
		jsonResult.setSuccess(true);
		jsonResult.setMessage("限买解除成功！");
		return jsonResult;
	}
}
