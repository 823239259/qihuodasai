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
			String tradeDate = jsonObject.getString("tradeDate");
			if(tradeDate == null || tradeDate.equals("null") || tradeDate.length() == 0){
				continue;
			}
			detail.setBuyNum(jsonObject.getString("buyNum"));
			detail.setCommodityNo(jsonObject.getString("commodityNo"));
			detail.setCurrencyNo(jsonObject.getString("currencyNo"));
			detail.setExchangeNo(jsonObject.getString("exchangeNo"));
			detail.setFree(jsonObject.getString("free"));
			detail.setOrderType(jsonObject.getString("orderType"));
			detail.setOrderUsername(jsonObject.getString("orderUsername"));
			detail.setOrderUserno(jsonObject.getString("orderUserno"));
			detail.setSellNum(jsonObject.getString("sellNum"));
			detail.setTradeDate(jsonObject.getString("tradeDate"));
			detail.setTradePrice(jsonObject.getString("tradePrice"));
			detail.setTradeType(jsonObject.getString("tradeType"));
			detail.setUpdateTime(time);
			detail.setCreateTime(time);
			detail.setUsername(jsonObject.getString("username"));
			detail.setUserNo(jsonObject.getString("userNo"));
			detail.setFastId(fastId);
			details.add(detail);
		}
		getEntityDao().saves(details);
	}
	@Override
	public List<TradeDetail> doGetByFtseId(String fsteId) {
		return getEntityDao().doGetByFtseId(fsteId);
	}
	@Override
	public void deleteByFastId(String fastId) {
		getEntityDao().deleteByFastId(fastId);
	}
}
