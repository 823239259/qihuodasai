package com.tzdr.web.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.tzdr.business.service.generalize.CommissionIncomeService;
import com.tzdr.business.service.generalize.UserCommissionIncomeService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.web.entity.UserCommission;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserFundFail;

/**
* @Description: 佣金结算定时器
* @ClassName: CommissionIncome
* @author wangpinqun
* @date 2015年1月15日 上午11:09:21
 */
@Component
public class CommissionIncome {
	
	public static final Logger logger = LoggerFactory.getLogger(CommissionIncome.class);

	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private CommissionIncomeService commissionIncomeService;
	
	@Autowired
	private static UserCommissionIncomeService usercommissionIncomeService;
	
	public void executeCommissionIncome(){
		logger.info("------------------佣金返点任务--------begin-------------------");
		try 
		{
			//获取用户佣金执行实例
			usercommissionIncomeService = SpringUtils.getBean(UserCommissionIncomeService.class);

			//保存佣金流行单
			List<UserCommission> userCommissionList = usercommissionIncomeService.saveUserCommission(null);

			//结算用户佣金收入-资金明细【佣金收入】
			List<UserFund> userFundList = usercommissionIncomeService.settlementUserCommission(userCommissionList,null);

			if(userFundList != null && !userFundList.isEmpty()){
				for (UserFund userFund : userFundList) {
					//更新结算用户佣金收入情况信息(更新用户帐号、保存结算用户总佣金收入信息)
					this.saveCommissionUserFund(userFund);
				}
			}
		} catch (BeansException e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			HandleException(e, "executeInternal");
		}
		logger.info("------------------佣金返点任务--------end-------------------");	
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
				userFundFail.setTypeStatus(userFund.getTypeStatus());
				userFundFail.setRunStatus((short)0);
				usercommissionIncomeService.saveUserFundFail(userFundFail);  //保存失败的用户佣金流水单
			} catch (Exception e) {
				String detailed = "【"+(userFund.getTypeStatus() == Integer.valueOf(1) ? "手动" : "自动")+"】【保存失败用户佣金明细失败】用户编号："+userFund.getUid()+",用户总佣金收入："+userFund.getMoney();
				this.HandleException(ex, detailed, "updateSettlementUserCommission");
			}
			
			String detailed = null;  //异常数据
			try {
				if(ex != null){
					detailed = "【"+(userFund.getTypeStatus() == Integer.valueOf(1) ? "手动" : "自动")+"】【更新用户结算佣金】用户编号："+userFund.getUid()+",用户总佣金收入："+userFund.getMoney();
					this.HandleException(ex, detailed, "updateSettlementUserCommission");
				}
			} catch (Exception e) {
				logger.error("detailed:{},methodName:saveCommissionUserFund,emailException:{}",detailed, ex.getMessage());
			}
		}
	}

	/**
	 * 异常处理
	 * @param e
	 * @param method
	 */
	private void HandleException(Exception e, String method) {
		// 发送邮件
		String devEmail = ConfUtil.getContext("mail.to.dev");
		List<String> pramas = Lists.newArrayList();
		String methodName = this.getClass().getName() + "." + method;
		String exception = Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,"exceptionemail", pramas);
		} catch (Exception ex) {
			logger.error("email:", ex.getMessage());
		}
		logger.error(methodName + ",error::{}", exception);
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
		// 发送邮件
		String devEmail = ConfUtil.getContext("mail.to.dev");
		List<String> pramas = Lists.newArrayList();
		String methodName = this.getClass().getName() + "." + method;
		String exception = Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(detailed);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,
					"commissionIncomeExceptionEmail", pramas);
		} catch (Exception ex) {
			logger.error("email:", ex.getMessage());
		}
		logger.error(methodName + ",error::{}", exception);
	}
}
