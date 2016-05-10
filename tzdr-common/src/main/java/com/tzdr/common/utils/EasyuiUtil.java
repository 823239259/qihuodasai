package com.tzdr.common.utils;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.common.dao.support.SearchFilter;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月4日 下午5:41:46
 * 类说明
 */
public class EasyuiUtil {

	public static final String SEARCH_PREFIX = "search_";
	
	public static final String ASC = "asc";
	
	public static final String DESC = "desc";
	
	/**
	 * 收索查询 去除 参数前缀，返回 搜索参数
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * prefix 前缀默认带search_
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (!StringUtil.equals(SEARCH_PREFIX,prefix)) {
			return params;
		}
		
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			
			String paramName = (String) paramNames.nextElement();
			
			//不以前缀search_开头
			if (!paramName.startsWith(prefix)) {
				continue;
			}
			
			String unprefixed = paramName.substring(prefix.length());
			String[] values = request.getParameterValues(paramName);
			
			if (ArrayUtils.isEmpty(values) || values.length > 1)
			{
				continue;
			}
			
			params.put(unprefixed, values[0]);
			request.setAttribute(paramName,values[0]);
		}
		return params;
	}
	
	/**
	 * 获取排序字段
	 * @param easyUiPage
	 * @return
	 */
	public static Map<String,Boolean>  getSortMap(EasyUiPageInfo easyUiPage)
	{
		String  sort = easyUiPage.getSort();
		Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
		if (StringUtil.isBlank(sort))
		{
			sortMap.put("id", true);
			return sortMap;
		}
		
		String order = easyUiPage.getOrder();
		if (StringUtil.isBlank(order))
		{
			sortMap.put(sort, true);
			return sortMap;
		}
		
		sortMap.put(sort,StringUtil.equals(order,ASC)?true:false);
		return sortMap;
	}
	
	/**
	 * 获取排序字段
	 * @param easyUiPage
	 * @return
	 */
	public static Map<String,String>  getMultilistSortMap(EasyUiPageInfo easyUiPage)
	{
		String  sort = easyUiPage.getSort();
		Map<String,String> sortMap = Maps.newHashMap();
		if (StringUtil.isBlank(sort))
		{
			return sortMap;
		}
		
		String order = easyUiPage.getOrder();
		if (StringUtil.isBlank(order))
		{
			sortMap.put(sort,ASC);
			return sortMap;
		}
		
		sortMap.put(sort,order);
		return sortMap;
	}
	
	
	public static MultiListParam  bySearchFilter(MultiListParam multilistParam){
		List<SearchFilter> searchFilters = SearchFilter.parse(multilistParam.getSearchMap());
		StringBuilder  builder  = new StringBuilder();
		if (CollectionUtils.isEmpty(searchFilters)){
			return multilistParam;
		}
		List<Object>  params = multilistParam.getParams();
		if (CollectionUtils.isEmpty(params)){
			params = Lists.newArrayList();
			multilistParam.setParams(params);
		}
		for (SearchFilter searchFilter : searchFilters){
			// logic operator
			switch (searchFilter.operator) {
				case NQ :
					builder.append(" and "+searchFilter.fieldName + " != ? " );
					params.add(searchFilter.value);
					break; 
				case EQ :
					builder.append(" and "+searchFilter.fieldName + " = ? " );
					params.add(searchFilter.value);
					break; 
				case LIKE :
					builder.append(" and "+searchFilter.fieldName + " like ? ");
					params.add("%"+searchFilter.value+"%");
					break;
				case GT :
					builder.append(" and "+searchFilter.fieldName + " > ? ");
					params.add(searchFilter.value);
					break;
				case LT :
					builder.append(" and "+searchFilter.fieldName + " < ? ");
					params.add(searchFilter.value);
					break;
				case GTE :
					builder.append(" and "+searchFilter.fieldName + " >= ? ");
					params.add(searchFilter.value);
					break;
				case LTE :
					builder.append(" and "+searchFilter.fieldName + " <= ? ");
					params.add(searchFilter.value);
					break;
				case IN :
					 if(searchFilter.value instanceof Collection<?>){  
						 Collection<?> collection =  (Collection<?>)searchFilter.value;   
						 builder.append(" and "+searchFilter.fieldName + " in "+getQueryInSql(collection.size())+" ");
						 for (Object object : collection) {
							 params.add(object);
						 }
		             }else if(searchFilter.value instanceof Object[]){ 
		            	 Object[] objs = (Object[])searchFilter.value;
		            	 builder.append(" and "+searchFilter.fieldName + " in "+getQueryInSql(objs.length)+" ");
		            	 for (Object object : objs) {
		            		 params.add(object);
						}
		             }else{
		            	builder.append(" and "+searchFilter.fieldName + " IN ( "+searchFilter.value+" ) ");
//						params.add(searchFilter.value);
		             } 
					break;
			}
		}
		
		if (builder.length()==0){
			return multilistParam;
		}
		
		String sql = multilistParam.getSql();
		String addSql = StringUtil.trimLeft(builder.toString());
		
		if (addSql.startsWith("and")){

			addSql = addSql.substring(3);
		}
		sql = "select * from ("+sql+") tb where  " + addSql;
		multilistParam.setSql(sql);
		
		return multilistParam;
	}
	
	// 创建排序的  带有easyui  表格排序的  sql 
	public static void createOrderSql(MultiListParam multilistParam){
		String  sort = multilistParam.getSort();
		String  order =  multilistParam.getOrder();
		
		if (StringUtil.isBlank(sort) || StringUtil.isBlank(order))
		{
			return;
		}
		String sql = multilistParam.getSql();
		sql = "select *  from ( "+ sql +" ) tempTab order by  tempTab."+sort+" "+order;
		multilistParam.setSql(sql);
	}
	
	 /**
	    * 如果sql中含有in 函数，根据paramSize =3生成（?,?,?）
	    * 
	    * @param sql
	    * @param paramSize
	    * @return
	    */
	   public static String getQueryInSql(int paramSize){
		  StringBuffer builder = new StringBuffer("(");
		  for (int i=0;i<paramSize;i++){
			  builder.append("?");
			  if ((paramSize-1)==i){
				  continue;
			  }
			  builder.append(",");
		  }
		  builder.append(")");
		  return builder.toString();
	   }
}
