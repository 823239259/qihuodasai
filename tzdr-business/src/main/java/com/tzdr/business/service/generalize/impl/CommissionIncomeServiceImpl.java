package com.tzdr.business.service.generalize.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.service.generalize.CommissionIncomeService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.domain.dao.generalize.UserCommissionDao;
import com.tzdr.domain.vo.UserFundVo;
import com.tzdr.domain.web.entity.UserCommission;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;

/**
 * @Description: TODO(佣金业务信息管理业务实现层)
 * @ClassName: CommissionIncomeServiceImpl
 * @author wangpinqun
 * @date 2015年1月14日 下午2:57:39
 */
@Transactional(propagation=Propagation.REQUIRED)
@Service("commissionIncomeService")
public class CommissionIncomeServiceImpl extends BaseServiceImpl<UserCommission, UserCommissionDao> implements CommissionIncomeService {

	public static final Logger logger = LoggerFactory.getLogger(CommissionIncomeServiceImpl.class);

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private WUserService wuserService;

	@Override
	public void saveUserCommission(UserCommission userCommission) {
		this.save(userCommission);
	}

	@Override
	public void saveUserCommission(List<UserCommission> userCommissions) {
		this.saves(userCommissions);
	}

	@Override
	public void commissionCalculation() {
		try {
			List<UserCommission> userCommissionList = this.budgetCommission();   //预算佣金
			if(!CollectionUtils.isEmpty(userCommissionList)){  //判断佣金流水单集合
				this.saveUserCommission(userCommissionList);   //保存佣金流水单信息
				Map<String, Double> userCommissionMap = this.settlementUserCommission(userCommissionList);   //结算用户佣金收入
				this.updateSettlementUserCommission(userCommissionMap);   //更新用户帐号信息、保存结算用户总佣金收入信息
				userCommissionMap = null; //注销集合
			}
			userCommissionList = null;  //注销集合
		} catch (Exception e) {
			this.HandleException(e, null, "commissionCalculation");
		}
	}

