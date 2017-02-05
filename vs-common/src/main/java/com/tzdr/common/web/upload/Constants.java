package com.tzdr.common.web.upload;
/**
 * @author zhouchen
 * @version 创建时间：2014年12月5日 下午1:51:46
 * 类说明
 */
public class Constants {

	 //默认大小 50M
    public static final long DEFAULT_MAX_SIZE = 52428800;

    //默认上传的地址
    public static String defaultBaseDir = "upload";
    
    //编码
    public static final  String ENCODING = "UTF-8";

    //默认的文件名最大长度
    public static final int DEFAULT_FILE_NAME_LENGTH = 200;

    public static final String[] IMAGE_EXTENSION = {
            "bmp", "gif", "jpg", "jpeg", "png"
    };

    public static final String[] FLASH_EXTENSION = {
            "swf", "flv"
    };

    public static final String[] MEDIA_EXTENSION = {
            "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"
    };

    public static final String[] OFFICE_EXTENSION = {
    	"doc", "docx", "xls", "xlsx", "ppt", "pptx",
        "html", "htm", "txt"};
    
    public static final String[] EXCEL_EXTENSION = {"xls","xlsx"};
    
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            //图片
            "bmp", "gif", "jpg", "jpeg", "png",
            //word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx",
            "html", "htm", "txt",
            //压缩文件
            "rar", "zip", "gz", "bz2",
            //pdf
            "pdf"
    };
   
    /**
     * 上传错误码
     * @zhouchen
     * 2014年12月8日
     */
    public static  class  errorCode{
    	//系统异常，上传失败
    	public static final String UPLOAD_ERROR="u00000001";
    	
    	//不允许上传 这类型扩展名文件
    	public static final String NOT_ALLOW_EXTENSION="u00000002";
    	
    	
    	//文件大小超过限制
    	public static final String EXCEED_MAXSIZE="u00000003";
    	
    	
    	//文件名过长
    	public static final String FILENAME_EXCEED_LENGTH="u00000004";
    }
    
    public static class FileType{
    	public static final String IMAGE="img";
    	public static final String FLASH="flash";
    	public static final String MEDIA="media";
    	public static final String OFFICE="office";
    	public static final String EXCEL="excel";
    }
}
