package com.example.festival.service.domain.model.festival;

import com.example.festival.service.domain.ValueObject;

/**
 * 大会番号.
 */
public class FestivalId implements ValueObject {

  long value;

  public FestivalId(long value) {
    this.value = value;
  }

  private FestivalId() {
  }

  public long value() {
    return value;
  }
}
