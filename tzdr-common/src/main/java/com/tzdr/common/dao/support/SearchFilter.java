package com.tzdr.common.dao.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.tzdr.common.utils.Dates;



public class SearchFilter {

	public static final String SEARCH_DATE="date_";
	public static final String SEARCH_DATETIME="datetime_";
	public enum Operator {
		NQ,EQ, LIKE, GT, LT, GTE, LTE,IN,NOT
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public static List<SearchFilter> parse(Map<String, Object> searchParams) {
		if (CollectionUtils.isEmpty(searchParams)){
			return null;
		}
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			Object value = entry.getValue();
			if (value == null || StringUtil.isBlank(String.valueOf(value))) {
				continue;
			}
			String[] names = StringUtil.split(entry.getKey(), "_");
			if (names.length < 2) {
				throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
			}
			
			String filedName = null;
			Operator operator = null;
			
			// 如果是日期类型的大小比较 格式如下：datetime_eq_filedName;
			if (entry.getKey().startsWith(SEARCH_DATETIME))
			{
				filedName = names[2]; 
				operator = Operator.valueOf(names[1]);
				
			    String date = StringUtils.trim(value.toString());
			    value = Dates.parse(date,Dates.CHINESE_DATETIME_FORMAT_LINE).getTime()/1000;
			}
			// 如果是日期类型的大小比较 格式如下：date_eq_filedName;
			else if (entry.getKey().startsWith(SEARCH_DATE))
			{
				filedName = names[2]; 
				operator = Operator.valueOf(names[1]);
				
			    String date = StringUtils.trim(value.toString());
			    if (StringUtils.equals(operator.name(),"GTE"))
			    {
			    	date = date+" 00:00:00";
			    }
			    if (StringUtils.equals(operator.name(),"LTE"))
			    {
			    	date = date+" 23:59:59";
			    }
			    value = Dates.parse(date,Dates.CHINESE_DATETIME_FORMAT_LINE).getTime()/1000;
			}
			else
			{
				 filedName = names[1];
				 operator = Operator.valueOf(names[0]);
			}
			
			SearchFilter filter = new SearchFilter(filedName,operator, value);
			filters.add(filter);
		}
		return filters;
	}
}
