package com.tzdr.business.cms.cpp.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tzdr.business.cms.cpp.CppTest;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.cpp.DataSource;
import com.tzdr.domain.dao.cpp.CppDao;
import com.tzdr.domain.vo.CppVo;
import com.tzdr.domain.web.entity.Cpp;
@Service("cppService")
@Transactional
@DataSource("dataSource2")
public class CppTestService extends BaseServiceImpl<Cpp, CppDao> implements CppTest {

	@Override
	public PageInfo<Object> find(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		StringBuffer buffer = new StringBuffer();
		List<Object> params = Lists.newArrayList();
		buffer.append("SELECT ID as id,CurrencyNo currencyNo,CurrencyName currencyName,ExchangeRate exchangeRate FROM a_currency_list");
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, params, buffer.toString());
		pageInfo = multiListPageQuery(multilistParam, CppVo.class);
		return pageInfo;
	}
	
}