package com.tzdr.web.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tzdr.common.utils.TypeConvert;
import com.tzdr.web.utils.template.ClusterTemplateClazz;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月3日下午4:32:31
 */
public class PageStatusUtil {
	
	static Log log = LogFactory.getLog(PageStatusUtil.class);
	
	/**
	 * 静态化页面目录
	 */
	public static String STATIC_PAGES_FOLDER = "";
	
	/**
	 * 获取RootPath
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static String getRootPath(ServletContext servletContext) {
		String path = servletContext.getRealPath("/");
		return path;
	}
	
	/**
	 * 获取类装载器
	 * @param clazz Class
	 * @return ClassLoader
	 */
	private static ClassLoader getClassLoader(Class<?> clazz) {
		ClassLoader clazzLoader = clazz.getClassLoader();
		if (clazzLoader == null) {
			clazzLoader = ClassLoader.getSystemClassLoader();
		}
		return clazzLoader;
	}
	
	/**
	 * 获取ClassPath路径
	 * @return String
	 */
	public static String getClassPath() {
		ClassLoader clazzLoader = getClassLoader(TypeConvert.class);
		URL url = clazzLoader.getResource("");
		String path = url.getPath();
		if (path.indexOf(":") > 0) {
			return path.substring(1);
		}
		return path;
	}
	
	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration();
    	//cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File("E:\\projects\\userpro\\code\\trunk\\tzdr-web\\src\\main\\java\\com\\tzdr\\web\\utils\\"));
    	//cfg.setClassForTemplateLoading(StudentInfo.class, "");
    	//读取template
    	Template template = cfg.getTemplate("homepage.ftl");
    	
    	// cfg.setClassForTemplateLoading(StudentInfo.class,"");;
         //cfg.setDirectoryForTemplateLoading(new File(getClassPath() + STATIC_PAGES_FOLDER));
         //读取template
       //  Template template = cfg.getTemplate("hello.ftl");
    	
    	//传入值  
    	Map<String,Object> dataModel = new HashMap<String,Object>();
    	dataModel.put("base", "/comser");
    	dataModel.put("isNeedCode", "ddd");
    	dataModel.put("activeUserCount", "ddd");
    	dataModel.put("newsdata", "ddd");
    	
    	List<StudentInfo> infoes = new ArrayList<StudentInfo>();  
    	for (int i = 0; i < 10; i++) {  
    		
    		StudentInfo info = new StudentInfo();  
    		info.setName(" qing " + i);  
    		info.setSex("male" + i);  
    		info.setAge(i);  
    		infoes.add(info);  
    	}  
    	dataModel.put("students", infoes);
    	//String rootPath = getRootPath(servletContext);
    	String rootPath = "f:\\un";
    	System.out.println(rootPath + " path");
    	File rootFile = new File(rootPath);
    	rootFile.mkdirs();
    	FileOutputStream outputStream = new FileOutputStream(
    			new File(rootPath + File.separator + "index22.html"));
    	PrintWriter out = new PrintWriter(outputStream);
    	template.process(dataModel, out);
    	out.flush();
    	out.close();
    	outputStream.close();
	}
	
	//静态锁定
	private static Object lock = new Object();
	
	/**
	 * 创建HttpServletRequest
	 * @param request HttpServletRequest
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void writeHtml(ServletContext servletContext,
			String templateName,Map<String,Object> dataModel) throws IOException, TemplateException {
	    synchronized (lock) {
	    	String base = servletContext.getContextPath();
	    	dataModel.put("base", base);
	    	Configuration cfg = new Configuration();
	    	cfg.setDefaultEncoding("UTF-8");
	    	cfg.setClassForTemplateLoading(ClusterTemplateClazz.class, "");
	    	//读取template
	    	Template template = cfg.getTemplate(templateName);
	    	String rootPath = getRootPath(servletContext);
	    	log.debug(rootPath);
	    	File rootFile = new File(rootPath);
	    	rootFile.mkdirs();
	    	FileOutputStream output = null;
	    	OutputStreamWriter outWriter = null;
	    	PrintWriter printWriter = null;
	    	try {
	    		output = new FileOutputStream(
						new File(rootPath + File.separator + "index.html"));
	    		outWriter = new OutputStreamWriter(output,"UTF-8");
	    		printWriter = new PrintWriter(outWriter);
				template.process(dataModel, printWriter);
			} 
	    	catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	    	finally {
	    		if (printWriter != null) {
	    			printWriter.close();
	    		}
	    		if (outWriter != null) {
	    			outWriter.close();
	    		}
	    		if (output != null) {
	    			output.close();
	    		}
	    	}
	    	
	    }
		
	}

}
