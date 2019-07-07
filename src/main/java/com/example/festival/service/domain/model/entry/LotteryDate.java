package com.example.festival.service.domain.model.entry;

import com.example.festival.service.domain.ValueObject;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 抽選年月日.
 */
public class LotteryDate implements ValueObject {

  private LocalDate value;

  private LotteryDate() {
  }

  public LotteryDate(LocalDate value) {
    this.value = value;
  }

  public LocalDate value() {
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
    LotteryDate that = (LotteryDate) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
