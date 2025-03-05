package com.auth.auth.common.exception;

import com.auth.auth.common.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice("com.auth.auth.user")
public class UserExceptionController {
    @ExceptionHandler(value = UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse invalidUserRequestHandler(UserException e) {
        return ErrorResponse
                .builder()
                .code("400")
                .message(e.getMessage())
                .build();
    }
}
