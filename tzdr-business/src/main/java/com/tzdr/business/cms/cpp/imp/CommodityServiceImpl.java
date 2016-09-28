package com.tzdr.business.cms.cpp.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tzdr.business.cms.cpp.CommodityService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.cpp.DataSource;
import com.tzdr.cpp.DynamicDataSourceHolder;
import com.tzdr.domain.dao.cpp.CommodityDao;
import com.tzdr.domain.vo.CommodityVo;
import com.tzdr.domain.web.entity.Commodity;
@Service("commodityService")
@Transactional
@DataSource(value="dataSource2")
public class CommodityServiceImpl extends BaseServiceImpl<Commodity, CommodityDao>  implements CommodityService{
@Autowired
private CommodityDao commodityDao;
	
	@Override
	public PageInfo<Object> find(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		StringBuffer buffer = new StringBuffer();
		List<Object> params = Lists.newArrayList(); 
		buffer.append("SELECT `index`, CommodityNo commodityNo,CommodityName commodityName,ExchangeNo exchangeNo,"
				+ "ExchangeName exchangeName,TimeBucket timeBucket,ContractSize contractSize,"
				+ "MiniTikeSize miniTikeSize,DotSize dotSize,CurrencyNo currencyNo,MainContract mainContract,DepositRatio depositRatio,"
				+ "TradeFee tradeFee FROM a_commodity_list order by `index`");
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, params, buffer.toString());
		pageInfo = multiListPageQuery(multilistParam, CommodityVo.class);
		List<Object> list=pageInfo.getPageResults();
		for (Object object : list) {
			if(object instanceof CommodityVo){
				CommodityVo temp=(CommodityVo)object;
				String s=temp.getTimeBucket();
				JSONArray jsonArray = JSONArray.parseArray(s);
				int size = jsonArray.size();
				String time="";
				for(int i = 0 ; i < size ; i++){
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					time += jsonObj.getString("TimeBucketBeginTime") + ",";
				}
				temp.setTimeBucket(time);
					
			       
				}
		}
		return pageInfo;
	}

	@Override
	public JsonResult add(Commodity commodity){
		String sqlString="insert into a_commodity_list value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		nativeUpdate(sqlString,commodity.getIndex(),
				commodity.getCommodityNo(),
				commodity.getCommodityName(),
				commodity.getExchangeNo(),
				commodity.getExchangeName(),
				commodity.getTimeBucket(),
				commodity.getContractSize(),
				commodity.getMiniTikeSize(),
				commodity.getDotSize(),
				commodity.getCurrencyNo(),
				commodity.getMainContract(),
				commodity.getDepositRatio(),
				commodity.getTradeFee());
		
		return new JsonResult(true);
	}
	
	@Override
	public JsonResult updateCommodity(Commodity commodity){
		
		String sqlString="update  a_commodity_list set CommodityNo=?,CommodityName=?,"
				+ "ExchangeNo=?,ExchangeName=?,TimeBucket=?,ContractSize=?,"
				+ "MiniTikeSize=?,DotSize=?,CurrencyNo=?,MainContract=?"
				+ "  where `index`=?";
		nativeUpdate(sqlString,
				commodity.getCommodityNo(),
				commodity.getCommodityName(),
				commodity.getExchangeNo(),
				commodity.getExchangeName(),
				commodity.getTimeBucket(),
				commodity.getContractSize(),
				commodity.getMiniTikeSize(),
				commodity.getDotSize(),
				commodity.getCurrencyNo(),
				commodity.getMainContract(),
				commodity.getIndex());
		
		return new JsonResult(true);
	}
}
