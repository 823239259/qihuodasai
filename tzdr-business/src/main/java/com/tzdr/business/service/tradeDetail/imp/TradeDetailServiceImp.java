package com.tzdr.business.service.tradeDetail.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.tradeDetail.TradeDetailService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.tradeDetail.TradeDetailDao;
import com.tzdr.domain.web.entity.TradeDetail;

@Service("tradeDetailService")
@Transactional
public class TradeDetailServiceImp extends BaseServiceImpl<TradeDetail, TradeDetailDao> implements TradeDetailService{
	@Override
	public void doSaveTradeExclDetail(String tradeDetail,String fastId) {
		JSONArray jsonArray = JSONArray.parseArray(tradeDetail);
		Integer size = jsonArray.size();
		List<TradeDetail> details = new ArrayList<>();
		Long time = new Date().getTime();
		getEntityDao().deleteByFastId(fastId);
		for(int i = 1 ; i < size ; i++){
			TradeDetail detail = new TradeDetail();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			detail.setCommodityNo(jsonObject.getString("commodityNo"));
			detail.setContractNo(jsonObject.getString("contractNo"));
			detail.setDrection(jsonObject.getString("drection"));
			detail.setFlat(jsonObject.getString("flat"));
			detail.setFree(jsonObject.getString("free"));
			detail.setMarketDate(jsonObject.getString("marketDate")+jsonObject.getString("marketTime"));
			detail.setOrderPrice(jsonObject.getString("orderPrice"));
			detail.setOrderType(jsonObject.getString("orderType"));
			detail.setOrderUser(jsonObject.getString("orderUser"));
			detail.setTradeNum(jsonObject.getString("tradeNum"));
			detail.setTradePrice(jsonObject.getString("tradePrice"));
			detail.setFastId(fastId);
			detail.setCreateTime(time);
			detail.setUpdateTime(time);
			details.add(detail);
		}
		getEntityDao().saves(details);
	}
	@Override
	public List<TradeDetail> doGetByFtseId(String fsteId) {
		return getEntityDao().doGetByFtseId(fsteId);
	}
}
