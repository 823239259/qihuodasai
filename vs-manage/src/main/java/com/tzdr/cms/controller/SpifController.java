package com.tzdr.cms.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleAppendLevelMoneyService;
import com.tzdr.business.service.userTrade.FSimpleUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.FSimpleAppendLevelMoneyVo;
import com.tzdr.domain.vo.FSimpleUserTradeVo;
import com.tzdr.domain.web.entity.FSimpleUserTrade;

/**
 * <B>说明: </B>股指期货申请和结算录入
 * @author LiuYang
 *
 * 2015年7月11日 下午2:50:16
 */
@Controller
@RequestMapping("/admin/spif")
public class SpifController extends BaseCmsController<FSimpleUserTrade> {
	private static Logger log = LoggerFactory.getLogger(SpifController.class);
	
	
	@Autowired		
	private FSimpleUserTradeService fSimpleUserTradeService;
	@Autowired
	private  TradeDayService tradeDayService;
	
	@Autowired
	private RechargeListService rechargeListService;
	
	@Autowired
	private FSimpleAppendLevelMoneyService fSimpleAppendLevelMoneyService;
	
	@Autowired
	private WUserService wUserService;
	
	@Override
	public BaseService<FSimpleUserTrade> getBaseService() {
		return fSimpleUserTradeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:spif");
	}
	
	/**
	 * 进入手工配资页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public  String  drawlist(HttpServletRequest request){
		return ViewConstants.SpifJsp.LIST_VIEW;
	}
	
	
	/**
	 * 获取申请列表
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	

	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public void getApplyList(HttpServletResponse response,HttpServletRequest request) throws Exception{
		try {
			
			DataGridVo<FSimpleUserTradeVo> grid = new DataGridVo<FSimpleUserTradeVo>();
			PageInfo<FSimpleUserTradeVo> pageInfo = new PageInfo<FSimpleUserTradeVo>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			
			pageInfo = fSimpleUserTradeService.queryApplyListVo(pageInfo, connVo);
			grid.add(pageInfo.getPageResults());
			grid.setTotal(pageInfo.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), response);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取结算列表
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/balance", method = RequestMethod.POST)
	@ResponseBody
	public void getBalanceList(HttpServletResponse response,HttpServletRequest request) throws Exception{
		try {
			
			DataGridVo<FSimpleUserTradeVo> grid = new DataGridVo<FSimpleUserTradeVo>();
			PageInfo<FSimpleUserTradeVo> pageInfo = new PageInfo<FSimpleUserTradeVo>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			
			if(connVo.isExcel()){
				List<FSimpleUserTradeVo> data = fSimpleUserTradeService.queryBalanceListVo(connVo);
				if(data != null && !data.isEmpty()){
					for (FSimpleUserTradeVo fSimpleUserTradeVo : data) {
						String startDate =  new SimpleDateFormat("yyyyMMdd").format(new Date(fSimpleUserTradeVo.getAppStarttime()*1000));
						String endDate =  new SimpleDateFormat("yyyyMMdd").format(new Date());
						fSimpleUserTradeVo.setUseTradeDay(fSimpleUserTradeVo.getStateType() == 3 ? fSimpleUserTradeVo.getUseTradeDay() : tradeDayService.getTradeDays(startDate, endDate));
					}
				}
				connVo.isNotExcel(data, response,"方案管理.xls");
				return;
			}
			
			pageInfo = fSimpleUserTradeService.queryBalanceListVo(pageInfo, connVo);
			
			List<FSimpleUserTradeVo> pageResults =  pageInfo.getPageResults();
			
			if(pageResults != null && !pageResults.isEmpty()){
				for (FSimpleUserTradeVo fSimpleUserTradeVo : pageResults) {
					String startDate =  new SimpleDateFormat("yyyyMMdd").format(new Date(fSimpleUserTradeVo.getAppStarttime()*1000));
					String endDate =  new SimpleDateFormat("yyyyMMdd").format(new Date());
					fSimpleUserTradeVo.setUseTradeDay(fSimpleUserTradeVo.getStateType() == 3 ? fSimpleUserTradeVo.getUseTradeDay() : tradeDayService.getTradeDays(startDate, endDate));
				}
				pageInfo.setPageResults(pageResults);
			}
			
			grid.add(pageInfo.getPageResults());
			grid.setTotal(pageInfo.getTotalCount());
			
			WebUtil.printText(JSON.toJSONString(grid), response);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 拒绝用户申请
	 * @param id
	 * @param resp
	 */
	@RequestMapping(value="refuse")
	public void refuse(String id,String mobileNo,HttpServletResponse resp){
		try {
			FSimpleUserTrade fSimpleUserTrade = this.fSimpleUserTradeService.get(id);
			if(fSimpleUserTrade.getStateType()==-1){
				WebUtil.printText("该申请刚被另一操作员拒绝，请刷新后查看!",resp);
			}else{
				if(fSimpleUserTrade.getStateType()!=1){
					WebUtil.printText("该申请已经被处理过，请刷新后查看!",resp);
					return;
				}
				fSimpleUserTrade.setStateType(-1);
				fSimpleUserTrade.setUseTranDay(0);  //帐号使用时间为0
				
				Integer tranLever = fSimpleUserTrade.getTranLever();
				BigDecimal tranBond=fSimpleUserTrade.getTraderBond();
				Integer businessType=fSimpleUserTrade.getBusinessType();

				BigDecimal totalTranBond = tranLever == null || tranLever <= 0 || businessType == 2  ? tranBond : tranBond.multiply(new BigDecimal(tranLever), MathContext.DECIMAL32);
				
				BigDecimal teeManage=fSimpleUserTrade.getFeeManage();
				
				String tradeNo = fSimpleUserTrade.getProgramNo();
				
				BigDecimal actualMoney=totalTranBond.add(teeManage);
				String actualMoneyStr = actualMoney.toString();
				String remark = "股指期货申请被拒绝，保证金和管理费退款";
				String sysType = TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS;
				
				try {
					this.fSimpleUserTradeService.updateFSimpleUserTrade(fSimpleUserTrade, tradeNo, mobileNo, actualMoneyStr, remark, sysType);
					WebUtil.printText("success",resp);
				} catch (Exception e) {
					e.printStackTrace();
					WebUtil.printText("服务器忙!",resp);
				}
			}
		} 
		catch (BusinessException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙!",resp);
		}
	}
	
