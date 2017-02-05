package com.tzdr.business.service.userTrade.impl;



import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FHandleFtseUserTradeService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.GeneralException;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.userTrade.FHandleFtseUserTradeDao;
import com.tzdr.domain.vo.ftse.FHandleFtseUserTradeVo;
import com.tzdr.domain.web.entity.FHandleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;

/**
 * 
 * <p></p>
 * @author WangPinQun
 * @see 
 * FHandleFtseUserTradeServiceImpl
 * @version 2.0
 * 2015年9月24日下午17:33:13
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FHandleFtseUserTradeServiceImpl extends BaseServiceImpl<FHandleFtseUserTrade, FHandleFtseUserTradeDao> implements FHandleFtseUserTradeService {

	private static Logger log = LoggerFactory.getLogger(FHandleFtseUserTradeServiceImpl.class);
	@Autowired
	private TradeDayService tradeDayService;

	@Override
	public PageInfo<Object> queryPageDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams,int businessType) throws Exception {

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT usr.mobile,\n");
		sql.append("		uver.tname,\n");
		sql.append("		hfut.app_end_time as appEndTime,\n");
		sql.append("		hfut.app_starttime as appStarttime,\n");
		sql.append("		hfut.app_time as appTime,\n");
		sql.append("		hfut.business_type as businessType,\n");
		sql.append("		hfut.end_amount as endAmount,\n");
		sql.append("		hfut.end_parities as endParities,\n");
		sql.append("		hfut.end_time as endTime,\n");
		sql.append("		hfut.fee_manage as feeManage,\n");
		//sql.append("		hfut.golden_money as goldenMoney,\n");
		sql.append("		hfut.line_loss as lineLoss,\n");
		sql.append("		hfut.program_no as programNo,\n");
		sql.append("		hfut.state_type as stateType,\n");
		sql.append("		hfut.trader_bond as traderBond,\n");
		sql.append("		hfut.trader_total as traderTotal,\n");
		sql.append("		hfut.tran_account as tranAccount,\n");
		sql.append("		hfut.tran_actual_lever as tranActualLever,\n");
		sql.append("		hfut.tran_fees as tranFees,\n");
		sql.append("		hfut.tran_fees_total as tranFeesTotal,\n");
		sql.append("		hfut.tran_lever as tranLever,\n");
		sql.append("		hfut.tran_password as tranPassword,\n");
		sql.append("		hfut.tran_profit_loss as tranProfitLoss,\n");
		sql.append("		hfut.uid,\n");
		sql.append("		hfut.update_time as updateTime,\n");
		sql.append("		hfut.use_tran_day as useTranDay\n");
		sql.append("FROM f_handle_ftse_user_trade hfut,w_user usr, w_user_verified uver\n");
		sql.append("WHERE usr.id = hfut.uid\n");
		sql.append("	AND usr.id = uver.uid\n");
		sql.append("	AND hfut.business_type = "+businessType+"\n");
		sql.append("ORDER BY hfut.app_time DESC");
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,searchParams, params, sql.toString());
		pageInfo = multiListPageQuery(multilistParam, FHandleFtseUserTradeVo.class);
		// 计算已操盘时间
		List<Object> list = pageInfo.getPageResults();
		for(Object obj :list ){
			FHandleFtseUserTradeVo simpleFtseVo = (FHandleFtseUserTradeVo)obj;
			//状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
			if(simpleFtseVo.getState()==2 || simpleFtseVo.getState()==3 || simpleFtseVo.getState()==4){
				simpleFtseVo.setUseTranDay(tradeDayService.getTradeDayNum(simpleFtseVo.getAppStarttimeLong().longValue(),14));
			}
		}
		return pageInfo;
	}

//	@Override
//	public void saveHandleFtseUserTrade(FSimpleFtseUserTrade simpleFtseUserTrade) throws Exception{
//		//状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
//		if(simpleFtseUserTrade != null){
//			String mark="";
//			if(simpleFtseUserTrade.getBusinessType()==0){
//				mark="富时A50保存收益报表时";
//			}else if(simpleFtseUserTrade.getBusinessType()==1){
//				mark="沪金保存收益报表时";
//			}else if(simpleFtseUserTrade.getBusinessType()==2){
//				mark="沪银保存收益报表时";
//			}else if(simpleFtseUserTrade.getBusinessType()==3){
//				mark="沪铜保存收益报表时";
//			}else if(simpleFtseUserTrade.getBusinessType()==4){
//				mark="橡胶保存收益报表时";
//			}else if(simpleFtseUserTrade.getBusinessType()==6){
//				mark="国际原油保存收益报表时";
//			}else if(simpleFtseUserTrade.getBusinessType()==7){
//				mark="恒生指数保存收益报表时";
//			}
//			if(simpleFtseUserTrade.getStateType() == null){
//				GeneralException exception = new GeneralException(mark+"，状态为NULL。");
//				log.error(mark+"，状态为NULL。", exception);
//				throw exception;
//			}
//			FHandleFtseUserTrade handleFtseUserTrade = new FHandleFtseUserTrade();
//			if(simpleFtseUserTrade.getAppTime()==null||
//					simpleFtseUserTrade.getBusinessType()==null||
//					simpleFtseUserTrade.getLineLoss()==null||
//					simpleFtseUserTrade.getProgramNo()==null||
//					simpleFtseUserTrade.getTraderBond()==null||
//					simpleFtseUserTrade.getTraderTotal()==null||
//					simpleFtseUserTrade.getTranLever()==null||
//					simpleFtseUserTrade.getUid()==null
//					){
//					GeneralException exception = new GeneralException(mark+"，方案申请参数异常。");
//					log.error(mark+"，方案申请参数异常。", exception);
//					throw exception;
//			}else if(simpleFtseUserTrade.getBusinessType()==0 && simpleFtseUserTrade.getGoldenMoney()==null){
//				GeneralException exception = new GeneralException(mark+"，方案申请参数异常。");
//				log.error(mark+"，方案申请参数异常。", exception);
//				throw exception;
//			}
//			
//			if(simpleFtseUserTrade.getStateType().intValue() == 4){ //4.操盘中 
//				if(simpleFtseUserTrade.getTranAccount()==null||
//						simpleFtseUserTrade.getTranPassword()==null||
//						simpleFtseUserTrade.getUpdateTime()==null
//						){
//					GeneralException exception = new GeneralException(mark+"，状态:操盘中。保存参数异常。");
//					log.error(mark+"，状态:操盘中。保存参数异常。", exception);
//					throw exception;
//				}
//			}
//			if(simpleFtseUserTrade.getStateType().intValue() == 5){ //5.审核不通过
//				if(simpleFtseUserTrade.getUpdateTime()==null){
//					GeneralException exception = new GeneralException(mark+"，状态:审核不通过。保存参数异常。");
//					log.error(mark+"，状态:审核不通过。保存参数异常。", exception);
//					throw exception;
//				}
//			}
//			if(simpleFtseUserTrade.getStateType().intValue() == 6){ //6.已结算
//				if(simpleFtseUserTrade.getTranActualLever()==null|| 
//						simpleFtseUserTrade.getTranFeesTotal()==null||
//						simpleFtseUserTrade.getTranFees()==null||
//						simpleFtseUserTrade.getTranProfitLoss()==null||
//						simpleFtseUserTrade.getUseTranDay()==null||
//						simpleFtseUserTrade.getEndParities()==null||
//						simpleFtseUserTrade.getEndAmount()==null||
//						simpleFtseUserTrade.getEndTime()==null
//						){
//					GeneralException exception = new GeneralException(mark+"，状态:已结算。保存参数异常。");
//					log.error(mark+"，状态:已结算。保存参数异常。", exception);
//					throw exception;
//				}
//			}
//			handleFtseUserTrade.setAppEndTime(simpleFtseUserTrade.getAppEndTime());
//			handleFtseUserTrade.setAppStarttime(simpleFtseUserTrade.getAppStarttime());
//			handleFtseUserTrade.setAppTime(simpleFtseUserTrade.getAppTime());
//			handleFtseUserTrade.setBusinessType(simpleFtseUserTrade.getBusinessType());
//			handleFtseUserTrade.setEndAmount(simpleFtseUserTrade.getEndAmount());
//			handleFtseUserTrade.setEndParities(simpleFtseUserTrade.getEndParities());
//			handleFtseUserTrade.setEndTime(simpleFtseUserTrade.getEndTime());
//			handleFtseUserTrade.setFeeManage(simpleFtseUserTrade.getFeeManage());
//			handleFtseUserTrade.setGoldenMoney(simpleFtseUserTrade.getGoldenMoney());
//			handleFtseUserTrade.setLineLoss(simpleFtseUserTrade.getLineLoss());
//			handleFtseUserTrade.setProgramNo(simpleFtseUserTrade.getProgramNo());
//			handleFtseUserTrade.setStateType(simpleFtseUserTrade.getStateType());
//			handleFtseUserTrade.setTraderBond(simpleFtseUserTrade.getTraderBond());
//			handleFtseUserTrade.setTraderTotal(simpleFtseUserTrade.getTraderTotal());
//			handleFtseUserTrade.setTranAccount(simpleFtseUserTrade.getTranAccount());
//			handleFtseUserTrade.setTranActualLever(simpleFtseUserTrade.getTranActualLever());
//			handleFtseUserTrade.setTranFees(simpleFtseUserTrade.getTranFees());
//			handleFtseUserTrade.setTranFeesTotal(simpleFtseUserTrade.getTranFeesTotal());
//			handleFtseUserTrade.setTranLever(simpleFtseUserTrade.getTranLever());
//			handleFtseUserTrade.setTranPassword(simpleFtseUserTrade.getTranPassword());
//			handleFtseUserTrade.setTranProfitLoss(simpleFtseUserTrade.getTranProfitLoss());
//			handleFtseUserTrade.setUid(simpleFtseUserTrade.getUid());
//			handleFtseUserTrade.setUpdateTime(simpleFtseUserTrade.getUpdateTime());
//			handleFtseUserTrade.setUseTranDay(simpleFtseUserTrade.getUseTranDay());
//			handleFtseUserTrade.setAppendTraderBond(simpleFtseUserTrade.getAppendTraderBond());
//			this.save(handleFtseUserTrade);
//		}
//	}
	
}
