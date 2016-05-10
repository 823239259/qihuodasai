package com.tzdr.business.cms.exception;


/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class UserNotExistsException extends UserException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6222964268393265409L;

	public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
