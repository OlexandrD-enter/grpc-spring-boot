package com.example.service.exception;


import io.grpc.Status.Code;

public class GrpcServerException extends GenericException {

  public GrpcServerException(Code exCode, String message) {
    super(message);
    code = exCode;
  }
}
