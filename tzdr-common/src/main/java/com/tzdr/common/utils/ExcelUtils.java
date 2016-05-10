package com.tzdr.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author zhouchen
 * @version 创建时间：2015年2月6日 上午9:06:31
 * 类说明
 */
public class ExcelUtils {

	public static Workbook create(InputStream inp) throws IOException,InvalidFormatException {
	    //如果此流实例的支持mark和reset方法，该方法返回true。
		if (!inp.markSupported()) {
	        inp = new PushbackInputStream(inp, 8);
	    }
		
		//xls返回的是HSSFWorkbook   , 而xlsx返回的是XSSFWorkbook. 
		
	    if (POIFSFileSystem.hasPOIFSHeader(inp)) {
	        return new HSSFWorkbook(inp);
	    }
	    if (POIXMLDocument.hasOOXMLHeader(inp)) {
	        return new XSSFWorkbook(OPCPackage.open(inp));
	    }
	    throw new IllegalArgumentException("你的excel版本目前poi解析不了");
	}
}
