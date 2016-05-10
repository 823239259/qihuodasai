package com.tzdr.api.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <B>说明: </B>接口统一返回值
 * @zhouchen
 * 2016年1月20日
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer",
		"javassistLazyInitializer" })
public class ApiResult {

	private boolean success = true;

	private String code = "";

	private String message = "";
	
	private Object data;
	
	

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ApiResult() {
		super();
	}

	public ApiResult(boolean success) {
		super();
		this.success = success;
	}

	public ApiResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
		this.success = false;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ApiResult(boolean success, String code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public ApiResult(String message) {
		super();
		this.message = message;
	}

	public ApiResult(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public ApiResult(boolean success, String code, String message,Object data) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.data=data;
	}	
}
