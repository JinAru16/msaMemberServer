package com.auth.auth.common.exception;

import com.auth.auth.common.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.auth.auth.user")
public class UserExceptionController {
    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public ErrorResponse invalidUserRequestHandler(UserException e) {
        return ErrorResponse
                .builder()
                .code("400")
                .message(e.getMessage())
                .build();
    }
}
