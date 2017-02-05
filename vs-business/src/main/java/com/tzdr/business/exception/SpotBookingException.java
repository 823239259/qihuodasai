
package com.tzdr.business.exception;

import com.tzdr.common.exception.GeneralException;

/**
 * @ClassName SpotBookingException
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月9日
 */
public class SpotBookingException extends GeneralException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8342795583737614945L;

	public SpotBookingException(String code, Object[] args) {
        super(code, args);
    }

}