	/**
	 * @Description: TODO(预算用户佣金，返回用户佣金流水单)
	 * @Title: budgetCommission
	 * @return List<UserCommission>    返回类型
	 */
	private List<UserCommission> budgetCommission() {
		List<UserCommission> userCommissionList = new ArrayList<UserCommission>();  //用户佣金流水单集合
		List<UserFundVo> userFundVoList = this.queryManagementFee();   //获取昨天收到的管理费信息
		if(!CollectionUtils.isEmpty(userFundVoList)){
			for (UserFundVo userFundVo : userFundVoList) {  //遍历用户管理费信息
				try {
					WUser wuser = wuserService.get(userFundVo.getUid());
					Double rebate = wuser.getRebate() == null ? 0.00 : wuser.getRebate();          //返点
					Double manageMoney = userFundVo.getMoney() == null ? 0.00 : -userFundVo.getMoney(); //管理费
					Double incomeMoney = BigDecimalUtils.round2(BigDecimalUtils.mul(BigDecimalUtils.div(rebate, 100), manageMoney), 2) ; //预算佣金
					String serialNumber = UUID.randomUUID().toString().replace("-", "");   //佣金流水号
					UserCommission userCommission = new UserCommission();   //流水单
					userCommission.setUid(wuser.getId());    	   //用户编号
					userCommission.setManageMoney(manageMoney);    //管理费
					userCommission.setRebate(rebate);              //返点
					userCommission.setBudgetMoney(incomeMoney);    //预算佣金
					userCommission.setMoney(incomeMoney);          //收入佣金
					userCommission.setSerialNumber(serialNumber);  //流水号
					userCommission.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"收入佣金"+incomeMoney+"元。"); //描述
					if(manageMoney <= 0) continue;
					List<UserCommission> parentUserCommissionList = new ArrayList<UserCommission>();  //单个用户父节点佣金流水单
					this.getParentNode(wuser,userCommission,parentUserCommissionList);  //递归父节点
					userCommissionList.add(userCommission);        //添加佣金流水单
					if(!CollectionUtils.isEmpty(parentUserCommissionList)){
						for (UserCommission parentUserCommission : parentUserCommissionList) {
							userCommissionList.add(parentUserCommission);   //添加父节点佣金流水单
						}
					}
				} catch (Exception e) {
					String detailed = "【预算用户佣金】用户编号："+userFundVo.getUid()+",管理费："+userFundVo.getMoney();
					this.HandleException(e, detailed, "budgetCommission");
				}
			}
		}
		userFundVoList = null;      //注销集合
		return userCommissionList;
	}

	/**
	 * @Description: TODO(获取昨天的所有已支付的用户管理费列表信息)
	 * @Title: queryManagementFee
	 * @return List<UserFundVo>    返回类型
	 */
	private List<UserFundVo> queryManagementFee(){
		//获取昨天收到的管理费信息
		List<UserFundVo> userFundVoList = null;
		try {
			userFundVoList = userFundService.getYestodayFee();
		} catch (Exception e) {
			this.HandleException(e, null, "queryManagementFee");
		}
		return userFundVoList;
	}
	
	/**
	 * @Description: TODO(递归父节点)
	 * @Title: getParentNode
	 * @param wuser           用户基本信息
	 * @param userCommission  下级佣金流水单
	 * @param parentUserCommissionList
	 * @return void    返回类型
	 */
	private void getParentNode(WUser wuser,UserCommission userCommission,List<UserCommission> parentUserCommissionList){
		WUser parentNode =  null;   //用户父节点
		try {
			parentNode =wuser==null?null:wuser.getParentNode();  //获取用户父节点
			if(wuser != null && userCommission != null){
				if(parentNode != null && !StringUtil.isBlank(parentNode.getId()) && !("-2".equals(parentNode.getUserType()) || "-3".equals(parentNode.getUserType()) || "-1".equals(parentNode.getUserType()))){
					UserCommission incomeUserCommission = this.buildIncomeUserCommission(parentNode, userCommission); //构建收入佣金流水单
					parentUserCommissionList.add(incomeUserCommission);
					/*UserCommission spendUserCommission = this.buildSpendUserCommission(parentNode, userCommission);   //构建支出佣金流水单
					parentUserCommissionList.add(spendUserCommission);*/
					this.getParentNode(parentNode, incomeUserCommission, parentUserCommissionList);    //递归父节点
				}
			}
		} catch (Exception e) {
			String detailed = null;
			if(parentNode == null){
				detailed = "【用户佣金收入】用户编号："+null+",管理费："+userCommission.getManageMoney()+",佣金来源用户编号："+(StringUtil.isBlank(userCommission.getIncomeSourceUid()) ? userCommission.getUid() : userCommission.getIncomeSourceUid())
						+",当前管理费流水号："+userCommission.getSerialNumber()+",我的返点："+null+",下级用户编号："+userCommission.getUid()+",下级返点："+userCommission.getRebate()+",下级佣金收入："+userCommission.getMoney();
			}else{
				detailed = "【用户佣金收入】用户编号："+parentNode.getId()+",管理费："+userCommission.getManageMoney()+",佣金来源用户编号："+(StringUtil.isBlank(userCommission.getIncomeSourceUid()) ? userCommission.getUid() : userCommission.getIncomeSourceUid())
						+",当前管理费流水号："+userCommission.getSerialNumber()+",我的返点："+parentNode.getRebate()+",下级用户编号："+userCommission.getUid()+",下级返点："+userCommission.getRebate()+",下级佣金收入："+userCommission.getMoney();
			}
			this.HandleException(e, detailed, "getParentNode");
		}
	}

	/**
	 * @Description: TODO(构建收入佣金流水单)
	 * @Title: buildIncomeUserCommission
	 * @param wuser           用户基本信息
	 * @param userCommission  下级流水单信息
	 * @return UserCommission    返回类型
	 */
	private UserCommission buildIncomeUserCommission(WUser wuser,UserCommission userCommission){
		if(wuser == null || userCommission == null) return null;
		UserCommission incomeUserCommission = new UserCommission(); //流水单
		Double rebate = wuser.getRebate() == null ? 0.00 : wuser.getRebate();          				//返点
		Double manageMoney = userCommission.getManageMoney() == null ? 0.00 : userCommission.getManageMoney();  //管理费
		Double incomeMoney = BigDecimalUtils.round2(BigDecimalUtils.mul(BigDecimalUtils.div(rebate, 100), manageMoney), 2) ; //预算佣金
		incomeUserCommission.setUid(wuser.getId());                 //用户编号
		incomeUserCommission.setManageMoney(manageMoney);           //管理费
		incomeUserCommission.setRebate(rebate);                     //返点
		incomeUserCommission.setBudgetMoney(incomeMoney);           //预算佣金
		incomeUserCommission.setSubordinateRebate(userCommission.getRebate());            //下级返点
		Double money = BigDecimalUtils.subRound(incomeMoney, userCommission.getBudgetMoney(), 2);  //收入佣金
		incomeUserCommission.setMoney(money);   //收入佣金
		incomeUserCommission.setDeductChildUid(userCommission.getUid());  //支出下级佣金用户编号
		incomeUserCommission.setIncomeSourceUid(StringUtil.isBlank(userCommission.getIncomeSourceUid()) ? userCommission.getUid() : userCommission.getIncomeSourceUid()); //佣金来源用户编号
		incomeUserCommission.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"收入佣金"+money+"元。"); //描述
		incomeUserCommission.setSerialNumber(userCommission.getSerialNumber());  //流水号
		return incomeUserCommission;
	}

	/**
	 * @Description: TODO(构建支出佣金流水单)
	 * @Title: buildSpendUserCommission
	 * @param wuser            用户基本信息
	 * @param userCommission   下级流水单信息
	 * @return UserCommission    返回类型
	 */
	@Deprecated
	private UserCommission buildSpendUserCommission(WUser wuser,UserCommission userCommission){
		if(wuser == null || userCommission == null) return null;
		UserCommission spendUserCommission = new UserCommission();  //流水单
		Double rebate = userCommission.getRebate() == null ? 0.00 : userCommission.getRebate();          				//下级返点
		Double manageMoney = userCommission.getManageMoney() == null ? 0.00 : userCommission.getManageMoney();          //管理费
		Double incomeMoney = userCommission.getMoney() ;            //下级收入佣金
		spendUserCommission.setUid(wuser.getId());                  //用户编号
		spendUserCommission.setManageMoney(manageMoney);            //管理费
		spendUserCommission.setRebate(rebate);                      //下级返点
		spendUserCommission.setMoney(-incomeMoney);                 //支出下级佣金
		spendUserCommission.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"支出佣金"+incomeMoney+"元。"); //描述
		spendUserCommission.setIncomeSourceUid(StringUtil.isBlank(userCommission.getIncomeSourceUid()) ? userCommission.getUid() : userCommission.getIncomeSourceUid()); //佣金来源用户编号
		spendUserCommission.setDeductChildUid(userCommission.getUid());  //支出下级佣金用户编号
		spendUserCommission.setSerialNumber(userCommission.getSerialNumber());  //流水号
		return spendUserCommission;
	}

	/**
	 * @Description: TODO(结算用户佣金收入情况)
	 * @Title: settlementUserCommission
	 * @param userCommissionList
	 * @return Map<String,Double>    返回类型  key:uid  value:佣金
	 */
	private Map<String, Double> settlementUserCommission(List<UserCommission> userCommissionList){
		if(CollectionUtils.isEmpty(userCommissionList)) return null;
		Map<String, Double> userCommissionMap = new HashMap<String, Double>();   //结算用户佣金收入
		for (UserCommission userCommission : userCommissionList) {
			try {
				if(userCommissionMap.containsKey(userCommission.getUid())){
					Double userMoney =BigDecimalUtils.add2(userCommissionMap.get(userCommission.getUid()),userCommission.getMoney());
					userCommissionMap.put(userCommission.getUid(),  userMoney);
				}else{
					userCommissionMap.put(userCommission.getUid(),  userCommission.getMoney());
				}
			} catch (Exception e) {
				String detailed = "【结算用户佣金】记录编号："+userCommission.getId()+",用户编号："+userCommission.getUid()+",管理费："+userCommission.getManageMoney()+",佣金来源用户编号："+(StringUtil.isBlank(userCommission.getIncomeSourceUid()) ? userCommission.getUid() : userCommission.getIncomeSourceUid())
						+",当前管理费流水号："+userCommission.getSerialNumber()+",返点："+userCommission.getRebate()+",佣金："+userCommission.getMoney()+",下级用户编号："+userCommission.getUid();
				this.HandleException(e, detailed, "settlementUserCommission");
			}
		}
		return userCommissionMap;
	}

	/**
	 * @Description: TODO(更新结算用户佣金收入情况信息(更新用户帐号、保存结算用户总佣金收入信息))
	 * @Title: updateSettlementUserCommission
	 * @param userCommissionMap     用户佣金收入情况（key:uid  value:佣金）
	 * @return void    返回类型
	 */
	private void updateSettlementUserCommission(Map<String, Double> userCommissionMap){
		if(userCommissionMap != null && !userCommissionMap.isEmpty()){  //判断用户佣金结算信息
			for (Entry<String, Double> entry: userCommissionMap.entrySet()) {
				try {
					if(entry.getValue() > 0){
						//创建用户总佣金流水单
						UserFund userFund = new UserFund();
						userFund.setUid(entry.getKey());
						userFund.setType(13);
						userFund.setMoney(BigDecimalUtils.round2(entry.getValue(),2));
						userFund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd")+"收入佣金"+BigDecimalUtils.round2(entry.getValue(),2)+"元。");
						userFund.setPayStatus((short)1);
						userFund.setAddtime((new Date().getTime()/1000));
						userFund.setUptime((new Date().getTime()/1000));
						userFundService.arrearsProcess(userFund);    //更新用户账户信息、保存用户总佣金流水单
					}
				} catch (Exception e) {
					String detailed = "【更新用户结算佣金】用户编号："+entry.getKey()+",用户总佣金收入："+entry.getValue();
					this.HandleException(e, detailed, "updateSettlementUserCommission");
				}
			}
			userCommissionMap = null;  //注销集合
		}
	}

	/**
	 * @Description: TODO(异常)
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
