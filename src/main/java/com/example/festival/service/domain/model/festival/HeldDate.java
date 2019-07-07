package com.example.festival.service.domain.model.festival;

import com.example.festival.service.domain.ValueObject;
import java.time.LocalDate;

/**
 * 開催年月日.
 */
public class HeldDate implements ValueObject {

  LocalDate value;

  public HeldDate(LocalDate value) {
    this.value = value;
  }

  private HeldDate() {
  }

  public LocalDate value() {
    return value;
  }
}
