package com.tzdr.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import jodd.util.StringUtil;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将html转为word
 * <P>title:@HtmlToDoc.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年4月27日
 * @version 1.0
 */
public class HtmlToDocUtils {
	private static Logger log = LoggerFactory.getLogger(HtmlToDocUtils.class);
	 /**

     * 读取html文件到word
     * @param filepath html文件的路径
     * @param targetpath 
     * @return
     * @throws Exception

     */
    public static boolean writeWordFile(String filepath,String targetpath) throws Exception {
           boolean flag = false;
           ByteArrayInputStream bais = null;
           FileOutputStream fos = null;
           try {
                  if (StringUtil.isNotBlank(targetpath)) {
                	     String dir=targetpath.substring(0,targetpath.lastIndexOf(File.separator));
                         File fileDir = new File(dir);
                         if(!fileDir.exists())
                        	 fileDir.mkdirs();
                         if (fileDir.exists()) {
                                String content = readFile(filepath);
                                byte b[] = content.getBytes();
                                bais = new ByteArrayInputStream(b);
                                POIFSFileSystem poifs = new POIFSFileSystem();
                                DirectoryEntry directory = poifs.getRoot();
                                directory.createDocument("WordDocument", bais);
                                fos = new FileOutputStream(targetpath);
                                poifs.writeFilesystem(fos);
                                bais.close();
                                fos.close();
                                flag=true;
                         }
                  }
           } catch (IOException e) {
        	   log.error("生成word文件错误"+filepath+e.getMessage());
           } finally {
                  if(fos != null) fos.close();
                  if(bais != null) bais.close();
           }
           return flag;
    }

    /**
     * 读取html文件到字符串
     * @param filename
     * @return
     * @throws Exception
     */

    public static String readFile(String filename) throws Exception {
           StringBuffer buffer = new StringBuffer("");
           BufferedReader br = null;
           try {
                  br = new BufferedReader(new FileReader(filename));
                  buffer = new StringBuffer();
                  while (br.ready())
                         buffer.append((char) br.read());
           } catch (Exception e) {
        	   log.error("生成word文件错误"+e.getMessage());
           } finally {
                  if(br!=null) br.close();
           }
           return buffer.toString();
    }

  



}

