package com.tzdr.common.exception;

/**
 * 一般业务异常（非事务异常）
 *  
 */
public class GeneralException extends AbstractI18NMessageException {

	/** serialVersionUID */
	private static final long serialVersionUID = -6910756266634262775L;

	public GeneralException() {
		super();
	}

	public GeneralException(String errorCode, Object[] errorArgs, String defaultMessage, Throwable cause) {
		super(errorCode, errorArgs, defaultMessage, cause);
	}

	public GeneralException(String errorCode, Object[] errorArgs, Throwable cause) {
		super(errorCode, errorArgs, cause);
	}

	public GeneralException(String errorCode, Object[] errorArgs) {
		super(errorCode, errorArgs);
	}

	public GeneralException(String debugMessage, Throwable cause) {
		super(debugMessage, cause);
	}

	public GeneralException(String errorCode) {
	}

}
