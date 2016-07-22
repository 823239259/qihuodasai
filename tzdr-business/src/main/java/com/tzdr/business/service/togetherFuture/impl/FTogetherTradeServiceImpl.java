package com.tzdr.business.service.togetherFuture.impl;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.exception.FTogetherTradeExcepton;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.togetherFuture.FTogetherConjunctureService;
import com.tzdr.business.service.togetherFuture.FTogetherRecordDetailService;
import com.tzdr.business.service.togetherFuture.FTogetherRecordService;
import com.tzdr.business.service.togetherFuture.FTogetherTradeService;
import com.tzdr.business.service.togetherFuture.TogetherFutureService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.app.vo.FTogetherTradeWebVo;
import com.tzdr.domain.app.vo.FtogetherLineDataVo;
import com.tzdr.domain.app.vo.UserFTogetherTradeVo;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.dao.fTogetherTrade.FTogetherTradeDao;
import com.tzdr.domain.vo.FTogetherRecordDetailVo;
import com.tzdr.domain.vo.TogetherConjunctureVo;
import com.tzdr.domain.web.entity.FTogetherConjuncture;
import com.tzdr.domain.web.entity.FTogetherRecord;
import com.tzdr.domain.web.entity.FTogetherRecordDetail;
import com.tzdr.domain.web.entity.FTogetherTrade;
import com.tzdr.domain.web.entity.TogetherFuture;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;

