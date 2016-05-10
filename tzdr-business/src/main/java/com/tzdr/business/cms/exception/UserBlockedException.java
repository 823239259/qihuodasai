package com.tzdr.business.cms.exception;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class UserBlockedException extends UserException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5745900197535500958L;

	public UserBlockedException(String reason) {
        super("user.blocked", new Object[]{reason});
  }
}
