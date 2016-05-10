package com.tzdr.business.service.userTrade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.userTrade.FSimpleAppendLevelMoneyService;
import com.tzdr.business.service.userTrade.FSimpleUserTradeService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.userTrade.FSimpleUserTradeDao;
import com.tzdr.domain.vo.FSimpleUserTradeVo;
import com.tzdr.domain.vo.FSimpleUserTradeWebVo;
import com.tzdr.domain.web.entity.FSimpleAppendLevelMoney;
import com.tzdr.domain.web.entity.FSimpleUserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 
 * FSimpleUserTradeServiceImpl
 * @version 2.0
 * 2015年2月5日下午7:33:13
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FSimpleUserTradeServiceImpl extends
		BaseServiceImpl<FSimpleUserTrade, FSimpleUserTradeDao> implements FSimpleUserTradeService {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory
			.getLogger(FSimpleUserTradeServiceImpl.class);
	
	@Autowired
	private RechargeListService rechargeListService;
	
	@Autowired
	private FSimpleAppendLevelMoneyService fSimpleAppendLevelMoneyService;

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FSimpleUserTradeVo> queryApplyListVo(
			PageInfo<FSimpleUserTradeVo> pageInfo, ConnditionVo connVo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(
				  " SELECT f.id ,f.program_no, f.trader_total ,f.fee_manage,f.trader_bond ,f.line_loss,f.tran_day ,f.app_time ,f.tran_account,f.tran_password,f.state_type,w.mobile,v.tname,f.tran_lever,f.return_fee_manage,f.use_tran_day,f.business_type "
                  +" FROM f_simple_user_trade f LEFT JOIN w_user w ON f.uid=w.id LEFT JOIN w_user_verified v ON f.uid=v.uid WHERE f.state_type=1 or f.state_type=-1 or f.state_type=2 ORDER BY IF(f.state_type=2,0.5,f.state_type) DESC,f.app_time");
		return this.getEntityDao().queryPageBySql(pageInfo, sql.toString(), FSimpleUserTradeVo.class, connVo, params.toArray());
	
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FSimpleUserTradeVo> queryBalanceListVo(
			PageInfo<FSimpleUserTradeVo> pageInfo, ConnditionVo connVo) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT f.id ,f.program_no,f.trader_bond ,f.app_starttime,f.app_endtime, ");
		sql.append(" f.tran_commission ,f.tran_profit_loss ,f.end_amount,f.tran_account, ");
		sql.append(" f.state_type,w.mobile,f.append_trader_bond,v.tname,f.trader_total,f.end_time,f.tran_day,f.tran_lever,f.fee_manage,f.return_fee_manage,f.app_time,f.use_tran_day,f.business_type ");
		sql.append(" FROM f_simple_user_trade f ");
		sql.append(" LEFT JOIN w_user w ON f.uid=w.id ");
		sql.append(" LEFT JOIN w_user_verified v ON f.uid=v.uid ");
		sql.append(" WHERE ");
		String sqlStateType = "	(f.state_type=2 OR f.state_type=3 OR f.state_type=4) ";
		if(connVo == null){
			sql.append(sqlStateType);
		}else{
			String mobileStr = connVo.getValueStr("mobileStr");
			String tname = connVo.getValueStr("tname");
			String tranAccount = connVo.getValueStr("tranAccount");
			String appStarttime = connVo.getValueStr("appStarttime");
			String appEndtime = connVo.getValueStr("appEndtime");
			String stateType = connVo.getValueStr("stateType");
			String businessType = connVo.getValueStr("businessType");
			if(StringUtil.isBlank(stateType)){
				sql.append(sqlStateType);
			}else{
				sql.append(" 1=1 ");
			}
			if(StringUtil.isNotBlank(mobileStr)){
				sql.append(" AND w.mobile like '%"+mobileStr+"%' ");
			}
			if(StringUtil.isNotBlank(tname)){
				sql.append(" AND v.tname LIKE '%"+tname+"%' ");
			}
			if(StringUtil.isNotBlank(tranAccount)){
				sql.append(" AND f.tran_account LIKE '%"+tranAccount+"%' ");
			}
			if(StringUtil.isNotBlank(appStarttime)){
				Date startdate=DateUtils.stringToDate(appStarttime, "yyyy-MM-dd");
				Long sdate=startdate.getTime()/1000;
				sql.append(" AND f.app_starttime >= ? ");
				params.add(sdate);
			}
			if(StringUtil.isNotBlank(appEndtime)){
				String endtime=appEndtime+" 23:59:59";
				Date enddate=DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
				Long edate=enddate.getTime()/1000;
				sql.append(" AND f.app_starttime<= ? ");
				params.add(edate);
			}
			if(StringUtil.isNotBlank(stateType)){
				sql.append(" AND f.state_type=? ");
				params.add(stateType);
			}
			if(StringUtil.isNotBlank(businessType)){
				sql.append(" AND f.business_type=? ");
				params.add(businessType);
			}
		}
		
		sql.append(" ORDER BY IF(f.state_type=4,2.5,f.state_type) ,f.app_endtime ");
		
		return this.getEntityDao().queryPageBySql(pageInfo, sql.toString(), FSimpleUserTradeVo.class, connVo, params.toArray());
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FSimpleUserTradeVo> queryBalanceListVo(ConnditionVo connVo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT f.id ,f.program_no,f.trader_bond ,f.app_starttime,f.app_endtime, ");
		sql.append(" f.tran_commission ,f.tran_profit_loss ,f.end_amount,f.tran_account, ");
		sql.append(" f.state_type,w.mobile,f.append_trader_bond,v.tname,f.trader_total,f.end_time,f.tran_day,f.tran_lever,f.fee_manage,f.return_fee_manage,f.app_time,f.use_tran_day,f.business_type ");
		sql.append(" FROM f_simple_user_trade f ");
		sql.append(" LEFT JOIN w_user w ON f.uid=w.id ");
		sql.append(" LEFT JOIN w_user_verified v ON f.uid=v.uid ");
		sql.append(" WHERE ");
		String sqlStateType = "	(f.state_type=2 OR f.state_type=3 OR f.state_type=4) ";
		if(connVo == null){
			sql.append(sqlStateType);
		}else{
			String mobileStr = connVo.getValueStr("mobileStr");
			String tname = connVo.getValueStr("tname");
			String tranAccount = connVo.getValueStr("tranAccount");
			String appStarttime = connVo.getValueStr("appStarttime");
			String appEndtime = connVo.getValueStr("appEndtime");
			String stateType = connVo.getValueStr("stateType");
			String businessType = connVo.getValueStr("businessType");
			if(StringUtil.isBlank(stateType)){
				sql.append(sqlStateType);
			}else{
				sql.append(" 1=1 ");
			}
			if(StringUtil.isNotBlank(mobileStr)){
				sql.append(" AND w.mobile like '%"+mobileStr+"%' ");
			}
			if(StringUtil.isNotBlank(tname)){
				sql.append(" AND v.tname LIKE '%"+tname+"%' ");
			}
			if(StringUtil.isNotBlank(tranAccount)){
				sql.append(" AND f.tran_account LIKE '%"+tranAccount+"%' ");
			}
			if(StringUtil.isNotBlank(appStarttime)){
				Date startdate=DateUtils.stringToDate(appStarttime, "yyyy-MM-dd");
				Long sdate=startdate.getTime()/1000;
				sql.append(" AND f.app_starttime >= ? ");
				params.add(sdate);
			}
			if(StringUtil.isNotBlank(appEndtime)){
				String endtime=appEndtime+" 23:59:59";
				Date enddate=DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
				Long edate=enddate.getTime()/1000;
				sql.append(" AND f.app_starttime<= ? ");
				params.add(edate);
			}
			if(StringUtil.isNotBlank(stateType)){
				sql.append(" AND f.state_type=? ");
				params.add(stateType);
			}
			if(StringUtil.isNotBlank(businessType)){
				sql.append(" AND f.business_type=? ");
				params.add(businessType);
			}
		}
		
		sql.append(" ORDER BY IF(f.state_type=4,2.5,f.state_type) ,f.app_endtime ");
		
		return this.getEntityDao().queryListBySql(sql.toString(), FSimpleUserTradeVo.class, connVo, params.toArray());
	}
	
	@Override
	public FSimpleUserTrade executePayable(FSimpleUserTrade st,String mobile,BigDecimal payable) throws Exception {
		st.setAppTime(TypeConvert.dbDefaultDate());
		this.save(st);
		st.setProgramNo("GT" + st.getId());
		this.update(st);
		//st.setProgramNo(programNo);
		rechargeListService.futureHandlerSaveRechargeStateWeb("GT"+ st.getId(), 
				mobile, payable.toString(), "投资沪深300当期主力合约申请（划款）。", TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS);
		
		return st;
	}
	
	@SuppressWarnings("unchecked")
	public PageInfo<FSimpleUserTradeWebVo> findData(String pageIndex,String perPage,String type,String uid) {
		PageInfo<FSimpleUserTradeWebVo> pageInfo= new PageInfo<FSimpleUserTradeWebVo>(Integer.valueOf(perPage),Integer.valueOf(pageIndex)+1);
		pageInfo = this.getEntityDao().queryPageBySql(pageInfo," SELECT * FROM f_simple_user_trade f WHERE f.state_type=? AND f.uid = ?",
				FSimpleUserTradeWebVo.class,null,new Integer(type),uid);
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FSimpleUserTradeWebVo> findDataList(String pageIndex,
			String perPage,String type, String uid) {
		PageInfo<FSimpleUserTradeWebVo> pageInfo= new PageInfo<FSimpleUserTradeWebVo>(Integer.valueOf(perPage),Integer.valueOf(pageIndex)+1);
		pageInfo = this.getEntityDao().queryPageBySql(pageInfo," SELECT * FROM f_simple_user_trade f WHERE f.state_type!=-1 AND f.business_type=? AND f.uid = ? ORDER BY f.app_time DESC",
				FSimpleUserTradeWebVo.class,null,new Integer(type),uid);
		return pageInfo;
	}

	@Override
	public void addAppendTraderBond(FSimpleUserTrade fSimpleUserTrade,
			Double appendMoney, WUser wuser) throws Exception {
		
		BigDecimal payMoney = new BigDecimal(appendMoney);  //追加保证金
		
		fSimpleUserTrade.setAppendTraderBond(TypeConvert.scale(fSimpleUserTrade.getAppendTraderBond().add(payMoney),2));
		
		//追加保证金划款
		rechargeListService.futureHandlerSaveRechargeStateWeb(fSimpleUserTrade.getProgramNo(), 
				wuser.getMobile(), appendMoney.toString(), "股指期货追加保证金。", TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS);
		
		//更新股指追加保证金
		this.update(fSimpleUserTrade);
		
		//创建追加保证金记录
		FSimpleAppendLevelMoney fSimpleAppendLevelMoney = new FSimpleAppendLevelMoney(wuser.getId(),fSimpleUserTrade.getProgramNo(),appendMoney);
		
		fSimpleAppendLevelMoneyService.save(fSimpleAppendLevelMoney);  //保存追加保证金记录
	}


	@Override
	public void updateFSimpleUserTrade(FSimpleUserTrade fSimpleUserTrade,
			BigDecimal returnFeeManage, WUser wuser) throws Exception {

		this.update(fSimpleUserTrade);
		
		if(returnFeeManage.compareTo(new BigDecimal("0")) > 0){
			rechargeListService.futureHandlerSaveRechargeStateWeb("GT"+ fSimpleUserTrade.getId(),wuser.getMobile(), returnFeeManage.toString(), "股指期货返还管理费。", TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS);
		}
	}


	@Override
	public void updateFSimpleUserTrade(FSimpleUserTrade fSimpleUserTrade,
			String tradeNo, String mobileNo, String actualMoneyStr,
			String remark, String sysType) throws Exception {
		
		this.update(fSimpleUserTrade);  //更新方案
		
		//调帐
		this.rechargeListService.futureHandlerSaveRechargeState(tradeNo, mobileNo, actualMoneyStr, remark, sysType);
	}
	
}
