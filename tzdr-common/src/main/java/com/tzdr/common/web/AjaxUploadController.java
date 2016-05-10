package com.tzdr.common.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.upload.AjaxUploadResponse;
import com.tzdr.common.web.upload.AjaxUploadResponse.FileMeta;
import com.tzdr.common.web.upload.Constants;
import com.tzdr.common.web.upload.FileUploadUtils;
import com.tzdr.common.web.upload.exception.FileNameLengthLimitExceededException;
import com.tzdr.common.web.upload.exception.InvalidExtensionException;
/**
 * ajax文件上传/下载
 */
@Controller
public class AjaxUploadController {

	private Logger log = LoggerFactory.getLogger(AjaxUploadController.class);

    //最大上传大小 字节为单位
    private long maxSize = Constants.DEFAULT_MAX_SIZE;
    //允许的文件内容类型
    private String[] allowedExtension = Constants.DEFAULT_ALLOWED_EXTENSION;
    //文件上传下载的父目录
    private String baseDir = Constants.defaultBaseDir;

    /**
     * @param request
     * @param files
     * @return
     */
    @RequestMapping(value = "ajaxUpload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUploadResponse ajaxUpload(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "files[]", required = false) MultipartFile[] files,@RequestParam(value = "size", required = false) String imgSize,@RequestParam(value = "dir", required = false) String dir) {

        //The file upload plugin makes use of an Iframe Transport module for browsers like Microsoft Internet Explorer and Opera, which do not yet support XMLHTTPRequest file uploads.
        response.setContentType("text/plain");        
        
        AjaxUploadResponse ajaxUploadResponse = new AjaxUploadResponse();

        if (ArrayUtils.isEmpty(files)) {
            return ajaxUploadResponse;
        }
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            long size = file.getSize();

            try {
                String url = FileUploadUtils.upload(request,StringUtil.isNotBlank(dir)?dir:baseDir, file, allowedExtension, maxSize, true);
                url = url.replaceAll("\\\\","/");
                String deleteURL = "/ajaxUpload/delete?filename=" + URLEncoder.encode(url, Constants.ENCODING);
                if (FileUploadUtils.isImage(filename)) {
                    ajaxUploadResponse.add(filename, size, url, url, deleteURL);
                } else {
                    ajaxUploadResponse.add(filename, size, url, deleteURL);
                }
                continue;
            } catch (IOException e) {
                //LogUtils.logError("file upload error", e);
                ajaxUploadResponse.add(Constants.errorCode.UPLOAD_ERROR,filename, size, MessageUtils.message("upload.fail"));
                continue;
            } catch (InvalidExtensionException e) {
            	 //不允许上传这种类型的文件
                ajaxUploadResponse.add(Constants.errorCode.NOT_ALLOW_EXTENSION,filename, size, MessageUtils.message("upload.not.allow.extension"));
                continue;
            } catch (FileUploadBase.FileSizeLimitExceededException e) {
            	//文件大小超过限制
                ajaxUploadResponse.add(Constants.errorCode.EXCEED_MAXSIZE,filename, size, MessageUtils.message("upload.exceed.maxSize"));
                continue;
            } catch (FileNameLengthLimitExceededException e) {
            	//文件名过长
                ajaxUploadResponse.add(Constants.errorCode.FILENAME_EXCEED_LENGTH,filename, size, MessageUtils.message("upload.filename.exceed.length"));
                continue;
            }
        }
        return ajaxUploadResponse;
    }

    /**
     *  上传前 验证上传类型 上传文件最大限制
     * @param limitSize
     * @param fileType
     */
    private void beforeUpload(Long limitSize,String fileType,String dir){
    	if (!ObjectUtil.equals(limitSize, null) && limitSize > 0){
        	this.maxSize=limitSize*1024*1024;
        }
        
        if (StringUtil.equals(fileType, Constants.FileType.IMAGE)){
        	this.allowedExtension=Constants.IMAGE_EXTENSION;
        }
        
        if (StringUtil.equals(fileType, Constants.FileType.FLASH)){
        	this.allowedExtension=Constants.FLASH_EXTENSION;
        }
        
        if (StringUtil.equals(fileType, Constants.FileType.MEDIA)){
        	this.allowedExtension=Constants.MEDIA_EXTENSION;
        }
        
        if (StringUtil.equals(fileType, Constants.FileType.OFFICE)){
        	this.allowedExtension=Constants.OFFICE_EXTENSION;
        }
        
        if (StringUtil.equals(fileType, Constants.FileType.EXCEL)){
        	this.allowedExtension=Constants.EXCEL_EXTENSION;
        }
        if(StringUtil.isNotBlank(dir)){
        	this.baseDir=dir;
        }
    }
    
    
    public static void printText(String str, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(str);
			out.flush();
		} catch (Exception e) {

		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
    
    
    /**
     * @param request
     * @param files
     * @return
     */
    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    public void singleAjaxUpload(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "files[]", required = false) MultipartFile[] files,
            @RequestParam(value = "limitSize", required = false) Long limitSize,
            @RequestParam(value = "fileType", required = false) String fileType,
            @RequestParam(value = "dir", required = false) String dir) {

        //The file upload plugin makes use of an Iframe Transport module for browsers like Microsoft Internet Explorer and Opera, which do not yet support XMLHTTPRequest file uploads.
        response.setContentType("text/plain");
        
        beforeUpload(limitSize, fileType,dir);
        
        AjaxUploadResponse ajaxUploadResponse = new AjaxUploadResponse();

        if (ArrayUtils.isEmpty(files)) {
        	printText(JSONObject.toJSONString(new FileMeta()),response);
        	return;
        }
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            long size = file.getSize();

            try {
                String url = FileUploadUtils.upload(request,baseDir, file, allowedExtension, maxSize, true);
                url = url.replaceAll("\\\\","/");
                String deleteURL = "/ajaxUpload/delete?filename=" + URLEncoder.encode(url, Constants.ENCODING);
                if (FileUploadUtils.isImage(filename)) {
                    ajaxUploadResponse.add(filename, size, url, url, deleteURL);
                } else {
                    ajaxUploadResponse.add(filename, size, url, deleteURL);
                }
                continue;
            } catch (IOException e) {
                //LogUtils.logError("file upload error", e);
                ajaxUploadResponse.add(Constants.errorCode.UPLOAD_ERROR,filename, size, MessageUtils.message("upload.server.error"));
                continue;
            } catch (InvalidExtensionException e) {
            	 //不允许上传这种类型的文件
                ajaxUploadResponse.add(Constants.errorCode.NOT_ALLOW_EXTENSION,filename, size, MessageUtils.message("upload.not.allow.extension"));
                continue;
            } catch (FileUploadBase.FileSizeLimitExceededException e) {
            	//文件大小超过限制
                ajaxUploadResponse.add(Constants.errorCode.EXCEED_MAXSIZE,filename, size, MessageUtils.message("upload.exceed.maxSize",limitSize==0?50:limitSize));
                continue;
            } catch (FileNameLengthLimitExceededException e) {
            	//文件名过长
                ajaxUploadResponse.add(Constants.errorCode.FILENAME_EXCEED_LENGTH,filename, size, MessageUtils.message("upload.filename.exceed.length"));
                continue;
            }
        }
        
        // 返回file
        List<FileMeta>   fileMetas  =ajaxUploadResponse.getFiles(); 
        if (fileMetas.size()>0)
        {
        	printText(JSONObject.toJSONString(fileMetas.get(0)),response);
        	return;
        }
        
        printText(JSONObject.toJSONString(new FileMeta()),response);
        return;
    }

    @RequestMapping(value = "ajaxUpload/delete", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxUploadDelete(
            HttpServletRequest request,
            @RequestParam(value = "filename") String filename) throws Exception {

        if (StringUtil.isEmpty(filename) || filename.contains("\\.\\.")) {
            return "";
        }
        filename = URLDecoder.decode(filename, Constants.ENCODING);

        String filePath = FileUploadUtils.extractUploadDir(request) + "/" + filename;

        File file = new File(filePath);
        file.deleteOnExit();

        return "";
    }
    
    
    public static void main(String[] args) {
    	String str = "testUplaod\2014/12/05\356853047270288845d21289f31e364d.txt";
    	
    	System.out.println(str.replaceAll("\\\\","/"));
	}
}
