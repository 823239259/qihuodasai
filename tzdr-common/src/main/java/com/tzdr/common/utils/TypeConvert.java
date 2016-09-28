package com.tzdr.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.umpay.api.exception.ParameterCheckException;

/**
 * 
 * <p>类型转换工具类</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2014年12月30日上午11:33:07
 */
public class TypeConvert {
	
	
	/**
	 * 券的初始值
	 */
	public static final int VOLUME_INI = 100001;
	
	/**
	 * 最大代码数
	 */
	public static final int VOLUME_MAX = 9999;
	
	/**
	 * 导出excel 大小
	 */
	public static final int EXCEL_PAGE_SIZE = 1000000;
	
	public static final String ACTIVITY_001 = "";
	
	/**
	 * freemark SUFFIX
	 */
	public static final String FREEMARK_SUFFIX =".ftl";
	
	
	/**
	 * 显示密码文内容
	 * @param content content
	 * @return  content
	 */
	public static synchronized String showPasswordText(String content) {
		if (content == null || cleanSpaceCharacter(content) == null) {
			return "";
		}
		char ch[] = content.toCharArray();
		return "*" + String.valueOf(ch[ch.length - 1]);
	}
	
	/**
	 * 显示
	 * @param content String
	 * @param showset int
	 * @param showoff int
	 * @param rep char
	 * @return String
	 */
	public static synchronized String showPasswordText(String content,int showset,int showoff,char rep) {
		if (content == null || cleanSpaceCharacter(content) == null){
			return content;
		}
		char ches[] = content.toCharArray();
		if (showset > ches.length || showoff > ches.length){
			showoff = ches.length;
		}
		for (int i = showset; i < showoff; i++) {
			ches[i] = rep;
		}
		return new String(ches);
	}
	
	
	
