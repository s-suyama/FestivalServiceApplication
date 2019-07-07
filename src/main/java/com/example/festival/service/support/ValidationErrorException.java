package com.example.festival.service.support;

import org.springframework.validation.Errors;

public class ValidationErrorException extends RuntimeException {

  private Errors errors;

  public ValidationErrorException(Errors errors) {
    super("入力内容にエラーがあります");
    this.errors = errors;
  }

  public Errors getErrors() {
    return errors;
  }
}
