package com.example.festival.service.support;

public class BusinessErrorException extends RuntimeException {

  /**
   * constructor.
   *
   */
  public BusinessErrorException(String message) {
    super(message);
  }

  /**
   * constructor.
   *
   */
  public BusinessErrorException(Exception e) {
    super(e);
  }
}
