package com.tzdr.common.web.support;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * JSON Response Basic data
 * 
 * @author Lin Feng
 * 
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer",
		"javassistLazyInitializer" })
public class JsonResult {

	private boolean success = true;

	private String code = "";

	private String message = "";

	private Map<Object, Object> data = new HashMap<Object, Object>();
	
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public JsonResult() {
		super();
	}

	public JsonResult(boolean success) {
		super();
		this.success = success;
	}

	public JsonResult(String code, String message) {
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

	public Map<Object, Object> getData() {
		return data;
	}

	public void setData(Map<Object, Object> data) {
		this.data = data;
	}

	public void appendData(Object key, Object value) {
		this.data.put(key, value);
	}

	public void appendData(Map<?, ?> map) {
		this.data.putAll(map);
	}

	public JsonResult(boolean success, String code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public JsonResult(String message) {
		super();
		this.message = message;
	}

	public JsonResult(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public JsonResult(boolean success, String code, String message,Object object) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.obj=object;
	}

	public JsonResult(String message,boolean success,Object object) {
		super();
		this.message = message;
		this.obj = object;
	}
	
}
