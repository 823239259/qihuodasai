
package com.tzdr.business.cms.exception;

import com.tzdr.common.exception.GeneralException;



/**
 * 
 * @zhouchen
 * 2014年12月25日
 */
public class AccountException extends GeneralException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4808686260994047641L;

	public AccountException(String code, Object[] args) {
        super(code, args);
    }

}
