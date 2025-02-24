package com.auth.auth.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.auth.auth.user")
public class UserExceptionController {
    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public ResponseEntity<String> invalidUserRequestHandler(UserException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
