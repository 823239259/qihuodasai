package com.tzdr.web.controller.fundDetail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tzdr.business.service.generalize.AgentReturnInfoService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.AgentReturnInfoVo;
import com.tzdr.domain.vo.UserFundVo;
import com.tzdr.domain.vo.UserFundWebVo;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;



/**
 * 资金明细
 * <P>title:@FundDetailController.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年1月14日
 * @version 1.0
 */
@Controller
@RequestMapping("/fund")
public class FundDetailController {
	
	@Autowired
	private UserFundService userFundService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private UserTradeService userTradeService;
	/**
	 * 到资金明细页面
	 * @param request
	 * @return
	 * @throws Exception
	 * @date 2015年1月14日
	 * @author zhangjun
	*/
	@RequestMapping(value = "/fundDetail")
	public String fundDetail(ModelMap modelMap, String index, HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user=this.wUserService.get(userSessionBean.getId());
		double frzbal=user.getFrzBal()==null?0:user.getFrzBal();
		user.setFrzBal(frzbal);
		//获取用户配资累计信息
		Double totalAccrual = this.userTradeService.getSumAccrualByWuserAndStatus(user.getId(), (short)2);  //累计盈亏
		totalAccrual = BigDecimalUtils.round2(totalAccrual, 2);
		Double totalLending = 0.00;     //操盘中总配资金额
		Double totalLeverMoney = 0.00;  //操盘中总风险保证金
		//获取用户进行中的配资列表信息
		//List<UserTradeVo> userTradeVoList = userTradeService.queryUserTradeVoByWuserAndStatus(user.getId(), (short)1);
		//求所有的收入记录
		List<UserFundVo> inmoney=this.userFundService.getFundbytype("1,3,13,15,19,16,21,23,24,25,26",user.getId());
		//求所有的支出记录
		List<UserFundVo> outmoney=this.userFundService.getFundbytype("2,4,10,11,12,17,18,20,27",user.getId());
		UserFundVo infund=inmoney.get(0);
		UserFundVo outfund=outmoney.get(0);
		//求充值收入收入记录
		List<UserFundVo> inpaymoney=this.userFundService.getFundbytype("1,3",user.getId());
		//求提款的支出记录
		List<UserFundVo> outpaymoney=this.userFundService.getFundbytype("2,4",user.getId());
		//求配资收入收入记录
		List<UserFundVo> inloanmoney=this.userFundService.getFundbytype("13,15,16,25,26",user.getId());
		//求配资的支出记录
		List<UserFundVo> outloanmoney=this.userFundService.getFundbytype("10,11,12,18,17,27",user.getId());
		//利息和管理费支出
		List<UserFundVo> outinterest=this.userFundService.getFundbytype("11,12",user.getId());
		List<UserFundVo> ininterest=this.userFundService.getFundbytype("25,26",user.getId());
		//利息和管理费收入
		List<UserFundVo> inunion=this.userFundService.getFundbytype("13",user.getId());
		UserFundVo inpayfund=inpaymoney.get(0);
		UserFundVo outpayfund=outpaymoney.get(0);
		UserFundVo inloanfund=inloanmoney.get(0);
		UserFundVo outloanfund=outloanmoney.get(0);
		UserFundVo outinterestfund=outinterest.get(0);
		UserFundVo ininterestfund=ininterest.get(0);
		UserFundVo inunionfund=inunion.get(0);
		
		List<DataMap>  data=CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.USER_FUND_TYPE);
//		if(userTradeVoList != null && !userTradeVoList.isEmpty()){
//			for (UserTradeVo userTradeVo : userTradeVoList) {
//				totalLending = BigDecimalUtils.add2(totalLending, userTradeVo.getTotalLending());
//				totalLeverMoney = BigDecimalUtils.add2(totalLeverMoney, userTradeVo.getTotalLeverMoney());
//				totalLeverMoney = BigDecimalUtils.add2(totalLeverMoney, userTradeVo.getTotalAppendLeverMoney());
//			}
//		}
		modelMap.put("totalAssets", BigDecimalUtils.round2(BigDecimalUtils.add2(BigDecimalUtils.add2(user.getFrzBal(),user.getAvlBal()),totalLeverMoney),2));                                //帐号总资产
		
		modelMap.put("infund", infund);
		modelMap.put("outfund", outfund);
		
		modelMap.put("inpayfund", inpayfund);
		modelMap.put("outpayfund", outpayfund);
		modelMap.put("inloanfund", inloanfund);
		modelMap.put("outloanfund", outloanfund);
		modelMap.put("outinterestfund", outinterestfund);
		modelMap.put("inunionfund", inunionfund);
		modelMap.put("ininterestfund", ininterestfund);
		
