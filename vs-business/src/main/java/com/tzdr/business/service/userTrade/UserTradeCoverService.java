package com.tzdr.business.service.userTrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.userTrade.impl.UserTradeServiceImpl;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.userTrade.UserTradeCoverDao;
import com.tzdr.domain.vo.CoverAuditVo;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.UserTradeCover;
import com.tzdr.domain.web.entity.WUser;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserTradeCoverService extends BaseServiceImpl<UserTradeCover, UserTradeCoverDao>{
	
	private static Logger log = LoggerFactory.getLogger(UserTradeServiceImpl.class);
	
	@Autowired
	private WUserService wUserService;

	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private ParentAccountService parentAccountService;
	
	@Autowired
	private CombineInfoService combineInfoService;
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 提交补仓申请
	 */
	public void saveUserTradeCover(UserTradeCover userTradeCover){

		this.getEntityDao().save(userTradeCover);
	}
	
	/**
	 * 获取补仓待审核列表数据
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryUnUserTradeCoverVoList(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.id,u.id AS uid,u.mobile,v.tname,t.group_id groupId,acc.account_name AS accountName, ");
		sql.append(" t.account,c.cover_money AS coverMoney,c.ctime ");
		sql.append(" FROM w_user_trade_cover c,w_user_trade t, w_user u, w_user_verified v, w_account acc ");
		sql.append(" WHERE c.`status`=0 AND c.group_id=t.group_id AND c.uid=t.uid AND t.`status` = 1 ");
		sql.append(" AND u.id = t.uid AND u.id = v.uid AND t.account_id = acc.id and t.fee_type=2 ");
		sql.append(" GROUP BY c.id ORDER BY c.ctime ASC ");
		
		MultiListParam multilistParam = new MultiListParam(easyUiPage,searchParams, null, sql.toString());

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());

		pageInfo = multiListPageQuery(multilistParam, CoverAuditVo.class);
		
		return pageInfo;
	}
	
	/**
	 * 获取补仓审核记录列表数据
	 * @param dataPage
	 * @param connVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageInfo<CoverAuditVo> queryUserTradeCoverVoList(
			PageInfo<CoverAuditVo> dataPage, ConnditionVo connVo) {
		List<Object> params = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.id,u.id AS uid,u.mobile,v.tname,c.group_id AS groupId, ");
		sql.append(" acc.account_name AS accountName,acc.account,c.cover_money AS coverMoney, ");
		sql.append(" c.ctime AS longCtime,c.`status`,c.upuid,c.uptime AS longUptime,sysu.realname AS upuName ");
		sql.append(" FROM w_user_trade_cover c,sys_user sysu,w_user_trade t,w_user u, w_user_verified v, w_account acc ");
		sql.append(" WHERE 1=1 ");
		if(connVo!=null){
			Object statusObj=connVo.getValue("status");
			if(TypeConvert.objToStrIsNotNull(statusObj)!=null){
				sql.append(" AND c.`status`=? ");
				params.add(Integer.valueOf(String.valueOf(statusObj)));
			}else{
				sql.append(" AND (c.`status`=1 OR c.`status`=2 ) ");
			}
			Object mobileObj=connVo.getValue("mobile");
			if(TypeConvert.objToStrIsNotNull(mobileObj)!=null){
				sql.append(" AND u.mobile LIKE ? ");
				params.add("%"+String.valueOf(mobileObj)+"%");
			}
			Object tnameObj=connVo.getValue("tname");
			if(TypeConvert.objToStrIsNotNull(tnameObj)!=null){
				sql.append(" AND v.tname LIKE ? ");
				params.add("%"+String.valueOf(tnameObj)+"%");
			}
			Object accountNameObj=connVo.getValue("accountName");
			if(TypeConvert.objToStrIsNotNull(accountNameObj)!=null){
				sql.append(" AND acc.account_name LIKE ? ");
				params.add("%"+String.valueOf(accountNameObj)+"%");
			}
			Object accountObj=connVo.getValue("account");
			if(TypeConvert.objToStrIsNotNull(accountObj)!=null){
				sql.append(" AND t.account LIKE ? ");
				params.add("%"+String.valueOf(accountObj)+"%");
			}
		}
		sql.append(" AND c.upuid=sysu.id");
		sql.append(" AND c.group_id=t.group_id AND c.uid=t.uid  AND u.id = t.uid ");
		sql.append(" AND u.id = v.uid AND t.account_id = acc.id ");
		sql.append(" GROUP BY c.id ORDER BY c.ctime ASC ");

		PageInfo<CoverAuditVo> page = getEntityDao().queryPageBySql(dataPage, sql.toString(), CoverAuditVo.class, null, params.toArray());
		
		return page;
	}
	
	/**
	 * 审核补仓记录
	 * @param id  补仓编号
	 * @param status  补仓状态
	 */
	public void updateUserTradeCover(String id,short status) throws Exception{
		UserTradeCover userTradeCover = this.get(id);
		if(userTradeCover != null && (status == 1 || status == 2)){
			if(userTradeCover.getStatus() != 0){
				throw new RuntimeException("该记录已经审核过，不能重复审核！");
			}else{
				userTradeCover.setUpuid(new String(authService.getCurrentUser().getId().toString()));
				userTradeCover.setUptime(Dates.getCurrentLongDate());
				userTradeCover.setStatus(status);
				this.getEntityDao().update(userTradeCover);  //更新状态
				if(status == 1){  //通过
					WUser wuser = userTradeCover.getWuser();  //获取用户信息
					//补仓
					this.saveCoverUserFund(userTradeCover.getGroupId(), wuser.getId(), userTradeCover.getCoverMoney());
				}
			}
		}
	}
	
	/**
	 * 补仓
	 * @param groupId  
	 * @param uid
	 * @param coverMoney
	 * @throws Exception 
	 */
	public void saveCoverUserFund(String groupId,String uid,Double coverMoney) throws Exception{
		//查询账户信息
		WUser wuser = wUserService.get(uid);
		
		List<UserTrade> userTradeList = userTradeService.findByGroupIdAndWuser(groupId, wuser);
		
		UserTrade userTrade = userTradeList == null || userTradeList.isEmpty() ? null : userTradeList.get(0);
		
		if(userTrade != null && userTrade.getStatus() == 1){  //操盘中
			double avlBal = wuser.getAvlBal();  //余额
			
			double finalBalance = 0.00;  //最终余额
			
			List<UserFund> userFundList = new ArrayList<UserFund>();
			
			if((avlBal > 0 && avlBal >= coverMoney) || avlBal == 0 || avlBal < 0){ //支出补仓金额
				finalBalance = BigDecimalUtils.sub(avlBal,coverMoney);
				UserFund coverUserFund =  new UserFund();   
				coverUserFund.setAddtime(Dates.getCurrentLongDate());
				coverUserFund.setAmount(BigDecimalUtils.round(finalBalance, 2));
				coverUserFund.setFreeze(0);
				coverUserFund.setLid(userTrade.getGroupId());
				coverUserFund.setMoney(BigDecimalUtils.round(-coverMoney, 2));
				short payStatus = avlBal > 0 && avlBal >= coverMoney ? (short)1 : (short)0;
				coverUserFund.setPayStatus(payStatus);
				coverUserFund.setRid(userTrade.getProgramNo());
				coverUserFund.setType(27);
				coverUserFund.setUid(uid);
				coverUserFund.setUptime(Dates.getCurrentLongDate());
				coverUserFund.setNo(userTrade.getId());
				coverUserFund.setRemark("补仓欠费"+BigDecimalUtils.round(-coverMoney, 2)+"元");
				coverUserFund.setTypeStatus(0);
				userFundList.add(coverUserFund);
			}else{
				//支出补仓欠费明细
				UserFund arrearageUserFundOne =  new UserFund();  //支出补仓金额 
				arrearageUserFundOne.setAddtime(Dates.getCurrentLongDate());
				arrearageUserFundOne.setAmount(BigDecimalUtils.round(0, 2));
				arrearageUserFundOne.setFreeze(0);
				arrearageUserFundOne.setLid(userTrade.getGroupId());
				arrearageUserFundOne.setMoney(BigDecimalUtils.round(-avlBal, 2));
				arrearageUserFundOne.setPayStatus((short)1);
				arrearageUserFundOne.setRid(userTrade.getProgramNo());
				arrearageUserFundOne.setType(27);
				arrearageUserFundOne.setUid(uid);
				arrearageUserFundOne.setUptime(Dates.getCurrentLongDate());
				arrearageUserFundOne.setNo(userTrade.getId());
				arrearageUserFundOne.setRemark("补仓欠费"+BigDecimalUtils.round(-avlBal, 2)+"元");
				arrearageUserFundOne.setTypeStatus(0);
				userFundList.add(arrearageUserFundOne);
				
				finalBalance = BigDecimalUtils.sub(avlBal,coverMoney);   //支出补仓欠费明细
				UserFund arrearageUserFundTwo =  new UserFund();   
				arrearageUserFundTwo.setAddtime(Dates.getCurrentLongDate()+1);
				arrearageUserFundTwo.setAmount(BigDecimalUtils.round(finalBalance, 2));
				arrearageUserFundTwo.setFreeze(0);
				arrearageUserFundTwo.setLid(userTrade.getGroupId());
				arrearageUserFundTwo.setMoney(BigDecimalUtils.round(finalBalance, 2));
				arrearageUserFundTwo.setPayStatus((short)0);
				arrearageUserFundTwo.setRid(userTrade.getProgramNo());
				arrearageUserFundTwo.setType(27);
				arrearageUserFundTwo.setUid(uid);
				arrearageUserFundTwo.setNo(userTrade.getId());
				arrearageUserFundTwo.setRemark("补仓欠费"+BigDecimalUtils.round(finalBalance, 2)+"元");
				arrearageUserFundTwo.setTypeStatus(0);
				userFundList.add(arrearageUserFundTwo);
			}
			
			//创建补仓流水
			userFundService.saves(userFundList);
			
			//更新账户资金
			wuser.setAvlBal(BigDecimalUtils.round(finalBalance, 2));
			wUserService.update(wuser);
			
			//划钱
			ParentAccount  parentAccount = parentAccountService.findByAccountNo(userTrade.getParentAccountNo());
			parentAccount.setFundsBalance(BigDecimalUtils.subRound(
					parentAccount.getFundsBalance(), coverMoney));
			parentAccountService.updateParentAccount(parentAccount);
			
			if(userTrade.getFeeType() == 0 || userTrade.getFeeType() == 1 ){
				String parentCombineId = combineInfoService.getHundSunCombineId(userTrade.getUnitNumber());

				boolean transFlag = userTradeService.transferMoney(parentAccount.getAccountNo(), parentCombineId,
						userTrade.getCombineId(), coverMoney, "补仓金额划转");	
				
				if(!transFlag){
					log.error("补仓金资金划转失败：parentAccount-"+parentAccount.getAccountNo()+"parentCombineId-"+parentCombineId+"targetCombineId-"+userTrade.getCombineId()+"money-"+coverMoney);
					throw new RuntimeException("补仓金资金划转失败");
				}
			}
		}
	}
}
