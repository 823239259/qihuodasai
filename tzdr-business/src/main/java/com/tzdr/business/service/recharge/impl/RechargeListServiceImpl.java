package com.tzdr.business.service.recharge.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.exception.WuserConcurrencyUpdateException;
import com.tzdr.business.service.exception.WuserDoesNotExistException;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.recharge.RechargeAuditRuleService;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.OperationLogVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.dao.rechargelist.RechargeListDao;
import com.tzdr.domain.vo.AllRechargeListVo;
import com.tzdr.domain.vo.AllRechargeTotalVo;
import com.tzdr.domain.vo.RechargeBankListVo;
import com.tzdr.domain.web.entity.RechargeAuditRule;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p>支付审核业务类</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年1月5日下午7:52:50
 */
@Transactional(propagation=Propagation.REQUIRED)
@Service("rechargeListService")
public class RechargeListServiceImpl extends BaseServiceImpl<RechargeList, RechargeListDao> implements RechargeListService {

	
	private static Logger log = LoggerFactory
			.getLogger(RechargeListServiceImpl.class);
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private RechargeListDao rechargeListDao;
	
	
	@Autowired
	private RechargeAuditRuleService auditRuleService;
	
	@Override
	public PageInfo<RechargeList> queryDataPageByConndition(
			PageInfo<RechargeList> page, final Map<String, Object> equals,
			final Map<String, String> isLike, final Map<String, Boolean> orders) {
		Page<RechargeList> pageData = rechargeListDao.findAll(new Specification<RechargeList>() {
			@Override
			public Predicate toPredicate(Root<RechargeList> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			
				List<Predicate> pres = new ArrayList<Predicate>();
				if (equals != null  && equals.size() > 0) {
					for (Map.Entry<String, Object> equalObj:equals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.equal(expression, equalObj.getValue());
							pres.add(pre);
						}
						else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.equal(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				if (isLike != null && isLike.size() > 0) {
					for (Map.Entry<String, String> isLikeObj:isLike.entrySet()) {
						String[] names = StringUtil.split(isLikeObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.like(expression, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
						else {
							Path<String> path = root.get(isLikeObj.getKey());
							Predicate pre = cb.like(path, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
					}
				}
				List<Order> queryOrders = new ArrayList<Order>();
				if (orders != null && orders.size() > 0) {
					for (Map.Entry<String, Boolean> order:orders.entrySet()) {
						if (order.getValue()) {
							queryOrders.add(cb.asc(root.get(order.getKey())));
						}
						else {
							queryOrders.add(cb.desc(root.get(order.getKey())));
						}
					}
				}
				if (queryOrders.size() > 0) {
					query.orderBy(queryOrders);
				}
				cb.createQuery();
				return cb.and(pres.toArray(new Predicate[pres.size()]));
				
			}
		}, new PageRequest(page.getCurrentPage(),page.getCountOfCurrentPage()));
		page.setTotalCount(pageData.getTotalElements());
		page.setPageResults(pageData.getContent());
		return page;
	}

	@Override
	@Deprecated
	public PageInfo<RechargeList> queryAlipayRecharge(
			PageInfo<RechargeList> page) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("type", "3");
		equals.put("status", TypeConvert.RECHARGE_LIST_PAYS_STATUS_WAITING);
		//equals.put("status", 1);
		Map<String,String> isLike = new HashMap<String,String>();
		Map<String,Boolean> orders = new HashMap<String,Boolean>();
		orders.put("addtime", false);
		page.setCurrentPage(page.getCurrentPage() - 1);
		return this.queryDataPageByConndition(page, equals, isLike, orders);
	}
	
	@Override
	public PageInfo<RechargeBankListVo> queryAlipayListRecharge(
			PageInfo<RechargeBankListVo> page,ConnditionVo connVo) {
		/**
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("type", "3");
		equals.put("status", TypeConvert.RECHARGE_LIST_PAYS_STATUS_WAITING);
		//equals.put("status", 1);
		Map<String,String> isLike = new HashMap<String,String>();
		Map<String,Boolean> orders = new HashMap<String,Boolean>();
		orders.put("addtime", false);
		page.setCurrentPage(page.getCurrentPage() - 1);
		return this.queryDataPageByConndition(page, equals, isLike, orders); */
		String sql = 
				  " SELECT w.id,(SELECT u.mobile FROM w_user u WHERE u.id=w.uid)mobile"
				+ " ,IFNULL((SELECT v.tname FROM w_user_verified v WHERE v.uid=w.uid),IF(LENGTH(w.uid)=32,'',w.uid)) tname"
				+ " ,w.account,w.trade_no,w.money,w.actual_money"
				+ " ,w.`status`,w.uptime,w.addtime,w.source "
				+ " FROM w_recharge_list w "
				+ " WHERE w.type='3' "
				+ createAuditRuleSql()
				+ " AND w.status = " + TypeConvert.RECHARGE_LIST_PAYS_STATUS_WAITING
				+ " ORDER BY w.addtime DESC ";
		return this.getEntityDao().queryPageBySql(page, sql, RechargeBankListVo.class, connVo, null);
		
	}

	@Override
	public RechargeList addUpdateRechargeList(RechargeList rechargeList) {
		return addUpdateRechargeList(rechargeList,null);
	}
	
	@Override
	public RechargeList addUpdateRechargeList(RechargeList rechargeList,Integer userType) {
		return addUpdateRechargeList(rechargeList,userType,null);
	}
	
	@Override
	public RechargeList addUpdateRechargeList(RechargeList rechargeList,Integer userType,String remark) {
		synchronized (lock) {
			User user = authService.getCurrentUser();//获取当前登录用户
			UserFund userFund = new UserFund();
			userFund.setUid(rechargeList.getUid());
			userFund.setMoney(rechargeList.getActualMoney());
			userFund.setAddtime(TypeConvert.dbDefaultDate());
			userFund.setSysUserId(user.getId().toString());
			if (userType != null) {
				userFund.setType(userType);
			}
			userFund.setNo(rechargeList.getNo() == null?"":rechargeList.getNo());
			userFund.setRemark(remark == null?"":remark);
			userFund.setUptime(TypeConvert.dbDefaultDate());
			WUser wuser = wuserService.getUser(rechargeList.getUid());
			Double oldAvlBal = wuser.getAvlBal();
			if (wuser != null) {
				if (rechargeList.getSysType() != null && !"".equals(rechargeList.getSysType())) {
					if (TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS.equals(rechargeList.getSysType())) {
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE);
						//this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
						this.userFundService.arrearsProcess(userFund);
						AdjustmentLogVo vo = new AdjustmentLogVo();
						vo.typeName = TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME;
						vo.account = wuser.getMobile();
						vo.money = TypeConvert.doubleToBigDecimalScale(userFund.getMoney(),2).toString();
						vo.amount = TypeConvert.doubleToBigDecimalScale(userFund.getAmount(),2).toString();
						vo.remark = userFund.getRemark();
						vo.oldAmount = TypeConvert.doubleToBigDecimalScale(oldAvlBal,2).toString();
						
						OperationLogVo opVo = new OperationLogVo();
						User currentUser = this.authService.getCurrentUser();
						if (currentUser != null) {
							opVo.setOperationPeople(currentUser.getUsername());
							opVo.setTitle(TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME);
						}
						opVo.setAccount(vo.account);
						opVo.add(vo.oldAmount);
						opVo.add(vo.money);
						opVo.add(vo.amount);
						log.info(TypeConvert.printPaymentOperationLog(opVo));
						
						//sendEmailAccountAdjustment(vo);
					}
					else if (TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS.equals(rechargeList.getSysType())) {
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_DEDUCTIONS);
						this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
						this.rechargeListDao.delete(rechargeList);
						AdjustmentLogVo vo = new AdjustmentLogVo();
						vo.typeName = TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS_NAME;
						vo.account = wuser.getMobile();
						vo.money = TypeConvert.doubleToBigDecimalScale(userFund.getMoney(),2).toString();
						vo.amount = TypeConvert.doubleToBigDecimalScale(userFund.getAmount(),2).toString();
						vo.remark = userFund.getRemark();
						vo.oldAmount = TypeConvert.doubleToBigDecimalScale(oldAvlBal,2).toString();
						
						OperationLogVo opVo = new OperationLogVo();
						User currentUser = this.authService.getCurrentUser();
						if (currentUser != null) {
							opVo.setOperationPeople(currentUser.getUsername());
						}
						opVo.setAccount(vo.account);
						opVo.setTitle(TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS_NAME);
						opVo.add(vo.oldAmount);
						opVo.add(vo.money);
						opVo.add(vo.amount);
						log.info(TypeConvert.printPaymentOperationLog(opVo));
						
						//sendEmailAccountAdjustment(vo);
					}
					else {
						throw new WuserDoesNotExistException("com.tzdr.business.userfund.message",null);
					}
				}
				else {
					//this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
					this.userFundService.arrearsProcess(userFund);
				}
				return rechargeList;
			}
			else {
				throw new WuserDoesNotExistException("com.tzdr.business.userfund.message",null);
			}
			
		}
	}
	
	@Override
	public RechargeList addUpdateRechargeList(RechargeList rechargeList,
			Integer userType, String remark, Integer businessType) {
		synchronized (lock) {
			
			UserFund userFund = new UserFund();
			
			userFund.setUid(rechargeList.getUid());
			userFund.setMoney(rechargeList.getActualMoney());
			userFund.setAddtime(TypeConvert.dbDefaultDate());
			if (userType != null) {
				userFund.setType(userType);
			}
			
			if(businessType != null){
				userFund.setBusinessType(businessType);
			}
			
			userFund.setNo(rechargeList.getNo() == null? rechargeList.getTradeNo() == null ? "": rechargeList.getTradeNo() :rechargeList.getNo());
			userFund.setRemark(remark == null?"":remark);
			userFund.setUptime(TypeConvert.dbDefaultDate());
			
			WUser wuser = wuserService.getUser(rechargeList.getUid());
			Double oldAvlBal = wuser.getAvlBal();
			if (wuser != null) {
				if (rechargeList.getSysType() != null && !"".equals(rechargeList.getSysType())) {
					if (TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS.equals(rechargeList.getSysType())) {
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE);
						//this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
						this.userFundService.arrearsProcess(userFund);
						AdjustmentLogVo vo = new AdjustmentLogVo();
						vo.typeName = TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME;
						vo.account = wuser.getMobile();
						vo.money = TypeConvert.doubleToBigDecimalScale(userFund.getMoney(),2).toString();
						vo.amount = TypeConvert.doubleToBigDecimalScale(userFund.getAmount(),2).toString();
						vo.remark = userFund.getRemark();
						vo.oldAmount = TypeConvert.doubleToBigDecimalScale(oldAvlBal,2).toString();
						
						OperationLogVo opVo = new OperationLogVo();
						User currentUser = this.authService.getCurrentUser();
						if (currentUser != null) {
							opVo.setOperationPeople(currentUser.getUsername());
							opVo.setTitle(TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME);
						}
						opVo.setAccount(vo.account);
						opVo.add(vo.oldAmount);
						opVo.add(vo.money);
						opVo.add(vo.amount);
						log.info(TypeConvert.printPaymentOperationLog(opVo));
						
						//sendEmailAccountAdjustment(vo);
					}
					else if (TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS.equals(rechargeList.getSysType())) {
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_DEDUCTIONS);
						this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
						this.rechargeListDao.delete(rechargeList);
						AdjustmentLogVo vo = new AdjustmentLogVo();
						vo.typeName = TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS_NAME;
						vo.account = wuser.getMobile();
						vo.money = TypeConvert.doubleToBigDecimalScale(userFund.getMoney(),2).toString();
						vo.amount = TypeConvert.doubleToBigDecimalScale(userFund.getAmount(),2).toString();
						vo.remark = userFund.getRemark();
						vo.oldAmount = TypeConvert.doubleToBigDecimalScale(oldAvlBal,2).toString();
						
						OperationLogVo opVo = new OperationLogVo();
						User currentUser = this.authService.getCurrentUser();
						if (currentUser != null) {
							opVo.setOperationPeople(currentUser.getUsername());
						}
						opVo.setAccount(vo.account);
						opVo.setTitle(TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS_NAME);
						opVo.add(vo.oldAmount);
						opVo.add(vo.money);
						opVo.add(vo.amount);
						log.info(TypeConvert.printPaymentOperationLog(opVo));
						
						//sendEmailAccountAdjustment(vo);
					}
					else {
						throw new WuserDoesNotExistException("com.tzdr.business.userfund.message",null);
					}
				}
				else {
					//this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
					this.userFundService.arrearsProcess(userFund);
				}
				return rechargeList;
			}
			else {
				throw new WuserDoesNotExistException("com.tzdr.business.userfund.message",null);
			}
		}
	}
	
	@Override
	public RechargeList addUpdateRechargeListWeb(RechargeList rechargeList,Integer userType,String remark) {
		synchronized (lock) {
			
			UserFund userFund = new UserFund();
			
			userFund.setUid(rechargeList.getUid());
			userFund.setMoney(rechargeList.getActualMoney());
			userFund.setAddtime(TypeConvert.dbDefaultDate());
			if (userType != null) {
				userFund.setType(userType);
			}
			userFund.setNo(rechargeList.getNo() == null?"":rechargeList.getNo());
			userFund.setRemark(remark == null?"":remark);
			userFund.setUptime(TypeConvert.dbDefaultDate());
			
			WUser wuser = wuserService.getUser(rechargeList.getUid());
			Double oldAvlBal = wuser.getAvlBal();
			if (wuser != null) {
				if (rechargeList.getSysType() != null && !"".equals(rechargeList.getSysType())) {
					if (TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS.equals(rechargeList.getSysType())) {
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE);
						//this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
						this.userFundService.arrearsProcess(userFund);
						AdjustmentLogVo vo = new AdjustmentLogVo();
						vo.typeName = TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME;
						vo.account = wuser.getMobile();
						vo.money = TypeConvert.doubleToBigDecimalScale(userFund.getMoney(),2).toString();
						vo.amount = TypeConvert.doubleToBigDecimalScale(userFund.getAmount(),2).toString();
						vo.remark = userFund.getRemark();
						vo.oldAmount = TypeConvert.doubleToBigDecimalScale(oldAvlBal,2).toString();
						
						OperationLogVo opVo = new OperationLogVo();
						//User currentUser = this.authService.getCurrentUser();
						//if (currentUser != null) {
						//	opVo.setOperationPeople(currentUser.getUsername());
						//	opVo.setTitle(TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME);
					    //	}
						opVo.setAccount(vo.account);
						opVo.add(vo.oldAmount);
						opVo.add(vo.money);
						opVo.add(vo.amount);
						log.info(TypeConvert.printPaymentOperationLog(opVo));
						
						//sendEmailAccountAdjustment(vo);
					}
					else if (TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS.equals(rechargeList.getSysType())) {
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_DEDUCTIONS);
						this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
						this.rechargeListDao.delete(rechargeList);
						AdjustmentLogVo vo = new AdjustmentLogVo();
						vo.typeName = TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS_NAME;
						vo.account = wuser.getMobile();
						vo.money = TypeConvert.doubleToBigDecimalScale(userFund.getMoney(),2).toString();
						vo.amount = TypeConvert.doubleToBigDecimalScale(userFund.getAmount(),2).toString();
						vo.remark = userFund.getRemark();
						vo.oldAmount = TypeConvert.doubleToBigDecimalScale(oldAvlBal,2).toString();
						
						OperationLogVo opVo = new OperationLogVo();
						//User currentUser = this.authService.getCurrentUser();
						//if (currentUser != null) {
						//	opVo.setOperationPeople(currentUser.getUsername());
						//}
						opVo.setAccount(vo.account);
						opVo.setTitle(TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS_NAME);
						opVo.add(vo.oldAmount);
						opVo.add(vo.money);
						opVo.add(vo.amount);
						log.info(TypeConvert.printPaymentOperationLog(opVo));
						
						//sendEmailAccountAdjustment(vo);
					}
					else {
						throw new WuserDoesNotExistException("com.tzdr.business.userfund.message",null);
					}
				}
				else {
					//this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
					this.userFundService.arrearsProcess(userFund);
				}
				return rechargeList;
			}
			else {
				throw new WuserDoesNotExistException("com.tzdr.business.userfund.message",null);
			}
		}
	}
	
	@Override
	public RechargeList addUpdateRechargeListWeb(RechargeList rechargeList,
			Integer userType, String remark, Integer businessType) {
		synchronized (lock) {
			
			UserFund userFund = new UserFund();
			
			userFund.setUid(rechargeList.getUid());
			userFund.setMoney(rechargeList.getActualMoney());
			userFund.setAddtime(TypeConvert.dbDefaultDate());
			if (userType != null) {
				userFund.setType(userType);
			}
			
			if(businessType != null){
				userFund.setBusinessType(businessType);
			}
			
			userFund.setNo(rechargeList.getNo() == null? rechargeList.getTradeNo() == null ? "": rechargeList.getTradeNo() :rechargeList.getNo());
			
			userFund.setRemark(remark == null?"":remark);
			userFund.setUptime(TypeConvert.dbDefaultDate());
			
			WUser wuser = wuserService.getUser(rechargeList.getUid());
			Double oldAvlBal = wuser.getAvlBal();
			if (wuser != null) {
				if (rechargeList.getSysType() != null && !"".equals(rechargeList.getSysType())) {
					if (TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS.equals(rechargeList.getSysType())) {
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE);
						//this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
						this.userFundService.arrearsProcess(userFund);
						AdjustmentLogVo vo = new AdjustmentLogVo();
						vo.typeName = TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME;
						vo.account = wuser.getMobile();
						vo.money = TypeConvert.doubleToBigDecimalScale(userFund.getMoney(),2).toString();
						vo.amount = TypeConvert.doubleToBigDecimalScale(userFund.getAmount(),2).toString();
						vo.remark = userFund.getRemark();
						vo.oldAmount = TypeConvert.doubleToBigDecimalScale(oldAvlBal,2).toString();
						
						OperationLogVo opVo = new OperationLogVo();
						//User currentUser = this.authService.getCurrentUser();
						//if (currentUser != null) {
						//	opVo.setOperationPeople(currentUser.getUsername());
						//	opVo.setTitle(TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME);
					    //	}
						opVo.setAccount(vo.account);
						opVo.add(vo.oldAmount);
						opVo.add(vo.money);
						opVo.add(vo.amount);
						log.info(TypeConvert.printPaymentOperationLog(opVo));
						
						//sendEmailAccountAdjustment(vo);
					}
					else if (TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS.equals(rechargeList.getSysType())) {
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_DEDUCTIONS);
						this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
						this.rechargeListDao.delete(rechargeList);
						AdjustmentLogVo vo = new AdjustmentLogVo();
						vo.typeName = TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS_NAME;
						vo.account = wuser.getMobile();
						vo.money = TypeConvert.doubleToBigDecimalScale(userFund.getMoney(),2).toString();
						vo.amount = TypeConvert.doubleToBigDecimalScale(userFund.getAmount(),2).toString();
						vo.remark = userFund.getRemark();
						vo.oldAmount = TypeConvert.doubleToBigDecimalScale(oldAvlBal,2).toString();
						
						OperationLogVo opVo = new OperationLogVo();
						//User currentUser = this.authService.getCurrentUser();
						//if (currentUser != null) {
						//	opVo.setOperationPeople(currentUser.getUsername());
						//}
						opVo.setAccount(vo.account);
						opVo.setTitle(TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS_NAME);
						opVo.add(vo.oldAmount);
						opVo.add(vo.money);
						opVo.add(vo.amount);
						log.info(TypeConvert.printPaymentOperationLog(opVo));
						
						//sendEmailAccountAdjustment(vo);
					}
					else {
						throw new WuserDoesNotExistException("com.tzdr.business.userfund.message",null);
					}
				}
				else {
					//this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
					this.userFundService.arrearsProcess(userFund);
				}
				return rechargeList;
			}
			else {
				throw new WuserDoesNotExistException("com.tzdr.business.userfund.message",null);
			}
		}
	}
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 
	 * <p>投资达人系统手工调账<br></p>
	 * <p>操作员：%s<br></p>
	 * <p>时间：%s<br></p>
     * <p>调账类型：%s<br></p>
     * <p>账号：%s <br></p> 
     * <p>金额：%s<br></p> 
     * <p>余额：%s<br></p> 
     * <p>原因：%s<br></p>
	 * @param params String...
	 */
	private void sendEmailAccountAdjustment(AdjustmentLogVo vo) {
		try {
			User user = authService.getCurrentUser();
			if (user != null){
				vo.operationName = user.getUsername();
			}
			List<String> params = new ArrayList<String>();
			params.add(vo.operationName);
			params.add(TypeConvert.dateToDatetimeStr(new Date()));
			params.add(vo.typeName);
			params.add(vo.account);
			params.add(vo.oldAmount);
			params.add(vo.money);
			params.add(vo.amount);
			params.add(vo.remark);
			EmailUtils.getInstance().sendMailTemp(
					ConfUtil.getContext("mail.to.tech"),vo.typeName,"accountAdjustment",
				  params);
		} 
		catch (Exception e1) {
			log.error(this.getClass().getName() + e1.getMessage());
		}
		
		
	}
	
	
	
	//资金更新
	private static Object lock = new Object();


	@Override
	public PageInfo<RechargeList> queryBankRecharge(PageInfo<RechargeList> page) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("type", "4");
		//equals.put("status", 1);
		equals.put("status", TypeConvert.RECHARGE_LIST_PAYS_STATUS_WAITING);
		Map<String,String> isLike = new HashMap<String,String>();
		Map<String,Boolean> orders = new HashMap<String,Boolean>();
		page.setCurrentPage(page.getCurrentPage() - 1);
		orders.put("addtime", false);
		return this.queryDataPageByConndition(page, equals, isLike, orders);
	}
	
	
	public PageInfo<RechargeBankListVo> queryBankListRecharge(PageInfo<RechargeBankListVo> page){
		String sql = " SELECT w.id,(SELECT u.mobile FROM w_user u WHERE u.id=w.uid)mobile,"
				+ " (SELECT v.tname FROM w_user_verified v WHERE v.uid=w.uid)tname"
				+ " ,w.trade_no,w.trade_account,w.money,w.actual_money,w.`status`"
				+ " ,w.uptime,w.addtime,w.source FROM w_recharge_list w "
				+ " WHERE w.type='4' "
				+ createAuditRuleSql()
				+ " AND w.`status`= " + TypeConvert.RECHARGE_LIST_PAYS_STATUS_WAITING
				+ " ORDER BY w.addtime DESC";
		return this.rechargeListDao.queryPageBySql(page,sql, RechargeBankListVo.class, null, null);
	}
	public PageInfo<RechargeBankListVo> queryNetBankListRecharge(PageInfo<RechargeBankListVo> page){
		String sql = " SELECT w.id,(SELECT u.mobile FROM w_user u WHERE u.id=w.uid)mobile,"
				+ " (SELECT v.tname FROM w_user_verified v WHERE v.uid=w.uid)tname"
				+ " ,w.trade_no,w.trade_account,w.money,w.actual_money,w.`status`,w.no"
				+ " ,w.uptime,w.addtime,w.source FROM w_recharge_list w "
				+ " WHERE w.type='2' "
				+ createAuditRuleSql()
				+ " AND w.`status`= " + TypeConvert.RECHARGE_LIST_PAYS_STATUS_WAITING
				+ " ORDER BY w.addtime DESC";
		return this.rechargeListDao.queryPageBySql(page,sql, RechargeBankListVo.class, null, null);
	}
	@Override
	public PageInfo<RechargeBankListVo> queryWechatListRecharge(PageInfo<RechargeBankListVo> page) {
		String sql = " SELECT w.id,(SELECT u.mobile FROM w_user u WHERE u.id=w.uid)mobile,"
				+ " (SELECT v.tname FROM w_user_verified v WHERE v.uid=w.uid)tname"
				+ " ,w.trade_no,w.trade_account,w.money,w.actual_money,w.`status`"
				+ " ,w.uptime,w.addtime,w.source,account FROM w_recharge_list w "
				+ " WHERE w.type='9' "
				+ createAuditRuleSql()
				+ " AND w.`status`= " + TypeConvert.RECHARGE_LIST_PAYS_STATUS_WAITING
				+ " ORDER BY w.addtime DESC";
		return this.rechargeListDao.queryPageBySql(page,sql, RechargeBankListVo.class, null, null);
	}
	/**
	 * 生成 审核规则 sql
	 * @return
	 */
	private String createAuditRuleSql(){
		User  user = authService.getCurrentUser();
		RechargeAuditRule auditRule = auditRuleService.queryAuditRuleByLoginUserId(String.valueOf(user.getId()));
		if (ObjectUtil.equals(null, auditRule)){
			return "";
		}
		
		return " and w.money>="+auditRule.getBeginMoney()+" and w.money<"+auditRule.getEndMoney();
	}
	//@Override
	//public PageInfo<RechargeList> queryHandlerRecharge(
		//	PageInfo<RechargeList> page) {
		//this.userFundService.q
		/*Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("type", "5");
		//equals.put("status", 1);
		Map<String,String> isLike = new HashMap<String,String>();
		Map<String,Boolean> orders = new HashMap<String,Boolean>();
		orders.put("oktime", false);
		page.setCurrentPage(page.getCurrentPage() - 1);
		return this.queryDataPageByConndition(page, equals, isLike, orders);*/
		//return null;
	//}

	@Override
	public PageInfo<RechargeList> queryAlipayHaveRecharge(
			PageInfo<RechargeList> page) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("type", "3");
		//equals.put("status", 21);
		//equals.put("status", 1);
		Map<String,String> isLike = new HashMap<String,String>();
		Map<String,Boolean> orders = new HashMap<String,Boolean>();
		orders.put("oktime", false);
		page.setCurrentPage(page.getCurrentPage() - 1);
		return this.queryDataPageByConndition(page, equals, isLike, orders);
	}

	@Override
	public PageInfo<RechargeList> queryBankHaveRecharge(
			PageInfo<RechargeList> page) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("type", "4");
		//equals.put("status", 1);
		//equals.put("status", 21);
		Map<String,String> isLike = new HashMap<String,String>();
		Map<String,Boolean> orders = new HashMap<String,Boolean>();
		orders.put("oktime", false);
		page.setCurrentPage(page.getCurrentPage() - 1);
		return this.queryDataPageByConndition(page, equals, isLike, orders);
	}

	@Override
	public PageInfo<AllRechargeListVo> queryRecharge(PageInfo<AllRechargeListVo> page,ConnditionVo conn) {
		List<Object> params = new ArrayList<Object>();
		//5人工调账、银行 4、支付宝3
		StringBuffer sqlBuf = new StringBuffer("SELECT  t.id, w.mobile,"
				+ " t.payment_channel paymentChannel,"
				+ " IF(t.type=3 AND t.`status` IN(0,1),"
				+ " IFNULL(v.tname,IF(LENGTH(t.uid)=32,'',t.uid)),v.tname) tname"
				+ ", t.no rechargeID, t.trade_no tradeNo, "
				+ " t.trade_account tradeAccountBank, t.money, t.type, t.addtime,"
				+ " t.actual_money actualMoney, t.`status`, t.oktime, t.source ,su.realname"
				+ " FROM w_recharge_list t left join sys_user su on su.id=t.re_account_id"
				+ " LEFT JOIN w_user_verified v "
				+ " ON t.uid = v.uid "
				+ " LEFT JOIN w_user w"
				+ " ON t.uid = w.id "
				+ " WHERE t.type != 5 ");
		if (conn != null) {
		    String type = TypeConvert.objToStrIsNotNull(conn.getValue("type"));
			if (type != null && TypeConvert.isDigital(type)) {
				sqlBuf.append(" AND t.type = ?");
				params.add(Long.parseLong(type));
			}
			String statusValue = TypeConvert.objToStrIsNotNull(conn.getValue("statusValue"));
			if (statusValue != null  && TypeConvert.isDigital(statusValue)) {
				sqlBuf.append(" AND t.status = ?");
				params.add(Long.parseLong(statusValue));
			}
			String rechargeID = TypeConvert.objToStrIsNotNull(conn.getValue("rechargeID"));
			if (rechargeID != null) {
				sqlBuf.append(" AND t.no = ?");
				params.add(rechargeID);
			}
			String mobile = TypeConvert.objToStrIsNotNull(conn.getValue("mobile"));
			if (mobile != null) {
				sqlBuf.append(" AND w.mobile LIKE ?");
				params.add(mobile + "%");
			}
			String tname = TypeConvert.objToStrIsNotNull(conn.getValue("tname"));
			if (tname != null) {
				sqlBuf.append(" AND v.tname LIKE ?");
				params.add("%" + tname +"%");
			}
			
			String paymentChannel = TypeConvert.objToStrIsNotNull(conn.getValue("paymentChannel"));
			if (paymentChannel != null) {
				sqlBuf.append(" AND t.payment_channel = ? ");
				params.add(paymentChannel);
			}
			String oktimeStr_start = TypeConvert.objToStrIsNotNull(conn.getValue("oktimeStr_start"));
			String oktimeStr_end = TypeConvert.objToStrIsNotNull(conn.getValue("oktimeStr_end"));
			if (oktimeStr_start != null && oktimeStr_end != null) {
				sqlBuf.append(" AND t.oktime BETWEEN ? AND ?");
				params.add(TypeConvert.strToDatetime1000Long(oktimeStr_start));
				params.add(TypeConvert.strToDatetime1000Long(oktimeStr_end));
			}
			String source = TypeConvert.objToStrIsNotNull(conn.getValue("source"));
			if (source != null  && TypeConvert.isDigital(source)) {
				if((Constant.Source.TZDR+"").equals(source)) {
					sqlBuf.append(" AND (t.source = ? or t.source is null)");
				} else {
					sqlBuf.append(" AND t.source = ?");
				}
				params.add(Integer.parseInt(source));
			}
		}
		sqlBuf.append(" ORDER BY t.oktime DESC");
		return this.getEntityDao().queryDataPageBySql(page, sqlBuf.toString(), AllRechargeListVo.class, params.toArray());
	}
	
	
	@Override
	public AllRechargeTotalVo queryRechargeAllTotal(ConnditionVo conn) {
		List<Object> params = new ArrayList<Object>();
		//5人工调账、银行 4、支付宝3
		StringBuffer sqlBuf = new StringBuffer(" SELECT SUM(t.money) money"
				+ " ,SUM(t.actual_money) actualMoney"
				+ " FROM w_recharge_list t"
				+ " LEFT JOIN w_user_verified v "
				+ " ON t.uid = v.uid "
				+ " LEFT JOIN w_user w"
				+ " ON t.uid = w.id "
				+ " WHERE t.type != 5");
		if (conn != null) {
		    String type = TypeConvert.objToStrIsNotNull(conn.getValue("type"));
			if (type != null && TypeConvert.isDigital(type)) {
				sqlBuf.append(" AND t.type = ?");
				params.add(Long.parseLong(type));
			}
			String statusValue = TypeConvert.objToStrIsNotNull(conn.getValue("statusValue"));
			if (statusValue != null  && TypeConvert.isDigital(statusValue)) {
				sqlBuf.append(" AND t.status = ?");
				params.add(Long.parseLong(statusValue));
			}
			String mobile = TypeConvert.objToStrIsNotNull(conn.getValue("mobile"));
			if (mobile != null) {
				sqlBuf.append(" AND w.mobile LIKE ?");
				params.add(mobile + "%");
			}
			String tname = TypeConvert.objToStrIsNotNull(conn.getValue("tname"));
			if (tname != null) {
				sqlBuf.append(" AND v.tname LIKE ?");
				params.add("%" + tname +"%");
			}
			String paymentChannel = TypeConvert.objToStrIsNotNull(conn.getValue("paymentChannel"));
			if (paymentChannel != null) {
				sqlBuf.append(" AND t.payment_channel = ? ");
				params.add(paymentChannel);
			}
			String oktimeStr_start = TypeConvert.objToStrIsNotNull(conn.getValue("oktimeStr_start"));
			String oktimeStr_end = TypeConvert.objToStrIsNotNull(conn.getValue("oktimeStr_end"));
			if (oktimeStr_start != null && oktimeStr_end != null) {
				sqlBuf.append(" AND t.oktime BETWEEN ? AND ?");
				params.add(TypeConvert.strToDatetime1000Long(oktimeStr_start));
				params.add(TypeConvert.strToDatetime1000Long(oktimeStr_end));
			}
			String source = TypeConvert.objToStrIsNotNull(conn.getValue("source"));
			if (source != null  && TypeConvert.isDigital(source)) {
				if((Constant.Source.TZDR+"").equals(source)) {
					sqlBuf.append(" AND (t.source = ? or t.source is null)");
				} else {
					sqlBuf.append(" AND t.source = ?");
				}
				params.add(Integer.parseInt(source));
			}
		}
		sqlBuf.append(" ORDER BY t.oktime DESC");
		List<AllRechargeTotalVo> data =  this.getEntityDao().queryListBySql(sqlBuf.toString(), 
				AllRechargeTotalVo.class, conn,params.toArray());
		if (data != null && data.size() > 0) {
			return data.get(0);
		}
		return null;
		
	}
	
	class AdjustmentLogVo {
		
		/**
		 * 操作员
		 */
		private String operationName;
		/**
		 * 调账类型：
		 */
		private String typeName;
		/**
		 * 账号
		 */
		private String account;
		/**
		 * 金额
		 */
		private String money;
		/**
		 * 余额
		 */
		private String amount;
		
		/**
		 * 调账前余额
		 */
		private String oldAmount;
		/**
		 * 原因
		 */
		private String remark;
	}
	
	/**
	 * 
	 * @param tradeNo 交易号
	 * @param mobileNo 手机号
	 * @param actualMoneyStr 金额
	 * @param remark 备注
	 * @param sysType 充值类开 
	 *   TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS 系统调账、 
	 *   TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS：系统冲账
	 */
	public void futureHandlerSaveRechargeState(
			String tradeNo,String mobileNo,String actualMoneyStr,String remark,String sysType) {
		
		try {
			User user = authService.getCurrentUser();
			if (user == null) {
				throw new WuserDoesNotExistException("com.tzdr.business.not.login",null);
			}
			String status = TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS + "";
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
			rechargeList.setType("5");
			this.save(rechargeList);
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
			this.addUpdateRechargeList(rechargeList,null,remark);
		} 
		catch (WuserDoesNotExistException e) {
			throw e;
		}
		catch (WuserConcurrencyUpdateException e) {
			throw e;
		}
		
		
		
	}
	
	@Override
	public void futureHandlerSaveRechargeState(String tradeNo, String mobileNo,
			String actualMoneyStr, String remark, String sysType,
			Integer businessType) throws Exception {
		try {
			User user = authService.getCurrentUser();
			if (user == null) {
				throw new WuserDoesNotExistException("com.tzdr.business.not.login",null);
			}
			String status = TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS + "";
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
			rechargeList.setType("5");
			this.save(rechargeList);
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
			this.addUpdateRechargeList(rechargeList,null,remark,businessType);
		} 
		catch (WuserDoesNotExistException e) {
			throw e;
		}
		catch (WuserConcurrencyUpdateException e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * @param tradeNo 交易号
	 * @param mobileNo 手机号
	 * @param actualMoneyStr 金额
	 * @param remark 备注
	 * @param sysType 充值类开 
	 *   TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS 系统调账、 
	 *   TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS：系统冲账
	 */
	public void futureHandlerSaveRechargeStateWeb(
			String tradeNo,String mobileNo,String actualMoneyStr,String remark,String sysType) {
		
		try {
			String status = TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS + "";
			RechargeList rechargeList = new RechargeList();
			WUser wuser = this.wuserService.queryWuserByUsername(mobileNo);
			if (wuser == null) {
				throw new WuserDoesNotExistException("com.tzdr.business.not.phonenumber",null);
			}
			rechargeList.setUid(wuser.getId());
			rechargeList.setStatus(new Integer(status));
			rechargeList.setStatusvalue(status);
			rechargeList.setTradeNo(tradeNo);
			rechargeList.setMoney(new BigDecimal(actualMoneyStr).doubleValue());
			rechargeList.setActualMoney(new BigDecimal(actualMoneyStr).doubleValue());
			rechargeList.setRemark(remark == null ? "":remark);
			rechargeList.setSysType(sysType);
			rechargeList.setType("5");
			this.save(rechargeList);
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
			this.addUpdateRechargeListWeb(rechargeList,null,remark);
		} 
		catch (WuserDoesNotExistException e) {
			throw e;
		}
		catch (WuserConcurrencyUpdateException e) {
			throw e;
		}
		
		
	}

	@Override
	public void futureHandlerSaveRechargeStateWeb(String tradeNo,
			String mobileNo, String actualMoneyStr, String remark,
			String sysType, Integer businessType) throws Exception {
		
		try {
			String status = TypeConvert.RECHARGE_LIST_PAYS_STATUS_SUCCESS + "";
			RechargeList rechargeList = new RechargeList();
			WUser wuser = this.wuserService.queryWuserByUsername(mobileNo);
			if (wuser == null) {
				throw new WuserDoesNotExistException("com.tzdr.business.not.phonenumber",null);
			}
			rechargeList.setUid(wuser.getId());
			rechargeList.setStatus(new Integer(status));
			rechargeList.setStatusvalue(status);
			rechargeList.setTradeNo(tradeNo);
			rechargeList.setMoney(new BigDecimal(actualMoneyStr).doubleValue());
			rechargeList.setActualMoney(new BigDecimal(actualMoneyStr).doubleValue());
			rechargeList.setRemark(remark == null ? "":remark);
			rechargeList.setSysType(sysType);
			rechargeList.setType("5");
			this.save(rechargeList);
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
			this.addUpdateRechargeListWeb(rechargeList,null,remark,businessType);
		} 
		catch (WuserDoesNotExistException e) {
			throw e;
		}
		catch (WuserConcurrencyUpdateException e) {
			throw e;
		}
	}
}
