package com.tzdr.business.hkstock.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.exception.UserTradeConcurrentException;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.hkstock.dao.HkUserTradeExtendDao;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.hkstock.entity.HkUserTradeExtend;
import com.tzdr.domain.hkstock.vo.HkEndTradeVo;

/**
 * 
 * @author WangPinQun
 * 2015年10月20日 上午11:45:32
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HkEndTradeService extends BaseServiceImpl<HkUserTradeExtend,HkUserTradeExtendDao> {

	@Autowired
	private AuthService authService;

	@Autowired
	private HkUserTradeService hkUserTradeService;
	
	@Autowired
	private DataMapService dataMapService;

	/**
	 * 终结方案列表（待审核列表【审1】、待审核列表【审2】、审核记录）
	 * @param easyUiPage    分页对象
	 * @param searchParams  查询条件
	 * @param type    类型  0：待审核列表【审1】   1：待审核列表【审2】 2：审核记录
	 * @return
	 */
	public PageInfo<Object> queryHkEndTradeVo(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams,int type) {

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());

		StringBuffer sql = new StringBuffer(" SELECT h.id,t.id AS tradeId,u.mobile,v.tname,p.trade_channel AS tradeChannel, ");
		sql.append(" t.account_name AS accountName,t.account_no AS accountNo,t.group_id AS groupId, ");
		sql.append(" sum(t.lever_money) leverMoney,sum(t.total_lever_money) totalLeverMoney, ");
		sql.append(" sum(h.finished_money) AS finishedMoney,h.audit_end_status AS auditEndStatus, ");
		sql.append(" h.end_audit_user_name AS endAuditUserName,h.end_audit_time AS endAuditTime, ");
		sql.append(" h.end_sub_time AS endSubTime,h.update_user AS updateUser,h.end_audit_firste_time AS endAuditFirsteTime ");
		sql.append(" FROM hk_user_trade_extend h, hk_user_trade t,w_user u,w_user_verified v ,hk_parent_account p ");
		sql.append(" WHERE t.id = h.trade_id AND u.id = t.uid AND u.id = v.uid AND t.parent_account_id=p.id  ");
		String typeSql = null;
		if(type == 0){
			typeSql = " AND h.audit_end_status = 0 AND h.finished_money IS NULL ";
		}else if(type == 1){
			typeSql = " AND h.audit_end_status = 0 AND h.finished_money IS NOT NULL ";
		}else{
			typeSql = " AND h.audit_end_status != -1 ";
		}
		sql.append(typeSql);
		sql.append(" GROUP BY t.group_id ");

		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql.toString());

		pageInfo = multiListPageQuery(multilistParam, HkEndTradeVo.class);
		return pageInfo; 
	}

	/**
	 * 根据方案号，获取方案审核信息
	 * @param tradeId
	 * @return
	 */
	public List<HkUserTradeExtend> findByTradeId(String tradeId){

		return this.getEntityDao().findByTradeId(tradeId);
	}

	/**
	 * 终结方案【初审通过】
	 * @param groupId 方案组合号
	 * @param amountValue  结算金额
	 */
	public void updateEndTradePassFirste(String groupId,BigDecimal amountValue) {

		User loginUser = authService.getCurrentUser();

		List<HkUserTrade> hkUserTradeList = this.hkUserTradeService.findByGroupId(groupId);

		List<HkUserTradeExtend> hkUserTradeExtendList = new ArrayList<HkUserTradeExtend>();  //方案审核信息

		if(hkUserTradeList == null || hkUserTradeList.isEmpty()){
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"根据该组合ID未找到该方案信息，执行失败!"});
		}

		for (HkUserTrade ut:hkUserTradeList) {  //遍历收集审核信息
			List<HkUserTradeExtend> hkUserTradeExtends = this.getEntityDao().findByTradeId(ut.getId());
			if(hkUserTradeExtends != null && !hkUserTradeList.isEmpty()){
				for (HkUserTradeExtend ht:hkUserTradeExtends) {
					if(!(ht.getAuditEndStatus() == 0 && ht.getFinishedMoney() == null)){
						throw new UserTradeConcurrentException(
								"com.tzdr.business.agent.error", new String[]{"该记录已经审核过，不能重复审核，执行失败!"});
					}
					hkUserTradeExtendList.add(ht);
				}
			}
		}

		if(hkUserTradeExtendList == null || hkUserTradeExtendList.isEmpty()){
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"根据该组合ID未找到该方案的审核信息，执行失败!"});
		}
	
		for (HkUserTradeExtend ht:hkUserTradeExtendList) {
			ht.setFinishedMoney(amountValue.doubleValue());
			if(ht.getEndAuditFirsteTime() == null){   //判断审1时间，如果非空为审核2填写金额不一致，打回审1，此时审核通过不需要赋值审1时间
				ht.setEndAuditFirsteTime(Dates.getCurrentLongDate());
			}
			ht.setUpdateTime(Dates.getCurrentLongDate());   //初审人信息
			ht.setUpdateUser(loginUser.getRealname());
			ht.setUpdateUserOrg(loginUser.getOrganization().getName());
			ht.setUpdateUserId(loginUser.getId());
			this.getEntityDao().update(ht);
		}
	}

	/**
	 * 终结方案【复审】
	 * @param groupId 方案组合号
	 * @param amountValue  结算金额
	 */
	public void updateEndTradePassRecheck(String groupId,BigDecimal amountValue) {
		
		List<HkUserTrade> hkUserTradeList = this.hkUserTradeService.findByGroupId(groupId);

		List<HkUserTradeExtend> hkUserTradeExtendList = new ArrayList<HkUserTradeExtend>();  //方案审核信息

		if(hkUserTradeList == null || hkUserTradeList.isEmpty()){
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"根据该组合ID未找到该方案信息，执行失败!"});
		}

		for (HkUserTrade ut:hkUserTradeList) {  //遍历收集审核信息
			List<HkUserTradeExtend> hkUserTradeExtends = this.getEntityDao().findByTradeId(ut.getId());
			if(hkUserTradeExtends != null && !hkUserTradeList.isEmpty()){
				for (HkUserTradeExtend ht:hkUserTradeExtends) {
					if(!(ht.getAuditEndStatus() == 0 && ht.getFinishedMoney() != null) || ut.getStatus() == 2){
						throw new UserTradeConcurrentException(
								"com.tzdr.business.agent.error", new String[]{"该记录已经审核过，不能重复审核，执行失败!"});
					}
					hkUserTradeExtendList.add(ht);
				}
			}
		}

		if(hkUserTradeExtendList == null || hkUserTradeExtendList.isEmpty()){
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"根据该组合ID未找到该方案的审核信息，执行失败!"});
		}
		
		User loginUser = authService.getCurrentUser();
		
		amountValue = TypeConvert.scale(amountValue, TypeConvert.SCALE_VALUE);

		boolean isPass = true;
		
		for (HkUserTradeExtend ht:hkUserTradeExtendList) {    //更新审核信息
			Double finishedMoney = ht.getFinishedMoney();
			BigDecimal finMoney = new BigDecimal(finishedMoney.toString());
			ht.setEndAuditTime(Dates.getCurrentLongDate());  //复审人信息
			ht.setEndAuditUserName(loginUser.getRealname());
			ht.setEndAuditUserId(loginUser.getId());
			if (finMoney.compareTo(amountValue) != 0) {  //判断复审金额是否和初审核一致，不一致打回初审
				ht.setFinishedMoney(null);
				isPass = false;
			}else{
				ht.setAuditEndStatus(TypeConvert.PLAN_PASS);
			}
			this.getEntityDao().update(ht);
		}
		
		if(isPass){  //判断是否可以终结方案
			this.hkUserTradeService.endOfWellGoldProgram(groupId,amountValue.doubleValue());
		}
	}

	/**
	 * 终结方案【初审、复审】
	 * @param groupId 方案组合号
	 * @param amountValue  结算金额
	 * @param tabType  1:初审  2：复审
	 */
	public void updateEndTradeNoPass(String groupId,String failCause,int tabType) {

		User loginUser = authService.getCurrentUser();

		List<HkUserTrade> hkUserTradeList = this.hkUserTradeService.findByGroupId(groupId);

		List<HkUserTradeExtend> hkUserTradeExtendList = new ArrayList<HkUserTradeExtend>();  //方案审核信息

		if(hkUserTradeList == null || hkUserTradeList.isEmpty()){
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"根据该组合ID未找到该方案信息，执行失败!"});
		}

		for (HkUserTrade ut:hkUserTradeList) {  //遍历收集审核信息
			List<HkUserTradeExtend> hkUserTradeExtends = this.getEntityDao().findByTradeId(ut.getId());
			if(hkUserTradeExtends != null && !hkUserTradeList.isEmpty()){
				for (HkUserTradeExtend ht:hkUserTradeExtends) {
					if(ht.getAuditEndStatus() != 0){
						throw new UserTradeConcurrentException(
								"com.tzdr.business.agent.error", new String[]{"该记录已经审核过，不能重复审核，执行失败!"});
					}
					hkUserTradeExtendList.add(ht);
				}
			}
		}

		if(hkUserTradeExtendList == null || hkUserTradeExtendList.isEmpty()){
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"根据该组合ID未找到该方案的审核信息，执行失败!"});
		}

		for (HkUserTradeExtend ht:hkUserTradeExtendList) {    //更新审核信息
			if(tabType == 1){
				ht.setUpdateTime(Dates.getCurrentLongDate());   //初审人信息
				ht.setUpdateUser(loginUser.getRealname());
				ht.setUpdateUserOrg(loginUser.getOrganization().getName());
				ht.setUpdateUserId(loginUser.getId());
			}else{
				ht.setEndAuditTime(Dates.getCurrentLongDate());  //复审人信息
				ht.setEndAuditUserName(loginUser.getRealname());
				ht.setEndAuditUserId(loginUser.getId());
			}
			ht.setAuditEndStatus(TypeConvert.PLAN_NO_PASS);
			ht.setFailCause(failCause);
			ht.setFinishedMoney(null);
			ht.setEndAuditFirsteTime(null);     
			this.getEntityDao().update(ht);
		}

		/**
		 * 发送失败原因短信
		 */
		if (hkUserTradeList.size() > 0) {
			try {
				HkUserTrade hkUserTrade = hkUserTradeList.get(0);
				String mobile = hkUserTrade.getWuser().getMobile();
				SMSSender.getInstance().sendByTemplateClasspath(dataMapService.getSmsContentOthers(), "planEnd01", mobile, hkUserTrade.getProgramNo(), failCause);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