	/**
	 * 通过申请，并分配账号密码
	 * @param id
	 * @param account
	 * @param password
	 * @param resp
	 */
	@RequestMapping(value="pass")
	public void pass(String id,String account,String password,HttpServletResponse resp){
		try {
			FSimpleUserTrade fSimpleUserTrade = this.fSimpleUserTradeService.get(id);
			Integer day = fSimpleUserTrade.getTranDay();
			if(fSimpleUserTrade.getStateType()!=1 && fSimpleUserTrade.getStateType()!=2){
				WebUtil.printText("该申请刚被另一操作员处理，请刷新后查看!",resp);
			}else{
				fSimpleUserTrade.setStateType(2);
				fSimpleUserTrade.setTranAccount(account);
				fSimpleUserTrade.setTranPassword(password);
				long nowDateLong =  TypeConvert.dbDefaultDate();
				String nowDate = TypeConvert.long1000ToDateStr(nowDateLong).replace("-", "");
				// 判断账户分配时间是否超过当前2点
				Date today = Dates.parse(nowDate, Dates.CHINESE_DATE_FORMAT_LONG);
				long two = today.getTime()/1000+3600*14;
				String startDate =null;
				if(nowDateLong>=two){
					fSimpleUserTrade.setAppStarttime(today.getTime()/1000+3600*24);
					startDate = TypeConvert.long1000ToDateStr(today.getTime()/1000+3600*24).replace("-", "");
				}else{
					fSimpleUserTrade.setAppStarttime(nowDateLong);
					startDate = nowDate;
				}
				
				if(day != null){
					String endDateString = tradeDayService.getExpirationDate(startDate, day);
					Date trade = Dates.parse(endDateString, Dates.CHINESE_DATE_FORMAT_LONG);
					fSimpleUserTrade.setAppEndtime(trade.getTime()/1000);
				}
				this.fSimpleUserTradeService.update(fSimpleUserTrade);
				
				WebUtil.printText("success",resp);
			}
		} 
		catch (BusinessException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙!",resp);
		}
	}
	/**
	 * 录入交易信息
	 * @param id
	 * @param profit
	 * @param commission
	 * @param resp
	 */
	@RequestMapping(value="input")
	public void input(String id,String profit,String commission,HttpServletResponse resp){
		try {
			FSimpleUserTrade fSimpleUserTrade = this.fSimpleUserTradeService.get(id);
			
			String startDate =  new SimpleDateFormat("yyyyMMdd").format(new Date(fSimpleUserTrade.getAppStarttime()*1000));
			String endDate =  new SimpleDateFormat("yyyyMMdd").format(new Date());
			int useTranDay =  tradeDayService.getTradeDays(startDate, endDate);
			useTranDay = fSimpleUserTrade.getTranDay() == null || useTranDay <= fSimpleUserTrade.getTranDay() ? useTranDay : fSimpleUserTrade.getTranDay();
			//单管理费
			BigDecimal manageAmount = new BigDecimal("0");
			if(useTranDay == 1){
				manageAmount = new BigDecimal("0");
			}
			else{
				if(useTranDay == 2 || useTranDay == 3){
					manageAmount = new BigDecimal("200");
				}else if(useTranDay == 4 || useTranDay == 5){
					manageAmount = new BigDecimal("150");
				}else if(useTranDay == 6 || useTranDay == 7){
					manageAmount = new BigDecimal("120");
				}else if(useTranDay == 8 || useTranDay == 9){
					manageAmount = new BigDecimal("100");
				}else if(useTranDay == 10){
					manageAmount = new BigDecimal("80");
				}
			}
			//总管理费
			BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount).multiply(new BigDecimal(useTranDay), MathContext.DECIMAL32);
			
			//返还管理费
			BigDecimal returnFeeManage = new BigDecimal("0");
			if(fSimpleUserTrade.getBusinessType() ==1 && totalManageAmount.compareTo(fSimpleUserTrade.getFeeManage()) < 0){
				returnFeeManage = fSimpleUserTrade.getFeeManage().subtract(totalManageAmount);
			}
			fSimpleUserTrade.setUseTranDay(useTranDay);  //实际操盘天数
			fSimpleUserTrade.setReturnFeeManage(returnFeeManage); //返还管理费
			
			BigDecimal tranBond=fSimpleUserTrade.getTraderBond();
			Integer businessType=fSimpleUserTrade.getBusinessType();
			
			Integer tranLever = fSimpleUserTrade.getTranLever();
			BigDecimal totalTranBond = tranLever == null || tranLever <= 0 || businessType == 2 ? tranBond : tranBond.multiply(new BigDecimal(tranLever), MathContext.DECIMAL32);
			BigDecimal tranProfitLoss=new BigDecimal(profit.replace(" ", ""));
			BigDecimal tranCommission=new BigDecimal(commission.replace(" ", ""));
			BigDecimal appendTraderBond = fSimpleUserTrade.getAppendTraderBond() == null ? new BigDecimal("0") : fSimpleUserTrade.getAppendTraderBond();
			BigDecimal endAmount=totalTranBond.add(tranProfitLoss).add(appendTraderBond).subtract(tranCommission).add(returnFeeManage);
			fSimpleUserTrade.setTranProfitLoss(tranProfitLoss);
			fSimpleUserTrade.setTranCommission(tranCommission);
			fSimpleUserTrade.setEndAmount(endAmount);
			this.fSimpleUserTradeService.update(fSimpleUserTrade);
			WebUtil.printText("success",resp);
			
		} 
		catch (BusinessException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙!",resp);
		}
	}
	
	/**
	 * 结算
	 * @param id
	 * @param resp
	 */
	@RequestMapping(value="end")
	public void end(String id,HttpServletResponse resp){
		try {
			FSimpleUserTrade fSimpleUserTrade = this.fSimpleUserTradeService.get(id);
			if(fSimpleUserTrade.getStateType()==3){
				WebUtil.printText("该方案已被其他操作员结算，请刷新后查看!",resp);
			}else{
				fSimpleUserTrade.setStateType(3);
				fSimpleUserTrade.setEndTime(Dates.getCurrentLongDate());
				this.fSimpleUserTradeService.update(fSimpleUserTrade);
				WebUtil.printText("success",resp);
			}
		} 
		catch (BusinessException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙!",resp);
		}catch (Exception e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙!",resp);
		}
	}
	
	/**
	 * 追加保证金列表
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/appendMoney", method = RequestMethod.POST)
	@ResponseBody
	public void getAppendMoneyList(EasyUiPageInfo easyUiPage, Model model,HttpServletResponse response,HttpServletRequest request) throws Exception{
		try {
			DataGridVo<FSimpleAppendLevelMoneyVo> grid = new DataGridVo<FSimpleAppendLevelMoneyVo>();
			PageInfo<FSimpleAppendLevelMoneyVo> pageInfo = new PageInfo<FSimpleAppendLevelMoneyVo>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			
			if(connVo.isExcel()){
				List<FSimpleAppendLevelMoneyVo> data = fSimpleAppendLevelMoneyService.queryList(connVo);
				connVo.isNotExcel(data, response,"补充保证金.xls");
				return;
			}
			
			pageInfo = fSimpleAppendLevelMoneyService.queryList(pageInfo, connVo);
			
			grid.add(pageInfo.getPageResults());
			grid.setTotal(pageInfo.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), response);
		} 
		catch (Exception e) {
		}
	}
	
	/**
	 * 处理追加保证金列表
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/handleAppendMoney")
	@ResponseBody
	public JsonResult handleAppendMoney(HttpServletRequest request,
			@RequestParam("ids") String  ids){
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray)){
			log.debug("处理补充保证金时，未选择数据！");
			return new JsonResult(Boolean.FALSE,"处理补充保证金时，未选择数据！");
		}
		fSimpleAppendLevelMoneyService.updateFSimpleAppendLevelMoney(idArray, 1);
		
		return new JsonResult("操作成功");
	}
}
