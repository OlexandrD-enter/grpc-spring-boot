package com.example.api.exception;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommonExceptionInfo {

  private final int status;
  private final String message;
  private final LocalDateTime timestamp;

  public CommonExceptionInfo(int status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }
}
