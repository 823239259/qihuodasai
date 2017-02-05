package com.tzdr.common.auth;

import org.springframework.core.NestedRuntimeException;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class SearchException extends NestedRuntimeException {

    public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
