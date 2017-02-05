package com.tzdr.common.web.upload;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * jquery File Upload 文件上传响应
 */
public class AjaxUploadResponse {

    private List<FileMeta> files = Lists.newArrayList();

    public void add(String name, long size, String error) {
        files.add(new FileMeta(name, size, error));
    }

    public void add(String errorCode,String name, long size, String errorMessage) {
        files.add(new FileMeta(errorCode,name, size, errorMessage));
    }
    
    public void add(String name, long size, String url, String delete_url) {
        files.add(new FileMeta(name, size, url, delete_url));
    }

    public void add(String name, long size, String url, String thumbnail_url, String delete_url) {
        files.add(new FileMeta(name, size, url, thumbnail_url, delete_url));
    }

    public List<FileMeta> getFiles() {
        return files;
    }

    public void setFiles(List<FileMeta> files) {
        this.files = files;
    }

    /**
     * 文件信息
     */
    public static class FileMeta {

        /**
         * 名称
         */
        private String name = "";
        /**
         * 大小
         */
        private long size;

        /**
         * 错误码
         */
        private String errorCode = "";
        
        /**
         * 错误信息
         */
        private String error = "";
        /**
         * 上传文件后的地址
         */
        private String url = "";
        /**
         * 缩略图
         */
        private String thumbnailUrl = "";
        /**
         * 删除时的URL
         */
        private String deleteUrl = "";
        private String delete_type = "POST";

        public FileMeta(String name, long size, String url, String thumbnail_url, String delete_url) {
            this.name = name;
            this.size = size;
            this.url = url;
            this.thumbnailUrl = thumbnail_url;
            this.deleteUrl = delete_url;
        }

        public FileMeta(String name, long size, String url, String delete_url) {
            this.name = name;
            this.size = size;
            this.url = url;
            this.deleteUrl = delete_url;
        }

        public FileMeta(String name, long size, String error) {
            this.name = name;
            this.error = error;
            this.size = size;
        }

        public FileMeta(String errorCode,String name, long size, String errorMessage) {
            this.name = name;
            this.error = errorMessage;
            this.size = size;
            this.errorCode=errorCode;
        }
        public FileMeta() {
        }
        
        
        public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

       

        public String getThumbnailUrl() {
			return thumbnailUrl;
		}

		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}

		public String getDeleteUrl() {
			return deleteUrl;
		}

		public void setDeleteUrl(String deleteUrl) {
			this.deleteUrl = deleteUrl;
		}

		public String getDelete_type() {
            return delete_type;
        }

        public void setDelete_type(String delete_type) {
            this.delete_type = delete_type;
        }
    }

    
}