	/**
	 * 对象_to字符串
	 * @param obj Object
	 * @return String
	 */
	public static synchronized String objToStrIsNotNull(Object obj) {
		if (obj == null) {
			return null;
		}
		String value = String.valueOf(obj);
		if (!"".equals(value) ) {
			return value;
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param sql String
	 * @param prefix String
	 * @param vo ConnditionVo
	 * @return String
	 */
	public static synchronized String autoOrderBy(String sql,String prefix,ConnditionVo vo) {
		StringBuffer sqlBuf = new StringBuffer(sql);
		String prefixStr = "";
		if (prefix != null && !"".equals(prefix)) {
			prefixStr = prefix + ".";
		}
		if (vo.getSortFieldName() != null) {
			sqlBuf.append(" ORDER BY ");
			if (vo.getSortType() != null && vo.getSortType().equalsIgnoreCase("asc")) {
				sqlBuf.append(" " + prefixStr + vo.getSortFieldName() + " " + vo.getSortType());
			}
			else {
				sqlBuf.append(" " + prefixStr + vo.getSortFieldName() + " DESC");
			}
		}
		return sqlBuf.toString();
	}
	
	/**
	 * 
	 * @param sql String
	 * @param prefix String
	 * @param vo ConnditionVo
	 * @return String
	 */
	public static synchronized String autoOrderBy(String sql,Class clazz,ConnditionVo vo) {
		StringBuffer sqlBuf = new StringBuffer(sql);
		try {
			if (vo.getSortFieldName() != null && !"".equals(vo.getSortFieldName())) {
				Field field = clazz.getDeclaredField(vo.getSortFieldName());
				if (field != null) {
					SqlOrder sqlOrder = field.getAnnotation(SqlOrder.class);
					if (sqlOrder != null) {
						sqlBuf.append(" ORDER BY ");
						if (SqlOrder.NONE.equals(sqlOrder.value())) {
							sqlBuf.append(" " + field.getName());
						}
						else {
							sqlBuf.append(" " + sqlOrder.value());
						}
						
						if (vo.getSortType() != null && "asc".equalsIgnoreCase(vo.getSortType())) {
							sqlBuf.append(" ASC");
						}
						else {
							sqlBuf.append(" DESC");
						}
					}
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqlBuf.toString();
	}
	
	
	
	
	
	/**
	 * 获取文件内容关传入参数
	 * @param fileName String 
	 * @param params String...paramsArrays
	 * @return String
	 */
	public static synchronized String getActivityFileContent(String fileName,String...params) {
		return getClassPathFileContentFormart("/sms/" + fileName,FREEMARK_SUFFIX,params);
	}
	
	/**
	 * 根据ClassPath获取对应文件路径
	 * @param clazz Class<?> 
	 * @param fileName String 文件路径 
	 * @param suffix String
	 * @param params String[]
	 * @return String
	 */
	public static synchronized String getClassPathFileContentFormart(Class<?> classPath,
			String fileName,String suffix,String...params) {
		String path = "/" + TypeConvert.class.getPackage().getName().replace(".", "/") + "/";
		return getClassPathFileContentFormart(path + fileName, suffix, params);
	}
	
	/**
	 * 
	 * @param fileName String 文件路径 
	 * @param suffix String
	 * @param params String[]
	 * @return String
	 */
	public static synchronized String getClassPathFileContentFormart(String fileName,
			String suffix,String...params) {
		String content = getClassPathFileContent(fileName,suffix);
		return String.format(content, params);
	}
	
	/**
	 * 获取临界时间
	 * @param dateStr  String
	 * @param day int meaning 0:currentDay、1:tomorrow、-1:yesterday
	 * @return Date
	 */
	public static synchronized Date strToZeroDate(String dateStr,int day,int second) {
		return strToZeroTimeByPattern("yyyy-MM-dd",dateStr,day,second);
	}
	
	/**
	 * 获取临界时间
	 * @param dateStr  String
	 * @param day int meaning 0:currentDay、1:tomorrow、-1:yesterday
	 * @return Date
	 */
	public static synchronized long strToZeroDate1000(String dateStr,int day,int second) {
		Date date = strToZeroTimeByPattern("yyyy-MM-dd",dateStr,day,second);
		if (date != null) {
			return date.getTime() / 1000;
		}
		return 0;
		
	}
	
	
	/**
	 * 获取临界时间
	 * @param dateStr  String
	 * @param day int meaning 0:currentDay、1:tomorrow、-1:yesterday
	 * @return Date
	 */
	public static synchronized Date strToZeroDate(String dateStr,int day) {
		return strToZeroTimeByPattern("yyyy-MM-dd",dateStr,day,0);
	}
	
	/**
	 * 获取临界时间
	 * @param dateStr  String
	 * @param day int meaning 0:currentDay、1:tomorrow、-1:yesterday
	 * @return Date
	 */
	public static synchronized long strToZeroDate1000(String dateStr,int day) {
		Date date = strToZeroDate(dateStr,day);
		if (date != null) {
			return date.getTime() / 1000;
		}
		return 0;
	}
	
	/**
	 * 
	 * @param pattern String 表达式【yyyy-MM-dd HH:mm:ss】
	 * @param timeStr String 时间 2015-10-12 12:12:10
	 * @param day int meaning 0:currentDay、1:tomorrow、-1:yesterday
	 * @return long
	 */
	public static synchronized long strToZeroTime1000ByPattern(String pattern,String timeStr,int day,int second)  {
		Date date = strToZeroTimeByPattern(pattern,timeStr,day,second);
		if (date != null) {
			return date.getTime() / 1000;
		}
		return 0;
	}
	
	/**
	 * 
	 * @param pattern String 表达式【yyyy-MM-dd HH:mm:ss】
	 * @param timeStr String 时间 2015-10-12 12:12:10
	 * @param day int meaning 0:currentDay、1:tomorrow、-1:yesterday
	 * @return Date
	 */
	public static synchronized Date strToZeroTimeByPattern(String pattern,String timeStr,int day,int second) {
		SimpleDateFormat simp = new SimpleDateFormat(pattern);
		Date createDate = null;
		try {
			createDate = simp.parse(timeStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(createDate);
			calendar.add(Calendar.DAY_OF_MONTH, day);
			calendar.add(Calendar.SECOND, second);
			createDate = calendar.getTime();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return createDate;
		
	}
	
	/**
	 * 获取Sms文件内容
	 * @param fileName String 
	 * @return String
	 */
	public static synchronized String getClassPathFileContent(String fileName,String suffix) {
		InputStream input = 
				TypeConvert.class.getResourceAsStream(fileName + suffix);
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		StringBuffer buf = new StringBuffer();
		try {
			inputReader = new InputStreamReader(input,"utf-8");
			bufferReader = new BufferedReader(inputReader);
			while (true) {
				String str = bufferReader.readLine();
				if (str == null) { 
					break;
				}
				buf.append(str);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (bufferReader != null) {
				try {
					bufferReader.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputReader != null) {
				try {
					inputReader.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buf.toString();
	}
	
	/**
	 * 发送短信时间
	 */
	public final static long SEND_SMS_LONG = 1000L;
	
	//@param typeValue beforeBegin:活动开始前提醒 beforeEnd: 结束前提醒 end:活动结束提醒
	public static enum SmsType {
		BEFORE_BEGIN,
		BEFORE_END,
		END;
	}
	
	/**
	 * beforeBegin:活动开始前提醒
	 */
	public final static String ACTIVITY_BEFORE_BEGIN = "beforeBegin";
	
	/**
	 *  beforeEnd: 结束前提醒
	 */
	public final static String ACTIVITY_BEFORE_END = "beforeEnd";
	
	/**
	 * end:活动结束提醒
	 */
	public final static String ACTIVITY_END = "end";
	
	/**
	 * 重置密码
	 */
	public final static String ACTIVITY_REPASSWORD = "repassword";
	
	/**
	 * 取精度
	 */
	public final static int SCALE_VALUE = 2;
	
	/**
	 * 代理类型
	 */
	public final static String AGENT_TYPE = "0";
	
	
	/**
	 * 中间拆分
	 * 
	 * @param str String
	 * @return Map<String,Boolean>
	 */
	public static Map<String,Boolean> strToOrderByType(String str) {
		return strToOrderByType(str,",",":");
	}
	
	/**
	 * 中间拆分
	 * @param str String
	 * @param split  String
	 * @param centerSplit String
	 * @return Map<String,Boolean>
	 */
	public static Map<String,Boolean> strToOrderByType(String str,String split,
			String centerSplit) {
		Map<String,Boolean> orders = new HashMap<String,Boolean>();
		String orderBys[] = str.split(split);
		if (orderBys != null && orderBys.length > 0) {
			for (String orderBy:orderBys) {
				String keyValues[] = orderBy.split(centerSplit);
				String key = keyValues[0];
				String value = keyValues[1];
				if ("asc".equals(value.toLowerCase())) {
					orders.put(key, true);
				}
				else {
					orders.put(key, false);
				}
			}
		}
		return orders;
	}
	
	/**
	 * if true is digital value
	 * @param str String
	 * @return boolean
	 */
	public static synchronized boolean isDigital(String str) {
		return str.matches("[0-9]*");
	}
	
	/**
	 * if true is ChongQingMobile
	 * @param mobile String
	 * @return boolean
	 * @throws Exception
	 */
	public static synchronized boolean isChongqingPhoneNumber(String mobile) throws Exception {
		if (mobile == null || mobile.length() == 0) {
			return false;
		}
		Map<String,String> keyValues = getAreaInfoByMobile(mobile);
		String province = keyValues.get("province");//重庆
		String areaVid = keyValues.get("areaVid");//29404
		if (province != null && province.indexOf("重庆") != -1 ) {
			return true;
		}
		if (areaVid != null && areaVid.indexOf("29404") != -1 ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 清除空字符
	 * @param str String
	 * @return String
	 */
	public static synchronized String cleanSpaceCharacter(String str) {
		if (str == null) {
			return null;
		}
		String noSpace = str.replaceAll("\\s*", "");
		if (!"".equals(noSpace)) {
			return noSpace;
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param mobile String
	 * @return Map<String,String>
	 * @throws Exception
	 */
	public static synchronized Map<String,String> getAreaInfoByMobile(String mobile) throws Exception {
		String html = TypeConvert.httpClientComplex(
				"http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + mobile,"gbk",null);
		Map<String,String> props = new TreeMap<String,String>();
		if (html != null) {
			html = html.replaceAll("\\s*", "");
			StringBuffer buf = new StringBuffer();
			int position = html.indexOf("{");
			int endIndex = html.indexOf("}",position);
			buf.append(html.substring(position, endIndex) + ",");
			for (String en:buf.toString().substring(1).split(",")) {
				String keyValue = en;
				String keyNames[] = keyValue.split(":");
				if (keyNames.length == 2) {
					props.put(keyNames[0],keyNames[1]);
				}
			}
		}
		return props;
	}
	
	/**
	 * 2015-01-29 00:00:00
	 * 获取临界时间
	 * @param dayValue 加减天数
	 * @return Long 返回值 为秒值
	 */
	public synchronized static Long longCriticalTimeDay(int dayValue){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH) + dayValue;
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = simp.parse(year + "-" + month + "-" + day);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime() / 1000;
	}
	
	/**
	 * 1009928 时间转为yyyy-MM-dd HH:mm:ss
	 * @param longTime long
	 * @return String
	 */
	public synchronized static String longStrToDatetimeStr(String longTime) {
		try {
			long timeLong = Long.parseLong(longTime);
			if (longTime != null) {
				return dateToDatetimeStr(new Date(timeLong));
			}
		} 
		catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 1009928 时间转为yyyy-MM-dd HH:mm:ss
	 * @param longTime long
	 * @return String
	 */
	public synchronized static String longToDatetimeStr(Long longTime) {
		try {
			if (longTime != null) {
				return dateToDatetimeStr(new Date(longTime));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 1009928 时间转为yyyy-MM-dd HH:mm:ss /1000
	 * @param longTime long 0：is today 临界时间 、1：tomorrow 、-1:yesterday
	 * @return String
	 */
	public synchronized static String long1000ToDatetimeStr(Long longTime) {
		try {
			if (longTime != null) {
				return dateToDatetimeStr(new Date(longTime * 1000));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 1009928 时间转为yyyy-MM-dd
	 * @param longTime long
	 * @return String
	 */
	public synchronized static String long1000ToDateStr(Long longTime) {
		try {
			if (longTime != null) {
				return dateToDateStr(new Date(longTime * 1000));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 1009928 时间转为yyyy-MM-dd HH:mm:ss
	 * @param longTime long
	 * @return String
	 */
	public synchronized static String longToDatetimeStrNotNull(Long longTime) {
		String str = longToDatetimeStr(longTime);
		return str == null ?"":str;
	}
	
	/**
	 * 1009928 时间转为yyyy-MM-dd HH:mm:ss
	 * @param longTime long
	 * @return String
	 */
	public synchronized static String longToDatetimeStrNotNull(Integer longTime) {
		if (longTime == null){
			return "";
		}
		int intTime = longTime;
		String str = longToDatetimeStr((long)intTime);
		return str == null ?"":str;
	}
	
	/**
	 * 时间对象转为字符串
	 * @param date Date
	 * @return String
	 */
	public synchronized static String dateToDatetimeStr(Date date) {
		if (date != null) {
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simple.format(date);
		}
		return null;
	}
	
	/**
	 * 时间对象转为字符串
	 * @param date Date
	 * @return String
	 */
	public synchronized static String dateToDateStr(Date date) {
		if (date != null) {
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			return simple.format(date);
		}
		return null;
	}
	
	/**
	 * 时间对象转为字符串
	 * @param date Date
	 * @return String
	 */
	public synchronized static String dateToDatetimeStrNotNull(Date date) {
		String str = dateToDatetimeStr(date);
		return str == null ?"":str;
	}
	
	/**
	 * 时间对象转为字符串
	 * @param date Date
	 * @return String
	 */
	public synchronized static Date strToDatetime(String str) {
		if (str != null && !"".equals(str)) {
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return simple.parse(str);
			} 
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param str String
	 * @return  Long
	 */
	public synchronized static Long strToDatetime1000Long(String str) {
		Date date = strToDatetime(str);
		if (date != null) {
			return date.getTime() / 1000;
		}
		else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param obj Object
	 * @return  Long
	 */
	public synchronized static Long strToDatetime1000Long(Object obj) {
		if (obj != null) {
			String str = String.valueOf(obj);
			Date date = strToDatetime(str);
			if (date != null) {
				return date.getTime() / 1000;
			}
		}
		return null;
	}
	
	
	
	
	/**
	 * 取项目精度
	 * @param big BigDecimal
	 * @param scale int
	 * @return BigDecimal
	 */
	public static synchronized BigDecimal scale(BigDecimal big,int scale) {
		if (big == null) {
			return null;
		}
		return big.divide(new BigDecimal(1), scale, RoundingMode.HALF_UP);
	}
	
	/**
	 * 加法运算取默认精度
	 * @param summand Double
	 * @param added Double
	 * @return Double
	 */
	public static synchronized Double additionCalculationSacle(Double summand,Double added) {
		BigDecimal value = additionCalculation(summand,added);
		return scale(value,SCALE_VALUE).doubleValue();
	}
	
	/**
	 * 加法运算
	 * @param summand Double
	 * @param added Double
	 * @return BigDecimal
	 */
	public static synchronized BigDecimal additionCalculation(Double summand,Double added) {
		if (summand == null) {
			summand = 0D;
		}
		if (added == null) {
			added = 0D;
		}
		
		BigDecimal summandBig = new BigDecimal(summand);
		BigDecimal addedBig = new BigDecimal(added);
		return summandBig.add(addedBig, MathContext.DECIMAL128);
	}
	
	/**
	 * 设置聚合参数
	 * @param cb CriteriaBuilder
	 * @param expression Path
	 * @param type String
	 */
	public static synchronized Expression settingExpression(CriteriaBuilder cb,Path expression,String type) {
		Expression exp = null;
		if ("sum".equalsIgnoreCase(type)) {
			exp = cb.sum(expression);
		}
		else if ("min".equalsIgnoreCase(type)) {
			exp = cb.min(expression);
		}
		else if ("max".equalsIgnoreCase(type)) {
			exp = cb.max(expression);
		}
		else if ("avg".equalsIgnoreCase(type)) {
			exp = cb.avg(expression);
		}
        else if ("count".equalsIgnoreCase(type)) {
			exp = cb.count(expression);
		}
		return exp;
	}
	
	
	/**
	 * 创建基本类型克隆数据对象
	 * @param obj Object
	 * @return Object createBaseType
	 */
	public static synchronized Object createBaseTypeClone(Object obj){
		if (obj == null) {
		    return null;
		}
		Object newObj = null;
	   	try {
	   		newObj = obj.getClass().newInstance();
	   		Field fields[] = obj.getClass().getDeclaredFields();
	   		for (Field en:fields) {
	   			en.setAccessible(true);
	   			Class fieldType = en.getType();
	   			boolean isBaseType = false;
	   			if (fieldType.isAssignableFrom(Byte.class) 
	   					|| "byte".equals(fieldType.getName())) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(Char.class) 
	   					|| "char".equals(fieldType.getName()) ) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(Short.class) 
	   					|| "short".equals(fieldType.getName())) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(Integer.class) 
	   					|| "int".equals(fieldType.getName())) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(Long.class) 
	   					|| "long".equals(fieldType.getName())) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(Float.class) 
	   					|| "float".equals(fieldType.getName())) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(Double.class) 
	   					|| "double".equals(fieldType.getName())) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(String.class)) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(BigDecimal.class) ) {
	   				isBaseType = true;
	   			}
	   			else if (fieldType.isAssignableFrom(BigInteger.class) ) {
	   				isBaseType = true;
	   			}
	   			if (isBaseType) {
	   				try {
	   				en.set(newObj, en.get(obj));
	   				}
	   				catch(Exception e){}
	   			}
	   			
	   		}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	   	return newObj;
	}
	
	/**
	 * 
	 * @param list List<Object[]>
	 * @param clazz Class
	 * @return List
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static synchronized List objectsToListData(List list,Class clazz)  {
		if (list == null || list.size() == 0) {
			return null;
		}
		List data = new ArrayList();
		try {
			Field fields[] = clazz.getDeclaredFields();
			if (list != null && list.size() > 0) {
				//为数组类型
				if (list.get(0).getClass().isArray()) {
					for (Object objArray:list) {
						Object[] objs = (Object[])objArray;
						Object obj = clazz.newInstance();
						int i = 0;
						for (Field field:fields) {
							if (i < objs.length) {
								SqlColumn sqlColumn = field.getAnnotation(SqlColumn.class);
								if (sqlColumn != null) {
									field.setAccessible(true);
									field.set(obj, objs[i++]);
									field.setAccessible(false);
								}
							}
						}
						data.add(obj);
					}
					
				}
				else {
					for (Object objArray:list) {
						Object obj = clazz.newInstance();
						for (Field field:fields) {
							SqlColumn sqlColumn = field.getAnnotation(SqlColumn.class);
							if (sqlColumn != null) {
								field.setAccessible(true);
								field.set(obj, objArray);
								field.setAccessible(false);
							}
						}
						data.add(obj);
					}
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}
	
	
	/**
	 * 
	 * @param list List<Object[]>
	 * @param clazz Class
	 * @return List
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static synchronized List objectsToListDataByMethod(List list,Class clazz)  {
		if (list == null || list.size() == 0) {
			return null;
		}
		List data = new ArrayList();
		try {
			Field fields[] = clazz.getDeclaredFields();
			if (list != null && list.size() > 0) {
				//为数组类型
				if (list.get(0).getClass().isArray()) {
					for (Object objArray:list) {
						Object[] objs = (Object[])objArray;
						Object obj = clazz.newInstance();
						int i = 0;
						for (Field field:fields) {
							if (i < objs.length) {
								SqlColumn sqlColumn = field.getAnnotation(SqlColumn.class);
								if (sqlColumn != null) {
									field.setAccessible(true);
									field.set(obj, objs[i++]);
									field.setAccessible(false);
								}
							}
						}
						data.add(obj);
					}
					
				}
                else if (list.get(0) instanceof Map) {
                		data = new TypeColumnConvert(clazz,list).getData();
				}
				else {
					for (Object objArray:list) {
						Object obj = clazz.newInstance();
						for (Field field:fields) {
							SqlColumn sqlColumn = field.getAnnotation(SqlColumn.class);
							if (sqlColumn != null) {
								field.setAccessible(true);
								field.set(obj, objArray);
								field.setAccessible(false);
							}
						}
						data.add(obj);
					}
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}
	
	
	
	/**
	 * SELECT * FROM user WHERE name = 'xx' 转为 SELECT COUNT(*) FROM user WHERE name= 'xx'
	 * @param jpql String
	 * @return String
	 */
	public static synchronized String jpqlToCountJpql(String jpql) {
		String jpqlUpper = jpql.toUpperCase();
		int position = jpqlUpper.indexOf("FROM");
		return "SELECT COUNT(*) " + jpql.substring(position);
	}
	
	/**
	 * 生成SQL方法
	 * 请使用sqlNativeCountSql
	 * @param sql String
	 * @return String
	 */
	public static synchronized String sqlToCountSql(String sql) {
		//return "SELECT COUNT(*) FROM (" + sql + ") count_table_name_00001";
		return ParseSqlUtils.createCountSql(sql);
	}
	
	/**
	 * 生成SQL方法
	 * @param sql String
	 * @return String
	 */
	public static synchronized String sqlNativeCountSql(String sql) {
		String upperSql = sql.toUpperCase();
		String froms[] = upperSql.split("FROM");
		String endSql = froms[froms.length - 1];
		int position = upperSql.indexOf(endSql);
		return "SELECT COUNT(*) FROM " + sql.substring(position);
	}
	
	/**
	 * 当前时间
	 * current long value /1000
	 * @return long
	 */
	public static synchronized long dbDefaultDate() {
		return new Date().getTime() / 1000;
	}
	
	/**
	 * 当前时间
	 * 时间秒不重复
	 * current long value /1000
	 * @return long
	 */
	public static synchronized long dbDefaultDateSecond() {
		try {
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new Date().getTime() / 1000;
	}
	
	/**
	 * 获取时间yyyy-MM-dd
	 * @return String
	 */
	public static synchronized String dbDateYmd() {
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
		return simp.format(new Date());
	}
	
	/**
	 * 加法运算
	 * @param a 被加数
	 * @param b 加数
	 * @return BigDecimal
	 */
	public static synchronized BigDecimal add(Double a,Double b) {
		a = a == null ?0D:a;
		b = b == null ?0D:b;
		return new BigDecimal(a).add(new BigDecimal(b));
	}
	
	/**
	 * 支付
	 * @param title String
	 * @param money Double
	 * @return String
	 */
	public static synchronized String payRemark(String title,Double money) {
		return title + money + "元";
	}
	/**
	 * 支付
	 * @param title String
	 * @param money Double
	 * @return String
	 */
	public static synchronized String payRemark(String title,int money) {
		return title + money + "元";
	}
	
	/**
	 * 加法运算
	 * @param a 被加数
	 * @param b 加数
	 * @return BigDecimal
	 */
	public static synchronized BigDecimal add(BigDecimal a,BigDecimal b) {
		a = a == null ?new BigDecimal(0):a;
		b = b == null ?new BigDecimal(0):b;
		return a.add(b);
	}
	
	/**
	 * 
	 * @param money double
	 * @param scale int
	 * @return BigDecimal
	 */
	public static synchronized BigDecimal doubleToBigDecimalScale(double money,int scale) {
		Double dbMoney = money;
		if (dbMoney == null) {
			return new BigDecimal(0);
		}
		return new BigDecimal(dbMoney.toString()).divide(new BigDecimal(1), scale, RoundingMode.FLOOR);
	}
	
	/**
	 * 
	 * @param money double
	 * @param scale int
	 * @return BigDecimal
	 */
	public static synchronized BigDecimal doubleToBigDecimalByScale(Double money,int scale) {
		Double dbMoney = money;
		if (dbMoney == null) {
			return new BigDecimal(0);
		}
		return new BigDecimal(dbMoney.toString()).divide(new BigDecimal(1), scale, RoundingMode.FLOOR);
	}
	
	/**
	 * 类型：1:充值,2:提现,,3:人工充值,4:人工扣款,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,
	 * 12：扣取管理费（新增），13佣金收入，14配资支出，15配资撤回,16利润提取17 追加保证金、18配资欠费
	 */
	
	/**
	 * 1充值
	 */
	public final static int USER_FUND_C_TYPE_RECHARGE = 1;
	
	/**
	 * 2:提现 Withdrawals Artificial recharge
	 */
	public final static int USER_FUND_C_TYPE_WITHDRAWALS = 2;
	
	/**
	 * 3:系统调账 Artificial deductions
	 */
	public final static int USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE = 3;
	
	/**
	 * 4:系统冲账
	 */
	public final static int USER_FUND_C_TYPE_ARTIFICIAL_DEDUCTIONS = 4;
	
	
	/**
	 * 15配资撤回
	 */
	public final static int USER_FUND_C_TYPE_REVOCATION = 15;
	
	/**
	 * 13佣金收入 Commission
	 */
	public final static int USER_FUND_COMMISSION_IN = 13;
	
	/**
	 * 18配资欠费
	 */
	public final static int USER_FUND_C_TYPE_ARREARS = 18; 
	
	/**
	 * 19活动赠送
	 */
	public final static int USER_FUND_C_TYPE_EVENT_AVL = 19;
	
	/**
	 * 20活动收回
	 */
	public final static int USER_FUND_C_TYPE_EVENT_AVL_BACK = 20;
	
	//0:未支付 1:已支付Unpaid
	/**
	 * 未支付
	 */
	public final static short UN_PAID = 0;
	
	/**
	 * 已支付
	 */
	public final static short PAID = 1;
	
	/**
	 * Http访问
	 * @param httpUrl String
	 * @return String
	 * @throws IOException
	 */
	public static synchronized String httpClient(String httpUrl) throws IOException {
		return httpClientComplex(httpUrl,"utf-8",null);
	}
	
	/**
	 * 
	 *  Map<String,String> parameters = new HashMap<String,String>();
	 *	parameters.put("templateName", "hello12");
	 *	httpClient("http://localhost:8080/tzdr-web/statusPage/statusUpdate","templateName","hello12")
	 * 
	 * Http访问
	 * @param httpUrl String
	 * @param params Object...
	 * @return String
	 * @throws IOException
	 */
	public static synchronized String httpClient(String httpUrl,Object...params) throws IOException {
		
		if (params != null && params.length > 0) {
			if (params.length % 2 != 0){
				throw new ParameterCheckException("The argument is not an even number");
			}
			else {
				Map keyValues = new HashMap();
				for (int i = 0; i < params.length; i++) {
					keyValues.put(params[i], params[++i]);
				}
				return httpClientComplex(httpUrl,"utf-8",keyValues);
			}
		}
		else {
			return httpClientComplex(httpUrl,"utf-8",null);
		}
	}
	
	
	/**
	 * Http访问
	 * @param httpUrl String
	 * @param parameters Map<String,String>
	 * @return String
	 * @throws IOException
	 */
	public static synchronized String httpClientComplex(String httpUrl,String charset,Map<String,Object> parameters) throws IOException {
		URL url = new URL(httpUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(30000);
		conn.setConnectTimeout(30000);
		conn.setUseCaches(false);
		StringBuffer result = new StringBuffer();
		InputStreamReader reader = null;
		BufferedReader bufReader = null;
		try {
			StringBuffer params = new StringBuffer();
			if (parameters != null && parameters.size() > 0) {
				conn.setDoOutput(true);
				for (Map.Entry<String, Object> parameter:parameters.entrySet()) {
					String key = parameter.getKey();
					Object value = parameter.getValue();
					params.append("&" + key + "=" + value);
				}
				params.substring(1);
				OutputStream output = conn.getOutputStream();
				output.write(params.toString().getBytes());
			}
			InputStream input = conn.getInputStream();
			reader = new InputStreamReader(input,charset);
			bufReader = new BufferedReader(reader);
			while (true) {
				String line = bufReader.readLine();
				if (line == null) {
					break;
				}
				result.append(line);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			if (bufReader != null) {
				bufReader.close();
			}
			if (reader != null) {
				reader.close();
			}
		}
		return result.toString();
	}
	
	/**
	 * @param opVo OperationLogVo 操作前金额:%s、金额:%s、操作后金额:%s
	 * 支付宝充值审核{title}日志-操作员：[张三{title}]、时间：{title}2015-03-14 12:23:56、
	 * 操作前金额{title}、金额{}、操作后金额{}
	 * 
	 */
	public static synchronized String printPaymentOperationLog(OperationLogVo opVo) {
		String opLong = "%s日志-操作员:[%s]、账户:%s 、时间:%s、操作前金额:%s、金额:%s、操作后金额:%s";
		List<String> params = new ArrayList<String>();
		params.add(opVo.getTitle());
		params.add(opVo.getOperationPeople());
		params.add(opVo.getAccount());
		params.add(TypeConvert.dateToDatetimeStr(new Date()));
		
		for (String p:opVo.getData()) {
			params.add(p);
		}
		opLong = String.format(opLong,params.toArray(new String[params.size()]));
		
		return opLong;
	}
	
	/**
	 * 获取当月开始结束时间值
	 * if current date[2015-5-23] return [2015-05-01 00:00:00,2015-05-31 00:00:00] long value
	 * [1430409600、1433087999]; 
	 * @return Long[]
	 */
	public static synchronized long[] dbDefaultMonthStartEndTime() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		long startDateLong = TypeConvert.strToZeroDate1000(year + "-" + month + "-01", 0);
		long endDateLong = TypeConvert.strToZeroDate1000(year + "-" + (month + 1) + "-01", 0,-1);
		long datas[] = new long[] {startDateLong,endDateLong}; 
		return datas;
	}
	
	/**
	 * 获取当月开始结束时间值根据月份
	 * if current date[2015-5-23] return [2015-05-01 00:00:00,2015-05-31 00:00:00] long value
	 * [1430409600、1433087999]; 
	 * @param month 月份为【1-12】
	 * @return
	 */
	public static synchronized long[] dbDefaultMonthStartEndTime(int month) {
		if (month == 0) {
			throw new IllegalArgumentException("month is not equal 0 ");
		}
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		long startDateLong = TypeConvert.strToZeroDate1000(year + "-" + month + "-01", 0);
		long endDateLong = TypeConvert.strToZeroDate1000(year + "-" + (month + 1) + "-01", 0,-1);
		long datas[] = new long[] {startDateLong,endDateLong}; 
		return datas;
	}
	
	/**
	 * 
	 *  获取当月开始结束时间值根据月份
	 * if current date[2015-5-23] return [2015-05-01 00:00:00,2015-05-31 00:00:00] long value
	 * @param year int
	 * @param month int 月份为【1-12】
	 * @return long[]
	 */
	public static synchronized long[] dbDefaultMonthStartEndTime(int year,int month) {
		if (month == 0) {
			throw new IllegalArgumentException("month is not equal 0 ");
		}
		long startDateLong = TypeConvert.strToZeroDate1000(year + "-" + month + "-01", 0);
		long endDateLong = TypeConvert.strToZeroDate1000(year + "-" + (month + 1) + "-01", 0,-1);
		long datas[] = new long[] {startDateLong,endDateLong}; 
		return datas;
	}
	
	/**
	  * 判断字符串是否是整数
	  */
	 public static synchronized boolean isInteger(String value) {
		  try {
			  Integer.parseInt(value);
			  return true;
		  } catch (NumberFormatException e) {
			  return false;
		  }
	 }

	 /**
	  * 判断字符串是否是浮点数
	  */
	 public static synchronized boolean isDouble(String value) {
		  try {
			   Double.parseDouble(value);
			   if (value.contains(".")){
				   return true;
			   }else{
				   return false;
			   }
		  } catch (NumberFormatException e) {
			  return false;
		  }
	 }
	 
	 /**
	  * 判断字符串是否是数字
	  */
	 public static synchronized boolean isNumber(String value) {
	  return isInteger(value) || isDouble(value);
	 }
	
	//takeDepositType 存取类型 1） 存、 2）取、3）冻结
	/**
	 * 存
	 */
	public final static int TAKE_DEPOSIT_TYPE_INSTORE = 1;
	
	/**
	 * 取
	 */
	public final static int TAKE_DEPOSIT_TYPE_OUTSTORE = 2;
	
	/**
	 * 冻结
	 */
	public final static int TAKE_DEPOSIT_TYPE_FREEZE = 3;
	
	/**
	 * 0:划款失败
	 */
	public final static int TRANSFER_STATE_FAIL = 0;
	
	/**
	 * 1 划款成功
	 */
	public final static int TRANSFER_STATE_SUCCESSFUL = 1;
	
	/**
	 * 13 佣金收入
	 */
	public final static int TAKE_COMMISSION_INCOME = 13;
	
	/**
	 * 6冻结账户
	 */
	public final static String USER_FREEZE_STATE = "6";
	/**
	 * 7解冻账户
	 */
	public final static String USER_THAW_STATE = "7";
	
	/**
	 * 冻结失败
	 */
	public final static int FAIL_FREEZE_FAIL = 1;
	
	/**
	 * 通讯异常
	 */
	public final static int FAIL_FREEZE_COMMUNICATION_ERROR = 2;
	
	/**
	 * 已终结
	 */
	public final static short ACCOUNT_STATUS_ENDED = 2;
	
	/**
	 * 系统调账 （存
	 */
	public final static String SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS = "1";
	
	/**
	 * 系统调账 （存 名称
	 */
	public final static String SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS_NAME="系统调账";
	
	
	/**
	 * 系统冲账 （取
	 */
	public final static String SYS_TYPE_OFFSET_ACCOUNTS = "2";
	
	/**
	 * 系统冲账 （取 名称
	 */
	public final static String SYS_TYPE_OFFSET_ACCOUNTS_NAME="系统冲账 ";
	
	/**
	 * 支付宝 （取 名称
	 */
	public final static String SYS_TYPE_ALIBABA_ACCOUNTS_NAME="支付宝 ";
	
	/**
	 * 微信（取 名称
	 */
	public final static String SYS_TYPE_WECHAT_ACCOUNTS_NAME="微信 ";
	
	/**
	 * 银行转账 （取 名称
	 */
	public final static String SYS_TYPE_BANK_ACCOUNTS_NAME="银行转账 ";
	
	
	/**
	 * 8800活动
	 */
	public final static String EVENT_ACTIVITY_TYPE = "1";
	/**
	 * 6600活动
	 */
	public final static String EVENT_ACTIVITY_TYPE_6600 = "2";
	
	/**
	 * 不是活动账户
	 */
	public final static String EVENT_ACTIVITY_TYPE_NONE = "0";
	
	/**
	 * 活动用户最大数
	 */
	public final static int EVENT_ACTIVITY_WUSER_MAX_NUM = 1000;
	
	/**
	 * 活动用户创建发送短信暂停时间
	 */
	public final static int EVENT_ACTIVITY_MESSAGE_SLEEP_TIME_MILLISECOND = 1000 * 1;
	
	/**
	 * 默认充值金额
	 */
	public final static int EVENT_ACTIVITY_DEFAULT_AVL_MONEY = 800;
	/**
	 * 6600活动
	 */
	public final static int EVENT_ACTIVITY_6600_AVL_MONEY = 600;
	
	/**
	 * 1配资用户
	 */
	public final static String USER_TYPE_ASSET = "1";
	
	/**
	 * 成功paystatus
	 */
	public final static int RECHARGE_LIST_PAYS_STATUS_SUCCESS = 21;
	
	/**
	 * 待处理 paystatus
	 */
	public final static int RECHARGE_LIST_PAYS_STATUS_WAITING = 0;
	
	/**
	 * 佣金划款成功
	 */
	public final static String COMMISSION_STATE_SUCCESSFUL = "1";
	
	/**
	 * 佣金划款失败
	 */
	public final static String COMMISSION_STATE_FAIL = "2";
	
	/**
	 * CMS 类型
	 */
	public final static String USER_TYPE_CMS = "-3";
	
	/**
	 * 配资中划转成功
	 */
	public final static Short ADJUST_MENT_SUCCESSFUL = 1;
	/**
	 * 活动抽奖，奖励
	 */
	public final static Integer LUCK_DRAW= 30;
	/**
	 * 活动免损补贴
	 */
	public final static Integer ACTIVITY_LOSS_FREE_REWARD = 31;
	
	/**
	 * 方案审核状态"未通过"
	 */
	public final static int PLAN_NO_PASS = 2;
	
	/**
	 * 方案审核状态"审核通过"
	 */
	public final static int PLAN_PASS = 1;
	
	/**
	 * 支付宝充值类型
	 */
	public static final String ALIPAY_TYPE = "3";// 支付宝
	
	


}
