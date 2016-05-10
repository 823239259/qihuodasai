
package com.tzdr.common.exception;

import com.tzdr.common.exception.GeneralException;

/**
 * <P>classname:BeanUtilsException</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年8月17日
 * @version 1.0
 */
public class BeanUtilsException extends GeneralException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -94451706794140989L;

	public BeanUtilsException(String code, Object[] args) {
        super(code, args);
    }

}
