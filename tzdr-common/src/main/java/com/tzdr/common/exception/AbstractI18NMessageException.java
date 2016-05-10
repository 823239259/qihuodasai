package com.tzdr.common.exception;

import jodd.util.StringUtil;

import com.tzdr.common.utils.MessageUtils;

/**
 * 异常抽象类,将错误信息国际化
 * 
 */
public abstract class AbstractI18NMessageException extends RuntimeException {
	/** UID */
	private static final long serialVersionUID = 2148374270769534530L;

	/** 错误代码,默认为未知错误 */
	private String errorCode = "UNKNOW_ERROR";

	public Object[] getErrorArgs() {
		return errorArgs;
	}

	public void setErrorArgs(Object[] errorArgs) {
		this.errorArgs = errorArgs;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	/** 错误信息中的参数 */
	protected Object[] errorArgs;

	/** 默认信息 */
	protected String defaultMessage;

	public AbstractI18NMessageException() {
		this(null, null, null);
	}

	public AbstractI18NMessageException(String errorCode) {
		this(errorCode, null, null, null);
	}

	public AbstractI18NMessageException(String defaultMessage, Throwable cause) {
		this(null, null, defaultMessage, cause);
	}

	public AbstractI18NMessageException(String errorCode, Object[] errorArgs) {
		this(errorCode, errorArgs, null, null);
	}

	public AbstractI18NMessageException(String errorCode, Object[] errorArgs, Throwable cause) {
		this(errorCode, errorArgs, null, cause);
	}

	public AbstractI18NMessageException(String errorCode, Object[] errorArgs, String defaultMessage, Throwable cause) {
		super(defaultMessage, cause);
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
	}

	public String getResourceMessage() {
		 String message = null;
	        if (!StringUtil.isEmpty(errorCode)) {
	            message = MessageUtils.message(errorCode, errorArgs);
	        }
	        if (message == null) {
	            message = defaultMessage;
	        }
	     return message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}