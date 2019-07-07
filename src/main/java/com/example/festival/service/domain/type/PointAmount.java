package com.example.festival.service.domain.type;

import com.example.festival.service.domain.ValueObject;

import java.math.BigDecimal;

/**
 * ポイント数.
 */
public class PointAmount implements ValueObject {

  private BigDecimal value;

  private PointAmount() {
  }

  public PointAmount(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal value() {
    return value;
  }
}
