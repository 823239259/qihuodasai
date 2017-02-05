package com.tzdr.business.cms.cpp.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tzdr.business.cms.cpp.CurrencyService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.cpp.DataSource;
import com.tzdr.domain.dao.cpp.CurrencyDao;
import com.tzdr.domain.web.entity.Currency;

@Service("currencyService")
@Transactional
@DataSource(value="dataSource2")
public class CurrencyServiceImpl  extends BaseServiceImpl<Currency, CurrencyDao>  implements CurrencyService{

	@Override
	public PageInfo<Object> find(EasyUiPageInfo easyUiPageInfo, Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPageInfo.getRows(), easyUiPageInfo.getPage());
		StringBuffer buffer = new StringBuffer();
		List<Object> params = Lists.newArrayList(); 
		buffer.append("SELECT CurrencyNo,CurrencyName FROM a_currency_list ");
		MultiListParam multilistParam = new MultiListParam(easyUiPageInfo, searchParams, params, buffer.toString());
		pageInfo = multiListPageQuery(multilistParam, Currency.class);
		
		
		return pageInfo;
	}

}
