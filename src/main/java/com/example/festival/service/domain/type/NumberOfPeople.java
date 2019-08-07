package com.example.festival.service.domain.type;

import com.example.festival.service.domain.ValueObject;

import java.util.Objects;

public class NumberOfPeople implements ValueObject {

  private int value;

  private NumberOfPeople() {
  }

  public NumberOfPeople(int value) {
    this.value = value;
  }

  public int value() {
    return value;
  }

  public NumberOfPeople increment() {

    return new NumberOfPeople(value + 1);
  }

  /**
   * 同じ人数かどうかを返す.
   */
  public boolean same(NumberOfPeople other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }

    return value == other.value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NumberOfPeople that = (NumberOfPeople) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
