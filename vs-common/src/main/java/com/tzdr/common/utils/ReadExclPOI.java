package com.tzdr.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExclPOI {
	/**
	 * 读取excl2007
	 * @param filePath
	 * 			文件路劲
	 * @param cls
	 * 			写入的目标一个对象
	 * 文件中的列长度需要和写入的目标对象中属性顺序对应且属性个数和文件列数一致
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public List<?> readExcl2007(String filePath,Class cls) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Object> resultList = new ArrayList<Object>();
		InputStream inputStream = new FileInputStream(filePath);
		XSSFWorkbook xssf = new XSSFWorkbook(inputStream);
		XSSFSheet xssfSheet = xssf.getSheetAt(0);
		if (xssfSheet != null) {
			//获取class的构造函数
			Constructor[] consts = getConstructor(cls);
			int fields = getFieldsLength(cls);
			Iterator<Row> rows = xssfSheet.iterator();
			while (rows.hasNext()) {
				Row row = rows.next();
				Iterator<Cell> cells = row.cellIterator();
				Object[] object = new Object[fields];
				int count = 0;
				while (cells.hasNext()) {
					Cell cell = cells.next();
					int cellType = cell.getCellType();
					switch (cellType) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						object[count] = cell.getNumericCellValue();
						break;
					case HSSFCell.CELL_TYPE_STRING:
						object[count] = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						object[count] = cell.getBooleanCellValue();
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						object[count] = cell.getCellFormula();
						break;
					}
					object[count] = String.valueOf(object[count]);
					count++;
					if(count == fields){
						break;
					}
				}
				resultList.add(getInstance(consts, object));
			}
		}
		return resultList;
	}
	/**
	 * 读取excl2003
	* @param filePath
	 * 			文件路劲
	 * @param cls
	 * 			写入的目标一个对象
	 * 文件中的列长度需要和写入的目标对象中属性顺序对应且属性个数和文件列数一致
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public List<?> readExcl2003(String filePath,Class cls) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Object> resultList = new ArrayList<Object>();
		InputStream inputStream = new FileInputStream(filePath);
		HSSFWorkbook xssf = new HSSFWorkbook(inputStream);
		HSSFSheet xssfSheet = xssf.getSheetAt(0);
		if (xssfSheet != null) {
			//获取class的构造函数
			Constructor[] consts = getConstructor(cls);
			int fields = getFieldsLength(cls);
			Iterator<Row> rows = xssfSheet.iterator();
			while (rows.hasNext()) {
				Row row = rows.next();
				Iterator<Cell> cells = row.cellIterator();
				Object[] object = new Object[fields];
				int count = 0;
				while (cells.hasNext()) {
					Cell cell = cells.next();
					int cellType = cell.getCellType();
					switch (cellType) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						object[count] = cell.getNumericCellValue();
						break;
					case HSSFCell.CELL_TYPE_STRING:
						object[count] = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						object[count] = cell.getBooleanCellValue();
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						object[count] = cell.getCellFormula();
						break;
					}
					object[count] = String.valueOf(object[count]);
					count++;
					if(count == fields){
						break;
					}
				}
				resultList.add(getInstance(consts, object));
			}
		}
		return resultList;
	}
	/**
	 * 获取构造函数
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Constructor[] getConstructor(Class cls){
		return cls.getConstructors();
	}
	/**
	 * 获取类属性数量
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private int getFieldsLength(Class cls){
		//获取class的属性
		Field[] fields = cls.getDeclaredFields();
		return fields.length;
	}
	/**
	 * 创建对象
	 * @param consts
	 * 			构造函数数组集合
	 * @param obj
	 * 		  	Object对象数组集合
	 * @return Object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	private Object getInstance(Constructor[] consts,Object[] obj) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Constructor constructor = null;
		for (Constructor object : consts) {
			if(object.getParameterTypes().length == obj.length){
				constructor = object;
			}
		}
		return constructor != null ? constructor.newInstance(obj) : null;
	}
}
