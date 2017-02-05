package com.tzdr.business.service.exception;

import com.tzdr.common.exception.GeneralException;

/**
 * 
 * <p></p>
 * @author LiuQing
 * @see 基类异常
 * @version 2.0
 * 2015年1月6日下午8:25:07
 */
public class RuntimeTzdrException extends GeneralException {

	private static final long serialVersionUID = 1404539853674376043L;
	
	public RuntimeTzdrException(String code, Object[] args) {
        super(code, args);
    }

}
