package com.jaylanz.web.advice;

import com.jaylanz.domain.vo.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>>handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(BaseResponse.BAD_PARAMS(), HttpStatus.BAD_REQUEST);
    }

    // TODO: handle the general Exception class at last
}
