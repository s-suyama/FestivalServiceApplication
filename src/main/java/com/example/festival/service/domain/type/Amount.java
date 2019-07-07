package com.example.festival.service.domain.type;

import com.example.festival.service.domain.ValueObject;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 金額.
 */
public class Amount implements ValueObject {

  private BigDecimal value;

  private Amount() {
  }

  public Amount(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Amount amount = (Amount) o;
    return Objects.equals(value, amount.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
