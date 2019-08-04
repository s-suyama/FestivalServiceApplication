package com.example.festival.service.presentation.application;

import com.example.festival.service.application.application.ApplicationCommandService;
import com.example.festival.service.application.application.ApplyForEntryRequest;
import com.example.festival.service.application.payment.PaymentCommandService;
import com.example.festival.service.application.payment.PaymentRequest;
import com.example.festival.service.support.ValidationErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

  private final ApplicationCommandService applicationCommandService;

  private final PaymentCommandService paymentCommandService;

  /**
   * Constructor.
   */
  public ApplicationController(
      ApplicationCommandService applicationCommandService,
      PaymentCommandService paymentCommandService) {

    this.applicationCommandService = applicationCommandService;
    this.paymentCommandService = paymentCommandService;
  }

  /**
   * エントリ枠に申し込む.
   */
  @PostMapping(value = "/applications/entry",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> applyForEntry(
      @RequestBody @Validated ApplyForEntryRequest request, Errors errors) {

    if (errors.hasErrors()) {
      throw new ValidationErrorException(errors);
    }

    applicationCommandService.applyForEntry(request);

    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  /**
   * 入金する.
   */
  @PostMapping(value = "/applications/payment",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> payToApplication(
      @RequestBody @Validated PaymentRequest request, Errors errors) {

    if (errors.hasErrors()) {
      throw new ValidationErrorException(errors);
    }

    paymentCommandService.payToApplication(request);

    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
