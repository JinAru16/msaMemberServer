package com.auth.auth.common.exception;

import com.auth.auth.common.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        ErrorResponse response =  ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다")
                .build();
        for(FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ErrorResponse badCredentialsHandler(BadCredentialsException e) {
        ErrorResponse response =  ErrorResponse.builder()
                .code("400")
                .message("로그인이 불가능합니다. 아이디나 비밀번호를 확인해주세요.")
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    public ErrorResponse usernameNotFoundHandler(UsernameNotFoundException e) {
        ErrorResponse response =  ErrorResponse.builder()
                .code("400")
                .message("로그인이 불가능합니다. 아이디나 비밀번호를 확인해주세요.")
                .build();

        return response;
    }
}
