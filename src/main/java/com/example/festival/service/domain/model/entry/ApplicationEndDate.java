package com.example.festival.service.domain.model.entry;

import com.example.festival.service.domain.ValueObject;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 募集終了年月日.
 */
public class ApplicationEndDate implements ValueObject {

  private LocalDate value;

  private ApplicationEndDate() {
  }

  public ApplicationEndDate(LocalDate value) {
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
    ApplicationEndDate that = (ApplicationEndDate) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
