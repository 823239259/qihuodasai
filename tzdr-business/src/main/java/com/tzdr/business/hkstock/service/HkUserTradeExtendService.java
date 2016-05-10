package com.tzdr.business.hkstock.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.hkstock.exception.HkUserTradeException;
import com.tzdr.business.service.exception.UserTradeConcurrentException;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.thread.SMSSendForContentThread;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.hkstock.dao.HkUserTradeExtendDao;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.hkstock.entity.HkUserTradeExtend;
import com.tzdr.domain.hkstock.vo.HkUserTradeExtendVo;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * @author zhouchen
 * 2015年10月16日 上午11:45:32
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HkUserTradeExtendService extends BaseServiceImpl<HkUserTradeExtend,HkUserTradeExtendDao> {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private HkUserTradeService hkUserTradeService;
	
	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private NoticeRecordService noticeRecordService;
	
	@Autowired
	private HkTradeCalendarService hkTradeCalendarService;
	
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
	
	/**
	 * 开户审核
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryWellGoldDatas(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());
	
		StringBuffer sql = new StringBuffer(" SELECT h.id,t.id AS tradeId,u.mobile,v.tname,t.account_no AS accountNo, ");
		sql.append(" t.account_name AS accountName,t.group_id AS groupId, sum(t.lever_money) leverMoney,sum(t.money) money, ");
		sql.append(" sum(t.total_lever_money) totalLeverMoney,t.warning, t.`open` AS openline,p.trade_channel AS tradeChannel, ");
		sql.append(" h.audit_status auditStatus,t.trade_start AS tradeStart,t.starttime,h.create_time AS createTime , ");
		sql.append(" h.update_user AS auditUser,h.update_time AS auditTime ");
		sql.append(" FROM hk_user_trade_extend h, hk_user_trade t LEFT JOIN hk_parent_account p ON t.parent_account_id = p.id,w_user u,w_user_verified v ");
		sql.append(" WHERE t.id = h.trade_id AND u.id = t.uid AND u.id = v.uid ");
		sql.append(" GROUP BY t.group_id ORDER BY h.create_time ASC ");
		
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql.toString());

		pageInfo = multiListPageQuery(multilistParam, HkUserTradeExtendVo.class);
		return pageInfo;
	}
	
	/**
	 * 根据方案号，获取方案审核信息
	 * @param tradeId  方案id
	 * @return
	 */
	public HkUserTradeExtend findByTradeId(String tradeId) {
		List<HkUserTradeExtend> list = this.getEntityDao().findByTradeId(tradeId);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 港股开户审核
	 * @param tradeId  方案id
	 * @param isPass false:未通过   true:通过
	 * @param hkUserTradeExtendVo 账号信息
	 */
	public void auditWellGoldTrade(String tradeId, boolean isPass,HkUserTradeExtendVo hkUserTradeExtendVo,HkUserTradeExtend hkUserTradeExtend) {
		
		HkUserTrade hkUserTrade = hkUserTradeService.get(tradeId);
		WUser wuser = wUserService.getUser(hkUserTrade.getWuser().getId());
		if (ObjectUtil.equals(hkUserTrade, null)) {
			throw new HkUserTradeException("no.hkUserTrade", null);
		}
		if (ObjectUtil.equals(hkUserTradeExtend, null)) {
			throw new UserTradeException("no.hkUserTradeExtend", null);
		}

		//更新审核人信息
		updateHKUserTradeExtend(hkUserTradeExtend,"港股配资方案","edit");
		
		if (isPass) {
			//1.保存账户信息到 配资方案中
			hkUserTrade.setAccountName(hkUserTradeExtendVo.getAccountName());
			hkUserTrade.setAccountNo(hkUserTradeExtendVo.getAccountNo());
			hkUserTrade.setPassword(hkUserTradeExtendVo.getPassword());
			hkUserTrade.setParentAccountId(hkUserTradeExtendVo.getParentAccountId());
			hkUserTrade.setPolicyNo(hkUserTradeExtendVo.getPolicyNo());
			hkUserTrade.setStatus((short) 1);
			hkUserTradeService.update(hkUserTrade);	
			
			//2.保存账户信息到人工审核处理中
			hkUserTradeExtend.setAuditStatus(1);
			this.update(hkUserTradeExtend);
			
			//3.发短信
			Map<String, String> map = Maps.newHashMap();
			map.put("group", hkUserTrade.getGroupId());
			map.put("starttime", Dates.format(Dates.parseLong2Date(hkUserTrade.getStarttime()),Dates.CHINESE_DATE_FORMAT_LINE));
			new SMSSenderThread(wuser.getMobile(),"hk.ihuyi.trade.ok.code.template", map).start();
			
			//4.配资审核通过，发送配资成功短信后，并校验余额是否够扣次日管理费，如不足则发送次日余额不足扣费短信。
			String nextDay = Dates.format(Dates.addDay(new Date()),Dates.CHINESE_DATE_FORMAT_LONG);
			boolean isNextTradeDay = hkTradeCalendarService.isTradeDay(nextDay);
			if(isNextTradeDay){  
				if(BigDecimalUtils.sub(wuser.getAvlBal(), hkUserTrade.getFeeDay()) < 0){
					String notEnoughContent = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
					new SMSSendForContentThread(wuser.getMobile(),notEnoughContent,2000).start();
					//保存通知记录
					noticeRecordService.save(new NoticeRecord(wuser.getId(), notEnoughContent, 3));
				}			
			}
		} else {
			
			//审核不通过
			hkUserTradeExtend.setAuditStatus(2);
			this.getEntityDao().update(hkUserTradeExtend);
			
			//方案结束
			hkUserTrade.setStatus((short) 2);
			hkUserTrade.setEndtime(Dates.getCurrentLongDate());
			hkUserTrade.setFinishedMoney(hkUserTrade.getTotalLeverMoney());
			hkUserTrade.setAccrual(0.0);
			hkUserTradeService.update(hkUserTrade);
		
			//配资撤回金额
			this.tradeRevoke(hkUserTrade, wuser);
		}
	}
	
	/**
	 * 开户审核更新方案审核信息
	 * @param hkUserTradeExtend 方案审核信息
	 * @param operateContent  内容
	 * @param operateType     操作类型类型  “edit”：编辑  非“edit”：创建
	 */
	private void updateHKUserTradeExtend(HkUserTradeExtend hkUserTradeExtend, String operateContent,
			String operateType) {
		User loginUser = authService.getCurrentUser();
		hkUserTradeExtend.setOperateContent(operateContent);
		if (StringUtil.equals(operateType, "edit")) {
			hkUserTradeExtend.setUpdateTime(Dates.getCurrentLongDate());
			hkUserTradeExtend.setUpdateUser(loginUser.getRealname());
			hkUserTradeExtend.setUpdateUserOrg(loginUser.getOrganization().getName());
			hkUserTradeExtend.setUpdateUserId(loginUser.getId());
		}else{
			hkUserTradeExtend.setCreateTime(Dates.getCurrentLongDate());
			hkUserTradeExtend.setCreateUser(loginUser.getRealname());
			hkUserTradeExtend.setCreateUserOrg(loginUser.getOrganization().getName());
			hkUserTradeExtend.setCreateUserId(loginUser.getId());
		}
	}
	
	/**
	 * 配资撤回金额
	 * @param userTrade  方案信息
	 * @param wuser      用户信息
	 */
	public void tradeRevoke(HkUserTrade khUserTrade,WUser wuser){
		//配资撤回资金流水
		//配资撤回金额
		double returnMoney= this.findByTradeIdAndType(khUserTrade.getId(),10);
		revokeUserFund(khUserTrade, wuser, returnMoney, 15,"配资撤回",1);

		//配资管理费撤回
		double returnFeeMoney = this.findByTradeIdAndType(khUserTrade.getId(),12);
		if (returnFeeMoney<0){
			revokeUserFund(khUserTrade, wuser, returnFeeMoney, 25,"配资管理费撤回",2);
		}
		
		//配资利息撤回
		double returnInterestMoney = this.queryInterest(khUserTrade.getId());
		if (returnInterestMoney<0){
			revokeUserFund(khUserTrade, wuser, returnInterestMoney, 26,"配资利息撤回",3);
		}
	}
	
	/**
	 * 根据tradeId 和 type 求和userfund
	 * @param tradeId 方案id
	 * @param type    资金明细类型
	 * @return
	 */
	public double findByTradeIdAndType(String tradeId,int type) {
		String sql = "SELECT ROUND(ifnull(SUM(wuf.money),0),2) FROM w_user_fund wuf WHERE wuf.`no`=? and type=?";
		List<Object> params = new ArrayList<Object>();
		params.add(tradeId);
		params.add(type);
		Double b = (Double) nativeQueryOne(sql, params);
		return b.doubleValue();
	}
	
	/**
	 * 配资撤回资金流水 
	 * @param hkUserTrade 配资信息
	 * @param wuser    用户信息
	 * @param revokeMoney  撤回资金  
	 * @param revokeType   撤回资金类型
	 * @param remark       备注
	 * @param time         时间
	 */
	private void revokeUserFund(HkUserTrade hkUserTrade,WUser wuser,double revokeMoney,
			int revokeType,String remark,long time){
		double balance=BigDecimalUtils.addRound(wuser.getAvlBal(), -revokeMoney);
		UserFund userfund = new UserFund();
		userfund.setMoney(-revokeMoney);
		userfund.setType(revokeType);
		userfund.setPayStatus(TypeConvert.PAID);
		userfund.setRemark(TypeConvert.payRemark(remark,
		userfund.getMoney()));
		userfund.setUid(wuser.getId());
		userfund.setNo(hkUserTrade.getId());
		userfund.setLid(hkUserTrade.getGroupId());
		userfund.setRid(hkUserTrade.getProgramNo());
		userfund.setAmount(balance);
		userfund.setAddtime(Dates.getCurrentLongDate()+time);
		userfund.setUptime(Dates.getCurrentLongDate()+time);
		userFundService.save(userfund);
		wuser.setAvlBal(balance);
		wUserService.update(wuser);
	}
	
	/**
	 * 查询抵扣券收入和利息的和
	 * @param tradeId  方案id
	 * @return
	 */
	public double queryInterest(String tradeId) {
		String sql = "SELECT ROUND(ifnull(SUM(wuf.money),0),2) FROM w_user_fund wuf WHERE wuf.`no`=? and type in (11,24)";
		List<Object> params = new ArrayList<Object>();
		params.add(tradeId);
		Double b = (Double) nativeQueryOne(sql, params);
		return b.doubleValue();
	};
	
	/**
	 * 申请终结方案  改变状态
	 * @param tradeId
	 */
	public void  endWellGoldTrade(String id){
		HkUserTradeExtend  handTrade  = this.getEntityDao().get(id);
		if (ObjectUtil.equals(handTrade, null)) {
			throw new HkUserTradeException("no.handtrade", null);
		}

		//获取方案信息
		HkUserTrade hkUserTrade = hkUserTradeService.get(handTrade.getTradeId());
		
		// 获取系统当前最新港币汇率
		FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(2);

		if (fSimpleParities == null || fSimpleParities.getParities() == null
				|| fSimpleParities.getParities().compareTo(new BigDecimal("0")) <= 0) {
			throw new UserTradeConcurrentException("com.tzdr.business.server.connection.timeout", null);
		}
		
		hkUserTrade.setEndExchangeRate(fSimpleParities.getParities().doubleValue());
		hkUserTradeService.update(hkUserTrade);
		
		handTrade.setEndSubTime(Dates.getCurrentLongDate());
		handTrade.setAuditEndStatus(0);
		super.update(handTrade);
	}
}
