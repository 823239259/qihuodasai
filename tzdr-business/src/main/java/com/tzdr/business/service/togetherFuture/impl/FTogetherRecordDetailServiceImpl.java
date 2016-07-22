package com.tzdr.business.service.togetherFuture.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.togetherFuture.FTogetherRecordDetailService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.fTogetherTrade.FTogetherRacordDeailDao;
import com.tzdr.domain.web.entity.FTogetherRecordDetail;

/**
 * Created by huangkai on 2016/5/20.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FTogetherRecordDetailServiceImpl extends BaseServiceImpl<FTogetherRecordDetail,FTogetherRacordDeailDao> implements FTogetherRecordDetailService {

	@Override
	public Map<String, Object> queryCopies(String tradeId,
			Integer direction) {
	    String  sql = " SELECT COUNT(detail.id) copies, COUNT( CASE WHEN detail.is_back = 1 THEN detail.id END ) backCopies "
	    			+ "	FROM f_together_record_detail detail WHERE detail.trade_id = ? AND detail.direction = ?  ";
		return this.getEntityDao().queryMapObj(sql, tradeId,direction);
	}

	@Override
	public List<Map<String, Object>> queryRecords(String tradeId, Integer direction) {
		  String  sql = " SELECT usr.mobile,fdetail.is_back, FROM_UNIXTIME( fdetail.buy_time/1000, '%m.%d %H:%i' ) buyTime, temp.percent FROM w_user usr, "
		  		+ " f_together_record_detail fdetail LEFT JOIN ( SELECT record.uid, "
		  		+ " ROUND(( SUM( CASE WHEN record.achieve_profit_loss > 0 THEN record.achieve_profit_loss END ) / SUM( record.pay_money - record.back_money )) * 100, 2 ) percent "
		  		+ " FROM f_together_record record, f_together_trade trade WHERE trade.id = record.trade_id AND trade.`status` = 3 "
		  		+ " AND record.copies > record.back_copies "
		  		+ " GROUP BY record.uid ) temp ON temp.uid = fdetail.uid WHERE usr.id = fdetail.uid AND fdetail.trade_id = ? "
		  		+ " AND fdetail.direction = ? ORDER BY fdetail.buy_time,fdetail.is_back ASC  ";
		return this.getEntityDao().queryMapBySql(sql, tradeId,direction);
	}

	@Override
	public List<FTogetherRecordDetail> queryTogetherRecordDetails(String tradeId,Integer direction) {
		
		return this.getEntityDao().findByTradeIdAndDirectionOrderByBuyTimeAsc(tradeId, direction);
		
	}
}
