package com.tzdr.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年4月2日上午8:51:42
 */
public class TypeColumnConvert {
	
	
	/**
	 * 设置
	 * @param clazz Class
	 */
	public void setCacheMethod(Class clazz) {
		Method methods[] = clazz.getDeclaredMethods();
		for (Method me:methods) {
			String fieldName = ExportExcel.toFirstToLower(me.getName().substring(3));
			if (me.getName().startsWith("set")) {
				Class clazzs[] = me.getParameterTypes();
				if (clazzs.length == 1) {
					fieldNameMethodNames.put(fieldName, me);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param clazz Class
	 * @param list List
	 * @throws Exception
	 */
	public TypeColumnConvert(Class clazz,List list) throws Exception {
		this.cacheColumnNames = queryFieldNamesByClass(clazz);
		this.setCacheMethod(clazz);
		for (Object columnObj:list) {
    		Map<String,Object> objColumns = (Map<String,Object>)columnObj;
    		
    		Object obj = clazz.newInstance();
    		for (Map.Entry<String, Object> keyValue:objColumns.entrySet()) {
    			String columnName = keyValue.getKey();
    			String fieldName = this.cacheColumnNames.get(columnName);
    			
    			Method method = this.fieldNameMethodNames.get(fieldName);
    			if (method != null) {
    				try {
    					Object value = keyValue.getValue(); 
    					if (value == null) {
    						continue;
    					}
						Class paramType = method.getParameterTypes()[0];
						if ("int".equals(paramType.getName())
								|| "Integer".equals(paramType.getName())) {
							double doubleValue = new Double(String.valueOf(value));
							method.invoke(obj,(int)doubleValue);
						}
						else if (paramType.isAssignableFrom(BigInteger.class)) {
							BigInteger bigValue = (BigInteger) value;
							method.invoke(obj,bigValue.longValue());
						}
						else if (paramType.isAssignableFrom(Long.class)) {
							double doubleValue = new Double(String.valueOf(value));
							method.invoke(obj,(long)doubleValue);
						}
						else if (paramType.isAssignableFrom(Integer.class)) {
							double doubleValue = new Double(String.valueOf(value));
							method.invoke(obj,(int)doubleValue);
						}
						else if (paramType.isAssignableFrom(Short.class)) {
							double doubleValue = new Double(String.valueOf(value));
							method.invoke(obj,(short)doubleValue);
						}
						else if (paramType.isAssignableFrom(Byte.class)) {
							double doubleValue = new Double(String.valueOf(value));
							method.invoke(obj,(byte)doubleValue);
						}
						else if (paramType.isAssignableFrom(Float.class)) {
							double doubleValue = new Double(String.valueOf(value));
							method.invoke(obj,(float)doubleValue);
						}
						else if (value instanceof BigInteger) {
							BigInteger bigValue = (BigInteger) value;
							method.invoke(obj,bigValue.intValue());
						}
						else {
							method.invoke(obj,value);
						}
    				}
    				catch (Exception e) {
    					e.printStackTrace();
    					Class paramType = method.getParameterTypes()[0];
    					Object value = keyValue.getValue();
    					if (value != null && paramType != null) {
    						System.out.println("result value " + value.getClass().getName() + " not match  VoObject of " + paramType.getName());
    					}
    				}
    			}
    		}
    		this.data.add(obj);
		}
	}
	
	/**
	 * 对象方法存储
	 */
	private Map<String,Method> fieldNameMethodNames = new HashMap<String,Method>();
	
	/**
	 * key is columnName of database
	 * value fieldName of database 
	 */
	private Map<String,String> cacheColumnNames = new ConcurrentHashMap<String,String>();
	
	private List data = new ArrayList();
	
	
	public List getData() {
		return data;
	}

	/**
	 * 查找对应的类自定义方法
	 * @param methodName
	 * @return boolean
	 */
	public boolean isClassMethodName(String methodName) {
		if (methodName != null) {
			if ("getClass".equals(methodName)) {
				return false;
			}
			if (methodName.startsWith("get") 
					|| methodName.startsWith("is")
					|| methodName.startsWith("set")) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * 获取已经注解的对象
	 * @param clazz Class
	 * @return Map<String,String>
	 */
	public Map<String,String> queryFieldNamesByClass(Class clazz) {
		
		Field fields[] = clazz.getDeclaredFields();
		for (Field field:fields) {
			SqlColumn sqlColumn = field.getAnnotation(SqlColumn.class);
			if (sqlColumn != null && sqlColumn.display()) {
				if (!"".equals(sqlColumn.name())) {
					cacheColumnNames.put(sqlColumn.name(),field.getName());
				}
				else {
					cacheColumnNames.put(field.getName(),field.getName());
				}
			}
		}
		
		Method methods[] = clazz.getDeclaredMethods();
		for (Method methodHandler:methods) {
			
			if (isClassMethodName(methodHandler.getName())) {
				
				SqlColumn sqlColumn = methodHandler.getAnnotation(SqlColumn.class);
				if (sqlColumn != null && sqlColumn.display()) {
					Class returnClazz = methodHandler.getReturnType();
					String fieldName = null;
					if (returnClazz.isAssignableFrom(Boolean.class) || "boolean".equals(returnClazz.getName())) {
						fieldName = ExportExcel.toFirstToLower(methodHandler.getName().substring(2));
					}
					else {
						fieldName = ExportExcel.toFirstToLower(methodHandler.getName().substring(3));
					}
					if (!"".equals(sqlColumn.name())) {
						cacheColumnNames.put(sqlColumn.name(),fieldName);
					}
					else {
						cacheColumnNames.put(fieldName, fieldName);
					}
					
				}
			}
		}
		return cacheColumnNames;
		
	}

}
