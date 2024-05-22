package com.example.service.exception;

import io.grpc.Status.Code;
import lombok.Getter;

@Getter
public abstract class GenericException extends RuntimeException {

  protected Code code;

  protected GenericException(String message) {
    super(message);
  }
}
