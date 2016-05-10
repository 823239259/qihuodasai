
package com.tzdr.business.cms.exception;

import com.tzdr.common.exception.GeneralException;



/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class UserException extends GeneralException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3790669114449089717L;

	public UserException(String code, Object[] args) {
        super(code, args);
    }

}
