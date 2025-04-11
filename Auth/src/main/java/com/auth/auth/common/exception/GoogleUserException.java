package com.auth.auth.common.exception;

import org.springframework.security.core.AuthenticationException;

public class GoogleUserException extends AuthenticationException {
    public GoogleUserException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public GoogleUserException(String msg) {
        super(msg);
    }
}
