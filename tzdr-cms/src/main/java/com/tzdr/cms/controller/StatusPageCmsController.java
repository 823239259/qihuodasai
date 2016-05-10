package com.tzdr.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tzdr.business.cms.quartz.QuartzCrudService;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.business.service.generalize.UserCommissionIncomeService;
import com.tzdr.business.service.pay.UserFundFailService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.entity.HundsunToken;
import com.tzdr.domain.vo.QuartzVo;
import com.tzdr.domain.web.entity.UserCommission;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserFundFail;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月4日下午2:51:35
 */
@Controller
@RequestMapping("/admin/status")
public class StatusPageCmsController extends BaseCmsController<User>{
	public static final Logger log = LoggerFactory.getLogger(StatusPageCmsController.class);


	private static final String QUARTZ_PRIFXX = "quartz.";
	
	@Autowired
	private QuartzCrudService quartzCrudService;
	
	@Autowired
	private UserCommissionIncomeService usercommissionIncomeService;
	
	@Autowired
	private UserFundFailService userFundFailService;
	
	@RequestMapping(value = "/page")
	public String list(HttpServletRequest request) throws Exception 
	{
		return ViewConstants.StatusPageViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/statusUpdate")
	@ResponseBody
	public String statusUpdate(HttpServletRequest request,HttpServletResponse response) {
		//测试静态
		try {
			String httpUrl = "http://localhost:8080/tzdr-web/statusPage/statusUpdate";
			String pageStatus = request.getParameter("templateName");
			if (pageStatus == null || "".equals(pageStatus)) {
				return "none";
			}
			TypeConvert.httpClient(httpUrl,"templateName",pageStatus);
			return "success";
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}	
	
	@RequestMapping(value = "/refreshToken")
	@ResponseBody
	public JsonResult  refreshToken(String environment,HttpServletRequest request){
		
		String operatorNo = null;
		String password = null;
		
		if (StringUtil.equals(Constants.Environment.API,environment)){
			operatorNo = MessageUtils.message("api.operator.no");
			password = MessageUtils.message("api.operator.password");
		}
		
		if (StringUtil.equals(Constants.Environment.APP1,environment)){
			operatorNo = MessageUtils.message("app1.operator.no");
			password = MessageUtils.message("app1.operator.password");
		}
		
		if (StringUtil.equals(Constants.Environment.APP2,environment)){
			operatorNo = MessageUtils.message("app2.operator.no");
			password = MessageUtils.message("app2.operator.password");
		}
		
		if (StringUtil.equals(Constants.Environment.CMS,environment) 
				|| (StringUtil.isBlank(operatorNo) && StringUtil.isBlank(password))){
			operatorNo = ConfUtil.getContext("hundsun.manager.operator.no");
			password = ConfUtil.getContext("hundsun.manager.operator.password");
		}
		try {
			HundsunToken hundsunToken = HundsunJres.getInstance()
					.findHundsunToken(operatorNo);
			if (hundsunToken != null) {
				HundsunJres.getInstance().LogoutOnce(hundsunToken.getToken());
			}
			HundsunToken newHundsunToken = HundsunJres.getInstance().get(operatorNo,password);
			
			if (ObjectUtil.equals(null, newHundsunToken) 
					|| StringUtil.isBlank(newHundsunToken.getToken())){
				return new JsonResult(false,"刷新恒生Token失败！");
			}
		} catch (Exception e) {
			String errorMessage = Exceptions.getStackTraceAsString(e);
			log.error(errorMessage);
			EmailExceptionHandler.getInstance().HandleException(e, "刷新恒生Token失败！",errorMessage);
			return new JsonResult(false,"刷新恒生Token失败！");
		}
		return new JsonResult("刷新成功！");
	}
	@Override
	public BaseService<User> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:system:refreshData");
	}

	/**
	 * 获取定时任务列表
	 * @return
	 */
	@RequestMapping("/getAllTriggers")
	@ResponseBody
	public Object getAllTriggers(){
		List<QuartzVo> quartzVos  = quartzCrudService.getQuartzTriggers();
		JSONArray  jsonArray = new JSONArray();
		for (QuartzVo quartzVo:quartzVos){
			JSONObject  jsonObject  = new JSONObject();
			jsonObject.put("valueField", quartzVo.getTriggerName());
			jsonObject.put("textField",MessageUtils.message(QUARTZ_PRIFXX+quartzVo.getTriggerName()));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	@RequestMapping(value = "/updateQuartz")
	@ResponseBody
	public JsonResult  updateQuartz(HttpServletRequest request,
			@RequestParam("triggerName") String triggerName,
			@RequestParam("nextFireTime") String nextFireTime){
		if (StringUtil.isBlank(nextFireTime)||StringUtil.isBlank(triggerName)){
			return new JsonResult(false,"参数有误，请重新输入！");
		}
		quartzCrudService.updateQuartz(triggerName, nextFireTime);
		return new JsonResult("设置成功！");
	}
	
	@RequestMapping(value = "/executeFailQuartz")
	@ResponseBody
	public JsonResult  executeFailQuartz(HttpServletRequest request,@RequestParam("nextFireTime") String nextFireTime){
		if (StringUtil.isBlank(nextFireTime)){
			return new JsonResult(false,"参数有误，请重新输入！");
		}
		
		Long runBatchTime = TypeConvert.strToZeroDate1000(nextFireTime, 0);

		List<UserCommission> userCommissions =  usercommissionIncomeService.findUserCommissions(1, runBatchTime);
		if(userCommissions != null && userCommissions.size() > 0){
			return new JsonResult(false,"该时间管理费已经执行过，不能重复执行！");
		}
		
		//保存佣金流行单
		List<UserCommission> userCommissionList = usercommissionIncomeService.saveUserCommission(runBatchTime);

		//结算用户佣金收入-资金明细【佣金收入】
		List<UserFund> userFundList = usercommissionIncomeService.settlementUserCommission(userCommissionList,runBatchTime);

		if(userFundList != null && !userFundList.isEmpty()){  //遍历用户佣金收入资金明细
			for (UserFund userFund : userFundList) {
				//更新结算用户佣金收入情况信息(更新用户帐号、保存结算用户总佣金收入信息)
				this.saveCommissionUserFund(userFund);
			}
		}
		return new JsonResult("执行成功！");
	}
	
	/**
	 * @Description: 更新结算用户佣金收入情况信息(更新用户帐号、保存结算用户总佣金收入信息)
	 * @Title: updateSettlementUserCommission
	 * @param userFund   佣金收入
	 * @return void    返回类型
	 */
	private void saveCommissionUserFund(UserFund userFund){

		//判断资金明细【佣金收入】
		if(userFund == null) return;   

		Exception ex = null;   //定义异常对象

		for (int i = 0; i < 5; i++) { //重置3次
			ex = null;
			try {
				usercommissionIncomeService.saveCommissionUserFund(userFund);    //更新用户账户信息、保存用户总佣金流水单
				break;
			} catch (Exception e) {
				ex = e;
			}
		}

		if(ex != null){    //保存资金明细-佣金收入失败
			try {
				//创建失败的用户总佣金流水单
				UserFundFail userFundFail = new UserFundFail();
				userFundFail.setUid(userFund.getUid());
				userFundFail.setType(userFund.getType());
				userFundFail.setMoney(userFund.getMoney());
				userFundFail.setAddtime(userFund.getAddtime());
				userFundFail.setRemark(userFund.getRemark());
				userFundFail.setPayStatus((short)0);
				userFundFail.setRunStatus((short)0);
				userFundFail.setTypeStatus(1);
				usercommissionIncomeService.saveUserFundFail(userFundFail);  //保存失败的用户佣金流水单
			} catch (Exception e) {
				String detailed = "【手动】【保存失败用户佣金明细失败】用户编号："+userFund.getUid()+",用户总佣金收入："+userFund.getMoney();
				this.HandleException(ex, detailed, "updateSettlementUserCommission");
			}
			
			String detailed = null;  //异常数据
			try {
				if(ex != null){
					detailed = "【手动】【更新用户结算佣金】用户编号："+userFund.getUid()+",用户总佣金收入："+userFund.getMoney();
					this.HandleException(ex, detailed, "updateSettlementUserCommission");
				}
			} catch (Exception e) {
				log.error("detailed:{},methodName:saveCommissionUserFund,emailException:{}",detailed, ex.getMessage());
			}
		}
	}

	/**
	 * @Description: 异常
	 * @Title: HandleException
	 * @param e
	 * @param detailed  明细
	 * @param method    方法
	 * @return void    返回类型
	 */
	private void HandleException(Exception e, String detailed, String method) {
		try {
		// 发送邮件
		String devEmail = ConfUtil.getContext("mail.to.dev");
		List<String> pramas = Lists.newArrayList();
		String methodName = this.getClass().getName() + "." + method;
		String exception = Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(detailed);
		pramas.add(exception);
		
		EmailUtils.getInstance().sendMailTemp(devEmail,
					"commissionIncomeExceptionEmail", pramas);
		} catch (Exception ex) {
			log.error("email:", ex.getMessage());
		}
		log.error(this.getClass().getName() + "." + method + ",error::{}", Exceptions.getStackTraceAsString(e));
	}
	
	/**
	 * 处理资金明细失败数据【佣金收入】
	 * @param request
	 * @param nextFireTime
	 * @return
	 */
	@RequestMapping(value = "/handleUserFundFail")
	@ResponseBody
	public JsonResult  handleUserFundFail(HttpServletRequest request){
		
		//处理资金明细失败数据【佣金收入】
		userFundFailService.saveUserFundByStatusAndType(13);

		return new JsonResult("执行成功！");
	}
}
