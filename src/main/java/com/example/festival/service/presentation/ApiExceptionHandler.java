package com.example.festival.service.presentation;

import com.example.festival.service.support.BusinessErrorException;
import com.example.festival.service.support.ValidationErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle All Exception.
   */
  @ExceptionHandler
  public ResponseEntity<Object> handleAllException(
      Exception ex,
      WebRequest request) {

    log.error("システムエラー", ex);

    ApiError apiError = createApiError(ex);
    return super.handleExceptionInternal(
        ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  /**
   * Handle ValidationErrorException.
   */
  @ExceptionHandler
  public ResponseEntity<Object> handleValidationErrorException(
      ValidationErrorException ex, WebRequest request) {

    ApiError apiError = createApiError(ex);
    if (ex instanceof ValidationErrorException) {

      for (FieldError error : ex.getErrors().getFieldErrors()) {
        apiError.addDetail(error.getField(), error.getDefaultMessage());
      }
    }

    return super.handleExceptionInternal(ex, apiError, null, HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Handle BusinessErrorException.
   */
  @ExceptionHandler
  public ResponseEntity<Object> handleBusinessErrorException(
      BusinessErrorException ex, WebRequest request) {

    ApiError apiError = createApiError(ex);

    return super.handleExceptionInternal(ex, apiError, null, HttpStatus.BAD_REQUEST, request);
  }

  private ApiError createApiError(Exception ex) {
    ApiError apiError = new ApiError();

    apiError.setMessage(ex.getMessage());
    return apiError;
  }
}
