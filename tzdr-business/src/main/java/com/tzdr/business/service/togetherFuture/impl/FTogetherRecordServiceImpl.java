package com.tzdr.business.service.togetherFuture.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.togetherFuture.FTogetherRecordDetailService;
import com.tzdr.business.service.togetherFuture.FTogetherRecordService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.app.vo.UserFTogetherRecordVo;
import com.tzdr.domain.dao.fTogetherTrade.FTogetherRecordDao;
import com.tzdr.domain.vo.FTogetherRecordVo;
import com.tzdr.domain.web.entity.FTogetherRecord;
import com.tzdr.domain.web.entity.FTogetherRecordDetail;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;

/**
 * Created by huangkai on 2016/5/20.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FTogetherRecordServiceImpl extends BaseServiceImpl<FTogetherRecord,FTogetherRecordDao> implements FTogetherRecordService {

	@Autowired
	private FTogetherRecordDetailService fTogetherRecordDetailService ;
	
	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private UserFundService  userFundService;
	
	@Override
	public UserFTogetherRecordVo queryUserTogetherRecord(String uid,
			String recordId) {
		String sql = " SELECT ftrade.full_copies fullCopies, ftrade.species ,record.id,ftrade.id tradeId, ftrade.`name`, ftrade.`status`, ftrade.open_time openTime, record.direction,"
				+ " record.copies, record.back_copies backCopies, ftrade.contract, record.achieve_profit_loss "
				+ "	achieveProfitLoss,ftrade.operate_time operateTime ,  record.actual_settlement_money actualSettlementMoney, record.back_money backMoney,"
				+ " record.expect_settlement_money expectSettlementMoney, record.pay_money payMoney,"
				+ " record.poundage, ftrade.price, ( SELECT count(0) FROM f_together_record_detail fdetail "
				+ " WHERE fdetail.trade_id = ftrade.id AND fdetail.direction = record.direction AND fdetail.is_back = 0 ) sameDireCopies,"
				+ " ( SELECT count(0) FROM f_together_record_detail fdetail WHERE fdetail.trade_id = ftrade.id AND fdetail.direction = record.direction ) allSameDireCopies,"
				+ " ( SELECT count(0) FROM f_together_record_detail fdetail WHERE fdetail.trade_id = ftrade.id ) allCopies "
				+ " FROM f_together_record record, f_together_trade ftrade WHERE record.trade_id = ftrade.id "
				+ " AND record.uid = ?  AND record.id = ? ";
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(recordId);
		List<UserFTogetherRecordVo>   fTogetherRecordVos = nativeQuery(sql,params,UserFTogetherRecordVo.class);
		if (CollectionUtils.isEmpty(fTogetherRecordVos)){
			return null;
		}
		return fTogetherRecordVos.get(0);
	}

	@Override
	public void createTogetherRecord(FTogetherRecord fTogetherRecord,BigDecimal payMoney,Integer copies,WUser wuser) {
		// 先保存或更新合买记录
		if (StringUtil.isBlank(fTogetherRecord.getId())){
			fTogetherRecord.setCopies(copies);
			fTogetherRecord.setPayMoney(payMoney);
			fTogetherRecord.setCreateTime(Dates.getCurrentLongDate());
			this.save(fTogetherRecord);
		}else
		{
			fTogetherRecord.setCopies(fTogetherRecord.getCopies()+copies);
			fTogetherRecord.setPayMoney(payMoney.add(fTogetherRecord.getPayMoney()));
			this.update(fTogetherRecord);
		}
		//用户扣款
		wuser.setAvlBal(BigDecimalUtils.round(wuser.getAvlBal()-payMoney.doubleValue(), 2));
		wuserService.updateUser(wuser);
		
		UserFund userFund = new UserFund();
		userFund.setUid(wuser.getId());
		userFund.setNo(fTogetherRecord.getId());
		//类型为系统冲涨
		userFund.setType(4);
		userFund.setMoney(-payMoney.doubleValue());
		userFund.setAmount(wuser.getAvlBal());
		userFund.setPayStatus((short) 1);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setUptime(Dates.getCurrentLongDate());
		userFund.setRemark("期货合买支付现金金额。");
		userFundService.save(userFund);
		// 生成合买记录详情
		for (int i=0;i<copies;i++){
			FTogetherRecordDetail fTogetherRecordDetail = new FTogetherRecordDetail(fTogetherRecord);
			fTogetherRecordDetailService.save(fTogetherRecordDetail);
		}
	}

	@Override
	public FTogetherRecord findByTradeIdAndDirectionAndUid(
			String tradeID, Integer direction, String uid) {
		return this.getEntityDao().findByTradeIdAndDirectionAndUid(tradeID, direction, uid);
	}

	@Override
	public List<FTogetherRecord> findByTradeId(String tradeID) {
		return this.getEntityDao().findByTradeId(tradeID);
	}
	/**
	 * 分页
	 */
	@Override
	public PageInfo<FTogetherRecordVo> getDatas(PageInfo<FTogetherRecordVo> dataPage,
			ConnditionVo connVo) {
		// TODO Auto-generated method stub
		
		Map<String, Object> paramValues = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select  (record.copies -record.back_copies) as copies, ");
		sql.append("(record.pay_money -record.back_money) as payMoney, ");
		sql.append("record.poundage as poundage, ");
		sql.append("record.profit_loss_point as profitLossPoint, ");
		sql.append("record.achieve_profit_loss as achieveProfitLoss, ");
		sql.append("record.actual_settlement_money as actualSettlementMoney,  ");
		sql.append("record.expect_settlement_money as expectSettlementMoney, ");
		sql.append("record.settlement_time as settlementTime, ");
		sql.append("user.mobile as mobile, ");
		sql.append("verified.tname as userName, ");
		sql.append("trade.name as tradeName, ");
		sql.append("trade.trade_no as tradeNo, ");
		sql.append("trade.species as species ");
		sql.append("from f_together_record record,w_user user,f_together_trade trade, w_user_verified verified ");
		sql.append("where record.trade_id = trade.id and record.uid = user.id and user.id = verified.uid and trade.status =3 and record.copies > record.back_copies ");

		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		Long startTime = TypeConvert.longCriticalTimeDay(0);
		Long endTime = TypeConvert.strToZeroDate1000(TypeConvert.dbDateYmd(), 1, -1);
		if(StringUtil.isNotBlank(starttimeStr_start)){
			startTime = TypeConvert.strToZeroDate1000(starttimeStr_start, 0);
			sql.append(" and record.settlement_time >=:beginTime ");
			paramValues.put("beginTime", startTime);
		}
		if(StringUtil.isNotBlank(starttimeStr_end)){
			endTime = TypeConvert.strToZeroDate1000(starttimeStr_end, 1, -1);
			sql.append(" and record.settlement_time <= :endTime ");
			paramValues.put("endTime", endTime);
		}
		
		
		
		String mobileString = connVo.getValueStr("mobile");
		if(!StringUtil.isBlank(mobileString)){
			sql.append(" and user.mobile =:mobile ");
			paramValues.put("mobile", mobileString);
		}
		String species = connVo.getValueStr("species");
		if(!StringUtil.isBlank(species)){
			sql.append(" and trade.species =:species");
			paramValues.put("species", species);
		}
		String sort = connVo.getSortFieldName();
		String sor = connVo.getSortType();
		if(!StringUtil.isBlank(sort) &&!StringUtil.isBlank(sor)){
			sql.append("order by  "+sort+" "+sor);
		}else{
			sql.append(" order by settlementTime DESC");
		}
		PageInfo<FTogetherRecordVo> pageInfo= getEntityDao().queryDataByParamsSql(dataPage, sql.toString(),FTogetherRecordVo.class,paramValues,null);
		
		return pageInfo;
	}

	@Override
	public List<FTogetherRecordVo> getRecordVos(ConnditionVo connVo) {
Map<String, Object> paramValues = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select (record.copies -record.back_copies) as copies, ");
		sql.append("(record.pay_money -record.back_money) as payMoney, ");
		sql.append("record.poundage as poundage, ");
		sql.append("record.profit_loss_point as profitLossPoint, ");
		sql.append("record.achieve_profit_loss as achieveProfitLoss, ");
		sql.append("record.actual_settlement_money as actualSettlementMoney,  ");
		sql.append("record.expect_settlement_money as expectSettlementMoney, ");
		sql.append("record.settlement_time as settlementTime, ");
		sql.append("user.mobile as mobile, ");
		sql.append("verified.tname as userName, ");
		sql.append("trade.name as tradeName, ");
		sql.append("trade.trade_no as tradeNo, ");
		sql.append("trade.species as species ");
		sql.append("from f_together_record record,w_user user,f_together_trade trade, w_user_verified verified ");
		sql.append(" where  ");
		sql.append("record.trade_id = trade.id and record.uid = user.id and user.id = verified.uid and trade.status =3 and record.copies > record.back_copies  ");
		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		Long startTime = TypeConvert.longCriticalTimeDay(0);
		Long endTime = TypeConvert.strToZeroDate1000(TypeConvert.dbDateYmd(), 1, -1);
		if(StringUtil.isNotBlank(starttimeStr_start)){
			startTime = TypeConvert.strToZeroDate1000(starttimeStr_start, 0);
			sql.append(" and record.settlement_time >=:beginTime ");
			paramValues.put("beginTime", startTime);
		}
		if(StringUtil.isNotBlank(starttimeStr_end)){
			endTime = TypeConvert.strToZeroDate1000(starttimeStr_end, 1, -1);
			sql.append(" and record.settlement_time <= :endTime ");
			paramValues.put("endTime", endTime);
		}
		
		
		
		String mobileString = connVo.getValueStr("mobile");
		if(!StringUtil.isBlank(mobileString)){
			sql.append(" and user.mobile =:mobile ");
			paramValues.put("mobile", mobileString);
		}
		String species = connVo.getValueStr("species");
		if(!StringUtil.isBlank(species)){
			sql.append(" and trade.species =:species");
			paramValues.put("species", species);
		}
		String sort = connVo.getSortFieldName();
		String sor = connVo.getSortType();
		if(!StringUtil.isBlank(sort) &&!StringUtil.isBlank(sor)){
			sql.append("order by  "+sort+" "+sor);
		}else{
			sql.append(" order by settlementTime DESC");
		}
		

		
		return  getEntityDao().queryDataByParamsSql(sql.toString(),
				FTogetherRecordVo.class, paramValues, connVo);
	
	}

	@Override
	public List<FTogetherRecord> findByTradeIdAndUid(String tradeID, String uid) {
		return this.getEntityDao().findByTradeIdAndUid(tradeID, uid);
	}
	
	
	
	
	
	
	
	
}
