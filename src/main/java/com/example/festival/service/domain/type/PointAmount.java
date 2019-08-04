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

  /**
   * Constructor.
   */
  public PointAmount(BigDecimal value) {

    if (value == null) {
      throw new IllegalArgumentException("ポイント数にnullは指定できません");
    }

    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("ポイント数はゼロ以上を指定してください");
    }

    if (value.scale() > 0) {
      throw new IllegalArgumentException("ポイント数は整数を指定してください");
    }

    this.value = value;
  }

  public BigDecimal value() {
    return value;
  }
}
