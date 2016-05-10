
package com.tzdr.business.exception;

import com.tzdr.common.exception.GeneralException;



/**
 * 
 * @zhouchen
 * 2014年12月25日
 */
public class OperationalConfigException extends GeneralException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8342795583737614945L;

	public OperationalConfigException(String code, Object[] args) {
        super(code, args);
    }

}
