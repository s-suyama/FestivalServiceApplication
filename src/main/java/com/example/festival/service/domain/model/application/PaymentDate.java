package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.ValueObject;

import java.time.LocalDate;

/**
 * 入金年月日.
 */
public class PaymentDate implements ValueObject {

  private LocalDate value;

  private PaymentDate() {
  }

  public PaymentDate(LocalDate value) {
    this.value = value;
  }

  public LocalDate value() {
    return value;
  }
}
