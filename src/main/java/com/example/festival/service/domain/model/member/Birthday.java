package com.example.festival.service.domain.model.member;

import java.time.LocalDate;
import java.util.Objects;

public class Birthday {

  private LocalDate value;

  private Birthday() {
  }

  public Birthday(LocalDate value) {
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
    Birthday birthday = (Birthday) o;
    return Objects.equals(value, birthday.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