/**
 * Created by huangkai on 2016/5/20.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FTogetherTradeServiceImpl extends BaseServiceImpl<FTogetherTrade,FTogetherTradeDao> implements FTogetherTradeService {

	private static final Logger logger = LoggerFactory.getLogger(FTogetherTradeServiceImpl.class);

	@Autowired
	FTogetherConjunctureService fTogetherConjunctureService;

	@Autowired
	FTogetherRecordDetailService fTogetherRecordDetailService;
	
	
	@Autowired
	FTogetherRecordService fTogetherRecordService;
	
	@Autowired
	TogetherFutureService togetherFutureService; 
	
	@Autowired
	WUserService wUserService;
	
	@Autowired
	UserFundService userFundService;
	

	@Override
	public List<FTogetherTradeWebVo> queryTogetherTrades(Integer  pageNo) {
		Object  limitStart = Constant.FtogetherGame.ZERO;
		Object  pageNumber = Constant.FtogetherGame.PAGE_NUMBER;
		if (pageNo > Constant.FtogetherGame.ZERO ){
			limitStart = (pageNo-1)*Constant.FtogetherGame.PAGE_NUMBER;
		}
		String sql = "SELECT ftrade.id, ftrade.`name`, ftrade.`status`, ftrade.open_time openTime, "
				+ " ftrade.full_copies fullCopies, ftrade.call_open_point callOpenPoint, "
				+ " ftrade.call_close_point callClosePoint, ftrade.put_open_point putOpenPoint, "
				+ " ftrade.put_close_point putClosePoint, COUNT( CASE WHEN fdetail.direction = 1 THEN fdetail.id END ) riseCopies,"
				+ " COUNT( CASE WHEN fdetail.direction = 2 THEN fdetail.id END ) dropCopies, "
				+ " COUNT( CASE WHEN fdetail.direction = 1 AND fdetail.is_back = 0 THEN fdetail.id END ) realRiseCopies, "
				+ " COUNT( CASE WHEN fdetail.direction = 2 AND fdetail.is_back = 0 THEN fdetail.id END ) realDropCopies "
				+ " FROM f_together_trade ftrade LEFT JOIN f_together_record_detail fdetail ON fdetail.trade_id = ftrade.id "
				+ " GROUP BY ftrade.id  ORDER BY ftrade.open_time DESC limit ?,? ";
		
		List<FTogetherTradeWebVo>   fTogetherTradeVos = nativeQuery(sql,Lists.newArrayList(limitStart,pageNumber), FTogetherTradeWebVo.class);
		return fTogetherTradeVos;
	}

	@Override
	public List<UserFTogetherTradeVo> queryUserTogetherTrades(String uid) {
		String sql = " SELECT record.id, ftrade.`name`, ftrade.`status`, ftrade.open_time openTime, record.direction, record.copies, record.back_copies backCopies, "
				+ " ftrade.contract, record.achieve_profit_loss achieveProfitLoss, "
				+ " ( SELECT count(0) FROM f_together_record_detail fdetail WHERE fdetail.trade_id = ftrade.id "
				+ " AND fdetail.direction = record.direction AND fdetail.is_back = 0 ) sameDireCopies "
				+ " FROM f_together_record record, f_together_trade ftrade WHERE record.trade_id = ftrade.id AND record.uid = ? ORDER BY ftrade.open_time DESC";
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		List<UserFTogetherTradeVo>   userFTogetherTradeVos = nativeQuery(sql,params, UserFTogetherTradeVo.class);
		return userFTogetherTradeVos;

	}
	
	@Override
	public List<Map<String, Object>> queryWinRank() {
		String sql = " SELECT usr.mobile,SUM(record.copies-record.back_copies) tcopies, ROUND(( COUNT( CASE WHEN record.profit_loss_point > 0 THEN fdetail.id END ) / COUNT(fdetail.id)) * 100, 2 ) percent FROM f_together_record record,"
				+ "  f_together_record_detail fdetail, f_together_trade trade, w_user usr WHERE "
				+ " fdetail.record_id = record.id AND trade.id = fdetail.trade_id AND fdetail.is_back = 0 "
				+ " AND trade.`status` = 3 AND usr.id = fdetail.uid AND record.copies > record.back_copies GROUP BY fdetail.uid "
				+ " ORDER BY percent DESC,tcopies desc LIMIT 10 ";
		return this.getEntityDao().queryMapBySql(sql);
	}

	@Override
	public List<Map<String, Object>> queryProfitRank() {
		String sql = " SELECT usr.mobile, SUM(record.copies-record.back_copies) tcopies,ROUND(( SUM( CASE WHEN record.achieve_profit_loss >= 0 THEN record.achieve_profit_loss END ) / SUM( record.pay_money - record.back_money )) * 100, 2 ) percent"
				+ " FROM f_together_record record, f_together_trade trade, w_user usr WHERE trade.id = record.trade_id "
				+ " AND trade.`status` = 3 AND usr.id = record.uid AND record.copies > record.back_copies "
				+ "  GROUP BY record.uid ORDER BY percent DESC,tcopies desc LIMIT 10  ";
		return this.getEntityDao().queryMapBySql(sql);
	}

	@Override
	public FTogetherTradeWebVo findTradeById(String tradeId) {
		String sql = " SELECT ftrade.species,ftrade.id, ftrade.`name`, ftrade.`status`, ftrade.open_time openTime, ftrade.full_copies fullCopies, "
				+ " ftrade.call_open_point callOpenPoint, ftrade.call_close_point callClosePoint, ftrade.put_open_point putOpenPoint,"
				+ " ftrade.put_close_point putClosePoint, COUNT( CASE WHEN fdetail.direction = 1 THEN fdetail.id END ) riseCopies,"
				+ " COUNT( CASE WHEN fdetail.direction = 2 THEN fdetail.id END ) dropCopies,"
				+ " COUNT( CASE WHEN fdetail.direction = 1 AND fdetail.is_back = 0 THEN fdetail.id END ) realRiseCopies,"
				+ " COUNT( CASE WHEN fdetail.direction = 2 AND fdetail.is_back = 0 THEN fdetail.id END ) realDropCopies, "
				+ " ftrade.operate_time operateTime, ftrade.price, ftrade.target_profit_point targetProfitPoint, "
				+ " ftrade.stop_point stopPoint, ftrade.profit_fee profitFee, ftrade.profit_copies_price profitCopiesPrice, "
				+ " ftrade.loss_fee lossFee, ftrade.loss_copies_price lossCopiesPrice, ftrade.float_moint floatMoint, "
				+ " ftrade.contract FROM f_together_trade ftrade LEFT JOIN f_together_record_detail fdetail ON fdetail.trade_id = ftrade.id "
				+ " WHERE ftrade.id = ? ";
		List<Object> params = Lists.newArrayList();
		params.add(tradeId);
		List<FTogetherTradeWebVo>   fTogetherTradeVos = nativeQuery(sql,params, FTogetherTradeWebVo.class);
		if (CollectionUtils.isEmpty(fTogetherTradeVos)){
			return null;
		}
		return fTogetherTradeVos.get(0);
	}

    /**
     * 
     *
     * @param
     * @param searchParams
     * @return
     */
    public PageInfo<Object> queryTogetherTradeList(EasyUiPageInfo easyUiPage,
                                                   Map<String, Object> searchParams){
      
        List<Object> params = Lists.newArrayList();

        String sql = "select f.id as id, "
                + "f.trade_no as tradeNo, "
				+ "f.config_id as configId,  "
                + "f.status as status, "
                + "f.name as name, "
				+ "f.species as species, "
                + "f.contract as contract, "
				+ "f.full_copies as fullCopies, "
                + "CONVERT(f.open_time,CHAR) as openTime, "
                + "f.operate_time as operateTime, "
                + "f.call_open_point as callOpenPoint, "
                + "f.put_open_point as putOpenPoint, "
                + "f.call_close_point as callClosePoint, "
                + "f.put_close_point as putClosePoint, "
                + "CONVERT(f.create_time,CHAR)as addTime, "
				+ "CONVERT((select count(*) from f_together_record_detail d where d.trade_id = f.id and d.direction = 1),CHAR )as callNo, "
				+ "CONVERT((select count(*) from f_together_record_detail d where d.trade_id = f.id and d.direction = 2),CHAR) as putNo "
                + "from f_together_trade as f "
               	+ "where f.deleted = 0 order by f.open_time DESC";

        MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams,params,sql);
        PageInfo<Object> pageInfo = multiListPageQuery(multilistParam, com.tzdr.domain.vo.FTogetherTradeVo.class);


        return pageInfo;
    }

	@Override
	public String add(TogetherFuture future, String name, Long OpenTime, Integer operateTime) {
		if(null == future){
			throw new FTogetherTradeExcepton("no future",null);
		}
		FTogetherTrade fTogetherTrade = new FTogetherTrade();
		fTogetherTrade.setCreateTime(new Date().getTime()/1000);
		fTogetherTrade.setContract(future.getTradingContract());
		fTogetherTrade.setName(name);
		fTogetherTrade.setConfigId(future.getId());
		fTogetherTrade.setSpecies(future.getScope());
		fTogetherTrade.setOpenTime(OpenTime);
		fTogetherTrade.setOperateTime(operateTime);
		fTogetherTrade.setFullCopies(future.getFullNum());
		fTogetherTrade.setPrice(future.getUnitPrice());
		fTogetherTrade.setTargetProfitPoint(future.getStopProfitPoint());
		fTogetherTrade.setStopPoint(future.getStopLossPoint());
		fTogetherTrade.setProfitFee(future.getProfitFee());
		fTogetherTrade.setLossFee(future.getLossFee());
		fTogetherTrade.setLossCopiesPrice(future.getLossPointPrice());
		fTogetherTrade.setProfitCopiesPrice(future.getProfitPointPrice());
		fTogetherTrade.setFloatMoint(future.getBeatPoint());
		fTogetherTrade.setStatus(1);
		fTogetherTrade.setTradeNo("HM"+RandomCodeUtil.genToken(6));
		this.getEntityDao().save(fTogetherTrade);

		for(int i=1;i<fTogetherTrade.getOperateTime();i++){
			FTogetherConjuncture fTogetherConjuncture = new FTogetherConjuncture();
			fTogetherConjuncture.setMinutes(i);
			fTogetherConjuncture.setTradeId(fTogetherTrade.getId());
			fTogetherConjunctureService.save(fTogetherConjuncture);
		}
		String s ="success";
		return s;
	}

	@Override
	public void refundNotFullCopies(FTogetherTrade fTogetherTrade) {
		//查询所有合买记录
		List<FTogetherRecordDetail> callFTogetherRecordDetails =  fTogetherRecordDetailService.queryTogetherRecordDetails(fTogetherTrade.getId(),Constant.FtogetherGame.CALL_DIRECTION);
		List<FTogetherRecordDetail> putFTogetherRecordDetails =  fTogetherRecordDetailService.queryTogetherRecordDetails(fTogetherTrade.getId(),Constant.FtogetherGame.PUT_DIRECTION);
		
		int fullCopies = fTogetherTrade.getFullCopies();
		//看涨退回份数
		int callCopies = callFTogetherRecordDetails.size();
		int callBackCopies  = callCopies%fullCopies;
		//看涨退回份数
		int putCopies  = putFTogetherRecordDetails.size();
		int putBackCopies = putCopies%fullCopies;
		// 如果看涨、看跌方向 均未满单 则直接标记为已结算
		if (callCopies==callBackCopies && putCopies==putBackCopies){
			// 更新方案状态
		    fTogetherTrade.setStatus(Constant.FtogetherGame.END_STATUS);
		    fTogetherTrade.setSettlementTime(Dates.getCurrentLongDate());
		    this.update(fTogetherTrade);
		}	
		if (CollectionUtils.isEmpty(callFTogetherRecordDetails) 
				&& CollectionUtils.isEmpty(putFTogetherRecordDetails)){
			return ;
		}
	    
		// 拼装用户退款信息<uid,backCopies>
		Map<String,Integer> refundMap = new HashMap<String, Integer>();
		// 看涨退回
		if (callBackCopies > 0 ){
			for (int i = (callCopies-callBackCopies);i<callCopies;i++){
				FTogetherRecordDetail  callDetail = callFTogetherRecordDetails.get(i);
				if (Constant.FtogetherGame.BACK == callDetail.getIsBack()){
					continue;
				}
				callDetail.setIsBack(Constant.FtogetherGame.BACK);
				//更新退回
				fTogetherRecordDetailService.update(callDetail);
				String recordId = callDetail.getRecordId();
				if (refundMap.containsKey(recordId)){
					refundMap.put(recordId,refundMap.get(recordId)+1);
				}else
				{
					refundMap.put(recordId,1);
				}
			}
		}
		
		// 看跌退回
		if (putBackCopies > 0 ){
			for (int i = (putCopies-putBackCopies);i<putCopies;i++){
				FTogetherRecordDetail  putDetail = putFTogetherRecordDetails.get(i);
				if (Constant.FtogetherGame.BACK == putDetail.getIsBack()){
					continue;
				}
				putDetail.setIsBack(Constant.FtogetherGame.BACK);
				//更新退回
				fTogetherRecordDetailService.update(putDetail);
				String recordId = putDetail.getRecordId();
				if (refundMap.containsKey(recordId)){
					refundMap.put(recordId,refundMap.get(recordId)+1);
				}else
				{
					refundMap.put(recordId,1);
				}
			}
		}
		// 如果为空则返回
		if (CollectionUtils.isEmpty(refundMap) || refundMap.size()==0){
			return;
		}
		//依次退款到用户账上
		for (String recordId:refundMap.keySet()) {
			FTogetherRecord  fTogetherRecord = fTogetherRecordService.get(recordId);
			Integer backCopies = refundMap.get(recordId);
			//获取用户信息
			String uid = fTogetherRecord.getUid();
			WUser wuser = wUserService.get(uid);
			//生成资金明细
			BigDecimal  fprice = fTogetherTrade.getPrice();
			Double backMoney = BigDecimalUtils.mulRound(fprice.doubleValue(),backCopies);
			// 如果活动记录 同时操盘分数全部退回则 减去 90 元
			if (Constant.FtogetherGame.ACTIVITY_TYPE_ONE  == fTogetherRecord.getActivityType().intValue() 
					&& backCopies.intValue() == fTogetherRecord.getCopies().intValue()){
				backMoney = BigDecimalUtils.subRound(backMoney,Constant.FtogetherGame.ACTIVITY_FREE_MONEY.doubleValue());
			}
			UserFund fund=new UserFund();
			fund.setMoney(backMoney);
			fund.setType(3);//
			fund.setNo(recordId);
			fund.setUid(uid);
			fund.setAmount(wuser.getAvlBal());//余额
			fund.setPayStatus((short)1);
			fund.setRemark("期货合买["+fTogetherTrade.getName()+"]未满单份数的退钱.");
			fund.setAddtime(new Date().getTime()/1000);
			fund.setUptime(new Date().getTime()/1000);
			userFundService.arrearsProcess(fund);
			// 设置退回金额和份数
			fTogetherRecord.setBackCopies(backCopies);
			fTogetherRecord.setBackMoney(new BigDecimal(backMoney));
			// 如果看涨、看跌方向 均未满单 则直接标记为已结算
			if (callCopies==callBackCopies && putCopies==putBackCopies){
				// 更新方案状态
				fTogetherRecord.setSettlementTime(Dates.getCurrentLongDate());
			}	
			fTogetherRecordService.update(fTogetherRecord);
		}
	}

	@Override
	public void updateByTime(FTogetherTrade fTogetherTrade) {
		// TODO Auto-generated method stub
		if(fTogetherTrade != null){

			String sql ="delete from f_together_conjuncture where trade_id = ?";
			nativeUpdate(sql, fTogetherTrade.getId());
			for(int i=1;i<fTogetherTrade.getOperateTime();i++){
				FTogetherConjuncture fTogetherConjuncture = new FTogetherConjuncture();
				fTogetherConjuncture.setMinutes(i);
				fTogetherConjuncture.setTradeId(fTogetherTrade.getId());
				fTogetherConjunctureService.save(fTogetherConjuncture);
			}
			
		}
		super.update(fTogetherTrade);
	}
	
	public Map<String,Object> getTradeValue(FTogetherTrade fTogetherTrade){
		
		Map<String, Object> map = new HashMap<String, Object>();			
			map.put("t_contract", fTogetherTrade.getContract());
			String openTime = Dates.format(Dates.parseLong2Date(fTogetherTrade.getOpenTime()),"MM.dd HH:mm" );
			map.put("t_openTime", openTime);
			String endTime = Dates.format(Dates.parseLong2Date(fTogetherTrade.getOpenTime()+(fTogetherTrade.getOperateTime()*60)),"MM.dd HH:mm");
			map.put("t_endTime", endTime);
			map.put("t_scope",CacheManager.getDataMapByKey(DataDicKeyConstants.FTOGETHER_TRADE_TYPE,String.valueOf(fTogetherTrade.getSpecies())));
			map.put("t_price", fTogetherTrade.getPrice()+"元/份");
			map.put("t_fullNum","每"+fTogetherTrade.getFullCopies()+"满1单");
			map.put("t_stopProfitPoint", fTogetherTrade.getTargetProfitPoint()+"点");
			map.put("t_stopLossPoint", fTogetherTrade.getStopPoint()+"点");
			map.put("t_profitFee", fTogetherTrade.getPrice()+"元-"+fTogetherTrade.getProfitFee()+"+盈利点数X"+fTogetherTrade.getProfitCopiesPrice()+"元/份");
			map.put("t_lossFee",fTogetherTrade.getPrice()+"元-"+fTogetherTrade.getLossFee()+"-亏损点数X"+fTogetherTrade.getLossCopiesPrice()+"元/份");
		return map;
	}




	@Override
	public PageInfo<Object> getProfitValue(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		String sql="select f.trade_id as tradeId,f.direction, f.buy_time as addTime,w.mobile as mobile,f.is_back as isBack  "
				+ "from f_together_record_detail f,f_together_trade ft,w_user w "
				+ "where  f.trade_id = ft.id and f.uid = w.id "
				+ "order by f.buy_time , f.is_back ASC";
		return multiListPageQuery(easyUiPage, searchParams, sql, FTogetherRecordDetailVo.class);
	}
	
	/**
	 * 得到行情数据
	 * @param
	 * @return
	 */
	public TogetherConjunctureVo getMarke(FTogetherTrade fTogetherTrade){
		if(fTogetherTrade !=null){
			
			String sqlString = "select id , minutes,point from f_together_conjuncture f where f.trade_id=? order by minutes asc";
			List<Map<String, Object>> maps = this.getEntityDao().queryMapBySql(sqlString, fTogetherTrade.getId());
			TogetherConjunctureVo vo = new TogetherConjunctureVo();
			vo.setMarkes(maps);
			vo.setCallClosePoint(fTogetherTrade.getCallClosePoint()==null?BigDecimal.ZERO:fTogetherTrade.getCallClosePoint());
			vo.setCallOpenPoint(fTogetherTrade.getCallOpenPoint()==null?BigDecimal.ZERO:fTogetherTrade.getCallOpenPoint());
			vo.setPutClosePoint(fTogetherTrade.getPutClosePoint()==null?BigDecimal.ZERO:fTogetherTrade.getPutClosePoint());
			vo.setPutOpenPoint(fTogetherTrade.getPutOpenPoint()==null?BigDecimal.ZERO:fTogetherTrade.getPutOpenPoint());
			return vo;
		}
		return null;
	}

	@Override
	public void instantSettle(FTogetherTrade fTogetherTrade) {
		// 结算前 先进行退款操作
		refundNotFullCopies(fTogetherTrade);
		
		List<FTogetherRecord>  fTogetherRecords = fTogetherRecordService.findByTradeId(fTogetherTrade.getId());
		 // 结算时间
	    Long settlementTime = Dates.getCurrentLongDate(); 
		// 更新方案状态
	    fTogetherTrade.setStatus(Constant.FtogetherGame.END_STATUS);
	    fTogetherTrade.setSettlementTime(settlementTime);
	    this.update(fTogetherTrade);
		if (CollectionUtils.isEmpty(fTogetherRecords)){
			return;
		}
		
		//获取行情点数 及价格
		BigDecimal profitFee = fTogetherTrade.getProfitFee();
	    BigDecimal profitCopiesPrice =  fTogetherTrade.getProfitCopiesPrice();
	    BigDecimal lossFee = fTogetherTrade.getLossFee();
	    BigDecimal lossCopiesPrice = fTogetherTrade.getLossCopiesPrice();
	    
	    BigDecimal callOpenPoint = fTogetherTrade.getCallOpenPoint();
	    BigDecimal putOpenPoint = fTogetherTrade.getPutOpenPoint();
	    BigDecimal callClosePoint = fTogetherTrade.getCallClosePoint();
	    BigDecimal putClosePoint = fTogetherTrade.getPutClosePoint();
	    // 常量值0
	    BigDecimal  zero = new BigDecimal(Constant.FtogetherGame.ZERO);
	   
	    for (FTogetherRecord fTogetherRecord : fTogetherRecords){
	    	//参与合买份数
	    	Integer  copies = fTogetherRecord.getCopies()-fTogetherRecord.getBackCopies();
	    	// 如果参与分数为0时 择未操盘
	    	if (copies > Constant.FtogetherGame.ZERO ){
		    	// 计算盈利点数
		    	BigDecimal profitPoint = zero;
		    	// 手续费 = 用户盈亏方向的手续费 X 参与操盘的份数
		    	BigDecimal poundage = zero;
		    	// 盈亏点数 X 该期货每个指数点的价值
		    	BigDecimal achieveProfitLoss = zero;
		    	//（合买价格–盈亏手续费 + 交易盈亏点数 X 该期货每指数点的价值）X 参与操盘的份数
		    	BigDecimal expectSettlementMoney = zero;
		    	//看涨合买记录结算
		    	if (Constant.FtogetherGame.CALL_DIRECTION==fTogetherRecord.getDirection()
		    			&& !ObjectUtil.equals(null, callOpenPoint)
		    			&& !ObjectUtil.equals(null, callClosePoint)){
		    		profitPoint = callClosePoint.subtract(callOpenPoint);
		    	}
		    	
		    	//看跌合买记录结算
		    	if (Constant.FtogetherGame.PUT_DIRECTION==fTogetherRecord.getDirection()
		    			&& !ObjectUtil.equals(null, putClosePoint)
		    			&& !ObjectUtil.equals(null, putOpenPoint)){
		    		profitPoint = putOpenPoint.subtract(putClosePoint);
		    	}
		    	// 计算手续费和盈亏
		    	if (profitPoint.compareTo(zero) >= Constant.FtogetherGame.ZERO ){
	    			poundage = profitFee.multiply(new BigDecimal(copies));
	    			achieveProfitLoss = profitPoint.multiply(profitCopiesPrice);
	    			expectSettlementMoney = fTogetherTrade.getPrice().subtract(profitFee).add(profitPoint.multiply(profitCopiesPrice)).multiply(new BigDecimal(copies));
	    			// 如果申请时参与了免除90元活动，则结算时要扣除
	    			if (Constant.FtogetherGame.ACTIVITY_TYPE_ONE  == fTogetherRecord.getActivityType().intValue()){
	    				expectSettlementMoney = expectSettlementMoney.subtract(Constant.FtogetherGame.ACTIVITY_FREE_MONEY).add(profitFee);
	    				poundage = poundage.subtract(profitFee);
	    			}
		    	}
	    		else
	    		{
	    			poundage = lossFee.multiply(new BigDecimal(copies));
	    			achieveProfitLoss = profitPoint.multiply(lossCopiesPrice);
	    			expectSettlementMoney = fTogetherTrade.getPrice().subtract(lossFee).add(profitPoint.multiply(lossCopiesPrice)).multiply(new BigDecimal(copies));
	    			// 如果申请时参与了免除90元活动，则结算时要扣除
	    			if (Constant.FtogetherGame.ACTIVITY_TYPE_ONE  == fTogetherRecord.getActivityType().intValue()){
	    				expectSettlementMoney = expectSettlementMoney.subtract(Constant.FtogetherGame.ACTIVITY_FREE_MONEY).add(lossFee);
	    				poundage = poundage.subtract(lossFee);
	    			}
	    		}
		    	/**
		    	 *  处理实际结算金额
		    	 *  结算金额为正数，数值和预计结算金额相同；若结算金额为负数，显示0。
		    	 *  意味着用户把支付本金亏完了，多亏的由公司承担，这里能统计处我们承担了多少亏损。
		    	 */
		    	BigDecimal actualSettlementMoney = zero;
		    	if (expectSettlementMoney.compareTo(zero) >= Constant.FtogetherGame.ZERO ){
		    		actualSettlementMoney = expectSettlementMoney;
		    	}
		    	// 更新对应的结算信息
		    	fTogetherRecord.setPoundage(BigDecimalUtils.roundTwo(poundage));
		    	fTogetherRecord.setProfitLossPoint(BigDecimalUtils.roundTwo(profitPoint));
		    	fTogetherRecord.setAchieveProfitLoss(BigDecimalUtils.roundTwo(achieveProfitLoss.multiply(new BigDecimal(copies))));
		    	fTogetherRecord.setExpectSettlementMoney(BigDecimalUtils.roundTwo(expectSettlementMoney));
		    	fTogetherRecord.setActualSettlementMoney(BigDecimalUtils.roundTwo(actualSettlementMoney));
		    	// 当结算金额大于0时，生成结算资金明细记录
		    	if (actualSettlementMoney.doubleValue() > Constant.FtogetherGame.ZERO){
		    		//获取用户信息
					String uid = fTogetherRecord.getUid();
					WUser wuser = wUserService.get(uid);
			    	UserFund fund=new UserFund();
					fund.setMoney(BigDecimalUtils.roundTwo(actualSettlementMoney).doubleValue());
					fund.setType(3);//系统调帐
					fund.setNo(fTogetherRecord.getId());
					fund.setUid(fTogetherRecord.getUid());
					fund.setAmount(wuser.getAvlBal());//余额
					fund.setPayStatus((short)1);
					fund.setRemark("期货合买["+fTogetherTrade.getName()+"]结算金额.");
					fund.setAddtime(new Date().getTime()/1000);
					fund.setUptime(new Date().getTime()/1000);
					userFundService.arrearsProcess(fund);
		    	}
	    	}
	    	fTogetherRecord.setSettlementTime(settlementTime);
	    	this.fTogetherRecordService.update(fTogetherRecord);
	    }
	}

	@Override
	public FtogetherLineDataVo queryLineData(String tradeID) {
		FTogetherTrade fTogetherTrade = this.get(tradeID);
		
		FtogetherLineDataVo  lineDataVo = new FtogetherLineDataVo();
		lineDataVo.setMaxPoint(fTogetherTrade.getTargetProfitPoint());
		lineDataVo.setMinPoint(fTogetherTrade.getTargetProfitPoint().multiply(new BigDecimal(-1)));
		
		List<Object> xAxisDatas = new ArrayList<Object>();
		//生成X轴数据
		for (int i=0;i<fTogetherTrade.getOperateTime();i++){
			xAxisDatas.add(i);
		}
		lineDataVo.setxAxisData(xAxisDatas);
		// 设置行情点位数
		List<Object> riseDatas = new ArrayList<Object>();
		List<Object> dropDatas = new ArrayList<Object>();
		BigDecimal openPoint = new BigDecimal(0);
		lineDataVo.setCallOpenPoint(fTogetherTrade.getCallOpenPoint());
		lineDataVo.setPutOpenPoint(fTogetherTrade.getPutOpenPoint());
		if (!ObjectUtil.equals(fTogetherTrade.getCallOpenPoint(),null)
				&& !ObjectUtil.equals(fTogetherTrade.getPutOpenPoint(),null)){
			 openPoint = fTogetherTrade.getCallOpenPoint().subtract(fTogetherTrade.getPutOpenPoint());
		}
		riseDatas.add(openPoint);
		dropDatas.add(new BigDecimal(0));
		
		
		//止损、止盈点
		BigDecimal  targetProfitPoint = fTogetherTrade.getTargetProfitPoint();
		BigDecimal  stopPoint = fTogetherTrade.getStopPoint();
		lineDataVo.setStopPoint(stopPoint);
		List<FTogetherConjuncture>  conjunctures = fTogetherConjunctureService.findByTradeID(tradeID);
		if (!CollectionUtils.isEmpty(conjunctures)){
			if (!ObjectUtil.equals(null,fTogetherTrade.getCallOpenPoint())){
				//lineDataVo.setRiseFloatPoint((BigDecimal) riseDatas.get(0));
				for (FTogetherConjuncture  conjuncture : conjunctures){
					BigDecimal point = conjuncture.getPoint();
					if (ObjectUtil.equals(null, point)){
						riseDatas.add(Constant.FtogetherGame.NULL_LINE_DATA);
						continue;
					}
					BigDecimal floatPoint = point.subtract(fTogetherTrade.getCallOpenPoint());
					riseDatas.add(floatPoint);
					lineDataVo.setRiseFloatPoint(floatPoint);
					if (floatPoint.compareTo(new BigDecimal(Constant.FtogetherGame.ZERO)) < Constant.FtogetherGame.ZERO
							&& floatPoint.abs().compareTo(stopPoint)>=Constant.FtogetherGame.ZERO){
						break;
					}
					if (floatPoint.compareTo(new BigDecimal(Constant.FtogetherGame.ZERO)) > Constant.FtogetherGame.ZERO 
							&& floatPoint.compareTo(targetProfitPoint)>=Constant.FtogetherGame.ZERO){
						break;
					}
				}
			}
			
			Integer riseDatasSize =   riseDatas.size() ;
			for (int i=riseDatasSize;i<fTogetherTrade.getOperateTime();i++){
				riseDatas.add(Constant.FtogetherGame.NULL_LINE_DATA);
			}
			
			if (!ObjectUtil.equals(null,fTogetherTrade.getPutOpenPoint())){
				//lineDataVo.setDropFloatPoint((BigDecimal) dropDatas.get(0));
				for (FTogetherConjuncture  conjuncture : conjunctures){
					BigDecimal point = conjuncture.getPoint();
					if (ObjectUtil.equals(null, point)){
						dropDatas.add(Constant.FtogetherGame.NULL_LINE_DATA);
						continue;
					}
					BigDecimal floatPoint = point.subtract(fTogetherTrade.getPutOpenPoint());
					dropDatas.add(floatPoint);
					lineDataVo.setDropFloatPoint(new BigDecimal(Constant.FtogetherGame.ZERO).subtract(floatPoint));
					if (floatPoint.compareTo(new BigDecimal(Constant.FtogetherGame.ZERO)) > Constant.FtogetherGame.ZERO
							&& floatPoint.compareTo(stopPoint)>=Constant.FtogetherGame.ZERO){
						break;
					}
					if (floatPoint.compareTo(new BigDecimal(Constant.FtogetherGame.ZERO)) < Constant.FtogetherGame.ZERO 
							&& floatPoint.abs().compareTo(targetProfitPoint)>=Constant.FtogetherGame.ZERO){
						break;
					}
				}
			}
			Integer dropDatasSize =   dropDatas.size() ;
			for (int i=dropDatasSize;i<fTogetherTrade.getOperateTime();i++){
				dropDatas.add(Constant.FtogetherGame.NULL_LINE_DATA);
			}
		}
		lineDataVo.setDropData(dropDatas);
		lineDataVo.setRiseData(riseDatas);
		return lineDataVo;
		
	}

	@Override
	public Map<String, Object> queryUserProfitRank(String uid) {
		String sql = " SELECT temp2.* FROM ( SELECT @counter \\:= @counter+1 AS rank, temp.percent, temp.id uid FROM "
				+ " ( SELECT usr.id, SUM( record.copies - record.back_copies ) tcopies, ROUND(( SUM(CASE WHEN record.achieve_profit_loss >= 0 THEN record.achieve_profit_loss END) "
				+ " /SUM( record.pay_money - record.back_money )) * 100, 2 ) percent FROM f_together_record record, "
				+ " f_together_trade trade, w_user usr WHERE trade.id = record.trade_id AND trade.`status` = 3 AND "
				+ " usr.id = record.uid AND record.copies > record.back_copies "
				+ "  GROUP BY record.uid ORDER BY percent DESC, tcopies DESC ) temp, "
				+ " (SELECT(@counter \\:= 0)) temp1 ) temp2 WHERE temp2.uid = ? ";
		List<Map<String,Object>>  result  =  this.getEntityDao().queryMapBySql(sql,uid);
		Map<String, Object>  map = new HashMap<String, Object>();
		if (CollectionUtils.isEmpty(result)){
			map.put("rank", 0);
			map.put("percent", 0);
			map.put("uid",uid);
			return map;
		}
		return result.get(0);
		
	}

	@Override
	public boolean checkIsNewUser(Object uid) {
		String sql  = " SELECT ( wtrade.wtrades + fgtrade.fgtrades + ftrade.ftrades ) totalTrades "
					+ " FROM ( SELECT count(0) wtrades FROM w_user_trade WHERE uid = ? ) wtrade,  "
					+ " ( SELECT count(0) fgtrades FROM f_together_record WHERE uid = ? ) fgtrade, "
					+ " ( SELECT count(0) ftrades FROM f_simple_ftse_user_trade WHERE uid =? ) ftrade ";
		BigInteger totalTrades = (BigInteger) this.nativeQueryOne(sql,Lists.newArrayList(uid,uid,uid));
		if (totalTrades.intValue() > Constant.FtogetherGame.ZERO ){
			return false;
		}
		return true;
	}
	
	

	/**
	 * 检测是否在活动期间免除90元费用
	 * @param payMoney
	 * @return
	 */
	@Override
	public  boolean  checkActivityTime() {
		String  activityStartTime = CacheManager.getDataMapByKey(DataDicKeyConstants.FTOGETHER_ACTIVITY_TIME,DataDicKeyConstants.START_TIME);
		String  activityEndTime = CacheManager.getDataMapByKey(DataDicKeyConstants.FTOGETHER_ACTIVITY_TIME,DataDicKeyConstants.END_TIME);

		if (StringUtil.isBlank(activityStartTime) || StringUtil.isBlank(activityEndTime))
		{
			return false;
		}
		Date startTime = Dates.parse(activityStartTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
		Date endTime = Dates.parse(activityEndTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
		Date nowTime = new Date();
		if (startTime.before(nowTime) && endTime.after(nowTime)){
			return true;
		}
		return false;
	}
	
}
