package com.example.festival.service.domain.model.festival;

import com.example.festival.service.domain.ValueObject;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FestivalId that = (FestivalId) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
