package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.ValueObject;

import java.time.LocalDate;

/**
 * 参加申込年月日.
 */
public class ApplicationDate implements ValueObject {

  private LocalDate value;

  private ApplicationDate() {
  }

  public ApplicationDate(LocalDate value) {
    this.value = value;
  }

  public LocalDate value() {
    return value;
  }
}
