package com.example.festival.service.domain.model.entry;

import com.example.festival.service.domain.ValueObject;

import java.util.Objects;

/**
 * エントリ枠番号.
 */
public class EntryId implements ValueObject {

  private long value;

  private EntryId() {
  }

  public EntryId(long value) {
    this.value = value;
  }

  public long value() {
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
    return value == entryId.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
