
package com.tzdr.business.exception;

import com.tzdr.common.exception.GeneralException;

/**
 * @ClassName SpotSalesmanException
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月9日
 */
public class SpotSalesmanException extends GeneralException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8342795583737614945L;

	public SpotSalesmanException(String code, Object[] args) {
        super(code, args);
    }

}
