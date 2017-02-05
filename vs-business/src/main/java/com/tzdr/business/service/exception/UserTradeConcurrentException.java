package com.tzdr.business.service.exception;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年1月19日上午9:33:02
 */
public class UserTradeConcurrentException extends RuntimeTzdrException{
	
	private static final long serialVersionUID = -6076509492443916500L;

	public UserTradeConcurrentException(String code, Object[] args) {
        super(code, args);
    }

}
