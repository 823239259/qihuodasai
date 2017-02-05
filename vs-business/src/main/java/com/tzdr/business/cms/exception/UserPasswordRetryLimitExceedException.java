package com.tzdr.business.cms.exception;

/**
 * <p>User: Lin Feng
 * <p>Date: 13-3-11 下午8:29
 * <p>Version: 1.0
 */
public class UserPasswordRetryLimitExceedException extends UserException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5159835129520796941L;

	public UserPasswordRetryLimitExceedException(int retryLimitCount) {
        super("user.password.retry.limit.exceed", new Object[]{retryLimitCount});
    }
}
