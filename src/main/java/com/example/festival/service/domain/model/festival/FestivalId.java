package com.example.festival.service.domain.model.festival;

import com.example.festival.service.domain.ValueObject;

/**
 * 大会番号.
 */
public class FestivalId implements ValueObject {

  Integer value;

  public FestivalId(Integer value) {
    this.value = value;
  }

  private FestivalId() {
  }

  public Integer value() {
    return value;
  }
}
