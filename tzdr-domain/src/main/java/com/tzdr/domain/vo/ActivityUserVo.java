package com.tzdr.domain.vo;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月10日下午4:28:11
 */
public class ActivityUserVo implements Serializable {
	
	public static void main(String[] args) {
		Double a = 10092893710182007D;
		System.out.println(new BigDecimal(a.toString()));
	}
	
	/**
	 * excel 转化为对象
	 * @param file File
	 * @return List<ActivityUserVo>
	 * @throws Exception
	 */
	public static List<ActivityUserVo> excelToObjs(InputStream input) throws Exception {
		
		List<ActivityUserVo> voes = new ArrayList<ActivityUserVo>();
		//InputStream input = new FileInputStream(file);
		 Workbook wb = WorkbookFactory.create(input);
		    Sheet sheet = wb.getSheetAt(0);
		    Iterator<Row> rows =  sheet.rowIterator();
		    while (rows.hasNext()) {
		    	ActivityUserVo vo = new ActivityUserVo();
		    	Row row = rows.next();
		    	Iterator<Cell> cells = row.cellIterator();
		    	int columnIndex = 0;
		    	while (cells.hasNext()) {
		    		Cell cell = cells.next();
		    		int cellType = cell.getCellType();
		    		if (cellType == Cell.CELL_TYPE_NUMERIC) {
		    			if (columnIndex == 0) {
		    				vo.setSn(cell.getNumericCellValue() + "");
		    			}
		    			else if (columnIndex == 1){
		    				vo.setMobile(
		    						TypeConvert.scale(new BigDecimal(cell.getNumericCellValue()),0).toString());
		    				break;
		    			}
		    			columnIndex++;
		    		}
		    		else if (cellType == Cell.CELL_TYPE_STRING) {
		    			if (columnIndex == 0) {
		    				vo.setSn(cell.getStringCellValue());
		    			}
		    			else if (columnIndex == 1) {
		    				vo.setMobile(cell.getStringCellValue());
		    				break;
		    			}
		    			columnIndex++;
		    		}
		    	}
		    	voes.add(vo);
		    }
		return voes;
	}
	
	
	private static final long serialVersionUID = 6220632691179413487L;

	//序号
	private String sn;
	//手机号
	private String mobile;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "ActivityUserVo [sn=" + sn + ", mobile=" + mobile + "]";
	}
	

}
