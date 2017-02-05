package com.tzdr.business.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import jodd.io.FileUtil;


/**
 * @author zhouchen
 * @version 创建时间：2014年12月8日 下午5:54:13 类说明
 */
public class WebUtil {

	/**
	* @Description: TODO(创建配资合同文件)
	* @Title: createContractFile
	* @param prefix   	文件名称   如：contractTemplate.ftl的contractTemplate为文件名称
	* @param suffix     文件后缀  如：contractTemplate.ftl的.ftl为文件后缀  
	* @param params     合同模板动态值
	* @param request
	* @throws IOException
	* @return void    返回类型
	 */
	public static String createContractFile(String prefix,String suffix,List<String> params,String basePath) throws Exception{
		//站点根目录的绝对路径
		//String basePath = request.getSession().getServletContext().getRealPath("/");
		//模板文件绝对路径
		String tempPath = basePath +"static"+File.separator+"views"+File.separator+"template"+File.separator;
		//模板文件内容
		String tempStr = FileUtil.readUTFString(new FileInputStream(tempPath+"contractTemplate.ftl"));
		for (String string : params) {
			tempStr = tempStr.replaceFirst("%s", string);
		}
		//新文件绝对路径
		String filePath = basePath + "upload"+File.separator+"tradeContract"+File.separator;
		//创建配资合同文件
		if(!new File(filePath+prefix+suffix).exists()){
			FileUtil.appendString(new File(filePath+prefix+suffix), tempStr);
		}
		
		return filePath+prefix+suffix;
	}
	
	
	/**
	* @Description:港股创建配资合同文件
	* @Title: createContractFile
	* @param prefix   	文件名称   如：contractTemplate.ftl的contractTemplate为文件名称
	* @param suffix     文件后缀  如：contractTemplate.ftl的.ftl为文件后缀  
	* @param params     合同模板动态值
	* @param request
	* @throws IOException
	* @return void    返回类型
	 */
	public static String createHkstockContractFile(String prefix,String suffix,List<String> params,String basePath) throws Exception{
		//站点根目录的绝对路径
		//String basePath = request.getSession().getServletContext().getRealPath("/");
		//模板文件绝对路径
		String tempPath = basePath +"static"+File.separator+"views"+File.separator+"template"+File.separator;
		//模板文件内容
		String tempStr = FileUtil.readUTFString(new FileInputStream(tempPath+"contractTemplate_hk.ftl"));
		for (String string : params) {
			tempStr = tempStr.replaceFirst("%s", string);
		}
		//新文件绝对路径
		String filePath = basePath + "upload"+File.separator+"tradeContract"+File.separator;
		//创建配资合同文件
		if(!new File(filePath+prefix+suffix).exists()){
			FileUtil.appendString(new File(filePath+prefix+suffix), tempStr);
		}
		
		return filePath+prefix+suffix;
	}
	
}
