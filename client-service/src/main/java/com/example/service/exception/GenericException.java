package com.example.service.exception;

import io.grpc.Status.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GenericException extends RuntimeException {

  protected Code code;

  protected GenericException(String message) {
    super(message);
  }
}
