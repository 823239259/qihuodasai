package com.tzdr.business.service.exception;

/**
 * 
 * <p>用户不存在异常</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年1月6日下午8:26:40
 */
public class WuserConcurrencyUpdateException extends RuntimeTzdrException {
	
	private static final long serialVersionUID = 4717005022813938074L;
	
	public WuserConcurrencyUpdateException(String code, Object[] args) {
		super(code, args);
		// TODO Auto-generated constructor stub
	}

	

}
