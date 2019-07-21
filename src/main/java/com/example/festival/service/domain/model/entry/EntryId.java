package com.example.festival.service.domain.model.entry;

import com.example.festival.service.domain.ValueObject;

import java.util.Objects;

/**
 * エントリ枠番号.
 */
public class EntryId implements ValueObject {

  private Integer value;

  private EntryId() {
  }

  public EntryId(Integer value) {
    this.value = value;
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
    EntryId entryId = (EntryId) o;
    return Objects.equals(value, entryId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
