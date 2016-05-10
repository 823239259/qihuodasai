package com.tzdr.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author LiuQing
 * @see excel 导出工具类
 * 使用方法
 * class EntityObjectClass {
 * 
 *      @AllowExcel(name="姓名")
 * 		private String name;
 * 
 *      @AllowExcel(name="生日")
 *      private Date birthday
 * 
 * }
 * 
 * ExportExcel<EntityObjectClass> export = new ExportExcel<EntityObjectClass>();
 * List<EntityObjectClass> data = new ArrayList<EntityObjectClass>();
 * InputStream inputStream = export.createExcel(data);
 * 
 * @version 1.0
 * @datetime 2010-11-24
 *
 */
public class ExportExcel<T extends Serializable> {
	
	/**
	 * 定义Excel hander 
	 */
	private Workbook excelHander = new HSSFWorkbook();
	
	/**
	 * 生成sheet 名称
	 */
	private String sheetName = "sName";
	
	private boolean showHeader = true;
	
	private Collection<T> tempData = new ArrayList<T>();
	
	private boolean isFirst = true;
	
	/**
	 * 缓存成员变量
	 */
	private List<String> fieldNameCaches = new ArrayList<String>();
	
	
	public ExportExcel() {
		
	}
    public ExportExcel(Collection<T> data) {
		this.tempData = data;
	}
    