		modelMap.put("data", data);
		modelMap.put("totalLending", totalLending);    		 //操盘中总配资金额
		modelMap.put("totalLeverMoney", totalLeverMoney);    //操盘中总风险保证金
		modelMap.put("user", user);
		
		modelMap.put("index", index);
		return ViewConstants.FundDetail.FUND_DETAIL;
	}
 
	/**
	 * 查询资金明细
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月14日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/fundHistory")
	@ResponseBody
	public PageInfo<UserFund> fundHistory(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		String pageIndex=request.getParameter("pageIndex");
		String perPage=request.getParameter("perPage");	
		String type=request.getParameter("type");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		long sdate=0;
		long edate=0;
		if(StringUtil.isNotBlank(starttime)){
			Date startdate=DateUtils.stringToDate(starttime, "yyyy-MM-dd");
			 sdate=startdate.getTime()/1000;
		}
		if(StringUtil.isNotBlank(endtime)){
			endtime+=" 23:59:59";
			Date enddate=DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
			edate=enddate.getTime()/1000;
		}
		PageInfo<UserFund> pageInfo=userFundService.
				findData(pageIndex,perPage,userSessionBean.getId(),type,sdate,edate);
		return pageInfo;
	}
	
	
	@RequestMapping(value = "/fundHistorySelfAndChild")
	@ResponseBody
	public PageInfo<UserFundWebVo> fundHistorySelfAndChild(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		String pageIndex=request.getParameter("pageIndex");
		String perPage=request.getParameter("perPage");	
		String type=request.getParameter("type");
		ConnditionVo connVo = new ConnditionVo(request);
		connVo.addParam(Constants.TZDR_USER_SESSION, userSessionBean.getId());
		PageInfo<UserFundWebVo> pageInfo = new PageInfo<UserFundWebVo>();
		pageInfo.setCurrentPage(new Integer(pageIndex));
		pageInfo.setCountOfCurrentPage(new Integer(perPage));
		pageInfo = userFundService.queryUserFundWebVoByConn(pageInfo, connVo);
		
		return pageInfo;
	}
	
	/**
	 * 获取配资
	 * @param modelMap
	 * @param uid
	 * @param date
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getTotalTradeMoney")
	@ResponseBody
	public JsonResult getTotalTradeMoneyByUserId(ModelMap modelMap,String uid,String date,HttpServletRequest request,HttpServletResponse response){
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		Double totalMoney = userTradeService.getTradeMoneyByUserId(uid,date);
		Map<Object,Object> data = new HashMap<Object,Object>();
		data.put("totalMoney", BigDecimalUtils.round2(totalMoney, 2));
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	 * 查询资金明细
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月14日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/fundAgentReturn")
	@ResponseBody
	public PageInfo<AgentReturnInfoVo> fundAgentReturn(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		String pageIndex=request.getParameter("pageIndex");
		String perPage=request.getParameter("perPage");	
		ConnditionVo connVo = new ConnditionVo(request);
		connVo.addParam(Constants.TZDR_USER_SESSION, userSessionBean.getId());
		PageInfo<AgentReturnInfoVo> pageInfo = new PageInfo<AgentReturnInfoVo>();
		pageInfo.setCurrentPage(new Integer(pageIndex));
		pageInfo.setCountOfCurrentPage(new Integer(perPage));
		pageInfo = agentReturnInfoService.queryAgentReturnInfoVoByConn(pageInfo, connVo);
		
		return pageInfo;
	}
	
	@Autowired
	private AgentReturnInfoService agentReturnInfoService;
	
	
	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private UserVerifiedService userVerifiedService;
	
	/**
	 * 查询资金明细
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月14日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/getTitleData")
	@ResponseBody
	public JsonResult  getTitleData(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		
		String type=request.getParameter("type");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		long sdate=0;
		long edate=0;
		if(StringUtil.isNotBlank(starttime)){
			Date startdate=DateUtils.stringToDate(starttime, "yyyy-MM-dd");
			 sdate=startdate.getTime()/1000;
		}
		if(StringUtil.isNotBlank(endtime)){
			endtime+=" 23:59:59";
			Date enddate=DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
			edate=enddate.getTime()/1000;
		}
		List<UserFundVo> datas=this.userFundService.getTitleData(sdate,edate,type,userSessionBean.getId());
		jsonResult.setObj(datas.get(0));
		return jsonResult;
	}
	
	
	
	
	
	
}
