package com.tzdr.business.service.generalize.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.service.generalize.UserCommissionIncomeService;
import com.tzdr.business.service.pay.UserFundFailService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.generalize.UserCommissionDao;
import com.tzdr.domain.vo.UserFundVo;
import com.tzdr.domain.web.entity.UserCommission;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserFundFail;
import com.tzdr.domain.web.entity.WUser;

/**
 * @Description: 佣金业务信息管理业务实现层
 * @ClassName: UserCommissionIncomeServiceImpl
 * @author wangpinqun
 * @date 2015年06月25日 下午2:57:39
 */
@Transactional(propagation=Propagation.REQUIRED)
@Service("userCommissionIncomeService")
public class UserCommissionIncomeServiceImpl extends BaseServiceImpl<UserCommission, UserCommissionDao> implements
		UserCommissionIncomeService {

	public static final Logger logger = LoggerFactory.getLogger(CommissionIncomeServiceImpl.class);

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private UserFundFailService userFundFailService;
	
	/**
	 * 跑批时间： 系统自动跑批默认该值为空，手动跑批时间为跑批设置时间
	 */
	private Long runBatchTime;
	
	@Override
	public List<UserCommission> findUserCommissions(int type, long manageFeeTime) {

		Long startTimeLong = TypeConvert.strToZeroDate1000(TypeConvert.long1000ToDatetimeStr(manageFeeTime), 0);;
		Long endTimeLong = TypeConvert.strToZeroDate1000(TypeConvert.long1000ToDatetimeStr(manageFeeTime), 1,-1);
		
		return this.getEntityDao().findByTypeAndManageFeeTimeBetween(type, startTimeLong, endTimeLong);
	}
	
	@Override
	public void saveBatchUserCommission(List<UserCommission> userCommissions) {
		this.saves(userCommissions);
	}

	@Override
	public List<UserCommission> saveUserCommission(Long runBatchTime) {
		
		this.runBatchTime = runBatchTime;
		List<UserCommission> userCommissionList = this.budgetCommission();   //预算佣金
		if(!CollectionUtils.isEmpty(userCommissionList)){  //判断佣金流水单集合
			this.saveBatchUserCommission(userCommissionList);   //保存佣金流水单信息
		}
		this.runBatchTime = null;
		return userCommissionList;
	}
	
	/**
	 * @Description: 预算用户佣金，返回用户佣金流水单
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
					String remark = DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"收入佣金"+incomeMoney+"元。";
					if(this.runBatchTime != null){
						remark = remark + "佣金来源于"+ TypeConvert.long1000ToDateStr(this.runBatchTime)+"的管理费【手工执行】。";
						userCommission.setType(1);
						userCommission.setManageFeeTime(this.runBatchTime);
					}
					userCommission.setRemark(remark); //描述
					if(manageMoney <= 0) continue;
					List<UserCommission> parentUserCommissionList = new ArrayList<UserCommission>();  //单个用户父节点佣金流水单
					this.getParentNode(wuser,userCommission,parentUserCommissionList);  //递归父节点
					userCommissionList.add(userCommission);        //添加佣金流水单
					if(!CollectionUtils.isEmpty(parentUserCommissionList)){
						for (UserCommission parentUserCommission : parentUserCommissionList) {
							userCommissionList.add(parentUserCommission);   //添加父节点佣金流水单
						}
					}
				}catch (Exception e) {
					String detailed = "【预算用户佣金】用户编号："+userFundVo.getUid()+",管理费："+userFundVo.getMoney();
					this.HandleException(e, detailed, "budgetCommission");
				}
			}
		}
		userFundVoList = null;      //注销集合
		return userCommissionList;
	}
	
	/**
	 * @Description: 获取昨天的所有已支付的用户管理费列表信息
	 * @Title: queryManagementFee
	 * @return List<UserFundVo>    返回类型
	 */
	private List<UserFundVo> queryManagementFee(){
		Long startTimeLong = null;
		Long endTimeLong = null;
		if(this.runBatchTime == null){
			startTimeLong = Dates.getYestodayZeroLong();
			endTimeLong = Dates.getCurrentLongDay();
		}else{
			startTimeLong = TypeConvert.strToZeroDate1000(TypeConvert.long1000ToDatetimeStr(this.runBatchTime), 0);
			endTimeLong = TypeConvert.strToZeroDate1000(TypeConvert.long1000ToDatetimeStr(this.runBatchTime), 1,-1);
		}
		//获取昨天收到的管理费信息
		List<UserFundVo> userFundVoList = userFundService.getManagementFee(startTimeLong, endTimeLong);
		return userFundVoList;
	}
	
	/**
	 * @Description: 递归父节点
	 * @Title: getParentNode
	 * @param wuser           用户基本信息
	 * @param userCommission  下级佣金流水单
	 * @param parentUserCommissionList
	 * @return void    返回类型
	 */
	private void getParentNode(WUser wuser,UserCommission userCommission,List<UserCommission> parentUserCommissionList){
		WUser parentNode =  null;   //用户父节点
		try {
			parentNode = wuser == null ? null : wuser.getParentNode();  //获取用户父节点
			if(wuser != null && userCommission != null){
				if(parentNode != null && !StringUtil.isBlank(parentNode.getId()) && !("-2".equals(parentNode.getUserType()) || "-3".equals(parentNode.getUserType()) || "-1".equals(parentNode.getUserType()))){
					UserCommission incomeUserCommission = this.buildIncomeUserCommission(parentNode, userCommission); //构建收入佣金流水单
					parentUserCommissionList.add(incomeUserCommission);
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
	 * @Description: 构建收入佣金流水单
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
		String remark = DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"收入佣金"+incomeMoney+"元。";
		if(this.runBatchTime != null){
			remark = remark + "佣金来源于"+ TypeConvert.long1000ToDateStr(this.runBatchTime)+"的管理费【手工执行】。";
			incomeUserCommission.setType(1);
			incomeUserCommission.setManageFeeTime(this.runBatchTime);
		}
		incomeUserCommission.setRemark(remark); //描述
		incomeUserCommission.setSerialNumber(userCommission.getSerialNumber());  //流水号
		return incomeUserCommission;
	}

	@Override
	public List<UserFund> settlementUserCommission(List<UserCommission> userCommissionList,Long runBatchTime) {
		
		if(CollectionUtils.isEmpty(userCommissionList)) return null;
		
		List<UserFund> userFundList = null;  //资金明细-用户佣金收入
		
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
		
		if(userCommissionMap != null && !userCommissionMap.isEmpty()){  //判断用户佣金结算信息
			userFundList = new ArrayList<UserFund>();
			for (Entry<String, Double> entry: userCommissionMap.entrySet()) {
				try {
					if(entry.getValue() > 0){
						//创建用户总佣金流水单
						UserFund userFund = new UserFund();
						userFund.setUid(entry.getKey());
						userFund.setType(13);
						userFund.setMoney(BigDecimalUtils.round2(entry.getValue(),2));
						String remark = DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss")+"收入佣金"+BigDecimalUtils.round2(entry.getValue(),2)+"元。";
						if(runBatchTime != null){
							remark = remark + "来源于"+ TypeConvert.long1000ToDateStr(TypeConvert.strToZeroDate1000(TypeConvert.long1000ToDateStr(runBatchTime),1))+"。";
							userFund.setTypeStatus(1);  //手工执行
						}
						userFund.setAddtime((new Date().getTime()/1000));
						userFund.setUptime((new Date().getTime()/1000));
						userFund.setRemark(remark);
						userFund.setPayStatus((short)1);
						userFundList.add(userFund);  //资金明细-用户佣金收入
					}
				} catch (Exception e) {
					String detailed = "【更新用户结算佣金】用户编号："+entry.getKey()+",用户总佣金收入："+entry.getValue();
					this.HandleException(e, detailed, "updateSettlementUserCommission");
				}
			}
		}
		
		userCommissionMap = null;  //注销集合
		
		return userFundList;
	}

	@Override
	public void saveCommissionUserFund(UserFund userFund) {
			userFundService.arrearsProcess(userFund);    //更新用户账户信息、保存用户总佣金流水单
	}

	@Override
	public void saveUserFundFail(UserFundFail userFundFail) {
		userFundFailService.save(userFundFail);
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
