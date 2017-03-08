package com.tzdr.business.service.tradeDetail.imp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.OutDisk.OutDiskParametersService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.tradeDetail.TradeDetailService;
import com.tzdr.business.service.userTrade.FPoundageParitiesService;
import com.tzdr.business.service.userTrade.FSimpleConfigService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.tradeDetail.TradeDetailDao;
import com.tzdr.domain.web.entity.FPoundageParities;
import com.tzdr.domain.web.entity.FSimpleConfig;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.OutDiskParameters;
import com.tzdr.domain.web.entity.TradeDetail;

@Service("tradeDetailService")
@Transactional
public class TradeDetailServiceImp extends BaseServiceImpl<TradeDetail, TradeDetailDao> implements TradeDetailService{
	@Autowired
	DataMapService dataMapService;
	@Autowired
	FSimpleFtseUserTradeService simpleFtseUserTradeService;
	@Autowired
	private FPoundageParitiesService poundageParitiesService;
	@Autowired
	OutDiskParametersService outDiskParametersService;
	@Autowired
	FSimpleConfigService simpleConfigService;
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
	public void doSaveTrades(List<TradeDetail> details) {
		
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
	@Override
	public List<TradeDetail> getByTranAccounts(String tranAccount) {
		return getEntityDao().findByTranAccount(tranAccount);
	}
	@Override
	public void deleteByTradeNo(String tradeNo) {
		getEntityDao().deleteByTradeNo(tradeNo);
		
	}
	@Override
	public double countTranProfitLoss(List<TradeDetail> tradeDetails,BigDecimal todayMoeny,String id,String tranAccount) {
		char ch = tranAccount.charAt(0);
		
		BigDecimal HKDfreeMoeny = new BigDecimal(0.0);
		BigDecimal JPYfreeMoeny = new BigDecimal(0.0);
		BigDecimal EURfreeMoeny = new BigDecimal(0.0);
		BigDecimal USDfreeMoeny = new BigDecimal(0.0);
		BigDecimal CNYfreeMoeny = new BigDecimal(0.0);
		
		for (TradeDetail tradeDetail : tradeDetails) {
			String currencyNo = tradeDetail.getCurrencyNo();
			BigDecimal free = new BigDecimal(tradeDetail.getFree());
			if("HKD-HKFE".equals(currencyNo)){
				HKDfreeMoeny = HKDfreeMoeny.add(free);
			}else if("JPY".equals(currencyNo)){
				JPYfreeMoeny = JPYfreeMoeny.add(free);
			}else if("EUR".equals(currencyNo)){
				EURfreeMoeny = EURfreeMoeny.add(free);
			}else if("CNY".equals(currencyNo)){
				CNYfreeMoeny = CNYfreeMoeny.add(free);
			}else
				USDfreeMoeny = USDfreeMoeny.add(free);
		}
		//获取结算汇率
	    List<FPoundageParities> parities = poundageParitiesService.getAll();
		//计算总的手续费    
		BigDecimal freeSum = USDfreeMoeny;
		//根据结算手续费汇率  将各币种手续费转化为美元    
		if(ch == 'Q'){//易胜手续费计算
			for (FPoundageParities fPoundageParities : parities) {
				if("HKD-HKFE".equals(fPoundageParities.getCurrencyNo())){
					freeSum = freeSum.add(HKDfreeMoeny.multiply(fPoundageParities.getParitiesYs()));
				}else if("JPY".equals(fPoundageParities.getCurrencyNo())){
					freeSum = freeSum.add(JPYfreeMoeny.multiply(fPoundageParities.getParitiesYs()));
				}else if("EUR".equals(fPoundageParities.getCurrencyNo())){
					freeSum = freeSum.add(EURfreeMoeny.multiply(fPoundageParities.getParitiesYs()));
				}else if("CNY".equals(fPoundageParities.getCurrencyNo())){
					freeSum = freeSum.add(CNYfreeMoeny.multiply(fPoundageParities.getParitiesYs()));
				}
			}
		}else{
			for (FPoundageParities fPoundageParities : parities) {
				if("HKD-HKFE".equals(fPoundageParities.getCurrencyNo())){
					freeSum = freeSum.add(HKDfreeMoeny.multiply(fPoundageParities.getParities()));
				}else if("JPY".equals(fPoundageParities.getCurrencyNo())){
					freeSum = freeSum.add(JPYfreeMoeny.multiply(fPoundageParities.getParities()));
				}else if("EUR".equals(fPoundageParities.getCurrencyNo())){
					freeSum = freeSum.add(EURfreeMoeny.multiply(fPoundageParities.getParities()));
				}else if("CNY".equals(fPoundageParities.getCurrencyNo())){
					freeSum = freeSum.add(CNYfreeMoeny.multiply(fPoundageParities.getParities()));
				}
			}
		}
		FSimpleFtseUserTrade ftse = simpleFtseUserTradeService.get(id);
		
		//追加保证金汇率,,,注意:入金汇率、追加保证金、手续费汇率、结算汇率均不一样
		String exchangeRate = dataMapService.findByTypeKey("exchangeRate").get(0).getValueKey();
		BigDecimal appendTraderBond = ftse.getAppendTraderBond().divide(new BigDecimal(exchangeRate),2, BigDecimal.ROUND_DOWN);//追加保证金（$）
		
		
		double tranProfitLoss = 0;
		if(ch == 'Q'){//易胜盈亏结算  
			
			Integer businessType = ftse.getBusinessType();
			BigDecimal traderBond = ftse.getTraderBond();//初始保证金人民币
			BigDecimal $traderBond = new BigDecimal(0); //入金
			if(businessType == 8){//国际综合
				OutDiskParameters outDiskParameters = outDiskParametersService.findByTraderBond(traderBond).get(0);
				$traderBond = outDiskParameters.getGoldenMoney();
			}else{
				if(businessType == 0){
					businessType = 5;
				}
				List<FSimpleConfig> findFSimpleConfigsByType = simpleConfigService.findFSimpleConfigsByType(businessType);
				for (FSimpleConfig fSimpleConfig : findFSimpleConfigsByType) {
					if(fSimpleConfig.getTraderBond().doubleValue() == traderBond.doubleValue()){
						$traderBond = fSimpleConfig.getGoldenMoney();
						break;
					}
				}
			}
			
			//总盈亏  交易盈亏=账户余额  - （初始保证金 + 追加保证金） + 手续费  （保留2位小数）
			tranProfitLoss = todayMoeny.subtract($traderBond.add(appendTraderBond)).add(freeSum).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		}else{//直达盈亏结算
			
			//总盈亏  交易盈亏=账户余额  - 总操盘资金（初始入金+追加保证金）  + 手续费  （保留2位小数）
	        tranProfitLoss = todayMoeny.subtract(ftse.getTraderTotal().add(appendTraderBond)).add(freeSum).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return tranProfitLoss;
	}
	
}
