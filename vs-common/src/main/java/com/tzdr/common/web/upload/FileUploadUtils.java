package com.tzdr.common.web.upload;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.tzdr.common.utils.Md5Utils;
import com.tzdr.common.web.upload.exception.FileNameLengthLimitExceededException;
import com.tzdr.common.web.upload.exception.InvalidExtensionException;

/**
 * 文件上传工具类
 */
public class FileUploadUtils {

	private static int counter = 0;
    /**
     * 以默认配置进行文件上传
     *
     * @param request 当前请求
     * @param file    上传的文件
     * @param result  添加出错信息
     * @return
     */
    public static final String upload(HttpServletRequest request, MultipartFile file, BindingResult result,String imgSize) {
        return upload(request, file, result, Constants.DEFAULT_ALLOWED_EXTENSION,imgSize);
    }


    /**
     * 以默认配置进行文件上传
     *
     * @param request          当前请求
     * @param file             上传的文件
     * @param result           添加出错信息
     * @param allowedExtension 允许上传的文件类型
     * @return
     */
    public static final String upload(HttpServletRequest request, MultipartFile file, BindingResult result, String[] allowedExtension,String imgSize) {
        try {
            return upload(request, Constants.defaultBaseDir, file, allowedExtension, Constants.DEFAULT_MAX_SIZE, true);
        } catch (IOException e) {
            //LogUtils.logError("file upload error", e);
            result.reject("upload.server.error");
        } catch (InvalidExtensionException.InvalidImageExtensionException e) {
            result.reject("upload.not.allow.image.extension");
        } catch (InvalidExtensionException.InvalidFlashExtensionException e) {
            result.reject("upload.not.allow.flash.extension");
        } catch (InvalidExtensionException.InvalidMediaExtensionException e) {
            result.reject("upload.not.allow.media.extension");
        } catch (InvalidExtensionException e) {
            result.reject("upload.not.allow.extension");
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            result.reject("upload.exceed.maxSize");
        } catch (FileNameLengthLimitExceededException e) {
            result.reject("upload.filename.exceed.length");
        }
        return null;
    }


    /**
     * 文件上传
     *
     * @param request          当前请求 从请求中提取 应用上下文根
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 允许的文件类型 null 表示允许所有
     * @param maxSize          最大上传的大小 -1 表示不限制
     *@param needDatePathAndRandomName 是否需要日期目录和随机文件名前缀
     * @return 返回上传成功的文件名
     * @throws InvalidExtensionException      如果MIME类型不允许
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException
     *                                        文件名太长
     * @throws IOException                    比如读写文件出错时
     */
    public static final String upload(
            HttpServletRequest request, String baseDir, MultipartFile file,
            String[] allowedExtension, long maxSize, boolean needDatePathAndRandomName)
            throws InvalidExtensionException, FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException {

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > Constants.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength, Constants.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension, maxSize);
        String filename = extractFilename(file, baseDir, needDatePathAndRandomName);

        File desc = getAbsoluteFile(extractUploadDir(request), filename);
        
        file.transferTo(desc);
        
       /* 
        * 此处为切图 暂时不用
        * if(StringUtils.isNotBlank(imgSize)){
        	String[] size = imgSize.split(",");
        	for(String s:size){
	        	if(StringUtil.isNotBlank(s)){
	        		String[] hw =  s.split("x");
	        		 Thumbnails.of(desc).size(Integer.valueOf(hw[0]),Integer.valueOf(hw[1])).toFile(
	        				 desc.getAbsolutePath().replace(".", s+"."));
	        	}
        	}
        }*/
        
        
        return filename;
    }

    private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException {

        uploadDir = FilenameUtils.normalizeNoEndSeparator(uploadDir);

        File desc = new File(uploadDir + File.separator + filename);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }


    public static final String extractFilename(MultipartFile file, String baseDir, boolean needDatePathAndRandomName)
            throws UnsupportedEncodingException {
        String filename = file.getOriginalFilename();
        int slashIndex = filename.indexOf("/");
        if (slashIndex >= 0) {
            filename = filename.substring(slashIndex + 1);
        }
        filename = filename.substring(filename.indexOf("."));
        if(needDatePathAndRandomName) {
            filename = baseDir + File.separator + datePath() + File.separator + encodingFilename(filename);
        } else {
            filename = baseDir + File.separator + filename;
        }

        return filename;
    }

    /**
     * 编码文件名,默认格式为：
     * 1、'_'替换为 ' '
     * 2、hex(md5(filename + now nano time + counter++))
     * 3、[2]_原始文件名
     *
     * @param filename
     * @return
     */
    private static final String encodingFilename(String filename) {
        filename = filename.replace("_", " ");
        filename = Md5Utils.hash(filename + System.nanoTime() + counter++) + filename;
        return filename;
    }

    /**
     * 日期路径 即年/月/日  如2013/01/03
     *
     * @return
     */
    private static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }


    /**
     * 是否允许文件上传
     *
     * @param file             上传的文件
     * @param allowedExtension 文件类型  null 表示允许所有
     * @param maxSize          最大大小 字节为单位 -1表示不限制
     * @return
     * @throws InvalidExtensionException      如果MIME类型不允许
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension, long maxSize)
            throws InvalidExtensionException, FileSizeLimitExceededException {

        String filename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == Constants.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension, filename);
            } else if (allowedExtension == Constants.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension, filename);
            } else if (allowedExtension == Constants.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension, filename);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, filename);
            }
        }

        long size = file.getSize();
        if (maxSize != -1 && size > maxSize) {
            throw new FileSizeLimitExceededException("not allowed upload upload", size, maxSize);
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 提取上传的根目录 默认是应用的根
     *
     * @param request
     * @return
     */
    public static final String extractUploadDir(HttpServletRequest request) {
        return request.getServletContext().getRealPath("/");
    }


    public static final void delete(HttpServletRequest request, String filename) throws IOException {
        if (StringUtil.isEmpty(filename)) {
            return;
        }
        File desc = getAbsoluteFile(extractUploadDir(request), filename);
        if (desc.exists()) {
            desc.delete();
        }
    }
    
    private static final String[] IMAGES_SUFFIXES = {
        "bmp", "jpg", "jpeg", "gif", "png", "tiff"
	};
	
	/**
	 * 是否是图片附件
	 *
	 * @param filename
	 * @return
	 */
	public static boolean isImage(String filename) {
	    if (filename == null || filename.trim().length() == 0) return false;
	    return ArrayUtils.contains(IMAGES_SUFFIXES, FilenameUtils.getExtension(filename).toLowerCase());
	}
}