    public ExportExcel(Collection<T> data,HttpServletRequest request,HttpServletResponse response,String filename) {
    	
    	String userAgent = request.getHeader("USER-AGENT");
    	this.filename = filename;
    	String finalFileName = null;
        try {
			if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
			    finalFileName = URLEncoder.encode(this.filename,"UTF8");
			}
			else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
			    finalFileName = new String(this.filename.getBytes(), "ISO8859-1");
			}
			else{
			    finalFileName = URLEncoder.encode(this.filename,"UTF8");//其他浏览器
			}
		} 
        catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	this.filename = finalFileName;
    	response.resetBuffer();
		this.tempData = data;
		this.execute(response);
	}
    
	/**
	 * @see 生成Excel 文件
	 * @param data
	 * @return
	 * @throws IllegalAccessException
	 */
	public InputStream createExcel(Collection<T> data) throws Exception {
		this.tempData = data;
		this.createHeader(data);
		return this.getInputStream();
	}
	
	public static String string2Unicode(String string) {
		 
	    StringBuffer unicode = new StringBuffer();
	    for (int i = 0; i < string.length(); i++) {
	        // 取出每一个字符
	        char c = string.charAt(i);
	        // 转换为unicode
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	    return unicode.toString();
	}
	
	private String filename = "export.xls";
	
	public void execute(HttpServletResponse response) {
		try {
			InputStream input = this.createExcel();
			String contentType = "application/vnd.ms-excel";
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			OutputStream output = response.getOutputStream();
			byte b[] = new byte[1024];
			while (true) {
				int length = input.read(b);
				if (length == -1) {
					break;
				}
				output.write(b,0,length);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addDate(T t) throws Exception {
		this.tempData.add(t);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public InputStream createExcel() throws Exception {
		this.createHeader(this.tempData);
		return this.getInputStream();
	}
	
	/**
	 * 创建器
	 * @param data
	 * @throws IllegalAccessException
	 */
	private void createHeader(Collection<T> data) throws Exception {
		//Sheet 创建工作表
		Sheet sheet = excelHander.createSheet(sheetName);
		
		int j = 0;
		for (T t:data) {
			Field[] fields = t.getClass().getDeclaredFields();
			/**
			 * 加入允许字段缓存数据
			 * if == 0时表示要添加缓存数据
			 */
			if (j == 0) {
				Row headRow = null;
				if (this.showHeader) {
					headRow = sheet.createRow(0);
				}
				int i = 0;
				for (Field field:fields) {
					//判断Excel 安全允许注解
					AllowExcel allowExcel = field.getAnnotation(AllowExcel.class);
					if (allowExcel != null && allowExcel.value()) {
						//显示关部信息
						if (this.showHeader) {
							Cell cell = headRow.createCell(i);
							cell.setCellValue(allowExcel.name());
							i++;
						}
						this.fieldNameCaches.add(field.getName());
					}
				}
				j++;
			}
			//创建产生行数据
			Row hssfRow = sheet.createRow(j);
			this.setCellValueToRow(t, hssfRow);
			j++;
			
		}
	}
	
	/**
	 * 输出Excel Row 信息
	 * @param t T extends Serializable
	 * @param hssfRow HSSFRow
	 * @return HSSFRow 
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public Row setCellValueToRow(T t,Row hssfRow) throws Exception {
		Class clazz = t.getClass();
		Field fields[] = clazz.getDeclaredFields();
		
		//定义Excel 输出行数
		int i = 0;
		for (Field field:fields) {
			//缓存中是否存在允许字段
			if (this.isCacheFiledName(field.getName())) {
				Cell cell = hssfRow.createCell(i);
				i++;
				field.setAccessible(true);
				Class clazzType = field.getType();
				String methodName = "";
				if (clazzType.getName().equals("boolean") || 
						clazzType.getName().equals("java.lang.Boolean")) {
					methodName = "is" + this.toFirstToUp(field.getName());
				}
				else {
					methodName = "get" + this.toFirstToUp(field.getName());
				}
				Object obj = clazz.getMethod(methodName, null).invoke(t, null);
				//Object obj = clazz.getDeclaredMethod(methodName, null).invoke(t, null);
				//类型转换
				if (obj instanceof Integer ) {
					cell.setCellValue((Integer)obj);
				}
				else if (obj instanceof String) {
					cell.setCellValue((String)obj);
				}
				else if (obj instanceof Date) {
					cell.setCellValue((Date)obj);
				}
				else if (obj instanceof Double) {
					cell.setCellValue((Double)obj);
				}
				else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean)obj);
				}
				else if (obj instanceof Float) {
					cell.setCellValue((Float)obj);
				}
				else if (obj instanceof Long) {
					cell.setCellValue((Long)obj);
				}
				else if (obj instanceof BigDecimal) {
					cell.setCellValue(obj.toString());
				}
				else if (obj instanceof BigInteger) {
					cell.setCellValue(obj.toString());
				}
				else {
					try {
					cell.setCellValue(obj.toString());
					}
					catch(Exception e){}
					//System.out.println(obj);
					
					//throw new TypeNotPresentException("类型不支持", null);
				}
				
			}
		}
		return hssfRow;
	}
	
	/**
	 * 判断Cache 是否有对应的FiledName
	 * @param fieldName String
	 * @return boolean
	 */
	private boolean isCacheFiledName(String fieldName) {
		if (fieldName == null) {
			return false;
		}
		for (String fieldNameCache:this.fieldNameCaches) {
			if (fieldName.equals(fieldNameCache)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得输入流
	 * @return InputStream
	 */
	private InputStream getInputStream() {
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		InputStream inputStream = null;
		try {
			this.excelHander.write(output);
			byte b[] = output.toByteArray();
			inputStream = new ByteArrayInputStream(b);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				output.flush();
				output.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputStream;
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
	 * 
	 * @param str
	 * @return String
	 */
	public static String toFirstToLower(String str) {
		char chars[] = TypeConvert.cleanSpaceCharacter(str).toCharArray();
		if (chars != null && chars.length > 0) {
			if (chars[0] >= 'A' && chars[0] < 'a') {
				chars[0] = (char) (chars[0] + 32);
			}
		}
		return new String(chars);
		
	}
	
	/**
	 * 首字母转为大写
	 * @param str String
	 * @return String
	 */
	public static String toFirstToUp(String str) {
		char chars[] = TypeConvert.cleanSpaceCharacter(str).toCharArray();
		if (chars != null && chars.length > 0) {
			if (chars[0] >= 'a' && chars[0] <= 'z') {
				chars[0] = (char) (chars[0] - 32);
			}
		}
		return new String(chars);
		
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

}
