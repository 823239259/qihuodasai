
package com.tzdr.business.cms.exception;

import com.tzdr.common.exception.GeneralException;



/**
 * 
 * @zhouchen
 * 2014年12月25日
 */
public class RoleException extends GeneralException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6327566533811359215L;

	public RoleException(String code, Object[] args) {
        super(code, args);
    }

}
