
package com.tzdr.business.hkstock.exception;

import com.tzdr.common.exception.GeneralException;



/**
 * 
 * @zhouchen
 * 2014年12月25日
 */
public class HkStockParamsException extends GeneralException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8342795583737614945L;

	public HkStockParamsException(String code, Object[] args) {
        super(code, args);
    }

}
