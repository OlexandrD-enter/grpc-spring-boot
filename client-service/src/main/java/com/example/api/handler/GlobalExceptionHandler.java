package com.example.api.handler;

import com.example.api.exception.CommonExceptionInfo;
import com.example.service.exception.GrpcServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(GrpcServerException.class)
  public ResponseEntity<CommonExceptionInfo> handleStatusRuntimeException(GrpcServerException ex) {
    return new ResponseEntity<>(
        new CommonExceptionInfo(ex.getCode().value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
  }
}
