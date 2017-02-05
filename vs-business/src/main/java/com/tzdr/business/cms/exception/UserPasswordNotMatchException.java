package com.tzdr.business.cms.exception;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class UserPasswordNotMatchException extends UserException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6537047250901183785L;

	public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
